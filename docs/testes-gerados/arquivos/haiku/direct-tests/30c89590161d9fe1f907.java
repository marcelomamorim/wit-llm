/*
 * @test
 * @summary Verifies NumberFormatProviderImpl.getPercentInstance returns a percent NumberFormat and rejects null locales
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
        NumberFormatProviderImpl provider =
            new NumberFormatProviderImpl(LocaleProviderAdapter.Type.JRE, Collections.emptySet());

        NumberFormat percent = provider.getPercentInstance(Locale.US);
        if (percent == null) {
            throw new RuntimeException("getPercentInstance returned null");
        }

        String half = percent.format(0.5);
        if (!"50%".equals(half)) {
            throw new RuntimeException("Unexpected percent format for 0.5: " + half);
        }

        NumberFormat reference = NumberFormat.getPercentInstance(Locale.US);
        String provided = percent.format(0.25);
        String expected = reference.format(0.25);
        if (!provided.equals(expected)) {
            throw new RuntimeException("Provider output " + provided + " differs from " + expected);
        }

        try {
            provider.getPercentInstance(null);
            throw new RuntimeException("Expected NullPointerException for null locale");
        } catch (NullPointerException expectedException) {
            // expected per the method's javadoc contract
        }
    }
}
