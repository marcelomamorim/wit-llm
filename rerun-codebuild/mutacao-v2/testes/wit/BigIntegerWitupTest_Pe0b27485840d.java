/*
 * @test
 * @summary Test BigInteger.sqrt() method for positive and negative inputs
 * @run main BigIntegerWitupTest_Pe0b27485840d
 */

import java.math.BigInteger;

public class BigIntegerWitupTest_Pe0b27485840d {
    public static void main(String[] args) {
        testSqrtPositive();
        testSqrtZero();
        testSqrtNegative();
    }

    private static void testSqrtPositive() {
        // sqrt of 16 should be 4
        BigInteger sixteen = BigInteger.valueOf(16L);
        BigInteger sqrt = sixteen.sqrt();
        if (!sqrt.equals(BigInteger.valueOf(4L))) {
            throw new RuntimeException("sqrt(16) expected 4 but got " + sqrt);
        }

        // sqrt of 17 should be 4 (floor)
        BigInteger seventeen = BigInteger.valueOf(17L);
        sqrt = seventeen.sqrt();
        if (!sqrt.equals(BigInteger.valueOf(4L))) {
            throw new RuntimeException("sqrt(17) expected 4 but got " + sqrt);
        }
    }

    private static void testSqrtZero() {
        BigInteger zero = BigInteger.ZERO;
        BigInteger sqrt = zero.sqrt();
        if (!sqrt.equals(BigInteger.ZERO)) {
            throw new RuntimeException("sqrt(0) expected 0 but got " + sqrt);
        }
    }

    private static void testSqrtNegative() {
        BigInteger negativeOne = BigInteger.valueOf(-1L);
        try {
            negativeOne.sqrt();
            throw new RuntimeException("Expected ArithmeticException for sqrt of negative number");
        } catch (ArithmeticException e) {
            // Expected exception
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Negative BigInteger")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}
