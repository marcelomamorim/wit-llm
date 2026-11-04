/*
 * @test
 * @summary Test java.util.BitSet.clear(int) method behavior
 * @run main BitSetWitupTest
 */

import java.util.BitSet;

public class BitSetWitupTest {
    public static void main(String[] args) {
        testClearNegativeIndex();
        testClearIndexBeyondWordsInUse();
        testClearSingleBit();
        testClearMultipleBits();
    }

    private static void testClearNegativeIndex() {
        BitSet bs = new BitSet();
        try {
            bs.clear(-1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testClearIndexBeyondWordsInUse() {
        BitSet bs = new BitSet();
        bs.set(0);
        bs.set(63);
        int initialCardinality = bs.cardinality();
        bs.clear(128); // index beyond wordsInUse, should do nothing
        if (bs.cardinality() != initialCardinality) {
            throw new RuntimeException("clear beyond wordsInUse modified BitSet");
        }
    }

    private static void testClearSingleBit() {
        BitSet bs = new BitSet();
        bs.set(5);
        if (!bs.get(5)) {
            throw new RuntimeException("Bit 5 should be set before clear");
        }
        bs.clear(5);
        if (bs.get(5)) {
            throw new RuntimeException("Bit 5 should be cleared");
        }
        if (bs.cardinality() != 0) {
            throw new RuntimeException("BitSet should be empty after clearing the only set bit");
        }
    }

    private static void testClearMultipleBits() {
        BitSet bs = new BitSet();
        bs.set(0);
        bs.set(1);
        bs.set(63);
        bs.set(64);
        bs.set(127);

        bs.clear(1);
        if (bs.get(1)) {
            throw new RuntimeException("Bit 1 should be cleared");
        }

        bs.clear(64);
        if (bs.get(64)) {
            throw new RuntimeException("Bit 64 should be cleared");
        }

        // Bits 0, 63, 127 should remain set
        if (!bs.get(0) || !bs.get(63) || !bs.get(127)) {
            throw new RuntimeException("Bits 0, 63, 127 should remain set");
        }
    }
}
