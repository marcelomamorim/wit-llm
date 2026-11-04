/* @test    @summary Test for BigInteger.intValueExact() method behavior.    @run main BigIntegerWitupTest */

import java.math.BigInteger;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        testWithinIntRange();
        testOutsideIntRange();
    }

    static void testWithinIntRange() {
        BigInteger bi = BigInteger.valueOf(12345L);
        int result = bi.intValueExact();
        if (result != 12345) {
            throw new AssertionError("Expected 12345, but got " + result);
        }
    }

    static void testOutsideIntRange() {
        // Create a BigInteger with more than 31 bits to trigger exception
        byte[] largeBytes = {0x7F, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF};
        BigInteger bi = new BigInteger(largeBytes);
        boolean exceptionThrown = false;
        try {
            bi.intValueExact();
        } catch (ArithmeticException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected ArithmeticException for out of range value");
        }
    }
}
