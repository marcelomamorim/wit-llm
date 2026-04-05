package aplicacao

import (
	"fmt"
	"os"
	"path/filepath"
	"sort"
	"strings"

	"github.com/marceloamorim/witup-llm/internal/armazenamento"
	"github.com/marceloamorim/witup-llm/internal/artefatos"
	"github.com/marceloamorim/witup-llm/internal/dominio"
	"github.com/marceloamorim/witup-llm/internal/metricas"
	"github.com/marceloamorim/witup-llm/internal/registro"
)

// agruparAnalisesPorContainer agrupa análises pelo contêiner/classe que as contém.
func agruparAnalisesPorContainer(report dominio.RelatorioAnalise) map[string][]dominio.AnaliseMetodo {
	grupos := map[string][]dominio.AnaliseMetodo{}
	for _, analise := range report.Analises {
		container := analise.Metodo.NomeContainer
		grupos[container] = append(grupos[container], analise)
	}
	return grupos
}

// filtrarAnalisesParte2 remove métodos que optamos por não materializar como
// testes na Parte 2. Isso preserva a Parte 1 intacta, mas impede que um alvo
// sabidamente ruidoso distorça a comparação das suítes.
func filtrarAnalisesParte2(report dominio.RelatorioAnalise) dominio.RelatorioAnalise {
	filtradas := make([]dominio.AnaliseMetodo, 0, len(report.Analises))
	for _, analise := range report.Analises {
		if deveExcluirAnaliseParte2(analise) {
			continue
		}
		filtradas = append(filtradas, analise)
	}
	report.Analises = filtradas
	report.TotalMetodos = len(filtradas)
	return report
}

func deveExcluirAnaliseParte2(analise dominio.AnaliseMetodo) bool {
	if analise.Metodo.NomeContainer == "de.strullerbaumann.visualee.ui.graph.control.HTMLManager" {
		return true
	}
	caminho := strings.ToLower(filepath.ToSlash(analise.Metodo.CaminhoArquivo))
	if strings.HasSuffix(caminho, "/ui/graph/control/htmlmanager.java") {
		return true
	}
	assinatura := strings.ToLower(strings.TrimSpace(analise.Metodo.Assinatura))
	return strings.Contains(assinatura, ".htmlmanager.generatehtml(")
}

const (
	limiteMetodosPorLoteGeracao       = 6
	limiteCaminhosPorLoteGeracao      = 18
	limiteCaracteresVisaoGeralGeracao = 3000
)

// dividirAnalisesParaGeracao quebra um conjunto grande de análises em lotes menores
// para reduzir o contexto enviado ao modelo durante a geração de testes.
func dividirAnalisesParaGeracao(analises []dominio.AnaliseMetodo) [][]dominio.AnaliseMetodo {
	if len(analises) == 0 {
		return nil
	}

	lotes := make([][]dominio.AnaliseMetodo, 0, len(analises))
	loteAtual := make([]dominio.AnaliseMetodo, 0, limiteMetodosPorLoteGeracao)
	totalCaminhos := 0

	for _, analise := range analises {
		quantidadeCaminhos := len(analise.CaminhosExcecao)
		if len(loteAtual) > 0 &&
			(len(loteAtual) >= limiteMetodosPorLoteGeracao || totalCaminhos+quantidadeCaminhos > limiteCaminhosPorLoteGeracao) {
			lotes = append(lotes, loteAtual)
			loteAtual = make([]dominio.AnaliseMetodo, 0, limiteMetodosPorLoteGeracao)
			totalCaminhos = 0
		}

		loteAtual = append(loteAtual, analise)
		totalCaminhos += quantidadeCaminhos
	}

	if len(loteAtual) > 0 {
		lotes = append(lotes, loteAtual)
	}

	return lotes
}

// reduzirVisaoGeralParaGeracao limita a visão geral do projeto para evitar prompts
// desproporcionalmente grandes durante a geração de testes.
func reduzirVisaoGeralParaGeracao(visaoGeral string) string {
	visaoGeral = strings.TrimSpace(visaoGeral)
	if len(visaoGeral) <= limiteCaracteresVisaoGeralGeracao {
		return visaoGeral
	}
	return strings.TrimSpace(visaoGeral[:limiteCaracteresVisaoGeralGeracao]) + "\n...[truncado]"
}

// consolidarArquivosGerados remove duplicatas por caminho relativo antes da escrita
// final dos arquivos de teste no workspace.
func consolidarArquivosGerados(arquivos []dominio.ArquivoTesteGerado) []dominio.ArquivoTesteGerado {
	if len(arquivos) == 0 {
		return nil
	}

	porCaminho := make(map[string]dominio.ArquivoTesteGerado, len(arquivos))
	for _, arquivo := range arquivos {
		chave := strings.TrimSpace(arquivo.CaminhoRelativo)
		if chave == "" {
			continue
		}
		porCaminho[chave] = arquivo
	}

	chaves := make([]string, 0, len(porCaminho))
	for chave := range porCaminho {
		chaves = append(chaves, chave)
	}
	sort.Strings(chaves)

	consolidados := make([]dominio.ArquivoTesteGerado, 0, len(chaves))
	for _, chave := range chaves {
		consolidados = append(consolidados, porCaminho[chave])
	}
	return consolidados
}

// contarCaminhosAnalises soma a quantidade de expaths em um conjunto de análises.
func contarCaminhosAnalises(analises []dominio.AnaliseMetodo) int {
	total := 0
	for _, analise := range analises {
		total += len(analise.CaminhosExcecao)
	}
	return total
}

// paraListaStrings converte slices genéricos em listas de strings limpas.
func paraListaStrings(raw interface{}) []string {
	if raw == nil {
		return nil
	}
	lista, ok := raw.([]interface{})
	if !ok {
		return nil
	}
	saida := make([]string, 0, len(lista))
	for _, item := range lista {
		valor := strings.TrimSpace(fmt.Sprint(item))
		if valor == "" || valor == "<nil>" {
			continue
		}
		saida = append(saida, valor)
	}
	return saida
}

// converterParaFloat converte valores arbitrários para float64 usando um fallback seguro.
func converterParaFloat(value interface{}, fallback float64) float64 {
	valorBruto := strings.TrimSpace(fmt.Sprint(value))
	if valorBruto == "" || valorBruto == "<nil>" {
		return fallback
	}

	var valorConvertido float64
	if _, err := fmt.Sscanf(valorBruto, "%f", &valorConvertido); err != nil {
		return fallback
	}
	return valorConvertido
}

// fallbackIDCaminho cria um identificador estável quando o payload não informa path_id.
func fallbackIDCaminho(raw, methodID string, index int) string {
	valor := strings.TrimSpace(raw)
	if valor == "" || valor == "<nil>" {
		return fmt.Sprintf("%s:%d", methodID, index)
	}
	return valor
}

// chaveOrdenacaoNota escolhe a melhor nota disponível para ranquear entradas de benchmark.
func chaveOrdenacaoNota(combined, metric, judge *float64) float64 {
	if combined != nil {
		return *combined
	}
	if metric != nil {
		return *metric
	}
	if judge != nil {
		return *judge
	}
	return -1
}

// construirMarkdownBenchmark renderiza o relatório de benchmark em formato Markdown.
func construirMarkdownBenchmark(entries []dominio.EntradaBenchmark) string {
	linhas := []string{
		"# Benchmark Report",
		"",
		"| Posicao | Scenario | Metric | Judge | Combined |",
		"| --- | --- | ---: | ---: | ---: |",
	}
	for _, entry := range entries {
		linhas = append(linhas, fmt.Sprintf("| %d | %s->%s | %s | %s | %s |",
			entry.Posicao,
			entry.ChaveModeloAnalise,
			entry.ChaveModeloGeracao,
			metricas.FormatarPontuacao(entry.NotaMetricas),
			metricas.FormatarPontuacao(entry.JudgeScore),
			metricas.FormatarPontuacao(entry.NotaCombinada),
		))
	}
	linhas = append(linhas, "")
	return strings.Join(linhas, "\n")
}

// GarantirCaminhosExistem valida se os arquivos esperados existem antes do carregamento.
func GarantirCaminhosExistem(paths ...string) error {
	for _, path := range paths {
		info, err := os.Stat(path)
		if err != nil {
			return fmt.Errorf("arquivo obrigatório %q: %w", path, err)
		}
		if info.IsDir() {
			return fmt.Errorf("o caminho obrigatório %q é um diretório", path)
		}
	}
	return nil
}

// carregarCatalogoProjeto descobre métodos e visão geral já respeitando o limite
// de métodos configurado para a execução.
func carregarCatalogoProjeto(
	catalogo CatalogoMetodos,
	maximoMetodos int,
) ([]dominio.DescritorMetodo, string, error) {
	metodos, err := catalogo.Catalogar()
	if err != nil {
		return nil, "", err
	}
	if len(metodos) == 0 {
		return nil, "", fmt.Errorf("nenhum método Java foi catalogado; revise project.root, project.include e project.exclude")
	}
	if maximoMetodos > 0 && len(metodos) > maximoMetodos {
		metodos = metodos[:maximoMetodos]
	}

	visaoGeral, err := catalogo.CarregarVisaoGeral()
	if err != nil {
		return nil, "", err
	}
	return metodos, visaoGeral, nil
}

// prepararEspacoTrabalho reutiliza um workspace informado ou cria um novo
// seguindo o padrão de diretórios do projeto.
func prepararEspacoTrabalho(
	espaco *artefatos.EspacoTrabalho,
	diretorioSaida string,
	prefixoExecucao string,
) (*artefatos.EspacoTrabalho, error) {
	if espaco != nil {
		return espaco, nil
	}
	return artefatos.NovoEspacoTrabalho(diretorioSaida, artefatos.NovoIDExecucao(prefixoExecucao))
}

// persistirCatalogo registra o catálogo usado na execução para facilitar auditoria.
func persistirCatalogo(
	espaco *artefatos.EspacoTrabalho,
	metodos []dominio.DescritorMetodo,
) error {
	return artefatos.EscreverJSON(filepath.Join(espaco.Raiz, "catalogo.json"), metodos)
}

// persistirPromptEResposta grava os artefatos textuais de uma chamada a LLM.
func persistirPromptEResposta(
	espaco *artefatos.EspacoTrabalho,
	nomeBase string,
	prompt string,
	resposta string,
) error {
	if err := artefatos.EscreverTexto(filepath.Join(espaco.Prompts, nomeBase+".txt"), prompt); err != nil {
		return err
	}
	return artefatos.EscreverTexto(filepath.Join(espaco.Respostas, nomeBase+".txt"), resposta)
}

// abrirBancoAnalitico cria uma conexão curta com o DuckDB configurado.
func abrirBancoAnalitico(cfg *dominio.ConfigAplicacao) (*armazenamento.BancoDuckDB, error) {
	caminhoDuckDB := strings.TrimSpace(cfg.Fluxo.CaminhoDuckDB)
	if caminhoDuckDB == "" {
		diretorioBase := strings.TrimSpace(cfg.Fluxo.DiretorioSaida)
		if diretorioBase == "" {
			diretorioBase = os.TempDir()
		}
		caminhoDuckDB = filepath.Join(diretorioBase, "witup-llm.duckdb")
	}
	return armazenamento.AbrirBancoDuckDB(caminhoDuckDB)
}

// registrarArtefatoNoBanco indexa um artefato gerado no DuckDB para facilitar
// consultas e navegação pela interface gráfica.
func registrarArtefatoNoBanco(
	cfg *dominio.ConfigAplicacao,
	idExecucao string,
	tipoArtefato string,
	chaveProjeto string,
	variante string,
	caminhoArquivo string,
	geradoEm string,
	payload interface{},
) error {
	banco, err := abrirBancoAnalitico(cfg)
	if err != nil {
		return err
	}
	defer banco.Fechar()

	return banco.RegistrarArtefatoExecucao(
		idExecucao,
		tipoArtefato,
		chaveProjeto,
		variante,
		caminhoArquivo,
		geradoEm,
		payload,
	)
}

// resolverDiretorioHistorico identifica onde a execução deve materializar os
// snapshots históricos em Parquet. Quando a saída fica sob `generated/`, o
// histórico é promovido para um diretório irmão chamado `historico/`.
func resolverDiretorioHistorico(cfg *dominio.ConfigAplicacao) string {
	diretorioSaida := strings.TrimSpace(cfg.Fluxo.DiretorioSaida)
	if diretorioSaida == "" {
		return "historico"
	}

	atual := filepath.Clean(diretorioSaida)
	for {
		if filepath.Base(atual) == "generated" {
			return filepath.Join(filepath.Dir(atual), "historico")
		}
		proximo := filepath.Dir(atual)
		if proximo == atual {
			break
		}
		atual = proximo
	}
	return filepath.Join(diretorioSaida, "historico")
}

// exportarHistoricoParquet grava snapshots em Parquet para preservar uma visão
// estável dos resultados analíticos de cada execução.
func exportarHistoricoParquet(
	cfg *dominio.ConfigAplicacao,
	idExecucao string,
	chaveProjeto string,
) (armazenamento.ResumoHistoricoParquet, error) {
	banco, err := abrirBancoAnalitico(cfg)
	if err != nil {
		return armazenamento.ResumoHistoricoParquet{}, err
	}
	defer banco.Fechar()

	diretorioHistorico := filepath.Join(
		resolverDiretorioHistorico(cfg),
		artefatos.Slugificar(chaveProjeto),
		idExecucao,
	)
	return banco.ExportarHistoricoExecucaoParquet(idExecucao, chaveProjeto, diretorioHistorico)
}

// imprimirResumoObservabilidade mostra onde acompanhar logs, artefatos e banco.
func imprimirResumoObservabilidade(configPath string, cfg *dominio.ConfigAplicacao, raizExecucao string) {
	if strings.TrimSpace(raizExecucao) != "" {
		fmt.Printf("Raiz da execução      : %s\n", raizExecucao)
	}
	if strings.TrimSpace(cfg.Fluxo.CaminhoDuckDB) != "" {
		fmt.Printf("DuckDB                : %s\n", cfg.Fluxo.CaminhoDuckDB)
	}
	if strings.TrimSpace(configPath) != "" {
		fmt.Printf("Abrir interface       : ./bin/witup visualizar-dados --config %s\n", configPath)
		fmt.Printf("Interface padrão      : http://127.0.0.1:8421\n")
	}
	if caminhoLog := registro.CaminhoArquivo(); strings.TrimSpace(caminhoLog) != "" {
		fmt.Printf("Log local             : %s\n", caminhoLog)
	}
}
