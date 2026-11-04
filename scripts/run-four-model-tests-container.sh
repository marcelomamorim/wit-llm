#!/usr/bin/env bash
# Executado dentro da imagem evaluator, com /in somente leitura e /out gravável.
# Não corrige, renomeia nem regenera os testes exportados.
set -euo pipefail
TJ=/opt/test-jdk
JT=/opt/jtreg/bin/jtreg
SUITE=/opt/openjdk-src/test/jdk/witup/four-models
[[ -x "$TJ/bin/java" && -x "$TJ/bin/javac" && -x "$JT" ]]
[[ -f /opt/openjdk-src/test/jdk/TEST.ROOT ]]
mkdir -p "$SUITE" /out
[[ ! -e /out/environment.txt ]] || { echo 'Diretório de execução já utilizado; escolha outro /out.' >&2; exit 2; }
{
  echo '=== JDK sob teste ==='
  "$TJ/bin/java" -version 2>&1
  cat "$TJ/release"
  echo '=== Build de origem ==='
  cat /opt/build-version.txt 2>/dev/null || true
  echo '=== Commit do fonte na imagem ==='
  git -C /opt/openjdk-src rev-parse HEAD 2>/dev/null || true
  echo '=== jtreg ==='
  "$JT" -version 2>&1
  echo '=== Parâmetros ==='
  echo 'UTF-8; assertions habilitadas; concorrência 2; timeout factor 1; fontes originais; rede desabilitada'
} > /out/environment.txt
python3 - <<'PY'
import csv,hashlib,json,pathlib,shutil
root=pathlib.Path('/in');dest=pathlib.Path('/opt/openjdk-src/test/jdk/witup/four-models')
plan=[]
for model in ['gpt_reasoning','gpt_standard','gemini','sonnet']:
 for row in csv.DictReader((root/model/'exported-tests.csv').open()):
  source=root/model/row['exported_path']
  assert source.resolve().is_relative_to((root/model/'generated-tests').resolve())
  assert hashlib.sha256(source.read_bytes()).hexdigest()==row['sha256']
  relative=pathlib.Path(row['exported_path']).relative_to('generated-tests',row['arm'])
  target=dest/model/row['arm']/relative
  assert target.resolve().is_relative_to(dest.resolve())
  target.parent.mkdir(parents=True,exist_ok=True);shutil.copyfile(source,target)
  assert hashlib.sha256(target.read_bytes()).hexdigest()==row['sha256']
  row['model_key']=model
  row['suite_relative']=str(target.relative_to(pathlib.Path('/opt/openjdk-src/test/jdk')))
  plan.append(row)
assert len(plan)==116, f'Esperados os 116 Java deste piloto, encontrados {len(plan)}'
pathlib.Path('/out/test-plan.json').write_text(json.dumps(plan,indent=2))
print('116 arquivos originais conferidos e posicionados no jtreg')
PY
for model in gpt_reasoning gpt_standard gemini sonnet; do
  for arm in direct-tests wit-context; do
    tests="$SUITE/$model/$arm"
    [[ -d "$tests" ]] || continue
    result="/out/$model/$arm"
    mkdir -p "$result/work" "$result/report"
    echo "[$model/$arm] iniciando ($(find "$tests" -name '*.java' | wc -l) Java)"
    set +e
    timeout 1200 "$JT" -jdk:"$TJ" -w:"$result/work" -r:"$result/report" \
      -othervm -conc:2 -timeout:1 -va \
      -javaoption:-ea -javacoption:-encoding -javacoption:UTF-8 \
      "$tests" > "$result/jtreg.log" 2>&1
    status=$?
    set -e
    echo "$status" > "$result/exit-code.txt"
    tail -5 "$result/jtreg.log"
    echo "[$model/$arm] concluído exit=$status"
  done
done
echo 'Execução jtreg concluída; os .jtr preservam os diagnósticos individuais.'
