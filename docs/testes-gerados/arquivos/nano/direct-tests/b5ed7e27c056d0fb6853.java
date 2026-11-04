/*
 * @test
 * @summary Test Locale.getISOCountries(IsoCountryCode type) for various inputs
 * @run main LocaleWitupTest
 */

import java.util.Locale;
import java.util.Set;

public class LocaleWitupTest {

    public static void main(String[] args) {
        testGetISOCountriesWithValidType();
        testGetISOCountriesWithNull();
    }

    static void testGetISOCountriesWithValidType() {
        try {
            Set<String> countries = Locale.getISOCountries(Locale.IsoCountryCode.alpha2);
            if (countries == null || countries.isEmpty()) {
                throw new AssertionError("Expected non-empty set for alpha2");
            }
            // Check for known country code
            if (!countries.contains("US")) {
                throw new AssertionError("Set should contain 'US'");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid type: " + e);
        }
    }

    static void testGetISOCountriesWithNull() {
        try {
            Locale.getISOCountries(null);
            throw new AssertionError("Expected NullPointerException for null type");
        } catch (NullPointerException e) {
            // Expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for null type: " + e);
        }
    }
}
