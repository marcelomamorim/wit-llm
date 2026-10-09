/* @test    @summary Test for BigInteger.intValueExact() method to verify exception throwing on out-of-range values    @run main BigIntegerWitupTest_P9c98b4b7e73d */

import java.math.BigInteger;

public class BigIntegerWitupTest_P9c98b4b7e73d {

    public static void main(String[] args) {
        testIntValueExactWithinRange();
        testIntValueExactOutOfRange();
    }

    private static void testIntValueExactWithinRange() {
        // Create a BigInteger with value within int range
        BigInteger bi = BigInteger.valueOf(12345L);
        int result = bi.intValueExact();
        if (result != 12345) {
            throw new AssertionError("Expected 12345, but got " + result);
        }
    }

    private static void testIntValueExactOutOfRange() {
        // Create a BigInteger with value outside int range
        // Using a value greater than Integer.MAX_VALUE
        BigInteger bi = new BigInteger("2147483648"); // Integer.MAX_VALUE + 1
        boolean threw = false;
        try {
            bi.intValueExact();
        } catch (ArithmeticException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected ArithmeticException for out-of-range value");
        }
        // Also test for value less than Integer.MIN_VALUE
        BigInteger biNeg = new BigInteger("-2147483649"); // Integer.MIN_VALUE - 1
        threw = false;
        try {
            biNeg.intValueExact();
        } catch (ArithmeticException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected ArithmeticException for out-of-range negative value");
        }
    }
}
