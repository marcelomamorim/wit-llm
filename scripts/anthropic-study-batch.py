#!/usr/bin/env python3
"""Native Anthropic Batch for the historical paired study, preserving prompt bytes.

prepare is offline. submit creates paid jobs. POST requests are never retried.
status/collect reuse durable remote IDs. Results retain failures and raw messages.
"""
import argparse
import contextlib
import concurrent.futures
import datetime as dt
import hashlib
import json
import os
from pathlib import Path, PurePosixPath
import re
from decimal import Decimal, ROUND_CEILING
import sys
import time
import subprocess
import urllib.error
import urllib.request

ROOT = Path(__file__).resolve().parents[1]
API_BASE = 'https://api.anthropic.com'
VERSION = '2023-06-01'
MODEL = 'claude-sonnet-4-6'
HAIKU = 'claude-haiku-5-5'


def digest(data):
    return hashlib.sha256(data).hexdigest()


def encoded(obj):
    return (json.dumps(obj, ensure_ascii=False, separators=(',', ':')) + '\n').encode()


def read_lines(path):
    return [json.loads(line) for line in Path(path).read_text().splitlines() if line.strip()]


def atomic(path, data):
    path = Path(path)
    path.parent.mkdir(parents=True, exist_ok=True)
    tmp = path.with_name(path.name + '.tmp')
    with tmp.open('wb') as f:
        f.write(data)
        f.flush()
        os.fsync(f.fileno())
    os.replace(tmp, path)


def save(path, obj):
    atomic(path, encoded(obj))


def now():
    return dt.datetime.now(dt.timezone.utc).isoformat()


@contextlib.contextmanager
def lock(run):
    path = run / '.batch.lock'
    try:
        path.mkdir()
    except FileExistsError:
        raise ValueError('Rodada bloqueada; confira o processo antes de remover .batch.lock')
    try:
        (path / 'pid').write_text(str(os.getpid()))
        yield
    finally:
        (path / 'pid').unlink(missing_ok=True)
        path.rmdir()


def prompts(row):
    body = row['body']
    if row.get('method') != 'POST' or row.get('url') != '/v1/responses' or body.get('previous_response_id'):
        raise ValueError('Requisição histórica incompatível')
    inputs = body['input']
    if len(inputs) != 2:
        raise ValueError('Esperadas duas mensagens históricas')
    texts = []
    for role, msg in zip(('system', 'user'), inputs):
        parts = msg['content']
        if msg['role'] != role or len(parts) != 1 or parts[0]['type'] != 'input_text':
            raise ValueError('Estrutura de prompt inesperada')
        text = parts[0]['text']
        if not isinstance(text, str) or not text:
            raise ValueError('Prompt vazio')
        texts.append(text)
    if body.get('temperature') != 0 or body.get('max_output_tokens') != 1024:
        raise ValueError('Temperatura/limite histórico diferente do protocolo')
    return texts


def native(row, model, max_tokens=1024):
    system, user = prompts(row)
    request = {'custom_id': 'r_' + digest(row['custom_id'].encode())[:60],
            'params': {'model': model, 'system': system, 'messages': [{'role': 'user', 'content': user}],
                       'temperature': 0, 'max_tokens': max_tokens, 'thinking': {'type': 'disabled'}}}
    if model == HAIKU:
        request['params'].pop('temperature')
        request['params']['thinking'] = {'type':'adaptive'}
        request['params']['output_config'] = {'effort':'low'}
    return request


def prepare(args):
    if args.model not in (MODEL, HAIKU):
        raise ValueError('Modelo não aprovado para o protocolo')
    sources, by_arm = [], {}
    for arm in ('direct-tests', 'wit-context'):
        p = args.sources / ('requests_' + arm + '.jsonl')
        sources.append({'path': str(p.resolve()), 'sha256': digest(p.read_bytes())})
        records = read_lines(p)
        indexed = {}
        for row in records:
            parts = row['custom_id'].split('/')
            if len(parts) < 4 or parts[1] != arm:
                raise ValueError('Identificador de braço inválido')
            pair = '/'.join([parts[0]] + parts[2:])
            if pair in indexed:
                raise ValueError('Alvo duplicado')
            prompts(row)
            indexed[pair] = row
        by_arm[arm] = indexed
    if set(by_arm['direct-tests']) != set(by_arm['wit-context']):
        raise ValueError('Conjuntos de alvos diferentes entre braços')
    if len(by_arm['direct-tests']) != 4417:
        raise ValueError('O protocolo espera os 4.417 alvos históricos')
    pairs = sorted(by_arm['direct-tests'])
    target_source = None
    if args.target_list:
        data = args.target_list.read_bytes()
        selected = json.loads(data)
        if not isinstance(selected,list) or len(set(selected)) != len(selected) or not set(selected) <= set(pairs):
            raise ValueError('Lista de alvos inválida')
        pairs = sorted(selected)
        target_source = {'path':str(args.target_list.resolve()),'sha256':digest(data)}
    ordered = [by_arm[arm][pair] for pair in pairs for arm in ('direct-tests', 'wit-context')]
    if args.method_limit:
        ordered = ordered[:2*args.method_limit]
    # Every job contains complete Direct/WIT pairs, including the small initial job.
    chunks = [ordered[:args.first_size]]
    chunks.extend(ordered[i:i+args.chunk_size] for i in range(args.first_size, len(ordered), args.chunk_size))
    protocol = {'model': args.model, 'api_version': VERSION, 'max_tokens': args.max_output_tokens, 'temperature': 0,
                'thinking': {'type': 'disabled'}, 'source_files': sources,
                'first_size': args.first_size, 'chunk_size': args.chunk_size,
                'generation_replicates': 1, 'prompt_changed': False, 'repair_or_retry_failed_generations': False}
    if args.model == HAIKU:
        protocol.pop('temperature')
        protocol['thinking'] = {'type':'adaptive'}
        protocol['output_config'] = {'effort':'low'}
    if args.method_limit:
        protocol['method_limit'] = args.method_limit
    if target_source:
        protocol['target_list'] = target_source
    fingerprint = digest(encoded(protocol))
    dest = args.run
    dest.mkdir(parents=True, exist_ok=True)
    with lock(dest):
        plan_path = dest / 'plan.json'
        if plan_path.exists():
            plan = json.loads(plan_path.read_text())
            if plan['fingerprint'] != fingerprint:
                raise ValueError('Rodada existente pertence a outro protocolo')
            load_plan(dest)
            return {'prepared': True, 'reused': True, 'requests': plan['total'], 'chunks': len(plan['chunks'])}
        if list((dest / 'jobs').glob('*.json')):
            raise ValueError('Jobs existentes sem plano; não sobrescrever')
        canonical = []
        jobs = []
        for i, chunk in enumerate(chunks, 1):
            converted = [native(row, args.model, args.max_output_tokens) for row in chunk]
            mapping = {row['custom_id']: orig['custom_id'] for row, orig in zip(converted, chunk)}
            if len(mapping) != len(chunk):
                raise ValueError('Colisão de custom_id')
            body = encoded({'requests': converted})
            if len(body) > 10_000_000:
                raise ValueError('Chunk excede limite local de 10 MB; reduza --chunk-size')
            rel = f'batches/chunk-{i:03}.json'
            atomic(dest / rel, body)
            map_rel = f'batches/chunk-{i:03}-ids.json'
            save(dest / map_rel, mapping)
            jobs.append({'number': i, 'requests': len(chunk), 'payload': rel, 'sha256': digest(body),
                         'mapping': map_rel, 'mapping_sha256': digest(encoded(mapping))})
            for row in chunk:
                copy = json.loads(json.dumps(row))
                copy['body']['model'] = args.model
                copy['body']['max_output_tokens'] = args.max_output_tokens
                copy['body'].pop('prompt_cache_key', None)
                if args.model == HAIKU:
                    copy['body'].pop('temperature',None)
                canonical.append(copy)
        canonical_data = b''.join(encoded(row) for row in canonical)
        atomic(dest / 'requests.jsonl', canonical_data)
        plan = {'schema_version': 1, 'created_at': now(), 'fingerprint': fingerprint, 'protocol': protocol,
                'pairs': len(ordered)//2, 'total': len(ordered), 'canonical_sha256': digest(canonical_data), 'chunks': jobs}
        save(plan_path, plan)
        return {'prepared': True, 'reused': False, 'pairs': len(ordered)//2, 'requests': len(ordered), 'chunks': len(jobs),
                'prompt_texts_verified': 2*len(ordered), 'fingerprint': fingerprint}


def load_plan(run):
    plan = json.loads((run / 'plan.json').read_text())
    if digest(encoded(plan['protocol'])) != plan['fingerprint']:
        raise ValueError('Protocolo alterado')
    if digest((run / 'requests.jsonl').read_bytes()) != plan['canonical_sha256']:
        raise ValueError('Requisições canônicas alteradas')
    for chunk in plan['chunks']:
        for key, hashkey in [('payload', 'sha256'), ('mapping', 'mapping_sha256')]:
            if digest((run / chunk[key]).read_bytes()) != chunk[hashkey]:
                raise ValueError('Artefato alterado: ' + chunk[key])
    return plan


class NoRedirect(urllib.request.HTTPRedirectHandler):
    def redirect_request(self, *args, **kwargs):
        return None


class APIError(Exception):
    def __init__(self, code, detail):
        self.code, self.detail = code, detail
        super().__init__(f'Anthropic HTTP {code}: {detail[:500]}')


class API:
    def __init__(self):
        key = os.getenv('ANTHROPIC_API_KEY')
        dotenv = ROOT / '.env'
        if dotenv.exists():
            for line in dotenv.read_text().splitlines():
                match = re.fullmatch(r'\s*(?:export\s+)?ANTHROPIC_API_KEY\s*=\s*(.*?)\s*', line)
                if match:
                    key = match.group(1)
                    if len(key)>1 and key[0]==key[-1] and key[0] in '\"\'': key=key[1:-1]
        if not key or not key.strip():
            raise ValueError('ANTHROPIC_API_KEY ausente')
        self.key = key.strip()
        self.opener = urllib.request.build_opener(NoRedirect())

    def call(self, method, path, body=None, raw=False):
        if not path.startswith('/v1/') or '://' in path or '..' in path:
            raise ValueError('Endpoint fora da API permitida')
        data = None if body is None else body if isinstance(body, bytes) else encoded(body)
        request = urllib.request.Request(API_BASE + path, data=data, method=method,
                 headers={'x-api-key': self.key, 'anthropic-version': VERSION, 'Content-Type': 'application/json'})
        # No automatic POST retry: a timeout may hide an already-created paid job.
        try:
            with self.opener.open(request, timeout=90) as response:
                data = response.read()
                return data if raw else json.loads(data)
        except urllib.error.HTTPError as error:
            detail = error.read().decode('utf-8', 'replace').replace(self.key, '[REDACTED]')
            raise APIError(error.code, detail) from None


def state_path(run, number):
    return run / 'jobs' / f'chunk-{number:03}.json'


def chunk_reservation(run, plan, chunk):
    if plan['protocol']['model'] != HAIKU:
        raise ValueError('O orçamento atual só autoriza Haiku 5.5')
    records = json.loads((run/chunk['payload']).read_text())['requests']
    input_reserved = output_reserved = 0
    for r in records:
        p = r['params']
        if p['model'] != HAIKU or p.get('max_tokens') != 4096 or 'temperature' in p or p.get('thinking') != {'type':'adaptive'} or p.get('output_config') != {'effort':'low'}:
            raise ValueError('Parâmetros não autorizados para a reserva de orçamento')
        # Deliberately pessimistic: one input token per UTF-8 byte plus 4096
        # server-overhead tokens/request. Also use the highest Haiku price tier
        # for every request, even though our prompts are much smaller than 100k.
        input_reserved += len(p['system'].encode()) + sum(len(m['content'].encode()) for m in p['messages']) + 4096
        output_reserved += p['max_tokens']
    amount = (Decimal(input_reserved)*Decimal('0.25') + Decimal(output_reserved)*Decimal('1.25'))/Decimal(1_000_000)
    return {'requests':len(records),'input_tokens_reserved':input_reserved,'output_tokens_reserved':output_reserved,
            'amount_usd':str(amount.quantize(Decimal('0.000001'),rounding=ROUND_CEILING)),
            'payload_sha256':chunk['sha256']}


def approve_budget(run, plan):
    path = ROOT/'generated/experiments/anthropic/budget-policy.json'
    if not path.exists(): raise ValueError('Política de orçamento ausente')
    policy = json.loads(path.read_text())
    if plan['total'] != policy['required_requests'] or plan['pairs'] != policy['required_pairs']:
        raise ValueError('A mesma amostra completa é obrigatória')
    costs = {str(c['number']):chunk_reservation(run,plan,c) for c in plan['chunks']}
    total = Decimal(policy['previous_pilots_cost_usd']) + sum(Decimal(x['amount_usd']) for x in costs.values())
    if total > Decimal(policy['operating_api_limit_usd']) or total > Decimal(policy['total_cap_usd']):
        raise ValueError('Reserva total excede o orçamento; nenhum envio autorizado')
    with lock(path.parent):
        policy = json.loads(path.read_text())
        if policy.get('reservations') and policy.get('approved_plan_fingerprint') != plan['fingerprint']:
            raise ValueError('Outro plano já possui reservas de orçamento')
        policy.update({'status':'approved','approved_run':str(run.resolve()),'approved_plan_fingerprint':plan['fingerprint'],
                       'chunk_allowances':costs,'total_conservative_reservation_usd':str(total),
                       'approved_at':now(),'pricing':'Haiku highest Batch tier: 0.25 input / 1.25 output USD per million',
                       'reservation_method':'UTF8 input bytes plus 4096 overhead tokens/request; full capped output; no retries',
                       'scope':'Mesmos 4417 alvos, 8834 solicitações; Haiku 5.5. Inclui 0.844547 USD dos pilotos Sonnet. Margem adicional fora do limite operacional de API.'})
        save(path,policy)
    save(run/'budget-approval.json',policy)
    return {'approved':True,'total_requests':plan['total'],'conservative_reserved_usd':str(total),
            'operating_api_limit_usd':policy['operating_api_limit_usd'],'user_cap_usd':policy['total_cap_usd']}


def pilot_allowance(policy, run, plan):
    allowance = policy.get('additional_pilots', {}).get(plan['fingerprint'])
    if not allowance:
        return None
    if (allowance['run'] != str(run.resolve()) or plan['protocol']['model'] != MODEL
            or plan['protocol']['max_tokens'] != 4096 or not 0 < plan['total'] <= 40
            or len(plan['chunks']) != 1):
        raise ValueError('Piloto fora do escopo autorizado')
    chunk = plan['chunks'][0]
    if allowance['payload_sha256'] != chunk['sha256'] or allowance['requests'] != plan['total']:
        raise ValueError('Payload do piloto difere da autorização')
    data = json.loads((run/chunk['payload']).read_text())['requests']
    input_bound = sum(len(r['params']['system'].encode()) + sum(len(m['content'].encode()) for m in r['params']['messages']) + 4096 for r in data)
    output_bound = sum(r['params']['max_tokens'] for r in data)
    amount = (Decimal(input_bound)*Decimal('1.5') + Decimal(output_bound)*Decimal('7.5'))/Decimal(1000000)
    if amount > Decimal(allowance['reserved_usd']):
        raise ValueError('Reserva insuficiente para o piloto')
    total = Decimal(policy['total_conservative_reservation_usd']) + sum(Decimal(p['reserved_usd']) for p in policy['additional_pilots'].values())
    if total > Decimal(policy['operating_api_limit_usd']) or total > Decimal(policy['total_cap_usd']):
        raise ValueError('Pilotos excedem orçamento global')
    return allowance


def reserve_budget(run, plan, chunk):
    path = ROOT/'generated/experiments/anthropic/budget-policy.json'
    if not path.exists():
        if plan.get('protocol',{}).get('model') == HAIKU:
            raise ValueError('Política de orçamento ausente; Haiku não pode ser enviado')
        return  # legacy Sonnet fixtures, before the budget requirement
    with lock(path.parent):
        policy=json.loads(path.read_text())
        allowance = pilot_allowance(policy, run, plan)
        if allowance:
            allowance['reserved_at'] = allowance.get('reserved_at', now())
            save(path, policy)
            return
        if policy.get('status')!='approved' or policy.get('approved_run')!=str(run.resolve()) or policy.get('approved_plan_fingerprint')!=plan['fingerprint']:
            raise ValueError('Envio bloqueado pelo teto de US$ 120; orçamento/modelo não aprovados')
        actual = chunk_reservation(run,plan,chunk)
        allowed = policy['chunk_allowances'].get(str(chunk['number']))
        if allowed != actual: raise ValueError('Reserva difere do payload aprovado')
        key=str(chunk['number'])
        existing=policy['reservations'].get(key)
        if existing and existing.get('payload_sha256')!=actual['payload_sha256']:
            raise ValueError('Reserva existente incompatível')
        policy['reservations'][key]=dict(actual,reserved_at=now())
        total=Decimal(policy['previous_pilots_cost_usd'])+sum(Decimal(x['amount_usd']) for x in policy['reservations'].values())
        if total>Decimal(policy['operating_api_limit_usd']):raise ValueError('Teto de custo atingido')
        save(path,policy)


def submit(run, plan, api, numbers):
    policy_path = ROOT / 'generated/experiments/anthropic/budget-policy.json'
    if policy_path.exists():
        policy = json.loads(policy_path.read_text())
        if not pilot_allowance(policy, run, plan) and (policy.get('status') != 'approved' or policy.get('approved_run') != str(run.resolve()) or policy.get('approved_plan_fingerprint') != plan['fingerprint']):
            raise ValueError('Envio bloqueado pelo teto de US$ 120; orçamento/modelo ainda não aprovados para esta rodada')
    submitted = []
    for c in plan['chunks']:
        if numbers and c['number'] not in numbers: continue
        path = state_path(run, c['number'])
        if path.exists():
            state = json.loads(path.read_text())
            if state.get('batch_id'):
                print(json.dumps({'chunk': c['number'], 'reused_batch': state['batch_id']}), flush=True)
                continue
            raise ValueError(f'Chunk {c["number"]} já tentado sem ID; reconcilie antes de reenviar')
        reserve_budget(run,plan,c)
        state = {'chunk': c['number'], 'fingerprint': plan['fingerprint'], 'status': 'submitting', 'started_at': now()}
        save(path, state)
        try:
            payload = api.call('POST', '/v1/messages/batches', (run / c['payload']).read_bytes())
            batch_id = payload.get('id', '')
            if not re.fullmatch(r'msgbatch_[A-Za-z0-9]+', batch_id):
                raise ValueError('Resposta de submissão sem ID válido')
            state.update({'batch_id': batch_id, 'status': payload['processing_status'], 'remote': payload, 'submitted_at': now()})
            save(path, state)
        except Exception as e:
            state['status'] = 'rejected' if isinstance(e, APIError) and 400 <= e.code < 500 else 'uncertain'
            state['error'] = str(e)
            save(path, state)
            raise
        item = {'chunk': c['number'], 'batch_id': batch_id, 'requests': c['requests'], 'status': state['status']}
        submitted.append(item)
        print(json.dumps(item), flush=True)
    return {'new_batches': len(submitted), 'new_requests': sum(x['requests'] for x in submitted)}


def normalize(message):
    if message.get('stop_reason') != 'end_turn':
        raise ValueError('Geração não concluída: ' + str(message.get('stop_reason')))
    text = ''.join(x.get('text','') for x in message.get('content',[]) if x.get('type') == 'text')
    if not text.strip(): raise ValueError('Texto vazio')
    # Parse without changing the answer or repairing Java. Schema validation is independent from compilation.
    try:
        obj, _ = json.JSONDecoder().raw_decode(text[text.index('{'):])
    except (ValueError, TypeError): raise ValueError('JSON de geração inválido') from None
    if not isinstance(obj, dict) or not isinstance(obj.get('files'), list):
        raise ValueError('Esquema sem files[]')
    for f in obj['files']:
        if not isinstance(f, dict) or not isinstance(f.get('content'), str) or not isinstance(f.get('relative_path'), str):
            raise ValueError('Arquivo sem conteúdo/caminho válido')
        p = PurePosixPath(f['relative_path'])
        if p.is_absolute() or '..' in p.parts or '\\' in str(p) or '\x00' in str(p) or p.suffix != '.java' or not f['content'].strip():
            raise ValueError('Arquivo Java inseguro/vazio')
    usage = message.get('usage', {})
    cached = usage.get('cache_read_input_tokens',0)
    created = usage.get('cache_creation_input_tokens',0)
    return {'id': message.get('id',''), 'model': message['model'], 'status': 'completed',
            'output': [{'type':'message','content':[{'type':'output_text','text':text}]}],
            'usage': {'input_tokens': usage.get('input_tokens',0)+cached+created,
                      'output_tokens': usage.get('output_tokens',0), 'input_tokens_details': {'cached_tokens':cached}}}


def convert_results(data, mapping, plan, batch_id):
    raw_rows = [json.loads(line) for line in data.decode().splitlines() if line.strip()]
    byid = {}
    for row in raw_rows:
        cid = row.get('custom_id')
        if cid not in mapping or cid in byid:
            raise ValueError('Resultado com ID desconhecido ou duplicado')
        byid[cid] = row
    if set(byid) != set(mapping):
        raise ValueError('Resultados incompletos; não marcar como coletados')
    records = []
    for native_id, original_id in mapping.items():
        r = byid[native_id]
        record = {'custom_id': original_id, 'provider':'anthropic', 'model':plan['protocol']['model'],
                  'model_config':plan['protocol'], 'fingerprint':plan['fingerprint'], 'batch_id':batch_id,
                  'execution_backend':'anthropic_batch', 'created_at':now(), 'native_result':r}
        result = r.get('result', {})
        if result.get('type') == 'succeeded':
            message = result['message']
            record['raw_provider_response'] = message
            try:
                if message.get('model') != plan['protocol']['model']:
                    raise ValueError('Modelo retornado difere do solicitado')
                body = normalize(message)
                record['response'] = {'status_code':200, 'request_id':message.get('id',''), 'body':body}
            except ValueError as error:
                record['error'] = {'type':'invalid_generation', 'message':str(error)}
        else:
            record['error'] = {'type':result.get('type','unknown'), 'detail':result.get('error')}
        records.append(record)
    return records


def reuse_pilot(run, plan, pilot_run, numbers):
    """Reuse only byte-identical paid jobs, with explicit provenance and new envelope fingerprint."""
    if run.resolve() == pilot_run.resolve():
        raise ValueError('Origem e destino devem ser rodadas diferentes')
    if (pilot_run / '.batch.lock').exists():
        raise ValueError('Piloto está em operação; aguarde antes de reutilizá-lo')
    pilot = load_plan(pilot_run)
    candidates = {c['sha256']: c for c in pilot['chunks']}
    reused = []
    for chunk in plan['chunks']:
        if chunk['number'] not in numbers:
            continue
        previous = candidates.get(chunk['sha256'])
        if not previous or chunk['mapping_sha256'] != previous['mapping_sha256']:
            raise ValueError('Lote do piloto não é byte a byte idêntico ao destino')
        old = json.loads(state_path(pilot_run, previous['number']).read_text())
        if old.get('fingerprint') != pilot['fingerprint'] or not old.get('batch_id'):
            raise ValueError('Checkpoint do piloto inválido')
        destination = state_path(run, chunk['number'])
        if destination.exists():
            existing = json.loads(destination.read_text())
            if existing.get('fingerprint') != plan['fingerprint'] or existing.get('batch_id') != old['batch_id']:
                raise ValueError('Lote de destino já possui outra tentativa')
            reused.append({'chunk': chunk['number'], 'batch_id': old['batch_id'], 'requests':chunk['requests']})
            continue
        state = dict(old)
        state.update({'chunk':chunk['number'], 'fingerprint':plan['fingerprint'],
                      'reused_from':{'run':str(pilot_run.resolve()),'fingerprint':pilot['fingerprint'],
                                     'chunk':previous['number']}, 'reused_at':now()})
        save(destination, state)
        raw = pilot_run / 'raw' / f'chunk-{previous["number"]:03}.jsonl'
        if raw.exists():
            data = raw.read_bytes()
            if digest(data) != state.get('result_sha256'):
                raise ValueError('Resultado original do piloto foi alterado')
            records = convert_results(data,json.loads((run/chunk['mapping']).read_text()),plan,state['batch_id'])
            atomic(run/'raw'/f'chunk-{chunk["number"]:03}.jsonl',data)
            atomic(run/'normalized'/f'chunk-{chunk["number"]:03}.jsonl',b''.join(encoded(r) for r in records))
        reused.append({'chunk':chunk['number'], 'batch_id':state['batch_id'], 'requests':chunk['requests']})
    return {'reused_jobs':reused, 'new_paid_requests':0}


def refresh(run, plan, api, collect=False):
    summary = {'planned_requests':plan['total'], 'submitted_requests':0, 'collected_requests':0,
               'jobs':[], 'valid_responses':0, 'generation_errors':0, 'empty_generations':0}
    combined=[]
    for c in plan['chunks']:
        path = state_path(run, c['number'])
        if not path.exists(): continue
        state = json.loads(path.read_text())
        if state.get('fingerprint') != plan['fingerprint']: raise ValueError('Job de outra rodada')
        if not state.get('batch_id'):
            summary['jobs'].append({'chunk':c['number'],'status':state['status']});continue
        batch_id=state['batch_id']
        remote=api.call('GET','/v1/messages/batches/'+batch_id)
        if remote.get('id')!=batch_id: raise ValueError('ID remoto inesperado')
        state.update({'remote':remote,'status':remote['processing_status'],'checked_at':now()})
        save(path,state)
        summary['submitted_requests']+=c['requests']
        summary['jobs'].append({'chunk':c['number'],'batch_id':batch_id,'status':state['status'],'counts':remote['request_counts']})
        native_path=run/'raw'/f'chunk-{c["number"]:03}.jsonl'
        if collect and state['status']=='ended':
            if native_path.exists():
                data=native_path.read_bytes()
                if state.get('result_sha256')!=digest(data):raise ValueError('Resultado bruto alterado')
            else:
                data=api.call('GET','/v1/messages/batches/'+batch_id+'/results',raw=True)
                atomic(native_path,data)
                state['result_sha256']=digest(data);save(path,state)
            mapping=json.loads((run/c['mapping']).read_text())
            records=convert_results(data,mapping,plan,batch_id)
            atomic(run/'normalized'/f'chunk-{c["number"]:03}.jsonl',b''.join(encoded(r) for r in records))
            state['collected_at']=now();save(path,state)
        normalized=run/'normalized'/f'chunk-{c["number"]:03}.jsonl'
        if normalized.exists(): combined.extend(read_lines(normalized))
    if combined:
        ids=[r['custom_id'] for r in combined]
        if len(ids)!=len(set(ids)):raise ValueError('Resultados canônicos duplicados')
        atomic(run/'responses.jsonl',b''.join(encoded(r) for r in combined))
    summary['collected_requests']=len(combined)
    for r in combined:
        if r.get('error'): summary['generation_errors']+=1
        else:
            summary['valid_responses']+=1
            raw=r['raw_provider_response'];text=''.join(c.get('text','') for c in raw['content'] if c.get('type')=='text')
            obj,_=json.JSONDecoder().raw_decode(text[text.index('{'):])
            summary['empty_generations']+=not bool(obj['files'])
    bill = Decimal('0')
    for r in combined:
        message = r.get('raw_provider_response')
        if not message: continue
        u = message.get('usage',{})
        input_total = sum(u.get(k,0) for k in ('input_tokens','cache_read_input_tokens','cache_creation_input_tokens'))
        if plan['protocol']['model']==HAIKU:
            ip,op = (Decimal('0.25'),Decimal('1.25')) if input_total>100000 else (Decimal('0.05'),Decimal('0.25'))
        else: ip,op = Decimal('1.5'),Decimal('7.5')
        bill += (ip*(Decimal(u.get('input_tokens',0))+2*Decimal(u.get('cache_creation_input_tokens',0))+Decimal('0.1')*Decimal(u.get('cache_read_input_tokens',0)))+op*Decimal(u.get('output_tokens',0)))/Decimal(1000000)
    summary['estimated_api_cost_usd']=str(bill.quantize(Decimal('0.000001')))
    summary['cost_is_provider_invoice']=False
    summary['generation_complete']=summary['collected_requests']==plan['total'] and summary['submitted_requests']==plan['total']
    save(run/'batch-summary.json',summary)
    return summary


def watch(run, interval, max_seconds):
    started=time.monotonic();api=API();errors=0
    while True:
        try:
            with lock(run):
                plan=load_plan(run)
                missing=[c['number'] for c in plan['chunks'] if not state_path(run,c['number']).exists()]
                if missing:raise ValueError('Watch não submete lotes; faltam chunks '+str(missing))
                summary=refresh(run,plan,api,collect=True)
            errors=0
            print(json.dumps({'checked_at':now(),'submitted':summary['submitted_requests'],
                             'collected':summary['collected_requests'],'valid':summary['valid_responses'],
                             'errors':summary['generation_errors'],'estimated_api_cost_usd':summary['estimated_api_cost_usd']}),flush=True)
            if summary['generation_complete']:
                subprocess.run([sys.executable,str(ROOT/'scripts/export-model-tests.py'),
                                '--run-root',str(run.parent),'--models',run.name],check=True)
                report=json.loads((run/'generation-summary.json').read_text())
                save(run/'completion.json',{'completed_at':now(),'batch_summary':summary,'generation_summary':report,
                     'compiled_or_executed':False,'all_prompts_historical':True,'budget_policy':str(ROOT/'generated/experiments/anthropic/budget-policy.json')})
                return {'complete':True,'requested':summary['planned_requests'],'valid_responses':summary['valid_responses'],
                        'java_files':report['java_files'],'estimated_api_cost_usd':summary['estimated_api_cost_usd']}
        except (APIError,urllib.error.URLError,TimeoutError) as error:
            errors+=1
            print(json.dumps({'watch_error':str(error),'consecutive_errors':errors}),flush=True)
            if errors>=6:raise
        if time.monotonic()-started>max_seconds:raise ValueError('Tempo de acompanhamento esgotado; resultados preservados')
        time.sleep(interval)


def estimate(run, plan, api):
    requests=[]
    for c in plan['chunks']:
        requests.extend(json.loads((run/c['payload']).read_text())['requests'])
    samples=[]
    # Stratified length sample within each arm; token counting does not generate text.
    for parity in (0,1):
        population=requests[parity::2]
        population.sort(key=lambda r: len(r['params']['system'])+len(r['params']['messages'][0]['content']))
        selected=[population[round((i+.5)*len(population)/8-.5)] for i in range(8)]
        sample_tokens=sample_chars=0
        for r in selected:
            p=r['params'];body={k:p[k] for k in ('model','system','messages')}
            result=api.call('POST','/v1/messages/count_tokens',body)
            chars=len(p['system'])+len(p['messages'][0]['content'])
            sample_tokens+=result['input_tokens'];sample_chars+=chars
        chars=sum(len(r['params']['system'])+len(r['params']['messages'][0]['content']) for r in population)
        samples.append({'arm': 'direct-tests' if parity==0 else 'wit-context','sample_requests':8,
                        'estimated_input_tokens':round(chars*sample_tokens/sample_chars)})
    estimate_tokens=sum(s['estimated_input_tokens'] for s in samples)
    input_price,output_price=(0.05,0.25) if plan['protocol']['model']==HAIKU else (1.5,7.5)
    result={'model':plan['protocol']['model'],'requests':plan['total'],'samples':samples,'estimated_input_tokens':estimate_tokens,
            'batch_input_price_per_million_usd':input_price,'batch_output_price_per_million_usd':output_price,
            'estimated_input_cost_usd':round(estimate_tokens*input_price/1e6,2),
            'maximum_output_tokens':plan['total']*plan['protocol']['max_tokens'],
            'maximum_output_cost_usd':round(plan['total']*plan['protocol']['max_tokens']*output_price/1e6,2),
            'estimated_input_plus_capped_output_usd':round(estimate_tokens*input_price/1e6+plan['total']*plan['protocol']['max_tokens']*output_price/1e6,2),
            'caveat':'Entrada estimada por amostra; não é teto financeiro. Não inclui impostos/câmbio. Sem cache explícito.'}
    save(run/'cost-estimate.json',result)
    return result


def main():
    p=argparse.ArgumentParser(description=__doc__)
    p.add_argument('action',choices=['prepare','submit','status','collect','estimate','reuse','approve-budget','watch'])
    p.add_argument('--run',type=Path,required=True)
    p.add_argument('--sources',type=Path,default=ROOT/'sast2026-artifact/prompts')
    p.add_argument('--model',default=MODEL)
    p.add_argument('--max-output-tokens',type=int,choices=[1024,4096],default=1024)
    p.add_argument('--method-limit',type=int,default=0,help='Somente para piloto separado; 0 = todos os alvos')
    p.add_argument('--target-list',type=Path,help='Lista explícita de alvos; somente para estudos com amostragem definida')
    p.add_argument('--first-size',type=int,default=20)
    p.add_argument('--chunk-size',type=int,default=500)
    p.add_argument('--chunks',type=int,nargs='+',help='Números dos chunks; obrigatório para submit')
    p.add_argument('--from-run',type=Path,help='Piloto já gerado; somente para reuse')
    p.add_argument('--poll-seconds',type=int,default=30)
    p.add_argument('--watch-max-seconds',type=int,default=86400)
    args=p.parse_args()
    if args.first_size<=0 or args.chunk_size<=0 or args.first_size%2 or args.chunk_size%2:
        p.error('Tamanhos devem ser positivos e pares')
    if args.method_limit<0 or args.method_limit>4417:
        p.error('--method-limit deve estar entre 0 e 4417')
    if not 10<=args.poll_seconds<=60 or args.watch_max_seconds<=0:
        p.error('Intervalo de consulta deve estar entre 10 e 60 segundos; duração deve ser positiva')
    try:
        if args.action=='prepare':result=prepare(args)
        elif args.action=='watch':result=watch(args.run,args.poll_seconds,args.watch_max_seconds)
        else:
            if args.action in ('submit','reuse') and not args.chunks:p.error('submit/reuse exige --chunks explicitamente')
            if args.action=='reuse' and args.from_run is None:p.error('reuse exige --from-run')
            with lock(args.run):
                plan=load_plan(args.run)
                if args.action in ('submit','reuse'):
                    if not set(args.chunks)<={c['number'] for c in plan['chunks']}:raise ValueError('Chunk inexistente')
                if args.action=='reuse':
                    result=reuse_pilot(args.run,plan,args.from_run,set(args.chunks))
                elif args.action=='approve-budget':
                    result=approve_budget(args.run,plan)
                else:
                    api=API()
                    if args.action=='submit':result=submit(args.run,plan,api,set(args.chunks))
                    elif args.action=='estimate':result=estimate(args.run,plan,api)
                    else:result=refresh(args.run,plan,api,args.action=='collect')
        print(json.dumps(result,ensure_ascii=False),flush=True)
    except (ValueError, OSError, APIError, urllib.error.URLError) as error:
        print(str(error),file=sys.stderr);return 1
    return 0

if __name__=='__main__':sys.exit(main())
