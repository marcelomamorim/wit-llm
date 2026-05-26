package aplicacao

import (
	"fmt"
	"os"
	"path/filepath"
	"sort"
	"strings"

	"github.com/marceloamorim/witup-llm/internal/artefatos"
	"github.com/marceloamorim/witup-llm/internal/dominio"
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

// filtrarMetodosPorContainers restringe a seleção aos contêineres explicitamente
// declarados na configuração do projeto da segunda fase.
func filtrarMetodosPorContainers(metodos []dominio.DescritorMetodo, containers []string) []dominio.DescritorMetodo {
	if len(containers) == 0 {
		return metodos
	}

	alvos := normalizarContainersAlvo(containers)
	filtrados := make([]dominio.DescritorMetodo, 0, len(metodos))
	for _, metodo := range metodos {
		if _, ok := alvos[strings.ToLower(strings.TrimSpace(metodo.NomeContainer))]; ok {
			filtrados = append(filtrados, metodo)
		}
	}
	return filtrados
}

func normalizarContainersAlvo(containers []string) map[string]struct{} {
	alvos := make(map[string]struct{}, len(containers))
	for _, container := range containers {
		valor := strings.ToLower(strings.TrimSpace(container))
		if valor == "" {
			continue
		}
		alvos[valor] = struct{}{}
	}
	return alvos
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

func deduplicarStrings(valores []string) []string {
	if len(valores) == 0 {
		return nil
	}
	vistos := map[string]bool{}
	saida := make([]string, 0, len(valores))
	for _, valor := range valores {
		valor = strings.TrimSpace(valor)
		if valor == "" || vistos[valor] {
			continue
		}
		vistos[valor] = true
		saida = append(saida, valor)
	}
	sort.Strings(saida)
	return saida
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

// imprimirResumoObservabilidade mostra onde acompanhar logs e artefatos locais.
func imprimirResumoObservabilidade(_ string, cfg *dominio.ConfigAplicacao, raizExecucao string) {
	if strings.TrimSpace(raizExecucao) != "" {
		fmt.Printf("Raiz da execução      : %s\n", raizExecucao)
	}
	if caminhoLog := registro.CaminhoArquivo(); strings.TrimSpace(caminhoLog) != "" {
		fmt.Printf("Log local             : %s\n", caminhoLog)
	}
	if cfg != nil && strings.TrimSpace(cfg.Fluxo.DiretorioSaida) != "" {
		fmt.Printf("Diretório de saída    : %s\n", cfg.Fluxo.DiretorioSaida)
	}
}
