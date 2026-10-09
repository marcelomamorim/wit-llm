/* @test    @summary Test setBit method for positive and negative inputs    @run main BigIntegerWitupTest_Pbb696ba2c7e8 */

import java.math.BigInteger;

public class BigIntegerWitupTest_Pbb696ba2c7e8 {

    public static void main(String[] args) {
        testSetBitWithPositiveInput();
        testSetBitWithZeroInput();
        testSetBitWithNegativeInput();
    }

    private static void testSetBitWithPositiveInput() {
        BigInteger bi = BigInteger.valueOf(0);
        BigInteger result = bi.setBit(5);
        BigInteger expected = BigInteger.valueOf(1).shiftLeft(5);
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: setBit with positive input");
        }
    }

    private static void testSetBitWithZeroInput() {
        BigInteger bi = BigInteger.valueOf(0);
        BigInteger result = bi.setBit(0);
        BigInteger expected = BigInteger.valueOf(1);
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: setBit with zero input");
        }
    }

    private static void testSetBitWithNegativeInput() {
        try {
            BigInteger bi = BigInteger.valueOf(0);
            bi.setBit(-1);
            throw new AssertionError("Expected ArithmeticException for negative input");
        } catch (ArithmeticException e) {
            if (!"Negative bit address".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message", e);
            }
        }
    }
}
