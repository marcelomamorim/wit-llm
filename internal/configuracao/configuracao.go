package configuracao

import (
	"bytes"
	"encoding/json"
	"errors"
	"fmt"
	"os"
	"path/filepath"
	"strings"

	"github.com/marceloamorim/witup-llm/internal/dominio"
)

var (
	provedoresSuportados = map[string]bool{"ollama": true, "openai_compatible": true}
	exclusoesPadrao      = []string{
		".git",
		"target",
		"build",
		"generated",
		"tests",
	}
)

type configFluxoBruta struct {
	DiretorioSaida     string `json:"output_dir"`
	CaminhoDuckDB      string `json:"duckdb_path"`
	RaizReplicacaoWIT  string `json:"replication_root"`
	ArquivoBaselineWIT string `json:"baseline_file"`
	SalvarPrompts      *bool  `json:"save_prompts"`
	MaximoMetodos      int    `json:"max_methods"`
	ModeloJuiz         string `json:"judge_model"`
	ModoLLM            string `json:"llm_mode"`
	TamanhoSubconjunto int    `json:"deep_validation_subset_size"`
}

type configAplicacaoBruta struct {
	Versao   string                          `json:"version"`
	Projeto  dominio.ConfigProjeto           `json:"project"`
	Fluxo    configFluxoBruta                `json:"pipeline"`
	Modelos  map[string]dominio.ConfigModelo `json:"models"`
	Metricas []dominio.ConfigMetrica         `json:"metrics"`
}

// Carregar carrega, normaliza e valida a configuração JSON da aplicação.
//
// O runtime atual é restrito a Java, então os valores padrão apontam
// intencionalmente para o layout Maven/Gradle em src/main/java.
func Carregar(caminho string) (*dominio.ConfigAplicacao, error) {
	caminhoAbsoluto, err := filepath.Abs(caminho)
	if err != nil {
		return nil, fmt.Errorf("ao resolver o caminho da configuração: %w", err)
	}

	conteudo, err := os.ReadFile(caminhoAbsoluto)
	if err != nil {
		return nil, fmt.Errorf("ao ler a configuração %q: %w", caminhoAbsoluto, err)
	}

	cfg, err := interpretarConfiguracao(conteudo)
	if err != nil {
		return nil, fmt.Errorf("ao interpretar a configuração JSON %q: %w", caminhoAbsoluto, err)
	}
	cfg.CaminhoConfig = caminhoAbsoluto

	if err := aplicarPadroes(cfg); err != nil {
		return nil, err
	}
	if err := resolverCaminhos(cfg); err != nil {
		return nil, err
	}
	if err := validar(cfg); err != nil {
		return nil, err
	}
	return cfg, nil
}

// interpretarConfiguracao converte o JSON bruto no modelo de domínio.
func interpretarConfiguracao(conteudo []byte) (*dominio.ConfigAplicacao, error) {
	var bruto configAplicacaoBruta
	decoder := json.NewDecoder(bytes.NewReader(conteudo))
	decoder.DisallowUnknownFields()
	if err := decoder.Decode(&bruto); err != nil {
		return nil, err
	}

	cfg := &dominio.ConfigAplicacao{
		Versao:   bruto.Versao,
		Projeto:  bruto.Projeto,
		Modelos:  bruto.Modelos,
		Metricas: bruto.Metricas,
		Fluxo: dominio.ConfigFluxo{
			DiretorioSaida:     bruto.Fluxo.DiretorioSaida,
			CaminhoDuckDB:      bruto.Fluxo.CaminhoDuckDB,
			RaizReplicacaoWIT:  bruto.Fluxo.RaizReplicacaoWIT,
			ArquivoBaselineWIT: bruto.Fluxo.ArquivoBaselineWIT,
			MaximoMetodos:      bruto.Fluxo.MaximoMetodos,
			ModeloJuiz:         bruto.Fluxo.ModeloJuiz,
			ModoLLM:            bruto.Fluxo.ModoLLM,
			TamanhoSubconjunto: bruto.Fluxo.TamanhoSubconjunto,
		},
	}
	if bruto.Fluxo.SalvarPrompts == nil {
		cfg.Fluxo.SalvarPrompts = true
	} else {
		cfg.Fluxo.SalvarPrompts = *bruto.Fluxo.SalvarPrompts
	}

	return cfg, nil
}

// aplicarPadroes preenche valores padrão ausentes na configuração.
func aplicarPadroes(cfg *dominio.ConfigAplicacao) error {
	if strings.TrimSpace(cfg.Versao) == "" {
		cfg.Versao = "1"
	}
	if len(cfg.Projeto.Include) == 0 {
		cfg.Projeto.Include = []string{"src/main/java", "."}
	}
	if len(cfg.Projeto.Exclude) == 0 {
		cfg.Projeto.Exclude = append([]string{}, exclusoesPadrao...)
	}
	if cfg.Projeto.TestFramework == "" {
		cfg.Projeto.TestFramework = "infer"
	}
	if cfg.Fluxo.DiretorioSaida == "" {
		cfg.Fluxo.DiretorioSaida = "generated"
	}
	if cfg.Fluxo.CaminhoDuckDB == "" {
		cfg.Fluxo.CaminhoDuckDB = filepath.Join(cfg.Fluxo.DiretorioSaida, "witup-llm.duckdb")
	}
	if cfg.Fluxo.RaizReplicacaoWIT == "" {
		cfg.Fluxo.RaizReplicacaoWIT = filepath.Join("resources", "wit-replication-package", "data", "output")
	}
	if cfg.Fluxo.ArquivoBaselineWIT == "" {
		cfg.Fluxo.ArquivoBaselineWIT = "wit.json"
	}
	if strings.TrimSpace(cfg.Fluxo.ModoLLM) == "" {
		cfg.Fluxo.ModoLLM = string(dominio.ModoLLMMultiagente)
	}
	if cfg.Fluxo.TamanhoSubconjunto == 0 {
		cfg.Fluxo.TamanhoSubconjunto = 8
	}

	for chave, modelo := range cfg.Modelos {
		if modelo.SegundosTimeout == 0 {
			modelo.SegundosTimeout = 180
		}
		if modelo.Provedor == "openai_compatible" {
			modelo.EsforcoRaciocinio = normalizarEsforcoRaciocinio(modelo.EsforcoRaciocinio)
		}
		if strings.TrimSpace(modelo.RetencaoCachePrompt) == "" && modelo.Provedor == "openai_compatible" {
			modelo.RetencaoCachePrompt = "24h"
		}
		if strings.TrimSpace(modelo.NivelServico) == "" && modelo.Provedor == "openai_compatible" {
			modelo.NivelServico = "auto"
		}
		cfg.Modelos[chave] = modelo
	}
	for indice := range cfg.Metricas {
		if cfg.Metricas[indice].Tipo == "" {
			cfg.Metricas[indice].Tipo = cfg.Metricas[indice].Nome
		}
		if cfg.Metricas[indice].Peso == 0 {
			cfg.Metricas[indice].Peso = 1.0
		}
		if cfg.Metricas[indice].Escala == 0 {
			cfg.Metricas[indice].Escala = 100.0
		}
	}
	return nil
}

// resolverCaminhos converte caminhos relativos da configuração em caminhos absolutos.
func resolverCaminhos(cfg *dominio.ConfigAplicacao) error {
	diretorioBase := filepath.Dir(cfg.CaminhoConfig)
	if cfg.Projeto.Raiz == "" {
		cfg.Projeto.Raiz = "."
	}
	cfg.Projeto.Raiz = resolverCaminho(diretorioBase, cfg.Projeto.Raiz)
	cfg.Fluxo.DiretorioSaida = resolverCaminho(diretorioBase, cfg.Fluxo.DiretorioSaida)
	cfg.Fluxo.CaminhoDuckDB = resolverCaminho(diretorioBase, cfg.Fluxo.CaminhoDuckDB)
	cfg.Fluxo.RaizReplicacaoWIT = resolverCaminho(diretorioBase, cfg.Fluxo.RaizReplicacaoWIT)
	if strings.TrimSpace(cfg.Projeto.OverviewFile) != "" {
		cfg.Projeto.OverviewFile = resolverCaminho(diretorioBase, cfg.Projeto.OverviewFile)
	}
	return nil
}

// validar aplica validações estruturais e semânticas à configuração carregada.
func validar(cfg *dominio.ConfigAplicacao) error {
	if cfg.Versao != "1" {
		return fmt.Errorf("version %q não suportada; use \"1\"", cfg.Versao)
	}
	if len(cfg.Modelos) == 0 {
		return errors.New("a configuração deve declarar ao menos um modelo em \"models\"")
	}

	infoProjeto, err := os.Stat(cfg.Projeto.Raiz)
	if err != nil {
		return fmt.Errorf("raiz do projeto %q: %w", cfg.Projeto.Raiz, err)
	}
	if !infoProjeto.IsDir() {
		return fmt.Errorf("a raiz do projeto %q deve ser um diretório", cfg.Projeto.Raiz)
	}

	if cfg.Projeto.OverviewFile != "" {
		infoOverview, err := os.Stat(cfg.Projeto.OverviewFile)
		if err != nil {
			return fmt.Errorf("arquivo de visão geral %q: %w", cfg.Projeto.OverviewFile, err)
		}
		if infoOverview.IsDir() {
			return fmt.Errorf("o arquivo de visão geral %q deve ser um arquivo", cfg.Projeto.OverviewFile)
		}
	}

	if cfg.Fluxo.MaximoMetodos < 0 {
		return errors.New("pipeline.max_methods deve ser >= 0")
	}
	if cfg.Fluxo.TamanhoSubconjunto < 0 {
		return errors.New("pipeline.deep_validation_subset_size deve ser >= 0")
	}
	if strings.TrimSpace(cfg.Fluxo.CaminhoDuckDB) == "" {
		return errors.New("pipeline.duckdb_path é obrigatório")
	}
	if strings.TrimSpace(cfg.Fluxo.RaizReplicacaoWIT) == "" {
		return errors.New("pipeline.replication_root é obrigatório")
	}
	if strings.TrimSpace(cfg.Fluxo.ArquivoBaselineWIT) == "" {
		return errors.New("pipeline.baseline_file é obrigatório")
	}
	if cfg.Fluxo.ModeloJuiz != "" {
		if _, ok := cfg.Modelos[cfg.Fluxo.ModeloJuiz]; !ok {
			return fmt.Errorf("pipeline.judge_model referencia o modelo desconhecido %q", cfg.Fluxo.ModeloJuiz)
		}
	}
	switch dominio.ModoLLM(cfg.Fluxo.ModoLLM) {
	case dominio.ModoLLMDireto, dominio.ModoLLMMultiagente:
	default:
		return fmt.Errorf("pipeline.llm_mode=%q não suportado; use %q ou %q", cfg.Fluxo.ModoLLM, dominio.ModoLLMDireto, dominio.ModoLLMMultiagente)
	}

	for chave, modelo := range cfg.Modelos {
		if !provedoresSuportados[modelo.Provedor] {
			return fmt.Errorf("provedor %q não suportado para o modelo %q", modelo.Provedor, chave)
		}
		if strings.TrimSpace(modelo.Modelo) == "" {
			return fmt.Errorf("models.%s.model é obrigatório", chave)
		}
		if strings.TrimSpace(modelo.URLBase) == "" {
			return fmt.Errorf("models.%s.base_url é obrigatório", chave)
		}
		if modelo.SegundosTimeout <= 0 {
			return fmt.Errorf("models.%s.timeout_seconds deve ser > 0", chave)
		}
		if modelo.MaximoTentativas < 0 {
			return fmt.Errorf("models.%s.max_retries deve ser >= 0", chave)
		}
		if modelo.Temperature < 0 {
			return fmt.Errorf("models.%s.temperature deve ser >= 0", chave)
		}
		if modelo.MaximoTokensSaida < 0 {
			return fmt.Errorf("models.%s.max_output_tokens deve ser >= 0", chave)
		}
		if modelo.Provedor == "openai_compatible" {
			switch modelo.EsforcoRaciocinio {
			case "", "none", "low", "medium", "high", "xhigh":
			default:
				return fmt.Errorf("models.%s.reasoning_effort=%q não suportado; use none, low, medium, high ou xhigh", chave, modelo.EsforcoRaciocinio)
			}
		}
	}

	for indice, metrica := range cfg.Metricas {
		rotulo := fmt.Sprintf("metrics[%d]", indice)
		if strings.TrimSpace(metrica.Nome) == "" {
			return fmt.Errorf("%s.name é obrigatório", rotulo)
		}
		if strings.TrimSpace(metrica.Comando) == "" {
			return fmt.Errorf("%s.command é obrigatório", rotulo)
		}
		if metrica.Peso < 0 {
			return fmt.Errorf("%s.weight deve ser >= 0", rotulo)
		}
		if metrica.Escala < 0 {
			return fmt.Errorf("%s.scale deve ser >= 0", rotulo)
		}
	}

	return nil
}

// resolverCaminho resolve um caminho com base no diretório do arquivo de configuração.
func resolverCaminho(diretorioBase, candidato string) string {
	if filepath.IsAbs(candidato) {
		return filepath.Clean(candidato)
	}
	return filepath.Clean(filepath.Join(diretorioBase, candidato))
}

// normalizarEsforcoRaciocinio converte aliases legados para valores aceitos
// pela Responses API dos modelos GPT-5 atuais.
func normalizarEsforcoRaciocinio(valor string) string {
	esforco := strings.TrimSpace(strings.ToLower(valor))
	switch esforco {
	case "":
		return "low"
	case "minimal":
		return "low"
	default:
		return esforco
	}
}
