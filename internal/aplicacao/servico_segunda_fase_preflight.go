package aplicacao

import (
	"context"
	"fmt"
	"os"
	"os/exec"
	"path/filepath"
	"strings"
	"time"

	"github.com/marceloamorim/witup-llm/internal/artefatos"
	"github.com/marceloamorim/witup-llm/internal/dominio"
	"github.com/marceloamorim/witup-llm/internal/registro"
	"github.com/marceloamorim/witup-llm/internal/witup"
)

var (
	lookPathPreflight = exec.LookPath
	execCommandCtx    = exec.CommandContext
)

type cachePreflightSegundaFase struct {
	catalogos map[string][]dominio.DescritorMetodo
	builds    map[string]resultadoBuildCheckSegundaFase
}

type resultadoBuildCheckSegundaFase struct {
	saida string
	err   error
}

// PreflightSegundaFase valida ambiente, baselines e alinhamento dos projetos da
// fase 2 antes de uma rodada paga.
func (s *Servico) PreflightSegundaFase(cfg *dominio.ConfigAplicacao, configPath string, checkBuild bool) (dominio.RelatorioPreflightSegundaFase, string, error) {
	if len(cfg.SegundaFase.Projetos) == 0 {
		return dominio.RelatorioPreflightSegundaFase{}, "", fmt.Errorf("phase_two.projects precisa listar ao menos um projeto")
	}
	ctxHeartbeat, cancelCtxHeartbeat := context.WithCancel(context.Background())
	progressoHeartbeat := registro.NovoProgresso(len(cfg.SegundaFase.Projetos))
	cancelHeartbeat := registro.IniciarHeartbeat(ctxHeartbeat, "phase-two", "preflight", "all", "running", progressoHeartbeat)
	defer cancelHeartbeat()
	defer cancelCtxHeartbeat()

	workspace, err := artefatos.NovoEspacoTrabalho(cfg.Fluxo.DiretorioSaida, artefatos.NovoIDExecucao("phase-two-preflight"))
	if err != nil {
		return dominio.RelatorioPreflightSegundaFase{}, "", err
	}

	ambiente := diagnosticarAmbienteSegundaFase()
	relatorio := dominio.RelatorioPreflightSegundaFase{
		IDExecucao:       filepath.Base(workspace.Raiz),
		GeradoEm:         dominio.HorarioUTC(),
		CaminhoConfig:    configPath,
		VerificacaoBuild: checkBuild,
		Pronto:           ambiente.JavaDisponivel,
		Ambiente:         ambiente,
		Projetos:         make([]dominio.DiagnosticoProjetoSegundaFase, 0, len(cfg.SegundaFase.Projetos)),
	}

	cache := cachePreflightSegundaFase{
		catalogos: map[string][]dominio.DescritorMetodo{},
		builds:    map[string]resultadoBuildCheckSegundaFase{},
	}
	for _, projeto := range cfg.SegundaFase.Projetos {
		diagnostico := s.preflightProjetoSegundaFase(cfg, projeto, ambiente, checkBuild, cache)
		progressoHeartbeat.Incrementar()
		if !diagnostico.Pronto {
			relatorio.Pronto = false
		}
		relatorio.Projetos = append(relatorio.Projetos, diagnostico)
	}

	if relatorio.Pronto {
		relatorio.ComandoSugerido = "witup executar-segunda-fase --config <config> --generation-model openai_main"
	}

	caminhoRelatorio := filepath.Join(workspace.Raiz, "phase-two-preflight.json")
	if err := artefatos.EscreverJSON(caminhoRelatorio, relatorio); err != nil {
		return dominio.RelatorioPreflightSegundaFase{}, "", err
	}

	registro.Info("phase-two", "preflight concluído: pronto=%t relatório=%s", relatorio.Pronto, caminhoRelatorio)
	return relatorio, caminhoRelatorio, nil
}

func (s *Servico) preflightProjetoSegundaFase(
	cfg *dominio.ConfigAplicacao,
	projeto dominio.ConfigProjetoSegundaFase,
	ambiente dominio.DiagnosticoAmbienteSegundaFase,
	checkBuild bool,
	cache cachePreflightSegundaFase,
) dominio.DiagnosticoProjetoSegundaFase {
	diagnostico := dominio.DiagnosticoProjetoSegundaFase{
		Projeto:         projeto.Chave,
		RotuloProjeto:   projeto.Rotulo,
		Raiz:            projeto.Raiz,
		CaminhoBaseline: projeto.CaminhoBaseline,
		OverviewFile:    projeto.OverviewFile,
		ContainersAlvo:  append([]string{}, projeto.ContainersAlvo...),
		Pronto:          true,
	}

	if info, err := os.Stat(projeto.Raiz); err != nil || !info.IsDir() {
		diagnostico.Problemas = append(diagnostico.Problemas, fmt.Sprintf("raiz do projeto inválida: %s", projeto.Raiz))
		diagnostico.Pronto = false
		return diagnostico
	}
	if info, err := os.Stat(projeto.CaminhoBaseline); err != nil || info.IsDir() {
		diagnostico.Problemas = append(diagnostico.Problemas, fmt.Sprintf("baseline WIT inválido: %s", projeto.CaminhoBaseline))
		diagnostico.Pronto = false
		return diagnostico
	}
	if strings.TrimSpace(projeto.OverviewFile) != "" {
		if info, err := os.Stat(projeto.OverviewFile); err != nil || info.IsDir() {
			diagnostico.Problemas = append(diagnostico.Problemas, fmt.Sprintf("overview_file inválido: %s", projeto.OverviewFile))
			diagnostico.Pronto = false
		}
	}

	diagnostico.TemPomXML = arquivoExiste(filepath.Join(projeto.Raiz, "pom.xml"))
	diagnostico.TemMavenWrapper = arquivoExiste(filepath.Join(projeto.Raiz, "mvnw"))
	if !diagnostico.TemPomXML {
		diagnostico.Problemas = append(diagnostico.Problemas, "projeto sem pom.xml; as métricas Maven não vão rodar")
		diagnostico.Pronto = false
	}
	if !diagnostico.TemMavenWrapper && !ambiente.MavenDisponivel {
		diagnostico.Problemas = append(diagnostico.Problemas, "nem mvnw no projeto nem mvn disponível no PATH")
		diagnostico.Pronto = false
	}

	cfgProjeto := clonarConfiguracaoParaProjetoSegundaFase(cfg, projeto)
	metodosCatalogados, err := s.carregarCatalogoSegundaFaseComCache(cfgProjeto, cache.catalogos)
	if err != nil {
		diagnostico.Problemas = append(diagnostico.Problemas, fmt.Sprintf("falha ao catalogar métodos: %v", err))
		diagnostico.Pronto = false
		return diagnostico
	}
	metodosCatalogados = filtrarMetodosPorContainers(metodosCatalogados, projeto.ContainersAlvo)
	diagnostico.MetodosCatalogados = len(metodosCatalogados)
	if len(metodosCatalogados) == 0 {
		if len(projeto.ContainersAlvo) > 0 {
			diagnostico.Problemas = append(diagnostico.Problemas, "nenhum método restou após aplicar target_containers")
		} else {
			diagnostico.Problemas = append(diagnostico.Problemas, "nenhum método Java foi catalogado")
		}
		diagnostico.Pronto = false
		return diagnostico
	}

	baselineReport, err := witup.CarregarAnalise(projeto.CaminhoBaseline)
	if err != nil {
		diagnostico.Problemas = append(diagnostico.Problemas, fmt.Sprintf("falha ao carregar baseline WIT: %v", err))
		diagnostico.Pronto = false
		return diagnostico
	}
	diagnostico.MetodosBaseline = baselineReport.TotalMetodos
	if diagnostico.MetodosBaseline == 0 {
		diagnostico.MetodosBaseline = len(baselineReport.Analises)
	}

	_, metodosAlvo, resumo := alinharWITUPAoCatalogo(baselineReport, metodosCatalogados, cfgProjeto.Fluxo.MaximoMetodos)
	diagnostico.MetodosAlinhados = len(metodosAlvo)
	if diagnostico.MetodosAlinhados == 0 {
		diagnostico.Problemas = append(diagnostico.Problemas, "baseline não alinhou nenhum método ao checkout atual")
		diagnostico.Pronto = false
		return diagnostico
	}
	if resumo.QuantidadeNaoEncontrados > 0 {
		diagnostico.Avisos = append(diagnostico.Avisos, fmt.Sprintf("%d métodos do baseline não foram encontrados no checkout atual", resumo.QuantidadeNaoEncontrados))
	}

	if checkBuild && diagnostico.TemPomXML && ambiente.JavaDisponivel && (diagnostico.TemMavenWrapper || ambiente.MavenDisponivel) {
		diagnostico.BuildCheckExecutado = true
		resultadoBuild := executarBuildCheckSegundaFaseComCache(projeto.Raiz, diagnostico.TemMavenWrapper, cache.builds)
		saida, err := resultadoBuild.saida, resultadoBuild.err
		diagnostico.SaidaBuildCheck = resumirSaidaPreflight(saida)
		if err != nil {
			diagnostico.BuildCheckSucesso = false
			diagnostico.Problemas = append(diagnostico.Problemas, fmt.Sprintf("build check falhou: %v", err))
			diagnostico.Pronto = false
			return diagnostico
		}
		diagnostico.BuildCheckSucesso = true
	}

	return diagnostico
}

func executarBuildCheckSegundaFaseComCache(raizProjeto string, usarWrapper bool, cache map[string]resultadoBuildCheckSegundaFase) resultadoBuildCheckSegundaFase {
	chave := filepath.Clean(raizProjeto)
	if usarWrapper {
		chave += "|wrapper"
	} else {
		chave += "|maven"
	}
	if resultado, ok := cache[chave]; ok {
		return resultado
	}
	saida, err := executarBuildCheckSegundaFase(raizProjeto, usarWrapper)
	resultado := resultadoBuildCheckSegundaFase{saida: saida, err: err}
	cache[chave] = resultado
	return resultado
}

func diagnosticarAmbienteSegundaFase() dominio.DiagnosticoAmbienteSegundaFase {
	diagnostico := dominio.DiagnosticoAmbienteSegundaFase{}

	javaPath, javaVersion, err := detectarFerramentaPreflight("java", "-version")
	if err != nil {
		diagnostico.Problemas = append(diagnostico.Problemas, fmt.Sprintf("java indisponível: %v", err))
	} else {
		diagnostico.JavaDisponivel = true
		diagnostico.JavaPath = javaPath
		diagnostico.JavaVersion = javaVersion
	}

	mavenPath, mavenVersion, err := detectarFerramentaPreflight("mvn", "-v")
	if err != nil {
		diagnostico.Avisos = append(diagnostico.Avisos, fmt.Sprintf("mvn indisponível no PATH: %v", err))
	} else {
		diagnostico.MavenDisponivel = true
		diagnostico.MavenPath = mavenPath
		diagnostico.MavenVersion = mavenVersion
	}

	return diagnostico
}

func detectarFerramentaPreflight(nome string, args ...string) (string, string, error) {
	caminho, err := lookPathPreflight(nome)
	if err != nil {
		return "", "", err
	}
	ctx, cancel := context.WithTimeout(context.Background(), 20*time.Second)
	defer cancel()
	cmd := execCommandCtx(ctx, caminho, args...)
	saida, err := cmd.CombinedOutput()
	if err != nil {
		return caminho, "", fmt.Errorf("%s: %w", nome, err)
	}
	return caminho, primeiraLinhaNaoVazia(string(saida)), nil
}

func executarBuildCheckSegundaFase(raizProjeto string, usarWrapper bool) (string, error) {
	comando := "mvn"
	if usarWrapper {
		comando = filepath.Join(raizProjeto, "mvnw")
	}
	ctx, cancel := context.WithTimeout(context.Background(), 6*time.Minute)
	defer cancel()
	progressoHeartbeat := registro.NovoProgresso(1)
	cancelHeartbeat := registro.IniciarHeartbeat(ctx, "phase-two", "preflight_build_check", filepath.Base(raizProjeto), "running", progressoHeartbeat)
	defer cancelHeartbeat()
	args := []string{"-q"}
	if repoLocal := strings.TrimSpace(os.Getenv("MAVEN_REPO_LOCAL")); repoLocal != "" {
		args = append(args, "-Dmaven.repo.local="+repoLocal)
	}
	if perfilArgs := strings.TrimSpace(os.Getenv("MAVEN_PROFILE_ARGS")); perfilArgs != "" {
		args = append(args, strings.Fields(perfilArgs)...)
	}
	args = append(args, "-DskipTests", "test-compile")
	cmd := execCommandCtx(ctx, comando, args...)
	cmd.Dir = raizProjeto
	saida, err := cmd.CombinedOutput()
	progressoHeartbeat.Incrementar()
	if err != nil {
		return string(saida), err
	}
	return string(saida), nil
}

func arquivoExiste(caminho string) bool {
	info, err := os.Stat(caminho)
	if err != nil {
		return false
	}
	return !info.IsDir()
}

func primeiraLinhaNaoVazia(texto string) string {
	for _, linha := range strings.Split(texto, "\n") {
		linha = strings.TrimSpace(linha)
		if linha != "" {
			return linha
		}
	}
	return ""
}

func resumirSaidaPreflight(texto string) string {
	texto = strings.TrimSpace(texto)
	if len(texto) <= 800 {
		return texto
	}
	return strings.TrimSpace(texto[:800]) + "\n...[truncado]"
}
