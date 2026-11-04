/*
 * @test
 * @summary Test getDisplayCountry(Locale) method of java.util.Locale for null argument and normal usage
 * @run main LocaleWitupTest
 */

import java.util.Locale;

public class LocaleWitupTest {
    public static void main(String[] args) {
        testGetDisplayCountryWithNull();
        testGetDisplayCountryWithValidLocale();
    }

    private static void testGetDisplayCountryWithNull() {
        Locale locale = new Locale("en", "US");
        try {
            locale.getDisplayCountry(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testGetDisplayCountryWithValidLocale() {
        Locale locale = new Locale("en", "US");
        Locale inLocale = new Locale("fr", "FR");
        String displayCountry = locale.getDisplayCountry(inLocale);
        if (displayCountry == null || displayCountry.isEmpty()) {
            throw new RuntimeException("getDisplayCountry returned null or empty string");
        }
        // Also test that the display country is different from the default locale display country if locales differ
        String defaultDisplayCountry = locale.getDisplayCountry(Locale.ENGLISH);
        if (inLocale.equals(Locale.ENGLISH)) {
            if (!displayCountry.equals(defaultDisplayCountry)) {
                throw new RuntimeException("Display country should be same for same locale");
            }
        } else {
            if (displayCountry.equals(defaultDisplayCountry)) {
                throw new RuntimeException("Display country should differ for different locale");
            }
        }
    }
}
