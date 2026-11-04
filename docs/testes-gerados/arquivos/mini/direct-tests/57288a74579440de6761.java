/*
 * @test
 * @summary Test java.util.Locale constructor with language, country, and variant
 * @run main java.util.LocaleWitupTest
 */

public class LocaleWitupTest {
    public static void main(String[] args) {
        testValidLocaleCreation();
        testNullLanguageThrows();
        testNullCountryThrows();
        testNullVariantThrows();
    }

    private static void testValidLocaleCreation() {
        java.util.Locale loc = new java.util.Locale("en", "US", "POSIX");
        if (!"en".equals(loc.getLanguage())) {
            throw new RuntimeException("Language mismatch: expected 'en' but got '" + loc.getLanguage() + "'");
        }
        if (!"US".equals(loc.getCountry())) {
            throw new RuntimeException("Country mismatch: expected 'US' but got '" + loc.getCountry() + "'");
        }
        if (!"POSIX".equals(loc.getVariant())) {
            throw new RuntimeException("Variant mismatch: expected 'POSIX' but got '" + loc.getVariant() + "'");
        }
    }

    private static void testNullLanguageThrows() {
        try {
            new java.util.Locale(null, "US", "POSIX");
            throw new RuntimeException("Expected NullPointerException for null language");
        } catch (NullPointerException expected) {
        }
    }

    private static void testNullCountryThrows() {
        try {
            new java.util.Locale("en", null, "POSIX");
            throw new RuntimeException("Expected NullPointerException for null country");
        } catch (NullPointerException expected) {
        }
    }

    private static void testNullVariantThrows() {
        try {
            new java.util.Locale("en", "US", null);
            throw new RuntimeException("Expected NullPointerException for null variant");
        } catch (NullPointerException expected) {
        }
    }
}
