/*
 * @test
 * @summary Test getDisplayCountry(Locale) method of java.util.Locale
 * @run main LocaleWitupTest
 */

public class LocaleWitupTest {
    public static void main(String[] args) {
        // Test 1: Basic test with Locale.US and Locale.FRANCE
        java.util.Locale us = java.util.Locale.US;
        java.util.Locale france = java.util.Locale.FRANCE;
        String displayCountryInFrench = us.getDisplayCountry(france);
        if (displayCountryInFrench == null || displayCountryInFrench.isEmpty()) {
            throw new RuntimeException("getDisplayCountry returned null or empty string for US locale in French");
        }

        // Test 2: Test with Locale with empty region
        java.util.Locale localeWithNoRegion = new java.util.Locale("en", "", "");
        String displayCountryEmptyRegion = localeWithNoRegion.getDisplayCountry(java.util.Locale.ENGLISH);
        if (displayCountryEmptyRegion == null) {
            throw new RuntimeException("getDisplayCountry returned null for locale with empty region");
        }

        // Test 3: Test with null argument (should behave as getDisplayCountry(null))
        // According to Locale API, null means default locale
        String displayCountryNull = us.getDisplayCountry(null);
        if (displayCountryNull == null || displayCountryNull.isEmpty()) {
            throw new RuntimeException("getDisplayCountry returned null or empty string for US locale with null argument");
        }

        // Test 4: Test with same locale as argument
        String displayCountrySameLocale = us.getDisplayCountry(us);
        if (displayCountrySameLocale == null || displayCountrySameLocale.isEmpty()) {
            throw new RuntimeException("getDisplayCountry returned null or empty string for US locale with itself as argument");
        }

        // Test 5: Test with a locale with unknown region code
        java.util.Locale unknownRegionLocale = new java.util.Locale("en", "ZZ");
        String displayCountryUnknown = unknownRegionLocale.getDisplayCountry(java.util.Locale.ENGLISH);
        if (displayCountryUnknown == null) {
            throw new RuntimeException("getDisplayCountry returned null for locale with unknown region code");
        }

        System.out.println("All tests passed.");
    }
}
