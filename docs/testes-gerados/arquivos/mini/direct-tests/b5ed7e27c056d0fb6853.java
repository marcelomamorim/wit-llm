/*
 * @test
 * @summary Test java.util.Locale.getISOCountries(IsoCountryCode) method
 * @run main java.util.LocaleWitupTest
 */

import java.util.Locale;
import java.util.Set;
import java.util.Objects;

public class LocaleWitupTest {
    public static void main(String[] args) {
        testGetISOCountriesWithNull();
        testGetISOCountriesWithAlpha2();
        testGetISOCountriesWithAlpha3();
    }

    private static void testGetISOCountriesWithNull() {
        try {
            Locale.getISOCountries(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testGetISOCountriesWithAlpha2() {
        Set<String> countries = Locale.getISOCountries(Locale.IsoCountryCode.TWO_LETTER);
        if (countries == null || countries.isEmpty()) {
            throw new RuntimeException("getISOCountries(TWO_LETTER) returned null or empty set");
        }
        for (String code : countries) {
            if (code == null || code.length() != 2) {
                throw new RuntimeException("Invalid country code in TWO_LETTER set: " + code);
            }
        }
    }

    private static void testGetISOCountriesWithAlpha3() {
        Set<String> countries = Locale.getISOCountries(Locale.IsoCountryCode.THREE_LETTER);
        if (countries == null || countries.isEmpty()) {
            throw new RuntimeException("getISOCountries(THREE_LETTER) returned null or empty set");
        }
        for (String code : countries) {
            if (code == null || code.length() != 3) {
                throw new RuntimeException("Invalid country code in THREE_LETTER set: " + code);
            }
        }
    }
}
