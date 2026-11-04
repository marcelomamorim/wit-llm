/* @test    @summary Test clearBit method of BigInteger for positive, zero, and negative cases    @run main BigIntegerWitupTest */

import java.math.BigInteger;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        testClearBitPositive();
        testClearBitZero();
        testClearBitNegative();
    }

    static void testClearBitPositive() {
        BigInteger bi = BigInteger.valueOf(15); // binary 1111
        BigInteger result = bi.clearBit(1); // clear second bit (bit index 1)
        assert result.equals(BigInteger.valueOf(13)) : "Expected 13, got " + result;
    }

    static void testClearBitZero() {
        BigInteger bi = BigInteger.valueOf(8); // binary 1000
        BigInteger result = bi.clearBit(0); // clear least significant bit
        assert result.equals(BigInteger.valueOf(8)) : "Expected 8, got " + result;
    }

    static void testClearBitNegative() {
        BigInteger bi = BigInteger.valueOf(10); // binary 1010
        try {
            bi.clearBit(-1);
            assert false : "Expected ArithmeticException for negative index";
        } catch (ArithmeticException e) {
            assert e.getMessage().equals("Negative bit address") : "Unexpected message: " + e.getMessage();
        }
    }
}
