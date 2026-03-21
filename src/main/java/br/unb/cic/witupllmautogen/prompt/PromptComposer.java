package br.unb.cic.witupllmautogen.prompt;

import br.unb.cic.witupllmautogen.analysis.model.AnalysisReport;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface PromptComposer {
  String buildPrompt(String projectOverview, AnalysisReport analysisReport)
      throws JsonProcessingException;
}
