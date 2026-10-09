/*
 * @test
 * @summary Test java.util.BitSet.flip(int fromIndex, int toIndex) method
 * @run main BitSetWitupTest_P8a6ec5804f15
 */

import java.util.BitSet;

public class BitSetWitupTest_P8a6ec5804f15 {
    public static void main(String[] args) {
        testFlipEmptyRange();
        testFlipSingleWord();
        testFlipMultipleWords();
        testFlipExpandWords();
        testFlipFullWord();
    }

    private static void testFlipEmptyRange() {
        BitSet bs = new BitSet();
        bs.set(10);
        bs.flip(5, 5); // empty range, should do nothing
        if (!bs.get(10)) {
            throw new RuntimeException("flip with empty range cleared bits unexpectedly");
        }
    }

    private static void testFlipSingleWord() {
        BitSet bs = new BitSet();
        bs.set(2); // set bit 2
        bs.set(4); // set bit 4
        bs.flip(1, 5); // flip bits 1 to 4 inclusive
        // bits 1,3 flipped from false to true; bits 2,4 flipped from true to false
        if (bs.get(1) != true || bs.get(2) != false || bs.get(3) != true || bs.get(4) != false) {
            throw new RuntimeException("flip single word failed");
        }
    }

    private static void testFlipMultipleWords() {
        BitSet bs = new BitSet();
        // set bit 0 and bit 130 (two different words)
        bs.set(0);
        bs.set(130);
        bs.flip(0, 131); // flip bits 0 to 130 inclusive
        // bit 0 and 130 should be cleared, bits in between set
        if (bs.get(0) || bs.get(130)) {
            throw new RuntimeException("flip multiple words failed to clear edge bits");
        }
        // check some bits in the middle are set
        if (!bs.get(64) || !bs.get(129)) {
            throw new RuntimeException("flip multiple words failed to set middle bits");
        }
    }

    private static void testFlipExpandWords() {
        BitSet bs = new BitSet();
        bs.flip(200, 205); // flip bits beyond current size
        for (int i = 200; i < 205; i++) {
            if (!bs.get(i)) {
                throw new RuntimeException("flip failed to set bit " + i + " after expansion");
            }
        }
    }

    private static void testFlipFullWord() {
        BitSet bs = new BitSet();
        int start = 64; // start of second word
        int end = 128;  // end of second word
        bs.flip(start, end); // flip full word
        for (int i = start; i < end; i++) {
            if (!bs.get(i)) {
                throw new RuntimeException("flip full word failed to set bit " + i);
            }
        }
        bs.flip(start, end); // flip back
        for (int i = start; i < end; i++) {
            if (bs.get(i)) {
                throw new RuntimeException("flip full word failed to clear bit " + i);
            }
        }
    }
}
