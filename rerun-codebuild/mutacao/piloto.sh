#!/usr/bin/env bash
# Piloto do Eixo B: muta 20 sites e verifica se a classe carrega via --patch-module.
set -uo pipefail
TJ=/opt/test-jdk/bin
AE="--add-exports java.base/jdk.internal.org.objectweb.asm=ALL-UNNAMED --add-exports java.base/jdk.internal.org.objectweb.asm.tree=ALL-UNNAMED"
ok=0; miss=0; loadfail=0
while IFS=, read -r cls cdot meth dmeth desc off resto; do
  [ "$cls" = "class" ] && continue
  p=$(echo "$cls" | tr '.' '/')
  src=/m/orig/classes/$p.class
  [ -f "$src" ] || { echo "SEMCLASSE $cls"; miss=$((miss+1)); continue; }
  # offset -> índice do athrow, via javap (fonte de verdade)
  jm="$meth"; [ "$meth" = "<init>" ] && jm="${cdot##*.}"
  idx=$($TJ/javap -c -p -cp /m/orig/classes "$cdot" 2>/dev/null \
    | awk -v m="$jm" -v o="$off" '
        $0 ~ m"\\(" {inm=1}
        inm && /athrow/ {n++; split($1,a,":"); if (a[1]+0==o+0) {print n; exit}}
        inm && /^$/ {inm=0; n=0}')
  if [ -z "$idx" ]; then echo "SEMOFFSET $cdot.$meth off=$off"; miss=$((miss+1)); continue; fi
  out=/m/mut/$cls/java.base/$p.class
  r=$($TJ/java $AE -cp /m/classes Mutador "$src" "$meth" "$desc" "$idx" "$out" 2>&1)
  case "$r" in OK*) : ;; *) echo "MUTFAIL $cdot.$meth -> $r"; miss=$((miss+1)); continue;; esac
  # a JVM carrega a classe mutada?
  if $TJ/java --patch-module java.base=/m/mut/$cls/java.base -Xshare:off -version >/dev/null 2>&1; then
    echo "OK       $cdot.$meth (athrow#$idx)"; ok=$((ok+1))
  else
    echo "LOADFAIL $cdot.$meth"; loadfail=$((loadfail+1))
  fi
done < /m/piloto.csv
echo "---"; echo "mutados e carregam: $ok | sem offset/classe: $miss | não carregam: $loadfail"
