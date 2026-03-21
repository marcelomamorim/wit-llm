package br.unb.cic.witupllmautogen.cli;

import br.unb.cic.witupllmautogen.analysis.WitupAnalysisExtractor;
import br.unb.cic.witupllmautogen.application.DocumentationGenerationFacade;
import br.unb.cic.witupllmautogen.application.GeneratedMarkdownValidator;
import br.unb.cic.witupllmautogen.application.GenerationRequest;
import br.unb.cic.witupllmautogen.application.GenerationResult;
import br.unb.cic.witupllmautogen.application.mode.GenerationModeFactory;
import br.unb.cic.witupllmautogen.common.JsonMapperFactory;
import br.unb.cic.witupllmautogen.io.LocalFileGateway;
import br.unb.cic.witupllmautogen.ollama.OllamaClient;
import br.unb.cic.witupllmautogen.prompt.PromptBuilder;

public final class Main {
  private Main() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static void main(final String[] args) throws java.io.IOException, InterruptedException {
    CliArgs cliArgs;
    try {
      cliArgs = CliArgs.parse(args);
    } catch (HelpRequestedException ex) {
      System.out.println(ex.getMessage());
      return;
    } catch (IllegalArgumentException ex) {
      System.err.println(CliConsole.error(ex.getMessage()));
      return;
    }

    printLaunchSummary(cliArgs);
    DocumentationGenerationFacade facade = buildFacade();
    GenerationRequest request = toRequest(cliArgs);
    GenerationResult result = facade.generate(request);

    System.out.println(CliConsole.section("Result"));
    System.out.println(CliConsole.success(result.describe()));
  }

  private static DocumentationGenerationFacade buildFacade() {
    return new DocumentationGenerationFacade(
        new WitupAnalysisExtractor(),
        new PromptBuilder(),
        new GenerationModeFactory(OllamaClient::new),
        new LocalFileGateway(JsonMapperFactory.createDefaultMapper()),
        new GeneratedMarkdownValidator());
  }

  private static GenerationRequest toRequest(final CliArgs cliArgs) {
    return new GenerationRequest(
        cliArgs.classPath(),
        cliArgs.className(),
        cliArgs.model(),
        cliArgs.numThread(),
        cliArgs.ollamaUrl(),
        cliArgs.outputPath(),
        cliArgs.analysisOutputPath(),
        cliArgs.promptOutputPath(),
        cliArgs.overviewFile(),
        cliArgs.dryRun());
  }

  private static void printLaunchSummary(final CliArgs cliArgs) {
    System.out.println(CliConsole.banner());
    System.out.println(CliConsole.section("Run"));
    System.out.println(CliConsole.note("Class      : " + cliArgs.className()));
    System.out.println(CliConsole.note("Classpath  : " + cliArgs.classPath()));
    System.out.println(CliConsole.note("Model      : " + cliArgs.model()));
    System.out.println(CliConsole.note("Mode       : " + (cliArgs.dryRun() ? "dry-run" : "generation")));
    System.out.println(CliConsole.note("Analysis   : " + cliArgs.analysisOutputPath().toAbsolutePath()));
    System.out.println(
        CliConsole.note(
            (cliArgs.dryRun() ? "Prompt     : " : "Output     : ")
                + (cliArgs.dryRun()
                    ? cliArgs.promptOutputPath().toAbsolutePath()
                    : cliArgs.outputPath().toAbsolutePath())));
  }
}
