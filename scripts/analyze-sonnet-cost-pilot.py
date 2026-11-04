#!/usr/bin/env python3
"""Offline cost projection from paired, size-stratified Sonnet Batch pilot."""
import collections
import json
from decimal import Decimal
from pathlib import Path
import sys

run = Path(sys.argv[1])
sampling = json.loads((run/'sampling.json').read_text())
rows = [json.loads(line) for line in (run/'responses.jsonl').read_text().splitlines()]
assert len(rows) == 2*len(sampling['strata'])
by_pair = collections.defaultdict(list)
input_total = output_total = 0
stops = collections.Counter()
for row in rows:
    parts = row['custom_id'].split('/')
    pair = '/'.join([parts[0]] + parts[2:])
    message = row['raw_provider_response']
    usage = message['usage']
    assert not usage.get('cache_creation_input_tokens') and not usage.get('cache_read_input_tokens')
    inp, out = usage['input_tokens'], usage['output_tokens']
    by_pair[pair].append({'input': inp, 'output': out, 'arm': parts[1]})
    input_total += inp
    output_total += out
    stops[message['stop_reason']] += 1

projected_input = projected_output = 0
for stratum in sampling['strata']:
    pair_rows = by_pair[stratum['target']]
    assert len(pair_rows) == 2 and len({r['arm'] for r in pair_rows}) == 2
    projected_input += sum(r['input'] for r in pair_rows)*stratum['population']
    projected_output += sum(r['output'] for r in pair_rows)*stratum['population']

def cost(inp, out):
    return (Decimal(inp)*Decimal('1.5') + Decimal(out)*Decimal('7.5'))/1000000

summary = json.loads((run/'batch-summary.json').read_text())
result = {
    'model': 'claude-sonnet-4-6', 'backend': 'anthropic_batch',
    'pilot_requests': len(rows), 'pilot_pairs': len(by_pair),
    'input_tokens': input_total, 'output_tokens': output_total,
    'pilot_cost_usd': str(cost(input_total, output_total)),
    'valid_responses': summary['valid_responses'],
    'generation_errors': summary['generation_errors'],
    'empty_generations': summary['empty_generations'], 'stop_reasons': dict(stops),
    'projected_full_requests': 8834,
    'projected_full_input_tokens': projected_input,
    'projected_full_output_tokens': projected_output,
    'projected_full_cost_usd': str(cost(projected_input, projected_output)),
    'projected_input_with_all_outputs_at_cap_usd': str(cost(projected_input, 8834*4096)),
    'previous_projection_from_first_20_usd': str(Decimal('0.540372')*Decimal(8834)/20),
    'cumulative_generation_cost_usd': str(Decimal('9.607065') + cost(input_total, output_total)),
    'method': sampling['method'],
    'caveats': ['Exploratory estimate, not a billing ceiling or provider invoice.',
                'One paired target per size stratum; output variability not fully measured.',
                'Prior pilot targets excluded from new selection.',
                'Truncated and invalid responses remain in billed usage; no repair or regeneration.',
                'Same historical prompt text; 4096 output cap differs from original nano/mini 1024.'],
}
(run/'cost-analysis.json').write_text(json.dumps(result, ensure_ascii=False, indent=2)+'\n')
print(json.dumps(result, ensure_ascii=False, indent=2))
