package dominio

// ResultadoMetrica registra o resultado de execução de uma métrica.
type ResultadoMetrica struct {
	Nome            string   `json:"name"`
	Tipo            string   `json:"kind"`
	Comando         string   `json:"command"`
	Sucesso         bool     `json:"success"`
	CodigoSaida     int      `json:"exit_code"`
	SaidaPadrao     string   `json:"stdout"`
	SaidaErro       string   `json:"stderr"`
	ValorNumerico   *float64 `json:"numeric_value"`
	NotaNormalizada *float64 `json:"normalized_score"`
	Peso            float64  `json:"weight"`
	Descricao       string   `json:"description"`
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
	IDExecucao         string             `json:"run_id"`
	ChaveModelo        string             `json:"model_key"`
	GeradoEm           string             `json:"generated_at"`
	CaminhoAnalise     string             `json:"analysis_path"`
	CaminhoGeracao     string             `json:"generation_path"`
	ResultadosMetricas []ResultadoMetrica `json:"metric_results"`
	NotaMetricas       *float64           `json:"metric_score"`
	ChaveModeloJuiz    string             `json:"judge_model_key,omitempty"`
	AvaliacaoJuiz      *AvaliacaoJuiz     `json:"judge_evaluation,omitempty"`
	NotaCombinada      *float64           `json:"combined_score"`
}

// CenarioBenchmark associa um modelo de análise a um modelo de geração.
type CenarioBenchmark struct {
	ChaveModeloAnalise string `json:"analysis_model_key"`
	ChaveModeloGeracao string `json:"generation_model_key"`
}

// EntradaBenchmark representa uma linha ranqueada do benchmark.
type EntradaBenchmark struct {
	ChaveModeloAnalise string   `json:"analysis_model_key"`
	ChaveModeloGeracao string   `json:"generation_model_key"`
	CaminhoAvaliacao   string   `json:"evaluation_path"`
	NotaMetricas       *float64 `json:"metric_score"`
	JudgeScore         *float64 `json:"judge_score"`
	NotaCombinada      *float64 `json:"combined_score"`
	Posicao            int      `json:"rank"`
}

// RelatorioBenchmark armazena o ranking consolidado entre cenários.
type RelatorioBenchmark struct {
	IDExecucao      string             `json:"run_id"`
	GeradoEm        string             `json:"generated_at"`
	ChaveModeloJuiz string             `json:"judge_model_key,omitempty"`
	Entradas        []EntradaBenchmark `json:"entries"`
}
