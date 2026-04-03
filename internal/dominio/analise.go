package dominio

// DescritorMetodo descreve um método Java catalogado no projeto analisado.
type DescritorMetodo struct {
	IDMetodo       string `json:"method_id"`
	CaminhoArquivo string `json:"file_path"`
	NomeContainer  string `json:"container_name"`
	NomeMetodo     string `json:"method_name"`
	Assinatura     string `json:"signature"`
	LinhaInicial   int    `json:"start_line"`
	LinhaFinal     int    `json:"end_line"`
	Origem         string `json:"source"`
}

// CaminhoExcecao representa um caminho de exceção hipotetizado pelo aplicacao.
type CaminhoExcecao struct {
	IDCaminho       string                 `json:"path_id"`
	TipoExcecao     string                 `json:"exception_type"`
	Gatilho         string                 `json:"trigger"`
	CondicoesGuarda []string               `json:"guard_conditions"`
	Confianca       float64                `json:"confidence"`
	Evidencias      []string               `json:"evidence"`
	Origem          OrigemExpath           `json:"source,omitempty"`
	Metadados       map[string]interface{} `json:"metadata,omitempty"`
}

// AnaliseMetodo é o artefato canônico de análise por método.
type AnaliseMetodo struct {
	Metodo          DescritorMetodo        `json:"method"`
	ResumoMetodo    string                 `json:"method_summary"`
	CaminhosExcecao []CaminhoExcecao       `json:"expaths"`
	RespostaBruta   map[string]interface{} `json:"raw_response"`
}

// RelatorioAnalise reúne todas as análises produzidas em uma execução.
type RelatorioAnalise struct {
	IDExecucao   string          `json:"run_id"`
	RaizProjeto  string          `json:"project_root"`
	ChaveModelo  string          `json:"model_key"`
	Origem       OrigemExpath    `json:"source"`
	Estrategia   string          `json:"strategy,omitempty"`
	GeradoEm     string          `json:"generated_at"`
	TotalMetodos int             `json:"total_methods"`
	Analises     []AnaliseMetodo `json:"analyses"`
}
