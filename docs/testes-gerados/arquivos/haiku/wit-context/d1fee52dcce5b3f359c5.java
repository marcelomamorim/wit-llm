/*
 * @test
 * @summary Witup-derived behavioral tests for BitSet.previousSetBit
 * @run main BitSetWitupTest
 */

import java.util.BitSet;

public class BitSetWitupTest {

    public static void main(String[] args) {
        testMinusOneReturnsMinusOne();
        testNegativeIndexBelowMinusOneThrows();
        testMinIntThrows();
        testEmptySetReturnsMinusOne();
        testSetBitsAreFound();
        testIndexBeyondWordsInUseReturnsLastSetBit();
        testCrossWordBoundary();
        testClearedBitIsIgnored();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testMinusOneReturnsMinusOne() {
        BitSet bs = new BitSet();
        bs.set(0);
        check(bs.previousSetBit(-1) == -1, "previousSetBit(-1) must return -1");
        check(new BitSet().previousSetBit(-1) == -1, "previousSetBit(-1) on empty set must return -1");
    }

    static void testNegativeIndexBelowMinusOneThrows() {
        BitSet bs = new BitSet();
        bs.set(3);
        boolean thrown = false;
        try {
            bs.previousSetBit(-2);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "previousSetBit(-2) must throw IndexOutOfBoundsException");
    }

    static void testMinIntThrows() {
        BitSet bs = new BitSet();
        boolean thrown = false;
        try {
            bs.previousSetBit(Integer.MIN_VALUE);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "previousSetBit(Integer.MIN_VALUE) must throw IndexOutOfBoundsException");
    }

    static void testEmptySetReturnsMinusOne() {
        BitSet bs = new BitSet();
        check(bs.previousSetBit(0) == -1, "empty set: previousSetBit(0) must be -1");
        check(bs.previousSetBit(10) == -1, "empty set: previousSetBit(10) must be -1");
        check(bs.previousSetBit(1000) == -1, "empty set: previousSetBit(1000) must be -1");
    }

    static void testSetBitsAreFound() {
        BitSet bs = new BitSet();
        bs.set(3);
        bs.set(70);
        check(bs.previousSetBit(70) == 70, "previousSetBit(70) must be 70");
        check(bs.previousSetBit(69) == 3, "previousSetBit(69) must be 3");
        check(bs.previousSetBit(3) == 3, "previousSetBit(3) must be 3");
        check(bs.previousSetBit(2) == -1, "previousSetBit(2) must be -1");
        check(bs.previousSetBit(0) == -1, "previousSetBit(0) must be -1");
    }

    static void testIndexBeyondWordsInUseReturnsLastSetBit() {
        BitSet bs = new BitSet();
        bs.set(3);
        bs.set(70);
        check(bs.previousSetBit(200) == 70, "previousSetBit(200) must return last set bit 70");
        check(bs.previousSetBit(1000) == 70, "previousSetBit(1000) must return last set bit 70");
    }

    static void testCrossWordBoundary() {
        BitSet bs = new BitSet();
        bs.set(64);
        check(bs.previousSetBit(64) == 64, "previousSetBit(64) must be 64");
        check(bs.previousSetBit(63) == -1, "previousSetBit(63) must be -1");
        check(bs.previousSetBit(127) == 64, "previousSetBit(127) must be 64");
    }

    static void testClearedBitIsIgnored() {
        BitSet bs = new BitSet();
        bs.set(5);
        bs.clear(5);
        check(bs.previousSetBit(10) == -1, "cleared bit must not be reported");
    }
}
