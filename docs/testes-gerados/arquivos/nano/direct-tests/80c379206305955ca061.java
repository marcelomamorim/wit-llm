/*
 * @test
 * @summary Test for Locale.getDisplayCountry(Locale inLocale)
 * @run main LocaleWitupTest
 */

import java.util.Locale;

public class LocaleWitupTest {
    public static void main(String[] args) {
        testGetDisplayCountrySameLocale();
        testGetDisplayCountryDifferentLocale();
        testGetDisplayCountryNullLocale();
    }

    static void testGetDisplayCountrySameLocale() {
        Locale locale = new Locale("en", "US");
        String displayCountry = locale.getDisplayCountry(locale);
        System.out.println("Display country (same locale): " + displayCountry);
        // Expect "United States" or localized equivalent
        if (displayCountry == null || displayCountry.isEmpty()) {
            throw new AssertionError("Expected non-empty display country");
        }
    }

    static void testGetDisplayCountryDifferentLocale() {
        Locale locale = new Locale("en", "US");
        Locale inLocale = new Locale("fr", "FR");
        String displayCountry = locale.getDisplayCountry(inLocale);
        System.out.println("Display country (French locale): " + displayCountry);
        // Expect "États-Unis" or localized equivalent
        if (displayCountry == null || displayCountry.isEmpty()) {
            throw new AssertionError("Expected non-empty display country in French locale");
        }
    }

    static void testGetDisplayCountryNullLocale() {
        Locale locale = new Locale("en", "US");
        String displayCountry = locale.getDisplayCountry(null);
        System.out.println("Display country (null locale): " + displayCountry);
        // Expect default display, e.g., "United States"
        if (displayCountry == null || displayCountry.isEmpty()) {
            throw new AssertionError("Expected non-empty display country with null locale");
        }
    }
}
