/*
 * @test
 * @summary Test for Locale.getISOCountries()
 * @run main LocaleWitupTest
 */

import java.util.Locale;

public class LocaleWitupTest {
    public static void main(String[] args) {
        String[] countries = Locale.getISOCountries();
        // Basic check: array should not be null
        if (countries == null) {
            throw new AssertionError("getISOCountries() returned null");
        }
        // Array should have at least one element
        if (countries.length == 0) {
            throw new AssertionError("getISOCountries() returned empty array");
        }
        // Check that all elements are non-null and non-empty strings
        for (String country : countries) {
            if (country == null || country.isEmpty()) {
                throw new AssertionError("Invalid country code: '" + country + "'");
            }
        }
        // Optional: print first few country codes for manual verification
        System.out.println("First 5 ISO country codes:");
        for (int i = 0; i < Math.min(5, countries.length); i++) {
            System.out.println(countries[i]);
        }
    }
}
