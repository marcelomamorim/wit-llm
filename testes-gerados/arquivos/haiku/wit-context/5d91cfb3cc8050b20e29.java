/*
 * @test
 * @summary Verify that the base NumberFormat.setRoundingMode(RoundingMode) throws UnsupportedOperationException for any argument
 * @run main NumberFormatWitupTest
 */
import java.math.RoundingMode;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;

public class NumberFormatWitupTest {

    static class MinimalFormat extends NumberFormat {
        @Override
        public StringBuffer format(double number, StringBuffer toAppendTo, FieldPosition pos) {
            return toAppendTo;
        }

        @Override
        public StringBuffer format(long number, StringBuffer toAppendTo, FieldPosition pos) {
            return toAppendTo;
        }

        @Override
        public Number parse(String source, ParsePosition parsePosition) {
            return null;
        }
    }

    static void expectUnsupported(NumberFormat nf, RoundingMode mode) {
        boolean thrown = false;
        try {
            nf.setRoundingMode(mode);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException for mode: " + mode);
        }
    }

    public static void main(String[] args) {
        NumberFormat nf = new MinimalFormat();
        for (RoundingMode mode : RoundingMode.values()) {
            expectUnsupported(nf, mode);
        }
        expectUnsupported(nf, null);
    }
}
