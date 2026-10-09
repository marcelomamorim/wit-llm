/* @test    @summary Test the flip method of BitSet for various ranges    @run main BitSetWitupTest_P57557c4ba980 */

import java.util.BitSet;

public class BitSetWitupTest_P57557c4ba980 {

    public static void main(String[] args) {
        testFlipSingleBit();
        testFlipRange();
        testFlipEmptyRange();
        testFlipFullRange();
        testFlipOverlappingRange();
        System.out.println("All tests passed.");
    }

    static void testFlipSingleBit() {
        BitSet bs = new BitSet();
        bs.set(10);
        bs.flip(10, 11);
        if (bs.get(10)) {
            throw new AssertionError("Failed: flip single bit - bit 10 should be false after flip.");
        }
    }

    static void testFlipRange() {
        BitSet bs = new BitSet();
        bs.set(5, 15);
        bs.flip(5, 15);
        for (int i = 5; i < 15; i++) {
            if (bs.get(i)) {
                throw new AssertionError("Failed: flip range - bit " + i + " should be false.");
            }
        }
        bs.flip(0, 5);
        for (int i = 0; i < 5; i++) {
            if (!bs.get(i)) {
                throw new AssertionError("Failed: flip range - bit " + i + " should be true.");
            }
        }
    }

    static void testFlipEmptyRange() {
        BitSet bs = new BitSet();
        bs.set(20);
        bs.flip(20, 20); // no change
        if (!bs.get(20)) {
            throw new AssertionError("Failed: flip empty range - bit 20 should remain true.");
        }
    }

    static void testFlipFullRange() {
        BitSet bs = new BitSet();
        bs.set(0, 64);
        bs.flip(0, 64);
        for (int i = 0; i < 64; i++) {
            if (bs.get(i)) {
                throw new AssertionError("Failed: flip full range - bit " + i + " should be false.");
            }
        }
    }

    static void testFlipOverlappingRange() {
        BitSet bs = new BitSet();
        bs.set(10, 20);
        bs.set(15, 25);
        bs.flip(12, 22);
        for (int i = 10; i < 15; i++) {
            if (!bs.get(i)) {
                throw new AssertionError("Failed: flip overlapping range - bit " + i + " should be true.");
            }
        }
        for (int i = 15; i < 20; i++) {
            if (bs.get(i)) {
                throw new AssertionError("Failed: flip overlapping range - bit " + i + " should be false.");
            }
        }
        for (int i = 20; i < 25; i++) {
            if (!bs.get(i)) {
                throw new AssertionError("Failed: flip overlapping range - bit " + i + " should be true.");
            }
        }
    }
}
