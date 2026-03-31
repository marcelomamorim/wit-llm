package pipeline

import (
	"encoding/json"
	"fmt"

	"github.com/marceloamorim/witup-llm/internal/domain"
)

func buildAnalysisSystemPrompt() string {
	return "You are an expert static analyzer for Java code. Output valid JSON only."
}

func buildAnalysisUserPrompt(overview string, method domain.MethodDescriptor) string {
	return fmt.Sprintf(`Analyze the Java method and list exception paths.
Return JSON: {"method_summary":"...","expaths":[{"path_id":"...","exception_type":"...","trigger":"...","guard_conditions":[...],"confidence":0.0,"evidence":[...]}]}

Project overview:
%s

Method signature: %s
Method source:
%s
`, overview, method.Signature, method.Source)
}

func buildGenerationSystemPrompt(framework string) string {
	return fmt.Sprintf("You are an expert Java test writer using %s. Output JSON only.", framework)
}

func buildGenerationUserPrompt(overview, containerName string, methodsPayload []domain.MethodAnalysis) string {
	compact, _ := json.MarshalIndent(methodsPayload, "", "  ")
	return fmt.Sprintf(`Generate deterministic Java test files for the methods below.
Return JSON: {"strategy_summary":"...","files":[{"relative_path":"...","content":"...","covered_method_ids":[...],"notes":"..."}]}

Language: Java
Container: %s
Project overview:
%s

Method analyses:
%s
`, containerName, overview, string(compact))
}

func buildJudgeSystemPrompt() string {
	return "You are a strict evaluator. Output JSON only with score, verdict, strengths, weaknesses, risks, and recommended_next_actions."
}

func buildJudgeUserPrompt(analysis domain.AnalysisReport, generation domain.GenerationReport, metricResults []domain.MetricResult) string {
	analysisJSON, _ := json.MarshalIndent(analysis, "", "  ")
	generationJSON, _ := json.MarshalIndent(generation, "", "  ")
	metricsJSON, _ := json.MarshalIndent(metricResults, "", "  ")
	return fmt.Sprintf(`Evaluate pipeline quality. Output JSON:
{"score":0-100,"verdict":"...","strengths":[...],"weaknesses":[...],"risks":[...],"recommended_next_actions":[...]}

Analysis:
%s

Generation:
%s

Metrics:
%s
`, string(analysisJSON), string(generationJSON), string(metricsJSON))
}
