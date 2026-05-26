package dominio

import (
	"path/filepath"
	"strings"
)

// CenarioSegundaFase identifica as duas condições comparadas na nova fase.
type CenarioSegundaFase string

const (
	CenarioSegundaFaseContextoWIT CenarioSegundaFase = "WIT_CONTEXT"
	CenarioSegundaFaseDireto      CenarioSegundaFase = "DIRECT_TESTS"
	CenarioSegundaFaseHintExcecao CenarioSegundaFase = "EXCEPTION_HINT"
)

const (
	ModoExecucaoSegundaFaseEstrito = "strict_1call"
	ModoExecucaoSegundaFaseReparo  = "repair_1retry"
)

// MetodoAlvoDetalhadoSegundaFase materializa o método-alvo no artefato final
// para facilitar auditoria, leitura humana e visualização no dashboard.
type MetodoAlvoDetalhadoSegundaFase struct {
	IDMetodo       string `json:"method_id"`
	CaminhoArquivo string `json:"file_path"`
	NomeContainer  string `json:"container_name"`
	NomeMetodo     string `json:"method_name"`
	Assinatura     string `json:"signature"`
	Origem         string `json:"source_code"`
}

// ArquivoTesteDetalhadoSegundaFase representa um arquivo de teste gerado com
// os métodos cobertos, pronto para visualização/auditoria.
type ArquivoTesteDetalhadoSegundaFase struct {
	CaminhoRelativo    string       `json:"relative_path"`
	Conteudo           string       `json:"content"`
	IDsMetodosCobertos []string     `json:"covered_method_ids"`
	Observacoes        string       `json:"notes,omitempty"`
	AcoesExpath        []AcaoExpath `json:"expath_actions,omitempty"`
}

// ParMetodoTesteSegundaFase aproxima cada método-alvo dos arquivos de teste
// gerados que o cobrem, facilitando inspeção e auditoria dos resultados.
type ParMetodoTesteSegundaFase struct {
	Metodo MetodoAlvoDetalhadoSegundaFase     `json:"method"`
	Testes []ArquivoTesteDetalhadoSegundaFase `json:"generated_tests"`
}

// ResultadoCenarioSegundaFase resume a geração e a avaliação de um cenário.
type ResultadoCenarioSegundaFase struct {
	Projeto             string                             `json:"project_key"`
	RotuloProjeto       string                             `json:"project_label"`
	Cenario             CenarioSegundaFase                 `json:"scenario"`
	CaminhoBaseline     string                             `json:"wit_analysis_path"`
	CaminhoAnalise      string                             `json:"analysis_path,omitempty"`
	CaminhoGeracao      string                             `json:"generation_path"`
	CaminhoAvaliacao    string                             `json:"evaluation_path"`
	QuantidadeMetodos   int                                `json:"method_count"`
	QuantidadeExpaths   int                                `json:"expath_count"`
	QuantidadeTestes    int                                `json:"test_file_count"`
	MetodosAlvo         []MetodoAlvoDetalhadoSegundaFase   `json:"target_methods,omitempty"`
	ArquivosTeste       []ArquivoTesteDetalhadoSegundaFase `json:"generated_test_files,omitempty"`
	ParesMetodoTeste    []ParMetodoTesteSegundaFase        `json:"method_test_pairs,omitempty"`
	ResultadosMetricas  []ResultadoMetrica                 `json:"metric_results"`
	NotaMetricas        *float64                           `json:"metric_score"`
	AuditoriaPontuacao  AuditoriaPontuacaoMetricas         `json:"metric_score_audit,omitempty"`
	ChaveModeloJuiz     string                             `json:"judge_model_key,omitempty"`
	AvaliacaoJuiz       *AvaliacaoJuiz                     `json:"judge_evaluation,omitempty"`
	NotaCombinada       *float64                           `json:"combined_score,omitempty"`
	IntervencoesHarness []string                           `json:"harness_interventions,omitempty"`
	ModoExecucao        string                             `json:"execution_mode,omitempty"`
	RequestCount        int                                `json:"request_count"`
	RepairUsed          bool                               `json:"repair_used"`
	InputTokens         int                                `json:"input_tokens"`
	OutputTokens        int                                `json:"output_tokens"`
	EstimatedCost       *float64                           `json:"estimated_cost,omitempty"`
}

// ComparacaoProjetoSegundaFase resume a comparação entre WIT context e geração direta.
type ComparacaoProjetoSegundaFase struct {
	Projeto              string                       `json:"project_key"`
	RotuloProjeto        string                       `json:"project_label"`
	ContextoWIT          ResultadoCenarioSegundaFase  `json:"wit_context"`
	GeracaoDireta        ResultadoCenarioSegundaFase  `json:"direct_generation"`
	HintExcecao          *ResultadoCenarioSegundaFase `json:"exception_hint,omitempty"`
	DirecaoDelta         string                       `json:"delta_direction"`
	DeltaNotaMetricas    *float64                     `json:"metric_score_delta_wit_minus_direct"`
	DeltaCoberturaLinha  *float64                     `json:"jacoco_line_delta_wit_minus_direct"`
	DeltaCoberturaBranch *float64                     `json:"jacoco_branch_delta_wit_minus_direct"`
	DeltaMutacao         *float64                     `json:"pit_mutation_delta_wit_minus_direct"`
	// Deltas EXCEPTION_HINT vs DIRECT_TESTS (presentes somente quando HintExcecao != nil)
	DeltaNotaMetricasHintMenosDireto    *float64 `json:"metric_score_delta_hint_minus_direct,omitempty"`
	DeltaCoberturaLinhaHintMenosDireto  *float64 `json:"jacoco_line_delta_hint_minus_direct,omitempty"`
	DeltaCoberturaBranchHintMenosDireto *float64 `json:"jacoco_branch_delta_hint_minus_direct,omitempty"`
}

// RelatorioSegundaFase consolida os dois projetos da nova etapa em um artefato único.
type RelatorioSegundaFase struct {
	IDExecucao           string                         `json:"run_id"`
	GeradoEm             string                         `json:"generated_at"`
	ChaveModeloGeracao   string                         `json:"generation_model_key"`
	ChaveModeloJuiz      string                         `json:"judge_model_key,omitempty"`
	Projetos             []ComparacaoProjetoSegundaFase `json:"projects"`
	CaminhoCSVResumo     string                         `json:"summary_csv_path"`
	CaminhoCSVMetricas   string                         `json:"metrics_csv_path"`
	CaminhoCSVComparacao string                         `json:"comparison_csv_path"`
	CaminhoDashboard     string                         `json:"dashboard_path"`
}

// DiagnosticoAmbienteSegundaFase resume a disponibilidade das dependências
// externas necessárias para a fase 2.
type DiagnosticoAmbienteSegundaFase struct {
	JavaPath        string   `json:"java_path,omitempty"`
	JavaVersion     string   `json:"java_version,omitempty"`
	MavenPath       string   `json:"maven_path,omitempty"`
	MavenVersion    string   `json:"maven_version,omitempty"`
	JavaDisponivel  bool     `json:"java_available"`
	MavenDisponivel bool     `json:"maven_available"`
	Problemas       []string `json:"problems,omitempty"`
	Avisos          []string `json:"warnings,omitempty"`
}

// DiagnosticoProjetoSegundaFase resume a prontidão de um único projeto da fase 2.
type DiagnosticoProjetoSegundaFase struct {
	Projeto             string   `json:"project_key"`
	RotuloProjeto       string   `json:"project_label"`
	Raiz                string   `json:"root"`
	CaminhoBaseline     string   `json:"wit_analysis_path"`
	OverviewFile        string   `json:"overview_file,omitempty"`
	ContainersAlvo      []string `json:"target_containers,omitempty"`
	TemPomXML           bool     `json:"has_pom_xml"`
	TemMavenWrapper     bool     `json:"has_maven_wrapper"`
	BuildCheckExecutado bool     `json:"build_check_executed"`
	BuildCheckSucesso   bool     `json:"build_check_success"`
	SaidaBuildCheck     string   `json:"build_check_output,omitempty"`
	MetodosBaseline     int      `json:"baseline_method_count"`
	MetodosCatalogados  int      `json:"catalog_method_count"`
	MetodosAlinhados    int      `json:"aligned_method_count"`
	Pronto              bool     `json:"ready"`
	Problemas           []string `json:"problems,omitempty"`
	Avisos              []string `json:"warnings,omitempty"`
}

// RelatorioPreflightSegundaFase consolida a checagem de prontidão antes de uma
// rodada paga da segunda fase.
type RelatorioPreflightSegundaFase struct {
	IDExecucao       string                          `json:"run_id"`
	GeradoEm         string                          `json:"generated_at"`
	CaminhoConfig    string                          `json:"config_path"`
	VerificacaoBuild bool                            `json:"build_check_enabled"`
	Pronto           bool                            `json:"ready"`
	Ambiente         DiagnosticoAmbienteSegundaFase  `json:"environment"`
	Projetos         []DiagnosticoProjetoSegundaFase `json:"projects"`
	ComandoSugerido  string                          `json:"suggested_command"`
}

// NomeArquivoBaseline devolve apenas o nome do arquivo de baseline usado no cenário.
func (r ResultadoCenarioSegundaFase) NomeArquivoBaseline() string {
	if strings.TrimSpace(r.CaminhoBaseline) == "" {
		return ""
	}
	return filepath.Base(r.CaminhoBaseline)
}

// TipoBaseline resume o tipo de baseline empregado na execução.
func (r ResultadoCenarioSegundaFase) TipoBaseline() string {
	nome := strings.ToLower(strings.TrimSpace(r.NomeArquivoBaseline()))
	switch nome {
	case "wit_filtered.json":
		return "WIT_FILTERED"
	case "wit.json":
		return "WIT"
	case "":
		return ""
	default:
		return strings.TrimSuffix(nome, filepath.Ext(nome))
	}
}

// RotuloHumano descreve o cenário com foco em leitura humana para CSV/dashboard.
func (r ResultadoCenarioSegundaFase) RotuloHumano() string {
	switch r.Cenario {
	case CenarioSegundaFaseContextoWIT:
		if r.TipoBaseline() == "WIT_FILTERED" {
			return "WIT filtrado como contexto"
		}
		return "WIT como contexto"
	case CenarioSegundaFaseDireto:
		return "Método cru (sem WIT)"
	case CenarioSegundaFaseHintExcecao:
		return "Apenas tipos de exceção (sem estrutura WIT)"
	default:
		return string(r.Cenario)
	}
}

// DescricaoHumana detalha, em uma frase, o que foi fornecido ao modelo.
func (r ResultadoCenarioSegundaFase) DescricaoHumana() string {
	switch r.Cenario {
	case CenarioSegundaFaseContextoWIT:
		if r.TipoBaseline() == "WIT_FILTERED" {
			return "Usa o wit_filtered.json alinhado ao checkout atual e gera testes a partir dos métodos e expaths filtrados."
		}
		return "Usa o baseline WIT alinhado ao checkout atual e gera testes a partir dos métodos e expaths fornecidos."
	case CenarioSegundaFaseDireto:
		return "Usa os mesmos métodos listados no baseline, mas envia apenas o código cru dos métodos ao modelo, sem contexto WIT."
	case CenarioSegundaFaseHintExcecao:
		return "Usa os mesmos métodos e os tipos de exceção dos expaths WIT, mas sem gatilho, condições de guarda ou evidências."
	default:
		return ""
	}
}
