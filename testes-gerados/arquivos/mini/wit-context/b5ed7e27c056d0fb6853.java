/*
 * @test
 * @summary Test java.util.Locale.getISOCountries(IsoCountryCode) for null argument and normal behavior
 * @run main java.util.LocaleWitupTest
 */

import java.util.Locale;
import java.util.Locale.IsoCountryCode;
import java.util.Set;
import java.util.Objects;

public class LocaleWitupTest {
    public static void main(String[] args) {
        testGetISOCountriesNull();
        testGetISOCountriesValid();
    }

    private static void testGetISOCountriesNull() {
        try {
            Locale.getISOCountries(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testGetISOCountriesValid() {
        // Test with IsoCountryCode.TWO_LETTER
        Set<String> countries = Locale.getISOCountries(IsoCountryCode.TWO_LETTER);
        if (countries == null || countries.isEmpty()) {
            throw new RuntimeException("getISOCountries returned null or empty set for TWO_LETTER");
        }
        // Check that all country codes are length 2
        for (String c : countries) {
            if (c == null || c.length() != 2) {
                throw new RuntimeException("Invalid country code length: " + c);
            }
        }

        // Test with IsoCountryCode.THREE_LETTER
        Set<String> countries3 = Locale.getISOCountries(IsoCountryCode.THREE_LETTER);
        if (countries3 == null || countries3.isEmpty()) {
            throw new RuntimeException("getISOCountries returned null or empty set for THREE_LETTER");
        }
        // Check that all country codes are length 3
        for (String c : countries3) {
            if (c == null || c.length() != 3) {
                throw new RuntimeException("Invalid country code length: " + c);
            }
        }
    }
}
