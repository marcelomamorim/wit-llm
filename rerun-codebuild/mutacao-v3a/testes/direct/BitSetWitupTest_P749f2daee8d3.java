/* @test    @summary Test the clear(int, int) method of BitSet for various ranges.    @run main BitSetWitupTest_P749f2daee8d3 */

import java.util.BitSet;

public class BitSetWitupTest_P749f2daee8d3 {
    public static void main(String[] args) {
        testClearEmptyRange();
        testClearSingleWord();
        testClearMultipleWords();
        testClearBeyondWords();
        testClearFullRange();
    }

    static void testClearEmptyRange() {
        BitSet bs = new BitSet(128);
        bs.set(10);
        bs.set(20);
        // clear empty range
        bs.clear(10, 10);
        assert bs.get(10) : "Bit 10 should remain set";
        assert bs.get(20) : "Bit 20 should remain set";
    }

    static void testClearSingleWord() {
        BitSet bs = new BitSet(128);
        bs.set(5);
        bs.set(7);
        bs.set(63);
        bs.set(62);
        // clear bits 5 to 8
        bs.clear(5, 8);
        assert !bs.get(5) : "Bit 5 should be cleared";
        assert !bs.get(6) : "Bit 6 should be cleared";
        assert bs.get(7) : "Bit 7 should remain";
        assert bs.get(62) : "Bit 62 should remain";
        assert bs.get(63) : "Bit 63 should remain";
    }

    static void testClearMultipleWords() {
        BitSet bs = new BitSet(200);
        bs.set(0);
        bs.set(65);
        bs.set(130);
        bs.set(199);
        // clear range covering multiple words
        bs.clear(0, 130);
        assert !bs.get(0) : "Bit 0 should be cleared";
        assert !bs.get(65) : "Bit 65 should be cleared";
        assert bs.get(130) : "Bit 130 should remain";
        assert bs.get(199) : "Bit 199 should remain";
    }

    static void testClearBeyondWords() {
        BitSet bs = new BitSet(64);
        bs.set(10);
        // clear range beyond current words
        bs.clear(10, 100);
        assert !bs.get(10) : "Bit 10 should be cleared";
    }

    static void testClearFullRange() {
        BitSet bs = new BitSet(128);
        for (int i = 0; i < 128; i++) {
            bs.set(i);
        }
        bs.clear(0, 128);
        for (int i = 0; i < 128; i++) {
            assert !bs.get(i) : "Bit " + i + " should be cleared";
        }
    }
}
