package agentes

import (
	"fmt"
	"path/filepath"
	"strings"

	"github.com/marceloamorim/witup-llm/internal/artefatos"
	"github.com/marceloamorim/witup-llm/internal/dominio"
)

// persistirEtapaAgente registra os artefatos de uma etapa executada para um
// método específico e devolve o trecho canônico do relatório de rastreio.
func persistirEtapaAgente(
	espaco *artefatos.EspacoTrabalho,
	salvarPrompts bool,
	indice int,
	metodo dominio.DescritorMetodo,
	papel dominio.PapelAgente,
	prompt string,
	idRespostaAnterior string,
	idResposta string,
	respostaBruta string,
	saida map[string]interface{},
) (dominio.EtapaRastreioAgente, error) {
	etapa := dominio.EtapaRastreioAgente{
		Papel:              papel,
		Resumo:             strings.TrimSpace(fmt.Sprint(saida["summary"])),
		IDResposta:         strings.TrimSpace(idResposta),
		IDRespostaAnterior: strings.TrimSpace(idRespostaAnterior),
		Saida:              saida,
	}
	if !salvarPrompts || espaco == nil {
		return etapa, nil
	}

	prefixo := fmt.Sprintf("agentic-%04d-%s-%s", indice+1, papel, artefatos.Slugificar(metodo.IDMetodo))
	caminhoPrompt, caminhoSaida, err := persistirArquivosEtapa(espaco, prefixo, prompt, respostaBruta, saida)
	if err != nil {
		return dominio.EtapaRastreioAgente{}, err
	}

	etapa.ArquivoPrompt = caminhoPrompt
	etapa.ArquivoSaida = caminhoSaida
	return etapa, nil
}

// persistirEtapaProjeto registra os artefatos de contexto compartilhado gerados
// uma vez por projeto antes do refino por método.
func persistirEtapaProjeto(
	espaco *artefatos.EspacoTrabalho,
	salvarPrompts bool,
	papel dominio.PapelAgente,
	prompt string,
	idRespostaAnterior string,
	idResposta string,
	respostaBruta string,
	saida map[string]interface{},
) (dominio.EtapaRastreioAgente, error) {
	etapa := dominio.EtapaRastreioAgente{
		Papel:              papel,
		Resumo:             strings.TrimSpace(fmt.Sprint(saida["summary"])),
		IDResposta:         strings.TrimSpace(idResposta),
		IDRespostaAnterior: strings.TrimSpace(idRespostaAnterior),
		Saida:              saida,
	}
	if !salvarPrompts || espaco == nil {
		return etapa, nil
	}

	prefixo := fmt.Sprintf("agentic-project-%s", papel)
	caminhoPrompt, caminhoSaida, err := persistirArquivosEtapa(espaco, prefixo, prompt, respostaBruta, saida)
	if err != nil {
		return dominio.EtapaRastreioAgente{}, err
	}

	etapa.ArquivoPrompt = caminhoPrompt
	etapa.ArquivoSaida = caminhoSaida
	return etapa, nil
}

// persistirArquivosEtapa grava prompt, resposta textual e payload estruturado
// usando o mesmo layout de nomes para etapas de projeto e de método.
func persistirArquivosEtapa(
	espaco *artefatos.EspacoTrabalho,
	prefixo string,
	prompt string,
	respostaBruta string,
	saida map[string]interface{},
) (string, string, error) {
	caminhoPrompt := filepath.Join(espaco.Prompts, prefixo+".txt")
	caminhoResposta := filepath.Join(espaco.Respostas, prefixo+".txt")
	caminhoSaida := filepath.Join(espaco.Rastreios, prefixo+".json")

	if err := artefatos.EscreverTexto(caminhoPrompt, prompt); err != nil {
		return "", "", err
	}
	if err := artefatos.EscreverTexto(caminhoResposta, respostaBruta); err != nil {
		return "", "", err
	}
	if err := artefatos.EscreverJSON(caminhoSaida, saida); err != nil {
		return "", "", err
	}
	return caminhoPrompt, caminhoSaida, nil
}
