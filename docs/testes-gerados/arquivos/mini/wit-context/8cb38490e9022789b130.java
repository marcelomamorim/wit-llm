/*
 * @test
 * @summary Test java.util.Locale.getISOCountries() for correctness and regression
 * @run main LocaleWitupTest
 */

public class LocaleWitupTest {
    public static void main(String[] args) {
        // Call getISOCountries multiple times to check for consistency and no exceptions
        String[] countries1 = java.util.Locale.getISOCountries();
        String[] countries2 = java.util.Locale.getISOCountries();

        // Basic sanity checks
        if (countries1 == null) {
            throw new RuntimeException("getISOCountries() returned null");
        }
        if (countries1.length == 0) {
            throw new RuntimeException("getISOCountries() returned empty array");
        }

        // Check that the returned array is a copy, not the internal array
        if (countries1 == countries2) {
            throw new RuntimeException("getISOCountries() returned the same array instance on multiple calls");
        }

        // Check that all country codes are of length 2
        for (int i = 0; i < countries1.length; i++) {
            String c = countries1[i];
            if (c == null) {
                throw new RuntimeException("Country code at index " + i + " is null");
            }
            if (c.length() != 2) {
                throw new RuntimeException("Country code at index " + i + " does not have length 2: " + c);
            }
            // Check that country code is uppercase ASCII letters
            for (int j = 0; j < 2; j++) {
                char ch = c.charAt(j);
                if (ch < 'A' || ch > 'Z') {
                    throw new RuntimeException("Country code at index " + i + " contains invalid character: " + ch);
                }
            }
        }

        // If reached here, test passed
        System.out.println("Locale.getISOCountries() test passed.");
    }
}
