/* @test    @summary Test for BigInteger.longValueExact() method behavior.    @run main BigIntegerWitupTest_P8a6bbc8084a9 */

import java.math.BigInteger;

public class BigIntegerWitupTest_P8a6bbc8084a9 {
    public static void main(String[] args) {
        testWithinLongRange();
        testOutOfLongRange();
    }

    static void testWithinLongRange() {
        try {
            BigInteger bi = BigInteger.valueOf(Long.MAX_VALUE);
            long result = bi.longValueExact();
            if (result != Long.MAX_VALUE) {
                throw new AssertionError("Expected Long.MAX_VALUE, got " + result);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for Long.MAX_VALUE: " + e);
        }
        try {
            BigInteger bi = BigInteger.valueOf(Long.MIN_VALUE);
            long result = bi.longValueExact();
            if (result != Long.MIN_VALUE) {
                throw new AssertionError("Expected Long.MIN_VALUE, got " + result);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for Long.MIN_VALUE: " + e);
        }
    }

    static void testOutOfLongRange() {
        try {
            // Create a BigInteger greater than Long.MAX_VALUE
            BigInteger bi = BigInteger.valueOf(Long.MAX_VALUE).add(BigInteger.ONE);
            bi.longValueExact();
            throw new AssertionError("Expected ArithmeticException for value > Long.MAX_VALUE");
        } catch (ArithmeticException e) {
            // Expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
        try {
            // Create a BigInteger less than Long.MIN_VALUE
            BigInteger bi = BigInteger.valueOf(Long.MIN_VALUE).subtract(BigInteger.ONE);
            bi.longValueExact();
            throw new AssertionError("Expected ArithmeticException for value < Long.MIN_VALUE");
        } catch (ArithmeticException e) {
            // Expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
