/*
 * @test
 * @summary Test BigInteger.sqrtAndRemainder() method for correctness and non-negative remainder
 * @run main BigIntegerWitupTest
 */

import java.math.BigInteger;

public class BigIntegerWitupTest {
    public static void main(String[] args) {
        testPerfectSquare();
        testNonPerfectSquare();
        testZero();
        testOne();
        testLargeNumber();
    }

    private static void testPerfectSquare() {
        BigInteger val = BigInteger.valueOf(16);
        BigInteger[] result = val.sqrtAndRemainder();
        BigInteger sqrt = result[0];
        BigInteger rem = result[1];
        if (!sqrt.equals(BigInteger.valueOf(4))) {
            throw new AssertionError("Expected sqrt 4 but got " + sqrt);
        }
        if (!rem.equals(BigInteger.ZERO)) {
            throw new AssertionError("Expected remainder 0 but got " + rem);
        }
    }

    private static void testNonPerfectSquare() {
        BigInteger val = BigInteger.valueOf(20);
        BigInteger[] result = val.sqrtAndRemainder();
        BigInteger sqrt = result[0];
        BigInteger rem = result[1];
        if (!sqrt.equals(BigInteger.valueOf(4))) {
            throw new AssertionError("Expected sqrt 4 but got " + sqrt);
        }
        if (!rem.equals(BigInteger.valueOf(4))) {
            throw new AssertionError("Expected remainder 4 but got " + rem);
        }
        if (rem.signum() < 0) {
            throw new AssertionError("Remainder must be non-negative");
        }
    }

    private static void testZero() {
        BigInteger val = BigInteger.ZERO;
        BigInteger[] result = val.sqrtAndRemainder();
        if (!result[0].equals(BigInteger.ZERO)) {
            throw new AssertionError("Expected sqrt 0 but got " + result[0]);
        }
        if (!result[1].equals(BigInteger.ZERO)) {
            throw new AssertionError("Expected remainder 0 but got " + result[1]);
        }
    }

    private static void testOne() {
        BigInteger val = BigInteger.ONE;
        BigInteger[] result = val.sqrtAndRemainder();
        if (!result[0].equals(BigInteger.ONE)) {
            throw new AssertionError("Expected sqrt 1 but got " + result[0]);
        }
        if (!result[1].equals(BigInteger.ZERO)) {
            throw new AssertionError("Expected remainder 0 but got " + result[1]);
        }
    }

    private static void testLargeNumber() {
        // Test a large number that is not a perfect square
        BigInteger val = new BigInteger("100000000000000000000000000000000000000");
        BigInteger[] result = val.sqrtAndRemainder();
        BigInteger sqrt = result[0];
        BigInteger rem = result[1];
        // sqrt^2 must be <= val
        if (sqrt.multiply(sqrt).compareTo(val) > 0) {
            throw new AssertionError("sqrt squared is greater than original value");
        }
        // remainder must be val - sqrt^2
        if (!rem.equals(val.subtract(sqrt.multiply(sqrt)))) {
            throw new AssertionError("Remainder incorrect");
        }
        if (rem.signum() < 0) {
            throw new AssertionError("Remainder must be non-negative");
        }
    }
}
