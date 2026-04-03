package aplicacao

import (
	"path/filepath"
	"strings"
	"testing"

	"github.com/marceloamorim/witup-llm/internal/artefatos"
	"github.com/marceloamorim/witup-llm/internal/dominio"
)

func TestPrincipalSemArgumentosEComandoDesconhecido(t *testing.T) {
	stdout, stderr, codigo := capturarSaidas(t, func() int {
		return Principal(nil)
	})
	if codigo != 2 || stderr != "" || !strings.Contains(stdout, "Uso:") {
		t.Fatalf("principal sem args inesperado codigo=%d stdout=%q stderr=%q", codigo, stdout, stderr)
	}

	stdout, stderr, codigo = capturarSaidas(t, func() int {
		return Principal([]string{"nao-existe"})
	})
	if codigo != 2 || !strings.Contains(stderr, "comando desconhecido") || !strings.Contains(stdout, "Comandos:") {
		t.Fatalf("principal comando desconhecido inesperado codigo=%d stdout=%q stderr=%q", codigo, stdout, stderr)
	}
}

func TestListaStringsFlagAceitaValoresERejeitaVazio(t *testing.T) {
	var flag listaStringsFlag
	if err := flag.Set("um"); err != nil {
		t.Fatalf("set um: %v", err)
	}
	if err := flag.Set("dois"); err != nil {
		t.Fatalf("set dois: %v", err)
	}
	if got := flag.String(); got != "um, dois" {
		t.Fatalf("string inesperada: %q", got)
	}
	if err := flag.Set(""); err == nil {
		t.Fatalf("esperava erro para valor vazio")
	}
}

func TestExecutarVisualizacaoDadosValidaArgsEEndereco(t *testing.T) {
	_, stderr, codigo := capturarSaidas(t, func() int {
		return executarVisualizacaoDados(nil)
	})
	if codigo != 2 || !strings.Contains(stderr, "--config") {
		t.Fatalf("validação de visualizar-dados inesperada codigo=%d stderr=%q", codigo, stderr)
	}

	_, stderr, codigo = capturarSaidas(t, func() int {
		return executarVisualizacaoDados([]string{"--config", filepath.Join(t.TempDir(), "nao-existe.json")})
	})
	if codigo != 1 || !strings.Contains(stderr, "erro:") {
		t.Fatalf("config inválida deveria falhar codigo=%d stderr=%q", codigo, stderr)
	}
}

func TestExecutarConsolidacaoEstudoValidaArgsERelatorio(t *testing.T) {
	_, stderr, codigo := capturarSaidas(t, func() int {
		return executarConsolidacaoEstudo(nil)
	})
	if codigo != 2 || !strings.Contains(stderr, "--config e --summary") {
		t.Fatalf("validação inesperada codigo=%d stderr=%q", codigo, stderr)
	}

	cfg := configBaseTeste(t)
	configPath := escreverConfigTeste(t, cfg)
	summaryPath := filepath.Join(t.TempDir(), "summary.json")
	if err := artefatos.EscreverJSON(summaryPath, map[string]interface{}{
		"project_key":  "sample",
		"generated_at": dominio.HorarioUTC(),
	}); err != nil {
		t.Fatalf("fixture summary: %v", err)
	}
	_, stderr, codigo = capturarSaidas(t, func() int {
		return executarConsolidacaoEstudo([]string{"--config", configPath, "--summary", summaryPath})
	})
	if codigo != 1 || !strings.Contains(stderr, "run_id") {
		t.Fatalf("relatório inválido deveria falhar codigo=%d stderr=%q", codigo, stderr)
	}
}

func TestExecutarSondaFalhaQuandoModeloNaoExisteOuSemCredencial(t *testing.T) {
	cfg := configBaseTeste(t)
	configPath := escreverConfigTeste(t, cfg)
	_, stderr, codigo := capturarSaidas(t, func() int {
		return executarSonda([]string{"--config", configPath, "--model", "ausente"})
	})
	if codigo != 1 || !strings.Contains(stderr, "não está configurado") {
		t.Fatalf("modelo ausente deveria falhar codigo=%d stderr=%q", codigo, stderr)
	}

	cfg.Modelos["analysis"] = dominio.ConfigModelo{
		Provedor:                 "openai_compatible",
		Modelo:                   "gpt-5.4",
		URLBase:                  "https://api.openai.com/v1",
		VariavelAmbienteChaveAPI: "OPENAI_API_KEY",
		SegundosTimeout:          10,
	}
	configPath = escreverConfigTeste(t, cfg)
	_, stderr, codigo = capturarSaidas(t, func() int {
		return executarSonda([]string{"--config", configPath, "--model", "analysis"})
	})
	if codigo != 1 || !strings.Contains(stderr, "OPENAI_API_KEY") {
		t.Fatalf("falta de credencial deveria falhar codigo=%d stderr=%q", codigo, stderr)
	}
}

func TestExecutarHandlersObrigatoriosRetornamCodigoDois(t *testing.T) {
	casos := []struct {
		nome string
		fn   func() int
	}{
		{"analise", func() int { return executarAnalise(nil, NovoServico(nil, nil)) }},
		{"analise-multiagentes", func() int { return executarAnaliseMultiagentes(nil, NovoServico(nil, nil)) }},
		{"geracao", func() int { return executarGeracao(nil, NovoServico(nil, nil)) }},
		{"avaliacao", func() int { return executarAvaliacao(nil, NovoServico(nil, nil)) }},
		{"run", func() int { return executarTudo(nil, NovoServico(nil, nil)) }},
		{"experimento", func() int { return executarExperimento(nil, NovoServico(nil, nil)) }},
		{"estudo-completo", func() int { return executarEstudoCompleto(nil, NovoServico(nil, nil)) }},
		{"benchmark", func() int { return executarBenchmark(nil, NovoServico(nil, nil)) }},
		{"ingestao", func() int { return executarIngestaoWITUP(nil, NovoServico(nil, nil)) }},
		{"jacoco", func() int { return executarExtracaoJacoco(nil) }},
		{"pit", func() int { return executarExtracaoPIT(nil) }},
		{"reproducao", func() int { return executarReproducaoExcecoes(nil) }},
	}
	for _, caso := range casos {
		_, _, codigo := capturarSaidas(t, caso.fn)
		if codigo != 2 {
			t.Fatalf("%s deveria retornar código 2 por argumentos ausentes, recebi %d", caso.nome, codigo)
		}
	}
}
