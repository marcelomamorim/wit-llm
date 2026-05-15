package aplicacao

import (
	"context"
	"encoding/csv"
	"fmt"
	"os"
	"os/exec"
	"path/filepath"
	"regexp"
	"sort"
	"strconv"
	"strings"
	"time"

	"github.com/marceloamorim/witup-llm/internal/artefatos"
	"github.com/marceloamorim/witup-llm/internal/dominio"
	"github.com/marceloamorim/witup-llm/internal/registro"
)

const (
	jdkGlobalJTRegReportFile     = "results_jdk_global_jtreg.json"
	jdkGlobalJTRegSummaryCSV     = "results_jdk_global_jtreg_summary.csv"
	jdkGlobalJTRegComparisonCSV  = "results_jdk_global_jtreg_comparison.csv"
	jdkGlobalDefaultGeneratedDir = "test/jdk/witup/generated"
)

// MedirImpactoJDKGlobalComJTReg executa jtreg sobre as variantes já
// materializadas do estudo global e registra métricas comparáveis.
func (s *Servico) MedirImpactoJDKGlobalComJTReg(runDir, jtregPath, testJDK, javaHome, baseTarget, generatedTarget, coverageCommand string, archX8664 bool, timeoutSeconds, concurrency int) (dominio.RelatorioJTRegJDKGlobal, string, error) {
	runDir = filepath.Clean(runDir)
	if strings.TrimSpace(runDir) == "" || runDir == "." {
		return dominio.RelatorioJTRegJDKGlobal{}, "", fmt.Errorf("run-dir é obrigatório")
	}
	if strings.TrimSpace(jtregPath) == "" {
		return dominio.RelatorioJTRegJDKGlobal{}, "", fmt.Errorf("jtreg é obrigatório")
	}
	if strings.TrimSpace(testJDK) == "" {
		return dominio.RelatorioJTRegJDKGlobal{}, "", fmt.Errorf("test-jdk é obrigatório")
	}
	if timeoutSeconds <= 0 {
		timeoutSeconds = timeoutGlobalJDK()
	}
	if concurrency <= 0 {
		concurrency = 1
	}
	if strings.TrimSpace(generatedTarget) == "" {
		generatedTarget = jdkGlobalDefaultGeneratedDir
	}
	if strings.TrimSpace(baseTarget) == "" {
		baseTarget = resolverAlvoBaseContextualJDKGlobal(runDir)
		if strings.TrimSpace(baseTarget) != "" {
			registro.Info("jdk-global", "alvo base contextual inferido: %s", baseTarget)
		}
	}
	variantes := []dominio.ResultadoJTRegJDKGlobal{
		{Variante: "baseline", Cenario: "BASELINE", RaizProjeto: filepath.Join(runDir, "variants", "baseline")},
		{Variante: "wit-context", Cenario: string(dominio.CenarioSegundaFaseContextoWIT), RaizProjeto: filepath.Join(runDir, "variants", "wit-context")},
		{Variante: "direct-tests", Cenario: string(dominio.CenarioSegundaFaseDireto), RaizProjeto: filepath.Join(runDir, "variants", "direct-tests")},
	}
	resultados := make([]dominio.ResultadoJTRegJDKGlobal, 0, len(variantes))
	for _, variante := range variantes {
		resultados = append(resultados, executarJTRegVarianteJDKGlobal(variante, runDir, jtregPath, testJDK, javaHome, baseTarget, generatedTarget, coverageCommand, archX8664, timeoutSeconds, concurrency))
	}
	report := dominio.RelatorioJTRegJDKGlobal{
		IDExecucao:           artefatos.NovoIDExecucao("jdk-global-jtreg"),
		GeradoEm:             time.Now().UTC().Format(time.RFC3339),
		RunDir:               runDir,
		JTReg:                jtregPath,
		TestJDK:              testJDK,
		JavaHome:             javaHome,
		ArchX8664:            archX8664,
		AlvoBase:             baseTarget,
		AlvoGerado:           generatedTarget,
		ComandoCobertura:     coverageCommand,
		Resultados:           resultados,
		CaminhoResumoCSV:     filepath.Join(runDir, jdkGlobalJTRegSummaryCSV),
		CaminhoComparacaoCSV: filepath.Join(runDir, jdkGlobalJTRegComparisonCSV),
	}
	reportPath := filepath.Join(runDir, jdkGlobalJTRegReportFile)
	if err := artefatos.EscreverJSON(reportPath, report); err != nil {
		return dominio.RelatorioJTRegJDKGlobal{}, "", err
	}
	if err := escreverResumoJTRegJDKGlobalCSV(report.CaminhoResumoCSV, report); err != nil {
		return dominio.RelatorioJTRegJDKGlobal{}, "", err
	}
	if err := escreverComparacaoJTRegJDKGlobalCSV(report.CaminhoComparacaoCSV, report); err != nil {
		return dominio.RelatorioJTRegJDKGlobal{}, "", err
	}
	return report, reportPath, nil
}

func executarJTRegVarianteJDKGlobal(variante dominio.ResultadoJTRegJDKGlobal, runDir, jtregPath, testJDK, javaHome, baseTarget, generatedTarget, coverageCommand string, archX8664 bool, timeoutSeconds, concurrency int) dominio.ResultadoJTRegJDKGlobal {
	variante.ReportDir = filepath.Join(runDir, "jtreg-results", variante.Variante)
	variante.WorkDir = filepath.Join(runDir, "jtreg-work", variante.Variante)
	variante.StatusCobertura = "skipped"
	targets := alvosJTRegJDKGlobal(variante.RaizProjeto, variante.Variante, baseTarget, generatedTarget)
	variante.Alvos = targets
	if len(targets) == 0 {
		variante.Status = "skipped"
		return variante
	}
	if _, err := os.Stat(variante.RaizProjeto); err != nil {
		variante.Status = "failed"
		variante.SaidaErro = err.Error()
		return variante
	}
	_ = os.RemoveAll(variante.ReportDir)
	_ = os.RemoveAll(variante.WorkDir)
	if err := os.MkdirAll(variante.ReportDir, 0o755); err != nil {
		variante.Status = "failed"
		variante.SaidaErro = err.Error()
		return variante
	}
	if err := os.MkdirAll(variante.WorkDir, 0o755); err != nil {
		variante.Status = "failed"
		variante.SaidaErro = err.Error()
		return variante
	}
	args := []string{
		"-agentvm",
		"-automatic",
		"-ignore:quiet",
		"-verbose:summary",
		"-retain:fail,error",
		fmt.Sprintf("-concurrency:%d", concurrency),
		"-timeoutFactor:4",
		"-jdk:" + testJDK,
		"-dir:" + variante.RaizProjeto,
		"-reportDir:" + variante.ReportDir,
		"-workDir:" + variante.WorkDir,
	}
	args = append(args, targets...)
	inicio := time.Now()
	ctx, cancel := context.WithTimeout(context.Background(), time.Duration(timeoutSeconds)*time.Second)
	defer cancel()
	progresso := registro.NovoProgresso(1)
	cancelHeartbeat := registro.IniciarHeartbeat(ctx, "jdk-global", "jtreg", variante.Variante, "running", progresso)
	defer cancelHeartbeat()
	command := jtregPath
	commandArgs := args
	if archX8664 {
		command = "/usr/bin/arch"
		commandArgs = append([]string{"-x86_64", jtregPath}, args...)
	}
	cmd := exec.CommandContext(ctx, command, commandArgs...)
	cmd.Dir = variante.RaizProjeto
	cmd.Env = ambienteJTRegJDKGlobal(javaHome)
	out, err := cmd.CombinedOutput()
	progresso.Incrementar()
	variante.DuracaoMillis = time.Since(inicio).Milliseconds()
	variante.SaidaPadrao = string(out)
	variante.Status = "ok"
	if err != nil {
		variante.Status = "failed"
		variante.CodigoSaida = 1
		if exit, ok := err.(*exec.ExitError); ok {
			variante.CodigoSaida = exit.ExitCode()
		}
		variante.SaidaErro = err.Error()
		if ctx.Err() == context.DeadlineExceeded {
			variante.Status = "timeout"
		}
	}
	preencherResumoJTRegJDKGlobal(&variante)
	if strings.TrimSpace(coverageCommand) != "" {
		linha, status, stderr := executarCoberturaJDKGlobal(variante, coverageCommand, timeoutSeconds)
		variante.CoberturaLinha = linha
		variante.StatusCobertura = status
		if stderr != "" {
			if variante.SaidaErro != "" {
				variante.SaidaErro += "\n"
			}
			variante.SaidaErro += stderr
		}
	}
	return variante
}

func ambienteJTRegJDKGlobal(javaHome string) []string {
	env := os.Environ()
	javaHome = strings.TrimSpace(javaHome)
	if javaHome == "" {
		return env
	}
	env = append(env, "JAVA_HOME="+javaHome)
	env = append(env, "PATH="+filepath.Join(javaHome, "bin")+string(os.PathListSeparator)+os.Getenv("PATH"))
	return env
}

func alvosJTRegJDKGlobal(root, variant, baseTarget, generatedTarget string) []string {
	alvos := []string{}
	if strings.TrimSpace(baseTarget) != "" {
		alvos = append(alvos, strings.Fields(baseTarget)...)
	}
	if variant != "baseline" && strings.TrimSpace(generatedTarget) != "" {
		gerado := filepath.Join(root, filepath.FromSlash(generatedTarget))
		if dirTemJava(gerado) {
			alvos = append(alvos, strings.Fields(generatedTarget)...)
		}
	}
	return alvos
}

func resolverAlvoBaseContextualJDKGlobal(runDir string) string {
	var preparation dominio.RelatorioPreparacaoJDKGlobal
	if err := artefatos.LerJSON(filepath.Join(runDir, jdkGlobalPreparationFile), &preparation); err != nil {
		registro.Info("jdk-global", "não foi possível inferir alvo base contextual: %v", err)
		return ""
	}
	baselineRoot := filepath.Join(runDir, "variants", "baseline")
	seen := map[string]bool{}
	var targets []string
	for _, metodo := range preparation.Metodos {
		for _, target := range candidatosAlvoBaseContextualJDKGlobal(baselineRoot, metodo) {
			if seen[target] {
				continue
			}
			seen[target] = true
			targets = append(targets, target)
		}
	}
	sort.Strings(targets)
	return strings.Join(targets, " ")
}

func candidatosAlvoBaseContextualJDKGlobal(root string, metodo dominio.MetodoJDKGlobal) []string {
	sourcePath := filepath.ToSlash(metodo.CaminhoArquivo)
	packagePath := pacotePathFonteJDKGlobal(sourcePath)
	className := strings.TrimSuffix(filepath.Base(sourcePath), filepath.Ext(sourcePath))

	candidatos := []string{}
	if strings.Contains(sourcePath, "com/sun/java/util/jar/pack/") {
		candidatos = append(candidatos, "test/jdk/tools/pack200")
	}
	if packagePath != "" && className != "" {
		candidatos = append(candidatos, "test/jdk/"+packagePath+"/"+className)
	}
	if packagePath != "" {
		candidatos = append(candidatos, "test/jdk/"+packagePath)
	}

	for _, candidato := range candidatos {
		if alvoJTRegExisteComJava(root, candidato) {
			return []string{candidato}
		}
	}
	return nil
}

func pacotePathFonteJDKGlobal(sourcePath string) string {
	sourcePath = filepath.ToSlash(sourcePath)
	marcadores := []string{"/classes/", "src/"}
	for _, marcador := range marcadores {
		idx := strings.Index(sourcePath, marcador)
		if idx < 0 {
			continue
		}
		resto := sourcePath[idx+len(marcador):]
		if marcador == "src/" {
			partes := strings.SplitN(resto, "/", 4)
			if len(partes) == 4 && partes[1] == "share" && partes[2] == "classes" {
				resto = partes[3]
			}
		}
		dir := filepath.ToSlash(filepath.Dir(resto))
		if dir == "." {
			return ""
		}
		return dir
	}
	return ""
}

func alvoJTRegExisteComJava(root, target string) bool {
	path := filepath.Join(root, filepath.FromSlash(target))
	info, err := os.Stat(path)
	if err != nil {
		return false
	}
	if !info.IsDir() {
		return strings.HasSuffix(path, ".java")
	}
	return dirTemJava(path)
}

func dirTemJava(root string) bool {
	found := false
	_ = filepath.WalkDir(root, func(path string, d os.DirEntry, err error) error {
		if err != nil || found {
			return nil
		}
		if !d.IsDir() && strings.HasSuffix(path, ".java") {
			found = true
		}
		return nil
	})
	return found
}

func preencherResumoJTRegJDKGlobal(resultado *dominio.ResultadoJTRegJDKGlobal) {
	summary := filepath.Join(resultado.ReportDir, "text", "summary.txt")
	data, err := os.ReadFile(summary)
	if err != nil {
		preencherResumoJTRegPorTexto(resultado, resultado.SaidaPadrao)
		return
	}
	texto := string(data)
	for _, line := range strings.Split(texto, "\n") {
		line = strings.TrimSpace(line)
		switch {
		case strings.Contains(line, " Passed."):
			resultado.Passou++
		case strings.Contains(line, " Failed."):
			resultado.Falhou++
		case strings.Contains(line, " Error."):
			resultado.Erro++
		case strings.Contains(line, " Not run."):
			resultado.NaoExecutado++
		}
	}
	resultado.Total = resultado.Passou + resultado.Falhou + resultado.Erro + resultado.NaoExecutado
	if resultado.Total == 0 {
		preencherResumoJTRegPorTexto(resultado, resultado.SaidaPadrao)
	}
}

func preencherResumoJTRegPorTexto(resultado *dominio.ResultadoJTRegJDKGlobal, texto string) {
	re := regexp.MustCompile(`(?i)Test results:\s*(.*)`)
	matches := re.FindStringSubmatch(texto)
	if len(matches) < 2 {
		return
	}
	partes := strings.Split(matches[1], ",")
	for _, parte := range partes {
		campos := strings.Fields(strings.TrimSpace(strings.TrimSuffix(parte, ".")))
		if len(campos) < 2 {
			continue
		}
		valor, err := strconv.Atoi(campos[len(campos)-1])
		if err != nil {
			continue
		}
		chave := strings.ToLower(campos[0])
		switch chave {
		case "passed":
			resultado.Passou = valor
		case "failed":
			resultado.Falhou = valor
		case "error", "errors":
			resultado.Erro = valor
		case "not":
			resultado.NaoExecutado = valor
		}
	}
	resultado.Total = resultado.Passou + resultado.Falhou + resultado.Erro + resultado.NaoExecutado
}

func executarCoberturaJDKGlobal(resultado dominio.ResultadoJTRegJDKGlobal, command string, timeoutSeconds int) (*float64, string, string) {
	command = strings.ReplaceAll(command, "{variant_root}", shellQuote(resultado.RaizProjeto))
	command = strings.ReplaceAll(command, "{report_dir}", shellQuote(resultado.ReportDir))
	command = strings.ReplaceAll(command, "{work_dir}", shellQuote(resultado.WorkDir))
	command = strings.ReplaceAll(command, "{variant}", shellQuote(resultado.Variante))
	ctx, cancel := context.WithTimeout(context.Background(), time.Duration(timeoutSeconds)*time.Second)
	defer cancel()
	cmd := exec.CommandContext(ctx, "sh", "-c", command)
	cmd.Dir = resultado.RaizProjeto
	out, err := cmd.CombinedOutput()
	if err != nil {
		return nil, "failed", string(out) + "\n" + err.Error()
	}
	if valor := primeiroNumeroMetricaGlobal(string(out)); valor != nil {
		return valor, "ok", ""
	}
	return nil, "no_numeric_output", string(out)
}

func shellQuote(value string) string {
	return "'" + strings.ReplaceAll(value, "'", "'\\''") + "'"
}

func escreverResumoJTRegJDKGlobalCSV(path string, report dominio.RelatorioJTRegJDKGlobal) error {
	if err := os.MkdirAll(filepath.Dir(path), 0o755); err != nil {
		return err
	}
	file, err := os.Create(path)
	if err != nil {
		return err
	}
	defer file.Close()
	writer := csv.NewWriter(file)
	defer writer.Flush()
	if err := writer.Write([]string{"variant", "scenario", "status", "exit_code", "total", "passed", "failed", "error", "not_run", "duration_ms", "line_coverage", "coverage_status", "targets", "report_dir", "work_dir"}); err != nil {
		return err
	}
	for _, resultado := range report.Resultados {
		if err := writer.Write([]string{
			resultado.Variante,
			resultado.Cenario,
			resultado.Status,
			strconv.Itoa(resultado.CodigoSaida),
			strconv.Itoa(resultado.Total),
			strconv.Itoa(resultado.Passou),
			strconv.Itoa(resultado.Falhou),
			strconv.Itoa(resultado.Erro),
			strconv.Itoa(resultado.NaoExecutado),
			strconv.FormatInt(resultado.DuracaoMillis, 10),
			formatarFloatOpcional(resultado.CoberturaLinha),
			resultado.StatusCobertura,
			strings.Join(resultado.Alvos, " "),
			resultado.ReportDir,
			resultado.WorkDir,
		}); err != nil {
			return err
		}
	}
	return writer.Error()
}

func escreverComparacaoJTRegJDKGlobalCSV(path string, report dominio.RelatorioJTRegJDKGlobal) error {
	if err := os.MkdirAll(filepath.Dir(path), 0o755); err != nil {
		return err
	}
	file, err := os.Create(path)
	if err != nil {
		return err
	}
	defer file.Close()
	writer := csv.NewWriter(file)
	defer writer.Flush()
	if err := writer.Write([]string{"metric", "baseline", "wit_context", "direct_tests", "delta_wit_minus_baseline", "delta_direct_minus_baseline", "delta_wit_minus_direct"}); err != nil {
		return err
	}
	valores := map[string]map[string]*float64{}
	for _, resultado := range report.Resultados {
		valores[resultado.Variante] = map[string]*float64{
			"jtreg_total":     floatPtrJDKGlobal(float64(resultado.Total)),
			"jtreg_passed":    floatPtrJDKGlobal(float64(resultado.Passou)),
			"jtreg_failed":    floatPtrJDKGlobal(float64(resultado.Falhou)),
			"jtreg_error":     floatPtrJDKGlobal(float64(resultado.Erro)),
			"jtreg_not_run":   floatPtrJDKGlobal(float64(resultado.NaoExecutado)),
			"jtreg_pass_rate": passRateJDKGlobal(resultado),
			"line_coverage":   resultado.CoberturaLinha,
		}
	}
	metrics := []string{"jtreg_total", "jtreg_passed", "jtreg_failed", "jtreg_error", "jtreg_not_run", "jtreg_pass_rate", "line_coverage"}
	sort.Strings(metrics)
	for _, metric := range metrics {
		base := valorMetricaVariante(valores, "baseline", metric)
		wit := valorMetricaVariante(valores, "wit-context", metric)
		direct := valorMetricaVariante(valores, "direct-tests", metric)
		if err := writer.Write([]string{
			metric,
			formatarFloatOpcional(base),
			formatarFloatOpcional(wit),
			formatarFloatOpcional(direct),
			formatarDeltaOpcional(wit, base),
			formatarDeltaOpcional(direct, base),
			formatarDeltaOpcional(wit, direct),
		}); err != nil {
			return err
		}
	}
	return writer.Error()
}

func floatPtrJDKGlobal(value float64) *float64 {
	return &value
}

func passRateJDKGlobal(resultado dominio.ResultadoJTRegJDKGlobal) *float64 {
	if resultado.Total == 0 {
		return nil
	}
	value := float64(resultado.Passou) / float64(resultado.Total) * 100
	return &value
}
