/* @test    @summary Test BigInteger.setBit method for positive, zero, and negative cases    @run main BigIntegerWitupTest_Pa6a725af9c85 */

import java.math.BigInteger;

public class BigIntegerWitupTest_Pa6a725af9c85 {

    public static void main(String[] args) {
        testSetBitPositive();
        testSetBitZero();
        testSetBitNegative();
    }

    static void testSetBitPositive() {
        BigInteger bi = BigInteger.valueOf(0);
        BigInteger result = bi.setBit(0);
        if (!result.equals(BigInteger.ONE)) {
            throw new AssertionError("Failed: setBit(0) on 0 should be 1, but got " + result);
        }
        result = bi.setBit(31);
        BigInteger expected = BigInteger.ONE.shiftLeft(31);
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: setBit(31) on 0 should be " + expected + ", but got " + result);
        }
        result = BigInteger.valueOf(2).setBit(1);
        BigInteger expected2 = BigInteger.valueOf(2).or(BigInteger.ONE.shiftLeft(1));
        if (!result.equals(expected2)) {
            throw new AssertionError("Failed: setBit(1) on 2 should be " + expected2 + ", but got " + result);
        }
    }

    static void testSetBitZero() {
        BigInteger bi = BigInteger.ZERO;
        BigInteger result = bi.setBit(0);
        if (!result.equals(BigInteger.ONE)) {
            throw new AssertionError("Failed: setBit(0) on 0 should be 1, but got " + result);
        }
    }

    static void testSetBitNegative() {
        try {
            BigInteger bi = BigInteger.valueOf(0);
            bi.setBit(-1);
            throw new AssertionError("Expected ArithmeticException for negative bit index");
        } catch (ArithmeticException e) {
            // expected
        }
    }
}
