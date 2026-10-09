/* @test    @summary Test BigInteger.pow(int) for various exponents    @run main BigIntegerWitupTest_P36d2aa1a7711 */

import java.math.BigInteger;

public class BigIntegerWitupTest_P36d2aa1a7711 {

    public static void main(String[] args) {
        testZeroExponent();
        testPositiveExponent();
        testNegativeExponentThrows();
        testZeroBigInteger();
        testOneBigInteger();
        testLargeExponent();
        testNegativeExponentLarge();
    }

    static void testZeroExponent() {
        BigInteger bi = BigInteger.valueOf(5);
        BigInteger result = bi.pow(0);
        assert result.equals(BigInteger.ONE) : "5^0 should be 1";
    }

    static void testPositiveExponent() {
        BigInteger bi = BigInteger.valueOf(2);
        BigInteger result = bi.pow(10);
        assert result.equals(BigInteger.valueOf(1024)) : "2^10 should be 1024";
    }

    static void testNegativeExponentThrows() {
        BigInteger bi = BigInteger.valueOf(2);
        try {
            bi.pow(-3);
            assert false : "Expected ArithmeticException for negative exponent";
        } catch (ArithmeticException e) {
            // expected
        }
    }

    static void testZeroBigInteger() {
        BigInteger bi = BigInteger.ZERO;
        BigInteger result = bi.pow(5);
        assert result.equals(BigInteger.ZERO) : "0^n should be 0 for n > 0";
        BigInteger resultZero = bi.pow(0);
        assert resultZero.equals(BigInteger.ONE) : "0^0 should be 1";
    }

    static void testOneBigInteger() {
        BigInteger bi = BigInteger.ONE;
        BigInteger result = bi.pow(100);
        assert result.equals(BigInteger.ONE) : "1^n should be 1";
    }

    static void testLargeExponent() {
        BigInteger bi = BigInteger.valueOf(3);
        BigInteger result = bi.pow(20);
        BigInteger expected = new BigInteger("3486784401");
        assert result.equals(expected) : "3^20 should be 3486784401";
    }

    static void testNegativeExponentLarge() {
        BigInteger bi = BigInteger.valueOf(2);
        try {
            bi.pow(-100);
            assert false : "Expected ArithmeticException for large negative exponent";
        } catch (ArithmeticException e) {
            // expected
        }
    }
}
