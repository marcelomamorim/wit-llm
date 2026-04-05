package metricas

import (
	"math"
	"os"
	"path/filepath"
	"strings"
	"testing"

	"github.com/marceloamorim/witup-llm/internal/dominio"
)

func ponteiroFloat(v float64) *float64 { return &v }

func TestAgregarPontuacao(t *testing.T) {
	results := []dominio.ResultadoMetrica{
		{NotaNormalizada: ponteiroFloat(80), Peso: 1.0},
		{NotaNormalizada: ponteiroFloat(100), Peso: 3.0},
	}
	out := AgregarPontuacao(results)
	if out == nil {
		t.Fatalf("expected aggregate score")
	}
	if *out != 95 {
		t.Fatalf("expected 95, got %f", *out)
	}
}

func TestNormalizarNota(t *testing.T) {
	value := 50.0
	n := normalizarNota(&value, 100)
	if n == nil || *n != 50 {
		t.Fatalf("expected 50, got %v", n)
	}
}

func TestCombinarPontuacoes(t *testing.T) {
	metric := 80.0
	judge := 60.0
	combined := CombinarPontuacoes(&metric, &judge)
	if combined == nil {
		t.Fatalf("expected combined score")
	}
	if *combined != 74 {
		t.Fatalf("expected 74, got %f", *combined)
	}
}

func TestExecutarMetricaCapturaSaidaECodigoDeErro(t *testing.T) {
	executor := NovoExecutor()
	ctx := ContextoExecucao{RaizProjeto: t.TempDir(), ChaveModelo: "gpt-5.4"}

	ok := executor.ExecutarMetrica(dominio.ConfigMetrica{
		Nome:       "coverage",
		Comando:    "printf 'Coverage: 87.5'",
		RegexValor: `Coverage: ([0-9.]+)`,
		Escala:     100,
		Peso:       1,
	}, ctx)
	if !ok.Sucesso || ok.CodigoSaida != 0 {
		t.Fatalf("esperava sucesso, recebi %#v", ok)
	}
	if ok.ValorNumerico == nil || *ok.ValorNumerico != 87.5 {
		t.Fatalf("valor numérico inesperado: %#v", ok.ValorNumerico)
	}
	if ok.NotaNormalizada == nil || *ok.NotaNormalizada != 87.5 {
		t.Fatalf("nota normalizada inesperada: %#v", ok.NotaNormalizada)
	}

	falha := executor.ExecutarMetrica(dominio.ConfigMetrica{
		Nome:       "pit",
		Comando:    "echo 'mutation failed 42' 1>&2; exit 7",
		RegexValor: `([0-9.]+)`,
		Escala:     100,
		Peso:       1,
	}, ctx)
	if falha.Sucesso || falha.CodigoSaida != 7 {
		t.Fatalf("esperava falha com exit code 7, recebi %#v", falha)
	}
	if falha.ValorNumerico != nil {
		t.Fatalf("não esperava valor numérico em falha: %#v", falha.ValorNumerico)
	}
	if falha.NotaNormalizada != nil {
		t.Fatalf("não esperava nota normalizada em falha: %#v", falha.NotaNormalizada)
	}
}

func TestRenderizarComandoEFormatarPontuacao(t *testing.T) {
	ctx := ContextoExecucao{
		RaizProjeto:       "/repo",
		DiretorioExecucao: "/repo/generated/run-1",
		DiretorioTestes:   "/repo/generated/run-1/tests",
		CaminhoAnalise:    "/repo/generated/run-1/analysis.json",
		CaminhoGeracao:    "/repo/generated/run-1/generation.json",
		ChaveModelo:       "analysis-model",
	}
	comando := renderizarComando("cd {project_root} && echo {model_key} {analysis_path} {generation_path}", ctx)
	if comando != "cd /repo && echo analysis-model /repo/generated/run-1/analysis.json /repo/generated/run-1/generation.json" {
		t.Fatalf("comando renderizado inesperado: %q", comando)
	}
	if FormatarPontuacao(nil) != "-" {
		t.Fatalf("pontuação nil deveria renderizar como hífen")
	}
}

func TestInterpretarValorNumericoENormalizarNotaLidamComBordas(t *testing.T) {
	if valor := interpretarValorNumerico(`valor=([0-9.]+)%`, "valor=12.5%", true); valor == nil || *valor != 12.5 {
		t.Fatalf("valor interpretado inesperado: %#v", valor)
	}
	if interpretarValorNumerico("(", "x", true) != nil {
		t.Fatalf("regex inválida deveria retornar nil")
	}
	if interpretarValorNumerico(`valor=([0-9.]+)`, "valor=99", false) != nil {
		t.Fatalf("falhas não devem produzir valor numérico")
	}
	negativo := -10.0
	if nota := normalizarNota(&negativo, 100); nota == nil || *nota != 0 {
		t.Fatalf("nota negativa deveria ser truncada para zero: %#v", nota)
	}
	alto := 250.0
	if nota := normalizarNota(&alto, 100); nota == nil || *nota != 100 {
		t.Fatalf("nota acima da escala deveria ser truncada em 100: %#v", nota)
	}
}

func TestExecutarTodasPreservaQuantidadeDeMetricas(t *testing.T) {
	executor := NovoExecutor()
	ctx := ContextoExecucao{RaizProjeto: t.TempDir()}
	resultados := executor.ExecutarTodas([]dominio.ConfigMetrica{
		{Nome: "a", Comando: "printf '1'", RegexValor: `(1)`, Escala: 1, Peso: 1},
		{Nome: "b", Comando: "printf '2'", RegexValor: `(2)`, Escala: 2, Peso: 1},
	}, ctx)
	if len(resultados) != 2 {
		t.Fatalf("esperava duas métricas executadas, recebi %d", len(resultados))
	}
}

func TestExecutarMetricaFalhaAoMudarDiretorioUsaCodigoUm(t *testing.T) {
	executor := NovoExecutor()
	ctx := ContextoExecucao{RaizProjeto: t.TempDir()}
	resultado := executor.ExecutarMetrica(dominio.ConfigMetrica{
		Nome:              "chdir",
		Comando:           "pwd",
		DiretorioTrabalho: "nao-existe",
	}, ctx)
	if resultado.Sucesso || resultado.CodigoSaida != 1 {
		t.Fatalf("esperava erro de infraestrutura com código 1, recebi %#v", resultado)
	}
	if resultado.SaidaErro == "" {
		t.Fatalf("esperava mensagem de erro de infraestrutura preenchida")
	}
}

func TestExecutarMetricaUsaDiretorioConfigurado(t *testing.T) {
	raiz := t.TempDir()
	subdir := filepath.Join(raiz, "sub")
	if err := os.MkdirAll(subdir, 0o755); err != nil {
		t.Fatalf("mkdir subdir: %v", err)
	}
	executor := NovoExecutor()
	ctx := ContextoExecucao{RaizProjeto: raiz}
	resultado := executor.ExecutarMetrica(dominio.ConfigMetrica{
		Nome:              "pwd",
		Comando:           "pwd",
		DiretorioTrabalho: "sub",
		RegexValor:        `(.*)`,
		Escala:            1,
	}, ctx)
	if !strings.Contains(resultado.SaidaPadrao, subdir) {
		t.Fatalf("esperava execução no subdiretório %q, recebi %q", subdir, resultado.SaidaPadrao)
	}
}

func TestAgregarPontuacaoPenalizaNilComoZeroERetornaNilSemPesos(t *testing.T) {
	if nota := AgregarPontuacao([]dominio.ResultadoMetrica{{Peso: 0}}); nota != nil {
		t.Fatalf("esperava nil quando não há pesos válidos")
	}
	nota := AgregarPontuacao([]dominio.ResultadoMetrica{{NotaNormalizada: ponteiroFloat(50), Peso: 1}, {Peso: 10}})
	if nota == nil || math.Abs(*nota-(50.0/11.0)) > 1e-9 {
		t.Fatalf("nota agregada inesperada: %#v", nota)
	}
	nota = AgregarPontuacao([]dominio.ResultadoMetrica{
		{NotaNormalizada: ponteiroFloat(50), Peso: 1},
		{Peso: 10},
		{NotaNormalizada: ponteiroFloat(100), Peso: 1},
	})
	if nota == nil || math.Abs(*nota-(150.0/12.0)) > 1e-9 {
		t.Fatalf("nota agregada com elemento nil intermediário inesperada: %#v", nota)
	}
}

func TestInterpretarValorNumericoCobreCantos(t *testing.T) {
	if interpretarValorNumerico("", "10", true) != nil {
		t.Fatalf("regex vazia deveria retornar nil")
	}
	if interpretarValorNumerico(`valor=[0-9.]+`, "valor=10", true) != nil {
		t.Fatalf("regex sem grupo de captura deveria retornar nil")
	}
	if interpretarValorNumerico(`valor=(.+)`, "valor=abc", true) != nil {
		t.Fatalf("valor não numérico deveria retornar nil")
	}
	if interpretarValorNumerico(`valor=([0-9.]+)`, "sem match", true) != nil {
		t.Fatalf("sem correspondência deveria retornar nil")
	}
}

func TestNormalizarNotaECombinarPontuacoesCobremFallbacks(t *testing.T) {
	if normalizarNota(nil, 100) != nil {
		t.Fatalf("value nil deveria produzir nota nil")
	}
	valor := 50.0
	if normalizarNota(&valor, 0) != nil {
		t.Fatalf("escala zero deveria produzir nil")
	}
	meio := 0.5
	if nota := normalizarNota(&meio, 1); nota == nil || *nota != 50 {
		t.Fatalf("escala um deveria ser suportada: %#v", nota)
	}
	zero := 0.0
	if nota := normalizarNota(&zero, 100); nota == nil || *nota != 0 {
		t.Fatalf("nota zero deveria permanecer zero: %#v", nota)
	}
	cheio := 100.0
	if nota := normalizarNota(&cheio, 100); nota == nil || *nota != 100 {
		t.Fatalf("nota cem deveria permanecer cem: %#v", nota)
	}
	if combinado := CombinarPontuacoes(ponteiroFloat(10), nil); combinado == nil || *combinado != 10 {
		t.Fatalf("nota combinada deveria cair para métricas: %#v", combinado)
	}
	if combinado := CombinarPontuacoes(nil, ponteiroFloat(20)); combinado == nil || *combinado != 20 {
		t.Fatalf("nota combinada deveria cair para juiz: %#v", combinado)
	}
}

func TestExecutarMetricaExigeArtefatoEsperado(t *testing.T) {
	executor := NovoExecutor()
	ctx := ContextoExecucao{RaizProjeto: t.TempDir()}

	resultado := executor.ExecutarMetrica(dominio.ConfigMetrica{
		Nome:            "jacoco",
		Comando:         "printf '88.2'",
		RegexValor:      `([0-9.]+)`,
		Escala:          100,
		SaidasEsperadas: []string{"target/site/jacoco/jacoco.xml"},
	}, ctx)
	if resultado.Sucesso {
		t.Fatalf("esperava falha quando o artefato esperado não existe")
	}
	if resultado.ValorNumerico != nil || resultado.NotaNormalizada != nil {
		t.Fatalf("não deveria pontuar métrica sem artefato esperado: %#v", resultado)
	}
	if !strings.Contains(resultado.SaidaErro, "artefato esperado não encontrado") {
		t.Fatalf("esperava mensagem sobre artefato esperado, recebi %q", resultado.SaidaErro)
	}
}

func TestExecutarMetricaPontuaComArtefatoEsperadoPresente(t *testing.T) {
	raiz := t.TempDir()
	arquivo := filepath.Join(raiz, "target", "site", "jacoco", "jacoco.xml")
	if err := os.MkdirAll(filepath.Dir(arquivo), 0o755); err != nil {
		t.Fatalf("mkdir artefato esperado: %v", err)
	}
	if err := os.WriteFile(arquivo, []byte("<report/>"), 0o644); err != nil {
		t.Fatalf("write artefato esperado: %v", err)
	}

	executor := NovoExecutor()
	ctx := ContextoExecucao{RaizProjeto: raiz}
	resultado := executor.ExecutarMetrica(dominio.ConfigMetrica{
		Nome:            "jacoco",
		Comando:         "printf '88.2'",
		RegexValor:      `([0-9.]+)`,
		Escala:          100,
		SaidasEsperadas: []string{"target/site/jacoco/jacoco.xml"},
	}, ctx)
	if !resultado.Sucesso {
		t.Fatalf("esperava sucesso com artefato esperado presente: %#v", resultado)
	}
	if resultado.ValorNumerico == nil || *resultado.ValorNumerico != 88.2 {
		t.Fatalf("valor numérico inesperado: %#v", resultado.ValorNumerico)
	}
}
