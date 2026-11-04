#!/usr/bin/env python3
"""Prepare an immutable, paired input subset for the Azure execution pilot."""
import argparse
import collections
import csv
import hashlib
import json
from pathlib import Path
import random
import shutil


def main():
    p = argparse.ArgumentParser(description=__doc__)
    p.add_argument('--generation', type=Path, required=True)
    p.add_argument('--output', type=Path, required=True)
    p.add_argument('--pairs', type=int, default=20)
    args = p.parse_args()
    if args.pairs <= 0 or args.output.exists():
        raise ValueError('Use a positive sample size and a new output directory')
    grouped = collections.defaultdict(list)
    with (args.generation/'exported-tests.csv').open() as stream:
        for row in csv.DictReader(stream):
            grouped[row['pair_id']].append(row)
    eligible = sorted(k for k, rows in grouped.items()
                      if {r['arm'] for r in rows} == {'direct-tests', 'wit-context'})
    chosen = sorted(random.Random(20261103).sample(eligible, args.pairs))
    plan = []
    for pair in chosen:
        for row in grouped[pair]:
            source = args.generation/row['exported_path']
            if not source.resolve().is_relative_to((args.generation/'generated-tests').resolve()):
                raise ValueError('Unsafe input path')
            if hashlib.sha256(source.read_bytes()).hexdigest() != row['sha256']:
                raise ValueError('Original Java hash mismatch')
            dest = args.output/row['exported_path']
            dest.parent.mkdir(parents=True, exist_ok=True)
            shutil.copyfile(source, dest)
            plan.append(row)
    (args.output/'test-plan.json').write_text(json.dumps(plan, ensure_ascii=False, indent=2)+'\n')
    metadata = {'generation': str(args.generation.resolve()), 'sampling_seed': 20261103,
                'sampling': 'Simple random sample of targets having generated Java in both arms; execution timing pilot only.',
                'eligible_paired_targets': len(eligible), 'sample_pairs': args.pairs,
                'java_files': len(plan), 'source_modified': False,
                'image': 'cloudarchlab/witup-evaluator@sha256:9cb51259cf4a2249d60d4e60aeead28b8d776fc5cb39d1e742757fc41ffdcdd7'}
    (args.output/'execution-plan.json').write_text(json.dumps(metadata, indent=2)+'\n')
    print(json.dumps(metadata))


if __name__ == '__main__':
    main()
