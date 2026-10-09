/*
 * @test
 * @summary Test java.math.BigInteger.setBit(int) method
 * @run main BigIntegerWitupTest_P101c03c843a3
 */

import java.math.BigInteger;

public class BigIntegerWitupTest_P101c03c843a3 {
    public static void main(String[] args) {
        testSetBitBasic();
        testSetBitZero();
        testSetBitNegativeIndex();
        testSetBitLargeIndex();
    }

    static void testSetBitBasic() {
        BigInteger bi = BigInteger.valueOf(0L);
        BigInteger result = bi.setBit(0);
        if (!result.equals(BigInteger.valueOf(1L))) {
            throw new RuntimeException("Failed: setBit(0) on 0 should be 1, got " + result);
        }

        bi = BigInteger.valueOf(1L);
        result = bi.setBit(1);
        if (!result.equals(BigInteger.valueOf(3L))) {
            throw new RuntimeException("Failed: setBit(1) on 1 should be 3, got " + result);
        }
    }

    static void testSetBitZero() {
        BigInteger bi = BigInteger.valueOf(123456789L);
        BigInteger result = bi.setBit(0);
        // Setting bit 0 should keep bit 0 set (idempotent)
        if (!result.testBit(0)) {
            throw new RuntimeException("Failed: bit 0 should be set after setBit(0)");
        }
    }

    static void testSetBitNegativeIndex() {
        BigInteger bi = BigInteger.valueOf(0L);
        try {
            bi.setBit(-1);
            throw new RuntimeException("Failed: setBit(-1) should throw ArithmeticException");
        } catch (ArithmeticException e) {
            // expected
        }
    }

    static void testSetBitLargeIndex() {
        BigInteger bi = BigInteger.valueOf(1L);
        int bitIndex = 100;
        BigInteger result = bi.setBit(bitIndex);
        if (!result.testBit(bitIndex)) {
            throw new RuntimeException("Failed: bit " + bitIndex + " should be set after setBit(" + bitIndex + ")");
        }
        // The original bit 0 should still be set
        if (!result.testBit(0)) {
            throw new RuntimeException("Failed: original bit 0 should remain set after setBit(" + bitIndex + ")");
        }
    }
}
