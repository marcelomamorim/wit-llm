FROM ubuntu:22.04

ARG OPENJDK_TAG=jdk-21+35
ARG OPENJDK_REPO=https://github.com/openjdk/jdk.git
ARG DEBUG_LEVEL=release

ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update && apt-get install -y --no-install-recommends \
        autoconf zip unzip file make build-essential \
        libx11-dev libxext-dev libxrender-dev libxtst-dev libxt-dev \
        libxrandr-dev libxi-dev \
        libcups2-dev libfontconfig1-dev libasound2-dev \
        openjdk-21-jdk-headless \
        git ccache curl ca-certificates wget \
    && rm -rf /var/lib/apt/lists/*

RUN if [ "$(uname -m)" = "aarch64" ]; then \
        ln -sf /usr/lib/jvm/java-21-openjdk-arm64 /opt/boot-jdk ; \
    else \
        ln -sf /usr/lib/jvm/java-21-openjdk-amd64 /opt/boot-jdk ; \
    fi

WORKDIR /opt

RUN git clone --branch "${OPENJDK_TAG}" --depth 1 "${OPENJDK_REPO}" openjdk-src

WORKDIR /opt/openjdk-src

RUN bash configure \
        --with-boot-jdk=/opt/boot-jdk \
        --with-debug-level=${DEBUG_LEVEL} \
        --enable-ccache \
        --disable-warnings-as-errors

RUN make CONF_NAME=$(ls build/) images

RUN ln -sf /opt/openjdk-src/build/*/images/jdk /opt/test-jdk \
    && ln -sf /opt/openjdk-src/build/*/images /opt/jdk-images

RUN /opt/test-jdk/bin/java -version 2>&1 | tee /opt/build-version.txt \
    && grep "21" /opt/build-version.txt

# Limpar artefatos de build desnecessários para reduzir tamanho da imagem
RUN rm -rf \
        /opt/openjdk-src/build/*/buildtools \
        /opt/openjdk-src/build/*/hotspot \
        /opt/openjdk-src/build/*/support

ENV TEST_JDK=/opt/test-jdk
ENV JDK_ROOT=/opt/openjdk-src
ENV PATH="/opt/test-jdk/bin:${PATH}"

CMD ["/opt/test-jdk/bin/java", "-version"]
