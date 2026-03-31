package experiment

import (
	"path/filepath"
	"sort"
	"strings"

	"github.com/marceloamorim/witup-llm/internal/artifacts"
	"github.com/marceloamorim/witup-llm/internal/domain"
)

type methodBucket struct {
	key      string
	unit     domain.ComparisonUnit
	analysis domain.MethodAnalysis
}

// BuildComparisonReport compares two canonical analysis reports and summarizes
// their overlap before test generation happens.
func BuildComparisonReport(
	witupPath string,
	witupReport domain.AnalysisReport,
	llmPath string,
	llmReport domain.AnalysisReport,
) domain.SourceComparisonReport {
	witupBuckets := bucketize(witupReport)
	llmBuckets := bucketize(llmReport)

	keys := sortedUnionKeys(witupBuckets, llmBuckets)
	methods := make([]domain.MethodComparison, 0, len(keys))
	summary := domain.SourceComparisonSummary{
		WITUPMethodCount: countNonEmptyBuckets(witupBuckets),
		LLMMethodCount:   countNonEmptyBuckets(llmBuckets),
		WITUPExpathCount: countReportExpaths(witupReport),
		LLMExpathCount:   countReportExpaths(llmReport),
	}

	for _, key := range keys {
		witupBucket, witupOK := witupBuckets[key]
		llmBucket, llmOK := llmBuckets[key]
		if witupOK && llmOK {
			summary.MethodsInBoth++
		} else if witupOK {
			summary.MethodsOnlyWITUP++
		} else {
			summary.MethodsOnlyLLM++
		}

		unit := comparisonUnitFor(witupBucket, llmBucket)
		witupIndex := expathIndex(witupBucket.analysis.Expaths)
		llmIndex := expathIndex(llmBucket.analysis.Expaths)

		sharedCount := 0
		witupOnly := make([]string, 0)
		llmOnly := make([]string, 0)
		for pathKey, path := range witupIndex {
			if _, ok := llmIndex[pathKey]; ok {
				sharedCount++
				continue
			}
			witupOnly = append(witupOnly, path.PathID)
		}
		for pathKey, path := range llmIndex {
			if _, ok := witupIndex[pathKey]; ok {
				continue
			}
			llmOnly = append(llmOnly, path.PathID)
		}
		sort.Strings(witupOnly)
		sort.Strings(llmOnly)

		summary.SharedExpathCount += sharedCount
		summary.WITUPOnlyExpathIDs += len(witupOnly)
		summary.LLMOnlyExpathIDs += len(llmOnly)

		methods = append(methods, domain.MethodComparison{
			Unit:               unit,
			WITUPExpathCount:   len(witupBucket.analysis.Expaths),
			LLMExpathCount:     len(llmBucket.analysis.Expaths),
			SharedExpathCount:  sharedCount,
			WITUPOnlyExpathIDs: witupOnly,
			LLMOnlyExpathIDs:   llmOnly,
		})
	}

	return domain.SourceComparisonReport{
		RunID:             artifacts.NewRunID("source-comparison"),
		GeneratedAt:       domain.TimestampUTC(),
		WITUPAnalysisPath: witupPath,
		LLMAnalysisPath:   llmPath,
		Methods:           methods,
		Summary:           summary,
	}
}

// BuildVariants materializes the three experimental branches used by the
// current study.
func BuildVariants(witupReport, llmReport domain.AnalysisReport) map[domain.ComparisonVariant]domain.AnalysisReport {
	return map[domain.ComparisonVariant]domain.AnalysisReport{
		domain.VariantWITUPOnly:    cloneReport(witupReport, domain.VariantWITUPOnly, "witup_only"),
		domain.VariantLLMOnly:      cloneReport(llmReport, domain.VariantLLMOnly, "llm_only"),
		domain.VariantWITUPPlusLLM: mergeReports(witupReport, llmReport),
	}
}

// WriteVariantArtifacts persists the three supported experimental variants.
func WriteVariantArtifacts(
	workspace *artifacts.Workspace,
	variants map[domain.ComparisonVariant]domain.AnalysisReport,
) ([]domain.VariantArtifact, error) {
	ordered := []domain.ComparisonVariant{
		domain.VariantWITUPOnly,
		domain.VariantLLMOnly,
		domain.VariantWITUPPlusLLM,
	}

	artifactsList := make([]domain.VariantArtifact, 0, len(ordered))
	for _, variant := range ordered {
		report, ok := variants[variant]
		if !ok {
			continue
		}
		path := filepath.Join(workspace.Variants, strings.ToLower(string(variant))+".analysis.json")
		if err := artifacts.WriteJSON(path, report); err != nil {
			return nil, err
		}
		artifactsList = append(artifactsList, domain.VariantArtifact{
			Variant:      variant,
			AnalysisPath: path,
			MethodCount:  len(report.Analyses),
			ExpathCount:  countReportExpaths(report),
		})
	}
	return artifactsList, nil
}

func cloneReport(report domain.AnalysisReport, variant domain.ComparisonVariant, strategy string) domain.AnalysisReport {
	cloned := report
	cloned.RunID = artifacts.NewRunID(strings.ToLower(string(variant)))
	cloned.Strategy = strategy
	return cloned
}

func mergeReports(witupReport, llmReport domain.AnalysisReport) domain.AnalysisReport {
	witupBuckets := bucketize(witupReport)
	llmBuckets := bucketize(llmReport)
	keys := sortedUnionKeys(witupBuckets, llmBuckets)

	mergedAnalyses := make([]domain.MethodAnalysis, 0, len(keys))
	for _, key := range keys {
		witupBucket, witupOK := witupBuckets[key]
		llmBucket, llmOK := llmBuckets[key]
		switch {
		case witupOK && llmOK:
			mergedAnalyses = append(mergedAnalyses, mergeMethodAnalysis(witupBucket.analysis, llmBucket.analysis))
		case witupOK:
			mergedAnalyses = append(mergedAnalyses, witupBucket.analysis)
		case llmOK:
			mergedAnalyses = append(mergedAnalyses, llmBucket.analysis)
		}
	}

	return domain.AnalysisReport{
		RunID:        artifacts.NewRunID("witup-plus-llm"),
		ProjectRoot:  firstNonEmpty(witupReport.ProjectRoot, llmReport.ProjectRoot),
		ModelKey:     llmReport.ModelKey,
		Source:       domain.ExpathSourceCombined,
		Strategy:     "witup_plus_llm",
		GeneratedAt:  domain.TimestampUTC(),
		TotalMethods: len(mergedAnalyses),
		Analyses:     mergedAnalyses,
	}
}

func mergeMethodAnalysis(witupAnalysis, llmAnalysis domain.MethodAnalysis) domain.MethodAnalysis {
	index := map[string]domain.ExceptionPath{}
	for _, path := range witupAnalysis.Expaths {
		index[expathKey(path)] = path
	}
	for _, path := range llmAnalysis.Expaths {
		key := expathKey(path)
		if existing, ok := index[key]; ok {
			if existing.Source == domain.ExpathSourceWITUP {
				existing.Metadata = mergeMetadata(existing.Metadata, path.Metadata)
				index[key] = existing
			}
			continue
		}
		index[key] = path
	}

	keys := make([]string, 0, len(index))
	for key := range index {
		keys = append(keys, key)
	}
	sort.Strings(keys)

	expaths := make([]domain.ExceptionPath, 0, len(keys))
	for _, key := range keys {
		expaths = append(expaths, index[key])
	}

	return domain.MethodAnalysis{
		Method:        preferMethodDescriptor(llmAnalysis.Method, witupAnalysis.Method),
		MethodSummary: firstNonEmpty(llmAnalysis.MethodSummary, witupAnalysis.MethodSummary),
		Expaths:       expaths,
		RawResponse: map[string]interface{}{
			"witup": witupAnalysis.RawResponse,
			"llm":   llmAnalysis.RawResponse,
		},
	}
}

func bucketize(report domain.AnalysisReport) map[string]methodBucket {
	out := make(map[string]methodBucket, len(report.Analyses))
	for _, analysis := range report.Analyses {
		key := methodKey(analysis.Method)
		out[key] = methodBucket{
			key:      key,
			unit:     buildComparisonUnit(analysis.Method, analysis.Expaths),
			analysis: analysis,
		}
	}
	return out
}

func buildComparisonUnit(method domain.MethodDescriptor, expaths []domain.ExceptionPath) domain.ComparisonUnit {
	unit := domain.ComparisonUnit{
		ClassName:       method.ContainerName,
		MethodSignature: method.Signature,
	}
	if len(expaths) > 0 {
		unit.ExceptionType = expaths[0].ExceptionType
	}
	return unit
}

func methodKey(method domain.MethodDescriptor) string {
	return method.Signature + "|" + method.FilePath
}

func expathIndex(expaths []domain.ExceptionPath) map[string]domain.ExceptionPath {
	out := make(map[string]domain.ExceptionPath, len(expaths))
	for _, path := range expaths {
		out[expathKey(path)] = path
	}
	return out
}

func expathKey(path domain.ExceptionPath) string {
	return strings.Join([]string{
		strings.TrimSpace(path.ExceptionType),
		strings.TrimSpace(path.Trigger),
		strings.Join(path.GuardConditions, " && "),
	}, "|")
}

func countReportExpaths(report domain.AnalysisReport) int {
	total := 0
	for _, analysis := range report.Analyses {
		total += len(analysis.Expaths)
	}
	return total
}

func comparisonUnitFor(witupBucket, llmBucket methodBucket) domain.ComparisonUnit {
	switch {
	case witupBucket.unit.MethodSignature != "":
		return witupBucket.unit
	case llmBucket.unit.MethodSignature != "":
		return llmBucket.unit
	default:
		return domain.ComparisonUnit{}
	}
}

func sortedUnionKeys(left, right map[string]methodBucket) []string {
	seen := map[string]bool{}
	keys := make([]string, 0, len(left)+len(right))
	for key := range left {
		if seen[key] {
			continue
		}
		seen[key] = true
		keys = append(keys, key)
	}
	for key := range right {
		if seen[key] {
			continue
		}
		seen[key] = true
		keys = append(keys, key)
	}
	sort.Strings(keys)
	return keys
}

func countNonEmptyBuckets(values map[string]methodBucket) int {
	return len(values)
}

func preferMethodDescriptor(primary, fallback domain.MethodDescriptor) domain.MethodDescriptor {
	method := primary
	if strings.TrimSpace(method.MethodID) == "" {
		method.MethodID = fallback.MethodID
	}
	if strings.TrimSpace(method.FilePath) == "" {
		method.FilePath = fallback.FilePath
	}
	if strings.TrimSpace(method.ContainerName) == "" {
		method.ContainerName = fallback.ContainerName
	}
	if strings.TrimSpace(method.MethodName) == "" {
		method.MethodName = fallback.MethodName
	}
	if strings.TrimSpace(method.Signature) == "" {
		method.Signature = fallback.Signature
	}
	if method.StartLine == 0 {
		method.StartLine = fallback.StartLine
	}
	if method.EndLine == 0 {
		method.EndLine = fallback.EndLine
	}
	if strings.TrimSpace(method.Source) == "" {
		method.Source = fallback.Source
	}
	return method
}

func mergeMetadata(left, right map[string]interface{}) map[string]interface{} {
	if len(left) == 0 && len(right) == 0 {
		return nil
	}
	merged := map[string]interface{}{}
	for key, value := range left {
		merged[key] = value
	}
	for key, value := range right {
		if _, exists := merged[key]; exists {
			continue
		}
		merged[key] = value
	}
	return merged
}

func firstNonEmpty(values ...string) string {
	for _, value := range values {
		if strings.TrimSpace(value) != "" {
			return value
		}
	}
	return ""
}
