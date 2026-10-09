/* @test    @summary Test BigInteger.sqrt() with positive number    @run main BigIntegerWitupTest_P3726e27fbfa1 */

import java.math.BigInteger;

public class BigIntegerWitupTest_P3726e27fbfa1 {
    public static void main(String[] args) {
        testSqrtPositive();
        testSqrtZero();
        testSqrtNegative();
    }

    static void testSqrtPositive() {
        BigInteger num = BigInteger.valueOf(16L);
        BigInteger sqrt = num.sqrt();
        assert sqrt.equals(BigInteger.valueOf(4L)) : "Expected sqrt(16) = 4, got " + sqrt;
    }

    static void testSqrtZero() {
        BigInteger num = BigInteger.ZERO;
        BigInteger sqrt = num.sqrt();
        assert sqrt.equals(BigInteger.ZERO) : "Expected sqrt(0) = 0, got " + sqrt;
    }

    static void testSqrtNegative() {
        try {
            BigInteger num = BigInteger.valueOf(-1L);
            num.sqrt();
            assert false : "Expected ArithmeticException for negative input";
        } catch (ArithmeticException e) {
            assert e.getMessage().contains("Negative BigInteger") : "Unexpected exception message: " + e.getMessage();
        }
    }
}
