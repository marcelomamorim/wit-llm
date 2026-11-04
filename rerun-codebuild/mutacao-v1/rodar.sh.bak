#!/usr/bin/env bash
# Para cada site: muta a classe, roda o teste de cada braço no JDK limpo e no mutado.
# killed = passa no limpo E falha no mutado.
set -uo pipefail
TJ=/opt/test-jdk/bin
AE="--add-exports java.base/jdk.internal.org.objectweb.asm=ALL-UNNAMED --add-exports java.base/jdk.internal.org.objectweb.asm.tree=ALL-UNNAMED"
LIM="${LIMITE:-0}"; n=0
echo "site,arm,classe,metodo,limpo,mutado,veredito" > /m/resultado.csv
while IFS=, read -r cls cdot meth desc off hash pair; do
  [ "$cls" = "cls" ] && continue
  n=$((n+1)); [ "$LIM" -gt 0 ] && [ "$n" -gt "$LIM" ] && break
  p=$(echo "$cls" | tr '.' '/'); src=/m/orig/classes/$p.class
  [ -f "$src" ] || continue
  jm="$meth"; [ "$meth" = "<init>" ] && jm="${cdot##*.}"
  idx=$($TJ/javap -c -p -cp /m/orig/classes "$cdot" 2>/dev/null \
    | awk -v m="$jm" -v o="$off" '$0 ~ m"\\(" {inm=1} inm && /athrow/ {k++; split($1,a,":"); if (a[1]+0==o+0) {print k; exit}} inm && /^$/ {inm=0; k=0}')
  [ -z "$idx" ] && continue
  MUT=/m/mut/$hash-$off/java.base
  $TJ/java $AE -cp /m/classes Mutador "$src" "$meth" "$desc" "$idx" "$MUT/$p.class" >/dev/null 2>&1 || continue
  for arm in direct wit; do
    t=$(ls /m/testes/$arm/*_P$hash.java 2>/dev/null | head -1); [ -z "$t" ] && continue
    cn=$(basename "$t" .java); W=/m/work/$arm-$hash; mkdir -p "$W"
    # exporta os pacotes internos que o teste importa
    EX=$(grep -oE '^import +(sun|jdk\.internal|com\.sun)[A-Za-z0-9_.]*' "$t" 2>/dev/null \
         | sed 's/^import *//' | sed 's/\.[A-Z][A-Za-z0-9_]*$//' | sort -u \
         | awk '{printf "--add-exports java.base/%s=ALL-UNNAMED ", $1}')
    $TJ/javac $EX -nowarn -d "$W" "$t" >/dev/null 2>&1 || { echo "$hash-$off,$arm,$cdot,$meth,NAOCOMPILA,-,-" >> /m/resultado.csv; continue; }
    timeout 25 $TJ/java $EX -cp "$W" "$cn" >/dev/null 2>&1; a=$?
    timeout 25 $TJ/java $EX --patch-module java.base=$MUT -Xshare:off -cp "$W" "$cn" >/dev/null 2>&1; b=$?
    L=$([ $a -eq 0 ] && echo passa || echo falha)
    M=$([ $b -eq 0 ] && echo passa || echo falha)
    V=$([ "$L" = passa ] && { [ "$M" = falha ] && echo MATOU || echo sobreviveu; } || echo invalido)
    echo "$hash-$off,$arm,$cdot,$meth,$L,$M,$V" >> /m/resultado.csv
  done
  rm -rf "$MUT"
  [ $((n % 25)) -eq 0 ] && echo "  ... $n sites processados" >&2
done < /m/alvos.csv
echo "fim: $n sites" >&2
