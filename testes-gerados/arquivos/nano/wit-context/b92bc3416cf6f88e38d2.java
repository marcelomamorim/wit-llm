/* @test    @summary Test for BigInteger.sqrtAndRemainder() method, including exception path for negative values    @run main BigIntegerWitupTest */

import java.math.BigInteger;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        testSqrtAndRemainderWithPositive();
        testSqrtAndRemainderWithZero();
        testSqrtAndRemainderWithNegative();
    }

    private static void testSqrtAndRemainderWithPositive() {
        BigInteger value = BigInteger.valueOf(16);
        BigInteger[] result = value.sqrtAndRemainder();
        assert result != null : "Result should not be null";
        assert result.length == 2 : "Result array should have length 2";
        BigInteger s = result[0];
        BigInteger r = result[1];
        assert s.multiply(s).compareTo(value) <= 0 : "Square of s should be less than or equal to value";
        assert s.add(BigInteger.ONE).multiply(s.add(BigInteger.ONE)).compareTo(value) > 0 : "Next integer squared should be greater than value";
        assert r.compareTo(BigInteger.ZERO) >= 0 : "Remainder should be non-negative";
        assert r.equals(value.subtract(s.square())) : "Remainder should be value minus square of s";
    }

    private static void testSqrtAndRemainderWithZero() {
        BigInteger value = BigInteger.ZERO;
        BigInteger[] result = value.sqrtAndRemainder();
        assert result != null : "Result should not be null";
        assert result.length == 2 : "Result array should have length 2";
        BigInteger s = result[0];
        BigInteger r = result[1];
        assert s.equals(BigInteger.ZERO) : "Square root of zero should be zero";
        assert r.equals(BigInteger.ZERO) : "Remainder of zero should be zero";
    }

    private static void testSqrtAndRemainderWithNegative() {
        BigInteger value = BigInteger.valueOf(-1);
        try {
            value.sqrtAndRemainder();
            assert false : "Expected ArithmeticException for negative input";
        } catch (ArithmeticException e) {
            // Expected exception
        }
    }
}
