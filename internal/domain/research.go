package domain

// ExpathSource identifies where an exception path originated.
type ExpathSource string

const (
	ExpathSourceWITUP    ExpathSource = "witup_article"
	ExpathSourceLLM      ExpathSource = "llm_generated"
	ExpathSourceCombined ExpathSource = "witup_plus_llm"
)

// ComparisonVariant describes the suite composition being evaluated.
type ComparisonVariant string

const (
	VariantWITUPOnly    ComparisonVariant = "WITUP_ONLY"
	VariantLLMOnly      ComparisonVariant = "LLM_ONLY"
	VariantWITUPPlusLLM ComparisonVariant = "WITUP_PLUS_LLM"
	VariantUnion        ComparisonVariant = "UNION"
	VariantIntersection ComparisonVariant = "INTERSECTION"
)

// ComparisonUnit is the stable unit used to align article and LLM outputs.
type ComparisonUnit struct {
	Project         string `json:"project"`
	ClassName       string `json:"class_name"`
	MethodSignature string `json:"method_signature"`
	ExceptionType   string `json:"exception_type"`
	ThrowSite       string `json:"throw_site,omitempty"`
}
