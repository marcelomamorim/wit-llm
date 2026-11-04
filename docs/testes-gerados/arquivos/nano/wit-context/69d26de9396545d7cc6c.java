/* @test    @summary Test for BigDecimal.pow(int n) method, including edge cases and exception paths    @run main BigDecimalWitupTest */

import java.math.BigDecimal;

public class BigDecimalWitupTest {

    public static void main(String[] args) {
        testPowWithValidValues();
        testPowWithZero();
        testPowWithNegativeExponent();
        testPowWithLargeExponent();
        testPowWithInvalidExponent();
    }

    static void testPowWithValidValues() {
        BigDecimal bd = new BigDecimal("2.5");
        BigDecimal result = bd.pow(3);
        BigDecimal expected = new BigDecimal("15.625");
        if (!result.equals(expected)) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }

    static void testPowWithZero() {
        BigDecimal bd = new BigDecimal("123.456");
        BigDecimal result = bd.pow(0);
        BigDecimal expected = BigDecimal.ONE;
        if (!result.equals(expected)) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }

    static void testPowWithNegativeExponent() {
        BigDecimal bd = new BigDecimal("1.5");
        try {
            bd.pow(-2);
            throw new AssertionError("Expected ArithmeticException for negative exponent");
        } catch (ArithmeticException e) {
            // Expected exception
        }
    }

    static void testPowWithLargeExponent() {
        BigDecimal bd = new BigDecimal("1.1");
        BigDecimal result = bd.pow(999999999);
        // Result is large; just check that no exception is thrown and result is not null
        if (result == null) {
            throw new AssertionError("Result should not be null for large exponent");
        }
    }

    static void testPowWithInvalidExponent() {
        BigDecimal bd = new BigDecimal("2");
        try {
            bd.pow(1000000000 + 1); // n > 999999999
            throw new AssertionError("Expected ArithmeticException for exponent > 999999999");
        } catch (ArithmeticException e) {
            // Expected exception
        }
    }
}
