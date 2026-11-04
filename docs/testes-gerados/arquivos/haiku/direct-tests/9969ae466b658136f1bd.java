/*
 * @test
 * @summary NumberFormat.getCurrency() throws UnsupportedOperationException in the base implementation
 * @run main NumberFormatWitupTest
 */

import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;

public class NumberFormatWitupTest {

    static class Stub extends NumberFormat {
        public StringBuffer format(double number, StringBuffer toAppendTo, FieldPosition pos) {
            return toAppendTo.append(number);
        }

        public StringBuffer format(long number, StringBuffer toAppendTo, FieldPosition pos) {
            return toAppendTo.append(number);
        }

        public Number parse(String source, ParsePosition parsePos) {
            return null;
        }
    }

    public static void main(String[] args) {
        NumberFormat nf = new Stub();
        boolean thrown = false;
        try {
            nf.getCurrency();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("getCurrency() should throw UnsupportedOperationException in base class");
        }

        thrown = false;
        try {
            nf.getCurrency();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("getCurrency() should consistently throw UnsupportedOperationException");
        }

        if (NumberFormat.getInstance() == null) {
            throw new AssertionError("NumberFormat.getInstance() should not return null");
        }
    }
}
