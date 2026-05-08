package aplicacao

import (
	"encoding/csv"
	"fmt"
	"math"
	"math/rand"
	"os"
	"path/filepath"
	"sort"
	"strconv"
	"strings"
)

const (
	seedBootstrapPrimeiraRodada     int64 = 20260429
	amostrasBootstrapPrimeiraRodada       = 2000
)

type caminhoEstatisticasPrimeiraRodada struct {
	Manifesto  string
	Resumo     string
	Metricas   string
	Comparacao string
	Saida      string
}

type metricaPareadaPrimeiraRodada struct {
	Nome        string
	ColunaDelta string
	Descricao   string
}

type resultadoEstatisticoPrimeiraRodada struct {
	Metrica        metricaPareadaPrimeiraRodada
	N              int
	Media          float64
	Mediana        float64
	ICBaixo        float64
	ICAlto         float64
	Positivos      int
	Negativos      int
	Zeros          int
	ValorPSinal    float64
	WilcoxonZ      float64
	WilcoxonValorP float64
}

type resumoManifestoPrimeiraRodada struct {
	TotalSlices int
	Projetos    map[string]int
}

var metricasPareadasPrimeiraRodada = []metricaPareadaPrimeiraRodada{
	{
		Nome:        "metric_score",
		ColunaDelta: "delta_metric_score",
		Descricao:   "Score objetivo agregado calculado pelas métricas do pipeline.",
	},
	{
		Nome:        "combined_score",
		ColunaDelta: "delta_combined_score",
		Descricao:   "Score combinado; na rodada principal tende a coincidir com metric_score porque o juiz IA fica desativado.",
	},
	{
		Nome:        "test_pass_rate",
		ColunaDelta: "delta_test_pass_rate",
		Descricao:   "Percentual de testes gerados que passaram.",
	},
	{
		Nome:        "target_method_coverage",
		ColunaDelta: "delta_target_method_coverage",
		Descricao:   "Percentual de métodos-alvo cobertos por pelo menos um teste gerado.",
	},
	{
		Nome:        "assertive_tests_rate",
		ColunaDelta: "delta_assertive_tests_rate",
		Descricao:   "Percentual de testes gerados com assertivas explícitas.",
	},
	{
		Nome:        "exception_assertion_rate",
		ColunaDelta: "delta_exception_assertion_rate",
		Descricao:   "Percentual de testes gerados que verificam exceções.",
	},
	{
		Nome:        "valid_java_rate",
		ColunaDelta: "delta_valid_java_rate",
		Descricao:   "Percentual de arquivos gerados que parecem Java puro, sem Markdown ou HTML.",
	},
	{
		Nome:        "package_path_valid_rate",
		ColunaDelta: "delta_package_path_valid_rate",
		Descricao:   "Percentual de arquivos cujo package é compatível com o caminho relativo do teste.",
	},
	{
		Nome:        "test_method_presence_rate",
		ColunaDelta: "delta_test_method_presence_rate",
		Descricao:   "Percentual de arquivos gerados que contêm pelo menos um método anotado com @Test.",
	},
	{
		Nome:        "target_invocation_rate",
		ColunaDelta: "delta_target_invocation_rate",
		Descricao:   "Percentual de métodos-alvo aparentemente invocados pelos testes gerados.",
	},
	{
		Nome:        "forbidden_dependency_rate",
		ColunaDelta: "delta_forbidden_dependency_rate",
		Descricao:   "Percentual de arquivos que usam dependências externas não declaradas; valores menores são melhores.",
	},
	{
		Nome:        "reflection_usage_rate",
		ColunaDelta: "delta_reflection_usage_rate",
		Descricao:   "Percentual de arquivos que usam reflexão frágil; valores menores são melhores e deltas negativos indicam menor fragilidade no cenário WIT.",
	},
	{
		Nome:        "brittle_exception_assertion_rate",
		ColunaDelta: "delta_brittle_exception_assertion_rate",
		Descricao:   "Percentual de blocos de teste com assertThrows frágil envolvendo reflexão; valores menores são melhores e deltas negativos indicam menor fragilidade no cenário WIT.",
	},
	{
		Nome:        "internal_state_assertion_rate",
		ColunaDelta: "delta_internal_state_assertion_rate",
		Descricao:   "Percentual de arquivos com assertivas sobre campos privados/estado interno; valores menores são melhores e deltas negativos indicam menor fragilidade no cenário WIT.",
	},
	{
		Nome:        "jacoco_line",
		ColunaDelta: "delta_jacoco_line",
		Descricao:   "Diferença pareada na cobertura de linhas JaCoCo.",
	},
	{
		Nome:        "jacoco_branch",
		ColunaDelta: "delta_jacoco_branch",
		Descricao:   "Diferença pareada na cobertura de branches JaCoCo.",
	},
	{
		Nome:        "pit_mutation",
		ColunaDelta: "delta_pit_mutation",
		Descricao:   "Diferença pareada no mutation score do PIT.",
	},
	{
		Nome:        "request_count",
		ColunaDelta: "delta_request_count",
		Descricao:   "Diferença pareada no número de requisições LLM usadas.",
	},
	{
		Nome:        "input_tokens",
		ColunaDelta: "delta_input_tokens",
		Descricao:   "Diferença pareada no consumo de tokens de entrada.",
	},
	{
		Nome:        "output_tokens",
		ColunaDelta: "delta_output_tokens",
		Descricao:   "Diferença pareada no consumo de tokens de saída.",
	},
	{
		Nome:        "estimated_cost",
		ColunaDelta: "delta_estimated_cost",
		Descricao:   "Diferença pareada no custo estimado da chamada LLM.",
	},
}

func consolidarEstatisticasPrimeiraRodada(caminhos caminhoEstatisticasPrimeiraRodada) (string, string, error) {
	if err := validarEntradasEstatisticasPrimeiraRodada(caminhos); err != nil {
		return "", "", err
	}
	if err := os.MkdirAll(caminhos.Saida, 0o755); err != nil {
		return "", "", fmt.Errorf("ao criar diretório de saída estatística: %w", err)
	}

	manifesto, err := lerCSVComoMapas(caminhos.Manifesto)
	if err != nil {
		return "", "", fmt.Errorf("ao ler manifesto estatístico: %w", err)
	}
	comparacao, err := lerCSVComoMapas(caminhos.Comparacao)
	if err != nil {
		return "", "", fmt.Errorf("ao ler CSV de comparação: %w", err)
	}

	resumoManifesto := resumirManifestoPrimeiraRodada(manifesto)
	resultados := make([]resultadoEstatisticoPrimeiraRodada, 0, len(metricasPareadasPrimeiraRodada))
	for _, metrica := range metricasPareadasPrimeiraRodada {
		valores := coletarDeltasWITMenosDireto(comparacao, metrica.ColunaDelta)
		if len(valores) == 0 {
			continue
		}
		resultados = append(resultados, calcularResultadoEstatisticoPrimeiraRodada(metrica, valores))
	}
	if len(resultados) == 0 {
		return "", "", fmt.Errorf("nenhuma coluna de delta pareado pôde ser consolidada em %s", caminhos.Comparacao)
	}

	csvPath := filepath.Join(caminhos.Saida, "phase-two-statistics.csv")
	if err := escreverCSVEstatisticasPrimeiraRodada(csvPath, resultados); err != nil {
		return "", "", err
	}
	mdPath := filepath.Join(caminhos.Saida, "phase-two-statistics.md")
	if err := os.WriteFile(mdPath, []byte(renderizarMarkdownEstatisticasPrimeiraRodada(resumoManifesto, resultados)), 0o644); err != nil {
		return "", "", fmt.Errorf("ao escrever resumo estatístico Markdown: %w", err)
	}
	return csvPath, mdPath, nil
}

func validarEntradasEstatisticasPrimeiraRodada(caminhos caminhoEstatisticasPrimeiraRodada) error {
	obrigatorios := map[string]string{
		"--manifest":   caminhos.Manifesto,
		"--summary":    caminhos.Resumo,
		"--metrics":    caminhos.Metricas,
		"--comparison": caminhos.Comparacao,
		"--output-dir": caminhos.Saida,
	}
	for nome, caminho := range obrigatorios {
		if strings.TrimSpace(caminho) == "" {
			return fmt.Errorf("%s é obrigatório", nome)
		}
		if nome == "--output-dir" {
			continue
		}
		info, err := os.Stat(caminho)
		if err != nil {
			return fmt.Errorf("%s=%q: %w", nome, caminho, err)
		}
		if info.IsDir() {
			return fmt.Errorf("%s=%q deve ser arquivo", nome, caminho)
		}
	}
	return nil
}

func lerCSVComoMapas(caminho string) ([]map[string]string, error) {
	arquivo, err := os.Open(caminho)
	if err != nil {
		return nil, err
	}
	defer arquivo.Close()

	leitor := csv.NewReader(arquivo)
	leitor.FieldsPerRecord = -1
	linhas, err := leitor.ReadAll()
	if err != nil {
		return nil, err
	}
	if len(linhas) == 0 {
		return nil, fmt.Errorf("CSV vazio")
	}
	cabecalho := linhas[0]
	registros := make([]map[string]string, 0, len(linhas)-1)
	for _, linha := range linhas[1:] {
		registro := map[string]string{}
		for indice, coluna := range cabecalho {
			if indice < len(linha) {
				registro[coluna] = linha[indice]
			} else {
				registro[coluna] = ""
			}
		}
		registros = append(registros, registro)
	}
	return registros, nil
}

func resumirManifestoPrimeiraRodada(registros []map[string]string) resumoManifestoPrimeiraRodada {
	resumo := resumoManifestoPrimeiraRodada{
		TotalSlices: len(registros),
		Projetos:    map[string]int{},
	}
	for _, registro := range registros {
		chave := strings.TrimSpace(registro["source_project_key"])
		if chave == "" {
			chave = strings.TrimSpace(registro["project_key"])
		}
		if chave == "" {
			chave = "desconhecido"
		}
		resumo.Projetos[chave]++
	}
	return resumo
}

func coletarDeltasWITMenosDireto(registros []map[string]string, coluna string) []float64 {
	valores := make([]float64, 0, len(registros))
	for _, registro := range registros {
		bruto := strings.TrimSpace(registro[coluna])
		if bruto == "" {
			continue
		}
		valor, err := strconv.ParseFloat(bruto, 64)
		if err != nil || math.IsNaN(valor) || math.IsInf(valor, 0) {
			continue
		}
		if strings.TrimSpace(registro["delta_direction"]) == "WIT_CONTEXT_MINUS_DIRECT_TESTS" {
			valores = append(valores, valor)
			continue
		}
		// CSVs antigos não possuem delta_direction e exportavam
		// DIRECT_TESTS - WIT_CONTEXT. A estatística reporta WIT_CONTEXT - DIRECT_TESTS.
		valores = append(valores, -valor)
	}
	return valores
}

func calcularResultadoEstatisticoPrimeiraRodada(metrica metricaPareadaPrimeiraRodada, valores []float64) resultadoEstatisticoPrimeiraRodada {
	positivos, negativos, zeros := contarSinais(valores)
	wilcoxonZ, wilcoxonP := wilcoxonAproximado(valores)
	baixo, alto := intervaloBootstrapMedia(valores, amostrasBootstrapPrimeiraRodada, seedBootstrapPrimeiraRodada)
	return resultadoEstatisticoPrimeiraRodada{
		Metrica:        metrica,
		N:              len(valores),
		Media:          media(valores),
		Mediana:        mediana(valores),
		ICBaixo:        baixo,
		ICAlto:         alto,
		Positivos:      positivos,
		Negativos:      negativos,
		Zeros:          zeros,
		ValorPSinal:    testeSinalBilateral(positivos, negativos),
		WilcoxonZ:      wilcoxonZ,
		WilcoxonValorP: wilcoxonP,
	}
}

func escreverCSVEstatisticasPrimeiraRodada(caminho string, resultados []resultadoEstatisticoPrimeiraRodada) error {
	linhas := [][]string{{
		"metric",
		"n",
		"mean_delta_wit_minus_direct",
		"median_delta_wit_minus_direct",
		"bootstrap_ci95_low",
		"bootstrap_ci95_high",
		"positive_count",
		"negative_count",
		"zero_count",
		"sign_test_p_value",
		"wilcoxon_z",
		"wilcoxon_p_value",
		"description",
	}}
	for _, resultado := range resultados {
		linhas = append(linhas, []string{
			resultado.Metrica.Nome,
			strconv.Itoa(resultado.N),
			formatarFloatEstatistico(resultado.Media),
			formatarFloatEstatistico(resultado.Mediana),
			formatarFloatEstatistico(resultado.ICBaixo),
			formatarFloatEstatistico(resultado.ICAlto),
			strconv.Itoa(resultado.Positivos),
			strconv.Itoa(resultado.Negativos),
			strconv.Itoa(resultado.Zeros),
			formatarFloatEstatistico(resultado.ValorPSinal),
			formatarFloatEstatistico(resultado.WilcoxonZ),
			formatarFloatEstatistico(resultado.WilcoxonValorP),
			resultado.Metrica.Descricao,
		})
	}
	return escreverCSV(caminho, linhas)
}

func renderizarMarkdownEstatisticasPrimeiraRodada(resumo resumoManifestoPrimeiraRodada, resultados []resultadoEstatisticoPrimeiraRodada) string {
	var builder strings.Builder
	builder.WriteString("# Primeira rodada estatística\n\n")
	builder.WriteString("Este resumo consolida deltas pareados no sentido `WIT_CONTEXT - DIRECT_TESTS`. Valores positivos favorecem o uso do contexto WIT; valores negativos favorecem a geração direta a partir do método cru.\n\n")
	builder.WriteString(fmt.Sprintf("- Slices pareados: %d\n", resumo.TotalSlices))
	builder.WriteString("- Bootstrap: 2000 reamostragens determinísticas, seed 20260429\n")
	builder.WriteString("- Teste de sinal: bilateral, ignorando empates\n")
	builder.WriteString("- Wilcoxon: aproximação normal sobre postos sinalizados, ignorando deltas zero\n")
	builder.WriteString("\n")
	if len(resumo.Projetos) > 0 {
		builder.WriteString("## Amostra\n\n")
		projetos := make([]string, 0, len(resumo.Projetos))
		for projeto := range resumo.Projetos {
			projetos = append(projetos, projeto)
		}
		sort.Strings(projetos)
		for _, projeto := range projetos {
			builder.WriteString(fmt.Sprintf("- `%s`: %d slices\n", projeto, resumo.Projetos[projeto]))
		}
		builder.WriteString("\n")
	}
	builder.WriteString("## Resultados Pareados\n\n")
	builder.WriteString("| Métrica | n | Média | Mediana | IC 95% bootstrap | Sinal p | Wilcoxon p |\n")
	builder.WriteString("| --- | ---: | ---: | ---: | ---: | ---: | ---: |\n")
	for _, resultado := range resultados {
		builder.WriteString(fmt.Sprintf(
			"| `%s` | %d | %s | %s | [%s, %s] | %s | %s |\n",
			resultado.Metrica.Nome,
			resultado.N,
			formatarFloatEstatistico(resultado.Media),
			formatarFloatEstatistico(resultado.Mediana),
			formatarFloatEstatistico(resultado.ICBaixo),
			formatarFloatEstatistico(resultado.ICAlto),
			formatarFloatEstatistico(resultado.ValorPSinal),
			formatarFloatEstatistico(resultado.WilcoxonValorP),
		))
	}
	builder.WriteString("\n")
	builder.WriteString("## Leitura\n\n")
	builder.WriteString("Use `metric_score`, `test_pass_rate`, `target_method_coverage`, `jacoco_line`, `jacoco_branch` e `pit_mutation` como indicadores primários de qualidade. Use `reflection_usage_rate`, `brittle_exception_assertion_rate`, `internal_state_assertion_rate` e `forbidden_dependency_rate` como diagnósticos de fragilidade; nesses casos, valores menores são melhores. Use `request_count`, `input_tokens`, `output_tokens` e `estimated_cost` para auditar igualdade de orçamento e custo entre os cenários.\n")
	return builder.String()
}

func media(valores []float64) float64 {
	if len(valores) == 0 {
		return math.NaN()
	}
	soma := 0.0
	for _, valor := range valores {
		soma += valor
	}
	return soma / float64(len(valores))
}

func mediana(valores []float64) float64 {
	if len(valores) == 0 {
		return math.NaN()
	}
	ordenados := append([]float64{}, valores...)
	sort.Float64s(ordenados)
	meio := len(ordenados) / 2
	if len(ordenados)%2 == 1 {
		return ordenados[meio]
	}
	return (ordenados[meio-1] + ordenados[meio]) / 2
}

func contarSinais(valores []float64) (int, int, int) {
	positivos, negativos, zeros := 0, 0, 0
	for _, valor := range valores {
		switch {
		case valor > 0:
			positivos++
		case valor < 0:
			negativos++
		default:
			zeros++
		}
	}
	return positivos, negativos, zeros
}

func intervaloBootstrapMedia(valores []float64, iteracoes int, seed int64) (float64, float64) {
	if len(valores) == 0 || iteracoes <= 0 {
		return math.NaN(), math.NaN()
	}
	gerador := rand.New(rand.NewSource(seed))
	medias := make([]float64, iteracoes)
	for i := 0; i < iteracoes; i++ {
		soma := 0.0
		for j := 0; j < len(valores); j++ {
			soma += valores[gerador.Intn(len(valores))]
		}
		medias[i] = soma / float64(len(valores))
	}
	sort.Float64s(medias)
	baixo := int(math.Floor(0.025 * float64(iteracoes-1)))
	alto := int(math.Floor(0.975 * float64(iteracoes-1)))
	return medias[baixo], medias[alto]
}

func testeSinalBilateral(positivos, negativos int) float64 {
	n := positivos + negativos
	if n == 0 {
		return math.NaN()
	}
	k := positivos
	if negativos < positivos {
		k = negativos
	}
	probabilidade := math.Pow(0.5, float64(n))
	soma := probabilidade
	for i := 1; i <= k; i++ {
		probabilidade *= float64(n-i+1) / float64(i)
		soma += probabilidade
	}
	p := 2 * soma
	if p > 1 {
		return 1
	}
	return p
}

func wilcoxonAproximado(valores []float64) (float64, float64) {
	type item struct {
		absoluto float64
		sinal    float64
	}
	itens := make([]item, 0, len(valores))
	for _, valor := range valores {
		if valor == 0 {
			continue
		}
		sinal := -1.0
		if valor > 0 {
			sinal = 1.0
		}
		itens = append(itens, item{absoluto: math.Abs(valor), sinal: sinal})
	}
	n := len(itens)
	if n == 0 {
		return math.NaN(), math.NaN()
	}
	sort.Slice(itens, func(i, j int) bool {
		return itens[i].absoluto < itens[j].absoluto
	})

	wMais := 0.0
	for i := 0; i < n; {
		j := i + 1
		for j < n && itens[j].absoluto == itens[i].absoluto {
			j++
		}
		postoMedio := (float64(i+1) + float64(j)) / 2
		for k := i; k < j; k++ {
			if itens[k].sinal > 0 {
				wMais += postoMedio
			}
		}
		i = j
	}
	mediaW := float64(n*(n+1)) / 4
	varianciaW := float64(n*(n+1)*(2*n+1)) / 24
	if varianciaW == 0 {
		return math.NaN(), math.NaN()
	}
	z := (wMais - mediaW) / math.Sqrt(varianciaW)
	p := math.Erfc(math.Abs(z) / math.Sqrt2)
	return z, p
}

func formatarFloatEstatistico(valor float64) string {
	if math.IsNaN(valor) || math.IsInf(valor, 0) {
		return ""
	}
	if math.Abs(valor) < 0.00005 {
		valor = 0
	}
	return strconv.FormatFloat(valor, 'f', 4, 64)
}
