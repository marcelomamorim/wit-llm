/*
 * @test
 * @summary Tests the flip(int bitIndex) method of java.util.BitSet
 * @run main BitSetWitupTest
 */

import java.util.BitSet;

public class BitSetWitupTest {
    public static void main(String[] args) {
        testFlipWithValidIndex();
        testFlipWithNegativeIndex();
        testFlipWithLargeIndex();
    }

    static void testFlipWithValidIndex() {
        BitSet bs = new BitSet();
        int index = 10;
        bs.set(index);
        boolean beforeFlip = bs.get(index);
        bs.flip(index);
        boolean afterFlip = bs.get(index);
        if (beforeFlip == afterFlip) {
            throw new AssertionError("flip did not toggle the bit at index " + index);
        }
    }

    static void testFlipWithNegativeIndex() {
        BitSet bs = new BitSet();
        try {
            bs.flip(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testFlipWithLargeIndex() {
        BitSet bs = new BitSet();
        int largeIndex = Integer.MAX_VALUE;
        bs.set(largeIndex);
        boolean beforeFlip = bs.get(largeIndex);
        bs.flip(largeIndex);
        boolean afterFlip = bs.get(largeIndex);
        if (beforeFlip == afterFlip) {
            throw new AssertionError("flip did not toggle the bit at large index " + largeIndex);
        }
    }
}