package dominio

// PapelAgente identifica uma etapa determinística da ramificação multiagente
// baseada apenas em LLM. Cada papel mantém responsabilidade restrita para
// facilitar explicação, teste e auditoria do fluxo.
type PapelAgente string

const (
	PapelAgenteArqueologo   PapelAgente = "archaeologist"
	PapelAgenteDependencias PapelAgente = "dependency_mesh"
	PapelAgenteExtrator     PapelAgente = "expath_extractor"
	PapelAgenteCetico       PapelAgente = "skeptic_reviewer"
)

// EtapaRastreioAgente registra uma execução de agente para um método.
type EtapaRastreioAgente struct {
	Papel              PapelAgente            `json:"role"`
	Resumo             string                 `json:"summary"`
	IDResposta         string                 `json:"response_id,omitempty"`
	IDRespostaAnterior string                 `json:"previous_response_id,omitempty"`
	ArquivoPrompt      string                 `json:"prompt_file,omitempty"`
	ArquivoSaida       string                 `json:"output_file,omitempty"`
	Saida              map[string]interface{} `json:"output"`
}

// RastreioAgenteMetodo agrupa todas as etapas de agentes executadas para um método.
type RastreioAgenteMetodo struct {
	Metodo         DescritorMetodo       `json:"method"`
	MotivosSelecao []string              `json:"selection_reasons,omitempty"`
	Etapas         []EtapaRastreioAgente `json:"steps"`
}

// RelatorioRastreioAgente é o artefato persistido de rastreio da ramificação LLM_ONLY.
type RelatorioRastreioAgente struct {
	IDExecucao    string                 `json:"run_id"`
	ChaveModelo   string                 `json:"model_key"`
	GeradoEm      string                 `json:"generated_at"`
	EtapasProjeto []EtapaRastreioAgente  `json:"project_steps,omitempty"`
	Metodos       []RastreioAgenteMetodo `json:"methods"`
}

// ComparacaoMetodo resume como duas fontes se alinham em uma unidade de comparação.
type ComparacaoMetodo struct {
	Unidade                         UnidadeComparacao `json:"unit"`
	QuantidadeExpathsWITUP          int               `json:"witup_expath_count"`
	QuantidadeExpathsLLM            int               `json:"llm_expath_count"`
	QuantidadeExpathsCompartilhados int               `json:"shared_expath_count"`
	IDsExpathsApenasWITUP           []string          `json:"witup_only_expath_ids"`
	IDsExpathsApenasLLM             []string          `json:"llm_only_expath_ids"`
}

// ResumoComparacaoFontes armazena contagens agregadas de sobreposição em um experimento.
type ResumoComparacaoFontes struct {
	QuantidadeMetodosWITUP          int `json:"witup_method_count"`
	QuantidadeMetodosLLM            int `json:"llm_method_count"`
	MetodosEmAmbos                  int `json:"methods_in_both"`
	MetodosApenasWITUP              int `json:"methods_only_witup"`
	MetodosApenasLLM                int `json:"methods_only_llm"`
	QuantidadeExpathsWITUP          int `json:"witup_expath_count"`
	QuantidadeExpathsLLM            int `json:"llm_expath_count"`
	QuantidadeExpathsCompartilhados int `json:"shared_expath_count"`
	QuantidadeExpathsApenasWITUP    int `json:"witup_only_expath_count"`
	QuantidadeExpathsApenasLLM      int `json:"llm_only_expath_count"`
}

// MetricasComparacaoFontes consolida métricas derivadas da Parte 1 para tornar
// a comparação entre WITUP e LLM legível sem depender apenas de contagens brutas.
type MetricasComparacaoFontes struct {
	TaxaCoberturaMetodosLLMSobreWITUP *float64 `json:"llm_method_coverage_over_witup"`
	TaxaCoberturaExpathsLLMSobreWITUP *float64 `json:"llm_expath_coverage_over_witup"`
	TaxaPrecisaoEstruturalLLM         *float64 `json:"llm_structural_precision"`
	IndiceJaccardExpaths              *float64 `json:"expath_jaccard_index"`
	TaxaNovidadeLLM                   *float64 `json:"llm_novelty_rate"`
}

// RelatorioComparacaoFontes registra a comparação entre fontes antes da produção
// de artefatos derivados, como testes gerados.
type RelatorioComparacaoFontes struct {
	IDExecucao          string                   `json:"run_id"`
	GeradoEm            string                   `json:"generated_at"`
	CaminhoAnaliseWITUP string                   `json:"witup_analysis_path"`
	CaminhoAnaliseLLM   string                   `json:"llm_analysis_path"`
	Metodos             []ComparacaoMetodo       `json:"methods"`
	Resumo              ResumoComparacaoFontes   `json:"summary"`
	Metricas            MetricasComparacaoFontes `json:"metrics"`
}

// ArtefatoVariante aponta para um artefato de análise persistido de uma variante experimental.
type ArtefatoVariante struct {
	Variante          VarianteComparacao `json:"variant"`
	CaminhoAnalise    string             `json:"analysis_path"`
	QuantidadeMetodos int                `json:"method_count"`
	QuantidadeExpaths int                `json:"expath_count"`
}

// RelatorioExperimento conecta as três ramificações suportadas:
// WITUP_ONLY, LLM_ONLY e WITUP_PLUS_LLM.
type RelatorioExperimento struct {
	IDExecucao                     string                 `json:"run_id"`
	GeradoEm                       string                 `json:"generated_at"`
	CaminhoAnaliseWITUP            string                 `json:"witup_analysis_path"`
	CaminhoAnaliseLLM              string                 `json:"llm_analysis_path"`
	CaminhoComparacao              string                 `json:"comparison_path"`
	ArtefatosVariantes             []ArtefatoVariante     `json:"variant_artifacts"`
	ResumoComparacao               ResumoComparacaoFontes `json:"comparison_summary"`
	CaminhoRelatorioRastreioAgente string                 `json:"agent_trace_report_path,omitempty"`
}

// ResultadoVarianteEstudoCompleto resume a execução de geração e avaliação para
// uma variante experimental.
type ResultadoVarianteEstudoCompleto struct {
	Variante                VarianteComparacao     `json:"variant"`
	CaminhoAnalise          string                 `json:"analysis_path"`
	QuantidadeMetodos       int                    `json:"method_count"`
	QuantidadeExpaths       int                    `json:"expath_count"`
	CaminhoGeracao          string                 `json:"generation_path"`
	QuantidadeArquivosTeste int                    `json:"test_file_count"`
	CaminhoAvaliacao        string                 `json:"evaluation_path"`
	ResultadosMetricas      []ResultadoMetrica     `json:"metric_results,omitempty"`
	NotaMetricas            *float64               `json:"metric_score,omitempty"`
	NotaJuiz                *float64               `json:"judge_score,omitempty"`
	VereditoJuiz            string                 `json:"judge_verdict,omitempty"`
	NotaCombinada           *float64               `json:"combined_score,omitempty"`
	MetricasDerivadas       MetricasVarianteEstudo `json:"derived_metrics"`
}

// MetricasVarianteEstudo resume indicadores derivados da Parte 2 para cada
// variante avaliada.
type MetricasVarianteEstudo struct {
	TaxaArquivosTestePorMetodo *float64 `json:"test_files_per_method"`
	TaxaArquivosTestePorExpath *float64 `json:"test_files_per_expath"`
	TaxaSucessoMetricas        *float64 `json:"metric_success_rate"`
}

// ComparacaoSuitesEstudo consolida o comparativo entre as suítes geradas a
// partir do WITUP, da LLM e da combinação das duas fontes.
type ComparacaoSuitesEstudo struct {
	MelhorVariantePorNotaMetricas      string   `json:"best_variant_by_metric_score,omitempty"`
	MelhorVariantePorNotaCombinada     string   `json:"best_variant_by_combined_score,omitempty"`
	DeltaArquivosTesteLLMVsWITUP       *float64 `json:"delta_test_files_llm_vs_witup,omitempty"`
	DeltaArquivosTesteCombinadoVsWITUP *float64 `json:"delta_test_files_combined_vs_witup,omitempty"`
	DeltaMetricasLLMVsWITUP            *float64 `json:"delta_metric_score_llm_vs_witup,omitempty"`
	DeltaMetricasCombinadoVsWITUP      *float64 `json:"delta_metric_score_combined_vs_witup,omitempty"`
	DeltaMetricasCombinadoVsLLM        *float64 `json:"delta_metric_score_combined_vs_llm,omitempty"`
	DeltaCombinadaLLMVsWITUP           *float64 `json:"delta_combined_score_llm_vs_witup,omitempty"`
	DeltaCombinadaCombinadoVsWITUP     *float64 `json:"delta_combined_score_combined_vs_witup,omitempty"`
	DeltaCombinadaCombinadoVsLLM       *float64 `json:"delta_combined_score_combined_vs_llm,omitempty"`
}

// RelatorioEstudoCompleto conecta a Parte 1 (comparação de expaths) e a Parte 2
// (geração e avaliação de testes) em um único artefato consolidado.
type RelatorioEstudoCompleto struct {
	IDExecucao          string                            `json:"run_id"`
	GeradoEm            string                            `json:"generated_at"`
	ChaveProjeto        string                            `json:"project_key"`
	ChaveModeloAnalise  string                            `json:"analysis_model_key"`
	ChaveModeloGeracao  string                            `json:"generation_model_key"`
	ChaveModeloJuiz     string                            `json:"judge_model_key,omitempty"`
	CaminhoExperimento  string                            `json:"experiment_report_path"`
	CaminhoComparacao   string                            `json:"comparison_path"`
	ResultadosVariantes []ResultadoVarianteEstudoCompleto `json:"variants"`
	ComparacaoSuites    ComparacaoSuitesEstudo            `json:"suite_comparison"`
}
