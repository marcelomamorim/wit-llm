package br.unb.cic.witupllmautogen.ollama;

public interface TextGenerator {
  String generate(String model, String prompt, Integer numThread)
      throws java.io.IOException, InterruptedException;
}
