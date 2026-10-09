#!/usr/bin/env bash
# Espera os 48 XML do experimento do artigo e dispara o remerge estrito.
# Substitui o encadeamento anterior, que tinha timeout de 1h — curto demais
# para um download de ~700 MB em arquivos de 14 MB.
set -uo pipefail
cd "$(dirname "$0")"
LOG=encadeia-original.log
diz(){ echo "[$(date '+%d/%m %H:%M')] $*" >> "$LOG"; }
diz "aguardando os 48 XML"
ini=$(date +%s)
while :; do
  n=$(find merge-original/direct-tests merge-original/wit-context -name '*.xml' 2>/dev/null | wc -l | tr -d ' ')
  [ "$n" -ge 48 ] && { diz "48/48 prontos"; break; }
  # desiste só se o download morreu E parou de progredir
  if ! pgrep -f 'aws s3 cp.*paired-20260706' >/dev/null && [ $(( $(date +%s)-ini )) -gt 600 ]; then
    diz "download parado em $n/48 — abortando"; exit 1
  fi
  sleep 60
done
diz "disparando remerge estrito"
bash ./remerge-original.sh
diz "remerge retornou $?"
