/*
 * @test
 * @summary Checks BigDecimal.pow(int) exception paths and small valid exponents
 * @run main BigDecimalWitupTest
 */

import java.math.BigDecimal;

public class BigDecimalWitupTest {

    public static void main(String[] args) {
        testInvalidExponentsThrow();
        testValidSmallExponents();
        testZeroExponentReturnsOne();
    }

    static void testInvalidExponentsThrow() {
        int[] invalid = {-1, Integer.MIN_VALUE, 1000000000, Integer.MAX_VALUE};
        BigDecimal base = new BigDecimal("1.5");
        for (int n : invalid) {
            try {
                base.pow(n);
            } catch (ArithmeticException e) {
                if (!"Invalid operation".equals(e.getMessage())) {
                    throw new RuntimeException("Unexpected message for n=" + n + ": " + e.getMessage());
                }
                continue;
            }
            throw new RuntimeException("No ArithmeticException for n=" + n);
        }
    }

    static void testValidSmallExponents() {
        BigDecimal base = new BigDecimal("1.5");
        check(base.pow(1).toString().equals("1.5"), "pow(1) of 1.5");
        check(base.pow(2).toString().equals("2.25"), "pow(2) of 1.5");
        check(base.pow(3).toString().equals("3.375"), "pow(3) of 1.5");
    }

    static void testZeroExponentReturnsOne() {
        BigDecimal result = new BigDecimal("1.5").pow(0);
        check(result.compareTo(BigDecimal.ONE) == 0, "pow(0) value must be 1");
        check(result.scale() == 0, "pow(0) scale must be 0");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + message);
        }
    }
}
