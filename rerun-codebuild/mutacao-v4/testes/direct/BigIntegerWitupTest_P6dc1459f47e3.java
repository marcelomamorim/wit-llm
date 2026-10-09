/* @test
   @summary Tests the clearBit method of BigInteger for various cases.
   @run main BigIntegerWitupTest_P6dc1459f47e3
 */
import java.math.BigInteger;
public class BigIntegerWitupTest_P6dc1459f47e3 {
    public static void main(String[] args) {
        testClearBitPositive();
        testClearBitZero();
        testClearBitNegative();
        testClearBitOutOfRange();
    }

    static void testClearBitPositive() {
        BigInteger bi = BigInteger.valueOf(15); // binary 1111
        BigInteger result = bi.clearBit(1); // clear second bit (bit index 1)
        BigInteger expected = BigInteger.valueOf(13); // binary 1101
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: clearBit(1) on 15, got " + result);
        }
    }

    static void testClearBitZero() {
        BigInteger bi = BigInteger.valueOf(8); // binary 1000
        BigInteger result = bi.clearBit(0); // clear least significant bit
        BigInteger expected = BigInteger.valueOf(8); // bit 0 is already 0
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: clearBit(0) on 8, got " + result);
        }
    }

    static void testClearBitNegative() {
        try {
            BigInteger bi = BigInteger.valueOf(10);
            bi.clearBit(-1);
            throw new AssertionError("Expected ArithmeticException for negative n");
        } catch (ArithmeticException e) {
            // expected
        }
    }

    static void testClearBitOutOfRange() {
        BigInteger bi = BigInteger.valueOf(1);
        BigInteger result = bi.clearBit(100); // clearing a high bit, should be no change
        if (!result.equals(bi)) {
            throw new AssertionError("Failed: clearBit(100) on 1, got " + result);
        }
    }
}
