/* @test @summary Basic tests for BigInteger.pow(int) method @run main BigIntegerWitupTest_P3ff327b59825 */

import java.math.BigInteger;

public class BigIntegerWitupTest_P3ff327b59825 {

    public static void main(String[] args) {
        testZeroExponent();
        testPositiveExponent();
        testNegativeExponentThrows();
        testZeroBigInteger();
        testOneBigInteger();
        testLargeExponent();
        testNegativeBaseOddExponent();
        testNegativeBaseEvenExponent();
        System.out.println("All tests passed.");
    }

    static void testZeroExponent() {
        BigInteger one = BigInteger.ONE;
        BigInteger result = one.pow(0);
        assert result.equals(BigInteger.ONE) : "1^0 should be 1";
    }

    static void testPositiveExponent() {
        BigInteger two = BigInteger.valueOf(2);
        BigInteger result = two.pow(10);
        assert result.equals(BigInteger.valueOf(1024)) : "2^10 should be 1024";
    }

    static void testNegativeExponentThrows() {
        BigInteger bi = BigInteger.valueOf(2);
        try {
            bi.pow(-1);
            assert false : "Negative exponent should throw";
        } catch (ArithmeticException e) {
            // expected
        }
    }

    static void testZeroBigInteger() {
        BigInteger zero = BigInteger.ZERO;
        BigInteger result = zero.pow(5);
        assert result.equals(BigInteger.ZERO) : "0^positive should be 0";
        BigInteger resultZero = zero.pow(0);
        assert resultZero.equals(BigInteger.ONE) : "0^0 should be 1";
    }

    static void testOneBigInteger() {
        BigInteger one = BigInteger.ONE;
        BigInteger result = one.pow(100);
        assert result.equals(BigInteger.ONE) : "1^any should be 1";
    }

    static void testLargeExponent() {
        BigInteger three = BigInteger.valueOf(3);
        BigInteger result = three.pow(20);
        BigInteger expected = new BigInteger("3486784401");
        assert result.equals(expected) : "3^20 should be 3486784401";
    }

    static void testNegativeBaseOddExponent() {
        BigInteger minusTwo = BigInteger.valueOf(-2);
        BigInteger result = minusTwo.pow(3);
        assert result.equals(BigInteger.valueOf(-8)) : "(-2)^3 should be -8";
    }

    static void testNegativeBaseEvenExponent() {
        BigInteger minusTwo = BigInteger.valueOf(-2);
        BigInteger result = minusTwo.pow(4);
        assert result.equals(BigInteger.valueOf(16)) : "(-2)^4 should be 16";
    }
}
