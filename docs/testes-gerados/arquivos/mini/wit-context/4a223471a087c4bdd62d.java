/*
 * @test
 * @summary Test BigInteger.longValueExact() for correct behavior and exception
 * @run main BigIntegerWitupTest
 */

import java.math.BigInteger;

public class BigIntegerWitupTest {
    public static void main(String[] args) {
        testLongValueExactWithinRange();
        testLongValueExactOutOfRange();
    }

    private static void testLongValueExactWithinRange() {
        // Test with a BigInteger that fits in a long exactly
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

        // Test zero
        bi = BigInteger.ZERO;
        val = bi.longValueExact();
        if (val != 0L) {
            throw new AssertionError("Expected 0, got " + val);
        }
    }

    private static void testLongValueExactOutOfRange() {
        // Create a BigInteger just outside the long range (Long.MAX_VALUE + 1)
        byte[] mag = new byte[] { (byte)0x80, 0, 0, 0, 0, 0, 0, 0, 0 }; // 2^63
        BigInteger bi = new BigInteger(1, mag);

        try {
            bi.longValueExact();
            throw new AssertionError("Expected ArithmeticException for value out of long range");
        } catch (ArithmeticException e) {
            // Expected
        }

        // Also test a negative value less than Long.MIN_VALUE
        byte[] negMag = new byte[] { (byte)0x80, 0, 0, 0, 0, 0, 0, 0, 0 }; // magnitude 2^63
        BigInteger biNeg = new BigInteger(-1, negMag);

        try {
            biNeg.longValueExact();
            throw new AssertionError("Expected ArithmeticException for negative value out of long range");
        } catch (ArithmeticException e) {
            // Expected
        }
    }
}
