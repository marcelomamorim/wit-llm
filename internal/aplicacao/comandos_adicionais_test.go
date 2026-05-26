package aplicacao

import (
	"strings"
	"testing"

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

func TestPrincipalRejeitaComandosLegadosRemovidosDaCLI(t *testing.T) {
	for _, comando := range []string{"executar-experimento", "run-experiment", "executar-estudo-completo", "run-full-study", "executar-benchmark", "benchmark"} {
		stdout, stderr, codigo := capturarSaidas(t, func() int {
			return Principal([]string{comando})
		})
		if codigo != 2 || !strings.Contains(stderr, "comando desconhecido") {
			t.Fatalf("comando legado %q deveria ser rejeitado codigo=%d stdout=%q stderr=%q", comando, codigo, stdout, stderr)
		}
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
		{"ingestao", func() int { return executarIngestaoWITUP(nil, NovoServico(nil, nil)) }},
		{"jacoco", func() int { return executarExtracaoJacoco(nil) }},
		{"pit", func() int { return executarExtracaoPIT(nil) }},
		{"surefire", func() int { return executarExtracaoSurefire(nil) }},
		{"reproducao", func() int { return executarReproducaoExcecoes(nil) }},
	}
	for _, caso := range casos {
		_, _, codigo := capturarSaidas(t, caso.fn)
		if codigo != 2 {
			t.Fatalf("%s deveria retornar código 2 por argumentos ausentes, recebi %d", caso.nome, codigo)
		}
	}
}
