/*
 * @test
 * @summary NumberFormat.setRoundingMode throws UnsupportedOperationException in the base class and is honored by DecimalFormat
 * @run main NumberFormatWitupTest
 */

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Locale;

public class NumberFormatWitupTest {

    static class StubFormat extends NumberFormat {
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
        checkBaseThrows(RoundingMode.HALF_UP);
        checkBaseThrows(RoundingMode.UNNECESSARY);
        checkBaseThrows(null);
        checkDecimalFormatHonorsRoundingMode();
    }

    static void checkBaseThrows(RoundingMode mode) {
        NumberFormat nf = new StubFormat();
        boolean thrown = false;
        try {
            nf.setRoundingMode(mode);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException for mode " + mode);
        }
    }

    static void checkDecimalFormatHonorsRoundingMode() {
        NumberFormat nf = NumberFormat.getInstance(Locale.US);
        if (!(nf instanceof DecimalFormat)) {
            throw new RuntimeException("Expected DecimalFormat for Locale.US");
        }
        nf.setMaximumFractionDigits(0);
        nf.setRoundingMode(RoundingMode.HALF_UP);
        String up = nf.format(2.5);
        nf.setRoundingMode(RoundingMode.HALF_EVEN);
        String even = nf.format(2.5);
        if (!"3".equals(up)) {
            throw new RuntimeException("HALF_UP expected \"3\" but got " + up);
        }
        if (!"2".equals(even)) {
            throw new RuntimeException("HALF_EVEN expected \"2\" but got " + even);
        }
    }
}
