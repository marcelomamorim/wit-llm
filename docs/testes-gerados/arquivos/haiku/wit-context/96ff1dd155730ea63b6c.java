/*
 * @test
 * @summary Verifies NumberFormat.getRoundingMode() throws UnsupportedOperationException in the base class and is overridden by concrete formats
 * @run main NumberFormatWitupTest
 */

import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.math.RoundingMode;
import java.util.Locale;

public class NumberFormatWitupTest {

    static class Minimal extends NumberFormat {
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

    static void checkBaseGetRoundingModeThrows() {
        NumberFormat nf = new Minimal();
        boolean thrown = false;
        try {
            nf.getRoundingMode();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("NumberFormat.getRoundingMode() did not throw UnsupportedOperationException");
        }
    }

    static void checkConcreteGetRoundingModeReturnsValue() {
        NumberFormat nf = NumberFormat.getInstance(Locale.US);
        RoundingMode mode = nf.getRoundingMode();
        if (mode == null) {
            throw new RuntimeException("getRoundingMode() returned null for a concrete format");
        }
    }

    public static void main(String[] args) {
        checkBaseGetRoundingModeThrows();
        checkConcreteGetRoundingModeReturnsValue();
    }
}
