package witup

import (
	"fmt"
	"path/filepath"
	"sort"
	"strings"

	"github.com/marceloamorim/witup-llm/internal/artefatos"
	"github.com/marceloamorim/witup-llm/internal/dominio"
)

type baselineBruta struct {
	Path       string        `json:"path"`
	HashCommit string        `json:"commitHash"`
	Classes    []classeBruta `json:"classes"`
}

type classeBruta struct {
	Path    string        `json:"path"`
	Metodos []metodoBruto `json:"methods"`
}

type metodoBruto struct {
	QualifiedSignature        string   `json:"qualifiedSignature"`
	Exception                 string   `json:"exception"`
	PathConjunction           string   `json:"pathCojunction"`
	SymbolicPathConjunction   string   `json:"symbolicPathConjunction"`
	BackwardsPathConjunction  string   `json:"backwardsPathConjunction"`
	SimplifiedPathConjunction string   `json:"simplifiedPathConjunction"`
	Z3Inputs                  string   `json:"z3Inputs"`
	SoundSymbolic             bool     `json:"soundSymbolic"`
	SoundBackwards            bool     `json:"soundBackwards"`
	Maybe                     bool     `json:"maybe"`
	Line                      int      `json:"line"`
	ThrowingLine              int      `json:"throwingLine"`
	IsStatic                  bool     `json:"isStatic"`
	TargetOnlyArguments       bool     `json:"targetOnlyArguments"`
	CallSequence              []string `json:"callSequence"`
	InlineSequence            []string `json:"inlineSequence"`
}

type metodoAgrupado struct {
	descritor      dominio.DescritorMetodo
	caminhos       []dominio.CaminhoExcecao
	entradasBrutas []metodoBruto
}

// CarregarAnalise converte um arquivo de baseline do WITUP para o artefato canônico
// de análise usado pelo restante do projeto.
func CarregarAnalise(path string) (dominio.RelatorioAnalise, error) {
	baseline := baselineBruta{}
	if err := artefatos.LerJSON(path, &baseline); err != nil {
		return dominio.RelatorioAnalise{}, err
	}

	metodosAgrupados := map[string]*metodoAgrupado{}
	for _, classe := range baseline.Classes {
		caminhoNormalizado := normalizarCaminhoArquivo(classe.Path)
		for index, metodo := range classe.Metodos {
			descritor := construirDescritorMetodo(caminhoNormalizado, metodo)
			chave := descritor.Assinatura + "|" + descritor.CaminhoArquivo
			registro, ok := metodosAgrupados[chave]
			if !ok {
				registro = &metodoAgrupado{descritor: descritor}
				metodosAgrupados[chave] = registro
			}
			registro.entradasBrutas = append(registro.entradasBrutas, metodo)
			registro.caminhos = append(registro.caminhos, construirCaminhoExcecao(descritor.IDMetodo, metodo, index+1, baseline.HashCommit))
		}
	}

	chaves := make([]string, 0, len(metodosAgrupados))
	for chave := range metodosAgrupados {
		chaves = append(chaves, chave)
	}
	sort.Strings(chaves)

	analises := make([]dominio.AnaliseMetodo, 0, len(chaves))
	for _, chave := range chaves {
		registro := metodosAgrupados[chave]
		respostaBruta := map[string]interface{}{
			"baseline":    "witup_article",
			"entry_count": len(registro.entradasBrutas),
			"raw_entries": registro.entradasBrutas,
		}
		analises = append(analises, dominio.AnaliseMetodo{
			Metodo:          registro.descritor,
			ResumoMetodo:    "Importado do pacote de replicação do WITUP.",
			CaminhosExcecao: registro.caminhos,
			RespostaBruta:   respostaBruta,
		})
	}

	return dominio.RelatorioAnalise{
		IDExecucao:   artefatos.NovoIDExecucao("witup-baseline"),
		RaizProjeto:  normalizarRaizProjeto(baseline.Path),
		ChaveModelo:  "witup_article",
		Origem:       dominio.OrigemExpathWITUP,
		Estrategia:   "witup_baseline_import",
		GeradoEm:     dominio.HorarioUTC(),
		TotalMetodos: len(analises),
		Analises:     analises,
	}, nil
}

// ResolverCaminhoBaseline aponta para um arquivo dentro do pacote de replicação local.
func ResolverCaminhoBaseline(raizReplicacao, chaveProjeto, nomeArquivo string) string {
	return filepath.Join(raizReplicacao, chaveProjeto, nomeArquivo)
}

// construirDescritorMetodo monta o descritor canônico de método a partir da entrada bruta.
func construirDescritorMetodo(caminhoClasse string, metodo metodoBruto) dominio.DescritorMetodo {
	assinatura := strings.TrimSpace(metodo.QualifiedSignature)
	nomeContainer, nomeMetodo := separarAssinaturaQualificada(assinatura)
	return dominio.DescritorMetodo{
		IDMetodo:       assinatura,
		CaminhoArquivo: caminhoClasse,
		NomeContainer:  nomeContainer,
		NomeMetodo:     nomeMetodo,
		Assinatura:     assinatura,
		LinhaInicial:   metodo.Line,
		LinhaFinal:     metodo.ThrowingLine,
		Origem:         "",
	}
}

// construirCaminhoExcecao converte uma entrada bruta da baseline em um expath canônico.
func construirCaminhoExcecao(idMetodo string, metodo metodoBruto, indice int, hashCommit string) dominio.CaminhoExcecao {
	gatilho := strings.TrimSpace(metodo.SimplifiedPathConjunction)
	if gatilho == "" {
		gatilho = strings.TrimSpace(metodo.PathConjunction)
	}
	idCaminho := fmt.Sprintf("%s#%d#%d", idMetodo, metodo.ThrowingLine, indice)
	return dominio.CaminhoExcecao{
		IDCaminho:       idCaminho,
		TipoExcecao:     extrairTipoExcecao(metodo.Exception),
		Gatilho:         gatilho,
		CondicoesGuarda: stringsNaoVazias(gatilho),
		Confianca:       derivarConfianca(metodo.Maybe, metodo.SoundSymbolic, metodo.SoundBackwards),
		Evidencias:      construirEvidencias(metodo),
		Origem:          dominio.OrigemExpathWITUP,
		Metadados: map[string]interface{}{
			"exception_statement":         metodo.Exception,
			"path_conjunction":            metodo.PathConjunction,
			"symbolic_path_conjunction":   metodo.SymbolicPathConjunction,
			"backwards_path_conjunction":  metodo.BackwardsPathConjunction,
			"simplified_path_conjunction": metodo.SimplifiedPathConjunction,
			"z3_inputs":                   metodo.Z3Inputs,
			"sound_symbolic":              metodo.SoundSymbolic,
			"sound_backwards":             metodo.SoundBackwards,
			"maybe":                       metodo.Maybe,
			"line":                        metodo.Line,
			"throwing_line":               metodo.ThrowingLine,
			"is_static":                   metodo.IsStatic,
			"target_only_arguments":       metodo.TargetOnlyArguments,
			"call_sequence":               metodo.CallSequence,
			"inline_sequence":             metodo.InlineSequence,
			"commit_hash":                 hashCommit,
		},
	}
}

// separarAssinaturaQualificada separa a assinatura qualificada em contêiner e nome do método.
func separarAssinaturaQualificada(assinatura string) (string, string) {
	assinaturaLimpa := strings.TrimSpace(assinatura)
	indiceParen := strings.Index(assinaturaLimpa, "(")
	prefixo := assinaturaLimpa
	if indiceParen >= 0 {
		prefixo = assinaturaLimpa[:indiceParen]
	}
	ultimoPonto := strings.LastIndex(prefixo, ".")
	if ultimoPonto < 0 {
		return prefixo, prefixo
	}
	return prefixo[:ultimoPonto], prefixo[ultimoPonto+1:]
}

// normalizarRaizProjeto normaliza o caminho da raiz do projeto vindo da baseline.
func normalizarRaizProjeto(raw string) string {
	valor := normalizarCaminhoComBarras(raw)
	return strings.TrimSuffix(valor, "/")
}

// normalizarCaminhoArquivo reduz o caminho absoluto da baseline para um caminho relativo ao código-fonte.
func normalizarCaminhoArquivo(raw string) string {
	valor := normalizarCaminhoComBarras(raw)
	segmentos := strings.Split(strings.TrimPrefix(valor, "/"), "/")
	for i := 0; i+2 < len(segmentos); i++ {
		if strings.EqualFold(segmentos[i], "src") &&
			strings.EqualFold(segmentos[i+1], "main") &&
			strings.EqualFold(segmentos[i+2], "java") {
			return strings.Join(segmentos[i:], "/")
		}
		if strings.EqualFold(segmentos[i], "src") &&
			strings.EqualFold(segmentos[i+1], "test") &&
			strings.EqualFold(segmentos[i+2], "java") {
			return strings.Join(segmentos[i:], "/")
		}
	}
	return strings.TrimPrefix(valor, "/")
}

// normalizarCaminhoComBarras normaliza separadores e espaços em caminhos.
func normalizarCaminhoComBarras(raw string) string {
	valor := strings.TrimSpace(raw)
	valor = strings.ReplaceAll(valor, "\\", "/")
	return filepath.ToSlash(valor)
}

// extrairTipoExcecao extrai o tipo da exceção a partir do statement registrado pela baseline.
func extrairTipoExcecao(statement string) string {
	conteudo := strings.TrimSpace(statement)
	if conteudo == "" {
		return "UnknownException"
	}
	const marcador = "new "
	indice := strings.Index(conteudo, marcador)
	if indice < 0 {
		return strings.Trim(conteudo, ";")
	}
	restante := conteudo[indice+len(marcador):]
	parada := len(restante)
	for _, delimitador := range []string{"(", " ", ";"} {
		if proximo := strings.Index(restante, delimitador); proximo >= 0 && proximo < parada {
			parada = proximo
		}
	}
	valor := strings.TrimSpace(restante[:parada])
	if valor == "" {
		return "UnknownException"
	}
	return valor
}

// derivarConfianca estima uma confiança inicial com base nas flags do WITUP.
func derivarConfianca(maybe, soundSymbolic, soundBackwards bool) float64 {
	switch {
	case !maybe && soundSymbolic && soundBackwards:
		return 1.0
	case !maybe:
		return 0.85
	case soundSymbolic || soundBackwards:
		return 0.6
	default:
		return 0.45
	}
}

// construirEvidencias transforma pistas da baseline em evidências lineares para o relatório.
func construirEvidencias(metodo metodoBruto) []string {
	evidencias := []string{
		fmt.Sprintf("article_line=%d", metodo.Line),
		fmt.Sprintf("throwing_line=%d", metodo.ThrowingLine),
	}
	for _, chamada := range metodo.CallSequence {
		chamada = strings.TrimSpace(chamada)
		if chamada == "" {
			continue
		}
		evidencias = append(evidencias, "call:"+chamada)
	}
	return evidencias
}

// stringsNaoVazias remove strings vazias preservando a ordem original.
func stringsNaoVazias(values ...string) []string {
	saida := make([]string, 0, len(values))
	for _, valor := range values {
		valorLimpo := strings.TrimSpace(valor)
		if valorLimpo == "" {
			continue
		}
		saida = append(saida, valorLimpo)
	}
	return saida
}
