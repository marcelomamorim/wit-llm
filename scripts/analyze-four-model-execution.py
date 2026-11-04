#!/usr/bin/env python3
"""Summarize original jtreg .jtr outcomes against the exported-file plan."""
import argparse
import collections
import csv
import json
import re
from pathlib import Path


def analyze_jtr(path):
    if not path.exists():
        return {'outcome': 'not_discovered', 'compiled': False, 'jtreg_status': '', 'reason': 'Sem arquivo .jtr'}
    text = path.read_text(errors='replace')
    match = re.search(r'^test result: (Passed|Failed|Error)\.?(.*)$', text, re.M)
    if not match:
        match = re.search(r'^execStatus=(Passed|Failed|Error)\.?(.*)$', text, re.M)
    if not match:
        return {'outcome': 'incomplete_result', 'compiled': False, 'jtreg_status': '', 'reason': 'Resultado .jtr sem status final'}
    status, reason = match.group(1), match.group(2).strip()
    sections = re.split(r'^#section:', text, flags=re.M)[1:]
    compile_results, main_results = [], []
    for section in sections:
        name = section.splitlines()[0].strip()
        results = re.findall(r'^result: (.*)$', section, re.M)
        if name == 'compile':
            compile_results.extend(results)
        elif name == 'main':
            main_results.extend(results)
    compile_failed = any(result.startswith(('Failed', 'Error')) for result in compile_results)
    compiled = not compile_failed and (any(result.startswith('Passed') for result in compile_results)
                                      or bool(main_results) or status == 'Passed')
    if compile_failed or 'compilation failed' in reason.lower():
        outcome = 'compile_error'
    elif 'timed out' in reason.lower() or 'timeout' in reason.lower():
        outcome = 'timeout'
    elif status == 'Passed':
        outcome = 'passed'
    elif main_results and status == 'Failed':
        outcome = 'runtime_failure'
    else:
        outcome = 'harness_or_execution_error'
    return {'outcome': outcome, 'compiled': compiled, 'jtreg_status': status,
            'reason': reason, 'compile_results': '; '.join(compile_results)}


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--execution-dir', required=True, type=Path)
    args = parser.parse_args()
    root = args.execution_dir
    plan = json.loads((root / 'test-plan.json').read_text())
    details = []
    for item in plan:
        relative = Path(item['suite_relative']).with_suffix('.jtr')
        jtr = root / item['model_key'] / item['arm'] / 'work' / relative
        row = {key: item[key] for key in ('model_key', 'model', 'arm', 'pair_id', 'custom_id', 'exported_path', 'sha256')}
        row.update(analyze_jtr(jtr))
        row['jtr'] = str(jtr.relative_to(root)) if jtr.exists() else ''
        details.append(row)
    counts = {}
    for model in ('gpt_reasoning', 'gpt_standard', 'gemini', 'sonnet'):
        for arm in ('direct-tests', 'wit-context'):
            rows = [row for row in details if row['model_key'] == model and row['arm'] == arm]
            counter = collections.Counter(row['outcome'] for row in rows)
            counts[f'{model}/{arm}'] = {'java_files': len(rows), 'compiled': sum(row['compiled'] for row in rows),
                                      **{key: counter[key] for key in ('passed', 'compile_error', 'runtime_failure', 'timeout', 'harness_or_execution_error', 'not_discovered', 'incomplete_result')}}
    report = {'total_java_files': len(plan), 'total_passed': sum(row['outcome'] == 'passed' for row in details),
              'by_model_arm': counts, 'source_files_repaired': False, 'coverage_measured': False}
    (root / 'execution-summary.json').write_text(json.dumps(report, indent=2, ensure_ascii=False) + '\n')
    fields = ['model_key', 'model', 'arm', 'pair_id', 'custom_id', 'exported_path', 'sha256', 'outcome', 'compiled', 'jtreg_status', 'reason', 'compile_results', 'jtr']
    with (root / 'test-results.csv').open('w', newline='') as stream:
        writer = csv.DictWriter(stream, fieldnames=fields)
        writer.writeheader()
        writer.writerows(details)
    with (root / 'execution-summary.csv').open('w', newline='') as stream:
        writer = csv.DictWriter(stream, fieldnames=['model_arm', *next(iter(counts.values())).keys()])
        writer.writeheader()
        writer.writerows({'model_arm': key, **value} for key, value in counts.items())
    for key, value in counts.items():
        print(key, json.dumps(value))


if __name__ == '__main__':
    main()
