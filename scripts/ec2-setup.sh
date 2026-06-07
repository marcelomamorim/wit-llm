#!/usr/bin/env bash
# ec2-setup.sh — roda NA EC2 (Amazon Linux 2023)
#
# Uso:
#   ssh ec2-user@<ip> 'bash -s' < scripts/ec2-setup.sh

set -euo pipefail

log() { printf '\n[ec2-setup] %s\n' "$*"; }

# ── Docker ────────────────────────────────────────────────────────────────────
log "Instalando Docker..."
sudo dnf install -y docker git
sudo systemctl enable --now docker
sudo usermod -aG docker ec2-user
log "Docker instalado."

# ── Clonar repo ───────────────────────────────────────────────────────────────
log "Clonando wit-llm..."
cd /home/ec2-user
git clone https://github.com/marceloamorim/wit-llm.git wit-llm || \
  (cd wit-llm && git pull)
cd wit-llm

# ── Build imagem evaluator ────────────────────────────────────────────────────
log "Build witup-llm/evaluator (~60min)..."
sudo docker compose build evaluator

log "Setup concluído. Execute o script de sync do seu Mac para transferir os dados."
