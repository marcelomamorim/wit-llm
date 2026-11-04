/*
 * @test
 * @summary Test java.util.Locale.getISOCountries() returns a non-null copy of ISO country codes
 * @run main LocaleWitupTest
 */

public class LocaleWitupTest {
    public static void main(String[] args) {
        String[] countries1 = java.util.Locale.getISOCountries();
        if (countries1 == null) {
            throw new RuntimeException("getISOCountries() returned null");
        }
        if (countries1.length == 0) {
            throw new RuntimeException("getISOCountries() returned empty array");
        }
        // Check that all entries are non-null and length 2 (ISO 3166 alpha-2 codes)
        for (int i = 0; i < countries1.length; i++) {
            String c = countries1[i];
            if (c == null) {
                throw new RuntimeException("Null country code at index " + i);
            }
            if (c.length() != 2) {
                throw new RuntimeException("Country code length not 2 at index " + i + ": " + c);
            }
            // Check uppercase letters
            char ch0 = c.charAt(0);
            char ch1 = c.charAt(1);
            if (!(ch0 >= 'A' && ch0 <= 'Z') || !(ch1 >= 'A' && ch1 <= 'Z')) {
                throw new RuntimeException("Country code not uppercase letters at index " + i + ": " + c);
            }
        }
        // Check that returned array is a copy (modifying it does not affect subsequent calls)
        String[] countries2 = java.util.Locale.getISOCountries();
        if (countries2.length != countries1.length) {
            throw new RuntimeException("Subsequent getISOCountries() call returned different length");
        }
        countries1[0] = "XX";
        String[] countries3 = java.util.Locale.getISOCountries();
        if ("XX".equals(countries3[0])) {
            throw new RuntimeException("Modifying returned array affected internal state");
        }
    }
}
