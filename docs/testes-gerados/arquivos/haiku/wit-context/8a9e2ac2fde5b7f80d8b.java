/*
 * @test
 * @summary Tests BitSet.nextSetBit behavior, including IndexOutOfBoundsException for negative fromIndex
 * @run main BitSetWitupTest
 */

import java.util.BitSet;

public class BitSetWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNegativeFromIndexThrows() {
        BitSet bs = new BitSet();
        bs.set(3);
        boolean thrown = false;
        try {
            bs.nextSetBit(-1);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "nextSetBit(-1) should throw IndexOutOfBoundsException");
    }

    static void testNegativeFromIndexMinValueThrows() {
        BitSet bs = new BitSet();
        boolean thrown = false;
        try {
            bs.nextSetBit(Integer.MIN_VALUE);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "nextSetBit(Integer.MIN_VALUE) should throw IndexOutOfBoundsException");
    }

    static void testEmptySetReturnsMinusOne() {
        BitSet bs = new BitSet();
        check(bs.nextSetBit(0) == -1, "empty BitSet should return -1");
        check(bs.nextSetBit(100) == -1, "empty BitSet should return -1 for any index");
    }

    static void testFindsSetBitFromStart() {
        BitSet bs = new BitSet();
        bs.set(5);
        bs.set(70);
        check(bs.nextSetBit(0) == 5, "expected 5");
        check(bs.nextSetBit(5) == 5, "expected 5 when fromIndex is set bit");
        check(bs.nextSetBit(6) == 70, "expected 70 crossing word boundary");
        check(bs.nextSetBit(71) == -1, "expected -1 past last set bit");
    }

    static void testFromIndexBeyondWordsInUse() {
        BitSet bs = new BitSet();
        bs.set(1);
        check(bs.nextSetBit(1000) == -1, "fromIndex beyond words in use should return -1");
    }

    static void testWordBoundary() {
        BitSet bs = new BitSet();
        bs.set(63);
        bs.set(64);
        check(bs.nextSetBit(63) == 63, "expected 63");
        check(bs.nextSetBit(64) == 64, "expected 64");
    }

    public static void main(String[] args) {
        testNegativeFromIndexThrows();
        testNegativeFromIndexMinValueThrows();
        testEmptySetReturnsMinusOne();
        testFindsSetBitFromStart();
        testFromIndexBeyondWordsInUse();
        testWordBoundary();
    }
}
