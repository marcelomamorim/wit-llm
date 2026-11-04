/*
 * @test
 * @summary NumberFormat.setCurrency throws UnsupportedOperationException in the base class and is overridden by concrete formats
 * @run main NumberFormatWitupTest
 */

import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Currency;
import java.util.Locale;

public class NumberFormatWitupTest {

    static class StubFormat extends NumberFormat {
        private static final long serialVersionUID = 1L;

        StubFormat() {
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
            return null;
        }
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) {
        StubFormat stub = new StubFormat();

        boolean thrown = false;
        try {
            stub.setCurrency(Currency.getInstance("USD"));
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        check(thrown, "base setCurrency(USD) must throw UnsupportedOperationException");

        thrown = false;
        try {
            stub.setCurrency(null);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        check(thrown, "base setCurrency(null) must throw UnsupportedOperationException");

        NumberFormat nf = NumberFormat.getInstance(Locale.US);
        nf.setCurrency(Currency.getInstance("EUR"));
        check("EUR".equals(nf.getCurrency().getCurrencyCode()),
              "concrete format must accept setCurrency and report EUR");
    }
}
