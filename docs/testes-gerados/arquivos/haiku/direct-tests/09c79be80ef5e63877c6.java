/*
 * @test
 * @summary Verifies rounding behavior of the BigDecimal(int, MathContext) constructor
 * @run main BigDecimalWitupTest
 */

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class BigDecimalWitupTest {

    public static void main(String[] args) {
        testUnlimitedPrecisionIsExact();
        testPositiveRoundingDropsDigits();
        testRoundingCarryPropagates();
        testNegativeRoundingHalfUp();
        testHalfEvenTie();
        testHalfUpTie();
        testZeroWithPrecision();
        testPrecisionMatchesRoundedDigits();
    }

    private static void check(String actual, String expected, String label) {
        if (!expected.equals(actual)) {
            throw new RuntimeException(label + ": expected " + expected + " but was " + actual);
        }
    }

    private static void checkInt(int actual, int expected, String label) {
        if (actual != expected) {
            throw new RuntimeException(label + ": expected " + expected + " but was " + actual);
        }
    }

    static void testUnlimitedPrecisionIsExact() {
        BigDecimal bd = new BigDecimal(123456, MathContext.UNLIMITED);
        check(bd.toPlainString(), "123456", "unlimited value");
        checkInt(bd.scale(), 0, "unlimited scale");
    }

    static void testPositiveRoundingDropsDigits() {
        MathContext mc = new MathContext(3, RoundingMode.HALF_UP);
        BigDecimal bd = new BigDecimal(123456, mc);
        check(bd.toPlainString(), "123000", "123456 rounded to 3 digits");
        checkInt(bd.scale(), -3, "scale after rounding");
    }

    static void testRoundingCarryPropagates() {
        MathContext mc = new MathContext(2, RoundingMode.HALF_UP);
        BigDecimal bd = new BigDecimal(999, mc);
        check(bd.toPlainString(), "1000", "999 rounded to 2 digits");
        checkInt(bd.precision(), 2, "precision after carry");
    }

    static void testNegativeRoundingHalfUp() {
        MathContext mc = new MathContext(3, RoundingMode.HALF_UP);
        BigDecimal bd = new BigDecimal(-123456, mc);
        check(bd.toPlainString(), "-123000", "negative 123456 rounded to 3 digits");
    }

    static void testHalfEvenTie() {
        MathContext mc = new MathContext(2, RoundingMode.HALF_EVEN);
        BigDecimal bd = new BigDecimal(125, mc);
        check(bd.toPlainString(), "120", "HALF_EVEN tie 125 to 2 digits");
    }

    static void testHalfUpTie() {
        MathContext mc = new MathContext(2, RoundingMode.HALF_UP);
        BigDecimal bd = new BigDecimal(125, mc);
        check(bd.toPlainString(), "130", "HALF_UP tie 125 to 2 digits");
    }

    static void testZeroWithPrecision() {
        MathContext mc = new MathContext(3, RoundingMode.HALF_UP);
        BigDecimal bd = new BigDecimal(0, mc);
        check(bd.toPlainString(), "0", "zero with precision");
        checkInt(bd.precision(), 1, "zero precision");
    }

    static void testPrecisionMatchesRoundedDigits() {
        MathContext mc = new MathContext(3, RoundingMode.HALF_UP);
        BigDecimal bd = new BigDecimal(123456, mc);
        checkInt(bd.precision(), 3, "precision of rounded 123456");
        BigDecimal same = new BigDecimal("1.23E+5");
        if (!bd.equals(same)) {
            throw new RuntimeException("123456 rounded to 3 digits should equal 1.23E+5");
        }
    }
}
