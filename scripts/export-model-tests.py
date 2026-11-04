#!/usr/bin/env python3
"""Export generated Java verbatim, isolating each request to avoid class collisions.

This does not compile/run tests or copy the OpenJDK checkout. Sources, token counts,
and all attempts remain in responses.jsonl. Each file is mapped in exported-tests.csv.
"""
import argparse
import csv
import hashlib
import json
from pathlib import Path, PurePosixPath


def export_model(directory):
    directory = Path(directory)
    requests = {row['custom_id']: row for row in read_jsonl(directory / 'requests.jsonl')}
    attempts = read_jsonl(directory / 'responses.jsonl')
    latest = {row['custom_id']: row for row in attempts}
    records, errors, empty = [], [], []
    total_input = total_output = 0
    for custom_id, row in latest.items():
        if custom_id not in requests:
            raise ValueError('Resposta não corresponde ao arquivo de requisições')
        response = row.get('response')
        if row.get('error') or not response:
            errors.append({'custom_id': custom_id, 'reason': 'generation_error'})
            continue
        body = response['body']
        usage = body.get('usage', {})
        total_input += usage.get('input_tokens', 0)
        total_output += usage.get('output_tokens', 0)
        text = '\n'.join(c['text'] for o in body['output'] if o.get('type') == 'message'
                         for c in o.get('content', []) if c.get('type') == 'output_text')
        try:
            payload, _ = json.JSONDecoder().raw_decode(text[text.index('{'):])
            files = payload['files']
            if not isinstance(files, list):
                raise ValueError('files deve ser uma lista')
            if not files:
                empty.append({'custom_id': custom_id, 'notes': payload.get('notes', '')})
                continue
            arm = custom_id.split('/')[1]
            if arm not in ('wit-context', 'direct-tests'):
                raise ValueError('Braço experimental desconhecido')
            # Same pair folder in both arms and across models, independent of filenames.
            pair = '/'.join(custom_id.split('/')[2:])
            pair_id = hashlib.sha256(pair.encode()).hexdigest()[:20]
            pending, seen = [], set()
            for item in files:
                relative = item['relative_path']
                content = item['content']
                path = PurePosixPath(relative)
                if (not relative or '\\' in relative or '\x00' in relative or path.is_absolute()
                        or '..' in path.parts or path.suffix != '.java'
                        or not isinstance(content, str) or not content.strip()):
                    raise ValueError('Arquivo Java ou caminho inválido')
                target = directory / 'generated-tests' / arm / pair_id / str(path)
                base = (directory / 'generated-tests').resolve()
                if not target.resolve().is_relative_to(base) or target in seen:
                    raise ValueError('Caminho inseguro ou repetido')
                seen.add(target)
                pending.append((target, relative, content))
            for target, relative, content in pending:
                target.parent.mkdir(parents=True, exist_ok=True)
                encoded = content.encode('utf-8')
                if target.exists() and target.read_bytes() != encoded:
                    raise ValueError('Arquivo existente diverge da resposta; preserve-o antes de reexportar')
                target.write_bytes(encoded)
                records.append({'model': row['model'], 'arm': arm, 'pair_id': pair_id,
                                'custom_id': custom_id, 'relative_path': relative,
                                'exported_path': str(target.relative_to(directory)),
                                'sha256': hashlib.sha256(encoded).hexdigest()})
        except (KeyError, ValueError, TypeError, IndexError) as error:
            errors.append({'custom_id': custom_id, 'reason': str(error)})
    columns = ['model', 'arm', 'pair_id', 'custom_id', 'relative_path', 'exported_path', 'sha256']
    with (directory / 'exported-tests.csv').open('w', newline='') as stream:
        writer = csv.DictWriter(stream, fieldnames=columns)
        writer.writeheader()
        writer.writerows(records)
    summary = {'requested': len(requests), 'unique_responses': len(latest),
               'successful_responses': sum(bool(r.get('response')) and not bool(r.get('error')) for r in latest.values()),
               'attempts': len(attempts), 'attempt_errors': sum(bool(r.get('error')) for r in attempts),
               'java_files': len(records),
               'files_by_arm': {arm: sum(r['arm'] == arm for r in records) for arm in ('wit-context', 'direct-tests')},
               'missing_responses': sorted(set(requests) - set(latest)),
               'empty_generations': empty, 'export_errors': errors, 'input_tokens_successful': total_input,
               'output_tokens_successful': total_output,
               'compiled_or_executed': False}
    (directory / 'generation-summary.json').write_text(json.dumps(summary, indent=2, ensure_ascii=False) + '\n')
    return summary


def read_jsonl(path):
    return [json.loads(line) for line in Path(path).read_text().splitlines() if line.strip()]


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--run-root', required=True, type=Path)
    parser.add_argument('--models', nargs='+', default=['gpt_reasoning', 'gpt_standard', 'gemini', 'sonnet'])
    args = parser.parse_args()
    summaries = {model: export_model(args.run_root / model) for model in args.models}
    (args.run_root / 'generation-summary.json').write_text(json.dumps(summaries, indent=2, ensure_ascii=False) + '\n')
    for model, summary in summaries.items():
        print(model, json.dumps(summary, ensure_ascii=False))


if __name__ == '__main__':
    main()
