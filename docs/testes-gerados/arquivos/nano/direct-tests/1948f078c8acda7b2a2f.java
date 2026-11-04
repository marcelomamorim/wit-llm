/*
 * @test
 * @summary Tests the clear(int bitIndex) method of java.util.BitSet
 * @run main BitSetClearMethodTest
 */

import java.util.BitSet;

public class BitSetClearMethodTest {
    public static void main(String[] args) {
        testClearBitAtZero();
        testClearBitAtHighIndex();
        testClearBitAtNegativeIndex();
        testClearBitAtIndexBeyondSize();
    }

    static void testClearBitAtZero() {
        BitSet bs = new BitSet();
        bs.set(0);
        bs.clear(0);
        if (bs.get(0)) {
            throw new AssertionError("Bit at index 0 should be cleared.");
        }
    }

    static void testClearBitAtHighIndex() {
        BitSet bs = new BitSet();
        int index = 1000;
        bs.set(index);
        bs.clear(index);
        if (bs.get(index)) {
            throw new AssertionError("Bit at index " + index + " should be cleared.");
        }
    }

    static void testClearBitAtNegativeIndex() {
        BitSet bs = new BitSet();
        boolean exceptionThrown = false;
        try {
            bs.clear(-1);
        } catch (IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index.");
        }
    }

    static void testClearBitAtIndexBeyondSize() {
        BitSet bs = new BitSet();
        int index = Integer.MAX_VALUE;
        // Setting a bit at a very high index
        bs.set(index);
        // Clearing the same bit
        bs.clear(index);
        if (bs.get(index)) {
            throw new AssertionError("Bit at index " + index + " should be cleared.");
        }
    }
}
