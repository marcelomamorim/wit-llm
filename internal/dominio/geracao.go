package dominio

// ArquivoTesteGerado representa um arquivo de teste emitido pelo aplicacao.
type ArquivoTesteGerado struct {
	CaminhoRelativo    string   `json:"relative_path"`
	Conteudo           string   `json:"content"`
	IDsMetodosCobertos []string `json:"covered_method_ids"`
	Observacoes        string   `json:"notes"`
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
}
