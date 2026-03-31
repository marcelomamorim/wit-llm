package experiment

import (
	"testing"

	"github.com/marceloamorim/witup-llm/internal/domain"
)

func TestBuildComparisonReportAndVariants(t *testing.T) {
	witupReport := domain.AnalysisReport{
		Source: domain.ExpathSourceWITUP,
		Analyses: []domain.MethodAnalysis{
			{
				Method: methodDescriptor("sample.Example.run(java.lang.String)", "src/main/java/sample/Example.java"),
				Expaths: []domain.ExceptionPath{
					expath("w1", "NullPointerException", "name == null", domain.ExpathSourceWITUP),
					expath("w2", "IllegalArgumentException", "name.isBlank()", domain.ExpathSourceWITUP),
				},
			},
		},
	}
	llmReport := domain.AnalysisReport{
		Source: domain.ExpathSourceLLM,
		Analyses: []domain.MethodAnalysis{
			{
				Method: methodDescriptor("sample.Example.run(java.lang.String)", "src/main/java/sample/Example.java"),
				Expaths: []domain.ExceptionPath{
					expath("l1", "NullPointerException", "name == null", domain.ExpathSourceLLM),
					expath("l2", "IllegalStateException", "cache == null", domain.ExpathSourceLLM),
				},
			},
			{
				Method: methodDescriptor("sample.Helper.validate()", "src/main/java/sample/Helper.java"),
				Expaths: []domain.ExceptionPath{
					expath("l3", "IllegalArgumentException", "count < 0", domain.ExpathSourceLLM),
				},
			},
		},
	}

	comparison := BuildComparisonReport("/tmp/witup.json", witupReport, "/tmp/llm.json", llmReport)
	if comparison.Summary.MethodsInBoth != 1 {
		t.Fatalf("expected 1 shared method, got %d", comparison.Summary.MethodsInBoth)
	}
	if comparison.Summary.MethodsOnlyLLM != 1 {
		t.Fatalf("expected 1 LLM-only method, got %d", comparison.Summary.MethodsOnlyLLM)
	}
	if comparison.Summary.SharedExpathCount != 1 {
		t.Fatalf("expected 1 shared expath, got %d", comparison.Summary.SharedExpathCount)
	}

	variants := BuildVariants(witupReport, llmReport)
	merged := variants[domain.VariantWITUPPlusLLM]
	if merged.Source != domain.ExpathSourceCombined {
		t.Fatalf("expected combined source, got %q", merged.Source)
	}
	if len(merged.Analyses) != 2 {
		t.Fatalf("expected 2 merged methods, got %d", len(merged.Analyses))
	}
	if len(merged.Analyses[0].Expaths) != 3 {
		t.Fatalf("expected merged expaths to deduplicate shared entry, got %d", len(merged.Analyses[0].Expaths))
	}
}

func methodDescriptor(signature, filePath string) domain.MethodDescriptor {
	return domain.MethodDescriptor{
		MethodID:      signature,
		FilePath:      filePath,
		ContainerName: "sample.Example",
		MethodName:    "run",
		Signature:     signature,
	}
}

func expath(id, exceptionType, trigger string, source domain.ExpathSource) domain.ExceptionPath {
	return domain.ExceptionPath{
		PathID:          id,
		ExceptionType:   exceptionType,
		Trigger:         trigger,
		GuardConditions: []string{trigger},
		Source:          source,
	}
}
