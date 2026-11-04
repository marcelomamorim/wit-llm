/*
 * @test
 * @summary Test java.util.Locale.setDefault(Locale) method behavior
 * @run main java.util.LocaleWitupTest
 */

public class LocaleWitupTest {
    public static void main(String[] args) {
        testSetDefaultWithValidLocale();
        testSetDefaultWithNullLocale();
    }

    private static void testSetDefaultWithValidLocale() {
        java.util.Locale originalDefault = java.util.Locale.getDefault();
        java.util.Locale testLocale = new java.util.Locale("fr", "FR");
        java.util.Locale.setDefault(testLocale);
        java.util.Locale currentDefault = java.util.Locale.getDefault();
        if (!currentDefault.equals(testLocale)) {
            throw new RuntimeException("setDefault did not set the default locale correctly");
        }
        // Restore original default
        java.util.Locale.setDefault(originalDefault);
    }

    private static void testSetDefaultWithNullLocale() {
        java.util.Locale originalDefault = java.util.Locale.getDefault();
        try {
            java.util.Locale.setDefault(null);
            java.util.Locale currentDefault = java.util.Locale.getDefault();
            if (currentDefault != null) {
                throw new RuntimeException("setDefault(null) did not set defaultLocale to null as expected");
            }
        } finally {
            // Restore original default
            java.util.Locale.setDefault(originalDefault);
        }
    }
}
