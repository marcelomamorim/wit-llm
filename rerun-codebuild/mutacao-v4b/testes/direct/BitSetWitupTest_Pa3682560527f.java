/* @test
   @summary Verifies the flip method on BitSet for various ranges.
   @run main BitSetWitupTest_Pa3682560527f
 */
import java.util.BitSet;
public class BitSetWitupTest_Pa3682560527f {
    public static void main(String[] args) {
        testFlipSingleWord();
        testFlipMultipleWords();
        testFlipZeroRange();
        testFlipFullRange();
    }

    static void testFlipSingleWord() {
        BitSet bs = new BitSet();
        bs.set(0);
        bs.set(63);
        bs.flip(0, 64);
        assert bs.get(0) == false : "Bit 0 should be flipped to false";
        assert bs.get(63) == false : "Bit 63 should be flipped to false";
        assert bs.get(64) == false : "Bit 64 should remain false";
    }

    static void testFlipMultipleWords() {
        BitSet bs = new BitSet();
        bs.set(0);
        bs.set(100);
        bs.flip(0, 200);
        assert bs.get(0) == false : "Bit 0 should be flipped to false";
        assert bs.get(100) == false : "Bit 100 should be flipped to false";
        assert bs.get(199) == true : "Bit 199 should be flipped to true";
        assert bs.get(200) == false : "Bit 200 should remain false";
    }

    static void testFlipZeroRange() {
        BitSet bs = new BitSet();
        bs.set(10);
        bs.flip(5, 5); // no change
        assert bs.get(10) == true : "Bit 10 should remain true";
    }

    static void testFlipFullRange() {
        BitSet bs = new BitSet();
        bs.set(0, 128);
        bs.flip(0, 128);
        for (int i = 0; i < 128; i++) {
            assert bs.get(i) == false : "Bit " + i + " should be flipped to false";
        }
    }
}
