/*
 * @test
 * @summary Verifies public behavior of NumberFormatProviderImpl.getCurrencyInstance for a valid locale and null locale
 * @modules java.base/sun.util.locale.provider
 * @run main NumberFormatProviderImplWitupTest
 */

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.Locale;
import sun.util.locale.provider.LocaleProviderAdapter;
import sun.util.locale.provider.NumberFormatProviderImpl;

public class NumberFormatProviderImplWitupTest {

    public static void main(String[] args) {
        NumberFormatProviderImpl provider = new NumberFormatProviderImpl(
                LocaleProviderAdapter.Type.JRE, Collections.<String>emptySet());

        NumberFormat nf = provider.getCurrencyInstance(Locale.US);
        if (nf == null) {
            throw new RuntimeException("getCurrencyInstance returned null");
        }
        if (!(nf instanceof DecimalFormat)) {
            throw new RuntimeException("Expected DecimalFormat but got " + nf.getClass());
        }
        if (nf.getMinimumFractionDigits() != 2 || nf.getMaximumFractionDigits() != 2) {
            throw new RuntimeException("Unexpected currency fraction digits: "
                    + nf.getMinimumFractionDigits() + "/" + nf.getMaximumFractionDigits());
        }
        String formatted = nf.format(1.5);
        if (!formatted.contains("1.50")) {
            throw new RuntimeException("Unexpected currency format output: " + formatted);
        }

        boolean npe = false;
        try {
            provider.getCurrencyInstance(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        if (!npe) {
            throw new RuntimeException("Expected NullPointerException for null locale");
        }
    }
}
