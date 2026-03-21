package br.unb.cic.witupllmautogen.analysis;

import br.unb.cic.witup.analysis.PathResolver;
import br.unb.cic.witup.analysis.ResolvedThrowCondition;
import br.unb.cic.witup.analysis.SymKind;
import br.unb.cic.witup.graph.WITUpAnalyser;
import br.unb.cic.witup.graph.WITUpGraph;
import br.unb.cic.witup.graph.edge.WITUpEdge;
import br.unb.cic.witup.graph.node.ThrowStatementNode;
import br.unb.cic.witup.graph.node.WITUpNode;
import br.unb.cic.witupllmautogen.analysis.model.AnalysisReport;
import br.unb.cic.witupllmautogen.analysis.model.PathConditionReport;
import br.unb.cic.witupllmautogen.analysis.model.ThrowPathReport;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.jgrapht.GraphPath;

public final class WitupAnalysisExtractor implements AnalysisProvider {

  @Override
  public AnalysisReport analyse(final String classPath, final String className) {
    WITUpAnalyser analyser = new WITUpAnalyser(classPath, className);
    Map<String, WITUpGraph> methodGraphs = analyser.buildWitUpGraphs();

    List<ThrowPathReport> throwPaths = new ArrayList<>();
    Map<String, String> symbolKinds = new LinkedHashMap<>();

    methodGraphs.entrySet().stream()
        .sorted(Map.Entry.comparingByKey())
        .forEach(
            entry -> {
              String methodSignature = entry.getKey();
              WITUpGraph graph = entry.getValue();
              List<WITUpNode> throwNodes = graph.getThrowNodes();

              for (int throwIndex = 0; throwIndex < throwNodes.size(); throwIndex++) {
                WITUpNode throwNode = throwNodes.get(throwIndex);
                List<GraphPath<WITUpNode, WITUpEdge>> paths = graph.getPathsWithIfStatements(throwNode);

                PathResolver resolver = new PathResolver(graph, paths);
                List<List<ResolvedThrowCondition>> resolvedPaths = resolver.resolveConditionPaths();
                resolver
                    .getSymbolKindTable()
                    .forEach((symbol, kind) -> symbolKinds.put(symbol, toKindName(kind)));

                String throwExpr = extractThrowExpression(throwNode);
                for (int pathIndex = 0; pathIndex < resolvedPaths.size(); pathIndex++) {
                  List<ResolvedThrowCondition> resolvedPath = resolvedPaths.get(pathIndex);
                  List<PathConditionReport> conditions = new ArrayList<>(resolvedPath.size());
                  for (ResolvedThrowCondition condition : resolvedPath) {
                    conditions.add(
                        new PathConditionReport(condition.getTruthValue(), condition.getNode().toString()));
                  }
                  throwPaths.add(
                      new ThrowPathReport(
                          methodSignature, throwIndex, pathIndex, throwExpr, List.copyOf(conditions)));
                }
              }
            });

    return new AnalysisReport(
        classPath,
        className,
        Instant.now(),
        methodGraphs.size(),
        throwPaths.size(),
        Map.copyOf(symbolKinds),
        List.copyOf(throwPaths));
  }

  private static String extractThrowExpression(final WITUpNode throwNode) {
    if (throwNode instanceof ThrowStatementNode ts) {
      return ts.getThrowExpr().toString();
    }
    return throwNode.getNode().toString();
  }

  private static String toKindName(final SymKind kind) {
    return kind == null ? "UNKNOWN" : kind.name();
  }
}
