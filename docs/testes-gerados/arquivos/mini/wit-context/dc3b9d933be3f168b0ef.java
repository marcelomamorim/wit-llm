/*
 * @test
 * @summary Test NumberFormatProviderImpl.getNumberInstance(Locale) for null and valid locales
 * @run main NumberFormatProviderImplWitupTest
 */

import sun.util.locale.provider.NumberFormatProviderImpl;
import sun.util.locale.provider.LocaleProviderAdapter;
import java.text.NumberFormat;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class NumberFormatProviderImplWitupTest {
    public static void main(String[] args) {
        // Prepare a dummy langtags set with a known locale tag
        Set<String> langtags = new HashSet<>();
        langtags.add("en");

        // Use the JRE type adapter for testing
        NumberFormatProviderImpl provider = new NumberFormatProviderImpl(LocaleProviderAdapter.Type.JRE, langtags);

        // Test 1: Passing null locale should throw NullPointerException
        try {
            provider.getNumberInstance(null);
            throw new RuntimeException("Expected NullPointerException for null locale");
        } catch (NullPointerException expected) {
            // Expected exception
        }

        // Test 2: Passing a supported locale should return a non-null NumberFormat
        Locale testLocale = Locale.ENGLISH;
        if (!provider.isSupportedLocale(testLocale)) {
            throw new RuntimeException("Test locale not supported by provider: " + testLocale);
        }
        NumberFormat nf = provider.getNumberInstance(testLocale);
        if (nf == null) {
            throw new RuntimeException("getNumberInstance returned null for supported locale");
        }

        // Test 3: Passing an unsupported locale should throw IllegalArgumentException
        Locale unsupportedLocale = new Locale("xx", "YY");
        if (provider.isSupportedLocale(unsupportedLocale)) {
            throw new RuntimeException("Unsupported locale unexpectedly supported: " + unsupportedLocale);
        }
        try {
            provider.getNumberInstance(unsupportedLocale);
            throw new RuntimeException("Expected IllegalArgumentException for unsupported locale");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }

        System.out.println("All tests passed.");
    }
}
