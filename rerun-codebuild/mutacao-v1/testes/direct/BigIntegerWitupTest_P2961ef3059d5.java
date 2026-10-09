/* @test    @summary Test byteValueExact() method of BigInteger for values within byte range    @run main BigIntegerWitupTest_P2961ef3059d5 */

import java.math.BigInteger;

public class BigIntegerWitupTest_P2961ef3059d5 {
    public static void main(String[] args) {
        testWithinByteRange();
        testOutsideByteRange();
    }

    static void testWithinByteRange() {
        // Test with value within byte range (-128 to 127)
        BigInteger biMin = BigInteger.valueOf(Byte.MIN_VALUE);
        BigInteger biMax = BigInteger.valueOf(Byte.MAX_VALUE);
        try {
            byte bMin = biMin.byteValueExact();
            byte bMax = biMax.byteValueExact();
            if (bMin != Byte.MIN_VALUE || bMax != Byte.MAX_VALUE) {
                throw new AssertionError("byteValueExact() returned incorrect value within range");
            }
        } catch (ArithmeticException e) {
            throw new AssertionError("Unexpected exception for value within byte range", e);
        }
    }

    static void testOutsideByteRange() {
        // Test with value outside byte range (e.g., 128)
        BigInteger biAbove = BigInteger.valueOf(128);
        BigInteger biBelow = BigInteger.valueOf(-129);
        boolean exceptionThrownAbove = false;
        boolean exceptionThrownBelow = false;
        try {
            biAbove.byteValueExact();
        } catch (ArithmeticException e) {
            exceptionThrownAbove = true;
        }
        try {
            biBelow.byteValueExact();
        } catch (ArithmeticException e) {
            exceptionThrownBelow = true;
        }
        if (!exceptionThrownAbove) {
            throw new AssertionError("Expected ArithmeticException for value 128");
        }
        if (!exceptionThrownBelow) {
            throw new AssertionError("Expected ArithmeticException for value -129");
        }
    }
}
