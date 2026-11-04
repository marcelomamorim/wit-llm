/*
 * @test
 * @summary Behavioral checks for BigDecimal(int, MathContext) rounding and precision handling
 * @run main BigDecimalWitupTest
 */

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class BigDecimalWitupTest {

    public static void main(String[] args) {
        check(new BigDecimal(123456, new MathContext(3)).compareTo(new BigDecimal("123000")) == 0,
              "HALF_UP rounding of 123456 to precision 3 should be 123000");

        check(new BigDecimal(123456, MathContext.UNLIMITED).compareTo(new BigDecimal("123456")) == 0,
              "Precision 0 must keep the exact value");
        check(new BigDecimal(123456, MathContext.UNLIMITED).scale() == 0,
              "Precision 0 must keep scale 0 for an int value");

        check(new BigDecimal(999, new MathContext(2)).compareTo(new BigDecimal("1000")) == 0,
              "Carry across digits: 999 at precision 2 should be 1000");

        check(new BigDecimal(-123456, new MathContext(3)).compareTo(new BigDecimal("-123000")) == 0,
              "Negative HALF_UP rounding should be symmetric");

        check(new BigDecimal(125, new MathContext(2, RoundingMode.HALF_EVEN)).compareTo(new BigDecimal("120")) == 0,
              "HALF_EVEN of 125 at precision 2 should round to 120");
        check(new BigDecimal(125, new MathContext(2, RoundingMode.HALF_UP)).compareTo(new BigDecimal("130")) == 0,
              "HALF_UP of 125 at precision 2 should round to 130");

        BigDecimal exact = new BigDecimal(42, new MathContext(5));
        check(exact.compareTo(new BigDecimal("42")) == 0 && exact.scale() == 0,
              "Value with fewer digits than precision must be unchanged");
        check(exact.precision() == 2,
              "Precision of 42 must be 2");

        BigDecimal zero = new BigDecimal(0, new MathContext(3));
        check(zero.signum() == 0 && zero.compareTo(BigDecimal.ZERO) == 0,
              "Zero must remain zero");

        check(new BigDecimal(Integer.MIN_VALUE, new MathContext(3)).compareTo(new BigDecimal("-2150000000")) == 0,
              "Integer.MIN_VALUE at precision 3 should round to -2.15E+9");

        boolean npe = false;
        try {
            new BigDecimal(1, null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "Null MathContext must raise NullPointerException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
