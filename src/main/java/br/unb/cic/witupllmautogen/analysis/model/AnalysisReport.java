package br.unb.cic.witupllmautogen.analysis.model;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public record AnalysisReport(
    String classPath,
    String className,
    Instant generatedAt,
    int analysedMethods,
    int analysedThrowPaths,
    Map<String, String> symbolKinds,
    List<ThrowPathReport> throwPaths) {}
