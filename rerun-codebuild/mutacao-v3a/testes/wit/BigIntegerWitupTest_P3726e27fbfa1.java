/* @test    @summary Tests for BigInteger.sqrt() method, including exception case for negative values    @run main BigIntegerWitupTest_P3726e27fbfa1 */

import java.math.BigInteger;

public class BigIntegerWitupTest_P3726e27fbfa1 {

    public static void main(String[] args) {
        testSqrtPositive();
        testSqrtZero();
        testSqrtNegative();
    }

    public static void testSqrtPositive() {
        BigInteger bi = BigInteger.valueOf(16);
        BigInteger result = bi.sqrt();
        assert result.equals(BigInteger.valueOf(4)) : "Expected sqrt of 16 to be 4, but got " + result;
    }

    public static void testSqrtZero() {
        BigInteger bi = BigInteger.ZERO;
        BigInteger result = bi.sqrt();
        assert result.equals(BigInteger.ZERO) : "Expected sqrt of 0 to be 0, but got " + result;
    }

    public static void testSqrtNegative() {
        BigInteger bi = BigInteger.valueOf(-1);
        try {
            bi.sqrt();
            assert false : "Expected ArithmeticException for negative BigInteger";
        } catch (ArithmeticException e) {
            assert e.getMessage().equals("Negative BigInteger") : "Unexpected exception message: " + e.getMessage();
        }
    }
}
