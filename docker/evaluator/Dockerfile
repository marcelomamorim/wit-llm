FROM witup-llm/jdk-builder:latest AS jdk-base

# ── Evaluator image ────────────────────────────────────────────────────────────
# Adiciona sobre o jdk-builder: jtreg, JCov, binário witup, ferramentas WIT e
# dependências Python para execução de testes e análise de cobertura.
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

# Copiar JDK 21 construído a partir do jdk-builder
COPY --from=jdk-base /opt/test-jdk      /opt/test-jdk
COPY --from=jdk-base /opt/openjdk-src   /opt/openjdk-src
COPY --from=jdk-base /opt/jdk-images    /opt/jdk-images
COPY --from=jdk-base /opt/build-version.txt /opt/build-version.txt

ENV TEST_JDK=/opt/test-jdk
ENV JDK_ROOT=/opt/openjdk-src
ENV PATH="/opt/test-jdk/bin:${PATH}"

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

# ── JCov 3.0 — jars pré-compilados do fonte (jcov3.0-b07, source/target 8) ────
# GitHub releases do openjdk/jcov não publica binários; compilado localmente e
# bundlado em resources/jcov/JCOV_BUILD/jcov_3.0/
COPY resources/jcov/JCOV_BUILD /opt/jcov/JCOV_BUILD

ENV JCOV_HOME=/opt/jcov/JCOV_BUILD/jcov_3.0

# ── Dependências Python (WIT) ──────────────────────────────────────────────────
# z3-solver 4.12.6.0: última versão que não exige C++20 <format> (GCC 11 OK)
RUN pip3 install --upgrade pip setuptools wheel \
    && pip3 install --no-cache-dir \
        "z3-solver==4.12.6.0" \
        sympy \
        psutil

# ── Ferramentas WIT ────────────────────────────────────────────────────────────
RUN mkdir -p /opt/wit
COPY resources/wit-replication-package/implementation/wit.jar        /opt/wit/wit.jar
COPY resources/wit-replication-package/implementation/run-wit.py     /opt/wit/run-wit.py
COPY resources/wit-replication-package/implementation/do-post-filter.py /opt/wit/do-post-filter.py

ENV WIT_JAR=/opt/wit/wit.jar

# ── Binário witup ──────────────────────────────────────────────────────────────
# Binário Linux arm64 (compilado com GOOS=linux GOARCH=arm64)
COPY bin/witup-linux /usr/local/bin/witup
RUN chmod +x /usr/local/bin/witup

# ── Scripts de análise ─────────────────────────────────────────────────────────
RUN mkdir -p /opt/scripts
COPY scripts/ /opt/scripts/
RUN chmod +x /opt/scripts/*.sh 2>/dev/null || true

# ── Diretório de trabalho padrão ───────────────────────────────────────────────
WORKDIR /data

# Smoke test
RUN java -version 2>&1 | grep "21" \
    && python3 -c "import z3; print('z3 ok:', z3.get_version_string())" \
    && python3 -c "import sympy, psutil; print('sympy/psutil ok')"

CMD ["/bin/bash"]
