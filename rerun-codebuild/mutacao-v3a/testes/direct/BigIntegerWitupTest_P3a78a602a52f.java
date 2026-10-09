/* @test    @summary Test BigInteger.flipBit(int n) method for positive, zero, and invalid input.    @run main BigIntegerWitupTest_P3a78a602a52f */

import java.math.BigInteger;

public class BigIntegerWitupTest_P3a78a602a52f {

    public static void main(String[] args) {
        testFlipBitWithValidInput();
        testFlipBitWithZero();
        testFlipBitWithNegativeInput();
    }

    private static void testFlipBitWithValidInput() {
        BigInteger bi = BigInteger.valueOf(0b1010); // 10
        BigInteger result = bi.flipBit(2); // flip 3rd bit (zero-based)
        BigInteger expected = BigInteger.valueOf(0b1110); // 14
        if (!result.equals(expected)) {
            throw new AssertionError("flipBit(2) failed: expected " + expected + " but got " + result);
        }
    }

    private static void testFlipBitWithZero() {
        BigInteger bi = BigInteger.ZERO;
        BigInteger result = bi.flipBit(0); // flip least significant bit
        BigInteger expected = BigInteger.ONE; // 1
        if (!result.equals(expected)) {
            throw new AssertionError("flipBit(0) on ZERO failed: expected " + expected + " but got " + result);
        }
    }

    private static void testFlipBitWithNegativeInput() {
        BigInteger bi = BigInteger.valueOf(1);
        boolean exceptionThrown = false;
        try {
            bi.flipBit(-1);
        } catch (ArithmeticException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected ArithmeticException for negative input") ;
        }
    }
}
