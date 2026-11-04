/*
 * @test
 * @summary Verify that the base NumberFormat.getCurrency() throws UnsupportedOperationException
 * @run main NumberFormatWitupTest
 */
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;

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
        public Number parse(String text, ParsePosition pos) {
            return null;
        }
    }

    public static void main(String[] args) {
        NumberFormat nf = new StubFormat();
        boolean thrown = false;
        try {
            nf.getCurrency();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException from NumberFormat.getCurrency()");
        }
    }
}
