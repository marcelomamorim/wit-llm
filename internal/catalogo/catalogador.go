package catalogo

import (
	"fmt"
	"os"
	"path/filepath"
	"regexp"
	"sort"
	"strings"

	"github.com/marceloamorim/witup-llm/internal/dominio"
)

var (
	// As expressões regulares abaixo mantêm a descoberta de métodos simples,
	// rápida e reproduzível para o baseline Java atual. Se o projeto passar a
	// exigir precisão sintática mais forte, a próxima evolução recomendada é
	// trocar essa estratégia por um parser dedicado, como tree-sitter Java ou um
	// adaptador externo baseado em JavaParser.
	regexClasse     = regexp.MustCompile(`\b(class|record|interface|enum)\s+([A-Za-z_][A-Za-z0-9_]*)`)
	regexPacote     = regexp.MustCompile(`^\s*package\s+([A-Za-z0-9_.]+)\s*;`)
	regexMetodoJava = regexp.MustCompile(`([A-Za-z_][A-Za-z0-9_]*)\s*\(([^)]*)\)\s*(throws\s+[^\{]+)?\{`)
)

var palavrasNaoMetodo = map[string]struct{}{
	"if":           {},
	"for":          {},
	"while":        {},
	"switch":       {},
	"catch":        {},
	"synchronized": {},
}

// Catalogador descobre métodos Java no projeto configurado.
//
// A implementação é deliberadamente conservadora. Ela prefere descoberta
// previsível e reproduzível em vez de tentar suportar múltiplas linguagens com
// um único parser. Isso mantém a baseline alinhada ao escopo atual, restrito a Java.
type Catalogador struct {
	cfg                dominio.ConfigProjeto
	segmentosExcluidos map[string]struct{}
}

// NovoCatalogador cria um catalogador para uma configuração de projeto.
func NovoCatalogador(cfg dominio.ConfigProjeto) *Catalogador {
	segmentosExcluidos := make(map[string]struct{}, len(cfg.Exclude))
	for _, item := range cfg.Exclude {
		itemLimpo := strings.TrimSpace(item)
		if itemLimpo == "" {
			continue
		}
		segmentosExcluidos[itemLimpo] = struct{}{}
	}
	return &Catalogador{
		cfg:                cfg,
		segmentosExcluidos: segmentosExcluidos,
	}
}

// Catalogar devolve todos os métodos Java descobertos em ordem determinística.
func (c *Catalogador) Catalogar() ([]dominio.DescritorMetodo, error) {
	arquivos, err := c.coletarArquivosFonte()
	if err != nil {
		return nil, err
	}

	metodos := make([]dominio.DescritorMetodo, 0, 512)
	for _, arquivo := range arquivos {
		metodosDescobertos, err := extrairMetodosJava(arquivo, c.cfg.Raiz)
		if err != nil {
			return nil, err
		}
		metodos = append(metodos, metodosDescobertos...)
	}

	sort.Slice(metodos, func(i, j int) bool {
		esquerda := metodos[i]
		direita := metodos[j]
		if esquerda.CaminhoArquivo != direita.CaminhoArquivo {
			return esquerda.CaminhoArquivo < direita.CaminhoArquivo
		}
		if esquerda.LinhaInicial != direita.LinhaInicial {
			return esquerda.LinhaInicial < direita.LinhaInicial
		}
		return esquerda.NomeMetodo < direita.NomeMetodo
	})
	return metodos, nil
}

// CarregarVisaoGeral lê o resumo opcional do projeto usado na construção de prompts.
func (c *Catalogador) CarregarVisaoGeral() (string, error) {
	if strings.TrimSpace(c.cfg.OverviewFile) == "" {
		return "", nil
	}

	data, err := os.ReadFile(c.cfg.OverviewFile)
	if err != nil {
		return "", fmt.Errorf("ao ler o arquivo de visão geral %q: %w", c.cfg.OverviewFile, err)
	}
	return string(data), nil
}

// coletarArquivosFonte resolve as raízes incluídas e mantém apenas arquivos Java
// que não são excluídos pela política do repositório.
func (c *Catalogador) coletarArquivosFonte() ([]string, error) {
	jaVistos := map[string]bool{}
	arquivos := make([]string, 0, 1024)

	for _, raizIncluida := range c.cfg.Include {
		caminhoCandidato := filepath.Join(c.cfg.Raiz, raizIncluida)
		caminhoResolvido, err := filepath.Abs(caminhoCandidato)
		if err != nil {
			return nil, fmt.Errorf("ao resolver o caminho incluído %q: %w", raizIncluida, err)
		}

		info, err := os.Stat(caminhoResolvido)
		if err != nil {
			continue
		}

		if info.Mode().IsRegular() {
			if c.ehFonteJava(caminhoResolvido) && !c.estaExcluido(caminhoResolvido) && !jaVistos[caminhoResolvido] {
				arquivos = append(arquivos, caminhoResolvido)
				jaVistos[caminhoResolvido] = true
			}
			continue
		}

		err = filepath.WalkDir(caminhoResolvido, func(caminho string, entrada os.DirEntry, erroPercurso error) error {
			if erroPercurso != nil {
				return erroPercurso
			}
			if entrada.IsDir() && c.estaExcluido(caminho) {
				return filepath.SkipDir
			}
			if entrada.Type().IsRegular() && c.ehFonteJava(caminho) && !c.estaExcluido(caminho) && !jaVistos[caminho] {
				arquivos = append(arquivos, caminho)
				jaVistos[caminho] = true
			}
			return nil
		})
		if err != nil {
			return nil, fmt.Errorf("ao percorrer o diretório incluído %q: %w", caminhoResolvido, err)
		}
	}

	return arquivos, nil
}

// ehFonteJava informa se o caminho aponta para um arquivo-fonte Java.
func (c *Catalogador) ehFonteJava(path string) bool {
	return strings.HasSuffix(path, ".java")
}

// estaExcluido aplica exclusão por segmentos para evitar lógica sensível a
// plataforma e manter os filtros do repositório fáceis de entender.
func (c *Catalogador) estaExcluido(path string) bool {
	caminhoParaAnalise := path
	if relativo, err := filepath.Rel(c.cfg.Raiz, path); err == nil && relativo != "" {
		caminhoParaAnalise = relativo
	}
	segmentos := strings.Split(filepath.ToSlash(caminhoParaAnalise), "/")
	for _, segmento := range segmentos {
		if _, excluido := c.segmentosExcluidos[segmento]; excluido {
			return true
		}
	}
	return false
}

// extrairMetodosJava extrai descritores de métodos Java a partir de um arquivo-fonte.
func extrairMetodosJava(path, projectRoot string) ([]dominio.DescritorMetodo, error) {
	data, err := os.ReadFile(path)
	if err != nil {
		return nil, fmt.Errorf("ao ler o arquivo Java %q: %w", path, err)
	}

	text := string(data)
	lines := strings.Split(text, "\n")

	nomePacote := ""
	for _, line := range lines {
		if match := regexPacote.FindStringSubmatch(line); len(match) > 1 {
			nomePacote = match[1]
			break
		}
	}

	nomeContainer := strings.TrimSuffix(filepath.Base(path), filepath.Ext(path))
	for _, line := range lines {
		if match := regexClasse.FindStringSubmatch(line); len(match) > 2 {
			nomeContainer = match[2]
			break
		}
	}
	if nomePacote != "" {
		nomeContainer = nomePacote + "." + nomeContainer
	}

	caminhoRelativo, _ := filepath.Rel(projectRoot, path)
	caminhoRelativo = filepath.ToSlash(caminhoRelativo)

	metodos := []dominio.DescritorMetodo{}
	for index, line := range lines {
		linhaLimpa := strings.TrimSpace(line)
		if iniciaBlocoControleFluxo(linhaLimpa) {
			continue
		}

		match := regexMetodoJava.FindStringSubmatch(linhaLimpa)
		if len(match) <= 2 {
			continue
		}

		nomeMetodo := match[1]
		parametros := strings.Join(strings.Fields(match[2]), " ")
		linhaFinal := localizarLinhaFinalMetodo(lines, index)
		assinatura := fmt.Sprintf("%s.%s(%s)", nomeContainer, nomeMetodo, parametros)

		metodos = append(metodos, dominio.DescritorMetodo{
			IDMetodo:       fmt.Sprintf("%s:%s:%d", nomeContainer, nomeMetodo, index+1),
			CaminhoArquivo: caminhoRelativo,
			NomeContainer:  nomeContainer,
			NomeMetodo:     nomeMetodo,
			Assinatura:     assinatura,
			LinhaInicial:   index + 1,
			LinhaFinal:     linhaFinal,
			Origem:         strings.Join(lines[index:linhaFinal], "\n"),
		})
	}

	return metodos, nil
}

// iniciaBlocoControleFluxo identifica linhas que abrem blocos de controle e não métodos.
func iniciaBlocoControleFluxo(line string) bool {
	palavraChave := palavraChaveInicial(line)
	_, bloqueado := palavrasNaoMetodo[palavraChave]
	return bloqueado
}

// palavraChaveInicial devolve a primeira palavra-chave alfabética da linha.
func palavraChaveInicial(line string) string {
	line = strings.TrimSpace(line)
	if line == "" {
		return ""
	}
	start := 0
	for start < len(line) {
		ch := line[start]
		if (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') {
			break
		}
		start++
	}
	end := start
	for end < len(line) {
		ch := line[end]
		if (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') {
			end++
			continue
		}
		break
	}
	if start == end {
		return ""
	}
	return strings.ToLower(line[start:end])
}

// localizarLinhaFinalMetodo estima a linha final do método a partir do balanço de chaves.
func localizarLinhaFinalMetodo(lines []string, linhaInicial int) int {
	balanceamento := strings.Count(lines[linhaInicial], "{") - strings.Count(lines[linhaInicial], "}")
	linhaFinal := linhaInicial + 1
	for cursor := linhaInicial + 1; cursor < len(lines) && balanceamento > 0; cursor++ {
		balanceamento += strings.Count(lines[cursor], "{")
		balanceamento -= strings.Count(lines[cursor], "}")
		linhaFinal = cursor + 1
	}
	return linhaFinal
}
