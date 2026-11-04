/*
 * @test
 * @summary Test BigInteger.intValueExact() for in-range and out-of-range values
 * @run main BigIntegerWitupTest
 */

import java.math.BigInteger;

public class BigIntegerWitupTest {
    public static void main(String[] args) {
        testIntValueExactInRange();
        testIntValueExactOutOfRange();
    }

    private static void testIntValueExactInRange() {
        // Test with a value that fits in int exactly
        BigInteger bi = BigInteger.valueOf(Integer.MAX_VALUE);
        int val = bi.intValueExact();
        if (val != Integer.MAX_VALUE) {
            throw new AssertionError("intValueExact() returned " + val + " but expected " + Integer.MAX_VALUE);
        }

        bi = BigInteger.valueOf(Integer.MIN_VALUE);
        val = bi.intValueExact();
        if (val != Integer.MIN_VALUE) {
            throw new AssertionError("intValueExact() returned " + val + " but expected " + Integer.MIN_VALUE);
        }

        bi = BigInteger.ZERO;
        val = bi.intValueExact();
        if (val != 0) {
            throw new AssertionError("intValueExact() returned " + val + " but expected 0");
        }
    }

    private static void testIntValueExactOutOfRange() {
        // Test with a value just outside int range (Integer.MAX_VALUE + 1)
        BigInteger bi = BigInteger.valueOf((long)Integer.MAX_VALUE).add(BigInteger.ONE);
        try {
            bi.intValueExact();
            throw new AssertionError("Expected ArithmeticException for value > Integer.MAX_VALUE");
        } catch (ArithmeticException e) {
            // Expected
        }

        // Test with a value just outside int range (Integer.MIN_VALUE - 1)
        bi = BigInteger.valueOf((long)Integer.MIN_VALUE).subtract(BigInteger.ONE);
        try {
            bi.intValueExact();
            throw new AssertionError("Expected ArithmeticException for value < Integer.MIN_VALUE");
        } catch (ArithmeticException e) {
            // Expected
        }

        // Test with a large positive BigInteger (more than 1 mag element)
        byte[] mag = new byte[] {1, 0}; // magnitude length > 1
        bi = new BigInteger(1, mag);
        try {
            bi.intValueExact();
            throw new AssertionError("Expected ArithmeticException for magnitude length > 1");
        } catch (ArithmeticException e) {
            // Expected
        }
    }
}
