package metricas

import (
	"encoding/xml"
	"fmt"
	"os"
	"path/filepath"
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
