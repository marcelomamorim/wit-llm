/*
 * @test
 * @summary Verifies BitSet.set(int fromIndex, int toIndex) range semantics, word-boundary handling, growth and invalid range rejection
 * @run main BitSetWitupTest
 */

import java.util.BitSet;

public class BitSetWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testEmptyRangeIsNoOp() {
        BitSet bs = new BitSet();
        bs.set(5, 5);
        check(bs.cardinality() == 0, "empty range must not set bits");
        check(bs.length() == 0, "empty range must not change length");
    }

    static void testSimpleRange() {
        BitSet bs = new BitSet();
        bs.set(3, 7);
        check(!bs.get(2), "bit 2 must be clear");
        check(bs.get(3), "bit 3 must be set");
        check(bs.get(6), "bit 6 must be set");
        check(!bs.get(7), "toIndex is exclusive");
        check(bs.cardinality() == 4, "cardinality must be 4");
    }

    static void testRangeAcrossWordBoundary() {
        BitSet bs = new BitSet();
        bs.set(60, 70);
        check(bs.cardinality() == 10, "cardinality must be 10");
        check(bs.get(63), "bit 63 must be set");
        check(bs.get(64), "bit 64 must be set");
        check(bs.get(69), "bit 69 must be set");
        check(!bs.get(59), "bit 59 must be clear");
        check(!bs.get(70), "bit 70 must be clear");
    }

    static void testFullWord() {
        BitSet bs = new BitSet();
        bs.set(64, 128);
        check(bs.cardinality() == 64, "cardinality must be 64");
        check(!bs.get(63), "bit 63 must be clear");
        check(bs.nextSetBit(0) == 64, "first set bit must be 64");
        check(bs.length() == 128, "length must be 128");
    }

    static void testMultipleFullWordsSpanned() {
        BitSet bs = new BitSet();
        bs.set(0, 128);
        check(bs.cardinality() == 128, "cardinality must be 128");
        check(bs.length() == 128, "length must be 128");
        check(bs.get(0) && bs.get(127), "edge bits must be set");
    }

    static void testGrowsBeyondCurrentCapacity() {
        BitSet bs = new BitSet();
        bs.set(100, 101);
        check(bs.get(100), "bit 100 must be set");
        check(bs.length() == 101, "length must be 101");
        check(bs.cardinality() == 1, "cardinality must be 1");
    }

    static void testOverlapWithExistingBits() {
        BitSet bs = new BitSet();
        bs.set(0, 2);
        bs.set(1, 4);
        check(bs.cardinality() == 4, "union of overlapping ranges must have cardinality 4");
        check(bs.get(0) && bs.get(1) && bs.get(2) && bs.get(3), "bits 0..3 must be set");
    }

    static void testNegativeFromIndexRejected() {
        BitSet bs = new BitSet();
        boolean thrown = false;
        try {
            bs.set(-1, 3);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "negative fromIndex must throw IndexOutOfBoundsException");
        check(bs.cardinality() == 0, "failed set must not modify the bit set");
    }

    public static void main(String[] args) {
        testEmptyRangeIsNoOp();
        testSimpleRange();
        testRangeAcrossWordBoundary();
        testFullWord();
        testMultipleFullWordsSpanned();
        testGrowsBeyondCurrentCapacity();
        testOverlapWithExistingBits();
        testNegativeFromIndexRejected();
    }
}
