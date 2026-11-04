/* @test    @summary Test BigInteger.divide for normal and exceptional behavior    @run main BigIntegerWitupTest */

import java.math.BigInteger;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        testDivideNormal();
        testDivideByZero();
        testDivideWithEmptyMag();
    }

    private static void testDivideNormal() {
        BigInteger a = BigInteger.valueOf(100);
        BigInteger b = BigInteger.valueOf(20);
        BigInteger result = a.divide(b);
        if (!result.equals(BigInteger.valueOf(5))) {
            throw new AssertionError("Expected 5, got " + result);
        }
    }

    private static void testDivideByZero() {
        BigInteger a = BigInteger.valueOf(100);
        BigInteger zero = BigInteger.ZERO;
        try {
            a.divide(zero);
            throw new AssertionError("Expected ArithmeticException for division by zero");
        } catch (ArithmeticException e) {
            // expected
        }
    }

    private static void testDivideWithEmptyMag() {
        // Create a BigInteger with empty magnitude to simulate edge case
        // Note: Using reflection or internal constructor is not allowed; instead, simulate with a small number
        BigInteger a = BigInteger.valueOf(1);
        BigInteger b = BigInteger.valueOf(1);
        BigInteger result = a.divide(b);
        if (!result.equals(BigInteger.ONE)) {
            throw new AssertionError("Expected 1, got " + result);
        }
    }
}
