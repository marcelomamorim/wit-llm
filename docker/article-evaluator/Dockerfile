# ── article-evaluator ─────────────────────────────────────────────────────────
# Imagem autocontida para avaliação do experimento de artigo (7 projetos Java).
#
# Conteúdo baked-in:
#   - witup CLI compilado do fonte
#   - Java 17 (compilação/execução) + Maven 3.9
#   - Python 3 + z3/sympy/psutil (WIT)
#   - WIT jar + run-wit.py (figshare CC BY 4.0)
#   - Baselines WIT extraídos do pacote figshare
#   - Scripts e pipelines do repositório público
#
# Projetos Java e baselines WIT gerados em runtime (volume mounted).
# Ver scripts/run-article-setup-docker.sh e scripts/run-article-evaluate-docker.sh.
#
# Uso:
#   docker compose run --rm article-setup    # clona projetos + gera WIT baselines
#   docker compose run --rm article-evaluate # executa JaCoCo + Surefire + PIT
# ──────────────────────────────────────────────────────────────────────────────

# ── Stage: Build witup CLI ─────────────────────────────────────────────────────
FROM golang:1.24-bookworm AS go-builder

ARG WITUP_REPO=https://github.com/marcelomamorim/witup-llm
ARG WITUP_COMMIT=main

RUN apt-get update && apt-get install -y --no-install-recommends git \
    && rm -rf /var/lib/apt/lists/*

RUN git clone "${WITUP_REPO}" /src \
    && cd /src \
    && git checkout "${WITUP_COMMIT}"

WORKDIR /src
RUN CGO_ENABLED=0 GOOS=linux go build -ldflags="-s -w" -o /witup ./cmd/witup/

# ── Stage: Download WIT replication package (figshare, CC BY 4.0) ─────────────
# Marcilio & Furia (2023). DOI: 10.6084/m9.figshare.22217014.v1
FROM alpine:3.20 AS wit-downloader

ARG WIT_PKG_URL=https://ndownloader.figshare.com/files/41786325

RUN apk add --no-cache wget unzip

RUN wget -q -O /tmp/wit-pkg.zip "${WIT_PKG_URL}" \
    && unzip -q /tmp/wit-pkg.zip -d /tmp/wit-extract \
    && rm /tmp/wit-pkg.zip

RUN PKG_ROOT=$(dirname "$(find /tmp/wit-extract -name 'wit.jar' | head -1)") \
    && PKG_ROOT=$(dirname "$PKG_ROOT") \
    && mkdir -p /opt/wit \
    && cp "${PKG_ROOT}/implementation/wit.jar"           /opt/wit/wit.jar \
    && cp "${PKG_ROOT}/implementation/run-wit.py"        /opt/wit/run-wit.py \
    && cp "${PKG_ROOT}/implementation/do-post-filter.py" /opt/wit/do-post-filter.py \
    # Copiar TODOS os dados de output disponíveis no pacote figshare
    && mkdir -p /wit-data \
    && cp -r "${PKG_ROOT}/data/output/." /wit-data/

# ── Stage: Clone repo (scripts, configs) ──────────────────────────────────────
FROM alpine:3.20 AS repo-source

ARG WITUP_REPO=https://github.com/marcelomamorim/witup-llm
ARG WITUP_COMMIT=main

RUN apk add --no-cache git

RUN git clone "${WITUP_REPO}" /repo \
    && cd /repo \
    && git checkout "${WITUP_COMMIT}"

# ── Final image ───────────────────────────────────────────────────────────────
FROM ubuntu:22.04

ENV DEBIAN_FRONTEND=noninteractive

# Java 17 + Maven + Python + ferramentas base
RUN apt-get update && apt-get install -y --no-install-recommends \
        openjdk-17-jdk-headless \
        maven \
        ant \
        python3 python3-pip python3-dev \
        cmake g++ gcc make \
        libgomp1 \
        curl wget ca-certificates unzip zip git \
        procps \
    && rm -rf /var/lib/apt/lists/*

# Detecta JAVA_HOME para amd64 e arm64
RUN JAVA_HOME_DIR=$(find /usr/lib/jvm -maxdepth 1 -name "java-17-openjdk-*" -type d | head -1) \
    && echo "JAVA_HOME=${JAVA_HOME_DIR}" >> /etc/environment \
    && echo "export JAVA_HOME=${JAVA_HOME_DIR}" >> /etc/profile.d/java.sh \
    && ln -sfn "${JAVA_HOME_DIR}" /usr/lib/jvm/java-17-openjdk
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk
ENV PATH="${JAVA_HOME}/bin:${PATH}"

# Dependências Python para WIT
RUN pip3 install --upgrade pip setuptools wheel \
    && pip3 install --no-cache-dir \
        "z3-solver==4.12.6.0" \
        sympy \
        psutil \
        openai

# witup CLI
COPY --from=go-builder /witup /usr/local/bin/witup
RUN chmod +x /usr/local/bin/witup

# WIT tools (figshare CC BY 4.0)
COPY --from=wit-downloader /opt/wit /opt/wit
ENV WIT_JAR=/opt/wit/wit.jar

# Baselines WIT do pacote figshare (todos os projetos disponíveis)
COPY --from=wit-downloader /wit-data /opt/wit-data

# Scripts e pipelines do repositório
COPY --from=repo-source /repo/scripts  /data/scripts
COPY --from=repo-source /repo/pipelines /data/pipelines
RUN chmod +x /data/scripts/*.sh 2>/dev/null || true

# Scripts específicos do article-evaluator
COPY run-article-setup-docker.sh    /data/scripts/run-article-setup-docker.sh
COPY run-article-evaluate-docker.sh /data/scripts/run-article-evaluate-docker.sh
COPY run-article-pit-docker.sh      /data/scripts/run-article-pit-docker.sh
RUN chmod +x /data/scripts/run-article-setup-docker.sh \
             /data/scripts/run-article-evaluate-docker.sh \
             /data/scripts/run-article-pit-docker.sh

# Maven local repo em volume para reuso entre execuções
ENV MAVEN_LOCAL_REPO=/data/generated/m2-repo

WORKDIR /data

# Smoke test
RUN java -version 2>&1 | grep "17" \
    && mvn --version \
    && witup ajuda \
    && python3 -c "import z3, sympy, psutil; print('python deps ok')"

CMD ["/bin/bash"]
