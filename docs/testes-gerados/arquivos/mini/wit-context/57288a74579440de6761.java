/*
 * @test
 * @summary Test constructor Locale(String language, String country, String variant) for null checks and normal behavior
 * @run main LocaleWitupTest
 */

public class LocaleWitupTest {
    public static void main(String[] args) {
        testNullLanguage();
        testNullCountry();
        testNullVariant();
        testValidLocale();
        System.out.println("LocaleWitupTest passed");
    }

    private static void testNullLanguage() {
        try {
            new java.util.Locale(null, "US", "VAR");
            throw new RuntimeException("Expected NullPointerException for null language");
        } catch (NullPointerException expected) {
        }
    }

    private static void testNullCountry() {
        try {
            new java.util.Locale("en", null, "VAR");
            throw new RuntimeException("Expected NullPointerException for null country");
        } catch (NullPointerException expected) {
        }
    }

    private static void testNullVariant() {
        try {
            new java.util.Locale("en", "US", null);
            throw new RuntimeException("Expected NullPointerException for null variant");
        } catch (NullPointerException expected) {
        }
    }

    private static void testValidLocale() {
        java.util.Locale loc = new java.util.Locale("en", "US", "VAR");
        if (!"en".equals(loc.getLanguage())) {
            throw new RuntimeException("Language mismatch");
        }
        if (!"US".equals(loc.getCountry())) {
            throw new RuntimeException("Country mismatch");
        }
        if (!"VAR".equals(loc.getVariant())) {
            throw new RuntimeException("Variant mismatch");
        }
    }
}
