/*
 * @test
 * @summary Test for Locale.getISOCountries() method.
 * @run main LocaleWitupTest
 */

import java.util.Locale;

public class LocaleWitupTest {

    public static void main(String[] args) {
        testGetISOCountries();
    }

    public static void testGetISOCountries() {
        String[] countries = Locale.getISOCountries();
        assert countries != null : "getISOCountries() returned null";
        assert countries.length > 0 : "getISOCountries() returned empty array";
        for (String country : countries) {
            assert country != null : "Country code is null";
            assert country.length() == 2 : "Country code length is not 2: " + country;
        }
    }
}
