package dominio

// ConfigProjeto descreve o projeto Java analisado pelo aplicacao.
//
// A implementação atual cataloga apenas código-fonte Java. Suporte a outras
// linguagens da JVM pode ser adicionado depois, mas permanece fora do escopo da
// linha de base de pesquisa atual.
type ConfigProjeto struct {
	Raiz          string   `json:"root"`
	Include       []string `json:"include"`
	Exclude       []string `json:"exclude"`
	OverviewFile  string   `json:"overview_file"`
	TestFramework string   `json:"test_framework"`
}

// ConfigFluxo controla o comportamento geral do aplicacao.
type ConfigFluxo struct {
	DiretorioSaida     string `json:"output_dir"`
	CaminhoDuckDB      string `json:"duckdb_path"`
	RaizReplicacaoWIT  string `json:"replication_root"`
	ArquivoBaselineWIT string `json:"baseline_file"`
	SalvarPrompts      bool   `json:"save_prompts"`
	MaximoMetodos      int    `json:"max_methods"`
	ModeloJuiz         string `json:"judge_model"`
	ModoLLM            string `json:"llm_mode"`
	TamanhoSubconjunto int    `json:"deep_validation_subset_size"`
}

// ConfigModelo define um endpoint de LLM configurado.
type ConfigModelo struct {
	Provedor                 string  `json:"provider"`
	Modelo                   string  `json:"model"`
	URLBase                  string  `json:"base_url"`
	VariavelAmbienteChaveAPI string  `json:"api_key_env"`
	Temperature              float64 `json:"temperature"`
	SegundosTimeout          int     `json:"timeout_seconds"`
	MaximoTentativas         int     `json:"max_retries"`
	EsforcoRaciocinio        string  `json:"reasoning_effort"`
	RetencaoCachePrompt      string  `json:"prompt_cache_retention"`
	NivelServico             string  `json:"service_tier"`
	MaximoTokensSaida        int     `json:"max_output_tokens"`
}

// ModoLLM identifica a estratégia principal usada pela branch LLM no experimento.
type ModoLLM string

const (
	ModoLLMDireto      ModoLLM = "direct"
	ModoLLMMultiagente ModoLLM = "multiagent"
)

// OpcoesRequisicaoLLM agrupa parâmetros variáveis por chamada, sem contaminar
// a configuração estática do modelo.
type OpcoesRequisicaoLLM struct {
	PromptCacheKey     string
	PreviousResponseID string
	PreservarEstado    bool
}

// ConfigMetrica define um comando executável de métrica.
type ConfigMetrica struct {
	Nome              string   `json:"name"`
	Tipo              string   `json:"kind"`
	Comando           string   `json:"command"`
	Peso              float64  `json:"weight"`
	RegexValor        string   `json:"value_regex"`
	Escala            float64  `json:"scale"`
	DiretorioTrabalho string   `json:"working_directory"`
	SaidasEsperadas   []string `json:"expected_outputs"`
	Descricao         string   `json:"description"`
}

// ConfigAplicacao representa a configuração raiz da aplicação.
type ConfigAplicacao struct {
	CaminhoConfig string                  `json:"config_path"`
	Versao        string                  `json:"version"`
	Projeto       ConfigProjeto           `json:"project"`
	Fluxo         ConfigFluxo             `json:"pipeline"`
	Modelos       map[string]ConfigModelo `json:"models"`
	Metricas      []ConfigMetrica         `json:"metrics"`
}
