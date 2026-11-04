/*
 * @test
 * @summary Test java.math.BigInteger.clearBit(int) method
 * @run main java.math.BigIntegerWitupTest
 */

import java.math.BigInteger;

public class BigIntegerWitupTest {
    public static void main(String[] args) {
        testClearBitBasic();
        testClearBitZeroBit();
        testClearBitHighBit();
        testClearBitNegativeIndex();
    }

    static void testClearBitBasic() {
        BigInteger bi = BigInteger.valueOf(0b1111); // 15 decimal
        BigInteger cleared = bi.clearBit(1); // clear bit 1 (second bit from right)
        // 0b1111 = 15, clearing bit 1 (value 2) => 13 (0b1101)
        if (!cleared.equals(BigInteger.valueOf(13))) {
            throw new RuntimeException("testClearBitBasic failed: expected 13 but got " + cleared);
        }
    }

    static void testClearBitZeroBit() {
        BigInteger bi = BigInteger.valueOf(1); // 0b1
        BigInteger cleared = bi.clearBit(0); // clear bit 0
        // clearing lowest bit of 1 should yield 0
        if (!cleared.equals(BigInteger.ZERO)) {
            throw new RuntimeException("testClearBitZeroBit failed: expected 0 but got " + cleared);
        }
    }

    static void testClearBitHighBit() {
        // Test clearing a bit beyond current length extends the array
        BigInteger bi = BigInteger.valueOf(1); // 0b1
        int bitToClear = 100; // high bit
        BigInteger cleared = bi.clearBit(bitToClear);
        // clearing a bit that is not set should not change value
        if (!cleared.equals(bi)) {
            throw new RuntimeException("testClearBitHighBit failed: expected " + bi + " but got " + cleared);
        }
    }

    static void testClearBitNegativeIndex() {
        try {
            BigInteger.ZERO.clearBit(-1);
            throw new RuntimeException("testClearBitNegativeIndex failed: expected ArithmeticException");
        } catch (ArithmeticException e) {
            // expected
            if (!"Negative bit address".equals(e.getMessage())) {
                throw new RuntimeException("testClearBitNegativeIndex failed: wrong exception message: " + e.getMessage());
            }
        }
    }
}
