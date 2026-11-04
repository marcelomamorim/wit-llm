/* @test    @summary Test for BigInteger.byteValueExact() method to verify exception throwing on out-of-range values    @run main BigIntegerWitupTest */

import java.math.BigInteger;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        testByteValueExactThrowsException();
        testByteValueExactWithinRange();
    }

    private static void testByteValueExactThrowsException() {
        // Create a BigInteger with magnitude length > 1 or bitLength() > 31 to trigger exception
        // Using a value that exceeds byte range
        BigInteger bigInt = new BigInteger("10000000000"); // large value
        try {
            bigInt.byteValueExact();
            System.err.println("Failed: Expected ArithmeticException was not thrown for large value.");
        } catch (ArithmeticException e) {
            // Expected exception
        }
        // Create a BigInteger with bitLength() > 31
        BigInteger largeBitLength = BigInteger.valueOf(1).shiftLeft(32); // bitLength() == 33
        try {
            largeBitLength.byteValueExact();
            System.err.println("Failed: Expected ArithmeticException was not thrown for bitLength > 31.");
        } catch (ArithmeticException e) {
            // Expected exception
        }
    }

    private static void testByteValueExactWithinRange() {
        // Create a BigInteger within byte range
        BigInteger withinRange = BigInteger.valueOf(127); // Byte.MAX_VALUE
        try {
            byte result = withinRange.byteValueExact();
            if (result != 127) {
                System.err.println("Failed: Unexpected byte value for within range.");
            }
        } catch (ArithmeticException e) {
            System.err.println("Failed: Unexpected exception for within range.");
        }
        // Negative within range
        BigInteger negativeWithinRange = BigInteger.valueOf(-128); // Byte.MIN_VALUE
        try {
            byte result = negativeWithinRange.byteValueExact();
            if (result != -128) {
                System.err.println("Failed: Unexpected byte value for negative within range.");
            }
        } catch (ArithmeticException e) {
            System.err.println("Failed: Unexpected exception for negative within range.");
        }
    }
}
