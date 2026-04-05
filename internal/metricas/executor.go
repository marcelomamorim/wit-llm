package metricas

import (
	"fmt"
	"os"
	"os/exec"
	"path/filepath"
	"regexp"
	"strconv"
	"strings"

	"github.com/marceloamorim/witup-llm/internal/dominio"
	"github.com/marceloamorim/witup-llm/internal/registro"
)

// ContextoExecucao fornece placeholders usados na renderização dos comandos de métricas.
type ContextoExecucao struct {
	RaizProjeto       string
	DiretorioExecucao string
	DiretorioTestes   string
	CaminhoAnalise    string
	CaminhoGeracao    string
	ChaveModelo       string
}

// Executor executa métricas comando a comando e interpreta saídas numéricas.
type Executor struct{}

// NovoExecutor cria uma instância do executor de métricas.
func NovoExecutor() *Executor {
	return &Executor{}
}

// ExecutarTodas executa todas as métricas configuradas.
func (r *Executor) ExecutarTodas(metrics []dominio.ConfigMetrica, ctx ContextoExecucao) []dominio.ResultadoMetrica {
	resultados := make([]dominio.ResultadoMetrica, 0, len(metrics))
	for _, metrica := range metrics {
		resultados = append(resultados, r.ExecutarMetrica(metrica, ctx))
	}
	return resultados
}

// ExecutarMetrica executa uma métrica no diretório de trabalho configurado.
func (r *Executor) ExecutarMetrica(metric dominio.ConfigMetrica, ctx ContextoExecucao) dominio.ResultadoMetrica {
	comando := renderizarComando(metric.Comando, ctx)
	diretorioTrabalho := ctx.RaizProjeto
	if strings.TrimSpace(metric.DiretorioTrabalho) != "" {
		diretorioTrabalho = filepath.Clean(filepath.Join(ctx.RaizProjeto, metric.DiretorioTrabalho))
	}

	cmd := exec.Command("sh", "-c", comando)
	cmd.Dir = diretorioTrabalho
	registro.Info("metricas", "executando métrica=%s diretório=%s comando=%s", metric.Nome, diretorioTrabalho, comando)
	saidaPadraoBytes, err := cmd.Output()
	saidaErro := ""
	codigoSaida := 0
	if err != nil {
		codigoSaida = 1
		if e, ok := err.(*exec.ExitError); ok {
			codigoSaida = e.ExitCode()
			saidaErro = string(e.Stderr)
		} else {
			saidaErro = err.Error()
		}
	}
	saidaPadrao := string(saidaPadraoBytes)

	if codigoSaida == 0 {
		if err := validarSaidasEsperadas(metric.SaidasEsperadas, diretorioTrabalho); err != nil {
			codigoSaida = 1
			if saidaErro != "" {
				saidaErro += "\n"
			}
			saidaErro += err.Error()
		}
	}

	valorNumerico := interpretarValorNumerico(metric.RegexValor, saidaPadrao, codigoSaida == 0)
	notaNormalizada := normalizarNota(valorNumerico, metric.Escala)

	resultado := dominio.ResultadoMetrica{
		Nome:            metric.Nome,
		Tipo:            metric.Tipo,
		Comando:         comando,
		Sucesso:         codigoSaida == 0,
		CodigoSaida:     codigoSaida,
		SaidaPadrao:     saidaPadrao,
		SaidaErro:       saidaErro,
		ValorNumerico:   valorNumerico,
		NotaNormalizada: notaNormalizada,
		Peso:            metric.Peso,
		Descricao:       metric.Descricao,
	}
	registro.Info("metricas", "métrica=%s finalizada sucesso=%t codigo=%d nota=%s", metric.Nome, resultado.Sucesso, resultado.CodigoSaida, FormatarPontuacao(resultado.NotaNormalizada))
	return resultado
}

// AgregarPontuacao calcula a média ponderada das métricas normalizadas.
func AgregarPontuacao(results []dominio.ResultadoMetrica) *float64 {
	totalPonderado := 0.0
	somaPesos := 0.0
	for _, resultado := range results {
		if resultado.Peso <= 0 {
			continue
		}
		somaPesos += resultado.Peso
		if resultado.NotaNormalizada == nil {
			continue
		}
		totalPonderado += (*resultado.NotaNormalizada) * resultado.Peso
	}
	if somaPesos == 0 {
		return nil
	}
	nota := totalPonderado / somaPesos
	return &nota
}

// renderizarComando substitui placeholders do comando pelos valores do contexto.
func renderizarComando(template string, ctx ContextoExecucao) string {
	substituidor := strings.NewReplacer(
		"{project_root}", ctx.RaizProjeto,
		"{run_dir}", ctx.DiretorioExecucao,
		"{tests_dir}", ctx.DiretorioTestes,
		"{analysis_path}", ctx.CaminhoAnalise,
		"{generation_path}", ctx.CaminhoGeracao,
		"{model_key}", ctx.ChaveModelo,
	)
	return substituidor.Replace(template)
}

// interpretarValorNumerico extrai um valor numérico da saída padrão usando regex configurada.
//
// A extração só acontece quando o comando foi concluído com sucesso. Isso evita
// contaminar métricas com números acidentais presentes em logs de erro.
func interpretarValorNumerico(regexValor, stdout string, comandoBemSucedido bool) *float64 {
	if strings.TrimSpace(regexValor) == "" || !comandoBemSucedido {
		return nil
	}
	regex, err := regexp.Compile(regexValor)
	if err != nil {
		return nil
	}
	grupos := regex.FindStringSubmatch(stdout)
	if len(grupos) < 2 {
		return nil
	}
	valorBruto := strings.TrimSpace(strings.TrimSuffix(grupos[1], "%"))
	valor, err := strconv.ParseFloat(valorBruto, 64)
	if err != nil {
		return nil
	}
	return &valor
}

// validarSaidasEsperadas garante que arquivos ou diretórios prometidos pela
// métrica realmente foram materializados antes de pontuar o resultado.
func validarSaidasEsperadas(saidas []string, diretorioTrabalho string) error {
	for _, caminho := range saidas {
		caminho = strings.TrimSpace(caminho)
		if caminho == "" {
			continue
		}
		absoluto := caminho
		if !filepath.IsAbs(absoluto) {
			absoluto = filepath.Join(diretorioTrabalho, caminho)
		}
		info, err := os.Stat(absoluto)
		if err != nil {
			return fmt.Errorf("artefato esperado não encontrado: %s", absoluto)
		}
		if info.IsDir() {
			continue
		}
		if info.Size() == 0 {
			return fmt.Errorf("artefato esperado vazio: %s", absoluto)
		}
	}
	return nil
}

// normalizarNota converte um valor bruto para a escala percentual do projeto.
func normalizarNota(value *float64, scale float64) *float64 {
	if value == nil || scale <= 0 {
		return nil
	}
	nota := (*value / scale) * 100.0
	if nota < 0 {
		nota = 0
	}
	if nota > 100 {
		nota = 100
	}
	return &nota
}

// CombinarPontuacoes combina nota de métricas e nota do juiz com peso 70/30 quando ambas existem.
func CombinarPontuacoes(notaMetrica, notaJuiz *float64) *float64 {
	switch {
	case notaMetrica != nil && notaJuiz != nil:
		nota := (*notaMetrica * 0.7) + (*notaJuiz * 0.3)
		return &nota
	case notaMetrica != nil:
		nota := *notaMetrica
		return &nota
	case notaJuiz != nil:
		nota := *notaJuiz
		return &nota
	default:
		return nil
	}
}

// FormatarPontuacao mantém a formatação estável das notas exibidas na CLI.
func FormatarPontuacao(value *float64) string {
	if value == nil {
		return "-"
	}
	return fmt.Sprintf("%.2f", *value)
}
