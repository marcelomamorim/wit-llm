/*
 * @test
 * @summary Test BitSet.flip(int, int) method for correct toggling of bits
 * @run main BitSetWitupTest_P0eabc4460916
 */

import java.util.BitSet;

public class BitSetWitupTest_P0eabc4460916 {
    public static void main(String[] args) {
        testFlipSingleWord();
        testFlipMultipleWords();
        testFlipZeroRange();
    }

    static void testFlipSingleWord() {
        BitSet bs = new BitSet();
        // Set bits 0 to 63
        for (int i = 0; i < 64; i++) {
            bs.set(i);
        }
        // Flip bits from 10 to 20
        bs.flip(10, 20);
        // Check bits 10 to 19 are flipped
        for (int i = 10; i < 20; i++) {
            if (bs.get(i)) {
                System.out.println("Error: bit " + i + " expected to be false after flip.");
            }
        }
        // Check bits outside remain set
        if (!bs.get(0) || !bs.get(63)) {
            System.out.println("Error: bits outside range flipped unexpectedly.");
        }
    }

    static void testFlipMultipleWords() {
        BitSet bs = new BitSet();
        // Set bits 0 to 130
        for (int i = 0; i <= 130; i++) {
            bs.set(i);
        }
        // Flip bits from 60 to 130
        bs.flip(60, 130);
        // Check bits 60 to 129 are flipped
        for (int i = 60; i < 130; i++) {
            if (bs.get(i)) {
                System.out.println("Error: bit " + i + " expected to be false after flip.");
            }
        }
        // Check bits outside range remain set
        if (!bs.get(0) || !bs.get(59) || !bs.get(130)) {
            System.out.println("Error: bits outside range flipped unexpectedly.");
        }
    }

    static void testFlipZeroRange() {
        BitSet bs = new BitSet();
        bs.set(0, 10);
        bs.flip(5, 5); // flipping zero-length range should not change anything
        // Verify bits 0-9 are unchanged
        for (int i = 0; i < 10; i++) {
            if (!bs.get(i)) {
                System.out.println("Error: bit " + i + " should remain set.");
            }
        }
    }
}
