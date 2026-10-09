/* @test    @summary Test BigInteger.sqrt() method for non-negative and negative cases    @run main BigIntegerWitupTest_P5107b0005554 */

import java.math.BigInteger;

public class BigIntegerWitupTest_P5107b0005554 {

    public static void main(String[] args) {
        testSqrtPositive();
        testSqrtZero();
        testSqrtNegative();
    }

    static void testSqrtPositive() {
        BigInteger num = new BigInteger("16");
        BigInteger result = num.sqrt();
        assert result.equals(new BigInteger("4")) : "Expected sqrt of 16 to be 4, but got " + result;
    }

    static void testSqrtZero() {
        BigInteger num = new BigInteger("0");
        BigInteger result = num.sqrt();
        assert result.equals(BigInteger.ZERO) : "Expected sqrt of 0 to be 0, but got " + result;
    }

    static void testSqrtNegative() {
        BigInteger num = new BigInteger("-1");
        try {
            num.sqrt();
            assert false : "Expected ArithmeticException for negative input";
        } catch (ArithmeticException e) {
            assert e.getMessage().equals("Negative BigInteger") : "Unexpected exception message: " + e.getMessage();
        }
    }
}
