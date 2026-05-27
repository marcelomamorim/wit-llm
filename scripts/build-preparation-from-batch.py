#!/usr/bin/env python3
"""
Constrói preparation_jdk_global_impact.json e analysis_jdk_wit_filtered_sample.json
diretamente do wit_filtered.json + batch output, sem precisar catalogar o JDK inteiro.

Uso:
  python3 scripts/build-preparation-from-batch.py \
    --batch    /path/to/batch_output.jsonl \
    --wit      resources/wit-replication-package/data/output/jdk/wit_filtered.json \
    --jdk-root /Users/marceloamorim/Documents/unb/jdk \
    --run-dir  generated/experiments/jdk-global-impact-study/RUNSTAMP \
    --config   generated/configs/rodada-artigo.runtime.json
"""

import argparse
import json
import os
import re
import datetime
import sys


# --------------------------------------------------------------------------- #
# Slugify (espelha artefatos.Slugificar do Go)                                #
# --------------------------------------------------------------------------- #

def slugify(value: str) -> str:
    v = value.lower()
    result = []
    last_dash = False
    for c in v:
        if c.isalpha() or c.isdigit():
            result.append(c)
            last_dash = False
        else:
            if not last_dash:
                result.append('-')
                last_dash = True
    return ''.join(result).strip('-')


# --------------------------------------------------------------------------- #
# Normalização de caminhos Windows → relativos a src/                         #
# --------------------------------------------------------------------------- #

def normalize_class_path(raw_path: str, baseline_root: str) -> str:
    """Converte C:\\dev\\jdk\\src\\... para src/..."""
    p = raw_path.replace('\\', '/')
    root = baseline_root.replace('\\', '/').rstrip('/')
    if root and p.lower().startswith(root.lower()):
        p = p[len(root):].lstrip('/')
    else:
        # Fallback: encontra "src/" no caminho
        idx = p.lower().find('/src/')
        if idx >= 0:
            p = p[idx + 1:]
    return p


# --------------------------------------------------------------------------- #
# Extração de container e método a partir de qualifiedSignature               #
# --------------------------------------------------------------------------- #

def parse_qualified_signature(sig: str):
    """
    'com.sun.crypto.provider.DHKeyPairGenerator.DHKeyPairGenerator()'
    → ('com.sun.crypto.provider.DHKeyPairGenerator', 'DHKeyPairGenerator')
    """
    sig = sig.strip()
    paren = sig.find('(')
    prefix = sig[:paren] if paren >= 0 else sig
    dot = prefix.rfind('.')
    if dot < 0:
        return prefix, prefix
    return prefix[:dot], prefix[dot + 1:]


def derive_catalog_container(file_path: str) -> str:
    """
    Deriva o container no estilo do catálogo (outer class) a partir do file_path.
    'src/java.base/share/classes/com/sun/crypto/provider/PKCS12PBECipherCore.java'
    → 'com.sun.crypto.provider.PKCS12PBECipherCore'

    Isso espelha o que extrairMetodosJava faz: usa o pacote do arquivo +
    nome da classe do arquivo (sem hierarquia de inner classes).
    """
    fp = file_path.replace('\\', '/')
    for marker in ['/share/classes/', 'share/classes/', '/src/main/java/', 'src/main/java/',
                   '/src/test/java/', 'src/test/java/']:
        idx = fp.find(marker)
        if idx >= 0:
            rest = fp[idx + len(marker):]
            parts = rest.split('/')
            outer_class = parts[-1].replace('.java', '')
            package = '.'.join(parts[:-1])
            return (package + '.' + outer_class) if package else outer_class
    # Fallback: usa o nome do arquivo
    basename = fp.split('/')[-1].replace('.java', '')
    return basename


# --------------------------------------------------------------------------- #
# Extração de source code do método Java                                       #
# --------------------------------------------------------------------------- #

def extract_method_source(java_file: str, start_line: int, max_lines: int = 300) -> str:
    try:
        with open(java_file, 'r', encoding='utf-8', errors='replace') as f:
            lines = f.readlines()
    except OSError:
        return ''
    idx = start_line - 1
    if idx < 0 or idx >= len(lines):
        return ''
    result = []
    depth = 0
    for i in range(idx, min(idx + max_lines, len(lines))):
        line = lines[i]
        result.append(line.rstrip('\n'))
        depth += line.count('{') - line.count('}')
        if i > idx and depth <= 0:
            break
    return '\n'.join(result)


# --------------------------------------------------------------------------- #
# Derivar confiança (espelha derivarConfianca do Go)                          #
# --------------------------------------------------------------------------- #

def derive_confidence(maybe: bool, sound_symbolic: bool, sound_backwards: bool) -> float:
    if not maybe and sound_symbolic and sound_backwards:
        return 1.0
    if not maybe:
        return 0.85
    if sound_symbolic or sound_backwards:
        return 0.6
    return 0.45


def extract_exception_type(statement: str) -> str:
    s = statement.strip()
    if not s:
        return 'UnknownException'
    marker = 'new '
    idx = s.find(marker)
    if idx < 0:
        return s.strip(';')
    rest = s[idx + len(marker):]
    end = len(rest)
    for delim in ['(', ' ', ';']:
        pos = rest.find(delim)
        if 0 <= pos < end:
            end = pos
    val = rest[:end].strip()
    return val if val else 'UnknownException'


def build_evidence(m: dict) -> list:
    evs = [f"article_line={m['line']}", f"throwing_line={m['throwingLine']}"]
    for call in (m.get('callSequence') or []):
        if call.strip():
            evs.append('call:' + call.strip())
    return evs


def build_expath(method_id: str, m: dict, index: int, commit_hash: str) -> dict:
    trigger = (m.get('simplifiedPathConjunction') or '').strip()
    if not trigger:
        trigger = (m.get('pathCojunction') or '').strip()
    path_id = f"{method_id}#{m['throwingLine']}#{index}"
    return {
        'path_id': path_id,
        'exception_type': extract_exception_type(m.get('exception', '')),
        'trigger': trigger,
        'guard_conditions': [trigger] if trigger else [],
        'confidence': derive_confidence(m.get('maybe', False), m.get('soundSymbolic', False), m.get('soundBackwards', False)),
        'evidence': build_evidence(m),
        'source': 'witup_article',
        'metadata': {
            'exception_statement': m.get('exception', ''),
            'path_conjunction': m.get('pathCojunction', ''),
            'symbolic_path_conjunction': m.get('symbolicPathConjunction', ''),
            'backwards_path_conjunction': m.get('backwardsPathConjunction', ''),
            'simplified_path_conjunction': m.get('simplifiedPathConjunction', ''),
            'z3_inputs': m.get('z3Inputs', ''),
            'sound_symbolic': m.get('soundSymbolic', False),
            'sound_backwards': m.get('soundBackwards', False),
            'maybe': m.get('maybe', False),
            'line': m.get('line', 0),
            'throwing_line': m.get('throwingLine', 0),
            'is_static': m.get('isStatic', False),
            'target_only_arguments': m.get('targetOnlyArguments', False),
            'call_sequence': m.get('callSequence') or [],
            'inline_sequence': m.get('inlineSequence') or [],
            'commit_hash': commit_hash,
        },
    }


# --------------------------------------------------------------------------- #
# Main                                                                         #
# --------------------------------------------------------------------------- #

def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('--batch',    required=True, help='Batch output JSONL')
    parser.add_argument('--wit',      required=True, help='wit_filtered.json')
    parser.add_argument('--jdk-root', required=True, help='Local JDK checkout')
    parser.add_argument('--run-dir',  required=True, help='Output run directory')
    parser.add_argument('--config',   required=True, help='Runtime config JSON')
    args = parser.parse_args()

    os.makedirs(args.run_dir, exist_ok=True)

    # ------------------------------------------------------------------ #
    # 1. Ler custom_ids do batch                                          #
    # ------------------------------------------------------------------ #
    batch_method_slugs = set()
    with open(args.batch) as f:
        for line in f:
            line = line.strip()
            if not line:
                continue
            d = json.loads(line)
            cid = d.get('custom_id', '')
            parts = cid.split('/')
            if len(parts) >= 4:
                batch_method_slugs.add(parts[3])
    print(f'[build-prep] Unique method slugs no batch: {len(batch_method_slugs)}')

    # ------------------------------------------------------------------ #
    # 2. Ler wit_filtered.json e construir mapa slug → dados             #
    # ------------------------------------------------------------------ #
    with open(args.wit) as f:
        wf = json.load(f)
    baseline_root = wf['path']
    commit_hash = wf['commitHash']

    # Agrupa entradas por (container, method_name, line) porque uma mesma
    # assinatura pode gerar N expaths (um por exception)
    method_map: dict = {}  # slug → {'container', 'method_name', 'line', 'file_path', 'sig', 'raw_entries': []}

    for cls in wf['classes']:
        file_path = normalize_class_path(cls['path'], baseline_root)
        catalog_container = derive_catalog_container(file_path)
        for m in cls.get('methods', []):
            sig = m['qualifiedSignature'].strip()
            _, method_name = parse_qualified_signature(sig)
            container = catalog_container
            line = m.get('line', 0)
            method_id = f'{container}:{method_name}:{line}'
            sl = slugify(method_id)
            if sl not in method_map:
                method_map[sl] = {
                    'container': container,
                    'method_name': method_name,
                    'line': line,
                    'file_path': file_path,
                    'signature': sig,
                    'method_id': method_id,
                    'raw_entries': [],
                }
            method_map[sl]['raw_entries'].append(m)

    print(f'[build-prep] Total slugs no wit_filtered: {len(method_map)}')

    # ------------------------------------------------------------------ #
    # 3. Cruzar batch com WIT                                            #
    # ------------------------------------------------------------------ #
    matched = []
    not_found = []
    for slug in sorted(batch_method_slugs):
        if slug in method_map:
            matched.append(slug)
        else:
            not_found.append(slug)

    print(f'[build-prep] Correspondidos: {len(matched)} / {len(batch_method_slugs)}')
    if not_found:
        print(f'[build-prep] AVISO: {len(not_found)} slugs sem correspondência no WIT:')
        for s in not_found[:10]:
            print(f'  {s}')

    # ------------------------------------------------------------------ #
    # 4. Construir análises                                              #
    # ------------------------------------------------------------------ #
    analyses = []
    missing_files = []

    for slug in sorted(matched):
        entry = method_map[slug]
        container = entry['container']
        method_name = entry['method_name']
        line = entry['line']
        file_path = entry['file_path']
        method_id = entry['method_id']
        signature = entry['signature']
        raw_entries = entry['raw_entries']

        # Ler código fonte
        java_file = os.path.join(args.jdk_root, file_path)
        if not os.path.exists(java_file):
            missing_files.append(java_file)
            source_code = ''
        else:
            source_code = extract_method_source(java_file, line)

        # Detectar linha final
        end_line = line
        if source_code:
            end_line = line + source_code.count('\n')

        # Construir expaths
        expaths = []
        for i, m in enumerate(raw_entries, 1):
            expaths.append(build_expath(method_id, m, i, commit_hash))

        descriptor = {
            'method_id': method_id,
            'file_path': file_path,
            'container_name': container,
            'method_name': method_name,
            'signature': signature,
            'start_line': line,
            'end_line': end_line,
            'source': source_code,
        }

        raw_response = {
            'baseline': 'witup_article',
            'entry_count': len(raw_entries),
            'raw_entries': raw_entries,
        }

        analyses.append({
            'method': descriptor,
            'method_summary': 'Importado do pacote de replicação do WITUP.',
            'expaths': expaths,
            'raw_response': raw_response,
        })

    if missing_files:
        print(f'[build-prep] AVISO: {len(missing_files)} arquivos não encontrados no JDK local:')
        for f in missing_files[:5]:
            print(f'  {f}')

    # ------------------------------------------------------------------ #
    # 5. Escrever analysis JSON                                          #
    # ------------------------------------------------------------------ #
    now = datetime.datetime.utcnow().strftime('%Y-%m-%dT%H:%M:%SZ')
    run_id = os.path.basename(args.run_dir)
    analysis_path = os.path.join(args.run_dir, 'analysis_jdk_wit_filtered_sample.json')

    analysis_doc = {
        'run_id': run_id,
        'project_root': args.jdk_root,
        'model_key': 'openai_main',
        'source': 'witup_article',
        'strategy': 'witup_baseline_import',
        'generated_at': now,
        'total_methods': len(analyses),
        'analyses': analyses,
    }

    with open(analysis_path, 'w') as f:
        json.dump(analysis_doc, f, ensure_ascii=False, indent=2)
    print(f'[build-prep] Arquivo de análise escrito: {analysis_path} ({len(analyses)} métodos)')

    # ------------------------------------------------------------------ #
    # 6. Escrever preparation JSON                                       #
    # ------------------------------------------------------------------ #
    methods_list = []
    for i, a in enumerate(analyses, 1):
        m = a['method']
        methods_list.append({
            'index': i,
            'method_id': m['method_id'],
            'file_path': m['file_path'],
            'container_name': m['container_name'],
            'method_name': m['method_name'],
            'signature': m['signature'],
            'expath_count': len(a['expaths']),
        })

    total_expaths = sum(len(a['expaths']) for a in analyses)
    prep_path = os.path.join(args.run_dir, 'preparation_jdk_global_impact.json')

    preparation_doc = {
        'run_id': run_id,
        'generated_at': now,
        'project': 'jdk',
        'repository_url': 'https://github.com/openjdk/jdk.git',
        'wit_commit': commit_hash,
        'jdk_root': args.jdk_root,
        'wit_analysis_path': args.wit,
        'experimental_unit': 'global_project_impact',
        'method_level_analysis_secondary': True,
        'method_count': len(analyses),
        'expath_count': total_expaths,
        'request_count': len(analyses) * 2,
        'generation_model_key': 'openai_main',
        'analysis_path': analysis_path,
        'manifest_csv_path': os.path.join(args.run_dir, 'manifest_jdk_global_methods.csv'),
        'requests_jsonl_path': os.path.join(args.run_dir, 'requests_openai_batch_generation.jsonl'),
        'methods': methods_list,
    }

    with open(prep_path, 'w') as f:
        json.dump(preparation_doc, f, ensure_ascii=False, indent=2)
    print(f'[build-prep] Arquivo de preparação escrito: {prep_path}')

    # ------------------------------------------------------------------ #
    # 7. Escrever manifest CSV                                           #
    # ------------------------------------------------------------------ #
    manifest_path = preparation_doc['manifest_csv_path']
    with open(manifest_path, 'w') as f:
        f.write('index,project,method_id,file_path,container_name,method_name,signature,expath_count\n')
        for m in methods_list:
            row = [str(m['index']), 'jdk', m['method_id'], m['file_path'],
                   m['container_name'], m['method_name'], m['signature'], str(m['expath_count'])]
            f.write(','.join(f'"{c}"' for c in row) + '\n')
    print(f'[build-prep] Manifest CSV escrito: {manifest_path}')

    print(f'\n[build-prep] Concluído: {len(analyses)} métodos preparados.')
    if not_found:
        print(f'[build-prep] {len(not_found)} slugs do batch sem correspondência WIT (serão ignorados no evaluate).')


if __name__ == '__main__':
    main()
