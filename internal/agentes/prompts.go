package agentes

import (
	"fmt"
	"sort"
	"strings"

	"github.com/marceloamorim/witup-llm/internal/dominio"
)

const (
	limiteContainersManifesto = 24
	limiteMetodosManifesto    = 80
)

// construirPromptSistemaArqueologoProjeto monta a instrução sistêmica do agente
// arqueólogo em nível de projeto.
func construirPromptSistemaArqueologoProjeto() (string, error) {
	return renderizarPromptTemplate("arqueologo_system.tmpl", nil)
}

// construirPromptUsuarioArqueologoProjeto monta o prompt do agente arqueólogo
// compartilhado por toda a execução multiagente.
func construirPromptUsuarioArqueologoProjeto(visaoGeral string, metodos []dominio.DescritorMetodo) (string, error) {
	return renderizarPromptTemplate("arqueologo_user.tmpl", map[string]string{
		"VisaoGeral": visaoGeral,
		"Manifesto":  construirManifestoProjeto(metodos),
	})
}

// construirPromptSistemaDependenciasProjeto monta a instrução sistêmica do
// agente de dependências compartilhado.
func construirPromptSistemaDependenciasProjeto() (string, error) {
	return renderizarPromptTemplate("dependencias_system.tmpl", nil)
}

// construirPromptUsuarioDependenciasProjeto monta o prompt do agente de
// dependências em nível de projeto.
func construirPromptUsuarioDependenciasProjeto(visaoGeral string, metodos []dominio.DescritorMetodo, saidaArqueologo map[string]interface{}) (string, error) {
	return renderizarPromptTemplate("dependencias_user.tmpl", map[string]string{
		"VisaoGeral":      visaoGeral,
		"Manifesto":       construirManifestoProjeto(metodos),
		"NotasArqueologo": formatarJSONOuObjetoVazio(saidaArqueologo),
	})
}

// construirPromptSistemaExtrator monta a instrução sistêmica do agente extrator.
func construirPromptSistemaExtrator() (string, error) {
	return renderizarPromptTemplate("extrator_system.tmpl", nil)
}

// construirPromptUsuarioExtrator monta o prompt do agente extrator por método.
func construirPromptUsuarioExtrator(metodo dominio.DescritorMetodo) (string, error) {
	return renderizarPromptTemplate("extrator_user.tmpl", map[string]string{
		"Assinatura":  metodo.Assinatura,
		"Arquivo":     metodo.CaminhoArquivo,
		"CodigoFonte": metodo.Origem,
	})
}

// construirPromptSistemaCetico monta a instrução sistêmica do agente revisor cético.
func construirPromptSistemaCetico() (string, error) {
	return renderizarPromptTemplate("cetico_system.tmpl", nil)
}

// construirPromptUsuarioCetico monta o prompt do agente revisor cético.
func construirPromptUsuarioCetico(metodo dominio.DescritorMetodo, saidaExtrator map[string]interface{}) (string, error) {
	return renderizarPromptTemplate("cetico_user.tmpl", map[string]string{
		"Assinatura":         metodo.Assinatura,
		"Arquivo":            metodo.CaminhoArquivo,
		"CodigoFonte":        metodo.Origem,
		"CandidatosExtrator": formatarJSONOuObjetoVazio(saidaExtrator),
	})
}

// construirManifestoProjeto resume os métodos-alvo em um manifesto compacto
// para reutilização pelos agentes de contexto compartilhado.
func construirManifestoProjeto(metodos []dominio.DescritorMetodo) string {
	if len(metodos) == 0 {
		return "(sem métodos)"
	}

	containers := make(map[string]int, len(metodos))
	for _, metodo := range metodos {
		containers[metodo.NomeContainer]++
	}

	nomesContainers := make([]string, 0, len(containers))
	for container := range containers {
		nomesContainers = append(nomesContainers, container)
	}
	sort.Strings(nomesContainers)
	if len(nomesContainers) > limiteContainersManifesto {
		nomesContainers = nomesContainers[:limiteContainersManifesto]
	}

	linhas := []string{
		fmt.Sprintf("Total de métodos-alvo: %d", len(metodos)),
		"Principais contêineres:",
	}
	for _, container := range nomesContainers {
		linhas = append(linhas, fmt.Sprintf("- %s (%d métodos)", container, containers[container]))
	}

	linhas = append(linhas, "Métodos-alvo de referência:")
	limiteMetodos := len(metodos)
	if limiteMetodos > limiteMetodosManifesto {
		limiteMetodos = limiteMetodosManifesto
	}
	for i := 0; i < limiteMetodos; i++ {
		metodo := metodos[i]
		linhas = append(linhas, fmt.Sprintf("- %s | %s | linha %d", metodo.Assinatura, metodo.CaminhoArquivo, metodo.LinhaInicial))
	}
	if len(metodos) > limiteMetodos {
		linhas = append(linhas, fmt.Sprintf("... %d métodos adicionais omitidos", len(metodos)-limiteMetodos))
	}

	return strings.Join(linhas, "\n")
}
