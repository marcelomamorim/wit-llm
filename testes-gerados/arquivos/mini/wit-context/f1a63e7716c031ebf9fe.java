/*
 * @test
 * @summary Test NumberFormatProviderImpl.getCurrencyInstance(Locale) for null and valid locale
 * @run main NumberFormatProviderImplWitupTest
 */

import sun.util.locale.provider.NumberFormatProviderImpl;
import sun.util.locale.provider.LocaleProviderAdapter;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.HashSet;
import java.util.Set;

public class NumberFormatProviderImplWitupTest {
    public static void main(String[] args) {
        // Prepare a dummy langtags set
        Set<String> langtags = new HashSet<>();
        langtags.add("en");

        // Use a known LocaleProviderAdapter.Type to construct the provider
        NumberFormatProviderImpl provider = new NumberFormatProviderImpl(LocaleProviderAdapter.Type.JRE, langtags);

        // Test 1: null locale should throw NullPointerException
        try {
            provider.getCurrencyInstance(null);
            throw new RuntimeException("Expected NullPointerException for null locale");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test 2: valid locale should return a non-null NumberFormat instance
        Locale locale = Locale.US;
        NumberFormat nf = provider.getCurrencyInstance(locale);
        if (nf == null) {
            throw new RuntimeException("getCurrencyInstance returned null for valid locale");
        }

        // Test 3: returned NumberFormat should format currency correctly for a known value
        double testValue = 1234.56;
        String formatted = nf.format(testValue);
        if (formatted == null || formatted.isEmpty()) {
            throw new RuntimeException("Formatted currency string is null or empty");
        }

        // Test 4: locale not supported should throw IllegalArgumentException or fallback
        // Since isSupportedLocale depends on langtags, test with unsupported locale
        Locale unsupportedLocale = new Locale("xx", "YY");
        boolean supported = provider.isSupportedLocale(unsupportedLocale);
        if (supported) {
            // If supported, getCurrencyInstance should succeed
            NumberFormat nf2 = provider.getCurrencyInstance(unsupportedLocale);
            if (nf2 == null) {
                throw new RuntimeException("getCurrencyInstance returned null for supported locale");
            }
        } else {
            try {
                provider.getCurrencyInstance(unsupportedLocale);
                throw new RuntimeException("Expected IllegalArgumentException for unsupported locale");
            } catch (IllegalArgumentException expected) {
                // expected
            }
        }
    }
}
