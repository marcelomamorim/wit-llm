package aplicacao

import (
	"context"
	"fmt"
	"path/filepath"
	"sort"

	"github.com/marceloamorim/witup-llm/internal/artefatos"
	"github.com/marceloamorim/witup-llm/internal/dominio"
	"github.com/marceloamorim/witup-llm/internal/llm"
	"github.com/marceloamorim/witup-llm/internal/registro"
	"github.com/marceloamorim/witup-llm/internal/witup"
)

// PrepararBatchGeracaoSegundaFase gera o JSONL de chamadas /v1/responses para
// WIT_CONTEXT e DIRECT_TESTS sem executar chamadas pagas.
func (s *Servico) PrepararBatchGeracaoSegundaFase(cfg *dominio.ConfigAplicacao, generationModelKey, requestsPath string) (int, error) {
	model, err := getModelOrError(cfg, generationModelKey)
	if err != nil {
		return 0, err
	}
	ctxHeartbeat, cancelCtxHeartbeat := context.WithCancel(context.Background())
	progressoHeartbeat := registro.NovoProgresso(len(cfg.SegundaFase.Projetos))
	cancelHeartbeat := registro.IniciarHeartbeat(ctxHeartbeat, "phase-two", "batch_prepare_jsonl", "all", "running", progressoHeartbeat)
	defer cancelHeartbeat()
	defer cancelCtxHeartbeat()
	linhas := make([]llm.LinhaRequisicaoBatch, 0, len(cfg.SegundaFase.Projetos)*2)
	cacheCatalogo := map[string][]dominio.DescritorMetodo{}
	for _, projeto := range cfg.SegundaFase.Projetos {
		cfgProjeto := clonarConfiguracaoParaProjetoSegundaFase(cfg, projeto)
		metodosCatalogados, err := s.carregarCatalogoSegundaFaseComCache(cfgProjeto, cacheCatalogo)
		if err != nil {
			return 0, err
		}
		metodosCatalogados = filtrarMetodosPorContainers(metodosCatalogados, projeto.ContainersAlvo)
		baselineReport, err := witup.CarregarAnalise(projeto.CaminhoBaseline)
		if err != nil {
			return 0, fmt.Errorf("ao carregar baseline WIT do projeto %s: %w", projeto.Chave, err)
		}
		baselineAlinhada, metodosAlvo, _ := alinharWITUPAoCatalogo(baselineReport, metodosCatalogados, cfgProjeto.Fluxo.MaximoMetodos)
		if len(metodosAlvo) == 0 {
			return 0, fmt.Errorf("nenhum método WIT foi alinhado no projeto %s", projeto.Chave)
		}
		overview, err := s.catalogFactory.NovoCatalogo(cfgProjeto.Projeto).CarregarVisaoGeral()
		if err != nil {
			return 0, err
		}
		witLines, err := construirLinhasBatchWIT(cfgProjeto, model, overview, projeto.Chave, baselineAlinhada)
		if err != nil {
			return 0, err
		}
		directLines, err := construirLinhasBatchDiretas(cfgProjeto, model, overview, projeto.Chave, metodosAlvo)
		if err != nil {
			return 0, err
		}
		linhas = append(linhas, witLines...)
		linhas = append(linhas, directLines...)
		progressoHeartbeat.Incrementar()
		registro.Info("phase-two", "batch_prepare_jsonl projeto=%s requests_wit=%d requests_direct=%d requests_total=%d", projeto.Chave, len(witLines), len(directLines), len(linhas))
	}
	if err := llm.EscreverJSONLBatch(requestsPath, linhas); err != nil {
		return 0, err
	}
	manifestPath := filepath.Join(filepath.Dir(requestsPath), "batch_request_manifest.json")
	if err := artefatos.EscreverJSON(manifestPath, map[string]interface{}{
		"generation_model_key": generationModelKey,
		"request_count":        len(linhas),
		"endpoint":             "/v1/responses",
		"delta_direction":      "WIT_CONTEXT_MINUS_DIRECT_TESTS",
	}); err != nil {
		return 0, err
	}
	return len(linhas), nil
}

func construirLinhasBatchWIT(cfg *dominio.ConfigAplicacao, model dominio.ConfigModelo, overview, projeto string, analysis dominio.RelatorioAnalise) ([]llm.LinhaRequisicaoBatch, error) {
	analysis = filtrarAnalisesParte2(analysis)
	grouped := agruparAnalisesPorContainer(analysis)
	keys := make([]string, 0, len(grouped))
	for key := range grouped {
		keys = append(keys, key)
	}
	sort.Strings(keys)
	linhas := []llm.LinhaRequisicaoBatch{}
	for _, containerName := range keys {
		for indiceLote, methodsPayload := range dividirAnalisesParaGeracao(grouped[containerName]) {
			contextoComum := construirContextoGeracaoTestes(cfg.Projeto, containerName, extrairMetodosDasAnalises(methodsPayload))
			linha, err := llm.ConstruirLinhaBatchResponses(
				fmt.Sprintf("%s/wit-context/%s/batch-%02d", artefatos.Slugificar(projeto), artefatos.Slugificar(containerName), indiceLote+1),
				model,
				construirPromptGeracaoSistema(resolverFrameworkTestes(cfg.Projeto)),
				construirPromptGeracaoUsuario(overview, containerName, methodsPayload, contextoComum),
				dominio.OpcoesRequisicaoLLM{PromptCacheKey: construirPromptCacheKey(identificarProjeto(cfg), "generation", containerName)},
			)
			if err != nil {
				return nil, err
			}
			linhas = append(linhas, linha)
		}
	}
	return linhas, nil
}

func construirLinhasBatchDiretas(cfg *dominio.ConfigAplicacao, model dominio.ConfigModelo, overview, projeto string, metodos []dominio.DescritorMetodo) ([]llm.LinhaRequisicaoBatch, error) {
	grupos := agruparMetodosPorContainer(metodos)
	keys := make([]string, 0, len(grupos))
	for key := range grupos {
		keys = append(keys, key)
	}
	sort.Strings(keys)
	linhas := []llm.LinhaRequisicaoBatch{}
	for _, containerName := range keys {
		for indiceLote, methodsPayload := range dividirMetodosDiretosParaGeracao(grupos[containerName]) {
			contextoComum := construirContextoGeracaoTestes(cfg.Projeto, containerName, methodsPayload)
			linha, err := llm.ConstruirLinhaBatchResponses(
				fmt.Sprintf("%s/direct-tests/%s/batch-%02d", artefatos.Slugificar(projeto), artefatos.Slugificar(containerName), indiceLote+1),
				model,
				construirPromptGeracaoDiretaSistema(resolverFrameworkTestes(cfg.Projeto)),
				construirPromptGeracaoDiretaUsuario(overview, containerName, methodsPayload, contextoComum),
				dominio.OpcoesRequisicaoLLM{PromptCacheKey: construirPromptCacheKey(identificarProjeto(cfg), "direct-generation", containerName)},
			)
			if err != nil {
				return nil, err
			}
			linhas = append(linhas, linha)
		}
	}
	return linhas, nil
}
