/*
 * @test
 * @summary Test BitSet.get(int) for valid and invalid indices
 * @run main BitSetWitupTest
 */

import java.util.BitSet;

public class BitSetWitupTest {
    public static void main(String[] args) {
        testGetNegativeIndex();
        testGetUnsetBit();
        testGetSetBit();
        testGetBitIndexAtBoundary();
        System.out.println("All tests passed.");
    }

    private static void testGetNegativeIndex() {
        BitSet bs = new BitSet();
        try {
            bs.get(-1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testGetUnsetBit() {
        BitSet bs = new BitSet(64);
        // No bits set, get should return false
        if (bs.get(0)) {
            throw new RuntimeException("Expected false for bit 0 when unset");
        }
        if (bs.get(63)) {
            throw new RuntimeException("Expected false for bit 63 when unset");
        }
    }

    private static void testGetSetBit() {
        BitSet bs = new BitSet(64);
        bs.set(0);
        bs.set(63);
        if (!bs.get(0)) {
            throw new RuntimeException("Expected true for bit 0 after set");
        }
        if (!bs.get(63)) {
            throw new RuntimeException("Expected true for bit 63 after set");
        }
    }

    private static void testGetBitIndexAtBoundary() {
        BitSet bs = new BitSet(128);
        bs.set(64); // set bit at next word boundary
        if (!bs.get(64)) {
            throw new RuntimeException("Expected true for bit 64 after set");
        }
        if (bs.get(65)) {
            throw new RuntimeException("Expected false for bit 65 when unset");
        }
    }
}
