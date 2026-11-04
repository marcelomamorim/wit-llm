/*
 * @test
 * @summary Test getDisplayLanguage(Locale) method of java.util.Locale for null argument and normal usage
 * @run main LocaleWitupTest
 */

import java.util.Locale;

public class LocaleWitupTest {
    public static void main(String[] args) {
        testGetDisplayLanguageWithNull();
        testGetDisplayLanguageWithValidLocale();
    }

    private static void testGetDisplayLanguageWithNull() {
        Locale locale = new Locale("en", "US");
        try {
            locale.getDisplayLanguage(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testGetDisplayLanguageWithValidLocale() {
        Locale locale = new Locale("en", "US");
        Locale french = new Locale("fr", "FR");
        String displayLanguage = locale.getDisplayLanguage(french);
        if (displayLanguage == null || displayLanguage.isEmpty()) {
            throw new RuntimeException("getDisplayLanguage returned null or empty string");
        }
        // Also test that the returned string is not the language code itself (basic sanity)
        if (displayLanguage.equals("en")) {
            throw new RuntimeException("getDisplayLanguage returned language code instead of display name");
        }
    }
}
