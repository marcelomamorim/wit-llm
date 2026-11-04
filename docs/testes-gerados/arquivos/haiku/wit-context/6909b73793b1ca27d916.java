/*
 * @test
 * @summary Verifies that NumberFormat.setCurrency throws UnsupportedOperationException in the base implementation
 * @run main NumberFormatWitupTest
 */

import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Currency;

public class NumberFormatWitupTest {

    static class PlainNumberFormat extends NumberFormat {
        private static final long serialVersionUID = 1L;

        PlainNumberFormat() {
            super();
        }

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
            parsePosition.setIndex(source.length());
            return Long.valueOf(source);
        }
    }

    static void checkThrowsUnsupported(NumberFormat nf, Currency c) {
        try {
            nf.setCurrency(c);
            throw new RuntimeException("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }

    public static void main(String[] args) {
        NumberFormat nf = new PlainNumberFormat();
        checkThrowsUnsupported(nf, Currency.getInstance("USD"));
        checkThrowsUnsupported(nf, Currency.getInstance("EUR"));
        checkThrowsUnsupported(nf, null);
    }
}
