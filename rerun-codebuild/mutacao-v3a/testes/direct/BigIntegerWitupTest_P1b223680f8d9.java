/* @test    @summary Test clearBit method of BigInteger for valid and invalid inputs    @run main BigIntegerWitupTest_P1b223680f8d9 */

import java.math.BigInteger;

public class BigIntegerWitupTest_P1b223680f8d9 {

    public static void main(String[] args) {
        testClearBitWithValidInput();
        testClearBitWithNegativeBit();
        testClearBitWithZeroBit();
        testClearBitWithLargeBit();
    }

    static void testClearBitWithValidInput() {
        BigInteger bi = new BigInteger("15"); // binary 1111
        BigInteger result = bi.clearBit(1); // clear bit at position 1 (zero-based)
        assert result.equals(new BigInteger("13")) : "Expected 13, got " + result;
    }

    static void testClearBitWithNegativeBit() {
        try {
            BigInteger bi = new BigInteger("10");
            bi.clearBit(-1);
            assert false : "Expected ArithmeticException for negative bit index";
        } catch (ArithmeticException e) {
            assert e.getMessage().equals("Negative bit address") : "Unexpected exception message: " + e.getMessage();
        }
    }

    static void testClearBitWithZeroBit() {
        BigInteger bi = new BigInteger("8"); // binary 1000
        BigInteger result = bi.clearBit(0); // clear least significant bit
        assert result.equals(new BigInteger("8")) : "Expected 8, got " + result;
    }

    static void testClearBitWithLargeBit() {
        BigInteger bi = new BigInteger("1024"); // 2^10
        BigInteger result = bi.clearBit(10); // clear bit at position 10
        assert result.equals(BigInteger.ZERO) : "Expected 0, got " + result;
    }
}
