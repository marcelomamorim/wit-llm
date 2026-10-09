/* @test    @summary Test BigInteger.flipBit(int n) for various cases    @run main BigIntegerWitupTest_Pcf9ad0b9c1d4 */

import java.math.BigInteger;

public class BigIntegerWitupTest_Pcf9ad0b9c1d4 {

    public static void main(String[] args) {
        testFlipBitPositiveIndex();
        testFlipBitZeroIndex();
        testFlipBitLargeIndex();
        testFlipBitNegativeIndex();
        testFlipBitNegativeIndexThrows();
    }

    static void testFlipBitPositiveIndex() {
        BigInteger bi = BigInteger.valueOf(0b1010); // 10
        BigInteger result = bi.flipBit(1); // flip second bit (index 1)
        BigInteger expected = BigInteger.valueOf(0b1010 ^ (1 << 1)); // 10 ^ 2 = 8
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: flipBit positive index. Expected " + expected + " but got " + result);
        }
    }

    static void testFlipBitZeroIndex() {
        BigInteger bi = BigInteger.valueOf(0b1000); // 8
        BigInteger result = bi.flipBit(0); // flip least significant bit
        BigInteger expected = BigInteger.valueOf(0b1000 ^ 1); // 8 ^ 1 = 9
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: flipBit zero index. Expected " + expected + " but got " + result);
        }
    }

    static void testFlipBitLargeIndex() {
        BigInteger bi = BigInteger.valueOf(1); // 1
        int largeIndex = 100;
        BigInteger result = bi.flipBit(largeIndex); // flip bit at position 100
        BigInteger expected = BigInteger.valueOf(1).xor(BigInteger.ONE.shiftLeft(largeIndex));
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: flipBit large index. Expected " + expected + " but got " + result);
        }
    }

    static void testFlipBitNegativeIndex() {
        BigInteger bi = BigInteger.valueOf(0);
        try {
            bi.flipBit(-1);
            throw new AssertionError("Expected ArithmeticException for negative index");
        } catch (ArithmeticException e) {
            // expected
        }
    }

    static void testFlipBitNegativeIndexThrows() {
        BigInteger bi = BigInteger.valueOf(0);
        boolean thrown = false;
        try {
            bi.flipBit(-10);
        } catch (ArithmeticException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected ArithmeticException for negative index");
        }
    }
}
