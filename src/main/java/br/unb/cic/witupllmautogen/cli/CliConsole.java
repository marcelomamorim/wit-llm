package br.unb.cic.witupllmautogen.cli;

public final class CliConsole {

  private static final String RESET = "\u001B[0m";
  private static final String BOLD = "\u001B[1m";
  private static final String DIM = "\u001B[2m";
  private static final String TEAL = "\u001B[38;5;44m";
  private static final String LIME = "\u001B[38;5;83m";
  private static final String SKY = "\u001B[38;5;117m";
  private static final String GOLD = "\u001B[38;5;221m";
  private static final String ROSE = "\u001B[38;5;203m";
  private static final String SLATE = "\u001B[38;5;245m";

  private CliConsole() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static String banner() {
    return colorize(
            TEAL,
            BOLD
                + """
 __          ___ _                 _     _     __  __
 \\ \\        / (_) |               | |   | |   |  \\/  |
  \\ \\  /\\  / / _| |_ _   _ _ __   | |   | |   | \\  / |
   \\ \\/  \\/ / | | __| | | | '_ \\  | |   | |   | |\\/| |
    \\  /\\  /  | | |_| |_| | |_) | | |___| |___| |  | |
     \\/  \\/   |_|\\__|\\__,_| .__/  |_____|_____|_|  |_|
                          | |
                          |_|
 """)
        + colorize(LIME, "  WITUp-powered unit test generation for the terminal\n")
        + colorize(SLATE, DIM + "  Analyze classes with witup-core and emit focused JUnit 5 tests.\n");
  }

  public static String section(final String title) {
    return colorize(SKY, BOLD + "\n[" + title + "]");
  }

  public static String success(final String message) {
    return colorize(LIME, BOLD + "OK  " + RESET + message);
  }

  public static String error(final String message) {
    return colorize(ROSE, BOLD + "ERR " + RESET + message);
  }

  public static String note(final String message) {
    return colorize(GOLD, "->  " + message);
  }

  private static String colorize(final String color, final String text) {
    if (usePlainText()) {
      return stripAnsi(text);
    }
    return color + text + RESET;
  }

  private static boolean usePlainText() {
    return System.console() == null || "true".equalsIgnoreCase(System.getenv("NO_COLOR"));
  }

  private static String stripAnsi(final String text) {
    return text.replace(RESET, "")
        .replace(BOLD, "")
        .replace(DIM, "")
        .replace(TEAL, "")
        .replace(LIME, "")
        .replace(SKY, "")
        .replace(GOLD, "")
        .replace(ROSE, "")
        .replace(SLATE, "");
  }
}
