package br.unb.cic.witupllmautogen.application;

import br.unb.cic.witupllmautogen.analysis.AnalysisProvider;
import br.unb.cic.witupllmautogen.analysis.model.AnalysisReport;
import br.unb.cic.witupllmautogen.application.mode.GenerationMode;
import br.unb.cic.witupllmautogen.application.mode.GenerationModeFactory;
import br.unb.cic.witupllmautogen.io.FileGateway;
import br.unb.cic.witupllmautogen.prompt.PromptComposer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class DocumentationGenerationFacade {

  private final AnalysisProvider analysisProvider;
  private final PromptComposer promptComposer;
  private final GenerationModeFactory generationModeFactory;
  private final FileGateway fileGateway;
  private final GeneratedMarkdownValidator generatedMarkdownValidator;

  public DocumentationGenerationFacade(
      final AnalysisProvider analysisProvider,
      final PromptComposer promptComposer,
      final GenerationModeFactory generationModeFactory,
      final FileGateway fileGateway) {
    this(
        analysisProvider,
        promptComposer,
        generationModeFactory,
        fileGateway,
        new GeneratedMarkdownValidator());
  }

  public DocumentationGenerationFacade(
      final AnalysisProvider analysisProvider,
      final PromptComposer promptComposer,
      final GenerationModeFactory generationModeFactory,
      final FileGateway fileGateway,
      final GeneratedMarkdownValidator generatedMarkdownValidator) {
    this.analysisProvider = analysisProvider;
    this.promptComposer = promptComposer;
    this.generationModeFactory = generationModeFactory;
    this.fileGateway = fileGateway;
    this.generatedMarkdownValidator = generatedMarkdownValidator;
  }

  public GenerationResult generate(final GenerationRequest request)
      throws IOException, InterruptedException {
    AnalysisReport analysisReport = analysisProvider.analyse(request.classPath(), request.className());
    fileGateway.writeJson(request.analysisOutputPath(), analysisReport);

    String overviewText = fileGateway.readTextOrEmpty(request.overviewFile());
    String prompt = promptComposer.buildPrompt(overviewText, analysisReport);

    GenerationMode mode = generationModeFactory.create(request.dryRun(), request.ollamaUrl());
    Path outputPath = mode.execute(request, prompt, fileGateway);
    validateGeneratedOutputIfNeeded(request, analysisReport, outputPath);

    return new GenerationResult(request.dryRun(), request.analysisOutputPath(), outputPath);
  }

  private void validateGeneratedOutputIfNeeded(
      final GenerationRequest request, final AnalysisReport analysisReport, final Path outputPath)
      throws IOException {
    if (request.dryRun()) {
      return;
    }

    String generatedMarkdown = fileGateway.readText(outputPath);
    try {
      generatedMarkdownValidator.validate(generatedMarkdown, analysisReport);
    } catch (IllegalStateException ex) {
      Files.deleteIfExists(outputPath);
      throw ex;
    }
  }
}
