/*
 * @test
 * @summary Verifies Locale.getDisplayLanguage(Locale) for common and empty locales
 * @run main LocaleWitupTest
 */

public class LocaleWitupTest {

    public static void main(String[] args) {
        check("English", Locale.ENGLISH.getDisplayLanguage(Locale.ENGLISH));
        check("French", Locale.FRENCH.getDisplayLanguage(Locale.ENGLISH));
        check("English", new Locale("en").getDisplayLanguage(new Locale("en")));
        check("", Locale.ROOT.getDisplayLanguage(Locale.ENGLISH));
        check("English", Locale.US.getDisplayLanguage(Locale.ENGLISH));
        check("English", Locale.ENGLISH.getDisplayLanguage(Locale.US));
    }

    private static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected [" + expected + "] but was [" + actual + "]");
        }
    }
}
