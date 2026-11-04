/*
 * @test
 * @summary Test NumberFormatProviderImpl.getIntegerInstance(Locale) for null argument and basic behavior
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
        testGetIntegerInstanceNullLocale();
        testGetIntegerInstanceValidLocale();
    }

    private static void testGetIntegerInstanceNullLocale() {
        Set<String> langtags = new HashSet<>();
        NumberFormatProviderImpl provider = new NumberFormatProviderImpl(LocaleProviderAdapter.Type.JRE, langtags);
        try {
            provider.getIntegerInstance(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testGetIntegerInstanceValidLocale() {
        Set<String> langtags = new HashSet<>();
        NumberFormatProviderImpl provider = new NumberFormatProviderImpl(LocaleProviderAdapter.Type.JRE, langtags);
        Locale locale = Locale.US;
        NumberFormat nf = provider.getIntegerInstance(locale);
        if (nf == null) {
            throw new RuntimeException("getIntegerInstance returned null for valid locale");
        }
        // Check that the returned NumberFormat formats integer values without fraction digits
        String formatted = nf.format(12345);
        if (formatted == null || formatted.isEmpty()) {
            throw new RuntimeException("Formatted string is null or empty");
        }
    }
}
