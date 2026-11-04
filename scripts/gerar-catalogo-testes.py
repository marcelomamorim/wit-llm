#!/usr/bin/env python3
"""Gera catálogo da coorte comum a partir dos manifestos locais, preservando o Java."""
import csv
import hashlib
import json
import pathlib
import re
import shutil
import tempfile

ROOT = pathlib.Path(__file__).resolve().parents[1]
COHORT = ROOT / 'generated/analysis/comparacao-tres-modelos-20261104'
DEST = ROOT / 'docs/testes-gerados'
GPT = ROOT / 'generated/execution/azure-gpt-paired-20261103-v2'
HAIKU = ROOT / 'generated/experiments/anthropic/haiku-5-5-historical-4096-20261101'
SECRET = re.compile(rb'sk-ant-[A-Za-z0-9_-]{20,}|sk-proj-[A-Za-z0-9_-]{20,}|gh[pousr]_[A-Za-z0-9_]{20,}|AKIA[A-Z0-9]{16}|-----BEGIN (?:RSA |EC |OPENSSH )?PRIVATE KEY-----')

def main():
    if DEST.exists():
        raise SystemExit('Destino já existe: preserve-o e escolha outro local antes de gerar novamente.')
    cohort = set((COHORT / 'coorte-3232.txt').read_text().splitlines())
    if len(cohort) != 3232:
        raise ValueError('Coorte inesperada')
    with (COHORT / 'pares-3232.csv').open(newline='') as f:
        outcomes = {(r['model'], r['target']): r for r in csv.DictReader(f)}
    items = []
    for model in ('nano', 'mini'):
        for plan in sorted((GPT / model / 'batches').glob('*/test-plan.json')):
            for row in json.loads(plan.read_text()):
                items.append((model, GPT / model, row))
    with (HAIKU / 'exported-tests.csv').open(newline='') as f:
        items.extend(('haiku', HAIKU, row) for row in csv.DictReader(f))
    entries, seen = [], set()
    staging = pathlib.Path(tempfile.mkdtemp(prefix='catalogo-testes-', dir=ROOT / 'generated'))
    try:
        for model, source_root, row in items:
            parts = row['custom_id'].split('/')
            target = '/'.join(['jdk'] + parts[2:])
            if target not in cohort:
                continue
            arm = row['arm']
            key = (model, target, arm)
            if key in seen or arm not in ('direct-tests', 'wit-context'):
                raise ValueError(f'Identificador duplicado ou cenário inválido: {key}')
            seen.add(key)
            source = (source_root / row['exported_path']).resolve()
            if not source.is_relative_to((source_root / 'generated-tests').resolve()):
                raise ValueError('Caminho fora de generated-tests')
            data = source.read_bytes()
            digest = hashlib.sha256(data).hexdigest()
            if digest != row['sha256'] or SECRET.search(data):
                raise ValueError(f'Hash divergente ou conteúdo exige revisão: {key}')
            rel = pathlib.PurePosixPath('arquivos') / model / arm / (row['pair_id'] + '.java')
            dest = staging / rel
            dest.parent.mkdir(parents=True, exist_ok=True)
            dest.write_bytes(data)
            historical = outcomes[(model, target)]
            prefix = 'direct' if arm == 'direct-tests' else 'wit'
            entries.append({'modelo': model, 'versao_modelo': row['model'], 'cenario': arm,
                            'alvo': target, 'id_pareado': row['pair_id'], 'arquivo': str(rel),
                            'caminho_original': row.get('original_relative_path', row['relative_path']), 'sha256': digest,
                            'estado_historico': historical[prefix + '_status'],
                            'aprovado_historico': historical[prefix + '_passed'] == '1',
                            'erro_compilacao_historico': historical[prefix + '_error'] == '1',
                            'resposta_malformada': row.get('malformed_files_array', False)})
        if len(entries) != 19392 or len(seen) != 19392:
            raise ValueError(f'Contagem incompleta: {len(entries)}')
        entries.sort(key=lambda x: (x['modelo'], x['cenario'], x['alvo']))
        (staging / 'catalogo.json').write_text(json.dumps(entries, ensure_ascii=False, separators=(',', ':')) + '\n')
        staging.rename(DEST)
        print(f'Gerados {len(entries)} registros com hashes verificados em {DEST.relative_to(ROOT)}')
    except BaseException:
        print('Geração incompleta preservada em', staging)
        raise

if __name__ == '__main__':
    main()
