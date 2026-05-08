package aplicacao

import (
	"os"
	"path/filepath"
	"regexp"
	"sort"
	"strings"

	"github.com/marceloamorim/witup-llm/internal/dominio"
)

const (
	limiteImportsContextoGeracao      = 40
	limiteDependenciasContextoGeracao = 40
	limiteConstrutoresContextoGeracao = 8
	limiteFactoriesContextoGeracao    = 8
	limiteCaracteresTrechoClasseAlvo  = 4000
)

var (
	regexImportJavaContexto      = regexp.MustCompile(`(?m)^\s*import\s+(static\s+)?([^;]+);`)
	regexPacoteJavaContexto      = regexp.MustCompile(`(?m)^\s*package\s+([A-Za-z0-9_.]+)\s*;`)
	regexDependencyMavenContexto = regexp.MustCompile(`(?s)<dependency>\s*(.*?)\s*</dependency>`)
	regexGroupIDMavenContexto    = regexp.MustCompile(`(?s)<groupId>\s*([^<]+)\s*</groupId>`)
	regexArtifactIDMavenContexto = regexp.MustCompile(`(?s)<artifactId>\s*([^<]+)\s*</artifactId>`)
	regexStaticFactoryContexto   = regexp.MustCompile(`(?m)\bpublic\s+static\s+[^;={]+?\s+(getInstance|of|valueOf|from|create|newInstance|builder)\s*\([^)]*\)`)
)

// construirContextoGeracaoTestes monta um bloco técnico leve e comum aos dois
// cenários da fase 2. O WIT_CONTEXT recebe este mesmo bloco antes dos expaths,
// e o DIRECT_TESTS recebe o bloco antes do método cru; isso preserva igualdade
// de orçamento experimental, exceto pelo contexto WIT deliberadamente testado.
func construirContextoGeracaoTestes(cfg dominio.ConfigProjeto, containerName string, metodos []dominio.DescritorMetodo) map[string]interface{} {
	raizProjeto := strings.TrimSpace(cfg.Raiz)
	metodoReferencia := primeiroMetodoComArquivo(metodos)
	caminhoFonteRelativo := strings.TrimSpace(metodoReferencia.CaminhoArquivo)
	caminhoFonteAbsoluto := caminhoFonteRelativo
	if raizProjeto != "" && caminhoFonteRelativo != "" && !filepath.IsAbs(caminhoFonteRelativo) {
		caminhoFonteAbsoluto = filepath.Join(raizProjeto, filepath.FromSlash(caminhoFonteRelativo))
	}

	conteudoFonte := lerTextoSeExistir(caminhoFonteAbsoluto)
	pacoteFonte := extrairPacoteJavaContexto(conteudoFonte)
	if pacoteFonte == "" {
		pacoteFonte = pacoteDoContainer(containerName)
	}
	moduloRelativo, moduloAbsoluto := resolverModuloMavenDoMetodo(raizProjeto, caminhoFonteRelativo, caminhoFonteAbsoluto)
	pomContexto := coletarConteudoPOMContexto(raizProjeto, moduloAbsoluto)
	framework := resolverFrameworkContexto(cfg, moduloAbsoluto, pomContexto)
	dependencias := extrairDependenciasMavenContexto(pomContexto)
	imports := extrairImportsJavaContexto(conteudoFonte)
	nomeClasseSimples := nomeSimplesContainer(containerName)
	caminhoTesteSugerido := caminhoTesteSugeridoContexto(moduloRelativo, pacoteFonte, nomeClasseSimples)

	return map[string]interface{}{
		"test_framework":                 framework,
		"maven_module":                   moduloRelativo,
		"recommended_test_source_root":   caminhoFonteTesteContexto(moduloRelativo),
		"recommended_test_package":       pacoteFonte,
		"recommended_relative_test_path": caminhoTesteSugerido,
		"source_file":                    filepath.ToSlash(caminhoFonteRelativo),
		"source_package":                 pacoteFonte,
		"source_imports":                 imports,
		"maven_dependencies":             dependencias,
		"dependency_signals": map[string]bool{
			"junit4":        pomSuportaJUnit4(pomContexto),
			"junit_jupiter": pomSuportaJUnitJupiter(pomContexto),
			"mockito":       pomSuportaMockito(pomContexto),
			"assertj":       strings.Contains(pomContexto, "<groupId>org.assertj</groupId>"),
		},
		"construction_hints": map[string]interface{}{
			"public_or_protected_constructors": extrairConstrutoresContexto(conteudoFonte, nomeClasseSimples),
			"public_static_factories":          extrairFactoriesContexto(conteudoFonte),
		},
		"target_class_excerpt": truncarContextoClasse(conteudoFonte),
		"target_methods":       compactarMetodosContextoComum(metodos),
	}
}

func primeiroMetodoComArquivo(metodos []dominio.DescritorMetodo) dominio.DescritorMetodo {
	for _, metodo := range metodos {
		if strings.TrimSpace(metodo.CaminhoArquivo) != "" {
			return metodo
		}
	}
	if len(metodos) > 0 {
		return metodos[0]
	}
	return dominio.DescritorMetodo{}
}

func extrairMetodosDasAnalises(analises []dominio.AnaliseMetodo) []dominio.DescritorMetodo {
	metodos := make([]dominio.DescritorMetodo, 0, len(analises))
	for _, analise := range analises {
		metodos = append(metodos, analise.Metodo)
	}
	return metodos
}

func resolverModuloMavenDoMetodo(raizProjeto, caminhoRelativo, caminhoAbsoluto string) (string, string) {
	raizProjeto = filepath.Clean(raizProjeto)
	moduloRelativo := "."
	if indice := strings.Index(filepath.ToSlash(caminhoRelativo), "/src/main/java/"); indice > 0 {
		moduloRelativo = filepath.ToSlash(caminhoRelativo[:indice])
	}
	moduloAbsoluto := raizProjeto
	if moduloRelativo != "." && raizProjeto != "" {
		moduloAbsoluto = filepath.Join(raizProjeto, filepath.FromSlash(moduloRelativo))
	}
	if arquivoExisteContextoGeracao(filepath.Join(moduloAbsoluto, "pom.xml")) {
		return moduloRelativo, moduloAbsoluto
	}
	if caminhoAbsoluto != "" && raizProjeto != "" {
		atual := filepath.Dir(caminhoAbsoluto)
		for atual != "" && strings.HasPrefix(filepath.Clean(atual), raizProjeto) {
			if arquivoExisteContextoGeracao(filepath.Join(atual, "pom.xml")) {
				rel, err := filepath.Rel(raizProjeto, atual)
				if err == nil && rel != "" && rel != "." {
					return filepath.ToSlash(rel), atual
				}
				return ".", atual
			}
			pai := filepath.Dir(atual)
			if pai == atual {
				break
			}
			atual = pai
		}
	}
	return ".", raizProjeto
}

func resolverFrameworkContexto(cfg dominio.ConfigProjeto, moduloAbsoluto, pomContexto string) string {
	framework := normalizarFrameworkTestes(cfg.TestFramework)
	if framework != frameworkInfer {
		return framework
	}
	if pomSuportaJUnitJupiter(pomContexto) {
		return frameworkJUnit5
	}
	if pomSuportaJUnit4(pomContexto) {
		return frameworkJUnit4
	}
	if strings.TrimSpace(moduloAbsoluto) != "" {
		if frameworkModulo := inferirFrameworkTestesNoProjeto(moduloAbsoluto); frameworkModulo != "" {
			return frameworkModulo
		}
	}
	return resolverFrameworkTestes(cfg)
}

func coletarConteudoPOMContexto(raizProjeto, moduloAbsoluto string) string {
	candidatos := []string{}
	if strings.TrimSpace(raizProjeto) != "" {
		candidatos = append(candidatos, filepath.Join(raizProjeto, "pom.xml"))
	}
	if strings.TrimSpace(moduloAbsoluto) != "" {
		candidatos = append(candidatos, filepath.Join(moduloAbsoluto, "pom.xml"))
	}
	vistos := map[string]bool{}
	var builder strings.Builder
	for _, candidato := range candidatos {
		candidato = filepath.Clean(candidato)
		if vistos[candidato] {
			continue
		}
		vistos[candidato] = true
		if texto := lerTextoSeExistir(candidato); texto != "" {
			builder.WriteString("\n")
			builder.WriteString(texto)
		}
	}
	return builder.String()
}

func extrairDependenciasMavenContexto(conteudo string) []string {
	if strings.TrimSpace(conteudo) == "" {
		return nil
	}
	vistos := map[string]bool{}
	dependencias := make([]string, 0)
	for _, bloco := range regexDependencyMavenContexto.FindAllStringSubmatch(conteudo, -1) {
		if len(bloco) < 2 {
			continue
		}
		groupID := primeiroGrupoRegex(regexGroupIDMavenContexto, bloco[1])
		artifactID := primeiroGrupoRegex(regexArtifactIDMavenContexto, bloco[1])
		if groupID == "" || artifactID == "" {
			continue
		}
		chave := groupID + ":" + artifactID
		if vistos[chave] {
			continue
		}
		vistos[chave] = true
		dependencias = append(dependencias, chave)
	}
	sort.Strings(dependencias)
	if len(dependencias) > limiteDependenciasContextoGeracao {
		return dependencias[:limiteDependenciasContextoGeracao]
	}
	return dependencias
}

func extrairImportsJavaContexto(conteudo string) []string {
	imports := make([]string, 0)
	for _, grupos := range regexImportJavaContexto.FindAllStringSubmatch(conteudo, -1) {
		if len(grupos) < 3 {
			continue
		}
		prefixoStatic := strings.TrimSpace(grupos[1])
		alvo := strings.TrimSpace(grupos[2])
		if alvo == "" {
			continue
		}
		if prefixoStatic != "" {
			imports = append(imports, "import static "+alvo+";")
		} else {
			imports = append(imports, "import "+alvo+";")
		}
	}
	if len(imports) > limiteImportsContextoGeracao {
		return imports[:limiteImportsContextoGeracao]
	}
	return imports
}

func extrairConstrutoresContexto(conteudo, nomeClasse string) []string {
	if strings.TrimSpace(conteudo) == "" || strings.TrimSpace(nomeClasse) == "" {
		return nil
	}
	regex := regexp.MustCompile(`(?m)\b(?:public|protected)\s+` + regexp.QuoteMeta(nomeClasse) + `\s*\([^)]*\)`)
	return limitarStringsContexto(regex.FindAllString(conteudo, -1), limiteConstrutoresContextoGeracao)
}

func extrairFactoriesContexto(conteudo string) []string {
	return limitarStringsContexto(regexStaticFactoryContexto.FindAllString(conteudo, -1), limiteFactoriesContextoGeracao)
}

func compactarMetodosContextoComum(metodos []dominio.DescritorMetodo) []map[string]interface{} {
	saida := make([]map[string]interface{}, 0, len(metodos))
	for _, metodo := range metodos {
		saida = append(saida, map[string]interface{}{
			"method_id":      metodo.IDMetodo,
			"method_name":    metodo.NomeMetodo,
			"signature":      metodo.Assinatura,
			"file_path":      metodo.CaminhoArquivo,
			"start_line":     metodo.LinhaInicial,
			"end_line":       metodo.LinhaFinal,
			"source_excerpt": extrairCabecalhoMetodo(metodo.Origem),
		})
	}
	return saidaOrdenadaContexto(saida)
}

func saidaOrdenadaContexto(valores []map[string]interface{}) []map[string]interface{} {
	sort.SliceStable(valores, func(i, j int) bool {
		return strings.TrimSpace(toStringContexto(valores[i]["method_id"])) < strings.TrimSpace(toStringContexto(valores[j]["method_id"]))
	})
	return valores
}

func caminhoFonteTesteContexto(moduloRelativo string) string {
	if strings.TrimSpace(moduloRelativo) == "" || moduloRelativo == "." {
		return "src/test/java"
	}
	return filepath.ToSlash(filepath.Join(moduloRelativo, "src/test/java"))
}

func caminhoTesteSugeridoContexto(moduloRelativo, pacote, nomeClasseSimples string) string {
	nomeArquivo := nomeClasseSimples
	if nomeArquivo == "" {
		nomeArquivo = "Generated"
	}
	nomeArquivo += "WitupGeneratedTest.java"
	partes := []string{caminhoFonteTesteContexto(moduloRelativo)}
	if pacote = strings.TrimSpace(pacote); pacote != "" {
		partes = append(partes, strings.ReplaceAll(pacote, ".", "/"))
	}
	partes = append(partes, nomeArquivo)
	return filepath.ToSlash(filepath.Join(partes...))
}

func pacoteDoContainer(containerName string) string {
	containerName = strings.TrimSpace(containerName)
	if indice := strings.LastIndex(containerName, "."); indice > 0 {
		return containerName[:indice]
	}
	return ""
}

func nomeSimplesContainer(containerName string) string {
	containerName = strings.TrimSpace(containerName)
	if indice := strings.LastIndex(containerName, "."); indice >= 0 {
		return containerName[indice+1:]
	}
	return containerName
}

func extrairPacoteJavaContexto(conteudo string) string {
	return primeiroGrupoRegex(regexPacoteJavaContexto, conteudo)
}

func primeiroGrupoRegex(regex *regexp.Regexp, texto string) string {
	grupos := regex.FindStringSubmatch(texto)
	if len(grupos) < 2 {
		return ""
	}
	return strings.TrimSpace(grupos[1])
}

func limitarStringsContexto(valores []string, limite int) []string {
	if len(valores) == 0 {
		return nil
	}
	limpas := make([]string, 0, len(valores))
	for _, valor := range valores {
		valor = strings.Join(strings.Fields(strings.TrimSpace(valor)), " ")
		if valor != "" {
			limpas = append(limpas, valor)
		}
	}
	if len(limpas) > limite {
		return limpas[:limite]
	}
	return limpas
}

func truncarContextoClasse(conteudo string) string {
	conteudo = strings.TrimSpace(conteudo)
	if len(conteudo) <= limiteCaracteresTrechoClasseAlvo {
		return conteudo
	}
	return strings.TrimSpace(conteudo[:limiteCaracteresTrechoClasseAlvo]) + "\n...[truncado]"
}

func lerTextoSeExistir(caminho string) string {
	if strings.TrimSpace(caminho) == "" {
		return ""
	}
	dados, err := os.ReadFile(caminho)
	if err != nil {
		return ""
	}
	return string(dados)
}

func arquivoExisteContextoGeracao(caminho string) bool {
	info, err := os.Stat(caminho)
	return err == nil && !info.IsDir()
}

func toStringContexto(valor interface{}) string {
	if valor == nil {
		return ""
	}
	if texto, ok := valor.(string); ok {
		return texto
	}
	return ""
}
