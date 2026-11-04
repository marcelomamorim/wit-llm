/*
 * @test
 * @summary Behavioral tests for BitSet.nextSetBit using only public API
 * @run main BitSetWitupTest
 */

import java.util.BitSet;

public class BitSetWitupTest {

    public static void main(String[] args) {
        testEmptyBitSetReturnsMinusOne();
        testSingleBitFromStart();
        testFromIndexOnSetBit();
        testFromIndexAfterSetBit();
        testWordBoundaryBits();
        testMultiWordSearch();
        testValueOfLongArray();
        testClearedBitIsNotFound();
        testTrailingBitsBoundary();
        testFromIndexBeyondWordsInUse();
        testNegativeFromIndexThrows();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testEmptyBitSetReturnsMinusOne() {
        BitSet bs = new BitSet();
        check(bs.nextSetBit(0) == -1, "empty set nextSetBit(0) should be -1");
        check(bs.nextSetBit(100) == -1, "empty set nextSetBit(100) should be -1");
    }

    static void testSingleBitFromStart() {
        BitSet bs = new BitSet();
        bs.set(5);
        check(bs.nextSetBit(0) == 5, "nextSetBit(0) should find bit 5");
    }

    static void testFromIndexOnSetBit() {
        BitSet bs = new BitSet();
        bs.set(5);
        check(bs.nextSetBit(5) == 5, "nextSetBit(5) should return 5 when bit 5 is set");
    }

    static void testFromIndexAfterSetBit() {
        BitSet bs = new BitSet();
        bs.set(5);
        check(bs.nextSetBit(6) == -1, "nextSetBit(6) should be -1 when only bit 5 is set");
    }

    static void testWordBoundaryBits() {
        BitSet bs = new BitSet();
        bs.set(63);
        bs.set(64);
        check(bs.nextSetBit(63) == 63, "nextSetBit(63) should be 63");
        check(bs.nextSetBit(64) == 64, "nextSetBit(64) should be 64");
        check(bs.nextSetBit(65) == -1, "nextSetBit(65) should be -1");
    }

    static void testMultiWordSearch() {
        BitSet bs = new BitSet();
        bs.set(130);
        check(bs.nextSetBit(0) == 130, "nextSetBit(0) should be 130");
        check(bs.nextSetBit(100) == 130, "nextSetBit(100) should be 130");
        check(bs.nextSetBit(131) == -1, "nextSetBit(131) should be -1");
    }

    static void testValueOfLongArray() {
        BitSet bs = BitSet.valueOf(new long[] {0L, 1L});
        check(bs.nextSetBit(0) == 64, "valueOf({0,1}) nextSetBit(0) should be 64");
        check(bs.nextSetBit(64) == 64, "valueOf({0,1}) nextSetBit(64) should be 64");
        check(bs.nextSetBit(65) == -1, "valueOf({0,1}) nextSetBit(65) should be -1");
    }

    static void testClearedBitIsNotFound() {
        BitSet bs = new BitSet();
        bs.set(3);
        bs.clear(3);
        check(bs.nextSetBit(0) == -1, "cleared bit should not be found");
    }

    static void testTrailingBitsBoundary() {
        BitSet bs = new BitSet();
        bs.set(1);
        bs.set(2);
        check(bs.nextSetBit(0) == 1, "nextSetBit(0) should be 1");
        check(bs.nextSetBit(2) == 2, "nextSetBit(2) should be 2");
        check(bs.nextSetBit(3) == -1, "nextSetBit(3) should be -1");
    }

    static void testFromIndexBeyondWordsInUse() {
        BitSet bs = new BitSet();
        bs.set(10);
        check(bs.nextSetBit(Integer.MAX_VALUE) == -1, "nextSetBit(MAX_VALUE) should be -1");
    }

    static void testNegativeFromIndexThrows() {
        BitSet bs = new BitSet();
        boolean thrown = false;
        try {
            bs.nextSetBit(-1);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "nextSetBit(-1) should throw IndexOutOfBoundsException");
    }
}
