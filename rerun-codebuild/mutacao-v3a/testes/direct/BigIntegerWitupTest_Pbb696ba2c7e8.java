/* @test    @summary Test BigInteger.setBit for positive index and exception on negative index    @run main BigIntegerWitupTest_Pbb696ba2c7e8 */

import java.math.BigInteger;

public class BigIntegerWitupTest_Pbb696ba2c7e8 {

    public static void main(String[] args) {
        testSetBitPositiveIndex();
        testSetBitNegativeIndex();
    }

    private static void testSetBitPositiveIndex() {
        BigInteger original = BigInteger.valueOf(0);
        int index = 3;
        BigInteger result = original.setBit(index);
        // The bit at position 3 (zero-based) should now be 1
        BigInteger expected = BigInteger.valueOf(8); // 2^3
        if (!result.equals(expected)) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }

    private static void testSetBitNegativeIndex() {
        BigInteger original = BigInteger.valueOf(0);
        int negativeIndex = -1;
        boolean exceptionThrown = false;
        try {
            original.setBit(negativeIndex);
        } catch (ArithmeticException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected ArithmeticException for negative index");
        }
    }
}
