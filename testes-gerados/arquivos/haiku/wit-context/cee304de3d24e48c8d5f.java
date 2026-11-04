/*
 * @test
 * @summary WITUP-derived checks for NumberFormatProviderImpl.getIntegerInstance: integer style result and NullPointerException on null locale
 * @modules java.base/sun.util.locale.provider
 * @run main NumberFormatProviderImplWitupTest
 */

import java.text.NumberFormat;
import java.util.Collections;
import java.util.Locale;
import sun.util.locale.provider.LocaleProviderAdapter;
import sun.util.locale.provider.NumberFormatProviderImpl;

public class NumberFormatProviderImplWitupTest {

    public static void main(String[] args) {
        NumberFormatProviderImpl provider = new NumberFormatProviderImpl(
                LocaleProviderAdapter.Type.JRE, Collections.<String>emptySet());

        testIntegerInstanceReturnsIntegerStyle(provider);
        testNullLocaleThrowsNullPointerException(provider);
    }

    private static void testIntegerInstanceReturnsIntegerStyle(NumberFormatProviderImpl provider) {
        NumberFormat nf = provider.getIntegerInstance(Locale.US);
        if (nf == null) {
            throw new RuntimeException("getIntegerInstance returned null");
        }
        if (nf.getMaximumFractionDigits() != 0) {
            throw new RuntimeException("Expected maximumFractionDigits 0 but was "
                    + nf.getMaximumFractionDigits());
        }
        String formatted = nf.format(1234.0);
        if (!"1,234".equals(formatted)) {
            throw new RuntimeException("Unexpected format result: " + formatted);
        }
    }

    private static void testNullLocaleThrowsNullPointerException(NumberFormatProviderImpl provider) {
        boolean thrown = false;
        try {
            provider.getIntegerInstance(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null locale");
        }
    }
}
