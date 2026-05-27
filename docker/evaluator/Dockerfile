FROM witup-llm/jdk-builder:latest AS jdk-base

# ── Stage: Build witup CLI from source ────────────────────────────────────────
# Compila o binário a partir do repositório público — não depende de nenhum
# arquivo local. A versão exata é controlada por WITUP_COMMIT.
FROM golang:1.24-bookworm AS go-builder

ARG WITUP_REPO=https://github.com/marcelomamorim/wit-llm
ARG WITUP_COMMIT=main

RUN apt-get update && apt-get install -y --no-install-recommends git \
    && rm -rf /var/lib/apt/lists/*

RUN git clone "${WITUP_REPO}" /src \
    && cd /src \
    && git checkout "${WITUP_COMMIT}"

WORKDIR /src
# CGO_ENABLED=0 → binário estático, sem dependência de libc da imagem final
RUN CGO_ENABLED=0 GOOS=linux go build -ldflags="-s -w" -o /witup ./cmd/witup/

# ── Stage: Build JCov 3.0 from source ────────────────────────────────────────
# Tag jcov3.0-b07 = commit 83b8e8d — versão exata usada no experimento.
# Dependências: ASM 7.0 (Maven Central) + ant + openjdk-11.
# Nota: ${java.home}/lib/tools.jar não existe no JDK 11+ — ant ignora
# entradas ausentes no classpath, não afeta a compilação dos jars de cobertura.
FROM ubuntu:22.04 AS jcov-builder

RUN apt-get update && apt-get install -y --no-install-recommends \
        openjdk-11-jdk-headless \
        ant \
        wget \
        git \
        ca-certificates \
    && rm -rf /var/lib/apt/lists/*

# ASM 7.0 jars (dependência do JCov 3.0, checksums do build.properties)
RUN mkdir -p /tmp/asm \
    && wget -q -O /tmp/asm/asm.jar \
       https://repo1.maven.org/maven2/org/ow2/asm/asm/7.0/asm-7.0.jar \
    && wget -q -O /tmp/asm/asm-tree.jar \
       https://repo1.maven.org/maven2/org/ow2/asm/asm-tree/7.0/asm-tree-7.0.jar \
    && wget -q -O /tmp/asm/asm-util.jar \
       https://repo1.maven.org/maven2/org/ow2/asm/asm-util/7.0/asm-util-7.0.jar

ARG JCOV_TAG=jcov3.0-b07

RUN git clone --branch "${JCOV_TAG}" --depth 1 \
    https://github.com/openjdk/jcov /opt/jcov-src

WORKDIR /opt/jcov-src/build

# Compilar target padrão "build".
# build.properties define javatestjar=javatest.jar por padrão; sem o jar real,
# build-jt.observer falharia. Passamos javatestjar= (vazio) para que a condição
# javatest.present não seja setada e build-jt.observer seja pulado via if="javatest.present".
RUN ant \
    -Djavatestjar= \
    -Dasm.jar=/tmp/asm/asm.jar \
    -Dasm.tree.jar=/tmp/asm/asm-tree.jar \
    -Dasm.util.jar=/tmp/asm/asm-util.jar

# Verificar que os três jars necessários foram gerados.
# basedir=".." no build.xml → output em /opt/jcov-src/JCOV_BUILD/ (não em build/)
RUN ls /opt/jcov-src/JCOV_BUILD/jcov_3.0/jcov.jar \
       /opt/jcov-src/JCOV_BUILD/jcov_3.0/jcov_file_saver.jar \
       /opt/jcov-src/JCOV_BUILD/jcov_3.0/jcov_network_saver.jar

# ── Stage: Clone repo (scripts, configs, código-fonte) ────────────────────────
# O conteúdo rastreado no git (scripts/, pipelines/, etc.) vem diretamente
# do repositório público — não monta nem lê nada da máquina do pesquisador.
FROM alpine:3.20 AS repo-source

ARG WITUP_REPO=https://github.com/marcelomamorim/wit-llm
ARG WITUP_COMMIT=main

RUN apk add --no-cache git

RUN git clone "${WITUP_REPO}" /repo \
    && cd /repo \
    && git checkout "${WITUP_COMMIT}"

# ── Stage: Download WIT replication package (figshare, CC BY 4.0) ─────────────
# Marcilio & Furia (2023). "Replication package for Lightweight Precise Automatic
# Extraction of Exceptional Preconditions in Java Methods."
# DOI: 10.6084/m9.figshare.22217014.v1 — Licença: CC BY 4.0
# Download direto (~60 MB comprimido). Nenhum arquivo local necessário.
FROM alpine:3.20 AS wit-downloader

ARG WIT_PKG_URL=https://ndownloader.figshare.com/files/41786325

RUN apk add --no-cache wget unzip

RUN wget -q -O /tmp/wit-pkg.zip "${WIT_PKG_URL}" \
    && unzip -q /tmp/wit-pkg.zip -d /tmp/wit-extract \
    && rm /tmp/wit-pkg.zip

# Localizar a raiz do pacote extraído (pode ter subdiretório witup-replication-package/ ou não)
RUN PKG_ROOT=$(dirname "$(find /tmp/wit-extract -name 'wit.jar' | head -1)") \
    && PKG_ROOT=$(dirname "$PKG_ROOT") \
    && echo "PKG_ROOT=${PKG_ROOT}" \
    # Ferramentas WIT: wit.jar, run-wit.py, do-post-filter.py
    && mkdir -p /opt/wit \
    && cp "${PKG_ROOT}/implementation/wit.jar"           /opt/wit/wit.jar \
    && cp "${PKG_ROOT}/implementation/run-wit.py"        /opt/wit/run-wit.py \
    && cp "${PKG_ROOT}/implementation/do-post-filter.py" /opt/wit/do-post-filter.py \
    # Dados WIT do JDK da75f3c4 (wit_filtered.json + auxiliares)
    && mkdir -p /wit-data/jdk \
    && cp -r "${PKG_ROOT}/data/output/jdk/." /wit-data/jdk/

# ── Evaluator image ────────────────────────────────────────────────────────────
# Dual-JDK setup:
#   /opt/test-jdk  → JDK 11+28 (da75f3c4, mesma versão do artigo WIT do Diego)
#                    usado via -jdk: flag no jtreg (compilação e execução dos testes)
#   openjdk-21     → JDK 21 do sistema, usado para RODAR jtreg/JCov (requerem ≥17)
# JCov 3.0 instrumenta bytecode class-v55 (JDK 11) sem problema.
FROM ubuntu:22.04

ENV DEBIAN_FRONTEND=noninteractive

# Dependências base + Python + cmake/g++ para compilar z3-solver 4.12.x no ARM64
RUN apt-get update && apt-get install -y --no-install-recommends \
        openjdk-21-jdk-headless \
        python3 python3-pip python3-dev \
        cmake g++ gcc make \
        libgomp1 \
        curl wget ca-certificates unzip zip git \
        procps \
    && rm -rf /var/lib/apt/lists/*

# Copiar JDK 11+28 (da75f3c4) construído a partir do jdk-builder
# NÃO adicionado ao PATH — jtreg usa -jdk: flag; sistema usa openjdk-21
COPY --from=jdk-base /opt/test-jdk      /opt/test-jdk
COPY --from=jdk-base /opt/openjdk-src   /opt/openjdk-src
COPY --from=jdk-base /opt/jdk-images    /opt/jdk-images
COPY --from=jdk-base /opt/build-version.txt /opt/build-version.txt

ENV TEST_JDK=/opt/test-jdk
ENV JDK_ROOT=/opt/openjdk-src
# PATH mantém openjdk-21 como java padrão (para jtreg/JCov runtime)

# ── Scripts, configs e código-fonte do repositório público ────────────────────
# Atenção: resources/ está em .gitignore — não faz parte do clone.
# Os recursos (JCov, WIT, wit_filtered.json) são copiados abaixo pelo build context.
COPY --from=repo-source /repo /data
RUN chmod +x /data/scripts/*.sh 2>/dev/null || true

# ── Binário witup compilado a partir do fonte ──────────────────────────────────
# Substitui o COPY bin/witup-linux — agora compilado no estágio go-builder.
COPY --from=go-builder /witup /usr/local/bin/witup
RUN chmod +x /usr/local/bin/witup

# ── jtreg 7.3.1+1 ─────────────────────────────────────────────────────────────
# Binários via builds.shipilev.net (GitHub releases não publica tar.gz pré-compilado)
ARG JTREG_VERSION=7.3.1%2B1
ARG JTREG_URL=https://builds.shipilev.net/jtreg/jtreg-${JTREG_VERSION}.zip

RUN mkdir -p /opt/jtreg \
    && wget -q -O /tmp/jtreg.zip "${JTREG_URL}" \
    && unzip -q /tmp/jtreg.zip -d /tmp/jtreg-extract \
    && mv /tmp/jtreg-extract/jtreg/* /opt/jtreg/ \
    && rm -rf /tmp/jtreg.zip /tmp/jtreg-extract \
    && /opt/jtreg/bin/jtreg -version

ENV JTREG_HOME=/opt/jtreg

# ── JCov 3.0 (compilado do fonte no stage jcov-builder) ───────────────────────
# Nenhum arquivo local necessário — compilado de github.com/openjdk/jcov@jcov3.0-b07
COPY --from=jcov-builder /opt/jcov-src/JCOV_BUILD /opt/jcov/JCOV_BUILD

ENV JCOV_HOME=/opt/jcov/JCOV_BUILD/jcov_3.0

# ── Dependências Python (WIT + análise de smells) ─────────────────────────────
# z3-solver 4.12.6.0: última versão que não exige C++20 <format> (GCC 11 OK)
RUN pip3 install --upgrade pip setuptools wheel \
    && pip3 install --no-cache-dir \
        "z3-solver==4.12.6.0" \
        sympy \
        psutil \
        openai

# ── Ferramentas e dados WIT (do stage wit-downloader) ─────────────────────────
# Extraídos do replication package de Marcilio & Furia (figshare, CC BY 4.0).
# Nenhum arquivo local necessário — tudo baixado durante o build.
COPY --from=wit-downloader /opt/wit /opt/wit

# Dados WIT do JDK da75f3c4 → caminho esperado pelo runtime config
COPY --from=wit-downloader /wit-data/jdk \
     /data/resources/wit-replication-package/data/output/jdk

ENV WIT_JAR=/opt/wit/wit.jar

# ── Diretório de trabalho padrão ───────────────────────────────────────────────
# /data contém o repositório clonado do GitHub (scripts/, pipelines/, etc.)
# /data/generated/ é montado em runtime como volume → persiste resultados do host
WORKDIR /data

# Smoke test: java de sistema = 21 (runtime), test-jdk = 11 (testes WIT)
RUN java -version 2>&1 | grep "21" \
    && /opt/test-jdk/bin/java -version 2>&1 | grep "11" \
    && witup ajuda \
    && python3 -c "import z3; print('z3 ok:', z3.get_version_string())" \
    && python3 -c "import sympy, psutil; print('sympy/psutil ok')" \
    && python3 -c "import openai; print('openai ok:', openai.__version__)"

CMD ["/bin/bash"]
