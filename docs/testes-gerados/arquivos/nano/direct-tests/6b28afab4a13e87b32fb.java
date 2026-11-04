/*
 * @test
 * @summary Test getDisplayLanguage(Locale inLocale) method of java.util.Locale
 * @run main LocaleWitupTest
 */

import java.util.Locale;

public class LocaleWitupTest {

    public static void main(String[] args) {
        testGetDisplayLanguageSameLocale();
        testGetDisplayLanguageDifferentLocale();
        testGetDisplayLanguageNullLocale();
    }

    static void testGetDisplayLanguageSameLocale() {
        Locale locale = new Locale("en", "US");
        String displayLanguage = locale.getDisplayLanguage(locale);
        System.out.println("Display language (same locale): " + displayLanguage);
        if (displayLanguage == null || displayLanguage.isEmpty()) {
            throw new AssertionError("Expected non-empty display language for same locale");
        }
    }

    static void testGetDisplayLanguageDifferentLocale() {
        Locale locale = new Locale("en", "US");
        Locale inLocale = new Locale("fr", "FR");
        String displayLanguage = locale.getDisplayLanguage(inLocale);
        System.out.println("Display language (in French): " + displayLanguage);
        if (displayLanguage == null || displayLanguage.isEmpty()) {
            throw new AssertionError("Expected non-empty display language in French locale");
        }
    }

    static void testGetDisplayLanguageNullLocale() {
        Locale locale = new Locale("es", "ES");
        String displayLanguage = locale.getDisplayLanguage(null);
        System.out.println("Display language (null locale): " + displayLanguage);
        if (displayLanguage == null || displayLanguage.isEmpty()) {
            throw new AssertionError("Expected non-empty display language with null locale");
        }
    }
}
