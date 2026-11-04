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
            throw new RuntimeException("intValueExact() returned wrong value for Integer.MAX_VALUE");
        }

        bi = BigInteger.valueOf(Integer.MIN_VALUE);
        val = bi.intValueExact();
        if (val != Integer.MIN_VALUE) {
            throw new RuntimeException("intValueExact() returned wrong value for Integer.MIN_VALUE");
        }

        bi = BigInteger.valueOf(0);
        val = bi.intValueExact();
        if (val != 0) {
            throw new RuntimeException("intValueExact() returned wrong value for zero");
        }
    }

    private static void testIntValueExactOutOfRange() {
        // Test with a value just outside int range (Integer.MAX_VALUE + 1)
        BigInteger bi = BigInteger.valueOf((long)Integer.MAX_VALUE + 1);
        try {
            bi.intValueExact();
            throw new RuntimeException("intValueExact() did not throw ArithmeticException for value > Integer.MAX_VALUE");
        } catch (ArithmeticException e) {
            // expected
        }

        // Test with a value just outside int range (Integer.MIN_VALUE - 1)
        bi = BigInteger.valueOf((long)Integer.MIN_VALUE - 1);
        try {
            bi.intValueExact();
            throw new RuntimeException("intValueExact() did not throw ArithmeticException for value < Integer.MIN_VALUE");
        } catch (ArithmeticException e) {
            // expected
        }

        // Test with a large positive BigInteger
        bi = BigInteger.ONE.shiftLeft(40); // 2^40 > Integer.MAX_VALUE
        try {
            bi.intValueExact();
            throw new RuntimeException("intValueExact() did not throw ArithmeticException for large positive value");
        } catch (ArithmeticException e) {
            // expected
        }

        // Test with a large negative BigInteger
        bi = BigInteger.ONE.shiftLeft(40).negate(); // -2^40 < Integer.MIN_VALUE
        try {
            bi.intValueExact();
            throw new RuntimeException("intValueExact() did not throw ArithmeticException for large negative value");
        } catch (ArithmeticException e) {
            // expected
        }
    }
}
