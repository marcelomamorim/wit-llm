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
func construirPromptSistemaArqueologoProjeto() string {
	return "Você é o agente Arqueólogo para pesquisa de caminhos de exceção em Java. Estude o projeto e o conjunto de métodos-alvo uma única vez. Responda apenas com JSON válido."
}

// construirPromptUsuarioArqueologoProjeto monta o prompt do agente arqueólogo
// compartilhado por toda a execução multiagente.
func construirPromptUsuarioArqueologoProjeto(visaoGeral string, metodos []dominio.DescritorMetodo) string {
	return fmt.Sprintf(`Estude o projeto e o conjunto de métodos-alvo.
Return JSON:
{"summary":"...","project_summary":"...","exception_hotspots":[...],"api_misuse_patterns":[...],"shared_invariants":[...],"target_manifest_summary":[...]}

Visão geral do projeto:
%s

Manifesto dos métodos-alvo:
%s
`, visaoGeral, construirManifestoProjeto(metodos))
}

// construirPromptSistemaDependenciasProjeto monta a instrução sistêmica do
// agente de dependências compartilhado.
func construirPromptSistemaDependenciasProjeto() string {
	return "Você é o agente de Malha de Dependências para pesquisa de caminhos de exceção em Java. Mapeie hotspots interprocedurais do projeto uma única vez. Responda apenas com JSON válido."
}

// construirPromptUsuarioDependenciasProjeto monta o prompt do agente de
// dependências em nível de projeto.
func construirPromptUsuarioDependenciasProjeto(visaoGeral string, metodos []dominio.DescritorMetodo, saidaArqueologo map[string]interface{}) string {
	return fmt.Sprintf(`Mapeie dependências, cadeias de chamadas e hotspots interprocedurais relevantes para os métodos-alvo.
Return JSON:
{"summary":"...","dependency_summary":"...","shared_dependencies":[...],"exception_propagation_hotspots":[...],"interprocedural_signals":[...],"context_gaps":[...]}

Visão geral do projeto:
%s

Manifesto dos métodos-alvo:
%s

Notas do arqueólogo:
%s
`, visaoGeral, construirManifestoProjeto(metodos), formatarJSONOuObjetoVazio(saidaArqueologo))
}

// construirPromptSistemaExtrator monta a instrução sistêmica do agente extrator.
func construirPromptSistemaExtrator() string {
	return "Você é o agente Extrator de Caminhos de Exceção para pesquisa em Java. Use o contexto stateful já preservado na conversa e responda apenas com JSON válido."
}

// construirPromptUsuarioExtrator monta o prompt do agente extrator por método.
func construirPromptUsuarioExtrator(metodo dominio.DescritorMetodo) string {
	return fmt.Sprintf(`Infira candidatos de caminhos de exceção para o método abaixo.
Return JSON:
{"summary":"...","method_summary":"...","dependency_mesh":{},"expaths":[{"path_id":"...","exception_type":"...","trigger":"...","guard_conditions":[...],"confidence":0.0,"evidence":[...]}]}

O contexto compartilhado do projeto e da malha de dependências já foi preservado
nas respostas anteriores desta conversa. Reaproveite esse contexto sem repeti-lo.

Assinatura do método: %s
Arquivo: %s
Código-fonte do método:
%s
`, metodo.Assinatura, metodo.CaminhoArquivo, metodo.Origem)
}

// construirPromptSistemaCetico monta a instrução sistêmica do agente revisor cético.
func construirPromptSistemaCetico() string {
	return "Você é o agente Revisor Cético para pesquisa de caminhos de exceção em Java. Use o estado preservado da conversa, mantenha apenas caminhos defensáveis com evidência explícita e responda apenas com JSON válido."
}

// construirPromptUsuarioCetico monta o prompt do agente revisor cético.
func construirPromptUsuarioCetico(metodo dominio.DescritorMetodo, saidaExtrator map[string]interface{}) string {
	return fmt.Sprintf(`Revise os candidatos de caminhos de exceção e mantenha apenas os defensáveis.
Return JSON:
{"summary":"...","method_summary":"...","accepted_expaths":[{"path_id":"...","exception_type":"...","trigger":"...","guard_conditions":[...],"confidence":0.0,"evidence":[...]}],"rejected_paths":[{"path_id":"...","reason":"..."}],"review_notes":[...]}

O contexto compartilhado do projeto, da malha de dependências e a última
resposta do extrator já foram preservados nesta conversa. Use esse estado como
base da revisão.

Assinatura do método: %s
Arquivo: %s
Código-fonte do método:
%s

Candidatos do extrator:
%s
`, metodo.Assinatura, metodo.CaminhoArquivo, metodo.Origem, formatarJSONOuObjetoVazio(saidaExtrator))
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
