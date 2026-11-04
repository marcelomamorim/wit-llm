/*
 * @test
 * @summary Verifies NumberFormatProviderImpl.getIntegerInstance returns integer-style formats
 * @modules java.base/sun.util.locale.provider
 * @run main NumberFormatProviderImplWitupTest
 */

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.Locale;
import java.util.Set;
import sun.util.locale.provider.LocaleProviderAdapter;
import sun.util.locale.provider.NumberFormatProviderImpl;

public class NumberFormatProviderImplWitupTest {

    public static void main(String[] args) {
        Set<String> tags = Collections.emptySet();
        NumberFormatProviderImpl provider =
                new NumberFormatProviderImpl(LocaleProviderAdapter.Type.JRE, tags);

        NumberFormat nf = provider.getIntegerInstance(Locale.US);
        if (nf == null) {
            throw new RuntimeException("getIntegerInstance returned null");
        }

        // Integer style: no fraction digits, rounding half-even
        check("12", nf.format(12.0));
        check("13", nf.format(12.7));
        check("12", nf.format(12.5));

        // Integer style parses integers only
        if (nf.parse("12.7").longValue() != 12L) {
            throw new RuntimeException("parse of 12.7 should yield 12, got "
                    + nf.parse("12.7"));
        }

        if (nf instanceof DecimalFormat) {
            DecimalFormat df = (DecimalFormat) nf;
            if (df.getMaximumFractionDigits() != 0) {
                throw new RuntimeException("max fraction digits should be 0, got "
                        + df.getMaximumFractionDigits());
            }
        }

        // Each call returns an independent instance
        NumberFormat other = provider.getIntegerInstance(Locale.US);
        if (other == nf) {
            throw new RuntimeException("expected a new instance on each call");
        }
        nf.setMaximumFractionDigits(3);
        nf.setParseIntegerOnly(false);
        check("13", other.format(12.7));
    }

    private static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("expected " + expected + " but got " + actual);
        }
    }
}
