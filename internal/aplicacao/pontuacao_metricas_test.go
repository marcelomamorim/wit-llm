package aplicacao

import (
	"testing"

	"github.com/marceloamorim/witup-llm/internal/dominio"
)

func TestCalcularPontuacaoAuditadaZeraQuandoNaoHaArquivosDeTeste(t *testing.T) {
	score, auditoria := calcularPontuacaoAuditadaSegundaFase([]dominio.ResultadoMetrica{
		{Nome: "test-compilation", Tipo: "build", Sucesso: true, NotaNormalizada: ponteiroFloatAplicacao(100), Peso: 1},
		{Nome: "target-method-coverage", Tipo: "generation_static", Sucesso: true, NotaNormalizada: ponteiroFloatAplicacao(100), Peso: 1},
	}, 0)

	if score == nil || *score != 0 {
		t.Fatalf("score deveria ser zerado sem arquivos de teste, recebi %#v", score)
	}
	if auditoria.RazaoCap != "no_test_files" {
		t.Fatalf("razão do cap inesperada: %#v", auditoria)
	}
	if auditoria.SuiteExecutavel {
		t.Fatalf("suite sem arquivos não deveria ser executável")
	}
}

func TestCalcularPontuacaoAuditadaCapaQuandoCompilacaoFalha(t *testing.T) {
	score, auditoria := calcularPontuacaoAuditadaSegundaFase([]dominio.ResultadoMetrica{
		{Nome: "test-compilation", Tipo: "build", Sucesso: false, NotaNormalizada: ponteiroFloatAplicacao(0), Peso: 1},
		{Nome: "target-method-coverage", Tipo: "generation_static", Sucesso: true, NotaNormalizada: ponteiroFloatAplicacao(100), Peso: 1},
	}, 1)

	if score == nil || *score != 25 {
		t.Fatalf("score deveria ser capado em 25, recebi %#v", score)
	}
	if auditoria.RazaoCap != "compile_failed" {
		t.Fatalf("razão do cap inesperada: %#v", auditoria)
	}
	if auditoria.NotaBruta == nil || *auditoria.NotaBruta != 50 {
		t.Fatalf("score bruto deveria preservar a média original: %#v", auditoria.NotaBruta)
	}
}

func TestCalcularPontuacaoAuditadaPreservaFluxoSemCompilacaoMensurada(t *testing.T) {
	score, auditoria := calcularPontuacaoAuditadaSegundaFase([]dominio.ResultadoMetrica{
		{Nome: "coverage", Sucesso: true, NotaNormalizada: ponteiroFloatAplicacao(82), Peso: 1},
	}, 1)

	if score == nil || *score != 82 {
		t.Fatalf("fluxos legados sem test-compilation devem preservar score bruto, recebi %#v", score)
	}
	if auditoria.RazaoCap != "none" {
		t.Fatalf("não deveria haver cap: %#v", auditoria)
	}
}
