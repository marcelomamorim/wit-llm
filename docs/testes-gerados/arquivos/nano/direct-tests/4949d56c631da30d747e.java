/* @test    @summary Test BigInteger.setBit method for positive bit index and exception for negative index    @run main BigIntegerWitupTest */

import java.math.BigInteger;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        testSetBitPositiveIndex();
        testSetBitZeroIndex();
        testSetBitLargeIndex();
        testSetBitNegativeIndex();
    }

    static void testSetBitPositiveIndex() {
        BigInteger original = BigInteger.valueOf(0b1010); // 10
        int bitIndex = 3; // zero-based, corresponds to the 4th bit
        BigInteger result = original.setBit(bitIndex);
        BigInteger expected = BigInteger.valueOf(0b1010 | (1 << bitIndex)); // 10 | 8 = 10
        assert result.equals(expected) : "Failed: setBit with positive index";
    }

    static void testSetBitZeroIndex() {
        BigInteger original = BigInteger.valueOf(0b1000); // 8
        int bitIndex = 0; // least significant bit
        BigInteger result = original.setBit(bitIndex);
        BigInteger expected = BigInteger.valueOf(0b1001); // 8 | 1 = 9
        assert result.equals(expected) : "Failed: setBit with zero index";
    }

    static void testSetBitLargeIndex() {
        BigInteger original = BigInteger.valueOf(1); // 1
        int bitIndex = 100; // large index
        BigInteger result = original.setBit(bitIndex);
        BigInteger expected = BigInteger.valueOf(1).setBit(bitIndex);
        assert result.equals(expected) : "Failed: setBit with large index";
    }

    static void testSetBitNegativeIndex() {
        BigInteger original = BigInteger.valueOf(1);
        int bitIndex = -1;
        boolean exceptionThrown = false;
        try {
            original.setBit(bitIndex);
        } catch (ArithmeticException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Failed: setBit should throw ArithmeticException for negative index";
    }
}
