package witup

import (
	"fmt"
	"path/filepath"
	"sort"
	"strings"

	"github.com/marceloamorim/witup-llm/internal/artifacts"
	"github.com/marceloamorim/witup-llm/internal/domain"
)

type rawBaseline struct {
	Path       string     `json:"path"`
	CommitHash string     `json:"commitHash"`
	Classes    []rawClass `json:"classes"`
}

type rawClass struct {
	Path    string      `json:"path"`
	Methods []rawMethod `json:"methods"`
}

type rawMethod struct {
	QualifiedSignature        string   `json:"qualifiedSignature"`
	Exception                 string   `json:"exception"`
	PathConjunction           string   `json:"pathCojunction"`
	SymbolicPathConjunction   string   `json:"symbolicPathConjunction"`
	BackwardsPathConjunction  string   `json:"backwardsPathConjunction"`
	SimplifiedPathConjunction string   `json:"simplifiedPathConjunction"`
	Z3Inputs                  string   `json:"z3Inputs"`
	SoundSymbolic             bool     `json:"soundSymbolic"`
	SoundBackwards            bool     `json:"soundBackwards"`
	Maybe                     bool     `json:"maybe"`
	Line                      int      `json:"line"`
	ThrowingLine              int      `json:"throwingLine"`
	IsStatic                  bool     `json:"isStatic"`
	TargetOnlyArguments       bool     `json:"targetOnlyArguments"`
	CallSequence              []string `json:"callSequence"`
	InlineSequence            []string `json:"inlineSequence"`
}

type groupedMethod struct {
	descriptor domain.MethodDescriptor
	expaths    []domain.ExceptionPath
	rawEntries []rawMethod
}

// LoadAnalysis converts a WITUP baseline file into the canonical analysis
// artifact used by the rest of the project.
func LoadAnalysis(path string) (domain.AnalysisReport, error) {
	raw := rawBaseline{}
	if err := artifacts.ReadJSON(path, &raw); err != nil {
		return domain.AnalysisReport{}, err
	}

	grouped := map[string]*groupedMethod{}
	for _, classEntry := range raw.Classes {
		normalizedPath := normalizeFilePath(classEntry.Path)
		for index, methodEntry := range classEntry.Methods {
			descriptor := buildMethodDescriptor(normalizedPath, methodEntry)
			key := descriptor.Signature + "|" + descriptor.FilePath
			record, ok := grouped[key]
			if !ok {
				record = &groupedMethod{descriptor: descriptor}
				grouped[key] = record
			}
			record.rawEntries = append(record.rawEntries, methodEntry)
			record.expaths = append(record.expaths, buildExceptionPath(descriptor.MethodID, methodEntry, index+1, raw.CommitHash))
		}
	}

	keys := make([]string, 0, len(grouped))
	for key := range grouped {
		keys = append(keys, key)
	}
	sort.Strings(keys)

	analyses := make([]domain.MethodAnalysis, 0, len(keys))
	for _, key := range keys {
		record := grouped[key]
		rawResponse := map[string]interface{}{
			"baseline":    "witup_article",
			"entry_count": len(record.rawEntries),
			"raw_entries": record.rawEntries,
		}
		analyses = append(analyses, domain.MethodAnalysis{
			Method:        record.descriptor,
			MethodSummary: "Imported from WITUP replication package.",
			Expaths:       record.expaths,
			RawResponse:   rawResponse,
		})
	}

	return domain.AnalysisReport{
		RunID:        artifacts.NewRunID("witup-baseline"),
		ProjectRoot:  normalizeProjectRoot(raw.Path),
		ModelKey:     "witup_article",
		Source:       domain.ExpathSourceWITUP,
		Strategy:     "witup_baseline_import",
		GeneratedAt:  domain.TimestampUTC(),
		TotalMethods: len(analyses),
		Analyses:     analyses,
	}, nil
}

// ResolveBaselinePath points to a file in the local replication package.
func ResolveBaselinePath(replicationRoot, projectKey, fileName string) string {
	return filepath.Join(replicationRoot, projectKey, fileName)
}

func buildMethodDescriptor(classPath string, method rawMethod) domain.MethodDescriptor {
	signature := strings.TrimSpace(method.QualifiedSignature)
	containerName, methodName := splitQualifiedSignature(signature)
	return domain.MethodDescriptor{
		MethodID:      signature,
		FilePath:      classPath,
		ContainerName: containerName,
		MethodName:    methodName,
		Signature:     signature,
		StartLine:     method.Line,
		EndLine:       method.ThrowingLine,
		Source:        "",
	}
}

func buildExceptionPath(methodID string, method rawMethod, index int, commitHash string) domain.ExceptionPath {
	trigger := strings.TrimSpace(method.SimplifiedPathConjunction)
	if trigger == "" {
		trigger = strings.TrimSpace(method.PathConjunction)
	}
	pathID := fmt.Sprintf("%s#%d#%d", methodID, method.ThrowingLine, index)
	return domain.ExceptionPath{
		PathID:          pathID,
		ExceptionType:   extractExceptionType(method.Exception),
		Trigger:         trigger,
		GuardConditions: nonEmptyStrings(trigger),
		Confidence:      deriveConfidence(method.Maybe, method.SoundSymbolic, method.SoundBackwards),
		Evidence:        buildEvidence(method),
		Source:          domain.ExpathSourceWITUP,
		Metadata: map[string]interface{}{
			"exception_statement":         method.Exception,
			"path_conjunction":            method.PathConjunction,
			"symbolic_path_conjunction":   method.SymbolicPathConjunction,
			"backwards_path_conjunction":  method.BackwardsPathConjunction,
			"simplified_path_conjunction": method.SimplifiedPathConjunction,
			"z3_inputs":                   method.Z3Inputs,
			"sound_symbolic":              method.SoundSymbolic,
			"sound_backwards":             method.SoundBackwards,
			"maybe":                       method.Maybe,
			"line":                        method.Line,
			"throwing_line":               method.ThrowingLine,
			"is_static":                   method.IsStatic,
			"target_only_arguments":       method.TargetOnlyArguments,
			"call_sequence":               method.CallSequence,
			"inline_sequence":             method.InlineSequence,
			"commit_hash":                 commitHash,
		},
	}
}

func splitQualifiedSignature(signature string) (string, string) {
	trimmed := strings.TrimSpace(signature)
	openParen := strings.Index(trimmed, "(")
	prefix := trimmed
	if openParen >= 0 {
		prefix = trimmed[:openParen]
	}
	lastDot := strings.LastIndex(prefix, ".")
	if lastDot < 0 {
		return prefix, prefix
	}
	return prefix[:lastDot], prefix[lastDot+1:]
}

func normalizeProjectRoot(raw string) string {
	value := normalizeSlashPath(raw)
	return strings.TrimSuffix(value, "/")
}

func normalizeFilePath(raw string) string {
	value := normalizeSlashPath(raw)
	lower := strings.ToLower(value)
	markers := []string{"/src/main/java/", "/src/test/java/"}
	for _, marker := range markers {
		index := strings.Index(lower, marker)
		if index >= 0 {
			return strings.TrimPrefix(value[index+1:], "/")
		}
	}
	return strings.TrimPrefix(value, "/")
}

func normalizeSlashPath(raw string) string {
	value := strings.TrimSpace(raw)
	value = strings.ReplaceAll(value, "\\", "/")
	return filepath.ToSlash(value)
}

func extractExceptionType(statement string) string {
	trimmed := strings.TrimSpace(statement)
	if trimmed == "" {
		return "UnknownException"
	}
	const marker = "new "
	index := strings.Index(trimmed, marker)
	if index < 0 {
		return strings.Trim(trimmed, ";")
	}
	after := trimmed[index+len(marker):]
	stop := len(after)
	for _, delimiter := range []string{"(", " ", ";"} {
		if next := strings.Index(after, delimiter); next >= 0 && next < stop {
			stop = next
		}
	}
	value := strings.TrimSpace(after[:stop])
	if value == "" {
		return "UnknownException"
	}
	return value
}

func deriveConfidence(maybe, soundSymbolic, soundBackwards bool) float64 {
	switch {
	case !maybe && soundSymbolic && soundBackwards:
		return 1.0
	case !maybe:
		return 0.85
	case soundSymbolic || soundBackwards:
		return 0.6
	default:
		return 0.45
	}
}

func buildEvidence(method rawMethod) []string {
	evidence := []string{
		fmt.Sprintf("article_line=%d", method.Line),
		fmt.Sprintf("throwing_line=%d", method.ThrowingLine),
	}
	for _, call := range method.CallSequence {
		call = strings.TrimSpace(call)
		if call == "" {
			continue
		}
		evidence = append(evidence, "call:"+call)
	}
	return evidence
}

func nonEmptyStrings(values ...string) []string {
	out := make([]string, 0, len(values))
	for _, value := range values {
		trimmed := strings.TrimSpace(value)
		if trimmed == "" {
			continue
		}
		out = append(out, trimmed)
	}
	return out
}
