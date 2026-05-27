FROM ubuntu:18.04

# da75f3c4 = JDK 11+28 (pré-GA, usado na análise WIT do Diego)
# ubuntu:18.04: make 4.1 + gcc 7 — ambiente compatível com JDK 11 de 2018
ARG OPENJDK_COMMIT=da75f3c4
ARG DEBUG_LEVEL=release

ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update && apt-get install -y --no-install-recommends \
        autoconf zip unzip file make build-essential \
        libx11-dev libxext-dev libxrender-dev libxtst-dev libxt-dev \
        libxrandr-dev libxi-dev \
        libcups2-dev libfontconfig1-dev libasound2-dev \
        openjdk-11-jdk-headless \
        git ccache curl ca-certificates wget \
    && rm -rf /var/lib/apt/lists/*

# Boot JDK: OpenJDK 11 do Ubuntu 18.04
RUN if [ "$(uname -m)" = "aarch64" ]; then \
        ln -sf /usr/lib/jvm/java-11-openjdk-arm64 /opt/boot-jdk ; \
    else \
        ln -sf /usr/lib/jvm/java-11-openjdk-amd64 /opt/boot-jdk ; \
    fi

WORKDIR /opt

RUN mkdir openjdk-src \
    && curl -fL "https://github.com/openjdk/jdk/archive/${OPENJDK_COMMIT}.tar.gz" \
       | tar -xz -C openjdk-src --strip-components=1

WORKDIR /opt/openjdk-src

RUN bash configure \
        --with-boot-jdk=/opt/boot-jdk \
        --with-debug-level=${DEBUG_LEVEL} \
        --disable-warnings-as-errors

# make + cleanup em UM ÚNICO RUN para evitar layer intermediário gigante (~4GB).
# BuildKit só armazena o delta: adiciona images/jdk, nunca persiste hotspot/support.
RUN make CONF_NAME=$(ls build/) images \
    && ln -sf /opt/openjdk-src/build/*/images/jdk /opt/test-jdk \
    && ln -sf /opt/openjdk-src/build/*/images /opt/jdk-images \
    && /opt/test-jdk/bin/java -version 2>&1 | tee /opt/build-version.txt \
    && grep "11" /opt/build-version.txt \
    && rm -rf \
           /opt/openjdk-src/build/*/buildtools \
           /opt/openjdk-src/build/*/hotspot \
           /opt/openjdk-src/build/*/support \
           /opt/boot-jdk

ENV TEST_JDK=/opt/test-jdk
ENV JDK_ROOT=/opt/openjdk-src
ENV PATH="/opt/test-jdk/bin:${PATH}"

CMD ["/opt/test-jdk/bin/java", "-version"]
