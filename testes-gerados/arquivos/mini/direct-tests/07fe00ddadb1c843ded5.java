/*
 * @test
 * @summary Test java.util.BitSet.nextClearBit(int) method behavior
 * @run main BitSetWitupTest
 */

import java.util.BitSet;

public class BitSetWitupTest {
    public static void main(String[] args) {
        testNextClearBitBasic();
        testNextClearBitFromIndexZero();
        testNextClearBitFromIndexBeyondWordsInUse();
        testNextClearBitThrowsOnNegativeIndex();
    }

    private static void testNextClearBitBasic() {
        BitSet bs = new BitSet();
        bs.set(0); // set bit 0
        bs.set(1); // set bit 1
        bs.set(2); // set bit 2
        bs.set(4); // set bit 4
        // bits 3 and 5 are clear

        int clearBit = bs.nextClearBit(0);
        if (clearBit != 3) {
            throw new RuntimeException("Expected nextClearBit(0) to be 3 but was " + clearBit);
        }

        clearBit = bs.nextClearBit(3);
        if (clearBit != 3) {
            throw new RuntimeException("Expected nextClearBit(3) to be 3 but was " + clearBit);
        }

        clearBit = bs.nextClearBit(5);
        if (clearBit != 5) {
            throw new RuntimeException("Expected nextClearBit(5) to be 5 but was " + clearBit);
        }
    }

    private static void testNextClearBitFromIndexZero() {
        BitSet bs = new BitSet();
        // no bits set, so first clear bit from 0 is 0
        int clearBit = bs.nextClearBit(0);
        if (clearBit != 0) {
            throw new RuntimeException("Expected nextClearBit(0) on empty BitSet to be 0 but was " + clearBit);
        }
    }

    private static void testNextClearBitFromIndexBeyondWordsInUse() {
        BitSet bs = new BitSet();
        bs.set(0);
        bs.set(1);
        // wordsInUse should be 1 (since bits 0 and 1 set)
        int fromIndex = 64; // beyond wordsInUse * 64
        int clearBit = bs.nextClearBit(fromIndex);
        if (clearBit != fromIndex) {
            throw new RuntimeException("Expected nextClearBit(" + fromIndex + ") to be " + fromIndex + " but was " + clearBit);
        }
    }

    private static void testNextClearBitThrowsOnNegativeIndex() {
        BitSet bs = new BitSet();
        try {
            bs.nextClearBit(-1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for negative fromIndex");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
