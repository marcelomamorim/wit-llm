/*
 * @test
 * @summary Test java.math.BigInteger.flipBit(int) method
 * @run main BigIntegerWitupTest_P31d289fad94d
 */

import java.math.BigInteger;

public class BigIntegerWitupTest_P31d289fad94d {
    public static void main(String[] args) {
        testFlipBitBasic();
        testFlipBitZero();
        testFlipBitHighBit();
        testFlipBitNegativeIndex();
    }

    static void testFlipBitBasic() {
        BigInteger bi = BigInteger.valueOf(0L);
        BigInteger flipped = bi.flipBit(0);
        // flipping bit 0 of 0 should set bit 0 to 1
        if (!flipped.equals(BigInteger.ONE)) {
            throw new RuntimeException("flipBit(0) on 0 did not produce 1");
        }

        BigInteger bi2 = BigInteger.valueOf(1L);
        BigInteger flipped2 = bi2.flipBit(0);
        // flipping bit 0 of 1 should clear bit 0, result 0
        if (!flipped2.equals(BigInteger.ZERO)) {
            throw new RuntimeException("flipBit(0) on 1 did not produce 0");
        }
    }

    static void testFlipBitZero() {
        BigInteger bi = BigInteger.ZERO;
        BigInteger flipped = bi.flipBit(0);
        if (!flipped.equals(BigInteger.ONE)) {
            throw new RuntimeException("flipBit(0) on ZERO did not produce ONE");
        }
    }

    static void testFlipBitHighBit() {
        // Flip a high bit (e.g. bit 100) on zero
        BigInteger bi = BigInteger.ZERO;
        BigInteger flipped = bi.flipBit(100);
        // The result should have bit 100 set
        if (!flipped.testBit(100)) {
            throw new RuntimeException("flipBit(100) did not set bit 100");
        }
        // Flipping again should clear bit 100
        BigInteger flippedAgain = flipped.flipBit(100);
        if (!flippedAgain.equals(BigInteger.ZERO)) {
            throw new RuntimeException("flipBit(100) twice did not return to zero");
        }
    }

    static void testFlipBitNegativeIndex() {
        try {
            BigInteger.ZERO.flipBit(-1);
            throw new RuntimeException("flipBit(-1) did not throw ArithmeticException");
        } catch (ArithmeticException e) {
            // expected
        }
    }
}
