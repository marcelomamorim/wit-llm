/*
 * @test
 * @summary Locale.getDisplayScript returns an empty string for a locale without script
 * @run main LocaleWitupTest
 */

public class LocaleWitupTest {

    public static void main(String[] args) {
        Locale language = new Locale("en");
        Locale inLocale = new Locale("en", "US");

        String script = language.getDisplayScript(inLocale);
        if (script == null || !script.isEmpty()) {
            throw new RuntimeException("Expected empty display script for locale without script, got: " + script);
        }

        Locale other = new Locale("pt", "BR");
        String otherScript = other.getDisplayScript(new Locale("pt"));
        if (otherScript == null || !otherScript.isEmpty()) {
            throw new RuntimeException("Expected empty display script for pt_BR, got: " + otherScript);
        }
    }
}
