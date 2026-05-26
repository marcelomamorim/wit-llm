package dominio

// TentativaMetrica registra uma tentativa individual de executar uma métrica,
// incluindo estratégias de fallback quando configuradas.
type TentativaMetrica struct {
	Nome            string   `json:"name"`
	Comando         string   `json:"command"`
	Sucesso         bool     `json:"success"`
	CodigoSaida     int      `json:"exit_code"`
	SaidaPadrao     string   `json:"stdout"`
	SaidaErro       string   `json:"stderr"`
	ValorNumerico   *float64 `json:"numeric_value"`
	NotaNormalizada *float64 `json:"normalized_score"`
	TempoEsgotado   bool     `json:"timed_out,omitempty"`
	TimeoutSegundos int      `json:"timeout_seconds,omitempty"`
	DuracaoMillis   int64    `json:"duration_ms,omitempty"`
}

// ResultadoMetrica registra o resultado de execução de uma métrica.
type ResultadoMetrica struct {
	Nome                string             `json:"name"`
	Tipo                string             `json:"kind"`
	Comando             string             `json:"command"`
	Sucesso             bool               `json:"success"`
	CodigoSaida         int                `json:"exit_code"`
	SaidaPadrao         string             `json:"stdout"`
	SaidaErro           string             `json:"stderr"`
	ValorNumerico       *float64           `json:"numeric_value"`
	NotaNormalizada     *float64           `json:"normalized_score"`
	Peso                float64            `json:"weight"`
	Descricao           string             `json:"description"`
	EstrategiaExecutada string             `json:"executed_strategy,omitempty"`
	Tentativas          []TentativaMetrica `json:"attempts,omitempty"`
	TempoEsgotado       bool               `json:"timed_out,omitempty"`
	TimeoutSegundos     int                `json:"timeout_seconds,omitempty"`
	DuracaoMillis       int64              `json:"duration_ms,omitempty"`
}

// AuditoriaPontuacaoMetricas registra como a nota objetiva foi derivada.
type AuditoriaPontuacaoMetricas struct {
	NotaBruta           *float64 `json:"uncapped_metric_score,omitempty"`
	NotaEstatica        *float64 `json:"static_metric_score,omitempty"`
	NotaExecutavel      *float64 `json:"execution_metric_score,omitempty"`
	RazaoCap            string   `json:"metric_score_cap_reason,omitempty"`
	SuiteExecutavel     bool     `json:"executable_suite"`
	QuantidadeTestes    int      `json:"test_file_count"`
	CompilacaoMensurada bool     `json:"test_compilation_measured"`
	CompilacaoSucesso   bool     `json:"test_compilation_success"`
}

// AvaliacaoJuiz armazena a saída opcional do juiz baseado em LLM.
type AvaliacaoJuiz struct {
	Nota                      float64                `json:"score"`
	Veredito                  string                 `json:"verdict"`
	Forcas                    []string               `json:"strengths"`
	Fraquezas                 []string               `json:"weaknesses"`
	Riscos                    []string               `json:"risks"`
	ProximasAcoesRecomendadas []string               `json:"recommended_next_actions"`
	RespostaBruta             map[string]interface{} `json:"raw_response"`
}

// RelatorioAvaliacao é o relatório final de uma execução ponta a ponta.
type RelatorioAvaliacao struct {
	IDExecucao          string                     `json:"run_id"`
	ChaveModelo         string                     `json:"model_key"`
	GeradoEm            string                     `json:"generated_at"`
	CaminhoAnalise      string                     `json:"analysis_path"`
	CaminhoGeracao      string                     `json:"generation_path"`
	ResultadosMetricas  []ResultadoMetrica         `json:"metric_results"`
	NotaMetricas        *float64                   `json:"metric_score"`
	AuditoriaPontuacao  AuditoriaPontuacaoMetricas `json:"metric_score_audit,omitempty"`
	ChaveModeloJuiz     string                     `json:"judge_model_key,omitempty"`
	AvaliacaoJuiz       *AvaliacaoJuiz             `json:"judge_evaluation,omitempty"`
	NotaCombinada       *float64                   `json:"combined_score"`
	IntervencoesHarness []string                   `json:"harness_interventions,omitempty"`
	RequestCount        int                        `json:"request_count"`
	InputTokens         int                        `json:"input_tokens"`
	OutputTokens        int                        `json:"output_tokens"`
	EstimatedCost       *float64                   `json:"estimated_cost,omitempty"`
}
