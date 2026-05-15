package dominio

// MetodoJDKGlobal registra um método-alvo selecionado para o estudo de impacto
// global no JDK.
type MetodoJDKGlobal struct {
	Indice            int    `json:"index"`
	IDMetodo          string `json:"method_id"`
	CaminhoArquivo    string `json:"file_path"`
	NomeContainer     string `json:"container_name"`
	NomeMetodo        string `json:"method_name"`
	Assinatura        string `json:"signature"`
	QuantidadeExpaths int    `json:"expath_count"`
}

// RelatorioPreparacaoJDKGlobal descreve a amostra e o JSONL Batch preparado.
type RelatorioPreparacaoJDKGlobal struct {
	IDExecucao              string            `json:"run_id"`
	GeradoEm                string            `json:"generated_at"`
	Projeto                 string            `json:"project"`
	URLRepositorio          string            `json:"repository_url"`
	CommitWIT               string            `json:"wit_commit"`
	RaizJDK                 string            `json:"jdk_root"`
	CaminhoWIT              string            `json:"wit_analysis_path"`
	UnidadeExperimental     string            `json:"experimental_unit"`
	AnaliseMetodoSecundaria bool              `json:"method_level_analysis_secondary"`
	QuantidadeMetodos       int               `json:"method_count"`
	QuantidadeExpaths       int               `json:"expath_count"`
	QuantidadeRequests      int               `json:"request_count"`
	ChaveModeloGeracao      string            `json:"generation_model_key"`
	CaminhoAnalise          string            `json:"analysis_path"`
	CaminhoManifestCSV      string            `json:"manifest_csv_path"`
	CaminhoRequestsJSONL    string            `json:"requests_jsonl_path"`
	Metodos                 []MetodoJDKGlobal `json:"methods"`
}

// ResultadoMetricaGlobalJDK guarda o resultado bruto de uma métrica global por
// variante do projeto.
type ResultadoMetricaGlobalJDK struct {
	Nome            string `json:"name"`
	Comando         string `json:"command,omitempty"`
	Status          string `json:"status"`
	CodigoSaida     int    `json:"exit_code,omitempty"`
	SaidaPadrao     string `json:"stdout,omitempty"`
	SaidaErro       string `json:"stderr,omitempty"`
	DuracaoMillis   int64  `json:"duration_ms,omitempty"`
	TimeoutSegundos int    `json:"timeout_seconds,omitempty"`
}

// ResultadoVarianteJDKGlobal representa uma das três variantes do experimento:
// baseline, WIT_CONTEXT ou DIRECT_TESTS.
type ResultadoVarianteJDKGlobal struct {
	Nome               string                      `json:"name"`
	Cenario            string                      `json:"scenario"`
	RaizProjeto        string                      `json:"project_root"`
	CaminhoGeracao     string                      `json:"generation_path,omitempty"`
	QuantidadeTestes   int                         `json:"generated_test_file_count"`
	InputTokens        int                         `json:"input_tokens,omitempty"`
	OutputTokens       int                         `json:"output_tokens,omitempty"`
	EstimatedCost      *float64                    `json:"estimated_cost,omitempty"`
	ResultadosMetricas []ResultadoMetricaGlobalJDK `json:"metric_results"`
}

// RelatorioJDKGlobal consolida o estudo de impacto global no projeto-alvo.
type RelatorioJDKGlobal struct {
	IDExecucao              string                       `json:"run_id"`
	GeradoEm                string                       `json:"generated_at"`
	Projeto                 string                       `json:"project"`
	URLRepositorio          string                       `json:"repository_url"`
	CommitWIT               string                       `json:"wit_commit"`
	UnidadeExperimental     string                       `json:"experimental_unit"`
	AnaliseMetodoSecundaria bool                         `json:"method_level_analysis_secondary"`
	CaminhoPreparacao       string                       `json:"preparation_path"`
	CaminhoManifestCSV      string                       `json:"manifest_csv_path"`
	CaminhoResumoCSV        string                       `json:"summary_csv_path"`
	CaminhoComparacaoCSV    string                       `json:"comparison_csv_path"`
	Variantes               []ResultadoVarianteJDKGlobal `json:"variants"`
}

// ResultadoJTRegJDKGlobal guarda o resultado de execução jtreg para uma variante
// materializada do estudo global do JDK.
type ResultadoJTRegJDKGlobal struct {
	Variante        string   `json:"variant"`
	Cenario         string   `json:"scenario"`
	RaizProjeto     string   `json:"project_root"`
	Alvos           []string `json:"targets"`
	Status          string   `json:"status"`
	CodigoSaida     int      `json:"exit_code,omitempty"`
	Total           int      `json:"total,omitempty"`
	Passou          int      `json:"passed,omitempty"`
	Falhou          int      `json:"failed,omitempty"`
	Erro            int      `json:"error,omitempty"`
	NaoExecutado    int      `json:"not_run,omitempty"`
	DuracaoMillis   int64    `json:"duration_ms,omitempty"`
	ReportDir       string   `json:"report_dir,omitempty"`
	WorkDir         string   `json:"work_dir,omitempty"`
	SaidaPadrao     string   `json:"stdout,omitempty"`
	SaidaErro       string   `json:"stderr,omitempty"`
	CoberturaLinha  *float64 `json:"line_coverage,omitempty"`
	StatusCobertura string   `json:"coverage_status,omitempty"`
}

// RelatorioJTRegJDKGlobal consolida uma execução local de jtreg sobre as
// variantes já materializadas do estudo global do JDK.
type RelatorioJTRegJDKGlobal struct {
	IDExecucao           string                    `json:"run_id"`
	GeradoEm             string                    `json:"generated_at"`
	RunDir               string                    `json:"run_dir"`
	JTReg                string                    `json:"jtreg"`
	TestJDK              string                    `json:"test_jdk"`
	JavaHome             string                    `json:"java_home,omitempty"`
	ArchX8664            bool                      `json:"arch_x86_64,omitempty"`
	AlvoBase             string                    `json:"base_target,omitempty"`
	AlvoGerado           string                    `json:"generated_target,omitempty"`
	ComandoCobertura     string                    `json:"coverage_command,omitempty"`
	Resultados           []ResultadoJTRegJDKGlobal `json:"results"`
	CaminhoResumoCSV     string                    `json:"summary_csv_path"`
	CaminhoComparacaoCSV string                    `json:"comparison_csv_path"`
}
