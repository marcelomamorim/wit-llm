package visualizacao

import (
	"fmt"
	"html"
	"html/template"
	"os"
	"path/filepath"
	"strings"
	"unicode"

	"github.com/marceloamorim/witup-llm/internal/dominio"
)

// GeradorSegundaFase cria um dashboard HTML estático para a nova fase do estudo.
type GeradorSegundaFase struct {
	titulo string
}

type painelMetrica struct {
	Rotulo    string
	Descricao string
	Valor     *float64
}

type painelMetodoAlvo struct {
	IDMetodo       string
	CaminhoArquivo string
	NomeContainer  string
	NomeMetodo     string
	Assinatura     string
	Origem         string
}

type painelArquivoTeste struct {
	CaminhoRelativo string
	Observacoes     string
	Conteudo        string
	MetodosCobertos []painelMetodoAlvo
}

type painelParMetodoTeste struct {
	Metodo painelMetodoAlvo
	Testes []painelArquivoTeste
}

type painelCenario struct {
	Rotulo                   string
	Descricao                string
	Score                    *float64
	ScoreCombinado           *float64
	ScoreJuiz                *float64
	VereditoJuiz             string
	ForcasJuiz               []string
	FraquezasJuiz            []string
	RiscosJuiz               []string
	ProximasAcoesJuiz        []string
	QuantidadeMetodos        int
	QuantidadeExpaths        int
	QuantidadeTestes         int
	ModoExecucao             string
	RequestCount             int
	RepairUsed               bool
	InputTokens              int
	OutputTokens             int
	EstimatedCost            *float64
	CategoriaFalhaCompilacao string
	IntervencoesHarness      []string
	Metricas                 []painelMetrica
	ArquivosTeste            []painelArquivoTeste
	ParesMetodoTeste         []painelParMetodoTeste
}

type painelProjeto struct {
	Projeto              string
	RotuloProjeto        string
	ContextoWIT          painelCenario
	GeracaoDireta        painelCenario
	DeltaNotaMetricas    *float64
	DeltaCoberturaLinha  *float64
	DeltaCoberturaBranch *float64
	DeltaMutacao         *float64
}

type painelDashboard struct {
	Titulo             string
	IDExecucao         string
	GeradoEm           string
	ChaveModeloGeracao string
	ChaveModeloJuiz    string
	Projetos           []painelProjeto
}

// NovoGeradorSegundaFase instancia o gerador visual com um título amigável.
func NovoGeradorSegundaFase(titulo string) *GeradorSegundaFase {
	return &GeradorSegundaFase{titulo: strings.TrimSpace(titulo)}
}

// Gerar materializa um dashboard HTML com comparação lado a lado por projeto.
func (g *GeradorSegundaFase) Gerar(relatorio dominio.RelatorioSegundaFase, caminhoSaida string) (string, error) {
	if strings.TrimSpace(g.titulo) == "" {
		g.titulo = "Segunda fase: WIT context vs geração direta"
	}
	if err := os.MkdirAll(filepath.Dir(caminhoSaida), 0o755); err != nil {
		return "", fmt.Errorf("ao criar diretório do dashboard: %w", err)
	}

	tpl, err := template.New("dashboard").Funcs(template.FuncMap{
		"metric": metricDisplay,
		"bar":    barWidth,
		"delta":  deltaDisplay,
		"bool":   boolDisplay,
		"cost":   costDisplay,
		"java":   highlightJava,
	}).Parse(dashboardSegundaFaseTemplate)
	if err != nil {
		return "", fmt.Errorf("ao preparar template do dashboard: %w", err)
	}

	arquivo, err := os.Create(caminhoSaida)
	if err != nil {
		return "", fmt.Errorf("ao criar dashboard %q: %w", caminhoSaida, err)
	}
	defer arquivo.Close()

	if err := tpl.Execute(arquivo, construirPainelDashboard(g.titulo, relatorio)); err != nil {
		return "", fmt.Errorf("ao renderizar dashboard %q: %w", caminhoSaida, err)
	}
	return caminhoSaida, nil
}

func construirPainelDashboard(titulo string, relatorio dominio.RelatorioSegundaFase) painelDashboard {
	projetos := make([]painelProjeto, 0, len(relatorio.Projetos))
	for _, projeto := range relatorio.Projetos {
		projetos = append(projetos, painelProjeto{
			Projeto:              projeto.Projeto,
			RotuloProjeto:        projeto.RotuloProjeto,
			ContextoWIT:          construirPainelCenario(projeto.ContextoWIT.RotuloHumano(), projeto.ContextoWIT.DescricaoHumana(), projeto.ContextoWIT),
			GeracaoDireta:        construirPainelCenario(projeto.GeracaoDireta.RotuloHumano(), projeto.GeracaoDireta.DescricaoHumana(), projeto.GeracaoDireta),
			DeltaNotaMetricas:    projeto.DeltaNotaMetricas,
			DeltaCoberturaLinha:  projeto.DeltaCoberturaLinha,
			DeltaCoberturaBranch: projeto.DeltaCoberturaBranch,
			DeltaMutacao:         projeto.DeltaMutacao,
		})
	}

	return painelDashboard{
		Titulo:             titulo,
		IDExecucao:         relatorio.IDExecucao,
		GeradoEm:           relatorio.GeradoEm,
		ChaveModeloGeracao: relatorio.ChaveModeloGeracao,
		ChaveModeloJuiz:    relatorio.ChaveModeloJuiz,
		Projetos:           projetos,
	}
}

func construirPainelCenario(rotulo, descricao string, resultado dominio.ResultadoCenarioSegundaFase) painelCenario {
	painel := painelCenario{
		Rotulo:                   rotulo,
		Descricao:                descricao,
		Score:                    resultado.NotaMetricas,
		ScoreCombinado:           resultado.NotaCombinada,
		QuantidadeMetodos:        resultado.QuantidadeMetodos,
		QuantidadeExpaths:        resultado.QuantidadeExpaths,
		QuantidadeTestes:         resultado.QuantidadeTestes,
		ModoExecucao:             resultado.ModoExecucao,
		RequestCount:             resultado.RequestCount,
		RepairUsed:               resultado.RepairUsed,
		InputTokens:              resultado.InputTokens,
		OutputTokens:             resultado.OutputTokens,
		EstimatedCost:            resultado.EstimatedCost,
		CategoriaFalhaCompilacao: categorizarFalhaCompilacaoVisual(resultado.ResultadosMetricas),
		IntervencoesHarness:      append([]string{}, resultado.IntervencoesHarness...),
		ArquivosTeste:            construirPainelArquivosTeste(resultado),
		ParesMetodoTeste:         construirPainelParesMetodoTeste(resultado),
		Metricas: []painelMetrica{
			novaPainelMetrica("Compilação", "Indica se a suíte gerada compila com sucesso no projeto alvo antes da execução das demais métricas.", valorMetricaPorAlias(resultado.ResultadosMetricas, "test-compilation")),
			novaPainelMetrica("Testes executados", "Quantidade de testes executados com sucesso pelo Surefire. Quando aparece n/d, a suíte não chegou a rodar de forma válida.", valorMetricaPorAlias(resultado.ResultadosMetricas, "unit-tests")),
			novaPainelMetrica("Taxa de sucesso", "Percentual de testes que passaram entre os testes executados. Mede estabilidade da suíte gerada.", valorMetricaPorAlias(resultado.ResultadosMetricas, "test-pass-rate")),
			novaPainelMetrica("Cobertura dos métodos-alvo", "Percentual dos métodos-alvo do estudo que receberam pelo menos um teste associado na geração.", valorMetricaPorAlias(resultado.ResultadosMetricas, "target-method-coverage")),
			novaPainelMetrica("Testes com assertiva", "Percentual de métodos de teste que realmente verificam comportamento com ao menos uma assertiva.", valorMetricaPorAlias(resultado.ResultadosMetricas, "assertive-tests-rate")),
			novaPainelMetrica("Testes de exceção", "Percentual de testes com assertivas voltadas para exceções, útil para checar aderência ao foco do estudo.", valorMetricaPorAlias(resultado.ResultadosMetricas, "exception-assertion-rate")),
			novaPainelMetrica("Java válido", "Percentual de arquivos gerados que parecem Java puro, sem Markdown/HTML e com estrutura mínima de classe.", valorMetricaPorAlias(resultado.ResultadosMetricas, "valid-java-rate")),
			novaPainelMetrica("Package/caminho", "Percentual de arquivos cujo package declarado é compatível com o caminho relativo do teste.", valorMetricaPorAlias(resultado.ResultadosMetricas, "package-path-valid-rate")),
			novaPainelMetrica("Presença de @Test", "Percentual de arquivos gerados que contêm ao menos um método anotado com @Test.", valorMetricaPorAlias(resultado.ResultadosMetricas, "test-method-presence-rate")),
			novaPainelMetrica("Invocação do alvo", "Percentual de métodos-alvo aparentemente invocados pelos testes gerados, usando heurística lexical.", valorMetricaPorAlias(resultado.ResultadosMetricas, "target-invocation-rate")),
			novaPainelMetrica("Deps proibidas", "Percentual de arquivos que usam bibliotecas externas não declaradas no projeto; menor é melhor.", valorMetricaPorAlias(resultado.ResultadosMetricas, "forbidden-dependency-rate")),
			novaPainelMetrica("Uso de reflexão", "Percentual de arquivos que usam reflexão frágil, como getDeclaredField/getDeclaredConstructor/setAccessible. Menor é melhor.", valorMetricaPorAlias(resultado.ResultadosMetricas, "reflection-usage-rate")),
			novaPainelMetrica("AssertThrows frágil", "Percentual de blocos @Test com assertThrows envolvendo reflexão sem tratar InvocationTargetException/getCause. Menor é melhor.", valorMetricaPorAlias(resultado.ResultadosMetricas, "brittle-exception-assertion-rate")),
			novaPainelMetrica("Assert estado interno", "Percentual de arquivos com assertivas sobre campos privados ou estado interno via reflexão. Menor é melhor.", valorMetricaPorAlias(resultado.ResultadosMetricas, "internal-state-assertion-rate")),
			novaPainelMetrica("JaCoCo line", "Cobertura de linhas reportada pelo JaCoCo. Mostra quanto do código mutável foi executado pela suíte.", valorMetricaPorAlias(resultado.ResultadosMetricas, "jacoco-line", "jacoco-line-coverage")),
			novaPainelMetrica("JaCoCo branch", "Cobertura de desvios condicionais reportada pelo JaCoCo. Ajuda a medir exploração de caminhos alternativos.", valorMetricaPorAlias(resultado.ResultadosMetricas, "jacoco-branch", "jacoco-branch-coverage")),
			novaPainelMetrica("PIT mutation", "Mutation score do PIT. Mede a capacidade da suíte de matar mutantes e, portanto, de detectar comportamentos incorretos.", valorMetricaPorAlias(resultado.ResultadosMetricas, "pit-mutation", "pit-mutation-score")),
		},
	}
	construirPainelJuiz(&painel, resultado.AvaliacaoJuiz)
	return painel
}

func construirPainelArquivosTeste(resultado dominio.ResultadoCenarioSegundaFase) []painelArquivoTeste {
	if len(resultado.ArquivosTeste) == 0 {
		return nil
	}

	metodosPorID := make(map[string]painelMetodoAlvo, len(resultado.MetodosAlvo))
	for _, metodo := range resultado.MetodosAlvo {
		metodosPorID[metodo.IDMetodo] = painelMetodoAlvo{
			IDMetodo:       metodo.IDMetodo,
			CaminhoArquivo: metodo.CaminhoArquivo,
			NomeContainer:  metodo.NomeContainer,
			NomeMetodo:     metodo.NomeMetodo,
			Assinatura:     metodo.Assinatura,
			Origem:         metodo.Origem,
		}
	}

	arquivos := make([]painelArquivoTeste, 0, len(resultado.ArquivosTeste))
	for _, arquivo := range resultado.ArquivosTeste {
		painelArquivo := painelArquivoTeste{
			CaminhoRelativo: arquivo.CaminhoRelativo,
			Observacoes:     arquivo.Observacoes,
			Conteudo:        arquivo.Conteudo,
			MetodosCobertos: make([]painelMetodoAlvo, 0, len(arquivo.IDsMetodosCobertos)),
		}
		for _, idMetodo := range arquivo.IDsMetodosCobertos {
			if metodo, ok := metodosPorID[idMetodo]; ok {
				painelArquivo.MetodosCobertos = append(painelArquivo.MetodosCobertos, metodo)
				continue
			}
			painelArquivo.MetodosCobertos = append(painelArquivo.MetodosCobertos, painelMetodoAlvo{
				IDMetodo:   idMetodo,
				NomeMetodo: idMetodo,
				Assinatura: idMetodo,
			})
		}
		arquivos = append(arquivos, painelArquivo)
	}
	return arquivos
}

func construirPainelParesMetodoTeste(resultado dominio.ResultadoCenarioSegundaFase) []painelParMetodoTeste {
	if len(resultado.ParesMetodoTeste) == 0 {
		return nil
	}

	pares := make([]painelParMetodoTeste, 0, len(resultado.ParesMetodoTeste))
	for _, par := range resultado.ParesMetodoTeste {
		painelPar := painelParMetodoTeste{
			Metodo: painelMetodoAlvo{
				IDMetodo:       par.Metodo.IDMetodo,
				CaminhoArquivo: par.Metodo.CaminhoArquivo,
				NomeContainer:  par.Metodo.NomeContainer,
				NomeMetodo:     par.Metodo.NomeMetodo,
				Assinatura:     par.Metodo.Assinatura,
				Origem:         par.Metodo.Origem,
			},
			Testes: make([]painelArquivoTeste, 0, len(par.Testes)),
		}
		for _, teste := range par.Testes {
			painelPar.Testes = append(painelPar.Testes, painelArquivoTeste{
				CaminhoRelativo: teste.CaminhoRelativo,
				Observacoes:     teste.Observacoes,
				Conteudo:        teste.Conteudo,
			})
		}
		pares = append(pares, painelPar)
	}
	return pares
}

func construirPainelJuiz(destino *painelCenario, avaliacao *dominio.AvaliacaoJuiz) {
	if avaliacao == nil {
		return
	}
	destino.ScoreJuiz = &avaliacao.Nota
	destino.VereditoJuiz = strings.TrimSpace(avaliacao.Veredito)
	destino.ForcasJuiz = append([]string{}, avaliacao.Forcas...)
	destino.FraquezasJuiz = append([]string{}, avaliacao.Fraquezas...)
	destino.RiscosJuiz = append([]string{}, avaliacao.Riscos...)
	destino.ProximasAcoesJuiz = append([]string{}, avaliacao.ProximasAcoesRecomendadas...)
}

func novaPainelMetrica(rotulo, descricao string, valor *float64) painelMetrica {
	return painelMetrica{
		Rotulo:    rotulo,
		Descricao: descricao,
		Valor:     valor,
	}
}

func valorMetricaPorAlias(resultados []dominio.ResultadoMetrica, nomes ...string) *float64 {
	for _, nome := range nomes {
		for _, resultado := range resultados {
			if strings.EqualFold(resultado.Nome, nome) {
				if resultado.NotaNormalizada != nil {
					return resultado.NotaNormalizada
				}
				return resultado.ValorNumerico
			}
		}
	}
	return nil
}

func categorizarFalhaCompilacaoVisual(resultados []dominio.ResultadoMetrica) string {
	var compilacao *dominio.ResultadoMetrica
	for indice := range resultados {
		if resultados[indice].Nome == "test-compilation" {
			compilacao = &resultados[indice]
			break
		}
	}
	if compilacao == nil {
		return "not_measured"
	}
	if compilacao.Sucesso {
		return ""
	}
	texto := strings.ToLower(compilacao.SaidaPadrao + "\n" + compilacao.SaidaErro)
	switch {
	case strings.Contains(texto, "tempo limite") || compilacao.TempoEsgotado:
		return "timeout"
	case strings.Contains(texto, "source option") || strings.Contains(texto, "target option") || strings.Contains(texto, "release version"):
		return "jdk_profile"
	case strings.Contains(texto, "package ") && strings.Contains(texto, " does not exist"):
		return "missing_dependency_or_import"
	case strings.Contains(texto, "cannot find symbol"):
		return "unknown_symbol"
	case strings.Contains(texto, "constructor ") && strings.Contains(texto, " cannot be applied"):
		return "constructor_mismatch"
	case strings.Contains(texto, "method ") && strings.Contains(texto, " cannot be applied"):
		return "method_signature_mismatch"
	case strings.Contains(texto, " has private access") || strings.Contains(texto, " has protected access") || strings.Contains(texto, " is not public"):
		return "visibility"
	case strings.Contains(texto, "compilation failure") || strings.Contains(texto, "failed to execute goal"):
		return "compile_error"
	default:
		return "unknown_compile_failure"
	}
}

func metricDisplay(valor *float64) string {
	if valor == nil {
		return "n/d"
	}
	return fmt.Sprintf("%.2f", *valor)
}

func deltaDisplay(valor *float64) string {
	if valor == nil {
		return "n/d"
	}
	sinal := ""
	if *valor > 0 {
		sinal = "+"
	}
	return fmt.Sprintf("%s%.2f", sinal, *valor)
}

func barWidth(valor *float64) string {
	if valor == nil {
		return "0%"
	}
	v := *valor
	if v < 0 {
		v = 0
	}
	if v > 100 {
		v = 100
	}
	return fmt.Sprintf("%.2f%%", v)
}

func boolDisplay(valor bool) string {
	if valor {
		return "sim"
	}
	return "não"
}

func costDisplay(valor *float64) string {
	if valor == nil {
		return "n/d"
	}
	return fmt.Sprintf("US$ %.4f", *valor)
}

var javaKeywords = map[string]struct{}{
	"abstract": {}, "assert": {}, "boolean": {}, "break": {}, "byte": {}, "case": {}, "catch": {}, "char": {},
	"class": {}, "const": {}, "continue": {}, "default": {}, "do": {}, "double": {}, "else": {}, "enum": {},
	"extends": {}, "final": {}, "finally": {}, "float": {}, "for": {}, "if": {}, "implements": {}, "import": {},
	"instanceof": {}, "int": {}, "interface": {}, "long": {}, "native": {}, "new": {}, "null": {}, "package": {},
	"private": {}, "protected": {}, "public": {}, "record": {}, "return": {}, "short": {}, "static": {}, "strictfp": {},
	"super": {}, "switch": {}, "synchronized": {}, "this": {}, "throw": {}, "throws": {}, "transient": {}, "true": {},
	"try": {}, "var": {}, "void": {}, "volatile": {}, "while": {}, "false": {},
}

func highlightJava(codigo string) template.HTML {
	if strings.TrimSpace(codigo) == "" {
		return template.HTML("")
	}

	var out strings.Builder
	for i := 0; i < len(codigo); {
		switch {
		case strings.HasPrefix(codigo[i:], "//"):
			fim := i + 2
			for fim < len(codigo) && codigo[fim] != '\n' {
				fim++
			}
			writeToken(&out, "tok-comment", codigo[i:fim])
			i = fim
		case strings.HasPrefix(codigo[i:], "/*"):
			fim := i + 2
			for fim < len(codigo)-1 && codigo[fim:fim+2] != "*/" {
				fim++
			}
			if fim < len(codigo)-1 {
				fim += 2
			} else {
				fim = len(codigo)
			}
			writeToken(&out, "tok-comment", codigo[i:fim])
			i = fim
		case codigo[i] == '"' || codigo[i] == '\'':
			fim := scanQuotedJavaLiteral(codigo, i)
			writeToken(&out, "tok-string", codigo[i:fim])
			i = fim
		case codigo[i] == '@':
			fim := i + 1
			for fim < len(codigo) && isJavaIdentifierPart(rune(codigo[fim])) {
				fim++
			}
			writeToken(&out, "tok-annotation", codigo[i:fim])
			i = fim
		case isJavaNumberStart(codigo, i):
			fim := i + 1
			for fim < len(codigo) && isJavaNumberPart(rune(codigo[fim])) {
				fim++
			}
			writeToken(&out, "tok-number", codigo[i:fim])
			i = fim
		case isJavaIdentifierStart(rune(codigo[i])):
			fim := i + 1
			for fim < len(codigo) && isJavaIdentifierPart(rune(codigo[fim])) {
				fim++
			}
			termo := codigo[i:fim]
			if _, ok := javaKeywords[termo]; ok {
				writeToken(&out, "tok-keyword", termo)
			} else {
				out.WriteString(html.EscapeString(termo))
			}
			i = fim
		default:
			out.WriteString(html.EscapeString(codigo[i : i+1]))
			i++
		}
	}
	return template.HTML(out.String())
}

func writeToken(out *strings.Builder, classe, valor string) {
	out.WriteString(`<span class="`)
	out.WriteString(classe)
	out.WriteString(`">`)
	out.WriteString(html.EscapeString(valor))
	out.WriteString(`</span>`)
}

func scanQuotedJavaLiteral(codigo string, inicio int) int {
	delim := codigo[inicio]
	fim := inicio + 1
	for fim < len(codigo) {
		if codigo[fim] == '\\' {
			fim += 2
			continue
		}
		if codigo[fim] == delim {
			fim++
			break
		}
		fim++
	}
	if fim > len(codigo) {
		return len(codigo)
	}
	return fim
}

func isJavaIdentifierStart(r rune) bool {
	return r == '_' || r == '$' || unicode.IsLetter(r)
}

func isJavaIdentifierPart(r rune) bool {
	return isJavaIdentifierStart(r) || unicode.IsDigit(r)
}

func isJavaNumberStart(codigo string, idx int) bool {
	if idx >= len(codigo) || !unicode.IsDigit(rune(codigo[idx])) {
		return false
	}
	if idx > 0 && isJavaIdentifierPart(rune(codigo[idx-1])) {
		return false
	}
	return true
}

func isJavaNumberPart(r rune) bool {
	return unicode.IsDigit(r) || r == '_' || r == '.' || r == 'x' || r == 'X' || (r >= 'a' && r <= 'f') || (r >= 'A' && r <= 'F') || r == 'l' || r == 'L'
}

const dashboardSegundaFaseTemplate = `<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>{{ .Titulo }}</title>
  <style>
    :root {
      --bg: #f3efe6;
      --paper: #fbfaf7;
      --ink: #1c2230;
      --muted: #5e6678;
      --accent: #1540d2;
      --accent-soft: #dce5ff;
      --warm: #b96d1f;
      --line: #d8d0c2;
      --shadow: 0 20px 60px rgba(28, 34, 48, 0.08);
    }
    * { box-sizing: border-box; }
    body {
      margin: 0;
      font-family: "Avenir Next", "Segoe UI", sans-serif;
      color: var(--ink);
      background:
        radial-gradient(circle at top left, rgba(21,64,210,0.08), transparent 30%),
        linear-gradient(180deg, #f8f5ee 0%, var(--bg) 100%);
    }
    .shell {
      max-width: 1240px;
      margin: 0 auto;
      padding: 40px 24px 72px;
    }
    .hero {
      display: grid;
      grid-template-columns: 1.3fr 0.7fr;
      gap: 24px;
      align-items: end;
      margin-bottom: 32px;
    }
    .headline {
      background: var(--paper);
      padding: 32px;
      border: 1px solid rgba(21,64,210,0.12);
      box-shadow: var(--shadow);
    }
    .eyebrow {
      letter-spacing: 0.12em;
      text-transform: uppercase;
      color: var(--accent);
      font-size: 12px;
      font-weight: 700;
    }
    h1 {
      margin: 12px 0 14px;
      font-family: "Iowan Old Style", "Palatino Linotype", "Book Antiqua", serif;
      font-size: clamp(36px, 6vw, 60px);
      line-height: 0.98;
      max-width: 10ch;
    }
    .lede {
      margin: 0;
      max-width: 52ch;
      color: var(--muted);
      font-size: 17px;
      line-height: 1.55;
    }
    .meta {
      background: #101726;
      color: #eef3ff;
      padding: 28px;
      min-height: 100%;
      display: flex;
      flex-direction: column;
      justify-content: space-between;
      box-shadow: var(--shadow);
    }
    .meta strong { display: block; font-size: 28px; margin-bottom: 4px; }
    .meta small { color: rgba(238,243,255,0.72); text-transform: uppercase; letter-spacing: 0.08em; }
    .grid {
      display: grid;
      gap: 24px;
    }
    .project {
      background: var(--paper);
      border: 1px solid var(--line);
      box-shadow: var(--shadow);
      overflow: hidden;
    }
    .project-header {
      display: grid;
      grid-template-columns: 1fr auto;
      gap: 16px;
      align-items: end;
      padding: 28px 28px 20px;
      border-bottom: 1px solid var(--line);
      background: linear-gradient(180deg, rgba(21,64,210,0.06), transparent);
    }
    .project-header h2 {
      margin: 0;
      font-family: "Iowan Old Style", "Palatino Linotype", "Book Antiqua", serif;
      font-size: 34px;
    }
    .project-header p {
      margin: 6px 0 0;
      color: var(--muted);
    }
    .delta-strip {
      display: flex;
      flex-wrap: wrap;
      gap: 12px;
      justify-content: flex-end;
    }
    .delta {
      border: 1px solid rgba(21,64,210,0.12);
      background: var(--accent-soft);
      color: var(--accent);
      padding: 10px 14px;
      min-width: 130px;
    }
    .delta span { display: block; font-size: 12px; text-transform: uppercase; letter-spacing: 0.08em; margin-bottom: 4px; color: var(--muted); }
    .compare {
      display: grid;
      grid-template-columns: repeat(2, minmax(0, 1fr));
      gap: 0;
    }
    .scenario {
      padding: 24px 28px 28px;
      border-right: 1px solid var(--line);
    }
    .scenario:last-child { border-right: 0; }
    .scenario h3 {
      margin: 0 0 6px;
      font-size: 20px;
      letter-spacing: 0.02em;
    }
    .scenario p {
      margin: 0 0 20px;
      color: var(--muted);
    }
    .statline {
      display: grid;
      grid-template-columns: 140px 1fr 56px;
      gap: 12px;
      align-items: center;
      margin-bottom: 12px;
      font-size: 14px;
    }
    .metric-label {
      display: inline-flex;
      align-items: center;
      gap: 8px;
      line-height: 1.25;
    }
    .info-badge {
      position: relative;
      display: inline-flex;
      width: 18px;
      height: 18px;
      border-radius: 999px;
      align-items: center;
      justify-content: center;
      font-size: 11px;
      font-weight: 700;
      border: 1px solid rgba(28,34,48,0.18);
      color: var(--muted);
      background: rgba(255,255,255,0.92);
      cursor: help;
      flex: 0 0 auto;
    }
    .info-badge::after {
      content: attr(data-tooltip);
      position: absolute;
      left: calc(100% + 10px);
      top: 50%;
      transform: translateY(-50%);
      width: min(280px, 52vw);
      padding: 10px 12px;
      border-radius: 12px;
      background: #101726;
      color: #eef3ff;
      box-shadow: 0 14px 30px rgba(16, 23, 38, 0.18);
      font-size: 12px;
      line-height: 1.45;
      opacity: 0;
      pointer-events: none;
      transition: opacity 120ms ease;
      z-index: 2;
      text-transform: none;
      letter-spacing: normal;
      font-weight: 500;
    }
    .info-badge:hover::after,
    .info-badge:focus-visible::after {
      opacity: 1;
    }
    .track {
      height: 10px;
      background: #ebe4d7;
      overflow: hidden;
      position: relative;
    }
    .fill {
      height: 100%;
      background: linear-gradient(90deg, var(--accent), #5a7cff);
    }
    .scenario.direct .fill {
      background: linear-gradient(90deg, var(--warm), #d89a52);
    }
    .small-table {
      width: 100%;
      border-collapse: collapse;
      margin-top: 18px;
      font-size: 13px;
    }
    .small-table th, .small-table td {
      padding: 10px 0;
      border-bottom: 1px solid rgba(28,34,48,0.08);
      text-align: left;
    }
    .small-table th:last-child, .small-table td:last-child {
      text-align: right;
    }
    .judge-card {
      margin-top: 18px;
      padding: 16px 18px;
      border: 1px solid rgba(28,34,48,0.08);
      background: rgba(255,255,255,0.66);
    }
    .judge-card h4 {
      margin: 0 0 12px;
      font-size: 15px;
      letter-spacing: 0.02em;
    }
    .judge-headline {
      display: flex;
      flex-wrap: wrap;
      gap: 10px 14px;
      align-items: center;
      margin-bottom: 10px;
      font-size: 13px;
      color: var(--muted);
    }
    .judge-pill {
      display: inline-flex;
      align-items: center;
      gap: 8px;
      padding: 6px 10px;
      border-radius: 999px;
      background: rgba(21,64,210,0.08);
      color: var(--ink);
      font-weight: 600;
    }
    .scenario.direct .judge-pill {
      background: rgba(185,109,31,0.14);
    }
    .judge-grid {
      display: grid;
      grid-template-columns: repeat(2, minmax(0, 1fr));
      gap: 12px 18px;
    }
    .judge-block span {
      display: block;
      margin-bottom: 6px;
      font-size: 11px;
      text-transform: uppercase;
      letter-spacing: 0.08em;
      color: var(--muted);
    }
    .judge-block ul {
      margin: 0;
      padding-left: 18px;
      color: var(--ink);
    }
    .judge-block li + li {
      margin-top: 6px;
    }
    .artifact-card {
      margin-top: 18px;
      border: 1px solid rgba(28,34,48,0.08);
      background: rgba(255,255,255,0.72);
    }
    .artifact-card summary {
      cursor: pointer;
      list-style: none;
      padding: 14px 16px;
      font-weight: 600;
    }
    .artifact-card summary::-webkit-details-marker,
    .artifact-file summary::-webkit-details-marker,
    .artifact-method summary::-webkit-details-marker,
    .pair-card summary::-webkit-details-marker {
      display: none;
    }
    .artifact-body {
      padding: 0 16px 16px;
    }
    .artifact-file {
      border-top: 1px solid rgba(28,34,48,0.08);
      padding-top: 10px;
      margin-top: 10px;
    }
    .artifact-file:first-child {
      border-top: 0;
      padding-top: 0;
      margin-top: 0;
    }
    .artifact-file summary,
    .artifact-method summary {
      cursor: pointer;
      font-weight: 600;
      color: var(--ink);
      padding: 8px 0;
    }
    .artifact-meta {
      color: var(--muted);
      font-size: 12px;
      line-height: 1.5;
      margin-bottom: 8px;
    }
    .artifact-code {
      margin: 8px 0 0;
      overflow: hidden;
      border-radius: 12px;
      border: 1px solid rgba(255,255,255,0.06);
      background: #0f1726;
      box-shadow: inset 0 1px 0 rgba(255,255,255,0.04);
    }
    .code-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      gap: 12px;
      padding: 10px 14px;
      background: rgba(255,255,255,0.04);
      color: rgba(238,243,255,0.8);
      font-size: 11px;
      letter-spacing: 0.08em;
      text-transform: uppercase;
    }
    .artifact-code pre {
      margin: 0;
      padding: 14px;
      overflow-x: auto;
    }
    .artifact-code code {
      display: block;
      color: #eef3ff;
      font-size: 12px;
      line-height: 1.6;
      white-space: pre-wrap;
      word-break: break-word;
      font-family: "SFMono-Regular", "Menlo", "Consolas", monospace;
    }
    .tok-keyword { color: #7cb7ff; font-weight: 600; }
    .tok-string { color: #d7ba7d; }
    .tok-comment { color: #7f8aa3; font-style: italic; }
    .tok-annotation { color: #4ec9b0; }
    .tok-number { color: #f78c6c; }
    .artifact-methods {
      display: grid;
      gap: 8px;
      margin-top: 10px;
    }
    .pair-card {
      margin-top: 18px;
      border: 1px solid rgba(28,34,48,0.08);
      background: rgba(255,255,255,0.72);
    }
    .pair-card summary {
      cursor: pointer;
      list-style: none;
      padding: 14px 16px;
      font-weight: 600;
    }
    .pair-body {
      padding: 0 16px 16px;
      display: grid;
      gap: 14px;
    }
    .pair-stack {
      display: grid;
      gap: 10px;
    }
    .pair-stack h5 {
      margin: 0;
      font-size: 13px;
      letter-spacing: 0.06em;
      text-transform: uppercase;
      color: var(--muted);
    }
    .foot {
      margin-top: 24px;
      color: var(--muted);
      font-size: 13px;
    }
    @media (max-width: 900px) {
      .hero, .compare, .project-header { grid-template-columns: 1fr; }
      .scenario { border-right: 0; border-top: 1px solid var(--line); }
      .scenario:first-child { border-top: 0; }
      .delta-strip { justify-content: flex-start; }
      .judge-grid { grid-template-columns: 1fr; }
      .info-badge::after {
        left: 0;
        top: calc(100% + 10px);
        transform: none;
        width: min(260px, 70vw);
      }
    }
  </style>
</head>
<body>
  <div class="shell">
    <section class="hero">
      <div class="headline">
        <div class="eyebrow">Segunda fase do estudo</div>
        <h1>{{ .Titulo }}</h1>
        <p class="lede">Comparação entre duas estratégias de geração de testes: usar a análise WIT como contexto ou gerar os testes diretamente a partir do código local, mantendo os mesmos métodos-alvo por projeto.</p>
      </div>
      <aside class="meta">
        <div>
          <small>modelo de geração</small>
          <strong>{{ .ChaveModeloGeracao }}</strong>
        </div>
        {{ if .ChaveModeloJuiz }}
        <div>
          <small>modelo juiz</small>
          <strong>{{ .ChaveModeloJuiz }}</strong>
        </div>
        {{ end }}
        <div>
          <small>execução</small>
          <strong>{{ .IDExecucao }}</strong>
          <small>{{ .GeradoEm }}</small>
        </div>
      </aside>
    </section>

    <section class="grid">
      {{ range .Projetos }}
      <article class="project">
        <header class="project-header">
          <div>
            <h2>{{ .RotuloProjeto }}</h2>
            <p>{{ .Projeto }}</p>
          </div>
          <div class="delta-strip">
            <div class="delta"><span>delta score</span>{{ delta .DeltaNotaMetricas }}</div>
            <div class="delta"><span>delta line</span>{{ delta .DeltaCoberturaLinha }}</div>
            <div class="delta"><span>delta branch</span>{{ delta .DeltaCoberturaBranch }}</div>
            <div class="delta"><span>delta mutation</span>{{ delta .DeltaMutacao }}</div>
          </div>
        </header>
        <div class="compare">
          <section class="scenario">
            <h3>{{ .ContextoWIT.Rotulo }}</h3>
            <p>{{ .ContextoWIT.Descricao }}</p>
            <div class="statline"><span class="metric-label">Score<span class="info-badge" tabindex="0" data-tooltip="Score agregado das métricas configuradas para o cenário. Ele resume a qualidade geral da suíte dentro do desenho atual do experimento.">i</span></span><div class="track"><div class="fill" style="width: {{ bar .ContextoWIT.Score }}"></div></div><strong>{{ metric .ContextoWIT.Score }}</strong></div>
            {{ range .ContextoWIT.Metricas }}
            <div class="statline"><span class="metric-label">{{ .Rotulo }}<span class="info-badge" tabindex="0" data-tooltip="{{ .Descricao }}">i</span></span><div class="track"><div class="fill" style="width: {{ bar .Valor }}"></div></div><strong>{{ metric .Valor }}</strong></div>
            {{ end }}
            {{ if .ContextoWIT.ScoreJuiz }}
            <div class="judge-card">
              <h4>Parecer IA</h4>
              <div class="judge-headline">
                <span class="judge-pill">Nota do juiz: {{ metric .ContextoWIT.ScoreJuiz }}</span>
                {{ if .ContextoWIT.ScoreCombinado }}<span>Score combinado: <strong>{{ metric .ContextoWIT.ScoreCombinado }}</strong></span>{{ end }}
                {{ if .ContextoWIT.VereditoJuiz }}<span>Veredito: <strong>{{ .ContextoWIT.VereditoJuiz }}</strong></span>{{ end }}
              </div>
              <div class="judge-grid">
                {{ if .ContextoWIT.ForcasJuiz }}
                <div class="judge-block"><span>Forças</span><ul>{{ range .ContextoWIT.ForcasJuiz }}<li>{{ . }}</li>{{ end }}</ul></div>
                {{ end }}
                {{ if .ContextoWIT.FraquezasJuiz }}
                <div class="judge-block"><span>Fraquezas</span><ul>{{ range .ContextoWIT.FraquezasJuiz }}<li>{{ . }}</li>{{ end }}</ul></div>
                {{ end }}
                {{ if .ContextoWIT.RiscosJuiz }}
                <div class="judge-block"><span>Riscos</span><ul>{{ range .ContextoWIT.RiscosJuiz }}<li>{{ . }}</li>{{ end }}</ul></div>
                {{ end }}
                {{ if .ContextoWIT.ProximasAcoesJuiz }}
                <div class="judge-block"><span>Próximas ações</span><ul>{{ range .ContextoWIT.ProximasAcoesJuiz }}<li>{{ . }}</li>{{ end }}</ul></div>
                {{ end }}
              </div>
            </div>
            {{ end }}
            {{ if .ContextoWIT.ParesMetodoTeste }}
            <details class="pair-card">
              <summary>Ver pares método testado + testes gerados</summary>
              <div class="pair-body">
                {{ range .ContextoWIT.ParesMetodoTeste }}
                <div class="pair-stack">
                  <h5>{{ .Metodo.Assinatura }}</h5>
                  <div class="artifact-meta">Contêiner: {{ .Metodo.NomeContainer }}<br>Arquivo: {{ .Metodo.CaminhoArquivo }}</div>
                  {{ if .Metodo.Origem }}
                  <div class="artifact-code">
                    <div class="code-header"><span>Método-alvo</span><span>java</span></div>
                    <pre><code class="language-java">{{ java .Metodo.Origem }}</code></pre>
                  </div>
                  {{ end }}
                  {{ if .Testes }}
                  {{ range .Testes }}
                  <div class="artifact-code">
                    <div class="code-header"><span>{{ .CaminhoRelativo }}</span><span>java</span></div>
                    <pre><code class="language-java">{{ java .Conteudo }}</code></pre>
                  </div>
                  {{ end }}
                  {{ else }}
                  <div class="artifact-meta">Nenhum teste gerado foi associado explicitamente a este método.</div>
                  {{ end }}
                </div>
                {{ end }}
              </div>
            </details>
            {{ end }}
            {{ if .ContextoWIT.ArquivosTeste }}
            <details class="artifact-card">
              <summary>Ver classe de teste gerada e métodos-alvo</summary>
              <div class="artifact-body">
                {{ range .ContextoWIT.ArquivosTeste }}
                <details class="artifact-file">
                  <summary>{{ .CaminhoRelativo }}</summary>
                  {{ if .Observacoes }}<div class="artifact-meta">Notas da geração: {{ .Observacoes }}</div>{{ end }}
                  {{ if .MetodosCobertos }}
                  <div class="artifact-methods">
                    {{ range .MetodosCobertos }}
                    <details class="artifact-method">
                      <summary>{{ .Assinatura }}</summary>
                      <div class="artifact-meta">Contêiner: {{ .NomeContainer }}<br>Arquivo: {{ .CaminhoArquivo }}</div>
                      {{ if .Origem }}
                      <div class="artifact-code">
                        <div class="code-header"><span>Método-alvo</span><span>java</span></div>
                        <pre><code class="language-java">{{ java .Origem }}</code></pre>
                      </div>
                      {{ end }}
                    </details>
                    {{ end }}
                  </div>
                  {{ end }}
                  <div class="artifact-code">
                    <div class="code-header"><span>{{ .CaminhoRelativo }}</span><span>java</span></div>
                    <pre><code class="language-java">{{ java .Conteudo }}</code></pre>
                  </div>
                </details>
                {{ end }}
              </div>
            </details>
            {{ end }}
            <table class="small-table">
              <tr><th>Modo de execução</th><td>{{ .ContextoWIT.ModoExecucao }}</td></tr>
              <tr><th>Chamadas IA</th><td>{{ .ContextoWIT.RequestCount }}</td></tr>
              <tr><th>Repair usado</th><td>{{ bool .ContextoWIT.RepairUsed }}</td></tr>
              <tr><th>Tokens de entrada</th><td>{{ .ContextoWIT.InputTokens }}</td></tr>
              <tr><th>Tokens de saída</th><td>{{ .ContextoWIT.OutputTokens }}</td></tr>
              <tr><th>Custo estimado</th><td>{{ cost .ContextoWIT.EstimatedCost }}</td></tr>
              {{ if .ContextoWIT.CategoriaFalhaCompilacao }}<tr><th>Categoria da falha</th><td>{{ .ContextoWIT.CategoriaFalhaCompilacao }}</td></tr>{{ end }}
              {{ if .ContextoWIT.IntervencoesHarness }}<tr><th>Intervenções harness</th><td>{{ range .ContextoWIT.IntervencoesHarness }}<code>{{ . }}</code><br>{{ end }}</td></tr>{{ end }}
              <tr><th>Métodos</th><td>{{ .ContextoWIT.QuantidadeMetodos }}</td></tr>
              <tr><th>Expaths</th><td>{{ .ContextoWIT.QuantidadeExpaths }}</td></tr>
              <tr><th>Arquivos de teste</th><td>{{ .ContextoWIT.QuantidadeTestes }}</td></tr>
            </table>
          </section>
          <section class="scenario direct">
            <h3>{{ .GeracaoDireta.Rotulo }}</h3>
            <p>{{ .GeracaoDireta.Descricao }}</p>
            <div class="statline"><span class="metric-label">Score<span class="info-badge" tabindex="0" data-tooltip="Score agregado das métricas configuradas para o cenário. Ele resume a qualidade geral da suíte dentro do desenho atual do experimento.">i</span></span><div class="track"><div class="fill" style="width: {{ bar .GeracaoDireta.Score }}"></div></div><strong>{{ metric .GeracaoDireta.Score }}</strong></div>
            {{ range .GeracaoDireta.Metricas }}
            <div class="statline"><span class="metric-label">{{ .Rotulo }}<span class="info-badge" tabindex="0" data-tooltip="{{ .Descricao }}">i</span></span><div class="track"><div class="fill" style="width: {{ bar .Valor }}"></div></div><strong>{{ metric .Valor }}</strong></div>
            {{ end }}
            {{ if .GeracaoDireta.ScoreJuiz }}
            <div class="judge-card">
              <h4>Parecer IA</h4>
              <div class="judge-headline">
                <span class="judge-pill">Nota do juiz: {{ metric .GeracaoDireta.ScoreJuiz }}</span>
                {{ if .GeracaoDireta.ScoreCombinado }}<span>Score combinado: <strong>{{ metric .GeracaoDireta.ScoreCombinado }}</strong></span>{{ end }}
                {{ if .GeracaoDireta.VereditoJuiz }}<span>Veredito: <strong>{{ .GeracaoDireta.VereditoJuiz }}</strong></span>{{ end }}
              </div>
              <div class="judge-grid">
                {{ if .GeracaoDireta.ForcasJuiz }}
                <div class="judge-block"><span>Forças</span><ul>{{ range .GeracaoDireta.ForcasJuiz }}<li>{{ . }}</li>{{ end }}</ul></div>
                {{ end }}
                {{ if .GeracaoDireta.FraquezasJuiz }}
                <div class="judge-block"><span>Fraquezas</span><ul>{{ range .GeracaoDireta.FraquezasJuiz }}<li>{{ . }}</li>{{ end }}</ul></div>
                {{ end }}
                {{ if .GeracaoDireta.RiscosJuiz }}
                <div class="judge-block"><span>Riscos</span><ul>{{ range .GeracaoDireta.RiscosJuiz }}<li>{{ . }}</li>{{ end }}</ul></div>
                {{ end }}
                {{ if .GeracaoDireta.ProximasAcoesJuiz }}
                <div class="judge-block"><span>Próximas ações</span><ul>{{ range .GeracaoDireta.ProximasAcoesJuiz }}<li>{{ . }}</li>{{ end }}</ul></div>
                {{ end }}
              </div>
            </div>
            {{ end }}
            {{ if .GeracaoDireta.ParesMetodoTeste }}
            <details class="pair-card">
              <summary>Ver pares método testado + testes gerados</summary>
              <div class="pair-body">
                {{ range .GeracaoDireta.ParesMetodoTeste }}
                <div class="pair-stack">
                  <h5>{{ .Metodo.Assinatura }}</h5>
                  <div class="artifact-meta">Contêiner: {{ .Metodo.NomeContainer }}<br>Arquivo: {{ .Metodo.CaminhoArquivo }}</div>
                  {{ if .Metodo.Origem }}
                  <div class="artifact-code">
                    <div class="code-header"><span>Método-alvo</span><span>java</span></div>
                    <pre><code class="language-java">{{ java .Metodo.Origem }}</code></pre>
                  </div>
                  {{ end }}
                  {{ if .Testes }}
                  {{ range .Testes }}
                  <div class="artifact-code">
                    <div class="code-header"><span>{{ .CaminhoRelativo }}</span><span>java</span></div>
                    <pre><code class="language-java">{{ java .Conteudo }}</code></pre>
                  </div>
                  {{ end }}
                  {{ else }}
                  <div class="artifact-meta">Nenhum teste gerado foi associado explicitamente a este método.</div>
                  {{ end }}
                </div>
                {{ end }}
              </div>
            </details>
            {{ end }}
            {{ if .GeracaoDireta.ArquivosTeste }}
            <details class="artifact-card">
              <summary>Ver classe de teste gerada e métodos-alvo</summary>
              <div class="artifact-body">
                {{ range .GeracaoDireta.ArquivosTeste }}
                <details class="artifact-file">
                  <summary>{{ .CaminhoRelativo }}</summary>
                  {{ if .Observacoes }}<div class="artifact-meta">Notas da geração: {{ .Observacoes }}</div>{{ end }}
                  {{ if .MetodosCobertos }}
                  <div class="artifact-methods">
                    {{ range .MetodosCobertos }}
                    <details class="artifact-method">
                      <summary>{{ .Assinatura }}</summary>
                      <div class="artifact-meta">Contêiner: {{ .NomeContainer }}<br>Arquivo: {{ .CaminhoArquivo }}</div>
                      {{ if .Origem }}
                      <div class="artifact-code">
                        <div class="code-header"><span>Método-alvo</span><span>java</span></div>
                        <pre><code class="language-java">{{ java .Origem }}</code></pre>
                      </div>
                      {{ end }}
                    </details>
                    {{ end }}
                  </div>
                  {{ end }}
                  <div class="artifact-code">
                    <div class="code-header"><span>{{ .CaminhoRelativo }}</span><span>java</span></div>
                    <pre><code class="language-java">{{ java .Conteudo }}</code></pre>
                  </div>
                </details>
                {{ end }}
              </div>
            </details>
            {{ end }}
            <table class="small-table">
              <tr><th>Modo de execução</th><td>{{ .GeracaoDireta.ModoExecucao }}</td></tr>
              <tr><th>Chamadas IA</th><td>{{ .GeracaoDireta.RequestCount }}</td></tr>
              <tr><th>Repair usado</th><td>{{ bool .GeracaoDireta.RepairUsed }}</td></tr>
              <tr><th>Tokens de entrada</th><td>{{ .GeracaoDireta.InputTokens }}</td></tr>
              <tr><th>Tokens de saída</th><td>{{ .GeracaoDireta.OutputTokens }}</td></tr>
              <tr><th>Custo estimado</th><td>{{ cost .GeracaoDireta.EstimatedCost }}</td></tr>
              {{ if .GeracaoDireta.CategoriaFalhaCompilacao }}<tr><th>Categoria da falha</th><td>{{ .GeracaoDireta.CategoriaFalhaCompilacao }}</td></tr>{{ end }}
              {{ if .GeracaoDireta.IntervencoesHarness }}<tr><th>Intervenções harness</th><td>{{ range .GeracaoDireta.IntervencoesHarness }}<code>{{ . }}</code><br>{{ end }}</td></tr>{{ end }}
              <tr><th>Métodos</th><td>{{ .GeracaoDireta.QuantidadeMetodos }}</td></tr>
              <tr><th>Expaths</th><td>{{ .GeracaoDireta.QuantidadeExpaths }}</td></tr>
              <tr><th>Arquivos de teste</th><td>{{ .GeracaoDireta.QuantidadeTestes }}</td></tr>
            </table>
          </section>
        </div>
      </article>
      {{ end }}
    </section>

    <p class="foot">Artefatos complementares: CSV de resumo, CSV de métricas e JSON consolidado na mesma pasta desta visualização.</p>
  </div>
</body>
</html>`
