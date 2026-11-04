#!/usr/bin/env bash
# codebuild-setup.sh — roda NO MAC
#
# Prepara tudo para rodar o jcov-baseline no AWS CodeBuild:
#   1. Cria repositório ECR e faz push da imagem (6.65GB)
#   2. Cria bucket S3 e faz upload dos dados do run
#   3. Cria projeto CodeBuild
#
# Uso:
#   AWS_ACCOUNT_ID=123456789012 \
#   AWS_REGION=us-east-1 \
#   RUN_STAMP=pilot-20260607T041241Z \
#     ./scripts/codebuild-setup.sh
#
# Pré-requisitos:
#   - AWS CLI configurado (aws configure)
#   - Docker rodando localmente
#   - Imagem witup-llm/evaluator:latest presente localmente

set -euo pipefail

AWS_ACCOUNT_ID="${AWS_ACCOUNT_ID:-}"
AWS_REGION="${AWS_REGION:-us-east-1}"
RUN_STAMP="${RUN_STAMP:-}"
EXPERIMENT_DIR="${EXPERIMENT_DIR:-jdk-pilot}"
JTREG_CONCURRENCY="${JTREG_CONCURRENCY:-48}"

[[ -n "${AWS_ACCOUNT_ID}" ]] || AWS_ACCOUNT_ID=$(aws sts get-caller-identity --query Account --output text)
[[ -n "${RUN_STAMP}" ]]      || { echo "Erro: RUN_STAMP obrigatório"; exit 1; }

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
RUN_DIR="${ROOT_DIR}/generated/experiments/${EXPERIMENT_DIR}/${RUN_STAMP}"

ECR_REPO="witup-llm/evaluator"
ECR_URI="${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${ECR_REPO}"
S3_BUCKET="witup-jcov-${AWS_ACCOUNT_ID}"
CODEBUILD_PROJECT="witup-jcov-baseline"

log()  { printf '\n[codebuild-setup] %s\n' "$*"; }
step() { printf '\n[codebuild-setup] ══ %s ══\n' "$*"; }

# ── Passo 1: ECR ──────────────────────────────────────────────────────────────
step "1/3 — Push imagem para ECR (~20–90min)"

log "Criando repositório ECR ${ECR_REPO}..."
aws ecr create-repository \
  --repository-name "${ECR_REPO}" \
  --region "${AWS_REGION}" 2>/dev/null || log "Repositório já existe."

log "Login no ECR..."
aws ecr get-login-password --region "${AWS_REGION}" | \
  docker login --username AWS --password-stdin \
  "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com"

log "Tag + push (6.65GB — aguarde)..."
docker tag witup-llm/evaluator:latest "${ECR_URI}:latest"
docker push "${ECR_URI}:latest"
log "Imagem disponível em: ${ECR_URI}:latest"

# ── Passo 2: S3 ───────────────────────────────────────────────────────────────
step "2/3 — Upload dados do run para S3"

log "Criando bucket S3 ${S3_BUCKET}..."
if [[ "${AWS_REGION}" == "us-east-1" ]]; then
  aws s3 mb "s3://${S3_BUCKET}" --region "${AWS_REGION}" 2>/dev/null || log "Bucket já existe."
else
  aws s3 mb "s3://${S3_BUCKET}" \
    --region "${AWS_REGION}" \
    --create-bucket-configuration LocationConstraint="${AWS_REGION}" 2>/dev/null || log "Bucket já existe."
fi

log "Fazendo upload de variants/ e jcov-baseline/work/ (testes já executados)..."
aws s3 sync \
  "${RUN_DIR}/variants/baseline" \
  "s3://${S3_BUCKET}/${EXPERIMENT_DIR}/${RUN_STAMP}/variants/baseline" \
  --exclude "jtreg-work*" --exclude "jtreg-report*" \
  --only-show-errors

aws s3 sync \
  "${RUN_DIR}/jcov-baseline/work" \
  "s3://${S3_BUCKET}/${EXPERIMENT_DIR}/${RUN_STAMP}/jcov-baseline/work" \
  --only-show-errors

log "Upload concluído."

# ── Passo 3: CodeBuild ────────────────────────────────────────────────────────
step "3/3 — Criar projeto CodeBuild"

# Criar role IAM para CodeBuild (se não existir)
ROLE_NAME="CodeBuildWitupRole"
ROLE_ARN=$(aws iam get-role --role-name "${ROLE_NAME}" \
  --query Role.Arn --output text 2>/dev/null || echo "")

if [[ -z "${ROLE_ARN}" ]]; then
  log "Criando IAM role ${ROLE_NAME}..."
  ROLE_ARN=$(aws iam create-role \
    --role-name "${ROLE_NAME}" \
    --assume-role-policy-document '{
      "Version":"2012-10-17",
      "Statement":[{
        "Effect":"Allow",
        "Principal":{"Service":"codebuild.amazonaws.com"},
        "Action":"sts:AssumeRole"
      }]
    }' --query Role.Arn --output text)

  aws iam attach-role-policy --role-name "${ROLE_NAME}" \
    --policy-arn arn:aws:iam::aws:policy/AmazonEC2ContainerRegistryReadOnly
  aws iam attach-role-policy --role-name "${ROLE_NAME}" \
    --policy-arn arn:aws:iam::aws:policy/AmazonS3FullAccess
  aws iam attach-role-policy --role-name "${ROLE_NAME}" \
    --policy-arn arn:aws:iam::aws:policy/CloudWatchLogsFullAccess
  log "Role criada: ${ROLE_ARN}"
fi

# Gerar buildspec.yml
BUILDSPEC_PATH="${ROOT_DIR}/buildspec.yml"
cat > "${BUILDSPEC_PATH}" << BUILDSPEC
version: 0.2

env:
  variables:
    EXPERIMENT_DIR: "${EXPERIMENT_DIR}"
    RUN_STAMP: "${RUN_STAMP}"
    JTREG_CONCURRENCY: "${JTREG_CONCURRENCY}"
    S3_BUCKET: "${S3_BUCKET}"

phases:
  pre_build:
    commands:
      - echo "Baixando dados do run do S3..."
      - mkdir -p /data/generated/experiments/\${EXPERIMENT_DIR}/\${RUN_STAMP}
      - aws s3 sync s3://\${S3_BUCKET}/\${EXPERIMENT_DIR}/\${RUN_STAMP}/variants/baseline
          /data/generated/experiments/\${EXPERIMENT_DIR}/\${RUN_STAMP}/variants/baseline
      - aws s3 sync s3://\${S3_BUCKET}/\${EXPERIMENT_DIR}/\${RUN_STAMP}/jcov-baseline/work
          /data/generated/experiments/\${EXPERIMENT_DIR}/\${RUN_STAMP}/jcov-baseline/work
      - echo "Dados prontos."

  build:
    commands:
      - echo "Iniciando jcov-baseline com concorrência \${JTREG_CONCURRENCY}..."
      - |
        docker run --rm \
          -v /data/generated:/data/generated \
          -e EXPERIMENT_DIR=\${EXPERIMENT_DIR} \
          -e RUN_STAMP=\${RUN_STAMP} \
          -e JTREG_CONCURRENCY=\${JTREG_CONCURRENCY} \
          ${ECR_URI}:latest \
          bash /data/scripts/run-jcov-baseline-docker.sh

  post_build:
    commands:
      - echo "Fazendo upload dos resultados para S3..."
      - aws s3 sync
          /data/generated/experiments/\${EXPERIMENT_DIR}/\${RUN_STAMP}/jcov-baseline
          s3://\${S3_BUCKET}/\${EXPERIMENT_DIR}/\${RUN_STAMP}/jcov-baseline
      - echo "Resultados disponíveis em s3://\${S3_BUCKET}/\${EXPERIMENT_DIR}/\${RUN_STAMP}/jcov-baseline"

artifacts:
  files:
    - "**/*"
  base-directory: /tmp/artifacts
  discard-paths: no
BUILDSPEC

log "buildspec.yml gerado em ${BUILDSPEC_PATH}"

# Criar projeto CodeBuild
log "Criando projeto CodeBuild ${CODEBUILD_PROJECT}..."
aws codebuild create-project \
  --name "${CODEBUILD_PROJECT}" \
  --region "${AWS_REGION}" \
  --source "{
    \"type\": \"NO_SOURCE\",
    \"buildspec\": $(cat "${BUILDSPEC_PATH}" | python3 -c 'import json,sys; print(json.dumps(sys.stdin.read()))')
  }" \
  --artifacts '{"type":"NO_ARTIFACTS"}' \
  --environment "{
    \"type\": \"LINUX_CONTAINER\",
    \"image\": \"${ECR_URI}:latest\",
    \"computeType\": \"BUILD_GENERAL1_2XLARGE\",
    \"privilegedMode\": true,
    \"imagePullCredentialsType\": \"SERVICE_ROLE\"
  }" \
  --service-role "${ROLE_ARN}" \
  --timeout-in-minutes 480 2>/dev/null || \
aws codebuild update-project \
  --name "${CODEBUILD_PROJECT}" \
  --region "${AWS_REGION}" \
  --environment "{
    \"type\": \"LINUX_CONTAINER\",
    \"image\": \"${ECR_URI}:latest\",
    \"computeType\": \"BUILD_GENERAL1_2XLARGE\",
    \"privilegedMode\": true,
    \"imagePullCredentialsType\": \"SERVICE_ROLE\"
  }" \
  --service-role "${ROLE_ARN}"

log "Projeto CodeBuild criado: ${CODEBUILD_PROJECT}"

# Disparar build
log "Disparando build..."
BUILD_ID=$(aws codebuild start-build \
  --project-name "${CODEBUILD_PROJECT}" \
  --region "${AWS_REGION}" \
  --query 'build.id' --output text)

log "Build iniciado: ${BUILD_ID}"
log ""
log "Acompanhe no console:"
log "  https://${AWS_REGION}.console.aws.amazon.com/codesuite/codebuild/projects/${CODEBUILD_PROJECT}/build/${BUILD_ID}/log"
log ""
log "Quando terminar, baixe os resultados:"
log "  RUN_STAMP=${RUN_STAMP} S3_BUCKET=${S3_BUCKET} AWS_REGION=${AWS_REGION} \\"
log "    ./scripts/codebuild-download-results.sh"
