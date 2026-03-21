package br.unb.cic.witupllmautogen.application;

import java.nio.file.Path;

public record GenerationRequest(
    String classPath,
    String className,
    String model,
    Integer numThread,
    String ollamaUrl,
    Path outputPath,
    Path analysisOutputPath,
    Path promptOutputPath,
    Path overviewFile,
    boolean dryRun) {}
