/*
 * @test
 * @summary Test BigDecimal.pow(int) for various inputs
 * @run main BigDecimalWitupTest
 */

import java.math.BigDecimal;

public class BigDecimalWitupTest {

    public static void main(String[] args) {
        testPowPositiveExponent();
        testPowZeroExponent();
        testPowNegativeExponentThrows();
        testPowLargeExponent();
    }

    static void testPowPositiveExponent() {
        BigDecimal bd = new BigDecimal("2.5");
        BigDecimal result = bd.pow(3);
        BigDecimal expected = new BigDecimal("15.625");
        if (!result.equals(expected)) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }

    static void testPowZeroExponent() {
        BigDecimal bd = new BigDecimal("123.456");
        BigDecimal result = bd.pow(0);
        BigDecimal expected = BigDecimal.ONE;
        if (!result.equals(expected)) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }

    static void testPowNegativeExponentThrows() {
        BigDecimal bd = new BigDecimal("1.23");
        boolean thrown = false;
        try {
            bd.pow(-1);
        } catch (ArithmeticException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected ArithmeticException for negative exponent");
        }
    }

    static void testPowLargeExponent() {
        BigDecimal bd = new BigDecimal("1.1");
        boolean thrown = false;
        try {
            bd.pow(1000000000);
        } catch (ArithmeticException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected ArithmeticException for large exponent");
        }
    }
}
