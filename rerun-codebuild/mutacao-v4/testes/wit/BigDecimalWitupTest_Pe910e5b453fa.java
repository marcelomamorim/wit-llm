/* @test
   @summary Testa o método divide de BigDecimal com MathContext.
   @run main BigDecimalWitupTest_Pe910e5b453fa
 */
import java.math.BigDecimal;
import java.math.MathContext;
public class BigDecimalWitupTest_Pe910e5b453fa {
    public static void main(String[] args) {
        testDivideNormal();
        testDivideByZero();
        testDivideZeroDividend();
        testDivideWithPrecision();
    }
    private static void testDivideNormal() {
        BigDecimal a = new BigDecimal("10");
        BigDecimal b = new BigDecimal("2");
        MathContext mc = new MathContext(10);
        BigDecimal result = a.divide(b, mc);
        if (!result.equals(new BigDecimal("5"))) {
            throw new AssertionError("Failed: expected 5, got " + result);
        }
    }
    private static void testDivideByZero() {
        BigDecimal a = new BigDecimal("10");
        BigDecimal zero = new BigDecimal("0");
        MathContext mc = new MathContext(10);
        try {
            a.divide(zero, mc);
            throw new AssertionError("Expected ArithmeticException for division by zero");
        } catch (ArithmeticException e) {
            // expected
        }
    }
    private static void testDivideZeroDividend() {
        BigDecimal zero = new BigDecimal("0");
        BigDecimal b = new BigDecimal("123.456");
        MathContext mc = new MathContext(10);
        BigDecimal result = zero.divide(b, mc);
        if (!result.equals(BigDecimal.ZERO)) {
            throw new AssertionError("Failed: expected 0, got " + result);
        }
    }
    private static void testDivideWithPrecision() {
        BigDecimal a = new BigDecimal("1");
        BigDecimal b = new BigDecimal("3");
        MathContext mc = new MathContext(5);
        BigDecimal result = a.divide(b, mc);
        BigDecimal expected = new BigDecimal("0.33333");
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: expected 0.33333, got " + result);
        }
    }
}
