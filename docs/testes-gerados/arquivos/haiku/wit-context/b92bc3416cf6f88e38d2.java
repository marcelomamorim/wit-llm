/*
 * @test
 * @summary Witup-generated tests for BigInteger.sqrtAndRemainder()
 * @run main BigIntegerWitupTest
 */

import java.math.BigInteger;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        testPerfectSquare();
        testNonPerfectSquare();
        testZero();
        testLargeValue();
        testNegativeThrows();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void checkInvariant(BigInteger value, BigInteger[] result) {
        BigInteger s = result[0];
        BigInteger r = result[1];
        check(r.signum() >= 0, "remainder must be non-negative for " + value);
        check(s.multiply(s).add(r).equals(value), "s^2 + r must equal value for " + value);
        check(r.compareTo(s.shiftLeft(1)) <= 0, "remainder must be <= 2s for " + value);
    }

    static void testPerfectSquare() {
        BigInteger[] result = BigInteger.valueOf(16).sqrtAndRemainder();
        check(result.length == 2, "result must have two elements");
        check(result[0].equals(BigInteger.valueOf(4)), "sqrt(16) must be 4");
        check(result[1].equals(BigInteger.ZERO), "remainder of 16 must be 0");
        checkInvariant(BigInteger.valueOf(16), result);
    }

    static void testNonPerfectSquare() {
        BigInteger value = BigInteger.valueOf(17);
        BigInteger[] result = value.sqrtAndRemainder();
        check(result[0].equals(BigInteger.valueOf(4)), "sqrt(17) must be 4");
        check(result[1].equals(BigInteger.ONE), "remainder of 17 must be 1");
        checkInvariant(value, result);
    }

    static void testZero() {
        BigInteger[] result = BigInteger.ZERO.sqrtAndRemainder();
        check(result[0].equals(BigInteger.ZERO), "sqrt(0) must be 0");
        check(result[1].equals(BigInteger.ZERO), "remainder of 0 must be 0");
    }

    static void testLargeValue() {
        BigInteger value = new BigInteger("100000000000000000000");
        BigInteger[] result = value.sqrtAndRemainder();
        check(result[0].equals(new BigInteger("10000000000")), "sqrt(10^20) must be 10^10");
        check(result[1].equals(BigInteger.ZERO), "remainder of 10^20 must be 0");
        checkInvariant(value, result);

        BigInteger almost = value.add(BigInteger.valueOf(12345));
        BigInteger[] result2 = almost.sqrtAndRemainder();
        check(result2[0].equals(new BigInteger("10000000000")), "sqrt(10^20 + 12345) must be 10^10");
        check(result2[1].equals(BigInteger.valueOf(12345)), "remainder must be 12345");
        checkInvariant(almost, result2);
    }

    static void testNegativeThrows() {
        boolean thrown = false;
        try {
            BigInteger.valueOf(-1).sqrtAndRemainder();
        } catch (ArithmeticException e) {
            thrown = true;
        }
        check(thrown, "sqrtAndRemainder on negative value must throw ArithmeticException");
    }
}
