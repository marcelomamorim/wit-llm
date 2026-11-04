/*
 * @test
 * @summary Test java.util.BitSet.set(int, boolean) method behavior
 * @run main BitSetWitupTest
 */

import java.util.BitSet;

public class BitSetWitupTest {
    public static void main(String[] args) {
        testSetTrue();
        testSetFalse();
        testSetToggle();
        testSetZeroIndex();
        testSetLargeIndex();
    }

    private static void testSetTrue() {
        BitSet bs = new BitSet();
        bs.set(5, true);
        if (!bs.get(5)) {
            throw new RuntimeException("Failed: bit 5 should be set to true");
        }
    }

    private static void testSetFalse() {
        BitSet bs = new BitSet();
        bs.set(10, true); // set first
        bs.set(10, false); // then clear
        if (bs.get(10)) {
            throw new RuntimeException("Failed: bit 10 should be cleared (false)");
        }
    }

    private static void testSetToggle() {
        BitSet bs = new BitSet();
        bs.set(20, true);
        if (!bs.get(20)) {
            throw new RuntimeException("Failed: bit 20 should be set to true initially");
        }
        bs.set(20, false);
        if (bs.get(20)) {
            throw new RuntimeException("Failed: bit 20 should be cleared after set to false");
        }
        bs.set(20, true);
        if (!bs.get(20)) {
            throw new RuntimeException("Failed: bit 20 should be set to true again");
        }
    }

    private static void testSetZeroIndex() {
        BitSet bs = new BitSet();
        bs.set(0, true);
        if (!bs.get(0)) {
            throw new RuntimeException("Failed: bit 0 should be set to true");
        }
        bs.set(0, false);
        if (bs.get(0)) {
            throw new RuntimeException("Failed: bit 0 should be cleared (false)");
        }
    }

    private static void testSetLargeIndex() {
        BitSet bs = new BitSet();
        int largeIndex = 1000;
        bs.set(largeIndex, true);
        if (!bs.get(largeIndex)) {
            throw new RuntimeException("Failed: bit " + largeIndex + " should be set to true");
        }
        bs.set(largeIndex, false);
        if (bs.get(largeIndex)) {
            throw new RuntimeException("Failed: bit " + largeIndex + " should be cleared (false)");
        }
    }
}
