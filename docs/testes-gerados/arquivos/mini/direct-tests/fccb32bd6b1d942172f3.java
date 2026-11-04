/*
 * @test
 * @summary Test java.util.Locale.getUnicodeLocaleType(String) method
 * @run main LocaleWitupTest
 */

public class LocaleWitupTest {
    public static void main(String[] args) {
        testValidKeyWithNoExtensions();
        testValidKeyWithExtensions();
        testInvalidKeyThrows();
    }

    private static void testValidKeyWithNoExtensions() {
        java.util.Locale locale = new java.util.Locale("en", "US");
        // No extensions, expect null
        String result = locale.getUnicodeLocaleType("ca");
        if (result != null) {
            throw new RuntimeException("Expected null for key 'ca' with no extensions, got: " + result);
        }
    }

    private static void testValidKeyWithExtensions() {
        // Construct a Locale with a Unicode locale extension
        // Using the Locale.Builder to add Unicode locale attributes
        java.util.Locale.Builder builder = new java.util.Locale.Builder();
        builder.setLanguage("en");
        builder.setRegion("US");
        builder.setUnicodeLocaleKeyword("ca", "buddhist");
        java.util.Locale locale = builder.build();

        String result = locale.getUnicodeLocaleType("ca");
        if (!"buddhist".equals(result)) {
            throw new RuntimeException("Expected 'buddhist' for key 'ca', got: " + result);
        }

        // Key that is valid but not present should return null
        String missing = locale.getUnicodeLocaleType("nu");
        if (missing != null) {
            throw new RuntimeException("Expected null for missing key 'nu', got: " + missing);
        }
    }

    private static void testInvalidKeyThrows() {
        java.util.Locale locale = new java.util.Locale("en", "US");
        boolean thrown = false;
        try {
            locale.getUnicodeLocaleType("invalid_key!");
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException for ill-formed key");
        }
    }
}
