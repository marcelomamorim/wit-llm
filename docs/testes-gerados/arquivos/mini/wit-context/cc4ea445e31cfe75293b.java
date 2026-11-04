/*
 * @test
 * @summary Test getDisplayVariant(Locale) method of java.util.Locale
 * @run main LocaleWitupTest
 */

public class LocaleWitupTest {
    public static void main(String[] args) {
        testGetDisplayVariantEmptyVariant();
        testGetDisplayVariantNonEmptyVariant();
    }

    private static void testGetDisplayVariantEmptyVariant() {
        // Locale with empty variant should return empty string
        java.util.Locale locale = new java.util.Locale("en", "US", "");
        String result = locale.getDisplayVariant(java.util.Locale.ENGLISH);
        if (!"".equals(result)) {
            throw new AssertionError("Expected empty string for empty variant, got: " + result);
        }
    }

    private static void testGetDisplayVariantNonEmptyVariant() {
        // Locale with non-empty variant should return a non-null string
        java.util.Locale locale = new java.util.Locale("en", "US", "POSIX");
        String result = locale.getDisplayVariant(java.util.Locale.ENGLISH);
        if (result == null) {
            throw new AssertionError("Expected non-null display variant string");
        }
        // The returned string may be empty or localized, but must not be null
    }
}
