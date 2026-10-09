/*
 * @test    @summary Test for BitSet.nextClearBit(int)
 * @run main BitSetWitupTest_P1bc82b1ab677
 */

import java.util.BitSet;

public class BitSetWitupTest_P1bc82b1ab677 {
    public static void main(String[] args) {
        testNextClearBitFromZero();
        testNextClearBitFromMiddle();
        testNextClearBitBeyondSetBits();
        testNextClearBitNegativeIndex();
    }

    static void testNextClearBitFromZero() {
        BitSet bs = new BitSet();
        bs.set(0);
        bs.set(1);
        bs.set(3);
        int nextClear = bs.nextClearBit(0);
        // Expect next clear bit at index 2
        if (nextClear != 2) {
            throw new AssertionError("Expected next clear bit at 2, but got: " + nextClear);
        }
    }

    static void testNextClearBitFromMiddle() {
        BitSet bs = new BitSet();
        bs.set(0, 5); // bits 0-4 set
        int nextClear = bs.nextClearBit(3);
        // Next clear bit after index 3 should be 5
        if (nextClear != 5) {
            throw new AssertionError("Expected next clear bit at 5, but got: " + nextClear);
        }
    }

    static void testNextClearBitBeyondSetBits() {
        BitSet bs = new BitSet();
        bs.set(0, 10);
        int nextClear = bs.nextClearBit(10);
        // All bits up to 9 are set, so next clear bit from 10 should be 10
        if (nextClear != 10) {
            throw new AssertionError("Expected next clear bit at 10, but got: " + nextClear);
        }
    }

    static void testNextClearBitNegativeIndex() {
        try {
            BitSet bs = new BitSet();
            bs.nextClearBit(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
