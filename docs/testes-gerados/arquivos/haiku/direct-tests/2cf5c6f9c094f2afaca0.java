/*
 * @test
 * @summary Behavioral tests for BitSet.clear(int fromIndex, int toIndex)
 * @run main BitSetWitupTest
 */

import java.util.BitSet;

public class BitSetWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testClearAcrossMultipleWords() {
        BitSet bs = new BitSet();
        bs.set(0, 200);
        bs.clear(10, 130);
        check(bs.get(9), "bit 9 should remain set");
        check(!bs.get(10), "bit 10 should be cleared");
        check(!bs.get(129), "bit 129 should be cleared");
        check(bs.get(130), "bit 130 should remain set");
        check(bs.cardinality() == 80, "expected cardinality 80 but was " + bs.cardinality());
    }

    static void testClearWithinSingleWord() {
        BitSet bs = new BitSet();
        bs.set(0, 64);
        bs.clear(5, 10);
        check(bs.cardinality() == 59, "expected cardinality 59 but was " + bs.cardinality());
        check(bs.get(4), "bit 4 should remain set");
        check(!bs.get(5), "bit 5 should be cleared");
        check(!bs.get(9), "bit 9 should be cleared");
        check(bs.get(10), "bit 10 should remain set");
    }

    static void testClearEmptyRangeIsNoOp() {
        BitSet bs = new BitSet();
        bs.set(3);
        bs.clear(3, 3);
        check(bs.get(3), "empty range clear must not change bit 3");
        check(bs.cardinality() == 1, "cardinality must stay 1");
    }

    static void testClearBeyondWordsInUseIsNoOp() {
        BitSet bs = BitSet.valueOf(new long[] {1L});
        int lengthBefore = bs.length();
        bs.clear(100, 200);
        check(bs.cardinality() == 1, "cardinality must stay 1");
        check(bs.get(0), "bit 0 must remain set");
        check(bs.length() == lengthBefore, "length must not change");
    }

    static void testClearOnEmptyBitSet() {
        BitSet bs = new BitSet();
        bs.clear(0, 100);
        check(bs.cardinality() == 0, "empty bitset must remain empty");
        check(bs.isEmpty(), "bitset must be empty");
    }

    static void testClearRangeExtendingPastEnd() {
        BitSet bs = new BitSet();
        bs.set(0, 10);
        bs.clear(5, 1000);
        check(bs.cardinality() == 5, "expected cardinality 5 but was " + bs.cardinality());
        check(bs.get(4), "bit 4 should remain set");
        check(!bs.get(5), "bit 5 should be cleared");
        check(bs.length() == 5, "expected length 5 but was " + bs.length());
    }

    static void testNegativeFromIndexThrows() {
        BitSet bs = new BitSet();
        try {
            bs.clear(-1, 5);
            throw new RuntimeException("expected IndexOutOfBoundsException for negative fromIndex");
        } catch (IndexOutOfBoundsException expected) {
        }
    }

    static void testFromIndexGreaterThanToIndexThrows() {
        BitSet bs = new BitSet();
        try {
            bs.clear(5, 3);
            throw new RuntimeException("expected IndexOutOfBoundsException for fromIndex > toIndex");
        } catch (IndexOutOfBoundsException expected) {
        }
    }

    public static void main(String[] args) {
        testClearAcrossMultipleWords();
        testClearWithinSingleWord();
        testClearEmptyRangeIsNoOp();
        testClearBeyondWordsInUseIsNoOp();
        testClearOnEmptyBitSet();
        testClearRangeExtendingPastEnd();
        testNegativeFromIndexThrows();
        testFromIndexGreaterThanToIndexThrows();
    }
}
