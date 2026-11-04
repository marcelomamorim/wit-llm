/*
 * @test
 * @summary Test java.util.Locale.getDisplayLanguage(Locale) method
 * @run main LocaleWitupTest
 */

import java.util.Locale;

public class LocaleWitupTest {
    public static void main(String[] args) {
        // Test 1: getDisplayLanguage with default locale
        Locale locale = new Locale("fr", "FR");
        String displayLang = locale.getDisplayLanguage(Locale.ENGLISH);
        if (displayLang == null || displayLang.isEmpty()) {
            throw new RuntimeException("getDisplayLanguage returned null or empty string");
        }

        // Test 2: getDisplayLanguage with same locale
        String displayLangSame = locale.getDisplayLanguage(locale);
        if (displayLangSame == null || displayLangSame.isEmpty()) {
            throw new RuntimeException("getDisplayLanguage with same locale returned null or empty string");
        }

        // Test 3: getDisplayLanguage with root locale
        String displayLangRoot = locale.getDisplayLanguage(Locale.ROOT);
        if (displayLangRoot == null || displayLangRoot.isEmpty()) {
            throw new RuntimeException("getDisplayLanguage with ROOT locale returned null or empty string");
        }

        // Test 4: getDisplayLanguage with null argument should throw NullPointerException
        boolean npeThrown = false;
        try {
            locale.getDisplayLanguage(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException when passing null to getDisplayLanguage");
        }

        // Test 5: getDisplayLanguage on locale with empty language
        Locale emptyLangLocale = new Locale("", "US");
        String displayLangEmpty = emptyLangLocale.getDisplayLanguage(Locale.ENGLISH);
        if (displayLangEmpty == null) {
            throw new RuntimeException("getDisplayLanguage returned null for locale with empty language");
        }

        System.out.println("LocaleWitupTest passed");
    }
}
