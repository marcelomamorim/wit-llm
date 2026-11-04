/* @test    @summary Test flipBit method of BigInteger for positive, zero, and negative bit positions    @run main BigIntegerWitupTest */

import java.math.BigInteger;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        testFlipBitPositive();
        testFlipBitZero();
        testFlipBitLarge();
        testFlipBitNegative();
    }

    static void testFlipBitPositive() {
        BigInteger bi = BigInteger.valueOf(0b1010); // 10
        BigInteger result = bi.flipBit(1); // flip second bit (0-based)
        BigInteger expected = BigInteger.valueOf(0b1010 ^ (1 << 1)); // 10 ^ 2 = 8
        if (!result.equals(expected)) {
            throw new AssertionError("flipBit positive test failed: expected " + expected + " but got " + result);
        }
    }

    static void testFlipBitZero() {
        BigInteger bi = BigInteger.valueOf(0b1001); // 9
        BigInteger result = bi.flipBit(0); // flip least significant bit
        BigInteger expected = BigInteger.valueOf(0b1001 ^ 1); // 9 ^ 1 = 8
        if (!result.equals(expected)) {
            throw new AssertionError("flipBit zero test failed: expected " + expected + " but got " + result);
        }
    }

    static void testFlipBitLarge() {
        BigInteger bi = new BigInteger("12345678901234567890");
        int bitPosition = 63; // flip a high bit
        BigInteger result = bi.flipBit(bitPosition);
        BigInteger expected = bi.setBit(bitPosition).flipBit(bitPosition); // flip the same bit again to get original
        // Since flipBit flips the bit, flipping twice should return original
        BigInteger doubleFlip = result.flipBit(bitPosition);
        if (!doubleFlip.equals(bi)) {
            throw new AssertionError("flipBit large bit test failed: expected " + bi + " but got " + doubleFlip);
        }
    }

    static void testFlipBitNegative() {
        BigInteger bi = BigInteger.valueOf(1);
        boolean exceptionThrown = false;
        try {
            bi.flipBit(-1);
        } catch (ArithmeticException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("flipBit negative index did not throw ArithmeticException");
        }
    }
}
