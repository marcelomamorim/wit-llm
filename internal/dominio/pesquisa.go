package dominio

// OrigemExpath identifica a origem de um caminho de exceção.
type OrigemExpath string

const (
	OrigemExpathWITUP     OrigemExpath = "witup_article"
	OrigemExpathLLM       OrigemExpath = "llm_generated"
	OrigemExpathCombinada OrigemExpath = "witup_plus_llm"
)

// VarianteComparacao descreve a composição da suíte avaliada no experimento.
type VarianteComparacao string

const (
	VarianteWITUPApenas  VarianteComparacao = "WITUP_ONLY"
	VarianteLLMApenas    VarianteComparacao = "LLM_ONLY"
	VarianteWITUPMaisLLM VarianteComparacao = "WITUP_PLUS_LLM"
)

// UnidadeComparacao é a unidade estável usada para alinhar saídas do artigo e da LLM.
type UnidadeComparacao struct {
	Projeto          string `json:"project"`
	NomeClasse       string `json:"class_name"`
	CaminhoArquivo   string `json:"file_path,omitempty"`
	NomeMetodo       string `json:"method_name,omitempty"`
	AssinaturaMetodo string `json:"method_signature"`
	LinhaInicial     int    `json:"start_line,omitempty"`
	TipoExcecao      string `json:"exception_type"`
	PontoThrow       string `json:"throw_site,omitempty"`
}
