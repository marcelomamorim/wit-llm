/*
 * @test
 * @summary Tests the BigInteger.pow(int) method for various inputs and behaviors.
 * @run main BigIntegerWitupTest_P3ff327b59825
 */

import java.math.BigInteger;

public class BigIntegerWitupTest_P3ff327b59825 {
    public static void main(String[] args) {
        testPositiveExponent();
        testZeroExponent();
        testNegativeExponent();
        testZeroBigInteger();
        testOneBigInteger();
        testLargeExponent();
        testOverflowExponent();
    }

    static void testPositiveExponent() {
        BigInteger base = BigInteger.valueOf(2);
        int exponent = 10;
        BigInteger result = base.pow(exponent);
        assert result.equals(BigInteger.valueOf(1024)) : "Failed positive exponent test";
    }

    static void testZeroExponent() {
        BigInteger base = BigInteger.valueOf(12345);
        int exponent = 0;
        BigInteger result = base.pow(exponent);
        assert result.equals(BigInteger.ONE) : "Failed zero exponent test";
    }

    static void testNegativeExponent() {
        BigInteger base = BigInteger.valueOf(5);
        int exponent = -3;
        boolean exceptionThrown = false;
        try {
            base.pow(exponent);
        } catch (ArithmeticException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected ArithmeticException for negative exponent";
    }

    static void testZeroBigInteger() {
        BigInteger zero = BigInteger.ZERO;
        BigInteger result = zero.pow(10);
        assert result.equals(BigInteger.ZERO) : "Failed zero BigInteger test";
    }

    static void testOneBigInteger() {
        BigInteger one = BigInteger.ONE;
        BigInteger result = one.pow(12345);
        assert result.equals(BigInteger.ONE) : "Failed one BigInteger test";
    }

    static void testLargeExponent() {
        BigInteger base = BigInteger.valueOf(3);
        int exponent = 20;
        BigInteger result = base.pow(exponent);
        BigInteger expected = new BigInteger("3486784401");
        assert result.equals(expected) : "Failed large exponent test";
    }

    static void testOverflowExponent() {
        BigInteger base = BigInteger.valueOf(2);
        int exponent = Integer.MAX_VALUE / 2 + 1;
        boolean exceptionThrown = false;
        try {
            base.pow(exponent);
        } catch (ArithmeticException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected overflow handling or exception for large exponent";
    }
}
