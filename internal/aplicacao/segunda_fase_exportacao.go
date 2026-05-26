package aplicacao

import (
	"encoding/csv"
	"fmt"
	"os"
	"path/filepath"
	"strconv"
	"strings"

	"github.com/marceloamorim/witup-llm/internal/artefatos"
	"github.com/marceloamorim/witup-llm/internal/dominio"
)

func exportarCSVsSegundaFase(workspace *artefatos.EspacoTrabalho, relatorio dominio.RelatorioSegundaFase) (string, string, string, error) {
	diretorioCSV := filepath.Join(workspace.Raiz, "csv")
	if err := os.MkdirAll(diretorioCSV, 0o755); err != nil {
		return "", "", "", fmt.Errorf("ao criar diretório CSV: %w", err)
	}

	resumoCSV := filepath.Join(diretorioCSV, "phase-two-summary.csv")
	if err := escreverCSVResumoSegundaFase(resumoCSV, relatorio); err != nil {
		return "", "", "", err
	}
	metricasCSV := filepath.Join(diretorioCSV, "phase-two-metrics.csv")
	if err := escreverCSVMetricasSegundaFase(metricasCSV, relatorio); err != nil {
		return "", "", "", err
	}
	comparacaoCSV := filepath.Join(diretorioCSV, "phase-two-comparison.csv")
	if err := escreverCSVComparacaoSegundaFase(comparacaoCSV, relatorio); err != nil {
		return "", "", "", err
	}
	return resumoCSV, metricasCSV, comparacaoCSV, nil
}

func escreverCSVResumoSegundaFase(caminho string, relatorio dominio.RelatorioSegundaFase) error {
	linhas := [][]string{{
		"project_key", "project_label", "scenario", "scenario_label", "baseline_file", "baseline_kind", "execution_mode", "request_count", "repair_used", "input_tokens", "output_tokens", "estimated_cost", "method_count", "expath_count", "test_file_count", "executable_suite", "metric_score", "uncapped_metric_score", "static_metric_score", "execution_metric_score", "metric_score_cap_reason", "judge_score", "combined_score", "judge_verdict", "test_compilation", "unit_tests", "test_pass_rate", "target_method_coverage", "assertive_tests_rate", "exception_assertion_rate", "valid_java_rate", "package_path_valid_rate", "test_method_presence_rate", "target_invocation_rate", "forbidden_dependency_rate", "reflection_usage_rate", "brittle_exception_assertion_rate", "internal_state_assertion_rate", "compile_failure_category", "harness_interventions", "jacoco_line", "jacoco_line_status", "jacoco_line_status_reason", "jacoco_branch", "jacoco_branch_status", "jacoco_branch_status_reason", "pit_mutation", "pit_mutation_status", "pit_mutation_status_reason", "expath_used_count", "expath_adapted_count", "expath_discarded_count", "expath_total_count", "expath_utilization_rate",
	}}
	for _, projeto := range relatorio.Projetos {
		cenarios := []dominio.ResultadoCenarioSegundaFase{projeto.ContextoWIT, projeto.GeracaoDireta}
		if projeto.HintExcecao != nil {
			cenarios = append(cenarios, *projeto.HintExcecao)
		}
		for _, resultado := range cenarios {
			var judgeScore *float64
			var judgeVerdict string
			if resultado.AvaliacaoJuiz != nil {
				judgeScore = &resultado.AvaliacaoJuiz.Nota
				judgeVerdict = resultado.AvaliacaoJuiz.Veredito
			}
			linhas = append(linhas, []string{
				resultado.Projeto,
				resultado.RotuloProjeto,
				string(resultado.Cenario),
				resultado.RotuloHumano(),
				resultado.NomeArquivoBaseline(),
				resultado.TipoBaseline(),
				resultado.ModoExecucao,
				strconv.Itoa(resultado.RequestCount),
				strconv.FormatBool(resultado.RepairUsed),
				strconv.Itoa(resultado.InputTokens),
				strconv.Itoa(resultado.OutputTokens),
				formatarFloatCSV(resultado.EstimatedCost),
				strconv.Itoa(resultado.QuantidadeMetodos),
				strconv.Itoa(resultado.QuantidadeExpaths),
				strconv.Itoa(resultado.QuantidadeTestes),
				strconv.FormatBool(resultado.AuditoriaPontuacao.SuiteExecutavel),
				formatarFloatCSV(resultado.NotaMetricas),
				formatarFloatCSV(resultado.AuditoriaPontuacao.NotaBruta),
				formatarFloatCSV(resultado.AuditoriaPontuacao.NotaEstatica),
				formatarFloatCSV(resultado.AuditoriaPontuacao.NotaExecutavel),
				resultado.AuditoriaPontuacao.RazaoCap,
				formatarFloatCSV(judgeScore),
				formatarFloatCSV(resultado.NotaCombinada),
				judgeVerdict,
				formatarFloatCSV(valorMetricaPorNome(resultado.ResultadosMetricas, "test-compilation")),
				formatarFloatCSV(valorMetricaPorNome(resultado.ResultadosMetricas, "unit-tests")),
				formatarFloatCSV(valorMetricaPorNome(resultado.ResultadosMetricas, "test-pass-rate")),
				formatarFloatCSV(valorMetricaPorNome(resultado.ResultadosMetricas, "target-method-coverage")),
				formatarFloatCSV(valorMetricaPorNome(resultado.ResultadosMetricas, "assertive-tests-rate")),
				formatarFloatCSV(valorMetricaPorNome(resultado.ResultadosMetricas, "exception-assertion-rate")),
				formatarFloatCSV(valorMetricaPorNome(resultado.ResultadosMetricas, "valid-java-rate")),
				formatarFloatCSV(valorMetricaPorNome(resultado.ResultadosMetricas, "package-path-valid-rate")),
				formatarFloatCSV(valorMetricaPorNome(resultado.ResultadosMetricas, "test-method-presence-rate")),
				formatarFloatCSV(valorMetricaPorNome(resultado.ResultadosMetricas, "target-invocation-rate")),
				formatarFloatCSV(valorMetricaPorNome(resultado.ResultadosMetricas, "forbidden-dependency-rate")),
				formatarFloatCSV(valorMetricaPorNome(resultado.ResultadosMetricas, "reflection-usage-rate")),
				formatarFloatCSV(valorMetricaPorNome(resultado.ResultadosMetricas, "brittle-exception-assertion-rate")),
				formatarFloatCSV(valorMetricaPorNome(resultado.ResultadosMetricas, "internal-state-assertion-rate")),
				categorizarFalhaCompilacao(resultado.ResultadosMetricas),
				strings.Join(resultado.IntervencoesHarness, ";"),
				formatarFloatCSV(valorMetricaPorNome(resultado.ResultadosMetricas, "jacoco-line")),
				statusMetricaResumo(resultado, "jacoco-line").Status,
				statusMetricaResumo(resultado, "jacoco-line").Razao,
				formatarFloatCSV(valorMetricaPorNome(resultado.ResultadosMetricas, "jacoco-branch")),
				statusMetricaResumo(resultado, "jacoco-branch").Status,
				statusMetricaResumo(resultado, "jacoco-branch").Razao,
				formatarFloatCSV(valorMetricaPorNome(resultado.ResultadosMetricas, "pit-mutation")),
				statusMetricaResumo(resultado, "pit-mutation").Status,
				statusMetricaResumo(resultado, "pit-mutation").Razao,
				strconv.Itoa(contarAcoesExpath(resultado.ArquivosTeste, "used")),
				strconv.Itoa(contarAcoesExpath(resultado.ArquivosTeste, "adapted")),
				strconv.Itoa(contarAcoesExpath(resultado.ArquivosTeste, "discarded")),
				strconv.Itoa(contarTodasAcoesExpath(resultado.ArquivosTeste)),
				formatarFloatCSV(calcularTaxaUtilizacaoExpath(resultado.ArquivosTeste)),
			})
		}
	}
	return escreverCSV(caminho, linhas)
}

func escreverCSVMetricasSegundaFase(caminho string, relatorio dominio.RelatorioSegundaFase) error {
	linhas := [][]string{{
		"project_key", "scenario", "metric_name", "success", "exit_code", "numeric_value", "normalized_score", "executed_strategy", "availability_status", "availability_reason",
	}}
	for _, projeto := range relatorio.Projetos {
		cenarios := []dominio.ResultadoCenarioSegundaFase{projeto.ContextoWIT, projeto.GeracaoDireta}
		if projeto.HintExcecao != nil {
			cenarios = append(cenarios, *projeto.HintExcecao)
		}
		for _, resultado := range cenarios {
			for _, metrica := range resultado.ResultadosMetricas {
				status := statusMetrica(resultado, metrica)
				linhas = append(linhas, []string{
					resultado.Projeto,
					string(resultado.Cenario),
					metrica.Nome,
					strconv.FormatBool(metrica.Sucesso),
					strconv.Itoa(metrica.CodigoSaida),
					formatarFloatCSV(metrica.ValorNumerico),
					formatarFloatCSV(metrica.NotaNormalizada),
					metrica.EstrategiaExecutada,
					status.Status,
					status.Razao,
				})
			}
		}
	}
	return escreverCSV(caminho, linhas)
}

func escreverCSVComparacaoSegundaFase(caminho string, relatorio dominio.RelatorioSegundaFase) error {
	linhas := [][]string{{
		"project_key", "project_label", "baseline_file", "delta_direction", "wit_scenario_label", "raw_scenario_label", "wit_execution_mode", "raw_execution_mode", "wit_request_count", "raw_request_count", "delta_request_count", "wit_repair_used", "raw_repair_used", "wit_input_tokens", "raw_input_tokens", "delta_input_tokens", "wit_output_tokens", "raw_output_tokens", "delta_output_tokens", "wit_estimated_cost", "raw_estimated_cost", "delta_estimated_cost", "wit_executable_suite", "raw_executable_suite", "wit_metric_score", "raw_metric_score", "wit_uncapped_metric_score", "raw_uncapped_metric_score", "wit_static_metric_score", "raw_static_metric_score", "wit_execution_metric_score", "raw_execution_metric_score", "wit_metric_score_cap_reason", "raw_metric_score_cap_reason", "wit_judge_score", "raw_judge_score", "wit_combined_score", "raw_combined_score", "delta_metric_score", "delta_judge_score", "delta_combined_score", "wit_test_compilation", "raw_test_compilation", "wit_unit_tests", "raw_unit_tests", "delta_test_pass_rate", "delta_target_method_coverage", "delta_assertive_tests_rate", "delta_exception_assertion_rate", "delta_valid_java_rate", "delta_package_path_valid_rate", "delta_test_method_presence_rate", "delta_target_invocation_rate", "delta_forbidden_dependency_rate", "delta_reflection_usage_rate", "delta_brittle_exception_assertion_rate", "delta_internal_state_assertion_rate", "wit_compile_failure_category", "raw_compile_failure_category", "wit_harness_interventions", "raw_harness_interventions", "delta_jacoco_line", "delta_jacoco_branch", "delta_pit_mutation", "hint_metric_score", "hint_exception_assertion_rate", "delta_metric_score_hint_minus_direct", "delta_jacoco_line_hint_minus_direct", "delta_jacoco_branch_hint_minus_direct",
	}}
	for _, projeto := range relatorio.Projetos {
		var witJudgeScore *float64
		var rawJudgeScore *float64
		if projeto.ContextoWIT.AvaliacaoJuiz != nil {
			witJudgeScore = &projeto.ContextoWIT.AvaliacaoJuiz.Nota
		}
		if projeto.GeracaoDireta.AvaliacaoJuiz != nil {
			rawJudgeScore = &projeto.GeracaoDireta.AvaliacaoJuiz.Nota
		}
		linhas = append(linhas, []string{
			projeto.Projeto,
			projeto.RotuloProjeto,
			projeto.ContextoWIT.NomeArquivoBaseline(),
			direcaoDeltaComparacao(projeto),
			projeto.ContextoWIT.RotuloHumano(),
			projeto.GeracaoDireta.RotuloHumano(),
			projeto.ContextoWIT.ModoExecucao,
			projeto.GeracaoDireta.ModoExecucao,
			strconv.Itoa(projeto.ContextoWIT.RequestCount),
			strconv.Itoa(projeto.GeracaoDireta.RequestCount),
			strconv.Itoa(projeto.ContextoWIT.RequestCount - projeto.GeracaoDireta.RequestCount),
			strconv.FormatBool(projeto.ContextoWIT.RepairUsed),
			strconv.FormatBool(projeto.GeracaoDireta.RepairUsed),
			strconv.Itoa(projeto.ContextoWIT.InputTokens),
			strconv.Itoa(projeto.GeracaoDireta.InputTokens),
			strconv.Itoa(projeto.ContextoWIT.InputTokens - projeto.GeracaoDireta.InputTokens),
			strconv.Itoa(projeto.ContextoWIT.OutputTokens),
			strconv.Itoa(projeto.GeracaoDireta.OutputTokens),
			strconv.Itoa(projeto.ContextoWIT.OutputTokens - projeto.GeracaoDireta.OutputTokens),
			formatarFloatCSV(projeto.ContextoWIT.EstimatedCost),
			formatarFloatCSV(projeto.GeracaoDireta.EstimatedCost),
			formatarFloatCSV(deltaPontuacoes(projeto.ContextoWIT.EstimatedCost, projeto.GeracaoDireta.EstimatedCost)),
			strconv.FormatBool(projeto.ContextoWIT.AuditoriaPontuacao.SuiteExecutavel),
			strconv.FormatBool(projeto.GeracaoDireta.AuditoriaPontuacao.SuiteExecutavel),
			formatarFloatCSV(projeto.ContextoWIT.NotaMetricas),
			formatarFloatCSV(projeto.GeracaoDireta.NotaMetricas),
			formatarFloatCSV(projeto.ContextoWIT.AuditoriaPontuacao.NotaBruta),
			formatarFloatCSV(projeto.GeracaoDireta.AuditoriaPontuacao.NotaBruta),
			formatarFloatCSV(projeto.ContextoWIT.AuditoriaPontuacao.NotaEstatica),
			formatarFloatCSV(projeto.GeracaoDireta.AuditoriaPontuacao.NotaEstatica),
			formatarFloatCSV(projeto.ContextoWIT.AuditoriaPontuacao.NotaExecutavel),
			formatarFloatCSV(projeto.GeracaoDireta.AuditoriaPontuacao.NotaExecutavel),
			projeto.ContextoWIT.AuditoriaPontuacao.RazaoCap,
			projeto.GeracaoDireta.AuditoriaPontuacao.RazaoCap,
			formatarFloatCSV(witJudgeScore),
			formatarFloatCSV(rawJudgeScore),
			formatarFloatCSV(projeto.ContextoWIT.NotaCombinada),
			formatarFloatCSV(projeto.GeracaoDireta.NotaCombinada),
			formatarFloatCSV(projeto.DeltaNotaMetricas),
			formatarFloatCSV(deltaPontuacoes(witJudgeScore, rawJudgeScore)),
			formatarFloatCSV(deltaPontuacoes(projeto.ContextoWIT.NotaCombinada, projeto.GeracaoDireta.NotaCombinada)),
			formatarFloatCSV(valorMetricaPorNome(projeto.ContextoWIT.ResultadosMetricas, "test-compilation")),
			formatarFloatCSV(valorMetricaPorNome(projeto.GeracaoDireta.ResultadosMetricas, "test-compilation")),
			formatarFloatCSV(valorMetricaPorNome(projeto.ContextoWIT.ResultadosMetricas, "unit-tests")),
			formatarFloatCSV(valorMetricaPorNome(projeto.GeracaoDireta.ResultadosMetricas, "unit-tests")),
			formatarFloatCSV(deltaMetricaWITMenosDireto(projeto, "test-pass-rate")),
			formatarFloatCSV(deltaMetricaWITMenosDireto(projeto, "target-method-coverage")),
			formatarFloatCSV(deltaMetricaWITMenosDireto(projeto, "assertive-tests-rate")),
			formatarFloatCSV(deltaMetricaWITMenosDireto(projeto, "exception-assertion-rate")),
			formatarFloatCSV(deltaMetricaWITMenosDireto(projeto, "valid-java-rate")),
			formatarFloatCSV(deltaMetricaWITMenosDireto(projeto, "package-path-valid-rate")),
			formatarFloatCSV(deltaMetricaWITMenosDireto(projeto, "test-method-presence-rate")),
			formatarFloatCSV(deltaMetricaWITMenosDireto(projeto, "target-invocation-rate")),
			formatarFloatCSV(deltaMetricaWITMenosDireto(projeto, "forbidden-dependency-rate")),
			formatarFloatCSV(deltaMetricaWITMenosDireto(projeto, "reflection-usage-rate")),
			formatarFloatCSV(deltaMetricaWITMenosDireto(projeto, "brittle-exception-assertion-rate")),
			formatarFloatCSV(deltaMetricaWITMenosDireto(projeto, "internal-state-assertion-rate")),
			categorizarFalhaCompilacao(projeto.ContextoWIT.ResultadosMetricas),
			categorizarFalhaCompilacao(projeto.GeracaoDireta.ResultadosMetricas),
			strings.Join(projeto.ContextoWIT.IntervencoesHarness, ";"),
			strings.Join(projeto.GeracaoDireta.IntervencoesHarness, ";"),
			formatarFloatCSV(projeto.DeltaCoberturaLinha),
			formatarFloatCSV(projeto.DeltaCoberturaBranch),
			formatarFloatCSV(projeto.DeltaMutacao),
			hintMetricScore(projeto),
			hintExceptionAssertionRate(projeto),
			formatarFloatCSV(projeto.DeltaNotaMetricasHintMenosDireto),
			formatarFloatCSV(projeto.DeltaCoberturaLinhaHintMenosDireto),
			formatarFloatCSV(projeto.DeltaCoberturaBranchHintMenosDireto),
		})
	}
	return escreverCSV(caminho, linhas)
}

func direcaoDeltaComparacao(projeto dominio.ComparacaoProjetoSegundaFase) string {
	if strings.TrimSpace(projeto.DirecaoDelta) != "" {
		return projeto.DirecaoDelta
	}
	return "WIT_CONTEXT_MINUS_DIRECT_TESTS"
}

func deltaMetricaWITMenosDireto(projeto dominio.ComparacaoProjetoSegundaFase, nome string) *float64 {
	return deltaPontuacoes(
		valorMetricaPorNome(projeto.ContextoWIT.ResultadosMetricas, nome),
		valorMetricaPorNome(projeto.GeracaoDireta.ResultadosMetricas, nome),
	)
}

func escreverCSV(caminho string, linhas [][]string) error {
	arquivo, err := os.Create(caminho)
	if err != nil {
		return fmt.Errorf("ao criar CSV %q: %w", caminho, err)
	}
	defer arquivo.Close()

	escritor := csv.NewWriter(arquivo)
	if err := escritor.WriteAll(linhas); err != nil {
		return fmt.Errorf("ao escrever CSV %q: %w", caminho, err)
	}
	return nil
}

func formatarFloatCSV(valor *float64) string {
	if valor == nil {
		return ""
	}
	return strconv.FormatFloat(*valor, 'f', 2, 64)
}

type statusDisponibilidadeMetrica struct {
	Status string
	Razao  string
}

func statusMetricaResumo(resultado dominio.ResultadoCenarioSegundaFase, nome string) statusDisponibilidadeMetrica {
	for _, metrica := range resultado.ResultadosMetricas {
		if metrica.Nome == nome {
			return statusMetrica(resultado, metrica)
		}
	}
	return statusDisponibilidadeMetrica{Status: "not_measured", Razao: "metric_missing"}
}

func statusMetrica(resultado dominio.ResultadoCenarioSegundaFase, metrica dominio.ResultadoMetrica) statusDisponibilidadeMetrica {
	if metrica.Sucesso {
		return statusDisponibilidadeMetrica{Status: "ok"}
	}
	switch strings.TrimSpace(metrica.Tipo) {
	case "coverage", "mutation", "tests":
		switch {
		case resultado.QuantidadeTestes == 0:
			return statusDisponibilidadeMetrica{Status: "not_applicable", Razao: "no_test_files"}
		case resultado.AuditoriaPontuacao.CompilacaoMensurada && !resultado.AuditoriaPontuacao.CompilacaoSucesso:
			return statusDisponibilidadeMetrica{Status: "blocked", Razao: "compile_failed"}
		case metrica.TempoEsgotado:
			return statusDisponibilidadeMetrica{Status: "failed", Razao: "timeout"}
		case strings.Contains(strings.ToLower(metrica.SaidaErro+"\n"+metrica.SaidaPadrao), "no such file or directory") ||
			strings.Contains(strings.ToLower(metrica.SaidaErro+"\n"+metrica.SaidaPadrao), "artefato esperado"):
			return statusDisponibilidadeMetrica{Status: "failed", Razao: "missing_report"}
		default:
			return statusDisponibilidadeMetrica{Status: "failed", Razao: "command_failed"}
		}
	default:
		if metrica.TempoEsgotado {
			return statusDisponibilidadeMetrica{Status: "failed", Razao: "timeout"}
		}
		return statusDisponibilidadeMetrica{Status: "failed", Razao: "command_failed"}
	}
}

func categorizarFalhaCompilacao(resultados []dominio.ResultadoMetrica) string {
	var compilacao *dominio.ResultadoMetrica
	for indice := range resultados {
		if resultados[indice].Nome == "test-compilation" {
			compilacao = &resultados[indice]
			break
		}
	}
	if compilacao == nil {
		return "not_measured"
	}
	if compilacao.Sucesso {
		return ""
	}
	texto := strings.ToLower(compilacao.SaidaPadrao + "\n" + compilacao.SaidaErro)
	switch {
	case strings.Contains(texto, "tempo limite") || compilacao.TempoEsgotado:
		return "timeout"
	case strings.Contains(texto, "source option") || strings.Contains(texto, "target option") || strings.Contains(texto, "release version"):
		return "jdk_profile"
	case strings.Contains(texto, "package ") && strings.Contains(texto, " does not exist"):
		return "missing_dependency_or_import"
	case strings.Contains(texto, "cannot find symbol"):
		return "unknown_symbol"
	case strings.Contains(texto, "constructor ") && strings.Contains(texto, " cannot be applied"):
		return "constructor_mismatch"
	case strings.Contains(texto, "method ") && strings.Contains(texto, " cannot be applied"):
		return "method_signature_mismatch"
	case strings.Contains(texto, " has private access") || strings.Contains(texto, " has protected access") || strings.Contains(texto, " is not public"):
		return "visibility"
	case strings.Contains(texto, "compilation failure") || strings.Contains(texto, "failed to execute goal"):
		return "compile_error"
	default:
		return "unknown_compile_failure"
	}
}

func contarAcoesExpath(arquivos []dominio.ArquivoTesteDetalhadoSegundaFase, acao string) int {
	total := 0
	for _, arquivo := range arquivos {
		for _, a := range arquivo.AcoesExpath {
			if a.Acao == acao {
				total++
			}
		}
	}
	return total
}

func contarTodasAcoesExpath(arquivos []dominio.ArquivoTesteDetalhadoSegundaFase) int {
	total := 0
	for _, arquivo := range arquivos {
		total += len(arquivo.AcoesExpath)
	}
	return total
}

func calcularTaxaUtilizacaoExpath(arquivos []dominio.ArquivoTesteDetalhadoSegundaFase) *float64 {
	total := contarTodasAcoesExpath(arquivos)
	if total == 0 {
		return nil
	}
	usados := contarAcoesExpath(arquivos, "used") + contarAcoesExpath(arquivos, "adapted")
	taxa := float64(usados) / float64(total)
	return &taxa
}

func hintMetricScore(projeto dominio.ComparacaoProjetoSegundaFase) string {
	if projeto.HintExcecao == nil {
		return ""
	}
	return formatarFloatCSV(projeto.HintExcecao.NotaMetricas)
}

func hintExceptionAssertionRate(projeto dominio.ComparacaoProjetoSegundaFase) string {
	if projeto.HintExcecao == nil {
		return ""
	}
	return formatarFloatCSV(valorMetricaPorNome(projeto.HintExcecao.ResultadosMetricas, "exception-assertion-rate"))
}
