/*
 * @test
 * @summary Test java.util.Locale.getISO3Country() method behavior
 * @run main java.util.LocaleWitupTest
 */

public class LocaleWitupTest {
    public static void main(String[] args) {
        testValidCountry();
        testEmptyCountry();
        testUnknownCountry();
    }

    private static void testValidCountry() {
        java.util.Locale locale = new java.util.Locale("en", "US");
        String iso3 = locale.getISO3Country();
        if (!"USA".equals(iso3)) {
            throw new RuntimeException("Expected USA but got: " + iso3);
        }
    }

    private static void testEmptyCountry() {
        java.util.Locale locale = new java.util.Locale("en", "");
        try {
            locale.getISO3Country();
            throw new RuntimeException("Expected MissingResourceException for empty country");
        } catch (java.util.MissingResourceException e) {
            // expected
        }
    }

    private static void testUnknownCountry() {
        java.util.Locale locale = new java.util.Locale("en", "ZZ");
        try {
            locale.getISO3Country();
            throw new RuntimeException("Expected MissingResourceException for unknown country code");
        } catch (java.util.MissingResourceException e) {
            // expected
        }
    }
}
