/*
 * @test
 * @summary Test java.math.BigInteger.testBit(int) method
 * @run main BigIntegerWitupTest
 */

import java.math.BigInteger;

public class BigIntegerWitupTest {
    public static void main(String[] args) {
        testBitPositiveIndex();
        testBitZeroIndex();
        testBitNegativeIndexThrows();
    }

    static void testBitPositiveIndex() {
        // BigInteger with bits 0 and 5 set (binary 100001 = 33 decimal)
        BigInteger bi = BigInteger.valueOf(33L);
        // bit 0 is set
        if (!bi.testBit(0)) {
            throw new AssertionError("bit 0 should be set");
        }
        // bit 5 is set
        if (!bi.testBit(5)) {
            throw new AssertionError("bit 5 should be set");
        }
        // bit 1 is not set
        if (bi.testBit(1)) {
            throw new AssertionError("bit 1 should not be set");
        }
        // bit 31 is not set
        if (bi.testBit(31)) {
            throw new AssertionError("bit 31 should not be set");
        }
    }

    static void testBitZeroIndex() {
        // BigInteger zero
        BigInteger zero = BigInteger.ZERO;
        if (zero.testBit(0)) {
            throw new AssertionError("bit 0 of zero should not be set");
        }
    }

    static void testBitNegativeIndexThrows() {
        BigInteger bi = BigInteger.ONE;
        try {
            bi.testBit(-1);
            throw new AssertionError("testBit(-1) should throw ArithmeticException");
        } catch (ArithmeticException e) {
            // expected
            if (!"Negative bit address".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
