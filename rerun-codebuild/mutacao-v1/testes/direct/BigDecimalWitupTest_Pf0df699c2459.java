/* @test    @summary Test BigDecimal.pow(int, MathContext) for various cases    @run main BigDecimalWitupTest_Pf0df699c2459 */

import java.math.BigDecimal;
import java.math.MathContext;

public class BigDecimalWitupTest_Pf0df699c2459 {

    public static void main(String[] args) {
        testPowerZeroExponent();
        testPowerPositiveExponent();
        testPowerNegativeExponent();
        testPowerLargeExponent();
        testPowerWithMathContext();
        testPowerInvalidLargeExponent();
    }

    static void testPowerZeroExponent() {
        BigDecimal bd = new BigDecimal("123.456");
        BigDecimal result = bd.pow(0, new MathContext(10));
        if (!result.equals(BigDecimal.ONE)) {
            throw new AssertionError("Failed: pow with zero exponent");
        }
    }

    static void testPowerPositiveExponent() {
        BigDecimal bd = new BigDecimal("2");
        BigDecimal result = bd.pow(10, new MathContext(10));
        BigDecimal expected = new BigDecimal("1024");
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: pow with positive exponent");
        }
    }

    static void testPowerNegativeExponent() {
        BigDecimal bd = new BigDecimal("2");
        BigDecimal result = bd.pow(-3, new MathContext(10));
        BigDecimal expected = BigDecimal.ONE.divide(new BigDecimal("8"), new MathContext(10));
        if (result.subtract(expected).abs().compareTo(new BigDecimal("1E-10")) > 0) {
            throw new AssertionError("Failed: pow with negative exponent");
        }
    }

    static void testPowerLargeExponent() {
        BigDecimal bd = new BigDecimal("2");
        try {
            bd.pow(1000000000, new MathContext(10));
            throw new AssertionError("Expected ArithmeticException for large exponent");
        } catch (ArithmeticException e) {
            // expected
        }
    }

    static void testPowerWithMathContext() {
        BigDecimal bd = new BigDecimal("1.5");
        MathContext mc = new MathContext(5);
        BigDecimal result = bd.pow(3, mc);
        BigDecimal expected = new BigDecimal("3.375");
        if (result.subtract(expected).abs().compareTo(new BigDecimal("1E-5")) > 0) {
            throw new AssertionError("Failed: pow with MathContext");
        }
    }

    static void testPowerInvalidLargeExponent() {
        BigDecimal bd = new BigDecimal("2");
        try {
            bd.pow(1000000000, new MathContext(10));
            throw new AssertionError("Expected ArithmeticException for invalid large exponent");
        } catch (ArithmeticException e) {
            // expected
        }
    }
}
