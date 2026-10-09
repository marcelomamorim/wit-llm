/* @test    @summary Test for BigInteger.intValueExact() method behavior.    @run main BigIntegerWitupTest_P9c98b4b7e73d */

import java.math.BigInteger;

public class BigIntegerWitupTest_P9c98b4b7e73d {
    public static void main(String[] args) {
        testWithinIntRange();
        testOutsideIntRange();
        testNegativeBigInteger();
        testZero();
        testSmallPositive();
        System.out.println("All tests passed.");
    }

    static void testWithinIntRange() {
        BigInteger bi = BigInteger.valueOf(12345L);
        int result = bi.intValueExact();
        if (result != 12345) {
            throw new AssertionError("Expected 12345, got " + result);
        }
    }

    static void testOutsideIntRange() {
        // Create a BigInteger with length > 1 and bitLength() > 31
        byte[] largeBytes = { (byte)0x80, 0x00, 0x00, 0x00 };
        BigInteger bi = new BigInteger(largeBytes);
        if (bi.bitLength() <= 31) {
            // Adjust to ensure bitLength > 31
            bi = bi.shiftLeft(1);
        }
        boolean exceptionThrown = false;
        try {
            bi.intValueExact();
        } catch (ArithmeticException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected ArithmeticException for out of int range");
        }
    }

    static void testNegativeBigInteger() {
        BigInteger bi = BigInteger.valueOf(-12345L);
        int result = bi.intValueExact();
        if (result != -12345) {
            throw new AssertionError("Expected -12345, got " + result);
        }
    }

    static void testZero() {
        BigInteger bi = BigInteger.ZERO;
        int result = bi.intValueExact();
        if (result != 0) {
            throw new AssertionError("Expected 0, got " + result);
        }
    }

    static void testSmallPositive() {
        BigInteger bi = BigInteger.valueOf(0x7FFFFFFFL); // max int value
        int result = bi.intValueExact();
        if (result != 0x7FFFFFFF) {
            throw new AssertionError("Expected 2147483647, got " + result);
        }
    }
}
