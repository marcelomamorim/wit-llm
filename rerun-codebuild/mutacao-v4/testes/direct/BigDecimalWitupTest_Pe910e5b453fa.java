/* @test
   @summary Verifies the behavior of BigDecimal.divide(BigDecimal, MathContext).
   @run main BigDecimalWitupTest_Pe910e5b453fa
 */
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;

public class BigDecimalWitupTest_Pe910e5b453fa {
    public static void main(String[] args) {
        testDivideWithMathContext();
        testDivideWithZeroDivision();
        testDivideWithZeroDividend();
        testDivideWithUnlimitedPrecision();
    }

    static void testDivideWithMathContext() {
        MathContext mc = new MathContext(5);
        BigDecimal bd1 = new BigDecimal("123.456789");
        BigDecimal bd2 = new BigDecimal("2.0");
        BigDecimal result = bd1.divide(bd2, mc);
        BigDecimal expected = new BigDecimal("61.7284");
        if (!result.equals(expected)) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }

    static void testDivideWithZeroDivision() {
        MathContext mc = new MathContext(10);
        BigDecimal bd1 = new BigDecimal("10");
        BigDecimal zero = BigDecimal.ZERO;
        try {
            bd1.divide(zero, mc);
            throw new AssertionError("Expected ArithmeticException for division by zero");
        } catch (ArithmeticException e) {
            // expected
        }
        BigDecimal zeroDividend = BigDecimal.ZERO;
        BigDecimal result = zeroDividend.divide(new BigDecimal("1"), mc);
        if (!result.equals(BigDecimal.ZERO)) {
            throw new AssertionError("Expected 0 but got " + result);
        }
    }

    static void testDivideWithZeroDividend() {
        MathContext mc = new MathContext(10);
        BigDecimal zero = BigDecimal.ZERO;
        BigDecimal divisor = new BigDecimal("12345");
        BigDecimal result = zero.divide(divisor, mc);
        if (!result.equals(BigDecimal.ZERO)) {
            throw new AssertionError("Expected 0 but got " + result);
        }
    }

    static void testDivideWithUnlimitedPrecision() {
        MathContext mc = new MathContext(0);
        BigDecimal bd1 = new BigDecimal("1");
        BigDecimal bd2 = new BigDecimal("3");
        BigDecimal result = bd1.divide(bd2, mc);
        BigDecimal expected = new BigDecimal("0.3333333333333333333333333333333333333333");
        if (!result.compareTo(expected) == 0) {
            throw new AssertionError("Expected approximately " + expected + " but got " + result);
        }
    }
}
