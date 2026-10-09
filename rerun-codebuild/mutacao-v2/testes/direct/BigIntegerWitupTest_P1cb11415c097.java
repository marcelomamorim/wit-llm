/*
 * @test
 * @summary Test BigInteger.longValueExact() for in-range and out-of-range values
 * @run main BigIntegerWitupTest_P1cb11415c097
 */

import java.math.BigInteger;

public class BigIntegerWitupTest_P1cb11415c097 {
    public static void main(String[] args) {
        testLongValueExactInRange();
        testLongValueExactOutOfRange();
    }

    private static void testLongValueExactInRange() {
        // Test with a value that fits in a long exactly
        BigInteger bi = BigInteger.valueOf(Long.MAX_VALUE);
        long val = bi.longValueExact();
        if (val != Long.MAX_VALUE) {
            throw new AssertionError("Expected Long.MAX_VALUE, got " + val);
        }

        bi = BigInteger.valueOf(Long.MIN_VALUE);
        val = bi.longValueExact();
        if (val != Long.MIN_VALUE) {
            throw new AssertionError("Expected Long.MIN_VALUE, got " + val);
        }

        // Test with a small positive value
        bi = BigInteger.valueOf(123456789L);
        val = bi.longValueExact();
        if (val != 123456789L) {
            throw new AssertionError("Expected 123456789, got " + val);
        }

        // Test with zero
        bi = BigInteger.ZERO;
        val = bi.longValueExact();
        if (val != 0L) {
            throw new AssertionError("Expected 0, got " + val);
        }
    }

    private static void testLongValueExactOutOfRange() {
        // Test with a value just above Long.MAX_VALUE
        BigInteger bi = BigInteger.valueOf(Long.MAX_VALUE).add(BigInteger.ONE);
        try {
            bi.longValueExact();
            throw new AssertionError("Expected ArithmeticException for value above Long.MAX_VALUE");
        } catch (ArithmeticException e) {
            // Expected
        }

        // Test with a value just below Long.MIN_VALUE
        bi = BigInteger.valueOf(Long.MIN_VALUE).subtract(BigInteger.ONE);
        try {
            bi.longValueExact();
            throw new AssertionError("Expected ArithmeticException for value below Long.MIN_VALUE");
        } catch (ArithmeticException e) {
            // Expected
        }

        // Test with a very large positive value
        bi = BigInteger.TEN.pow(30);
        try {
            bi.longValueExact();
            throw new AssertionError("Expected ArithmeticException for very large positive value");
        } catch (ArithmeticException e) {
            // Expected
        }

        // Test with a very large negative value
        bi = BigInteger.TEN.pow(30).negate();
        try {
            bi.longValueExact();
            throw new AssertionError("Expected ArithmeticException for very large negative value");
        } catch (ArithmeticException e) {
            // Expected
        }
    }
}
