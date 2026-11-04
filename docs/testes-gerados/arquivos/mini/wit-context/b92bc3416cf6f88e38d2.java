/*
 * @test
 * @summary Test sqrtAndRemainder method of BigInteger for correctness and exception behavior
 * @run main BigIntegerWitupTest
 */

import java.math.BigInteger;

public class BigIntegerWitupTest {
    public static void main(String[] args) {
        testSqrtAndRemainderPositive();
        testSqrtAndRemainderZero();
        testSqrtAndRemainderNegative();
    }

    private static void testSqrtAndRemainderPositive() {
        // Test with a positive BigInteger
        BigInteger val = new BigInteger("100"); // 100
        BigInteger[] result = val.sqrtAndRemainder();
        BigInteger sqrt = result[0];
        BigInteger rem = result[1];

        // sqrt should be 10
        if (!sqrt.equals(new BigInteger("10"))) {
            throw new AssertionError("Expected sqrt 10 but got " + sqrt);
        }

        // remainder should be 0
        if (!rem.equals(BigInteger.ZERO)) {
            throw new AssertionError("Expected remainder 0 but got " + rem);
        }
    }

    private static void testSqrtAndRemainderZero() {
        // Test with zero
        BigInteger val = BigInteger.ZERO;
        BigInteger[] result = val.sqrtAndRemainder();
        BigInteger sqrt = result[0];
        BigInteger rem = result[1];

        // sqrt should be 0
        if (!sqrt.equals(BigInteger.ZERO)) {
            throw new AssertionError("Expected sqrt 0 but got " + sqrt);
        }

        // remainder should be 0
        if (!rem.equals(BigInteger.ZERO)) {
            throw new AssertionError("Expected remainder 0 but got " + rem);
        }
    }

    private static void testSqrtAndRemainderNegative() {
        // Test with a negative BigInteger
        BigInteger val = new BigInteger("-1");
        try {
            val.sqrtAndRemainder();
            throw new AssertionError("Expected ArithmeticException for negative input");
        } catch (ArithmeticException e) {
            // Expected exception
        }
    }
}
