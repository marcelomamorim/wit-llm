/* @test    @summary Test for BigInteger.clearBit(int n) exception behavior    @run main BigIntegerWitupTest_Pdba7a0947ed2 */

import java.math.BigInteger;

public class BigIntegerWitupTest_Pdba7a0947ed2 {

    public static void main(String[] args) {
        testClearBitNegative();
        testClearBitZero();
        testClearBitPositive();
    }

    private static void testClearBitNegative() {
        try {
            BigInteger bi = BigInteger.valueOf(12345);
            bi.clearBit(-1);
            throw new AssertionError("Expected ArithmeticException for negative n");
        } catch (ArithmeticException e) {
            if (!"Negative bit address".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
        }
    }

    private static void testClearBitZero() {
        try {
            BigInteger bi = BigInteger.valueOf(12345);
            BigInteger result = bi.clearBit(0);
            if (!result.equals(bi)) {
                throw new AssertionError("clearBit(0) should not change the BigInteger");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for clearBit(0)", e);
        }
    }

    private static void testClearBitPositive() {
        try {
            BigInteger bi = new BigInteger("1024"); // 2^10
            BigInteger result = bi.clearBit(10);
            BigInteger expected = BigInteger.valueOf(0); // clearing bit 10 (which is set)
            if (!result.equals(expected)) {
                throw new AssertionError("clearBit(10) did not clear the 10th bit as expected");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for clearBit(10)", e);
        }
    }
}
