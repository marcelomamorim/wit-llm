/* @test    @summary Test BigInteger.testBit method for various cases    @run main BigIntegerWitupTest */

import java.math.BigInteger;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        testTestBitWithPositiveBit();
        testTestBitWithZeroBit();
        testTestBitWithNegativeIndex();
        testTestBitWithLargeIndex();
    }

    static void testTestBitWithPositiveBit() {
        BigInteger bi = BigInteger.valueOf(0b10101); // 21 in decimal
        boolean result = bi.testBit(0); // least significant bit
        assert result : "Expected true for bit 0";
        result = bi.testBit(2); // third bit from right
        assert result : "Expected true for bit 2";
        result = bi.testBit(4); // fifth bit from right
        assert result : "Expected true for bit 4";
        result = bi.testBit(1); // second bit
        assert !result : "Expected false for bit 1";
    }

    static void testTestBitWithZeroBit() {
        BigInteger bi = BigInteger.ZERO;
        boolean result = bi.testBit(0);
        assert !result : "Expected false for bit 0 in zero";
    }

    static void testTestBitWithNegativeIndex() {
        BigInteger bi = BigInteger.ONE;
        boolean exceptionThrown = false;
        try {
            bi.testBit(-1);
        } catch (ArithmeticException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected ArithmeticException for negative index";
    }

    static void testTestBitWithLargeIndex() {
        BigInteger bi = BigInteger.valueOf(1L << 63); // bit at position 63 set
        boolean result = bi.testBit(63);
        assert result : "Expected true for bit 63";
        result = bi.testBit(62);
        assert !result : "Expected false for bit 62";
    }
}
