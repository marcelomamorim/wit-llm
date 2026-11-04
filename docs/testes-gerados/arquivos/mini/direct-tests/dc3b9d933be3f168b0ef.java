/*
 * @test
 * @summary Test getNumberInstance(Locale) method of NumberFormatProviderImpl
 * @run main NumberFormatProviderImplWitupTest
 */

import java.text.NumberFormat;
import java.util.Locale;
import java.util.HashSet;
import sun.util.locale.provider.NumberFormatProviderImpl;
import sun.util.locale.provider.LocaleProviderAdapter;

public class NumberFormatProviderImplWitupTest {

    public static void main(String[] args) throws Exception {
        // Prepare a dummy type and langtags for construction
        LocaleProviderAdapter.Type dummyType = LocaleProviderAdapter.Type.JRE;
        HashSet<String> langtags = new HashSet<>();
        langtags.add("en");

        NumberFormatProviderImpl provider = new NumberFormatProviderImpl(dummyType, langtags);

        // Test with a supported locale
        Locale locale = Locale.ENGLISH;
        if (!provider.isSupportedLocale(locale)) {
            throw new RuntimeException("Locale should be supported: " + locale);
        }
        NumberFormat nf = provider.getNumberInstance(locale);
        if (nf == null) {
            throw new RuntimeException("getNumberInstance returned null for supported locale");
        }

        // Test with a locale that is likely unsupported (use a made-up locale)
        Locale unsupportedLocale = new Locale("xx", "YY");
        boolean supported = provider.isSupportedLocale(unsupportedLocale);
        // If unsupported, getNumberInstance should throw IllegalArgumentException
        try {
            provider.getNumberInstance(unsupportedLocale);
            if (!supported) {
                throw new RuntimeException("Expected IllegalArgumentException for unsupported locale");
            }
        } catch (IllegalArgumentException e) {
            if (supported) {
                throw new RuntimeException("Unexpected IllegalArgumentException for supported locale", e);
            }
        }

        // Test with null locale - expect NullPointerException
        try {
            provider.getNumberInstance(null);
            throw new RuntimeException("Expected NullPointerException for null locale");
        } catch (NullPointerException e) {
            // expected
        }

        System.out.println("All tests passed.");
    }
}
