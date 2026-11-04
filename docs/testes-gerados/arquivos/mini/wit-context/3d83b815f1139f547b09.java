/*
 * @test
 * @summary Test BigInteger.flipBit(int) for correct bit flipping and exception on negative input
 * @run main BigIntegerWitupTest
 */

import java.math.BigInteger;

public class BigIntegerWitupTest {
    public static void main(String[] args) {
        testFlipBitPositive();
        testFlipBitZero();
        testFlipBitNegative();
    }

    private static void testFlipBitPositive() {
        // flip bit 0 of zero should set bit 0 to 1
        BigInteger zero = BigInteger.ZERO;
        BigInteger flipped = zero.flipBit(0);
        if (!flipped.equals(BigInteger.ONE)) {
            throw new AssertionError("flipBit(0) of 0 should be 1, got: " + flipped);
        }

        // flip bit 5 of 1 (binary 1) should flip bit 5
        BigInteger one = BigInteger.ONE;
        BigInteger flipped5 = one.flipBit(5);
        // bit 5 corresponds to value 32, so result should be 33
        BigInteger expected = BigInteger.valueOf(33);
        if (!flipped5.equals(expected)) {
            throw new AssertionError("flipBit(5) of 1 should be 33, got: " + flipped5);
        }
    }

    private static void testFlipBitZero() {
        // flip bit 0 of a positive number twice should return original number
        BigInteger val = BigInteger.valueOf(12345);
        BigInteger flippedOnce = val.flipBit(0);
        BigInteger flippedTwice = flippedOnce.flipBit(0);
        if (!flippedTwice.equals(val)) {
            throw new AssertionError("flipBit(0) twice should return original value, got: " + flippedTwice);
        }
    }

    private static void testFlipBitNegative() {
        // flipBit with negative index should throw ArithmeticException
        try {
            BigInteger.ONE.flipBit(-1);
            throw new AssertionError("flipBit with negative index did not throw ArithmeticException");
        } catch (ArithmeticException e) {
            // expected
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Negative bit address")) {
                throw new AssertionError("ArithmeticException message unexpected: " + msg);
            }
        }
    }
}
