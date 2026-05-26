package dominio

// AcaoExpath registra o que o modelo fez com um expath WIT específico.
type AcaoExpath struct {
	IDCaminho string `json:"path_id"`
	Acao      string `json:"action"` // "used" | "adapted" | "discarded"
	Razao     string `json:"reason"`
}

// ArquivoTesteGerado representa um arquivo de teste emitido pelo aplicacao.
type ArquivoTesteGerado struct {
	CaminhoRelativo    string       `json:"relative_path"`
	Conteudo           string       `json:"content"`
	IDsMetodosCobertos []string     `json:"covered_method_ids"`
	Observacoes        string       `json:"notes"`
	AcoesExpath        []AcaoExpath `json:"expath_actions,omitempty"`
}

// RelatorioGeracao resume os testes gerados em uma execução.
type RelatorioGeracao struct {
	IDExecucao           string                   `json:"run_id"`
	CaminhoAnaliseOrigem string                   `json:"source_analysis_path"`
	ChaveModelo          string                   `json:"model_key"`
	GeradoEm             string                   `json:"generated_at"`
	ResumoEstrategia     string                   `json:"strategy_summary"`
	ArquivosTeste        []ArquivoTesteGerado     `json:"test_files"`
	RespostasBrutas      []map[string]interface{} `json:"raw_responses"`
	IntervencoesHarness  []string                 `json:"harness_interventions,omitempty"`
	RequestCount         int                      `json:"request_count"`
	InputTokens          int                      `json:"input_tokens"`
	OutputTokens         int                      `json:"output_tokens"`
	EstimatedCost        *float64                 `json:"estimated_cost,omitempty"`
}
