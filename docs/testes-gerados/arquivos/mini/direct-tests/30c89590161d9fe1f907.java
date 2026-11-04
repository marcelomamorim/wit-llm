/*
 * @test
 * @summary Test getPercentInstance method of NumberFormatProviderImpl
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
        HashSet<String> langtags = new HashSet<String>();
        langtags.add("en");

        NumberFormatProviderImpl provider = new NumberFormatProviderImpl(dummyType, langtags);

        // Test with a supported locale
        Locale locale = Locale.ENGLISH;
        if (!provider.isSupportedLocale(locale)) {
            throw new RuntimeException("Locale should be supported: " + locale);
        }

        NumberFormat nf = provider.getPercentInstance(locale);
        if (nf == null) {
            throw new RuntimeException("getPercentInstance returned null");
        }

        // Check that the format style is percent by formatting a known value
        String formatted = nf.format(0.75);
        if (!formatted.contains("%")) {
            throw new RuntimeException("Formatted string does not contain percent sign: " + formatted);
        }

        // Test with a locale that is likely unsupported (e.g. a made-up locale)
        Locale unsupportedLocale = new Locale("xx", "YY");
        boolean supported = provider.isSupportedLocale(unsupportedLocale);
        if (supported) {
            // If supported, getPercentInstance should not throw but return a NumberFormat
            NumberFormat nf2 = provider.getPercentInstance(unsupportedLocale);
            if (nf2 == null) {
                throw new RuntimeException("getPercentInstance returned null for unsupported locale");
            }
        } else {
            // If not supported, getPercentInstance should throw IllegalArgumentException
            boolean threw = false;
            try {
                provider.getPercentInstance(unsupportedLocale);
            } catch (IllegalArgumentException e) {
                threw = true;
            }
            if (!threw) {
                throw new RuntimeException("Expected IllegalArgumentException for unsupported locale");
            }
        }

        System.out.println("Test passed.");
    }
}
