/* @test    @summary Test for BigInteger.byteValueExact() method to verify exception throwing on out-of-range values    @run main BigIntegerWitupTest_P2961ef3059d5 */

import java.math.BigInteger;

public class BigIntegerWitupTest_P2961ef3059d5 {

    public static void main(String[] args) {
        testByteValueExactThrowsOnLargeValue();
        testByteValueExactThrowsOnSmallValue();
        testByteValueExactReturnsCorrectValueWithinRange();
    }

    private static void testByteValueExactThrowsOnLargeValue() {
        try {
            // Create a BigInteger with magnitude length > 1 to trigger exception
            byte[] mag = {1, 2};
            BigInteger bi = new BigInteger(1, mag);
            // Force bitLength() > 31 by setting a large magnitude
            // Since constructor with signum and magnitude is used, magnitude length > 1 triggers exception
            bi.byteValueExact();
            throw new AssertionError("Expected ArithmeticException not thrown for large value");
        } catch (ArithmeticException e) {
            // Expected exception
        }
    }

    private static void testByteValueExactThrowsOnSmallValue() {
        try {
            // Create a BigInteger with magnitude length = 1 but value out of byte range
            byte[] mag = { (byte) 0x80 }; // 128 in unsigned, out of byte range
            BigInteger bi = new BigInteger(1, mag);
            bi.byteValueExact();
            throw new AssertionError("Expected ArithmeticException not thrown for out-of-range small value");
        } catch (ArithmeticException e) {
            // Expected exception
        }
    }

    private static void testByteValueExactReturnsCorrectValueWithinRange() {
        // Create a BigInteger within byte range
        BigInteger bi = BigInteger.valueOf(127);
        byte result = bi.byteValueExact();
        if (result != 127) {
            throw new AssertionError("Incorrect byte value for 127: " + result);
        }
        bi = BigInteger.valueOf(-128);
        result = bi.byteValueExact();
        if (result != -128) {
            throw new AssertionError("Incorrect byte value for -128: " + result);
        }
    }
}
