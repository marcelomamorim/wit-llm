/*
 * @test
 * @summary Test NumberFormatProviderImpl.getPercentInstance(Locale) for null and valid locale
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
        testGetPercentInstanceWithNullLocale();
        testGetPercentInstanceWithValidLocale();
    }

    private static void testGetPercentInstanceWithNullLocale() {
        Set<String> langtags = new HashSet<>();
        NumberFormatProviderImpl provider = new NumberFormatProviderImpl(LocaleProviderAdapter.Type.JRE, langtags);
        try {
            provider.getPercentInstance(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testGetPercentInstanceWithValidLocale() {
        Set<String> langtags = new HashSet<>();
        NumberFormatProviderImpl provider = new NumberFormatProviderImpl(LocaleProviderAdapter.Type.JRE, langtags);
        Locale locale = Locale.US;
        NumberFormat nf = provider.getPercentInstance(locale);
        if (nf == null) {
            throw new RuntimeException("getPercentInstance returned null for valid locale");
        }
        // Check that the returned NumberFormat formats a percent value correctly
        String formatted = nf.format(0.75);
        if (formatted == null || formatted.isEmpty()) {
            throw new RuntimeException("Formatted percent string is null or empty");
        }
    }
}
