/* @test    @summary Test BigInteger.clearBit(int n) for various cases    @run main BigIntegerWitupTest_Pdba7a0947ed2 */

import java.math.BigInteger;

public class BigIntegerWitupTest_Pdba7a0947ed2 {

    public static void main(String[] args) {
        testClearBitPositive();
        testClearBitZero();
        testClearBitNegative();
        testClearBitLarge();
        testClearBitNegativeIndex();
    }

    static void testClearBitPositive() {
        BigInteger bi = BigInteger.valueOf(0b1111); // 15
        BigInteger result = bi.clearBit(2); // clear 3rd bit (zero-based)
        BigInteger expected = BigInteger.valueOf(0b1011); // 11
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: clearBit(2) on 15, got " + result);
        }
    }

    static void testClearBitZero() {
        BigInteger bi = BigInteger.valueOf(0b1010); // 10
        BigInteger result = bi.clearBit(0); // clear 1st bit
        BigInteger expected = BigInteger.valueOf(0b1010); // 10, bit 0 is already 0
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: clearBit(0) on 10, got " + result);
        }
    }

    static void testClearBitNegative() {
        try {
            BigInteger bi = BigInteger.valueOf(1);
            bi.clearBit(-1);
            throw new AssertionError("Expected ArithmeticException for negative index");
        } catch (ArithmeticException e) {
            // expected
        }
    }

    static void testClearBitLarge() {
        BigInteger bi = BigInteger.valueOf(1).shiftLeft(100); // 2^100
        BigInteger result = bi.clearBit(100); // clear the only set bit
        BigInteger expected = BigInteger.ZERO;
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: clearBit(100) on 2^100, got " + result);
        }
    }

    static void testClearBitNegativeIndex() {
        try {
            BigInteger bi = BigInteger.valueOf(12345);
            bi.clearBit(-10);
            throw new AssertionError("Expected ArithmeticException for negative index");
        } catch (ArithmeticException e) {
            // expected
        }
    }
}
