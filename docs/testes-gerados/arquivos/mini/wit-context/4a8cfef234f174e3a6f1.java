/*
 * @test
 * @summary Test java.util.BitSet.set(int fromIndex, int toIndex) for range checks and bit setting
 * @run main BitSetWitupTest
 */

import java.util.BitSet;

public class BitSetWitupTest {
    public static void main(String[] args) {
        testSetRangeValid();
        testSetRangeEmpty();
        testSetRangeSingleWord();
        testSetRangeMultipleWords();
        testSetRangeFromIndexNegative();
        testSetRangeToIndexNegative();
        testSetRangeFromIndexGreaterThanToIndex();
    }

    private static void testSetRangeValid() {
        BitSet bs = new BitSet();
        bs.set(0, 64); // full first word
        for (int i = 0; i < 64; i++) {
            if (!bs.get(i)) {
                throw new AssertionError("Bit " + i + " should be set");
            }
        }
    }

    private static void testSetRangeEmpty() {
        BitSet bs = new BitSet();
        bs.set(10, 10); // empty range, should do nothing
        if (bs.length() != 0) {
            throw new AssertionError("Empty range set should not change BitSet length");
        }
    }

    private static void testSetRangeSingleWord() {
        BitSet bs = new BitSet();
        bs.set(5, 10); // within one word
        for (int i = 5; i < 10; i++) {
            if (!bs.get(i)) {
                throw new AssertionError("Bit " + i + " should be set");
            }
        }
        if (bs.get(4) || bs.get(10)) {
            throw new AssertionError("Bits outside range should not be set");
        }
    }

    private static void testSetRangeMultipleWords() {
        BitSet bs = new BitSet();
        bs.set(60, 130); // spans multiple words
        for (int i = 60; i < 130; i++) {
            if (!bs.get(i)) {
                throw new AssertionError("Bit " + i + " should be set");
            }
        }
        if (bs.get(59) || bs.get(130)) {
            throw new AssertionError("Bits outside range should not be set");
        }
    }

    private static void testSetRangeFromIndexNegative() {
        BitSet bs = new BitSet();
        try {
            bs.set(-1, 5);
            throw new AssertionError("Expected IndexOutOfBoundsException for fromIndex < 0");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testSetRangeToIndexNegative() {
        BitSet bs = new BitSet();
        try {
            bs.set(0, -1);
            throw new AssertionError("Expected IndexOutOfBoundsException for toIndex < 0");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testSetRangeFromIndexGreaterThanToIndex() {
        BitSet bs = new BitSet();
        try {
            bs.set(10, 5);
            throw new AssertionError("Expected IndexOutOfBoundsException for fromIndex > toIndex");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }
}
