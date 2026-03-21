package br.unb.cic.witupllmautogen.cli;

final class HelpRequestedException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  HelpRequestedException(final String message) {
    super(message);
  }
}
