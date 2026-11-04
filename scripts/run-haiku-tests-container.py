#!/usr/bin/env python3
"""Run original Java in the historical evaluator image; no source repairs.

/in: immutable prepared subset. /out: new writable output directory.
Separate jtreg and per-JVM JCov passes preserve all logs and raw coverage XMLs.
"""
import collections
import hashlib
import importlib.util
import json
import os
from pathlib import Path
import shutil
import subprocess
import time


def main():
    root, out = Path('/in'), Path('/out')
    suite_root = Path('/opt/openjdk-src/test/jdk')
    suite = suite_root/'witup/azure-haiku'
    jdk = Path('/opt/test-jdk')
    jtreg = '/opt/jtreg/bin/jtreg'
    jcov = '/opt/jcov/JCOV_BUILD/jcov_3.0/jcov.jar'
    if (out/'test-plan.json').exists():
        raise ValueError('Output already contains an execution; choose a new directory')
    suite.mkdir(parents=True, exist_ok=True)
    out.mkdir(exist_ok=True)
    version = subprocess.run([str(jdk/'bin/java'), '-version'], capture_output=True, text=True, check=True)
    if '"11' not in version.stderr:
        raise ValueError('Historical JDK 11 required')
    environment = {'jdk_version': version.stderr, 'jdk_release': (jdk/'release').read_text(),
                   'jtreg': subprocess.run([jtreg, '-version'], capture_output=True, text=True).stdout,
                   'assertions': True, 'normal_concurrency': 2, 'coverage_concurrency': 1,
                   'timeout_factor': 1, 'source_repairs': False, 'network': 'disabled by container launcher',
                   'coverage_intrinsic_excludes': ['java/lang/Object', 'java/lang/Class', 'java/lang/System',
                       'java/lang/Thread', 'java/lang/Double', 'java/lang/Float',
                       'java/lang/reflect/Array', 'java/util/zip/CRC32'],
                   'coverage_strategy': 'One XML per JVM; preserve all raw files. No skip-on-error merge.'}
    (out/'environment.json').write_text(json.dumps(environment, indent=2))
    plan = json.loads((root/'test-plan.json').read_text())
    for row in plan:
        source = root/row['exported_path']
        if not source.resolve().is_relative_to(root.resolve()):
            raise ValueError('Unsafe input')
        assert hashlib.sha256(source.read_bytes()).hexdigest() == row['sha256']
        relative = Path(row['relative_path']).relative_to('test/jdk')
        target = suite/row['arm']/row['pair_id']/relative
        assert target.resolve().is_relative_to(suite.resolve())
        target.parent.mkdir(parents=True, exist_ok=True)
        shutil.copyfile(source, target)
        row['suite_relative'] = str(target.relative_to(suite_root))
    (out/'test-plan.json').write_text(json.dumps(plan, indent=2))
    spec = importlib.util.spec_from_file_location('outcomes', '/scripts/analyze-four-model-execution.py')
    outcomes = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(outcomes)
    results, timings = [], []
    for arm in ('direct-tests', 'wit-context'):
        for stage in ('jtreg', 'jcov'):
            stage_out = out/arm/stage
            stage_out.mkdir(parents=True)
            target_jdk = jdk
            if stage == 'jcov':
                wrapper = Path('/tmp')/('wrapper-'+arm)
                shutil.copytree(jdk, wrapper, symlinks=True)
                raw = stage_out/'raw'
                raw.mkdir()
                excludes = ''.join(',exclude='+c for c in environment['coverage_intrinsic_excludes'])
                agent = f'-javaagent:{jcov}=file={raw}/jcov-$$.xml,type=branch,merge=overwrite,include=java/,include=javax/,include=sun/,include=com/sun/,include=jdk/,native=off{excludes}'
                java = wrapper/'bin/java'
                # Replace only the copied runtime launcher, never the image's original.
                java.unlink()
                java.write_text('#!/bin/bash\nfor arg in "$@"; do\n'
                                ' if [[ "$arg" == "com.sun.javatest.regtest.agent.MainWrapper" ]]; then\n'
                                f'  {jdk}/bin/java {agent} "$@"\n  code=$?\n'
                                f'  if [[ -f {raw}/jcov-$$.xml ]]; then gzip {raw}/jcov-$$.xml; fi\n'
                                '  exit "$code"\n fi\ndone\n'
                                f'exec {jdk}/bin/java "$@"\n')
                java.chmod(0o755)
                target_jdk = wrapper
            command = [jtreg, '-jdk:'+str(target_jdk), '-w:'+str(stage_out/'work'),
                       '-r:'+str(stage_out/'report'), '-othervm',
                       '-conc:'+('2' if stage == 'jtreg' else '1'), '-timeout:1', '-va',
                       '-javaoption:-ea', '-javacoption:-encoding', '-javacoption:UTF-8', str(suite/arm)]
            started = time.time()
            with (stage_out/'jtreg.log').open('w') as log:
                try:
                    result = subprocess.run(command, stdout=log, stderr=subprocess.STDOUT, timeout=1800)
                    code = result.returncode
                except subprocess.TimeoutExpired:
                    code = 124
            timing = {'arm': arm, 'stage': stage, 'seconds': time.time()-started, 'exit_code': code}
            timings.append(timing)
            (stage_out/'execution.json').write_text(json.dumps(timing, indent=2))
            print(json.dumps(timing), flush=True)
            for row in plan:
                if row['arm'] != arm:
                    continue
                jtr = stage_out/'work'/Path(row['suite_relative']).with_suffix('.jtr')
                results.append({**row, 'stage': stage, **outcomes.analyze_jtr(jtr)})
            if stage == 'jcov':
                shutil.rmtree(target_jdk)
    summary = {'java_files': len(plan), 'source_repaired': False, 'timings': timings,
               'by_arm_stage': {}, 'raw_coverage_files': {arm: len(list((out/arm/'jcov/raw').glob('*.xml*')))
                                                        for arm in ('direct-tests', 'wit-context')}}
    for arm in ('direct-tests', 'wit-context'):
        for stage in ('jtreg', 'jcov'):
            rows = [r for r in results if r['arm'] == arm and r['stage'] == stage]
            summary['by_arm_stage'][arm+'/'+stage] = {
                'files': len(rows), 'compiled': sum(r['compiled'] for r in rows),
                'outcomes': dict(collections.Counter(r['outcome'] for r in rows))}
    (out/'test-results.json').write_text(json.dumps(results, ensure_ascii=False, indent=2))
    (out/'execution-summary.json').write_text(json.dumps(summary, indent=2))
    print(json.dumps(summary), flush=True)


if __name__ == '__main__':
    main()
