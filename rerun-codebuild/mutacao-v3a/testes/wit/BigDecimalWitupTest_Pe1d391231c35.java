/* @test    @summary Test BigDecimal.divide(BigDecimal, MathContext) behavior with various inputs    @run main BigDecimalWitupTest_Pe1d391231c35 */

import java.math.BigDecimal;
import java.math.MathContext;

public class BigDecimalWitupTest_Pe1d391231c35 {

    public static void main(String[] args) {
        testDivideWithMathContext();
    }

    static void testDivideWithMathContext() {
        // Test normal division
        BigDecimal bd1 = new BigDecimal("10.0");
        BigDecimal bd2 = new BigDecimal("2.0");
        MathContext mc = new MathContext(5);
        BigDecimal result = bd1.divide(bd2, mc);
        if (!result.toString().equals("5")) {
            throw new AssertionError("Expected 5 but got " + result);
        }

        // Test division by zero divisor (should throw ArithmeticException)
        BigDecimal zero = new BigDecimal("0");
        try {
            bd1.divide(zero, mc);
            throw new AssertionError("Expected ArithmeticException for division by zero");
        } catch (ArithmeticException e) {
            // expected
        }

        // Test zero divided by non-zero (should return zero with scale)
        BigDecimal resultZeroDiv = zero.divide(bd2, mc);
        if (!resultZeroDiv.equals(BigDecimal.ZERO)) {
            throw new AssertionError("Expected 0 but got " + resultZeroDiv);
        }

        // Test division with scale and rounding
        BigDecimal bd3 = new BigDecimal("1");
        BigDecimal bd4 = new BigDecimal("3");
        MathContext mc2 = new MathContext(3);
        BigDecimal res = bd3.divide(bd4, mc2);
        if (!res.toString().equals("0.333")) {
            throw new AssertionError("Expected 0.333 but got " + res);
        }
    }
}
