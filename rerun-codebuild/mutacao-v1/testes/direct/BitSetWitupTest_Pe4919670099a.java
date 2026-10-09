/* @test    @summary Test the clear(int fromIndex, int toIndex) method of BitSet for correctness and boundary cases    @run main BitSetWitupTest_Pe4919670099a */

import java.util.BitSet;

public class BitSetWitupTest_Pe4919670099a {
    public static void main(String[] args) {
        testClearBasic();
        testClearEmptyRange();
        testClearFullRange();
        testClearPartialRange();
        testClearBeyondSize();
        System.out.println("All tests passed.");
    }

    static void testClearBasic() {
        BitSet bs = new BitSet();
        bs.set(0);
        bs.set(10);
        bs.set(64);
        bs.clear(0, 1); // clear bit 0
        if (bs.get(0)) {
            throw new AssertionError("Failed: bit 0 should be cleared");
        }
        if (!bs.get(10)) {
            throw new AssertionError("Failed: bit 10 should remain set");
        }
        if (!bs.get(64)) {
            throw new AssertionError("Failed: bit 64 should remain set");
        }
    }

    static void testClearEmptyRange() {
        BitSet bs = new BitSet();
        bs.set(5);
        bs.clear(5, 5); // empty range, should do nothing
        if (!bs.get(5)) {
            throw new AssertionError("Failed: bit 5 should remain set after clearing empty range");
        }
    }

    static void testClearFullRange() {
        BitSet bs = new BitSet();
        for (int i = 0; i < 100; i++) {
            bs.set(i);
        }
        bs.clear(0, 100); // clear all bits
        for (int i = 0; i < 100; i++) {
            if (bs.get(i)) {
                throw new AssertionError("Failed: bit " + i + " should be cleared");
            }
        }
    }

    static void testClearPartialRange() {
        BitSet bs = new BitSet();
        for (int i = 0; i < 200; i++) {
            bs.set(i);
        }
        bs.clear(50, 150); // clear middle range
        for (int i = 0; i < 50; i++) {
            if (!bs.get(i)) {
                throw new AssertionError("Failed: bit " + i + " should remain set");
            }
        }
        for (int i = 50; i < 150; i++) {
            if (bs.get(i)) {
                throw new AssertionError("Failed: bit " + i + " should be cleared");
            }
        }
        for (int i = 150; i < 200; i++) {
            if (!bs.get(i)) {
                throw new AssertionError("Failed: bit " + i + " should remain set");
            }
        }
    }

    static void testClearBeyondSize() {
        BitSet bs = new BitSet();
        bs.set(0);
        bs.set(10);
        bs.set(64);
        // clear range beyond current size
        bs.clear(1000, 2000); // should do nothing
        if (!bs.get(0) || !bs.get(10) || !bs.get(64)) {
            throw new AssertionError("Failed: bits should remain set after clearing beyond size");
        }
    }
}
