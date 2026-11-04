/*
 * @test
 * @summary Test getIntegerInstance method of NumberFormatProviderImpl
 * @run main NumberFormatProviderImplWitupTest
 */

import java.text.NumberFormat;
import java.util.HashSet;
import java.util.Locale;
import sun.util.locale.provider.NumberFormatProviderImpl;
import sun.util.locale.provider.LocaleProviderAdapter;

public class NumberFormatProviderImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Prepare a set of langtags with a known locale
        HashSet<String> langtags = new HashSet<>();
        langtags.add("en-US");

        // Use the JRE adapter type
        LocaleProviderAdapter.Type type = LocaleProviderAdapter.Type.JRE;

        NumberFormatProviderImpl provider = new NumberFormatProviderImpl(type, langtags);

        // Test with a supported locale
        Locale locale = Locale.US;
        if (!provider.isSupportedLocale(locale)) {
            throw new RuntimeException("Locale should be supported: " + locale);
        }

        NumberFormat nf = provider.getIntegerInstance(locale);
        if (nf == null) {
            throw new RuntimeException("getIntegerInstance returned null");
        }

        // Check that the NumberFormat formats integer values correctly
        String formatted = nf.format(12345);
        if (formatted == null || formatted.isEmpty()) {
            throw new RuntimeException("Formatted string is null or empty");
        }

        // Test with a locale that is not supported (empty langtags)
        NumberFormatProviderImpl providerEmpty = new NumberFormatProviderImpl(type, new HashSet<String>());
        Locale unsupportedLocale = new Locale("xx", "YY");
        if (providerEmpty.isSupportedLocale(unsupportedLocale)) {
            throw new RuntimeException("Locale should not be supported: " + unsupportedLocale);
        }

        // According to the contract, getIntegerInstance should throw IllegalArgumentException if locale not supported
        boolean threwIAE = false;
        try {
            providerEmpty.getIntegerInstance(unsupportedLocale);
        } catch (IllegalArgumentException e) {
            threwIAE = true;
        }
        if (!threwIAE) {
            throw new RuntimeException("Expected IllegalArgumentException for unsupported locale");
        }

        // Test that passing null locale throws NullPointerException
        boolean threwNPE = false;
        try {
            provider.getIntegerInstance(null);
        } catch (NullPointerException e) {
            threwNPE = true;
        }
        if (!threwNPE) {
            throw new RuntimeException("Expected NullPointerException for null locale");
        }
    }
}
