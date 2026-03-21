package br.unb.cic.witupllmautogen.analysis;

import br.unb.cic.witupllmautogen.analysis.model.AnalysisReport;

public interface AnalysisProvider {
  AnalysisReport analyse(String classPath, String className);
}
