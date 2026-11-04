/*
 * @test
 * @summary Test getFallbackLocale method of ResourceBundle for various locales
 * @run main ResourceBundleWitupTest
 */

import java.util.Locale;

public class ResourceBundleWitupTest {

    public static void main(String[] args) {
        testGetFallbackLocaleWithDefaultLocale();
        testGetFallbackLocaleWithDifferentLocale();
        testGetFallbackLocaleWithNullBaseName();
    }

    static void testGetFallbackLocaleWithDefaultLocale() {
        Locale defaultLocale = Locale.getDefault();
        String baseName = "dummy";
        Locale locale = defaultLocale;
        Locale result = ResourceBundle.getFallbackLocale(baseName, locale);
        if (result != null) {
            throw new AssertionError("Expected null when locale equals default locale, but got: " + result);
        }
    }

    static void testGetFallbackLocaleWithDifferentLocale() {
        Locale defaultLocale = Locale.getDefault();
        String baseName = "dummy";
        Locale locale = new Locale("en", "US");
        if (locale.equals(defaultLocale)) {
            locale = new Locale("fr", "FR");
        }
        Locale result = ResourceBundle.getFallbackLocale(baseName, locale);
        if (result == null || !result.equals(defaultLocale)) {
            throw new AssertionError("Expected default locale when locale differs, but got: " + result);
        }
    }

    static void testGetFallbackLocaleWithNullBaseName() {
        try {
            ResourceBundle.getFallbackLocale(null, Locale.getDefault());
            throw new AssertionError("Expected NullPointerException for null baseName");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
