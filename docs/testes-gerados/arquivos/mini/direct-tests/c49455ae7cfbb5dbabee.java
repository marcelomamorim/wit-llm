/*
 * @test
 * @summary Test BigInteger.byteValueExact() for correct exact byte conversion and exception
 * @run main java.math.BigIntegerWitupTest
 */

import java.math.BigInteger;

public class BigIntegerWitupTest {
    public static void main(String[] args) {
        testByteValueExactInRange();
        testByteValueExactOutOfRangePositive();
        testByteValueExactOutOfRangeNegative();
    }

    private static void testByteValueExactInRange() {
        // Test values within byte range [-128..127]
        for (int i = -128; i <= 127; i++) {
            BigInteger bi = BigInteger.valueOf(i);
            byte b = bi.byteValueExact();
            if (b != (byte) i) {
                throw new AssertionError("byteValueExact() returned " + b + " for " + i);
            }
        }
    }

    private static void testByteValueExactOutOfRangePositive() {
        // Test values just outside byte max value
        BigInteger bi = BigInteger.valueOf(128L);
        try {
            bi.byteValueExact();
            throw new AssertionError("Expected ArithmeticException for value 128");
        } catch (ArithmeticException e) {
            // expected
        }

        // Test a larger positive value
        bi = BigInteger.valueOf(1000L);
        try {
            bi.byteValueExact();
            throw new AssertionError("Expected ArithmeticException for value 1000");
        } catch (ArithmeticException e) {
            // expected
        }
    }

    private static void testByteValueExactOutOfRangeNegative() {
        // Test values just outside byte min value
        BigInteger bi = BigInteger.valueOf(-129L);
        try {
            bi.byteValueExact();
            throw new AssertionError("Expected ArithmeticException for value -129");
        } catch (ArithmeticException e) {
            // expected
        }

        // Test a larger negative value
        bi = BigInteger.valueOf(-1000L);
        try {
            bi.byteValueExact();
            throw new AssertionError("Expected ArithmeticException for value -1000");
        } catch (ArithmeticException e) {
            // expected
        }
    }
}
