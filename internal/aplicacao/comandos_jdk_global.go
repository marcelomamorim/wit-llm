package aplicacao

import (
	"flag"
	"fmt"
	"os"
	"path/filepath"
	"runtime"

	"github.com/marceloamorim/witup-llm/internal/configuracao"
)

func executarPreparacaoJDKGlobal(args []string, service *Servico) int {
	fs := flag.NewFlagSet("prepare-jdk-global-impact", flag.ContinueOnError)
	configPath := fs.String("config", "", "Caminho para o arquivo de configuração JSON com modelos")
	generationModelKey := fs.String("generation-model", "openai_main", "Chave do modelo configurado para geração")
	jdkRoot := fs.String("jdk-root", "", "Checkout local do OpenJDK/JDK")
	witPath := fs.String("wit-analysis", "", "Arquivo wit_filtered.json do JDK")
	outputDir := fs.String("output-dir", "", "Diretório da rodada")
	requestsPath := fs.String("requests", "", "Arquivo JSONL Batch a gerar")
	methodCount := fs.Int("method-count", jdkGlobalDefaultMethodCount, "Quantidade de métodos-alvo")
	workers := fs.Int("workers", runtime.NumCPU(), "Número máximo de workers Go")
	if err := fs.Parse(args); err != nil {
		return 2
	}
	if *configPath == "" || *jdkRoot == "" || *witPath == "" || *outputDir == "" {
		fmt.Fprintln(os.Stderr, "erro: --config, --jdk-root, --wit-analysis e --output-dir são obrigatórios")
		return 2
	}
	cfg, err := configuracao.Carregar(*configPath)
	if err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}
	report, reportPath, err := service.PrepararEstudoJDKGlobal(cfg, *generationModelKey, *jdkRoot, *witPath, *outputDir, *requestsPath, *methodCount, *workers)
	if err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}
	fmt.Printf("Relatório preparação : %s\n", reportPath)
	fmt.Printf("Projeto              : %s\n", report.Projeto)
	fmt.Printf("URL operacional      : %s\n", report.URLRepositorio)
	fmt.Printf("Commit WIT           : %s\n", report.CommitWIT)
	fmt.Printf("Unidade experimental : %s\n", report.UnidadeExperimental)
	fmt.Printf("Métodos selecionados : %d\n", report.QuantidadeMetodos)
	fmt.Printf("Expaths selecionados : %d\n", report.QuantidadeExpaths)
	fmt.Printf("Requests Batch       : %d\n", report.QuantidadeRequests)
	fmt.Printf("Manifest CSV         : %s\n", report.CaminhoManifestCSV)
	fmt.Printf("Requests JSONL       : %s\n", report.CaminhoRequestsJSONL)
	return 0
}

func executarAvaliacaoJDKGlobal(args []string, service *Servico) int {
	fs := flag.NewFlagSet("evaluate-jdk-global-impact", flag.ContinueOnError)
	configPath := fs.String("config", "", "Caminho para o arquivo de configuração JSON com modelos")
	generationModelKey := fs.String("generation-model", "openai_main", "Chave do modelo configurado para geração")
	jdkRoot := fs.String("jdk-root", "", "Checkout local do OpenJDK/JDK")
	runDir := fs.String("run-dir", "", "Diretório da rodada preparada")
	responsesPath := fs.String("responses", "", "Arquivo JSONL de respostas Batch")
	errorsPath := fs.String("errors", "", "Arquivo JSONL de erros Batch")
	workers := fs.Int("workers", runtime.NumCPU(), "Número máximo de workers Go")
	if err := fs.Parse(args); err != nil {
		return 2
	}
	if *configPath == "" || *jdkRoot == "" || *runDir == "" || *responsesPath == "" {
		fmt.Fprintln(os.Stderr, "erro: --config, --jdk-root, --run-dir e --responses são obrigatórios")
		return 2
	}
	cfg, err := configuracao.Carregar(*configPath)
	if err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}
	report, reportPath, err := service.AvaliarEstudoJDKGlobal(cfg, *generationModelKey, *jdkRoot, filepath.Clean(*runDir), *responsesPath, *errorsPath, *workers)
	if err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}
	fmt.Printf("Relatório global     : %s\n", reportPath)
	fmt.Printf("Projeto              : %s\n", report.Projeto)
	fmt.Printf("URL operacional      : %s\n", report.URLRepositorio)
	fmt.Printf("Commit WIT           : %s\n", report.CommitWIT)
	fmt.Printf("Unidade experimental : %s\n", report.UnidadeExperimental)
	fmt.Printf("Variantes avaliadas  : %d\n", len(report.Variantes))
	fmt.Printf("Resumo CSV           : %s\n", report.CaminhoResumoCSV)
	fmt.Printf("Comparação CSV       : %s\n", report.CaminhoComparacaoCSV)
	return 0
}

func executarMedicaoJTRegJDKGlobal(args []string, service *Servico) int {
	fs := flag.NewFlagSet("measure-jdk-global-impact", flag.ContinueOnError)
	runDir := fs.String("run-dir", "", "Diretório da rodada com variants/")
	jtregPath := fs.String("jtreg", "", "Executável jtreg")
	testJDK := fs.String("test-jdk", "", "Imagem JDK testada, normalmente build/.../images/jdk")
	javaHome := fs.String("java-home", "", "JAVA_HOME usado para executar o jtreg")
	archX8664 := fs.Bool("arch-x86-64", false, "Executa jtreg via /usr/bin/arch -x86_64")
	baseTarget := fs.String("base-target", "", "Alvo jtreg base executado em todas as variantes; se omitido, infere alvos contextuais do manifesto")
	generatedTarget := fs.String("generated-target", jdkGlobalDefaultGeneratedDir, "Alvo jtreg dos testes gerados")
	coverageCommand := fs.String("coverage-command", "", "Comando opcional que imprime a cobertura de linhas como número")
	timeoutSeconds := fs.Int("timeout-seconds", timeoutGlobalJDK(), "Timeout por variante")
	concurrency := fs.Int("concurrency", 8, "Concorrência passada ao jtreg")
	if err := fs.Parse(args); err != nil {
		return 2
	}
	if *runDir == "" || *jtregPath == "" || *testJDK == "" {
		fmt.Fprintln(os.Stderr, "erro: --run-dir, --jtreg e --test-jdk são obrigatórios")
		return 2
	}
	report, reportPath, err := service.MedirImpactoJDKGlobalComJTReg(filepath.Clean(*runDir), *jtregPath, *testJDK, *javaHome, *baseTarget, *generatedTarget, *coverageCommand, *archX8664, *timeoutSeconds, *concurrency)
	if err != nil {
		fmt.Fprintf(os.Stderr, "erro: %v\n", err)
		return 1
	}
	fmt.Printf("Relatório jtreg      : %s\n", reportPath)
	fmt.Printf("Run dir              : %s\n", report.RunDir)
	fmt.Printf("JTReg                : %s\n", report.JTReg)
	fmt.Printf("Test JDK             : %s\n", report.TestJDK)
	fmt.Printf("Alvo base            : %s\n", report.AlvoBase)
	fmt.Printf("Alvo gerado          : %s\n", report.AlvoGerado)
	fmt.Printf("Resumo CSV           : %s\n", report.CaminhoResumoCSV)
	fmt.Printf("Comparação CSV       : %s\n", report.CaminhoComparacaoCSV)
	for _, resultado := range report.Resultados {
		fmt.Printf("%s: status=%s total=%d passed=%d failed=%d error=%d coverage=%s\n",
			resultado.Variante,
			resultado.Status,
			resultado.Total,
			resultado.Passou,
			resultado.Falhou,
			resultado.Erro,
			formatarFloatOpcional(resultado.CoberturaLinha),
		)
	}
	return 0
}
