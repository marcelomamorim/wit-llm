/*
 * @test
 * @summary Test java.util.BitSet.set(int fromIndex, int toIndex) method
 * @run main BitSetWitupTest
 */

import java.util.BitSet;

public class BitSetWitupTest {
    public static void main(String[] args) {
        testSetRangeWithinOneWord();
        testSetRangeMultipleWords();
        testSetEmptyRange();
        testSetFullWordBoundary();
        testSetInvalidRange();
    }

    private static void testSetRangeWithinOneWord() {
        BitSet bs = new BitSet();
        bs.set(5, 10); // set bits 5..9
        for (int i = 0; i < 64; i++) {
            if (i >= 5 && i < 10) {
                if (!bs.get(i)) {
                    throw new RuntimeException("Bit " + i + " should be set");
                }
            } else {
                if (bs.get(i)) {
                    throw new RuntimeException("Bit " + i + " should not be set");
                }
            }
        }
    }

    private static void testSetRangeMultipleWords() {
        BitSet bs = new BitSet();
        int from = 60;
        int to = 130; // spans from word 0 to word 2
        bs.set(from, to);
        for (int i = 0; i < to; i++) {
            if (i >= from && i < to) {
                if (!bs.get(i)) {
                    throw new RuntimeException("Bit " + i + " should be set");
                }
            } else {
                if (bs.get(i)) {
                    throw new RuntimeException("Bit " + i + " should not be set");
                }
            }
        }
    }

    private static void testSetEmptyRange() {
        BitSet bs = new BitSet();
        bs.set(10, 10); // empty range, should do nothing
        if (bs.length() != 0) {
            throw new RuntimeException("Empty range set should not change BitSet length");
        }
    }

    private static void testSetFullWordBoundary() {
        BitSet bs = new BitSet();
        // set bits from 0 to 64 (one full word)
        bs.set(0, 64);
        for (int i = 0; i < 64; i++) {
            if (!bs.get(i)) {
                throw new RuntimeException("Bit " + i + " should be set");
            }
        }
        if (bs.length() != 64) {
            throw new RuntimeException("BitSet length should be 64 after setting bits 0-63");
        }
    }

    private static void testSetInvalidRange() {
        BitSet bs = new BitSet();
        boolean npeThrown = false;
        try {
            bs.set(-1, 5); // invalid fromIndex
        } catch (IndexOutOfBoundsException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected IndexOutOfBoundsException for negative fromIndex");
        }

        npeThrown = false;
        try {
            bs.set(5, 3); // fromIndex > toIndex
        } catch (IndexOutOfBoundsException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected IndexOutOfBoundsException for fromIndex > toIndex");
        }
    }
}
