/*
 * @test
 * @summary Test java.util.BitSet.flip(int, int) for range checks and bit flipping
 * @run main BitSetWitupTest_P8a6ec5804f15
 */

import java.util.BitSet;

public class BitSetWitupTest_P8a6ec5804f15 {
    public static void main(String[] args) {
        testFlipRangeChecks();
        testFlipBehavior();
    }

    private static void testFlipRangeChecks() {
        BitSet bs = new BitSet();

        // fromIndex < 0 should throw IndexOutOfBoundsException
        try {
            bs.flip(-1, 0);
            throw new RuntimeException("Expected IndexOutOfBoundsException for fromIndex < 0");
        } catch (IndexOutOfBoundsException expected) {
        }

        // toIndex < 0 should throw IndexOutOfBoundsException
        try {
            bs.flip(0, -1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for toIndex < 0");
        } catch (IndexOutOfBoundsException expected) {
        }

        // fromIndex > toIndex should throw IndexOutOfBoundsException
        try {
            bs.flip(5, 3);
            throw new RuntimeException("Expected IndexOutOfBoundsException for fromIndex > toIndex");
        } catch (IndexOutOfBoundsException expected) {
        }
    }

    private static void testFlipBehavior() {
        BitSet bs = new BitSet();

        // Flip empty range: no change
        bs.set(10);
        bs.flip(5, 5);
        if (!bs.get(10)) {
            throw new RuntimeException("Flip of empty range should not change bits");
        }

        // Flip a single bit
        bs.clear();
        bs.flip(3, 4);
        if (!bs.get(3)) {
            throw new RuntimeException("Bit 3 should be set after flip");
        }
        bs.flip(3, 4);
        if (bs.get(3)) {
            throw new RuntimeException("Bit 3 should be cleared after second flip");
        }

        // Flip multiple bits spanning multiple words
        bs.clear();
        int start = 0;
        int end = 130; // spans at least 3 longs (64*2=128)
        bs.flip(start, end);
        for (int i = start; i < end; i++) {
            if (!bs.get(i)) {
                throw new RuntimeException("Bit " + i + " should be set after flip");
            }
        }
        bs.flip(start, end);
        for (int i = start; i < end; i++) {
            if (bs.get(i)) {
                throw new RuntimeException("Bit " + i + " should be cleared after second flip");
            }
        }
    }
}
