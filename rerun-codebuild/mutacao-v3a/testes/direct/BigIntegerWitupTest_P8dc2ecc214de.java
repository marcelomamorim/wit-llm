/* @test    @summary Test for BigInteger.longValueExact() method behavior under different internal states    @run main BigIntegerWitupTest_P8dc2ecc214de */

import java.math.BigInteger;

public class BigIntegerWitupTest_P8dc2ecc214de {
    public static void main(String[] args) {
        testLongValueExactWithinRange();
        testLongValueExactOutOfRange();
    }

    static void testLongValueExactWithinRange() {
        BigInteger bigInt = BigInteger.valueOf(123456789L);
        long result = bigInt.longValueExact();
        if (result != 123456789L) {
            throw new AssertionError("Expected 123456789L, but got " + result);
        }
    }

    static void testLongValueExactOutOfRange() {
        // Construct a BigInteger with magnitude > Long.MAX_VALUE to trigger exception
        byte[] magnitude = new byte[] { (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0x7F }; // 2^63
        BigInteger bigInt = new BigInteger(1, magnitude);
        try {
            bigInt.longValueExact();
            throw new AssertionError("Expected ArithmeticException was not thrown");
        } catch (ArithmeticException e) {
            if (!"BigInteger out of long range".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
