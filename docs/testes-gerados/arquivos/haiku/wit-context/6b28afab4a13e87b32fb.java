/*
 * @test
 * @summary WITUP-derived checks for Locale.getDisplayLanguage(Locale)
 * @run main LocaleWitupTest
 */

public class LocaleWitupTest {

    public static void main(String[] args) {
        testNullInLocaleThrowsNPE();
        testEnglishDisplayNameInEnglish();
        testFrenchDisplayNameInEnglish();
        testEmptyLanguageReturnsEmptyString();
    }

    static void testNullInLocaleThrowsNPE() {
        Locale locale = new Locale("en");
        try {
            locale.getDisplayLanguage(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null inLocale");
    }

    static void testEnglishDisplayNameInEnglish() {
        Locale locale = new Locale("en");
        String name = locale.getDisplayLanguage(Locale.ENGLISH);
        if (!"English".equals(name)) {
            throw new RuntimeException("Unexpected display language: " + name);
        }
    }

    static void testFrenchDisplayNameInEnglish() {
        Locale locale = new Locale("fr");
        String name = locale.getDisplayLanguage(Locale.ENGLISH);
        if (!"French".equals(name)) {
            throw new RuntimeException("Unexpected display language: " + name);
        }
    }

    static void testEmptyLanguageReturnsEmptyString() {
        Locale locale = new Locale("");
        String name = locale.getDisplayLanguage(Locale.ENGLISH);
        if (name == null || !name.isEmpty()) {
            throw new RuntimeException("Expected empty display language, got: " + name);
        }
    }
}
