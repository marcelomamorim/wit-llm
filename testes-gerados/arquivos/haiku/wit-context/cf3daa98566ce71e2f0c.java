/*
 * @test
 * @summary WITUP-derived tests for BigDecimal(char[] in, int offset, int len)
 * @run main BigDecimalWitupTest
 */

import java.math.BigDecimal;

public class BigDecimalWitupTest {

    public static void main(String[] args) {
        char[] digits = "12345".toCharArray();

        String sub = new BigDecimal(digits, 1, 3).toString();
        if (!"234".equals(sub)) {
            throw new AssertionError("expected 234 but was " + sub);
        }

        String whole = new BigDecimal(digits, 0, digits.length).toString();
        if (!"12345".equals(whole)) {
            throw new AssertionError("expected 12345 but was " + whole);
        }

        char[] fraction = "1.50".toCharArray();
        String scaled = new BigDecimal(fraction, 0, fraction.length).toString();
        if (!"1.50".equals(scaled)) {
            throw new AssertionError("expected 1.50 but was " + scaled);
        }

        expectNumberFormatException(digits, -1, 2);
        expectNumberFormatException(digits, 3, 3);
        expectNumberFormatException(digits, 0, 6);
        expectNumberFormatException(digits, 5, 1);
    }

    private static void expectNumberFormatException(char[] in, int offset, int len) {
        try {
            new BigDecimal(in, offset, len);
        } catch (NumberFormatException e) {
            return;
        }
        throw new AssertionError("NumberFormatException expected for offset=" + offset + ", len=" + len);
    }
}
