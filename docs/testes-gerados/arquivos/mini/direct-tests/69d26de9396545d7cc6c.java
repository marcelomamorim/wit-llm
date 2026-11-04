/*
 * @test
 * @summary Test BigDecimal.pow(int) method for valid and invalid inputs
 * @run main BigDecimalWitupTest
 */

import java.math.BigDecimal;
import java.math.MathContext;

public class BigDecimalWitupTest {
    public static void main(String[] args) {
        testPowZeroExponent();
        testPowPositiveExponent();
        testPowLargeExponent();
        testPowNegativeExponentThrows();
        testPowExponentTooLargeThrows();
    }

    static void testPowZeroExponent() {
        BigDecimal bd = new BigDecimal("123.45");
        BigDecimal result = bd.pow(0);
        // Any number to the power 0 is 1
        if (!result.equals(BigDecimal.ONE)) {
            throw new RuntimeException("pow(0) did not return 1: " + result);
        }
    }

    static void testPowPositiveExponent() {
        BigDecimal bd = new BigDecimal("2");
        BigDecimal result = bd.pow(3);
        // 2^3 = 8
        if (!result.equals(new BigDecimal("8"))) {
            throw new RuntimeException("pow(3) did not return 8: " + result);
        }
    }

    static void testPowLargeExponent() {
        BigDecimal bd = new BigDecimal("1.1");
        int exponent = 10;
        BigDecimal result = bd.pow(exponent);
        // result should be > 1
        if (result.compareTo(BigDecimal.ONE) <= 0) {
            throw new RuntimeException("pow(10) result not greater than 1: " + result);
        }
    }

    static void testPowNegativeExponentThrows() {
        BigDecimal bd = new BigDecimal("2");
        try {
            bd.pow(-1);
            throw new RuntimeException("pow(-1) did not throw ArithmeticException");
        } catch (ArithmeticException e) {
            // expected
        }
    }

    static void testPowExponentTooLargeThrows() {
        BigDecimal bd = new BigDecimal("2");
        try {
            bd.pow(1000000000); // 1_000_000_000 > 999_999_999
            throw new RuntimeException("pow(1000000000) did not throw ArithmeticException");
        } catch (ArithmeticException e) {
            // expected
        }
    }
}
