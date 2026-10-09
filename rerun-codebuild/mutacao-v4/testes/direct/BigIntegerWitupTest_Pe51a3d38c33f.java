/* @test
   @summary Tests the flipBit method of BigInteger for positive, zero, and negative bit positions.
   @run main BigIntegerWitupTest_Pe51a3d38c33f
 */
import java.math.BigInteger;
public class BigIntegerWitupTest_Pe51a3d38c33f {
    public static void main(String[] args) {
        testFlipBitPositive();
        testFlipBitZero();
        testFlipBitNegative();
    }

    static void testFlipBitPositive() {
        BigInteger bi = BigInteger.valueOf(0b1010); // 10
        BigInteger result = bi.flipBit(1); // flip bit at position 1 (zero-based)
        BigInteger expected = BigInteger.valueOf(0b1010 ^ (1 << 1)); // flip second bit
        if (!result.equals(expected)) {
            throw new AssertionError("flipBit positive test failed: expected " + expected + " but got " + result);
        }
    }

    static void testFlipBitZero() {
        BigInteger bi = BigInteger.valueOf(0b1); // 1
        BigInteger result = bi.flipBit(0); // flip bit at position 0
        BigInteger expected = BigInteger.valueOf(0b1 ^ (1 << 0)); // flip first bit
        if (!result.equals(expected)) {
            throw new AssertionError("flipBit zero test failed: expected " + expected + " but got " + result);
        }
    }

    static void testFlipBitNegative() {
        try {
            BigInteger bi = BigInteger.valueOf(1);
            bi.flipBit(-1);
            throw new AssertionError("Expected ArithmeticException for negative bit position");
        } catch (ArithmeticException e) {
            // expected
        }
    }
}
