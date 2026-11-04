/*
 * @test
 * @summary Verify NumberFormatProviderImpl.getCurrencyInstance rejects null locale and returns a currency format for a valid locale
 * @modules java.base/sun.util.locale.provider
 * @run main NumberFormatProviderImplWitupTest
 */

import java.text.NumberFormat;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import sun.util.locale.provider.LocaleProviderAdapter;
import sun.util.locale.provider.NumberFormatProviderImpl;

public class NumberFormatProviderImplWitupTest {

    public static void main(String[] args) {
        Set<String> langtags = new HashSet<>();
        NumberFormatProviderImpl provider =
                new NumberFormatProviderImpl(LocaleProviderAdapter.Type.JRE, langtags);

        testNullLocaleThrowsNPE(provider);
        testValidLocaleReturnsFormat(provider);
    }

    private static void testNullLocaleThrowsNPE(NumberFormatProviderImpl provider) {
        boolean thrown = false;
        try {
            provider.getCurrencyInstance(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null locale");
        }
    }

    private static void testValidLocaleReturnsFormat(NumberFormatProviderImpl provider) {
        NumberFormat nf = provider.getCurrencyInstance(Locale.US);
        if (nf == null) {
            throw new RuntimeException("getCurrencyInstance(Locale.US) returned null");
        }
    }
}
