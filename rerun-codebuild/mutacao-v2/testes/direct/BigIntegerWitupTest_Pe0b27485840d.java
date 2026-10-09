/*
 * @test
 * @summary Test BigInteger.sqrt() method behavior including negative input exception
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
        // sqrt(16) == 4
        BigInteger sixteen = BigInteger.valueOf(16L);
        BigInteger expected = BigInteger.valueOf(4L);
        BigInteger result = sixteen.sqrt();
        if (!result.equals(expected)) {
            throw new RuntimeException("sqrt(16) expected 4 but got " + result);
        }

        // sqrt(17) == 4 (floor)
        BigInteger seventeen = BigInteger.valueOf(17L);
        BigInteger expectedFloor = BigInteger.valueOf(4L);
        BigInteger resultFloor = seventeen.sqrt();
        if (!resultFloor.equals(expectedFloor)) {
            throw new RuntimeException("sqrt(17) expected 4 but got " + resultFloor);
        }
    }

    private static void testSqrtZero() {
        BigInteger zero = BigInteger.ZERO;
        BigInteger result = zero.sqrt();
        if (!result.equals(BigInteger.ZERO)) {
            throw new RuntimeException("sqrt(0) expected 0 but got " + result);
        }
    }

    private static void testSqrtNegative() {
        BigInteger negativeOne = BigInteger.valueOf(-1L);
        try {
            negativeOne.sqrt();
            throw new RuntimeException("Expected ArithmeticException for sqrt of negative number");
        } catch (ArithmeticException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Negative BigInteger")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}
