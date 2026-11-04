/*
 * @test
 * @summary Deterministic tests for java.math.BigDecimal.pow(int) covering valid and invalid inputs
 * @run main BigDecimalWitupTest
 */

import java.math.BigDecimal;
import java.math.ArithmeticException;

public class BigDecimalWitupTest {
    public static void main(String[] args) {
        testPowValid();
        testPowZero();
        testPowOne();
        testPowNegativeExponentThrows();
        testPowExponentTooLargeThrows();
        System.out.println("All tests passed.");
    }

    private static void testPowValid() {
        BigDecimal bd = new BigDecimal("2");
        BigDecimal result = bd.pow(3);
        // 2^3 = 8
        if (!"8".equals(result.toPlainString())) {
            throw new AssertionError("Expected 8 but got " + result.toPlainString());
        }

        bd = new BigDecimal("1.5");
        result = bd.pow(2);
        // 1.5^2 = 2.25
        if (!"2.25".equals(result.toPlainString())) {
            throw new AssertionError("Expected 2.25 but got " + result.toPlainString());
        }
    }

    private static void testPowZero() {
        BigDecimal bd = new BigDecimal("123.456");
        BigDecimal result = bd.pow(0);
        // Any number to power 0 is 1
        if (!"1".equals(result.toPlainString())) {
            throw new AssertionError("Expected 1 but got " + result.toPlainString());
        }
    }

    private static void testPowOne() {
        BigDecimal bd = new BigDecimal("-789.01");
        BigDecimal result = bd.pow(1);
        // Any number to power 1 is itself
        if (!bd.equals(result)) {
            throw new AssertionError("Expected " + bd.toPlainString() + " but got " + result.toPlainString());
        }
    }

    private static void testPowNegativeExponentThrows() {
        BigDecimal bd = new BigDecimal("2");
        try {
            bd.pow(-1);
            throw new AssertionError("Expected ArithmeticException for negative exponent");
        } catch (ArithmeticException e) {
            // Expected
        }
    }

    private static void testPowExponentTooLargeThrows() {
        BigDecimal bd = new BigDecimal("2");
        try {
            bd.pow(1000000000); // 1_000_000_000 > 999_999_999
            throw new AssertionError("Expected ArithmeticException for exponent > 999999999");
        } catch (ArithmeticException e) {
            // Expected
        }
    }
}
