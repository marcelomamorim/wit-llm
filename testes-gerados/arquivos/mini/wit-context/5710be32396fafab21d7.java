/*
 * @test
 * @summary Test java.util.Locale.setDefault(Locale) for null argument and normal usage
 * @run main LocaleWitupTest
 */

public class LocaleWitupTest {
    public static void main(String[] args) {
        testSetDefaultWithValidLocale();
        testSetDefaultWithNullLocale();
    }

    private static void testSetDefaultWithValidLocale() {
        java.util.Locale loc = new java.util.Locale("en", "US");
        java.util.Locale.setDefault(loc);
        java.util.Locale def = java.util.Locale.getDefault();
        if (!def.equals(loc)) {
            throw new AssertionError("Default locale was not set correctly");
        }
    }

    private static void testSetDefaultWithNullLocale() {
        // According to current source, setDefault(Locale) calls setDefault(Category, Locale)
        // which throws NullPointerException if newLocale is null and Category.DISPLAY != null
        // So we expect NullPointerException here
        try {
            java.util.Locale.setDefault(null);
            throw new AssertionError("Expected NullPointerException when setting default locale to null");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
