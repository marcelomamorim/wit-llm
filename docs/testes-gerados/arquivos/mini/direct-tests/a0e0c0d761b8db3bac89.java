/*
 * @test
 * @summary Test getDisplayCountry method of LocaleNameProviderImpl
 * @run main LocaleNameProviderImplWitupTest
 */

import java.util.Locale;
import java.util.Set;
import sun.util.locale.provider.LocaleNameProviderImpl;
import sun.util.locale.provider.LocaleProviderAdapter;

public class LocaleNameProviderImplWitupTest {
    public static void main(String[] args) {
        // Setup: create a LocaleNameProviderImpl with a known type and langtags
        LocaleProviderAdapter.Type type = LocaleProviderAdapter.Type.JRE;
        Set<String> langtags = Set.of("en", "fr", "de", "ja");
        LocaleNameProviderImpl provider = new LocaleNameProviderImpl(type, langtags);

        // Test 1: getDisplayCountry with valid country code and supported locale
        String countryCode = "US";
        Locale locale = Locale.ENGLISH;
        String result = provider.getDisplayCountry(countryCode, locale);
        // Result may be null if no mapping, but must not throw
        if (result == null) {
            System.out.println("Test 1: getDisplayCountry returned null for US/EN (acceptable)");
        } else {
            System.out.println("Test 1: getDisplayCountry returned: " + result);
        }

        // Test 2: getDisplayCountry with valid country code and unsupported locale
        Locale unsupportedLocale = new Locale("xx", "YY");
        try {
            String res2 = provider.getDisplayCountry(countryCode, unsupportedLocale);
            // According to contract, should throw IllegalArgumentException or return null
            if (res2 == null) {
                System.out.println("Test 2: getDisplayCountry returned null for unsupported locale (acceptable)");
            } else {
                System.out.println("Test 2: getDisplayCountry returned: " + res2);
            }
        } catch (IllegalArgumentException iae) {
            System.out.println("Test 2: getDisplayCountry threw IllegalArgumentException for unsupported locale (expected)");
        }

        // Test 3: getDisplayCountry with null country code (should throw NullPointerException)
        try {
            provider.getDisplayCountry(null, locale);
            throw new RuntimeException("Test 3 failed: expected NullPointerException for null country code");
        } catch (NullPointerException npe) {
            System.out.println("Test 3: NullPointerException thrown as expected for null country code");
        }

        // Test 4: getDisplayCountry with null locale (should throw NullPointerException)
        try {
            provider.getDisplayCountry(countryCode, null);
            throw new RuntimeException("Test 4 failed: expected NullPointerException for null locale");
        } catch (NullPointerException npe) {
            System.out.println("Test 4: NullPointerException thrown as expected for null locale");
        }

        // Test 5: getDisplayCountry with invalid country code format (e.g. lowercase or length != 2)
        String invalidCountryCode = "usa"; // length 3
        try {
            provider.getDisplayCountry(invalidCountryCode, locale);
            throw new RuntimeException("Test 5 failed: expected IllegalArgumentException for invalid country code");
        } catch (IllegalArgumentException iae) {
            System.out.println("Test 5: IllegalArgumentException thrown as expected for invalid country code");
        }

        // Test 6: getDisplayCountry with empty string country code
        try {
            provider.getDisplayCountry("", locale);
            throw new RuntimeException("Test 6 failed: expected IllegalArgumentException for empty country code");
        } catch (IllegalArgumentException iae) {
            System.out.println("Test 6: IllegalArgumentException thrown as expected for empty country code");
        }
    }
}
