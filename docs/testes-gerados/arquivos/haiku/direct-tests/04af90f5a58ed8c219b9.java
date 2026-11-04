/*
 * @test
 * @summary Verifies observable behavior of Locale.getISO3Country() for known, empty and unknown country codes
 * @run main LocaleWitupTest
 */

import java.util.Locale;
import java.util.MissingResourceException;

public class LocaleWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testKnownCountries() {
        check(new Locale("en", "US").getISO3Country().equals("USA"), "en_US should map to USA");
        check(new Locale("de", "DE").getISO3Country().equals("DEU"), "de_DE should map to DEU");
        check(new Locale("ja", "JP").getISO3Country().equals("JPN"), "ja_JP should map to JPN");
    }

    static void testEmptyCountry() {
        check(new Locale("en").getISO3Country().equals(""), "Locale without country should return empty string");
    }

    static void testUnknownCountryThrows() {
        boolean thrown = false;
        try {
            new Locale("en", "ZZ").getISO3Country();
        } catch (MissingResourceException e) {
            thrown = true;
        }
        check(thrown, "Unknown country code should throw MissingResourceException");
    }

    public static void main(String[] args) {
        testKnownCountries();
        testEmptyCountry();
        testUnknownCountryThrows();
    }
}
