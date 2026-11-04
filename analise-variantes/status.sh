#!/usr/bin/env bash
# Placar do run. Compatível com bash 3.2 (macOS).
cd "$(dirname "$0")"
RUN_STAMP=$(cat .run_stamp)
ids=$(awk '$3!=""{print $3}' builds.txt)
n=$(echo "$ids" | grep -c . )

echo "=== builds ($n lançados de 48) ==="
# batch-get-builds aceita até 100 ids por chamada
echo "$ids" | xargs aws codebuild batch-get-builds --ids 2>/dev/null \
  --query 'builds[].buildStatus' --output text \
  | tr '\t' '\n' | sort | uniq -c | sed 's/^/  /'

echo "=== artefatos no S3 ==="
ls_out=$(aws s3 ls "s3://witup-jcov-results/$RUN_STAMP/paired-results/" --recursive 2>/dev/null)
printf "  %-22s %s / 48\n" "jtr.tgz"        "$(echo "$ls_out" | grep -c 'jtr\.tgz')"
printf "  %-22s %s / 48\n" "jcov-result.xml" "$(echo "$ls_out" | grep -c 'jcov-result\.xml')"
for v in direct-tests wit-context; do
  printf "  %-22s %s / 24\n" "$v" "$(echo "$ls_out" | grep "/$v/" | grep -c 'jtr\.tgz')"
done

d=$(tail -1 $(ls -t driver-*.log 2>/dev/null | head -1) 2>/dev/null)
[ -n "$d" ] && echo "=== driver ===" && echo "  $d"
