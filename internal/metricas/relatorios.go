package metricas

import (
	"encoding/xml"
	"fmt"
	"os"
	"path/filepath"
	"regexp"
	"sort"
	"strings"

	"github.com/marceloamorim/witup-llm/internal/artefatos"
	"github.com/marceloamorim/witup-llm/internal/dominio"
)

type jacocoCounter struct {
	Tipo    string `xml:"type,attr"`
	Perdido int    `xml:"missed,attr"`
	Coberto int    `xml:"covered,attr"`
}

type jacocoReport struct {
	Counters []jacocoCounter `xml:"counter"`
}

type pitMutation struct {
	Detectado bool   `xml:"detected,attr"`
	Status    string `xml:"status,attr"`
}

type pitReport struct {
	Mutations []pitMutation `xml:"mutation"`
}

type surefireTestSuite struct {
	Tests    int `xml:"tests,attr"`
	Failures int `xml:"failures,attr"`
	Errors   int `xml:"errors,attr"`
	Skipped  int `xml:"skipped,attr"`
}

type surefireTestSuites struct {
	Suites []surefireTestSuite `xml:"testsuite"`
}

// EstatisticasSurefire resume a execução registrada pelos XMLs do Maven Surefire.
type EstatisticasSurefire struct {
	Tests    int
	Failures int
	Errors   int
	Skipped  int
}

// Executados devolve o total de testes executados segundo o Surefire.
func (e EstatisticasSurefire) Executados() int {
	return e.Tests
}

// Aprovados devolve quantos testes passaram sem erro nem falha.
func (e EstatisticasSurefire) Aprovados() int {
	aprovados := e.Tests - e.Failures - e.Errors
	if aprovados < 0 {
		return 0
	}
	return aprovados
}

// TaxaSucesso devolve a percentagem de testes aprovados.
func (e EstatisticasSurefire) TaxaSucesso() float64 {
	if e.Tests <= 0 {
		return 0
	}
	return (float64(e.Aprovados()) / float64(e.Tests)) * 100.0
}

// EstatisticasGeracao resume sinais estáticos extraídos de generation.json.
type EstatisticasGeracao struct {
	ArquivosTeste               int
	ArquivosJavaValidos         int
	ArquivosPacoteValido        int
	ArquivosComMetodoTeste      int
	ArquivosDependenciaProibida int
	ArquivosComReflexaoFragil   int
	BlocosAssertThrowsFragil    int
	ArquivosComAssertEstadoInt  int
	MetodosTeste                int
	MetodosComAssertiva         int
	MetodosComAssertivaExn      int
	MetodosAlvo                 int
	MetodosAlvoCobertos         int
	MetodosAlvoInvocados        int
}

// TaxaMetodosAlvoCobertos devolve a percentagem de métodos-alvo com pelo menos um teste associado.
func (e EstatisticasGeracao) TaxaMetodosAlvoCobertos() float64 {
	if e.MetodosAlvo <= 0 {
		return 0
	}
	return (float64(e.MetodosAlvoCobertos) / float64(e.MetodosAlvo)) * 100.0
}

// TaxaTestesAssertivos devolve a percentagem de métodos de teste com pelo menos uma assertiva.
func (e EstatisticasGeracao) TaxaTestesAssertivos() float64 {
	if e.MetodosTeste <= 0 {
		return 0
	}
	return (float64(e.MetodosComAssertiva) / float64(e.MetodosTeste)) * 100.0
}

// TaxaTestesExcecao devolve a percentagem de métodos de teste focados em exceção.
func (e EstatisticasGeracao) TaxaTestesExcecao() float64 {
	if e.MetodosTeste <= 0 {
		return 0
	}
	return (float64(e.MetodosComAssertivaExn) / float64(e.MetodosTeste)) * 100.0
}

// TaxaArquivosJavaValidos mede quantos arquivos gerados parecem ser Java puro,
// sem Markdown/HTML e com estrutura mínima de classe.
func (e EstatisticasGeracao) TaxaArquivosJavaValidos() float64 {
	if e.ArquivosTeste <= 0 {
		return 0
	}
	return (float64(e.ArquivosJavaValidos) / float64(e.ArquivosTeste)) * 100.0
}

// TaxaPacotesValidos mede se o package declarado é coerente com o caminho do teste.
func (e EstatisticasGeracao) TaxaPacotesValidos() float64 {
	if e.ArquivosTeste <= 0 {
		return 0
	}
	return (float64(e.ArquivosPacoteValido) / float64(e.ArquivosTeste)) * 100.0
}

// TaxaArquivosComMetodoTeste mede arquivos que contêm ao menos um método @Test.
func (e EstatisticasGeracao) TaxaArquivosComMetodoTeste() float64 {
	if e.ArquivosTeste <= 0 {
		return 0
	}
	return (float64(e.ArquivosComMetodoTeste) / float64(e.ArquivosTeste)) * 100.0
}

// TaxaMetodosAlvoInvocados usa heurística lexical para ver se o método-alvo foi chamado.
func (e EstatisticasGeracao) TaxaMetodosAlvoInvocados() float64 {
	if e.MetodosAlvo <= 0 {
		return 0
	}
	return (float64(e.MetodosAlvoInvocados) / float64(e.MetodosAlvo)) * 100.0
}

// TaxaDependenciasProibidas mede arquivos que usam bibliotecas externas não
// declaradas no projeto. Quanto menor, melhor; a métrica é diagnóstica.
func (e EstatisticasGeracao) TaxaDependenciasProibidas() float64 {
	if e.ArquivosTeste <= 0 {
		return 0
	}
	return (float64(e.ArquivosDependenciaProibida) / float64(e.ArquivosTeste)) * 100.0
}

// TaxaUsoReflexao mede arquivos que recorrem a reflexão frágil. Quanto menor,
// melhor; a métrica é diagnóstica e ajuda a explicar falhas de execução.
func (e EstatisticasGeracao) TaxaUsoReflexao() float64 {
	if e.ArquivosTeste <= 0 {
		return 0
	}
	return (float64(e.ArquivosComReflexaoFragil) / float64(e.ArquivosTeste)) * 100.0
}

// TaxaAssertThrowsFragil mede blocos @Test que usam assertThrows em torno de
// reflexão sem tratar InvocationTargetException/getCause. Quanto menor, melhor.
func (e EstatisticasGeracao) TaxaAssertThrowsFragil() float64 {
	if e.MetodosTeste <= 0 {
		return 0
	}
	return (float64(e.BlocosAssertThrowsFragil) / float64(e.MetodosTeste)) * 100.0
}

// TaxaAssertEstadoInterno mede arquivos que verificam campos privados/estado
// interno por reflexão, um sinal de teste frágil para projetos de terceiros.
func (e EstatisticasGeracao) TaxaAssertEstadoInterno() float64 {
	if e.ArquivosTeste <= 0 {
		return 0
	}
	return (float64(e.ArquivosComAssertEstadoInt) / float64(e.ArquivosTeste)) * 100.0
}

// ExtrairCoberturaJaCoCo lê um relatório XML do JaCoCo e devolve a cobertura
// percentual do contador solicitado.
func ExtrairCoberturaJaCoCo(caminhoXML, tipoContador string) (float64, error) {
	dados, err := os.ReadFile(caminhoXML)
	if err != nil {
		return 0, fmt.Errorf("ao ler relatório JaCoCo %q: %w", caminhoXML, err)
	}

	var relatorio jacocoReport
	if err := xml.Unmarshal(dados, &relatorio); err != nil {
		return 0, fmt.Errorf("ao interpretar relatório JaCoCo %q: %w", caminhoXML, err)
	}

	tipoContador = strings.ToUpper(strings.TrimSpace(tipoContador))
	for _, contador := range relatorio.Counters {
		if strings.ToUpper(strings.TrimSpace(contador.Tipo)) != tipoContador {
			continue
		}
		total := contador.Coberto + contador.Perdido
		if total == 0 {
			return 0, nil
		}
		return (float64(contador.Coberto) / float64(total)) * 100.0, nil
	}
	return 0, fmt.Errorf("contador JaCoCo %q não encontrado em %q", tipoContador, caminhoXML)
}

// ExtrairMutacaoPIT procura o relatório XML mais recente do PIT e devolve o
// percentual de mutantes detectados.
func ExtrairMutacaoPIT(raizRelatorios string) (float64, string, error) {
	caminhoXML, err := localizarRelatorioPIT(raizRelatorios)
	if err != nil {
		return 0, "", err
	}

	dados, err := os.ReadFile(caminhoXML)
	if err != nil {
		return 0, "", fmt.Errorf("ao ler relatório PIT %q: %w", caminhoXML, err)
	}

	var relatorio pitReport
	if err := xml.Unmarshal(dados, &relatorio); err != nil {
		return 0, "", fmt.Errorf("ao interpretar relatório PIT %q: %w", caminhoXML, err)
	}
	if len(relatorio.Mutations) == 0 {
		return 0, caminhoXML, nil
	}

	detectados := 0
	for _, mutacao := range relatorio.Mutations {
		if mutacao.Detectado {
			detectados++
			continue
		}
		switch strings.ToUpper(strings.TrimSpace(mutacao.Status)) {
		case "KILLED", "TIMED_OUT", "MEMORY_ERROR", "NON_VIABLE":
			detectados++
		}
	}
	return (float64(detectados) / float64(len(relatorio.Mutations))) * 100.0, caminhoXML, nil
}

// CalcularReproducaoExcecoes mede quantos expaths têm pelo menos um teste
// gerado que referencia o tipo de exceção esperado para o mesmo método.
func CalcularReproducaoExcecoes(caminhoAnalise, caminhoGeracao string) (float64, error) {
	var relatorioAnalise dominio.RelatorioAnalise
	if err := artefatos.LerJSON(caminhoAnalise, &relatorioAnalise); err != nil {
		return 0, err
	}

	var relatorioGeracao dominio.RelatorioGeracao
	if err := artefatos.LerJSON(caminhoGeracao, &relatorioGeracao); err != nil {
		return 0, err
	}

	totalExpaths := 0
	reproduzidos := 0
	for _, analise := range relatorioAnalise.Analises {
		arquivosDoMetodo := selecionarArquivosDoMetodo(relatorioGeracao.ArquivosTeste, analise.Metodo.IDMetodo)
		for _, caminho := range analise.CaminhosExcecao {
			totalExpaths++
			if expathReproduzido(caminho, arquivosDoMetodo) {
				reproduzidos++
			}
		}
	}
	if totalExpaths == 0 {
		return 0, nil
	}
	return (float64(reproduzidos) / float64(totalExpaths)) * 100.0, nil
}

// ExtrairEstatisticasSurefire resume a execução registrada nos XMLs do Surefire.
func ExtrairEstatisticasSurefire(raizRelatorios string) (EstatisticasSurefire, error) {
	caminhos, err := localizarRelatoriosSurefire(raizRelatorios)
	if err != nil {
		return EstatisticasSurefire{}, err
	}

	total := EstatisticasSurefire{}
	for _, caminho := range caminhos {
		dados, err := os.ReadFile(caminho)
		if err != nil {
			return EstatisticasSurefire{}, fmt.Errorf("ao ler relatório Surefire %q: %w", caminho, err)
		}

		var suite surefireTestSuite
		if err := xml.Unmarshal(dados, &suite); err == nil && suite.Tests > 0 {
			total.Tests += suite.Tests
			total.Failures += suite.Failures
			total.Errors += suite.Errors
			total.Skipped += suite.Skipped
			continue
		}

		var suites surefireTestSuites
		if err := xml.Unmarshal(dados, &suites); err != nil {
			return EstatisticasSurefire{}, fmt.Errorf("ao interpretar relatório Surefire %q: %w", caminho, err)
		}
		for _, item := range suites.Suites {
			total.Tests += item.Tests
			total.Failures += item.Failures
			total.Errors += item.Errors
			total.Skipped += item.Skipped
		}
	}

	return total, nil
}

// ExtrairTestesExecutadosSurefire soma os testes executados a partir dos XMLs
// produzidos pelo Maven Surefire.
func ExtrairTestesExecutadosSurefire(raizRelatorios string) (float64, error) {
	estatisticas, err := ExtrairEstatisticasSurefire(raizRelatorios)
	if err != nil {
		return 0, err
	}
	return float64(estatisticas.Executados()), nil
}

// ExtrairTaxaSucessoSurefire devolve a percentagem de testes aprovados.
func ExtrairTaxaSucessoSurefire(raizRelatorios string) (float64, error) {
	estatisticas, err := ExtrairEstatisticasSurefire(raizRelatorios)
	if err != nil {
		return 0, err
	}
	return estatisticas.TaxaSucesso(), nil
}

// ExtrairEstatisticasGeracao resume sinais estáticos do generation.json cruzados com o analysis.json.
func ExtrairEstatisticasGeracao(caminhoAnalise, caminhoGeracao string) (EstatisticasGeracao, error) {
	return ExtrairEstatisticasGeracaoComProjeto(caminhoAnalise, caminhoGeracao, "")
}

// ExtrairEstatisticasGeracaoComProjeto resume sinais estáticos do generation.json
// e, quando a raiz do projeto é informada, verifica dependências externas contra o POM.
func ExtrairEstatisticasGeracaoComProjeto(caminhoAnalise, caminhoGeracao, raizProjeto string) (EstatisticasGeracao, error) {
	var relatorioAnalise dominio.RelatorioAnalise
	if err := artefatos.LerJSON(caminhoAnalise, &relatorioAnalise); err != nil {
		return EstatisticasGeracao{}, err
	}

	var relatorioGeracao dominio.RelatorioGeracao
	if err := artefatos.LerJSON(caminhoGeracao, &relatorioGeracao); err != nil {
		return EstatisticasGeracao{}, err
	}

	alvos := make(map[string]struct{})
	metodosPorID := make(map[string]dominio.DescritorMetodo)
	for _, analise := range relatorioAnalise.Analises {
		alvos[analise.Metodo.IDMetodo] = struct{}{}
		metodosPorID[analise.Metodo.IDMetodo] = analise.Metodo
	}

	cobertos := make(map[string]struct{})
	invocados := make(map[string]struct{})
	depsProjeto := detectarDependenciasProjeto(raizProjeto)
	estatisticas := EstatisticasGeracao{ArquivosTeste: len(relatorioGeracao.ArquivosTeste), MetodosAlvo: len(alvos)}
	for _, arquivo := range relatorioGeracao.ArquivosTeste {
		if arquivoJavaValido(arquivo) {
			estatisticas.ArquivosJavaValidos++
		}
		if pacoteCompativelComCaminho(arquivo, metodosPorID) {
			estatisticas.ArquivosPacoteValido++
		}
		if arquivoContemMetodoTeste(arquivo.Conteudo) {
			estatisticas.ArquivosComMetodoTeste++
		}
		if arquivoUsaDependenciaProibida(arquivo.Conteudo, depsProjeto) {
			estatisticas.ArquivosDependenciaProibida++
		}
		if arquivoUsaReflexaoFragil(arquivo.Conteudo) {
			estatisticas.ArquivosComReflexaoFragil++
		}
		if arquivoAssertEstadoInterno(arquivo.Conteudo) {
			estatisticas.ArquivosComAssertEstadoInt++
		}
		for _, id := range arquivo.IDsMetodosCobertos {
			if _, ok := alvos[id]; ok {
				cobertos[id] = struct{}{}
				if metodoAlvoInvocado(arquivo.Conteudo, metodosPorID[id]) {
					invocados[id] = struct{}{}
				}
			}
		}
		partes := extrairBlocosDeTeste(arquivo.Conteudo)
		estatisticas.MetodosTeste += len(partes)
		for _, bloco := range partes {
			if blocoContemAssertiva(bloco) {
				estatisticas.MetodosComAssertiva++
			}
			if blocoContemAssertivaExcecao(bloco) {
				estatisticas.MetodosComAssertivaExn++
			}
			if blocoContemAssertThrowsFragil(bloco) {
				estatisticas.BlocosAssertThrowsFragil++
			}
		}
	}
	estatisticas.MetodosAlvoCobertos = len(cobertos)
	estatisticas.MetodosAlvoInvocados = len(invocados)
	return estatisticas, nil
}

var regexInicioTeste = regexp.MustCompile(`(?m)^[ 	]*@(?:Test|ParameterizedTest|RepeatedTest|TestFactory|TestTemplate)\b`)
var regexAssertiva = regexp.MustCompile(`(?i)\b(assert[A-Za-z0-9_]*|fail|verify|assertThat(?:ThrownBy)?)\s*\(|@Test\s*\([^)]*expected\s*=|ExpectedException`)
var regexAssertivaExcecao = regexp.MustCompile(`(?i)\b(assertThrows(?:Exactly)?|assertThatThrownBy|catchThrowable(?:OfType)?|expectThrows)\b|@Test\s*\([^)]*expected\s*=|ExpectedException`)
var regexPackageJava = regexp.MustCompile(`(?m)^\s*package\s+([A-Za-z0-9_.]+)\s*;`)
var regexReflexaoFragil = regexp.MustCompile(`(?i)\b(getDeclaredField|getDeclaredConstructor|getDeclaredMethod|setAccessible)\s*\(|java\.lang\.reflect|InvocationTargetException`)
var regexAssertThrows = regexp.MustCompile(`(?i)\b(assertThrows(?:Exactly)?|assertThatThrownBy|catchThrowable(?:OfType)?|expectThrows)\b|@Test\s*\([^)]*expected\s*=|ExpectedException`)
var regexCampoDeclarado = regexp.MustCompile(`(?s)getDeclaredField\s*\(\s*"[^"]+"\s*\).*?(assert[A-Za-z0-9_]*|assertThat|fail)\s*\(`)
var regexCampoPrivadoNomeado = regexp.MustCompile(`(?m)\b(assert[A-Za-z0-9_]*|assertThat)\s*\([^;]*(?:\b_[A-Za-z0-9_]+|"(?:_[A-Za-z0-9_]+|explicitName|explName|instance)")`)

func extrairBlocosDeTeste(conteudo string) []string {
	indices := regexInicioTeste.FindAllStringIndex(conteudo, -1)
	if len(indices) == 0 {
		return nil
	}
	blocos := make([]string, 0, len(indices))
	for i, indice := range indices {
		inicio := indice[0]
		fim := len(conteudo)
		if i+1 < len(indices) {
			fim = indices[i+1][0]
		}
		blocos = append(blocos, conteudo[inicio:fim])
	}
	return blocos
}

func blocoContemAssertiva(bloco string) bool {
	return regexAssertiva.MatchString(bloco)
}

func blocoContemAssertivaExcecao(bloco string) bool {
	return regexAssertivaExcecao.MatchString(bloco)
}

func blocoContemAssertThrowsFragil(bloco string) bool {
	if !regexAssertThrows.MatchString(bloco) || !regexReflexaoFragil.MatchString(bloco) {
		return false
	}
	return !strings.Contains(bloco, "InvocationTargetException") && !strings.Contains(bloco, "getCause(") && !strings.Contains(bloco, "getCause()")
}

func arquivoUsaReflexaoFragil(conteudo string) bool {
	return regexReflexaoFragil.MatchString(conteudo)
}

func arquivoAssertEstadoInterno(conteudo string) bool {
	return regexCampoDeclarado.MatchString(conteudo) || regexCampoPrivadoNomeado.MatchString(conteudo)
}

func arquivoJavaValido(arquivo dominio.ArquivoTesteGerado) bool {
	caminho := strings.ToLower(filepath.ToSlash(strings.TrimSpace(arquivo.CaminhoRelativo)))
	conteudo := strings.TrimSpace(arquivo.Conteudo)
	if conteudo == "" || !strings.HasSuffix(caminho, ".java") {
		return false
	}
	lower := strings.ToLower(conteudo)
	if strings.Contains(lower, "```") ||
		strings.Contains(lower, "<html") ||
		strings.Contains(lower, "</html") ||
		strings.Contains(lower, "<!doctype html") {
		return false
	}
	return strings.Contains(conteudo, "class ") ||
		strings.Contains(conteudo, " record ") ||
		strings.Contains(conteudo, " interface ") ||
		strings.Contains(conteudo, " enum ")
}

func pacoteCompativelComCaminho(arquivo dominio.ArquivoTesteGerado, metodosPorID map[string]dominio.DescritorMetodo) bool {
	pacoteDeclarado := extrairPacoteJava(arquivo.Conteudo)
	pacoteEsperado := pacoteEsperadoPeloCaminho(arquivo.CaminhoRelativo)
	if pacoteEsperado == "" {
		pacoteEsperado = pacoteComumMetodosCobertos(arquivo.IDsMetodosCobertos, metodosPorID)
	}
	if pacoteEsperado == "" {
		return pacoteDeclarado == ""
	}
	return pacoteDeclarado == pacoteEsperado
}

func pacoteEsperadoPeloCaminho(caminhoRelativo string) string {
	normalizado := filepath.ToSlash(strings.TrimSpace(caminhoRelativo))
	for _, marcador := range []string{"src/test/java/", "src/it/java/"} {
		if indice := strings.Index(normalizado, marcador); indice >= 0 {
			resto := normalizado[indice+len(marcador):]
			dir := filepath.ToSlash(filepath.Dir(resto))
			if dir == "." || dir == "" {
				return ""
			}
			return strings.ReplaceAll(dir, "/", ".")
		}
	}
	return ""
}

func pacoteComumMetodosCobertos(ids []string, metodosPorID map[string]dominio.DescritorMetodo) string {
	pacote := ""
	for _, id := range ids {
		metodo, ok := metodosPorID[id]
		if !ok {
			continue
		}
		atual := pacoteDoContainerMetrica(metodo.NomeContainer)
		if atual == "" {
			continue
		}
		if pacote == "" {
			pacote = atual
			continue
		}
		if pacote != atual {
			return ""
		}
	}
	return pacote
}

func pacoteDoContainerMetrica(container string) string {
	container = strings.TrimSpace(container)
	if indice := strings.LastIndex(container, "."); indice > 0 {
		return container[:indice]
	}
	return ""
}

func arquivoContemMetodoTeste(conteudo string) bool {
	return regexInicioTeste.MatchString(conteudo)
}

func metodoAlvoInvocado(conteudo string, metodo dominio.DescritorMetodo) bool {
	nome := strings.TrimSpace(metodo.NomeMetodo)
	if nome == "" {
		return false
	}
	regex := regexp.MustCompile(`\b` + regexp.QuoteMeta(nome) + `\s*\(`)
	return regex.MatchString(conteudo)
}

func arquivoUsaDependenciaProibida(conteudo string, deps dependenciasProjeto) bool {
	if strings.Contains(conteudo, "org.mockito") || strings.Contains(conteudo, "Mockito.") {
		return !deps.Mockito
	}
	if strings.Contains(conteudo, "org.assertj") || strings.Contains(conteudo, "Assertions.assertThat") {
		return !deps.AssertJ
	}
	return false
}

type dependenciasProjeto struct {
	Mockito bool
	AssertJ bool
}

func detectarDependenciasProjeto(raizProjeto string) dependenciasProjeto {
	if strings.TrimSpace(raizProjeto) == "" {
		return dependenciasProjeto{}
	}
	conteudo := coletarPOMsProjeto(raizProjeto)
	return dependenciasProjeto{
		Mockito: strings.Contains(conteudo, "<groupId>org.mockito</groupId>") ||
			strings.Contains(conteudo, "mockito-core") ||
			strings.Contains(conteudo, "mockito-junit"),
		AssertJ: strings.Contains(conteudo, "<groupId>org.assertj</groupId>") ||
			strings.Contains(conteudo, "assertj-core"),
	}
}

func coletarPOMsProjeto(raizProjeto string) string {
	var builder strings.Builder
	_ = filepath.Walk(raizProjeto, func(caminho string, info os.FileInfo, err error) error {
		if err != nil || info == nil || info.IsDir() || info.Name() != "pom.xml" {
			return nil
		}
		if strings.Contains(filepath.ToSlash(caminho), "/target/") {
			return nil
		}
		dados, err := os.ReadFile(caminho)
		if err != nil {
			return nil
		}
		builder.WriteString("\n")
		builder.Write(dados)
		return nil
	})
	return builder.String()
}

func extrairPacoteJava(conteudo string) string {
	grupos := regexPackageJava.FindStringSubmatch(conteudo)
	if len(grupos) < 2 {
		return ""
	}
	return strings.TrimSpace(grupos[1])
}

// localizarRelatorioPIT encontra o mutations.xml mais recente dentro da árvore
// de relatórios gerada pelo PIT.
func localizarRelatorioPIT(raizRelatorios string) (string, error) {
	var candidato string
	var candidatoInfo os.FileInfo

	err := filepath.Walk(raizRelatorios, func(caminho string, info os.FileInfo, err error) error {
		if err != nil {
			return err
		}
		if info.IsDir() || info.Name() != "mutations.xml" {
			return nil
		}
		if candidato == "" || info.ModTime().After(candidatoInfo.ModTime()) {
			candidato = caminho
			candidatoInfo = info
		}
		return nil
	})
	if err != nil {
		return "", fmt.Errorf("ao localizar relatório PIT em %q: %w", raizRelatorios, err)
	}
	if candidato == "" {
		return "", fmt.Errorf("nenhum mutations.xml foi encontrado em %q", raizRelatorios)
	}
	return candidato, nil
}

// localizarRelatoriosSurefire encontra os relatórios XML produzidos pelo
// Maven Surefire e os devolve em ordem estável.
func localizarRelatoriosSurefire(raizRelatorios string) ([]string, error) {
	relatorios := make([]string, 0)

	err := filepath.Walk(raizRelatorios, func(caminho string, info os.FileInfo, err error) error {
		if err != nil {
			return err
		}
		if info.IsDir() {
			return nil
		}
		nome := info.Name()
		if !strings.HasPrefix(nome, "TEST-") || !strings.HasSuffix(nome, ".xml") {
			return nil
		}
		relatorios = append(relatorios, caminho)
		return nil
	})
	if err != nil {
		return nil, fmt.Errorf("ao localizar relatórios Surefire em %q: %w", raizRelatorios, err)
	}
	if len(relatorios) == 0 {
		return nil, fmt.Errorf("nenhum relatório Surefire foi encontrado em %q", raizRelatorios)
	}
	sort.Strings(relatorios)
	return relatorios, nil
}

// selecionarArquivosDoMetodo limita a inspeção aos arquivos explicitamente
// associados ao método quando a geração preserva esse mapeamento.
func selecionarArquivosDoMetodo(arquivos []dominio.ArquivoTesteGerado, idMetodo string) []dominio.ArquivoTesteGerado {
	filtrados := make([]dominio.ArquivoTesteGerado, 0)
	for _, arquivo := range arquivos {
		if len(arquivo.IDsMetodosCobertos) == 0 {
			filtrados = append(filtrados, arquivo)
			continue
		}
		for _, coberto := range arquivo.IDsMetodosCobertos {
			if coberto == idMetodo {
				filtrados = append(filtrados, arquivo)
				break
			}
		}
	}
	if len(filtrados) > 0 {
		return filtrados
	}
	return arquivos
}

// expathReproduzido usa o nome completo ou simples da exceção como heurística
// leve para detectar se a geração materializou um expath em pelo menos um teste.
func expathReproduzido(caminho dominio.CaminhoExcecao, arquivos []dominio.ArquivoTesteGerado) bool {
	tipoCompleto := strings.TrimSpace(caminho.TipoExcecao)
	tipoSimples := tipoCompleto
	if indice := strings.LastIndex(tipoCompleto, "."); indice >= 0 {
		tipoSimples = tipoCompleto[indice+1:]
	}

	for _, arquivo := range arquivos {
		if strings.Contains(arquivo.Conteudo, tipoCompleto) || strings.Contains(arquivo.Conteudo, tipoSimples) {
			return true
		}
	}
	return false
}
