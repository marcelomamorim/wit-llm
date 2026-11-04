/* @test    @summary Test BitSet.clear(int, int) method for correctness and edge cases    @run main BitSetWitupTest */

import java.util.BitSet;

public class BitSetWitupTest {

    public static void main(String[] args) {
        testClearEmptyRange();
        testClearSingleBit();
        testClearMultipleBits();
        testClearRangeBeyondSize();
        testClearFullRange();
        testClearPartialOverlap();
    }

    static void testClearEmptyRange() {
        BitSet bs = new BitSet();
        bs.set(10);
        bs.set(20);
        bs.clear(15, 15); // empty range, should do nothing
        assert bs.get(10) : "Bit 10 should be set";
        assert bs.get(20) : "Bit 20 should be set";
    }

    static void testClearSingleBit() {
        BitSet bs = new BitSet();
        bs.set(5);
        bs.set(8);
        bs.clear(5, 6); // clear only bit 5
        assert !bs.get(5) : "Bit 5 should be cleared";
        assert bs.get(8) : "Bit 8 should remain set";
    }

    static void testClearMultipleBits() {
        BitSet bs = new BitSet();
        for (int i = 0; i < 20; i++) {
            bs.set(i);
        }
        bs.clear(5, 15); // clear bits 5 to 14
        for (int i = 0; i < 5; i++) {
            assert bs.get(i) : "Bit " + i + " should remain set";
        }
        for (int i = 5; i < 15; i++) {
            assert !bs.get(i) : "Bit " + i + " should be cleared";
        }
        for (int i = 15; i < 20; i++) {
            assert bs.get(i) : "Bit " + i + " should remain set";
        }
    }

    static void testClearRangeBeyondSize() {
        BitSet bs = new BitSet();
        bs.set(0);
        bs.set(64);
        bs.clear(60, 70); // range beyond current size
        assert bs.get(0) : "Bit 0 should remain set";
        assert !bs.get(64) : "Bit 64 should be cleared";
    }

    static void testClearFullRange() {
        BitSet bs = new BitSet();
        for (int i = 0; i < 100; i++) {
            bs.set(i);
        }
        bs.clear(0, 100); // clear entire range
        for (int i = 0; i < 100; i++) {
            assert !bs.get(i) : "Bit " + i + " should be cleared";
        }
    }

    static void testClearPartialOverlap() {
        BitSet bs = new BitSet();
        bs.set(10);
        bs.set(20);
        bs.set(30);
        bs.clear(15, 25); // overlaps with bit 20 only
        assert bs.get(10) : "Bit 10 should remain set";
        assert !bs.get(20) : "Bit 20 should be cleared";
        assert bs.get(30) : "Bit 30 should remain set";
    }
}
