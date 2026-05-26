package agentes

import (
	"encoding/json"
	"fmt"
	"strings"
)

// formatarJSONOuObjetoVazio serializa o payload em JSON legível ou devolve um
// objeto vazio quando a serialização falha.
func formatarJSONOuObjetoVazio(payload map[string]interface{}) string {
	data, err := json.MarshalIndent(payload, "", "  ")
	if err != nil {
		return "{}"
	}
	return string(data)
}

// primeiroTextoPreenchido devolve o primeiro valor textual útil entre várias opções.
func primeiroTextoPreenchido(values ...interface{}) string {
	for _, value := range values {
		texto := strings.TrimSpace(fmt.Sprint(value))
		if texto == "" || texto == "<nil>" {
			continue
		}
		return texto
	}
	return ""
}

// listaStrings converte listas genéricas vindas da LLM para strings limpas.
func listaStrings(raw interface{}) []string {
	if raw == nil {
		return nil
	}
	itens, ok := raw.([]interface{})
	if !ok {
		return nil
	}
	saida := make([]string, 0, len(itens))
	for _, item := range itens {
		texto := strings.TrimSpace(fmt.Sprint(item))
		if texto == "" || texto == "<nil>" {
			continue
		}
		saida = append(saida, texto)
	}
	return saida
}

// limitarConfianca interpreta a confiança textual e a limita ao intervalo [0,1].
func limitarConfianca(raw interface{}) float64 {
	valor := strings.TrimSpace(fmt.Sprint(raw))
	if valor == "" || valor == "<nil>" {
		return 0
	}
	var confianca float64
	if _, err := fmt.Sscanf(valor, "%f", &confianca); err != nil {
		return 0
	}
	if confianca < 0 {
		return 0
	}
	if confianca > 1 {
		return 1
	}
	return confianca
}

// idCaminhoFallback cria um identificador estável quando o payload não traz path_id.
func idCaminhoFallback(raw, idMetodo string, indice int) string {
	valor := strings.TrimSpace(raw)
	if valor == "" || valor == "<nil>" {
		return fmt.Sprintf("%s:%d", idMetodo, indice)
	}
	return valor
}
