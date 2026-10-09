/* @test    @summary Test the flip method of BitSet for various ranges    @run main BitSetWitupTest_Pf89c763429c6 */

import java.util.BitSet;

public class BitSetWitupTest_Pf89c763429c6 {

    public static void main(String[] args) {
        testFlipSingleWord();
        testFlipMultipleWords();
        testFlipZeroRange();
        testFlipFullRange();
    }

    static void testFlipSingleWord() {
        BitSet bs = new BitSet();
        bs.set(10);
        bs.set(20);
        bs.flip(10, 21);
        assert bs.get(10) == false : "Bit 10 should be flipped to false";
        assert bs.get(20) == false : "Bit 20 should be flipped to false";
        assert bs.get(15) == true : "Bit 15 should be flipped to true";
    }

    static void testFlipMultipleWords() {
        BitSet bs = new BitSet();
        for (int i = 0; i < 130; i += 10) {
            bs.set(i);
        }
        bs.flip(0, 130);
        for (int i = 0; i < 130; i++) {
            if (i % 10 == 0) {
                assert bs.get(i) == false : "Bit " + i + " should be flipped to false";
            } else {
                assert bs.get(i) == true : "Bit " + i + " should be flipped to true";
            }
        }
    }

    static void testFlipZeroRange() {
        BitSet bs = new BitSet();
        bs.set(5);
        bs.flip(5, 5); // should do nothing
        assert bs.get(5) == true : "Bit 5 should remain true";
    }

    static void testFlipFullRange() {
        BitSet bs = new BitSet();
        for (int i = 0; i < 64; i++) {
            bs.set(i);
        }
        bs.flip(0, 64);
        for (int i = 0; i < 64; i++) {
            assert bs.get(i) == false : "Bit " + i + " should be flipped to false";
        }
    }
}
