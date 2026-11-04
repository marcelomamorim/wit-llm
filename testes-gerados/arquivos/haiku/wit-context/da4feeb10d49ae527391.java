/*
 * @test
 * @summary Witup-generated tests for the BitArray(int) constructor and related public observable behavior
 * @modules java.base/sun.security.util
 * @run main BitArrayWitupTest
 */

import sun.security.util.BitArray;

public class BitArrayWitupTest {

    public static void main(String[] args) {
        testNegativeLengthThrows();
        testZeroLengthAccepted();
        testNewBitsAreZero();
        testBooleanArrayConstructorPreservesBits();
    }

    static void testNegativeLengthThrows() {
        int[] lengths = {-1, Integer.MIN_VALUE};
        for (int len : lengths) {
            boolean thrown = false;
            try {
                new BitArray(len);
            } catch (IllegalArgumentException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("Expected IllegalArgumentException for length " + len);
            }
        }
    }

    static void testZeroLengthAccepted() {
        BitArray ba = new BitArray(0);
        if (ba == null) {
            throw new RuntimeException("BitArray(0) returned null");
        }
    }

    static void testNewBitsAreZero() {
        BitArray ba = new BitArray(8);
        for (int i = 0; i < 8; i++) {
            if (ba.get(i)) {
                throw new RuntimeException("Bit " + i + " should be zero in new BitArray(8)");
            }
        }
    }

    static void testBooleanArrayConstructorPreservesBits() {
        boolean[] bits = {true, false, true};
        BitArray ba = new BitArray(bits);
        if (!ba.get(0) || ba.get(1) || !ba.get(2)) {
            throw new RuntimeException("BitArray(boolean[]) did not preserve bit values");
        }
    }
}
