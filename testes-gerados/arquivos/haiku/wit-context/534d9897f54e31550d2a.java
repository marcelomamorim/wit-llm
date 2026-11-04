/*
 * @test
 * @summary Verify BigInteger.sqrt() behavior for negative and non-negative values
 * @run main BigIntegerWitupTest
 */

import java.math.BigInteger;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        testNegativeThrowsArithmeticException();
        testZeroSqrt();
        testPerfectSquares();
        testNonPerfectSquares();
    }

    static void testNegativeThrowsArithmeticException() {
        BigInteger negative = BigInteger.valueOf(-1);
        boolean thrown = false;
        try {
            negative.sqrt();
        } catch (ArithmeticException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected ArithmeticException for negative value");
        }

        BigInteger largeNegative = new BigInteger("-123456789012345678901234567890");
        thrown = false;
        try {
            largeNegative.sqrt();
        } catch (ArithmeticException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected ArithmeticException for large negative value");
        }
    }

    static void testZeroSqrt() {
        BigInteger result = BigInteger.ZERO.sqrt();
        if (!BigInteger.ZERO.equals(result)) {
            throw new RuntimeException("sqrt(0) expected 0 but was " + result);
        }
    }

    static void testPerfectSquares() {
        check(BigInteger.ONE, BigInteger.ONE);
        check(BigInteger.valueOf(4), BigInteger.valueOf(2));
        check(BigInteger.valueOf(9), BigInteger.valueOf(3));
        check(BigInteger.valueOf(144), BigInteger.valueOf(12));
        check(new BigInteger("1000000000000000000000000"),
              new BigInteger("1000000000000"));
    }

    static void testNonPerfectSquares() {
        check(BigInteger.valueOf(2), BigInteger.ONE);
        check(BigInteger.valueOf(8), BigInteger.valueOf(2));
        check(BigInteger.valueOf(99), BigInteger.valueOf(9));
        check(BigInteger.valueOf(10), BigInteger.valueOf(3));
    }

    static void check(BigInteger input, BigInteger expected) {
        BigInteger result = input.sqrt();
        if (!expected.equals(result)) {
            throw new RuntimeException("sqrt(" + input + ") expected " + expected
                    + " but was " + result);
        }
    }
}
