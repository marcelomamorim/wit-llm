package aplicacao

import (
	"context"
	"encoding/csv"
	"encoding/json"
	"fmt"
	"os"
	"os/exec"
	"path/filepath"
	"runtime"
	"sort"
	"strconv"
	"strings"
	"sync"
	"time"

	"github.com/marceloamorim/witup-llm/internal/artefatos"
	"github.com/marceloamorim/witup-llm/internal/dominio"
	"github.com/marceloamorim/witup-llm/internal/llm"
	"github.com/marceloamorim/witup-llm/internal/registro"
	"github.com/marceloamorim/witup-llm/internal/witup"
)

const (
	jdkGlobalProjectKey         = "jdk"
	jdkGlobalRepositoryURL      = "https://github.com/openjdk/jdk.git"
	jdkGlobalExperimentalUnit   = "global_project_impact"
	jdkGlobalPreparationFile    = "preparation_jdk_global_impact.json"
	jdkGlobalAnalysisFile       = "analysis_jdk_wit_filtered_sample.json"
	jdkGlobalManifestFile       = "manifest_jdk_global_methods.csv"
	jdkGlobalDefaultMethodCount = 30
)

var jdkGlobalExcludes = []string{
	".git",
	".gradle",
	"build",
	"builds",
	"bundles",
	"generated",
	"images",
	"support",
	"test-results",
}

type metadadosWITJDK struct {
	Path       string `json:"path"`
	HashCommit string `json:"commitHash"`
}

// PrepararEstudoJDKGlobal prepara a amostra, o relatório WIT filtrado e o JSONL
// Batch para o estudo de impacto global no OpenJDK.
func (s *Servico) PrepararEstudoJDKGlobal(cfg *dominio.ConfigAplicacao, generationModelKey, jdkRoot, witPath, outputDir, requestsPath string, methodCount, workers int) (dominio.RelatorioPreparacaoJDKGlobal, string, error) {
	if methodCount <= 0 {
		methodCount = jdkGlobalDefaultMethodCount
	}
	if workers <= 0 {
		workers = runtime.NumCPU()
	}
	if strings.TrimSpace(outputDir) == "" {
		return dominio.RelatorioPreparacaoJDKGlobal{}, "", fmt.Errorf("outputDir é obrigatório")
	}
	if strings.TrimSpace(requestsPath) == "" {
		requestsPath = filepath.Join(outputDir, "requests_openai_batch_generation.jsonl")
	}
	model, err := getModelOrError(cfg, generationModelKey)
	if err != nil {
		return dominio.RelatorioPreparacaoJDKGlobal{}, "", err
	}
	if info, err := os.Stat(jdkRoot); err != nil || !info.IsDir() {
		return dominio.RelatorioPreparacaoJDKGlobal{}, "", fmt.Errorf("raiz JDK inválida: %s", jdkRoot)
	}
	if info, err := os.Stat(witPath); err != nil || info.IsDir() {
		return dominio.RelatorioPreparacaoJDKGlobal{}, "", fmt.Errorf("baseline WIT filtrado inválido: %s", witPath)
	}
	if err := os.MkdirAll(outputDir, 0o755); err != nil {
		return dominio.RelatorioPreparacaoJDKGlobal{}, "", err
	}

	ctxHeartbeat, cancelCtxHeartbeat := context.WithCancel(context.Background())
	progressoHeartbeat := registro.NovoProgresso(4)
	cancelHeartbeat := registro.IniciarHeartbeat(ctxHeartbeat, "jdk-global", "prepare", jdkGlobalProjectKey, "running", progressoHeartbeat)
	defer cancelHeartbeat()
	defer cancelCtxHeartbeat()

	cfgJDK := clonarConfiguracaoJDKGlobal(cfg, jdkRoot, methodCount)
	registro.Info("jdk-global", "catalogando checkout JDK root=%s workers=%d", jdkRoot, workers)
	metodosCatalogados, err := s.catalogFactory.NovoCatalogo(cfgJDK.Projeto).Catalogar()
	if err != nil {
		return dominio.RelatorioPreparacaoJDKGlobal{}, "", err
	}
	progressoHeartbeat.Incrementar()

	baselineReport, err := witup.CarregarAnalise(witPath)
	if err != nil {
		return dominio.RelatorioPreparacaoJDKGlobal{}, "", err
	}
	baselineAlinhada, metodosAlvo, resumo := alinharWITUPAoCatalogo(baselineReport, metodosCatalogados, methodCount)
	if len(metodosAlvo) == 0 {
		return dominio.RelatorioPreparacaoJDKGlobal{}, "", fmt.Errorf("nenhum método do WIT filtrado alinhou ao checkout JDK atual")
	}
	registro.Info("jdk-global", "alinhamento WIT concluído baseline=%d alinhados=%d não_encontrados=%d", resumo.QuantidadeBaseline, resumo.QuantidadeCorrespondidos, resumo.QuantidadeNaoEncontrados)
	progressoHeartbeat.Incrementar()

	analysisPath := filepath.Join(outputDir, jdkGlobalAnalysisFile)
	if err := artefatos.EscreverJSON(analysisPath, baselineAlinhada); err != nil {
		return dominio.RelatorioPreparacaoJDKGlobal{}, "", err
	}
	overview := overviewJDKGlobal()
	linhas, err := construirLinhasBatchJDKGlobalParalelo(cfgJDK, model, generationModelKey, overview, baselineAlinhada, metodosAlvo, workers)
	if err != nil {
		return dominio.RelatorioPreparacaoJDKGlobal{}, "", err
	}
	if err := llm.EscreverJSONLBatch(requestsPath, linhas); err != nil {
		return dominio.RelatorioPreparacaoJDKGlobal{}, "", err
	}
	progressoHeartbeat.Incrementar()

	manifestPath := filepath.Join(outputDir, jdkGlobalManifestFile)
	metodos := metodosJDKGlobal(baselineAlinhada)
	if err := escreverManifestJDKGlobal(manifestPath, metodos); err != nil {
		return dominio.RelatorioPreparacaoJDKGlobal{}, "", err
	}
	metadata, err := carregarMetadadosWITJDK(witPath)
	if err != nil {
		return dominio.RelatorioPreparacaoJDKGlobal{}, "", err
	}
	report := dominio.RelatorioPreparacaoJDKGlobal{
		IDExecucao:              filepath.Base(outputDir),
		GeradoEm:                dominio.HorarioUTC(),
		Projeto:                 jdkGlobalProjectKey,
		URLRepositorio:          jdkGlobalRepositoryURL,
		CommitWIT:               metadata.HashCommit,
		RaizJDK:                 jdkRoot,
		CaminhoWIT:              witPath,
		UnidadeExperimental:     jdkGlobalExperimentalUnit,
		AnaliseMetodoSecundaria: true,
		QuantidadeMetodos:       len(metodos),
		QuantidadeExpaths:       contarCaminhosAnalises(baselineAlinhada.Analises),
		QuantidadeRequests:      len(linhas),
		ChaveModeloGeracao:      generationModelKey,
		CaminhoAnalise:          analysisPath,
		CaminhoManifestCSV:      manifestPath,
		CaminhoRequestsJSONL:    requestsPath,
		Metodos:                 metodos,
	}
	reportPath := filepath.Join(outputDir, jdkGlobalPreparationFile)
	if err := artefatos.EscreverJSON(reportPath, report); err != nil {
		return dominio.RelatorioPreparacaoJDKGlobal{}, "", err
	}
	progressoHeartbeat.Incrementar()
	registro.Info("jdk-global", "preparação concluída métodos=%d expaths=%d requests=%d artefato=%s", report.QuantidadeMetodos, report.QuantidadeExpaths, report.QuantidadeRequests, reportPath)
	return report, reportPath, nil
}

// AvaliarEstudoJDKGlobal materializa respostas Batch em três variantes do JDK
// e executa métricas globais sobre cada variante.
func (s *Servico) AvaliarEstudoJDKGlobal(cfg *dominio.ConfigAplicacao, generationModelKey, jdkRoot, runDir, responsesPath, errorsPath string, workers int) (dominio.RelatorioJDKGlobal, string, error) {
	if workers <= 0 {
		workers = runtime.NumCPU()
	}
	preparationPath := filepath.Join(runDir, jdkGlobalPreparationFile)
	var preparation dominio.RelatorioPreparacaoJDKGlobal
	if err := artefatos.LerJSON(preparationPath, &preparation); err != nil {
		return dominio.RelatorioJDKGlobal{}, "", err
	}
	var analysisReport dominio.RelatorioAnalise
	if err := artefatos.LerJSON(preparation.CaminhoAnalise, &analysisReport); err != nil {
		return dominio.RelatorioJDKGlobal{}, "", err
	}
	resultadosBatch, err := llm.LerResultadosBatch(responsesPath)
	if err != nil {
		return dominio.RelatorioJDKGlobal{}, "", err
	}
	model, err := getModelOrError(cfg, generationModelKey)
	if err != nil {
		return dominio.RelatorioJDKGlobal{}, "", err
	}
	cfgJDK := clonarConfiguracaoJDKGlobal(cfg, jdkRoot, preparation.QuantidadeMetodos)
	metodosAlvo := extrairMetodosDasAnalises(analysisReport.Analises)
	overview := overviewJDKGlobal()

	ctxHeartbeat, cancelCtxHeartbeat := context.WithCancel(context.Background())
	progressoHeartbeat := registro.NovoProgresso(3)
	cancelHeartbeat := registro.IniciarHeartbeat(ctxHeartbeat, "jdk-global", "evaluate", jdkGlobalProjectKey, "running", progressoHeartbeat)
	defer cancelHeartbeat()
	defer cancelCtxHeartbeat()

	witWorkspace, err := artefatos.NovoEspacoTrabalho(filepath.Join(runDir, "materialized"), artefatos.NovoIDExecucao("wit-context-batch"))
	if err != nil {
		return dominio.RelatorioJDKGlobal{}, "", err
	}
	witGeneration, witGenerationPath, err := materializarGeracaoBatchJDKGlobalWITPorMetodo(cfgJDK, model, generationModelKey, overview, analysisReport, preparation.CaminhoAnalise, resultadosBatch, witWorkspace)
	if err != nil {
		return dominio.RelatorioJDKGlobal{}, "", err
	}
	directWorkspace, err := artefatos.NovoEspacoTrabalho(filepath.Join(runDir, "materialized"), artefatos.NovoIDExecucao("direct-tests-batch"))
	if err != nil {
		return dominio.RelatorioJDKGlobal{}, "", err
	}
	directGeneration, directGenerationPath, err := materializarGeracaoBatchJDKGlobalDiretaPorMetodo(cfgJDK, model, generationModelKey, overview, metodosAlvo, preparation.CaminhoAnalise, resultadosBatch, directWorkspace)
	if err != nil {
		return dominio.RelatorioJDKGlobal{}, "", err
	}
	progressoHeartbeat.Incrementar()

	variantes, err := materializarVariantesJDKGlobal(jdkRoot, runDir, witGeneration, directGeneration, workers)
	if err != nil {
		return dominio.RelatorioJDKGlobal{}, "", err
	}
	variantes[1].CaminhoGeracao = witGenerationPath
	variantes[1].QuantidadeTestes = len(witGeneration.ArquivosTeste)
	variantes[1].InputTokens = witGeneration.InputTokens
	variantes[1].OutputTokens = witGeneration.OutputTokens
	variantes[1].EstimatedCost = witGeneration.EstimatedCost
	witUsado, witAdaptado, witDescartado, witTotal, witTaxa := agregarExpathsGeracao(witGeneration.ArquivosTeste)
	variantes[1].ExpathUsado = witUsado
	variantes[1].ExpathAdaptado = witAdaptado
	variantes[1].ExpathDescartado = witDescartado
	variantes[1].ExpathTotal = witTotal
	variantes[1].TaxaUtilizacaoExpath = witTaxa
	variantes[2].CaminhoGeracao = directGenerationPath
	variantes[2].QuantidadeTestes = len(directGeneration.ArquivosTeste)
	variantes[2].InputTokens = directGeneration.InputTokens
	variantes[2].OutputTokens = directGeneration.OutputTokens
	variantes[2].EstimatedCost = directGeneration.EstimatedCost
	progressoHeartbeat.Incrementar()

	variantes = executarMetricasGlobaisJDK(varientesOrdenadasJDKGlobal(variantes), workers)
	progressoHeartbeat.Incrementar()

	if strings.TrimSpace(errorsPath) != "" {
		if _, err := os.Stat(errorsPath); err == nil {
			_ = copiarArquivo(errorsPath, filepath.Join(runDir, "errors_openai_batch_generation.jsonl"))
		}
	}
	report := dominio.RelatorioJDKGlobal{
		IDExecucao:              filepath.Base(runDir),
		GeradoEm:                dominio.HorarioUTC(),
		Projeto:                 jdkGlobalProjectKey,
		URLRepositorio:          jdkGlobalRepositoryURL,
		CommitWIT:               preparation.CommitWIT,
		UnidadeExperimental:     jdkGlobalExperimentalUnit,
		AnaliseMetodoSecundaria: true,
		CaminhoPreparacao:       preparationPath,
		CaminhoManifestCSV:      preparation.CaminhoManifestCSV,
		CaminhoResumoCSV:        filepath.Join(runDir, "results_jdk_global_impact_summary.csv"),
		CaminhoComparacaoCSV:    filepath.Join(runDir, "results_jdk_global_impact_comparison.csv"),
		CaminhoStatsGeracaoCSV:  filepath.Join(runDir, "results_jdk_global_generation_stats.csv"),
		Variantes:               variantes,
	}
	reportPath := filepath.Join(runDir, "results_jdk_global_impact.json")
	if err := artefatos.EscreverJSON(reportPath, report); err != nil {
		return dominio.RelatorioJDKGlobal{}, "", err
	}
	if err := escreverResumoJDKGlobalCSV(report.CaminhoResumoCSV, report); err != nil {
		return dominio.RelatorioJDKGlobal{}, "", err
	}
	if err := escreverComparacaoJDKGlobalCSV(report.CaminhoComparacaoCSV, report); err != nil {
		return dominio.RelatorioJDKGlobal{}, "", err
	}
	if err := escreverStatsGeracaoJDKGlobalCSV(report.CaminhoStatsGeracaoCSV, report); err != nil {
		return dominio.RelatorioJDKGlobal{}, "", err
	}
	registro.Info("jdk-global", "avaliação global concluída artefato=%s stats=%s", reportPath, report.CaminhoStatsGeracaoCSV)
	return report, reportPath, nil
}

func clonarConfiguracaoJDKGlobal(cfg *dominio.ConfigAplicacao, jdkRoot string, methodCount int) *dominio.ConfigAplicacao {
	clone := *cfg
	clone.Projeto = dominio.ConfigProjeto{
		Raiz:          jdkRoot,
		Include:       []string{"src"},
		Exclude:       append([]string{}, jdkGlobalExcludes...),
		TestFramework: frameworkJTReg,
	}
	clone.Fluxo.MaximoMetodos = methodCount
	clone.Fluxo.SalvarPrompts = true
	return &clone
}

func overviewJDKGlobal() string {
	return "OpenJDK JDK. Projeto Java modular de grande porte. Gere testes jtreg pequenos, determinísticos e focados em comportamento observável. A unidade experimental principal deste estudo é o impacto global dos testes adicionados à suíte do projeto."
}

func carregarMetadadosWITJDK(path string) (metadadosWITJDK, error) {
	var metadata metadadosWITJDK
	if err := artefatos.LerJSON(path, &metadata); err != nil {
		return metadata, err
	}
	return metadata, nil
}

func metodosJDKGlobal(analysis dominio.RelatorioAnalise) []dominio.MetodoJDKGlobal {
	metodos := make([]dominio.MetodoJDKGlobal, 0, len(analysis.Analises))
	for i, analise := range analysis.Analises {
		metodos = append(metodos, dominio.MetodoJDKGlobal{
			Indice:            i + 1,
			IDMetodo:          analise.Metodo.IDMetodo,
			CaminhoArquivo:    analise.Metodo.CaminhoArquivo,
			NomeContainer:     analise.Metodo.NomeContainer,
			NomeMetodo:        analise.Metodo.NomeMetodo,
			Assinatura:        analise.Metodo.Assinatura,
			QuantidadeExpaths: len(analise.CaminhosExcecao),
		})
	}
	return metodos
}

func escreverManifestJDKGlobal(path string, metodos []dominio.MetodoJDKGlobal) error {
	if err := os.MkdirAll(filepath.Dir(path), 0o755); err != nil {
		return err
	}
	file, err := os.Create(path)
	if err != nil {
		return err
	}
	defer file.Close()
	writer := csv.NewWriter(file)
	defer writer.Flush()
	if err := writer.Write([]string{"index", "project", "method_id", "file_path", "container_name", "method_name", "signature", "expath_count"}); err != nil {
		return err
	}
	for _, metodo := range metodos {
		if err := writer.Write([]string{
			strconv.Itoa(metodo.Indice),
			jdkGlobalProjectKey,
			metodo.IDMetodo,
			metodo.CaminhoArquivo,
			metodo.NomeContainer,
			metodo.NomeMetodo,
			metodo.Assinatura,
			strconv.Itoa(metodo.QuantidadeExpaths),
		}); err != nil {
			return err
		}
	}
	return writer.Error()
}

func construirLinhasBatchJDKGlobalParalelo(cfg *dominio.ConfigAplicacao, model dominio.ConfigModelo, generationModelKey, overview string, analysis dominio.RelatorioAnalise, metodos []dominio.DescritorMetodo, workers int) ([]llm.LinhaRequisicaoBatch, error) {
	type resultado struct {
		linhas []llm.LinhaRequisicaoBatch
		err    error
	}
	jobs := []func() ([]llm.LinhaRequisicaoBatch, error){
		func() ([]llm.LinhaRequisicaoBatch, error) {
			return construirLinhasBatchJDKGlobalWITPorMetodo(cfg, model, overview, analysis)
		},
		func() ([]llm.LinhaRequisicaoBatch, error) {
			return construirLinhasBatchJDKGlobalDiretasPorMetodo(cfg, model, overview, metodos)
		},
	}
	if workers <= 0 {
		workers = 1
	}
	if workers > len(jobs) {
		workers = len(jobs)
	}
	entrada := make(chan func() ([]llm.LinhaRequisicaoBatch, error))
	saida := make(chan resultado, len(jobs))
	var wg sync.WaitGroup
	for i := 0; i < workers; i++ {
		wg.Add(1)
		go func() {
			defer wg.Done()
			for job := range entrada {
				linhas, err := job()
				saida <- resultado{linhas: linhas, err: err}
			}
		}()
	}
	for _, job := range jobs {
		entrada <- job
	}
	close(entrada)
	wg.Wait()
	close(saida)
	todas := []llm.LinhaRequisicaoBatch{}
	for item := range saida {
		if item.err != nil {
			return nil, item.err
		}
		todas = append(todas, item.linhas...)
	}
	sort.Slice(todas, func(i, j int) bool {
		return todas[i].CustomID < todas[j].CustomID
	})
	_ = generationModelKey
	return todas, nil
}

func construirLinhasBatchJDKGlobalWITPorMetodo(cfg *dominio.ConfigAplicacao, model dominio.ConfigModelo, overview string, analysis dominio.RelatorioAnalise) ([]llm.LinhaRequisicaoBatch, error) {
	analysis = filtrarAnalisesParte2(analysis)
	analises := append([]dominio.AnaliseMetodo{}, analysis.Analises...)
	sort.Slice(analises, func(i, j int) bool {
		return analises[i].Metodo.IDMetodo < analises[j].Metodo.IDMetodo
	})
	linhas := make([]llm.LinhaRequisicaoBatch, 0, len(analises))
	for _, analise := range analises {
		containerName := analise.Metodo.NomeContainer
		methodsPayload := []dominio.AnaliseMetodo{analise}
		contextoComum := construirContextoGeracaoTestes(cfg.Projeto, containerName, extrairMetodosDasAnalises(methodsPayload))
		linha, err := llm.ConstruirLinhaBatchResponses(
			customIDJDKGlobalPorMetodo("wit-context", analise.Metodo),
			model,
			construirPromptGeracaoSistema(resolverFrameworkTestes(cfg.Projeto)),
			construirPromptGeracaoUsuario(overview, containerName, methodsPayload, contextoComum),
			dominio.OpcoesRequisicaoLLM{PromptCacheKey: construirPromptCacheKey(identificarProjeto(cfg), "generation", analise.Metodo.IDMetodo)},
		)
		if err != nil {
			return nil, err
		}
		linhas = append(linhas, linha)
	}
	return linhas, nil
}

func construirLinhasBatchJDKGlobalDiretasPorMetodo(cfg *dominio.ConfigAplicacao, model dominio.ConfigModelo, overview string, metodos []dominio.DescritorMetodo) ([]llm.LinhaRequisicaoBatch, error) {
	ordenados := append([]dominio.DescritorMetodo{}, metodos...)
	sort.Slice(ordenados, func(i, j int) bool {
		return ordenados[i].IDMetodo < ordenados[j].IDMetodo
	})
	linhas := make([]llm.LinhaRequisicaoBatch, 0, len(ordenados))
	for _, metodo := range ordenados {
		containerName := metodo.NomeContainer
		methodsPayload := []dominio.DescritorMetodo{metodo}
		contextoComum := construirContextoGeracaoTestes(cfg.Projeto, containerName, methodsPayload)
		linha, err := llm.ConstruirLinhaBatchResponses(
			customIDJDKGlobalPorMetodo("direct-tests", metodo),
			model,
			construirPromptGeracaoDiretaSistema(resolverFrameworkTestes(cfg.Projeto)),
			construirPromptGeracaoDiretaUsuario(overview, containerName, methodsPayload, contextoComum),
			dominio.OpcoesRequisicaoLLM{PromptCacheKey: construirPromptCacheKey(identificarProjeto(cfg), "direct-generation", metodo.IDMetodo)},
		)
		if err != nil {
			return nil, err
		}
		linhas = append(linhas, linha)
	}
	return linhas, nil
}

func customIDJDKGlobalPorMetodo(cenario string, metodo dominio.DescritorMetodo) string {
	return fmt.Sprintf("%s/%s/%s/%s", artefatos.Slugificar(jdkGlobalProjectKey), cenario, artefatos.Slugificar(metodo.NomeContainer), artefatos.Slugificar(metodo.IDMetodo))
}

func materializarGeracaoBatchJDKGlobalWITPorMetodo(cfg *dominio.ConfigAplicacao, model dominio.ConfigModelo, generationModelKey, overview string, analysisReport dominio.RelatorioAnalise, analysisPath string, resultadosBatch map[string]llm.LinhaResultadoBatch, workspace *artefatos.EspacoTrabalho) (dominio.RelatorioGeracao, string, error) {
	analysisReport = filtrarAnalisesParte2(analysisReport)
	porMetodo := map[string]dominio.AnaliseMetodo{}
	keys := make([]string, 0, len(analysisReport.Analises))
	for _, analise := range analysisReport.Analises {
		key := analise.Metodo.IDMetodo
		porMetodo[key] = analise
		keys = append(keys, key)
	}
	sort.Strings(keys)
	return materializarGeracaoBatch(workspace, cfg.Projeto.Raiz, generationModelKey, analysisPath, keys, func(string) int {
		return 1
	}, func(key string, _ int) string {
		return customIDJDKGlobalPorMetodo("wit-context", porMetodo[key].Metodo)
	}, func(key string, _ int) (string, string) {
		analise := porMetodo[key]
		lote := []dominio.AnaliseMetodo{analise}
		contextoComum := construirContextoGeracaoTestes(cfg.Projeto, analise.Metodo.NomeContainer, extrairMetodosDasAnalises(lote))
		return construirPromptGeracaoUsuario(overview, analise.Metodo.NomeContainer, lote, contextoComum), construirPromptGeracaoSistema(resolverFrameworkTestes(cfg.Projeto))
	}, resultadosBatch, model)
}

func materializarGeracaoBatchJDKGlobalDiretaPorMetodo(cfg *dominio.ConfigAplicacao, model dominio.ConfigModelo, generationModelKey, overview string, metodos []dominio.DescritorMetodo, analysisPath string, resultadosBatch map[string]llm.LinhaResultadoBatch, workspace *artefatos.EspacoTrabalho) (dominio.RelatorioGeracao, string, error) {
	porMetodo := map[string]dominio.DescritorMetodo{}
	keys := make([]string, 0, len(metodos))
	for _, metodo := range metodos {
		key := metodo.IDMetodo
		porMetodo[key] = metodo
		keys = append(keys, key)
	}
	sort.Strings(keys)
	return materializarGeracaoBatch(workspace, cfg.Projeto.Raiz, generationModelKey, analysisPath, keys, func(string) int {
		return 1
	}, func(key string, _ int) string {
		return customIDJDKGlobalPorMetodo("direct-tests", porMetodo[key])
	}, func(key string, _ int) (string, string) {
		metodo := porMetodo[key]
		lote := []dominio.DescritorMetodo{metodo}
		contextoComum := construirContextoGeracaoTestes(cfg.Projeto, metodo.NomeContainer, lote)
		return construirPromptGeracaoDiretaUsuario(overview, metodo.NomeContainer, lote, contextoComum), construirPromptGeracaoDiretaSistema(resolverFrameworkTestes(cfg.Projeto))
	}, resultadosBatch, model)
}

func materializarVariantesJDKGlobal(jdkRoot, runDir string, witGeneration, directGeneration dominio.RelatorioGeracao, workers int) ([]dominio.ResultadoVarianteJDKGlobal, error) {
	variantsRoot := filepath.Join(runDir, "variants")
	if err := os.MkdirAll(variantsRoot, 0o755); err != nil {
		return nil, err
	}
	variantes := []dominio.ResultadoVarianteJDKGlobal{
		{Nome: "baseline", Cenario: "BASELINE", RaizProjeto: filepath.Join(variantsRoot, "baseline")},
		{Nome: "wit-context", Cenario: string(dominio.CenarioSegundaFaseContextoWIT), RaizProjeto: filepath.Join(variantsRoot, "wit-context")},
		{Nome: "direct-tests", Cenario: string(dominio.CenarioSegundaFaseDireto), RaizProjeto: filepath.Join(variantsRoot, "direct-tests")},
	}
	if workers <= 0 {
		workers = 1
	}
	if workers > len(variantes) {
		workers = len(variantes)
	}
	jobs := make(chan int)
	errs := make(chan error, len(variantes))
	var wg sync.WaitGroup
	for i := 0; i < workers; i++ {
		wg.Add(1)
		go func() {
			defer wg.Done()
			for indice := range jobs {
				variante := variantes[indice]
				if err := os.RemoveAll(variante.RaizProjeto); err != nil {
					errs <- err
					continue
				}
				registro.Info("jdk-global", "materializando variante=%s raiz=%s", variante.Nome, variante.RaizProjeto)
				if err := artefatos.CopiarDiretorioFiltrado(jdkRoot, variante.RaizProjeto, jdkGlobalExcludes); err != nil {
					errs <- err
					continue
				}
				switch variante.Cenario {
				case string(dominio.CenarioSegundaFaseContextoWIT):
					errs <- injetarTestesGeradosJDKGlobal(variante.RaizProjeto, witGeneration)
				case string(dominio.CenarioSegundaFaseDireto):
					errs <- injetarTestesGeradosJDKGlobal(variante.RaizProjeto, directGeneration)
				default:
					errs <- nil
				}
			}
		}()
	}
	for i := range variantes {
		jobs <- i
	}
	close(jobs)
	wg.Wait()
	close(errs)
	for err := range errs {
		if err != nil {
			return nil, err
		}
	}
	return variantes, nil
}

func injetarTestesGeradosJDKGlobal(raiz string, generation dominio.RelatorioGeracao) error {
	for _, arquivo := range generation.ArquivosTeste {
		rel, err := artefatos.CaminhoRelativoSeguro(arquivo.CaminhoRelativo)
		if err != nil {
			return err
		}
		if !strings.HasPrefix(filepath.ToSlash(rel), "test/") {
			rel = filepath.ToSlash(filepath.Join("test", "jdk", "witup", "generated", rel))
		}
		if err := artefatos.EscreverTexto(filepath.Join(raiz, rel), arquivo.Conteudo); err != nil {
			return err
		}
	}
	return nil
}

func varientesOrdenadasJDKGlobal(variantes []dominio.ResultadoVarianteJDKGlobal) []dominio.ResultadoVarianteJDKGlobal {
	sort.SliceStable(variantes, func(i, j int) bool {
		return variantes[i].Nome < variantes[j].Nome
	})
	return variantes
}

func executarMetricasGlobaisJDK(variantes []dominio.ResultadoVarianteJDKGlobal, workers int) []dominio.ResultadoVarianteJDKGlobal {
	if workers <= 0 {
		workers = 1
	}
	if workers > len(variantes) {
		workers = len(variantes)
	}
	jobs := make(chan int)
	var wg sync.WaitGroup
	for i := 0; i < workers; i++ {
		wg.Add(1)
		go func() {
			defer wg.Done()
			for indice := range jobs {
				variantes[indice].ResultadosMetricas = executarMetricasVarianteJDK(variantes[indice].RaizProjeto)
			}
		}()
	}
	for i := range variantes {
		jobs <- i
	}
	close(jobs)
	wg.Wait()
	return variantes
}

func executarMetricasVarianteJDK(root string) []dominio.ResultadoMetricaGlobalJDK {
	metrics := []struct {
		name    string
		command string
		timeout int
	}{
		{"java_source_file_count", "find . -path './.git' -prune -o -path './build' -prune -o -name '*.java' -type f -print | wc -l", 120},
		{"test_source_file_count", "find test -name '*.java' -type f -print 2>/dev/null | wc -l", 120},
		{"generated_test_file_count", "find test/jdk/witup/generated -name '*.java' -type f -print 2>/dev/null | wc -l", 120},
		{"jdk_build", strings.TrimSpace(os.Getenv("JDK_GLOBAL_BUILD_COMMAND")), timeoutGlobalJDK()},
		{"jdk_tests", strings.TrimSpace(os.Getenv("JDK_GLOBAL_TEST_COMMAND")), timeoutGlobalJDK()},
		{"jdk_coverage", strings.TrimSpace(os.Getenv("JDK_GLOBAL_COVERAGE_COMMAND")), timeoutGlobalJDK()},
		{"jdk_mutation", strings.TrimSpace(os.Getenv("JDK_GLOBAL_MUTATION_COMMAND")), timeoutGlobalJDK()},
	}
	resultados := make([]dominio.ResultadoMetricaGlobalJDK, 0, len(metrics))
	for _, metric := range metrics {
		resultados = append(resultados, executarComandoMetricaGlobalJDK(root, metric.name, metric.command, metric.timeout))
	}
	return resultados
}

func timeoutGlobalJDK() int {
	valor := strings.TrimSpace(os.Getenv("JDK_GLOBAL_METRIC_TIMEOUT_SECONDS"))
	if valor == "" {
		return 7200
	}
	segundos, err := strconv.Atoi(valor)
	if err != nil || segundos <= 0 {
		return 7200
	}
	return segundos
}

func executarComandoMetricaGlobalJDK(root, name, command string, timeoutSeconds int) dominio.ResultadoMetricaGlobalJDK {
	resultado := dominio.ResultadoMetricaGlobalJDK{
		Nome:            name,
		Comando:         command,
		Status:          "skipped",
		TimeoutSegundos: timeoutSeconds,
	}
	if strings.TrimSpace(command) == "" {
		return resultado
	}
	inicio := time.Now()
	ctx, cancel := context.WithTimeout(context.Background(), time.Duration(timeoutSeconds)*time.Second)
	defer cancel()
	progressoHeartbeat := registro.NovoProgresso(1)
	cancelHeartbeat := registro.IniciarHeartbeat(ctx, "jdk-global", "global_metric", name, "running", progressoHeartbeat)
	defer cancelHeartbeat()
	cmd := exec.CommandContext(ctx, "sh", "-c", command)
	cmd.Dir = root
	out, err := cmd.CombinedOutput()
	progressoHeartbeat.Incrementar()
	resultado.DuracaoMillis = time.Since(inicio).Milliseconds()
	resultado.SaidaPadrao = string(out)
	if err != nil {
		resultado.Status = "failed"
		resultado.CodigoSaida = 1
		if exit, ok := err.(*exec.ExitError); ok {
			resultado.CodigoSaida = exit.ExitCode()
		}
		resultado.SaidaErro = err.Error()
		if ctx.Err() == context.DeadlineExceeded {
			resultado.Status = "timeout"
		}
		return resultado
	}
	resultado.Status = "ok"
	return resultado
}

func escreverResumoJDKGlobalCSV(path string, report dominio.RelatorioJDKGlobal) error {
	if err := os.MkdirAll(filepath.Dir(path), 0o755); err != nil {
		return err
	}
	file, err := os.Create(path)
	if err != nil {
		return err
	}
	defer file.Close()
	writer := csv.NewWriter(file)
	defer writer.Flush()
	if err := writer.Write([]string{"variant", "scenario", "metric", "status", "exit_code", "duration_ms", "project_root"}); err != nil {
		return err
	}
	for _, variante := range report.Variantes {
		for _, metric := range variante.ResultadosMetricas {
			if err := writer.Write([]string{
				variante.Nome,
				variante.Cenario,
				metric.Nome,
				metric.Status,
				strconv.Itoa(metric.CodigoSaida),
				strconv.FormatInt(metric.DuracaoMillis, 10),
				variante.RaizProjeto,
			}); err != nil {
				return err
			}
		}
	}
	return writer.Error()
}

func escreverComparacaoJDKGlobalCSV(path string, report dominio.RelatorioJDKGlobal) error {
	if err := os.MkdirAll(filepath.Dir(path), 0o755); err != nil {
		return err
	}
	file, err := os.Create(path)
	if err != nil {
		return err
	}
	defer file.Close()
	writer := csv.NewWriter(file)
	defer writer.Flush()
	header := []string{
		"metric",
		"baseline", "wit_context", "direct_tests",
		"delta_wit_minus_baseline", "delta_direct_minus_baseline", "delta_wit_minus_direct",
	}
	if err := writer.Write(header); err != nil {
		return err
	}

	porVariante := map[string]map[string]*float64{}
	porVarianteInt := map[string]map[string]int{}
	witTaxa := (*float64)(nil)
	for _, variante := range report.Variantes {
		metricasVariante := map[string]*float64{}
		for _, metric := range variante.ResultadosMetricas {
			metricasVariante[metric.Nome] = primeiroNumeroMetricaGlobal(metric.SaidaPadrao)
		}
		porVariante[variante.Nome] = metricasVariante
		porVarianteInt[variante.Nome] = map[string]int{
			"generated_test_file_count": variante.QuantidadeTestes,
			"input_tokens":              variante.InputTokens,
			"output_tokens":             variante.OutputTokens,
		}
		if variante.Nome == "wit-context" {
			witTaxa = variante.TaxaUtilizacaoExpath
		}
	}

	// Linhas de métricas externas (jtreg, cobertura, etc.)
	// Exclui métricas já cobertas pela seção de stats de geração abaixo.
	statsGeracaoKeys := map[string]bool{"generated_test_file_count": true, "input_tokens": true, "output_tokens": true}
	nomes := map[string]bool{}
	for _, metricasVariante := range porVariante {
		for nome := range metricasVariante {
			if !statsGeracaoKeys[nome] {
				nomes[nome] = true
			}
		}
	}
	ordenados := make([]string, 0, len(nomes))
	for nome := range nomes {
		ordenados = append(ordenados, nome)
	}
	sort.Strings(ordenados)
	for _, nome := range ordenados {
		base := valorMetricaVariante(porVariante, "baseline", nome)
		wit := valorMetricaVariante(porVariante, "wit-context", nome)
		direct := valorMetricaVariante(porVariante, "direct-tests", nome)
		if err := writer.Write([]string{
			nome,
			formatarFloatOpcional(base),
			formatarFloatOpcional(wit),
			formatarFloatOpcional(direct),
			formatarDeltaOpcional(wit, base),
			formatarDeltaOpcional(direct, base),
			formatarDeltaOpcional(wit, direct),
		}); err != nil {
			return err
		}
	}

	// Linhas de stats de geração (test_count, tokens)
	for _, chave := range []string{"generated_test_file_count", "input_tokens", "output_tokens"} {
		baseV := intParaFloatPtr(porVarianteInt["baseline"][chave])
		witV := intParaFloatPtr(porVarianteInt["wit-context"][chave])
		directV := intParaFloatPtr(porVarianteInt["direct-tests"][chave])
		if err := writer.Write([]string{
			chave,
			formatarFloatOpcional(baseV),
			formatarFloatOpcional(witV),
			formatarFloatOpcional(directV),
			formatarDeltaOpcional(witV, baseV),
			formatarDeltaOpcional(directV, baseV),
			formatarDeltaOpcional(witV, directV),
		}); err != nil {
			return err
		}
	}

	// Linha de taxa de utilização de expaths (só WIT_CONTEXT tem valor)
	if err := writer.Write([]string{
		"expath_utilization_rate",
		"",
		formatarFloatOpcional(witTaxa),
		"",
		"", "", "",
	}); err != nil {
		return err
	}

	return writer.Error()
}

func valorMetricaVariante(valores map[string]map[string]*float64, variante, metrica string) *float64 {
	metricasVariante, ok := valores[variante]
	if !ok {
		return nil
	}
	return metricasVariante[metrica]
}

func primeiroNumeroMetricaGlobal(texto string) *float64 {
	for _, campo := range strings.Fields(texto) {
		valor, err := strconv.ParseFloat(strings.TrimSpace(campo), 64)
		if err == nil {
			return &valor
		}
	}
	return nil
}

func intParaFloatPtr(v int) *float64 {
	f := float64(v)
	return &f
}

func formatarFloatOpcional(valor *float64) string {
	if valor == nil {
		return ""
	}
	return strconv.FormatFloat(*valor, 'f', 4, 64)
}

func formatarDeltaOpcional(esquerda, direita *float64) string {
	if esquerda == nil || direita == nil {
		return ""
	}
	delta := *esquerda - *direita
	return strconv.FormatFloat(delta, 'f', 4, 64)
}

// agregarExpathsGeracao agrega as ações de expath de todos os arquivos gerados.
func agregarExpathsGeracao(arquivos []dominio.ArquivoTesteGerado) (used, adapted, discarded, total int, taxa *float64) {
	for _, arquivo := range arquivos {
		for _, acao := range arquivo.AcoesExpath {
			total++
			switch acao.Acao {
			case "used":
				used++
			case "adapted":
				adapted++
			case "discarded":
				discarded++
			}
		}
	}
	if total > 0 {
		v := float64(used+adapted) / float64(total)
		taxa = &v
	}
	return
}

// escreverStatsGeracaoJDKGlobalCSV grava um CSV com uma linha por variante contendo
// test_count, tokens, custo e utilização de expaths (para WIT_CONTEXT).
func escreverStatsGeracaoJDKGlobalCSV(path string, report dominio.RelatorioJDKGlobal) error {
	if err := os.MkdirAll(filepath.Dir(path), 0o755); err != nil {
		return err
	}
	file, err := os.Create(path)
	if err != nil {
		return err
	}
	defer file.Close()
	writer := csv.NewWriter(file)
	defer writer.Flush()
	header := []string{
		"variant", "scenario",
		"generated_test_file_count",
		"input_tokens", "output_tokens", "estimated_cost_usd",
		"expath_used", "expath_adapted", "expath_discarded", "expath_total", "expath_utilization_rate",
	}
	if err := writer.Write(header); err != nil {
		return err
	}
	for _, v := range report.Variantes {
		row := []string{
			v.Nome,
			v.Cenario,
			strconv.Itoa(v.QuantidadeTestes),
			strconv.Itoa(v.InputTokens),
			strconv.Itoa(v.OutputTokens),
			formatarFloatOpcional(v.EstimatedCost),
			strconv.Itoa(v.ExpathUsado),
			strconv.Itoa(v.ExpathAdaptado),
			strconv.Itoa(v.ExpathDescartado),
			strconv.Itoa(v.ExpathTotal),
			formatarFloatOpcional(v.TaxaUtilizacaoExpath),
		}
		if err := writer.Write(row); err != nil {
			return err
		}
	}
	return writer.Error()
}

func compactarJSONParaLog(payload interface{}) string {
	data, err := json.Marshal(payload)
	if err != nil {
		return ""
	}
	texto := string(data)
	if len(texto) > 1000 {
		return texto[:1000] + "...[truncado]"
	}
	return texto
}
