/*
 * @test
 * @summary Witup-derived checks for BitSet.previousClearBit(int)
 * @run main BitSetWitupTest
 */

import java.util.BitSet;

public class BitSetWitupTest {
    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void checkEquals(int expected, int actual, String msg) {
        if (expected != actual) {
            throw new RuntimeException(msg + ": expected " + expected + " but was " + actual);
        }
    }

    static void testNegativeOne() {
        BitSet bs = new BitSet();
        bs.set(3);
        checkEquals(-1, bs.previousClearBit(-1), "fromIndex -1 must return -1");
    }

    static void testNegativeBelowMinusOneThrows() {
        BitSet bs = new BitSet();
        boolean thrown = false;
        try {
            bs.previousClearBit(-2);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "fromIndex -2 must throw IndexOutOfBoundsException");
    }

    static void testMinIntThrows() {
        BitSet bs = new BitSet();
        boolean thrown = false;
        try {
            bs.previousClearBit(Integer.MIN_VALUE);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "Integer.MIN_VALUE must throw IndexOutOfBoundsException");
    }

    static void testEmptySetReturnsFromIndex() {
        BitSet bs = new BitSet();
        checkEquals(10, bs.previousClearBit(10), "empty set must return fromIndex");
    }

    static void testBeyondWordsInUseReturnsFromIndex() {
        BitSet bs = new BitSet();
        bs.set(3);
        checkEquals(100, bs.previousClearBit(100), "index beyond logical size must return fromIndex");
    }

    static void testBasicPreviousClear() {
        BitSet bs = new BitSet();
        bs.set(0, 5);
        bs.set(6);
        checkEquals(5, bs.previousClearBit(6), "previous clear bit before 6 should be 5");
    }

    static void testAllSetInWordReturnsMinusOne() {
        BitSet bs = new BitSet();
        bs.set(0, 64);
        checkEquals(-1, bs.previousClearBit(63), "no clear bit at or below 63 must return -1");
    }

    static void testAcrossWordBoundary() {
        BitSet bs = new BitSet();
        bs.set(0, 64);
        bs.set(65);
        checkEquals(64, bs.previousClearBit(65), "clear bit 64 must be found across word boundary");
    }

    static void testSearchesPreviousWord() {
        BitSet bs = new BitSet();
        bs.set(0, 128);
        bs.clear(10);
        checkEquals(10, bs.previousClearBit(127), "clear bit 10 must be found from index 127");
    }

    public static void main(String[] args) {
        testNegativeOne();
        testNegativeBelowMinusOneThrows();
        testMinIntThrows();
        testEmptySetReturnsFromIndex();
        testBeyondWordsInUseReturnsFromIndex();
        testBasicPreviousClear();
        testAllSetInWordReturnsMinusOne();
        testAcrossWordBoundary();
        testSearchesPreviousWord();
    }
}
