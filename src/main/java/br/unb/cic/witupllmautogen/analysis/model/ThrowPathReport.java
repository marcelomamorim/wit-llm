package br.unb.cic.witupllmautogen.analysis.model;

import java.util.List;

public record ThrowPathReport(
    String methodSignature,
    int throwIndex,
    int pathIndex,
    String throwExpression,
    List<PathConditionReport> conditions) {}
