/*
 * @test
 * @summary Test BigInteger.divide(BigInteger) for normal and edge cases including division by zero
 * @run main BigIntegerWitupTest
 */

import java.math.BigInteger;

public class BigIntegerWitupTest {
    public static void main(String[] args) {
        testDivideNormal();
        testDivideByZero();
    }

    private static void testDivideNormal() {
        // Test division with normal positive numbers
        BigInteger dividend = new BigInteger("123456789012345678901234567890");
        BigInteger divisor = new BigInteger("1234567890");
        BigInteger result = dividend.divide(divisor);
        // Expected result computed externally or by known correct method
        BigInteger expected = new BigInteger("100000000010000000001");
        if (!result.equals(expected)) {
            throw new AssertionError("Normal division failed: expected " + expected + " but got " + result);
        }

        // Test division where dividend equals divisor
        BigInteger equalDividend = new BigInteger("987654321");
        BigInteger equalDivisor = new BigInteger("987654321");
        BigInteger equalResult = equalDividend.divide(equalDivisor);
        if (!equalResult.equals(BigInteger.ONE)) {
            throw new AssertionError("Division of equal numbers should be 1 but got " + equalResult);
        }

        // Test division where dividend is zero
        BigInteger zeroDividend = BigInteger.ZERO;
        BigInteger nonZeroDivisor = new BigInteger("12345");
        BigInteger zeroResult = zeroDividend.divide(nonZeroDivisor);
        if (!zeroResult.equals(BigInteger.ZERO)) {
            throw new AssertionError("Zero dividend division should be zero but got " + zeroResult);
        }
    }

    private static void testDivideByZero() {
        // Test division by zero triggers ArithmeticException
        BigInteger dividend = new BigInteger("123456789");
        BigInteger zeroDivisor = BigInteger.ZERO;
        try {
            dividend.divide(zeroDivisor);
            throw new AssertionError("Expected ArithmeticException for division by zero");
        } catch (ArithmeticException e) {
            // Expected exception
        }
    }
}
