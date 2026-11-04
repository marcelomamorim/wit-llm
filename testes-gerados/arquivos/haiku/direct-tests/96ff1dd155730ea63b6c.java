/*
 * @test
 * @summary NumberFormat.getRoundingMode() throws UnsupportedOperationException in the base class and is implemented by the default DecimalFormat
 * @run main NumberFormatWitupTest
 */

import java.math.RoundingMode;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Locale;

public class NumberFormatWitupTest {

    static class MinimalFormat extends NumberFormat {
        @Override
        public StringBuffer format(double number, StringBuffer toAppendTo, FieldPosition pos) {
            return toAppendTo.append(number);
        }

        @Override
        public StringBuffer format(long number, StringBuffer toAppendTo, FieldPosition pos) {
            return toAppendTo.append(number);
        }

        @Override
        public Number parse(String source, ParsePosition parsePosition) {
            return null;
        }
    }

    public static void main(String[] args) {
        NumberFormat nf = new MinimalFormat();
        try {
            nf.getRoundingMode();
            throw new AssertionError("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected: base implementation does not support rounding mode
        }

        NumberFormat def = NumberFormat.getInstance(Locale.US);
        RoundingMode mode = def.getRoundingMode();
        if (mode != RoundingMode.HALF_EVEN) {
            throw new AssertionError("Unexpected default rounding mode: " + mode);
        }
    }
}
