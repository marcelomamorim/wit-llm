/*
 * @test
 * @summary Test java.util.BitSet.nextSetBit(int) method behavior
 * @run main BitSetWitupTest_Peeb4a0015b74
 */

import java.util.BitSet;

public class BitSetWitupTest_Peeb4a0015b74 {
    public static void main(String[] args) {
        testNextSetBitBasic();
        testNextSetBitFromIndexZero();
        testNextSetBitFromIndexBeyondWordsInUse();
        testNextSetBitThrowsOnNegativeIndex();
    }

    private static void testNextSetBitBasic() {
        BitSet bs = new BitSet();
        bs.set(10);
        bs.set(64); // next word
        bs.set(130); // third word

        // nextSetBit from 0 should find 10
        int result = bs.nextSetBit(0);
        if (result != 10) {
            throw new AssertionError("Expected 10 but got " + result);
        }

        // nextSetBit from 11 should find 64
        result = bs.nextSetBit(11);
        if (result != 64) {
            throw new AssertionError("Expected 64 but got " + result);
        }

        // nextSetBit from 65 should find 130
        result = bs.nextSetBit(65);
        if (result != 130) {
            throw new AssertionError("Expected 130 but got " + result);
        }

        // nextSetBit from 131 should return -1
        result = bs.nextSetBit(131);
        if (result != -1) {
            throw new AssertionError("Expected -1 but got " + result);
        }
    }

    private static void testNextSetBitFromIndexZero() {
        BitSet bs = new BitSet();
        // no bits set
        int result = bs.nextSetBit(0);
        if (result != -1) {
            throw new AssertionError("Expected -1 for empty BitSet but got " + result);
        }

        bs.set(0);
        result = bs.nextSetBit(0);
        if (result != 0) {
            throw new AssertionError("Expected 0 but got " + result);
        }
    }

    private static void testNextSetBitFromIndexBeyondWordsInUse() {
        BitSet bs = new BitSet();
        bs.set(5);
        // fromIndex beyond wordsInUse (e.g. 1000)
        int result = bs.nextSetBit(1000);
        if (result != -1) {
            throw new AssertionError("Expected -1 for fromIndex beyond wordsInUse but got " + result);
        }
    }

    private static void testNextSetBitThrowsOnNegativeIndex() {
        BitSet bs = new BitSet();
        try {
            bs.nextSetBit(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative fromIndex");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
