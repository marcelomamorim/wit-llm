/*
 * @test
 * @summary Test java.time.format.DecimalStyle.of(Locale) method
 * @run main DecimalStyleWitupTest
 */

import java.time.format.DecimalStyle;
import java.util.Locale;
import java.util.Objects;

public class DecimalStyleWitupTest {
    public static void main(String[] args) {
        testOfWithNullLocale();
        testOfWithKnownLocale();
        testOfWithRepeatedCallsReturnsSameInstance();
    }

    private static void testOfWithNullLocale() {
        try {
            DecimalStyle.of(null);
            throw new AssertionError("Expected NullPointerException for null locale");
        } catch (NullPointerException ex) {
            // expected
            if (!"locale".equals(ex.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch", ex);
            }
        }
    }

    private static void testOfWithKnownLocale() {
        Locale locale = Locale.US;
        DecimalStyle ds = DecimalStyle.of(locale);
        if (ds == null) {
            throw new AssertionError("DecimalStyle.of returned null for Locale.US");
        }
        // The returned DecimalStyle should have zeroDigit '0' for Locale.US
        char zeroDigit = ds.getZeroDigit();
        if (zeroDigit != '0') {
            throw new AssertionError("Expected zeroDigit '0' for Locale.US but got '" + zeroDigit + "'");
        }
    }

    private static void testOfWithRepeatedCallsReturnsSameInstance() {
        Locale locale = Locale.FRANCE;
        DecimalStyle first = DecimalStyle.of(locale);
        DecimalStyle second = DecimalStyle.of(locale);
        if (first == null || second == null) {
            throw new AssertionError("DecimalStyle.of returned null");
        }
        if (first != second) {
            throw new AssertionError("DecimalStyle.of did not return cached instance for repeated calls with same locale");
        }
    }
}
