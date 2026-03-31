package pipeline

import (
	"fmt"
	"strings"

	"github.com/marceloamorim/witup-llm/internal/domain"
)

func normalizeMethodAnalysis(method domain.MethodDescriptor, payload map[string]interface{}) domain.MethodAnalysis {
	summary := strings.TrimSpace(fmt.Sprint(payload["method_summary"]))
	if summary == "<nil>" {
		summary = ""
	}

	expaths := make([]domain.ExceptionPath, 0)
	if raw, ok := payload["expaths"].([]interface{}); ok {
		for i, item := range raw {
			entry, ok := item.(map[string]interface{})
			if !ok {
				continue
			}

			exType := strings.TrimSpace(fmt.Sprint(entry["exception_type"]))
			trigger := strings.TrimSpace(fmt.Sprint(entry["trigger"]))
			if exType == "" || exType == "<nil>" {
				continue
			}

			confidence := parseFloat(entry["confidence"], 0)
			if confidence < 0 {
				confidence = 0
			}
			if confidence > 1 {
				confidence = 1
			}

			expaths = append(expaths, domain.ExceptionPath{
				PathID:          fallbackPathID(fmt.Sprint(entry["path_id"]), method.MethodID, i+1),
				ExceptionType:   exType,
				Trigger:         trigger,
				GuardConditions: toStringList(entry["guard_conditions"]),
				Confidence:      confidence,
				Evidence:        toStringList(entry["evidence"]),
			})
		}
	}

	return domain.MethodAnalysis{
		Method:        method,
		MethodSummary: summary,
		Expaths:       expaths,
		RawResponse:   payload,
	}
}

func normalizeGenerationResponse(payload map[string]interface{}) (string, []domain.GeneratedTestFile) {
	summary := strings.TrimSpace(fmt.Sprint(payload["strategy_summary"]))
	if summary == "<nil>" {
		summary = ""
	}

	files := []domain.GeneratedTestFile{}
	raw, ok := payload["files"].([]interface{})
	if !ok {
		return summary, files
	}

	for _, item := range raw {
		entry, ok := item.(map[string]interface{})
		if !ok {
			continue
		}

		relativePath := strings.TrimSpace(fmt.Sprint(entry["relative_path"]))
		content := fmt.Sprint(entry["content"])
		if relativePath == "" || relativePath == "<nil>" || strings.TrimSpace(content) == "" {
			continue
		}

		files = append(files, domain.GeneratedTestFile{
			RelativePath:     relativePath,
			Content:          content,
			CoveredMethodIDs: toStringList(entry["covered_method_ids"]),
			Notes:            strings.TrimSpace(fmt.Sprint(entry["notes"])),
		})
	}
	return summary, files
}

func normalizeJudgeResponse(payload map[string]interface{}) domain.JudgeEvaluation {
	score := parseFloat(payload["score"], 0)
	if score < 0 {
		score = 0
	}
	if score > 100 {
		score = 100
	}

	return domain.JudgeEvaluation{
		Score:                  score,
		Verdict:                strings.TrimSpace(fmt.Sprint(payload["verdict"])),
		Strengths:              toStringList(payload["strengths"]),
		Weaknesses:             toStringList(payload["weaknesses"]),
		Risks:                  toStringList(payload["risks"]),
		RecommendedNextActions: toStringList(payload["recommended_next_actions"]),
		RawResponse:            payload,
	}
}
