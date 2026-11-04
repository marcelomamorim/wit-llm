/*
 * @test
 * @summary Test java.util.BitSet.get(int) method behavior
 * @run main BitSetWitupTest
 */

import java.util.BitSet;

public class BitSetWitupTest {
    public static void main(String[] args) {
        testGetNegativeIndex();
        testGetUnsetBit();
        testGetSetBit();
        testGetBitIndexAtWordBoundary();
    }

    private static void testGetNegativeIndex() {
        BitSet bs = new BitSet();
        try {
            bs.get(-1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testGetUnsetBit() {
        BitSet bs = new BitSet();
        // No bits set, get should return false
        if (bs.get(0)) {
            throw new RuntimeException("Expected false for bit 0 when unset");
        }
        if (bs.get(100)) {
            throw new RuntimeException("Expected false for bit 100 when unset");
        }
    }

    private static void testGetSetBit() {
        BitSet bs = new BitSet();
        bs.set(5);
        bs.set(63);
        bs.set(64);
        if (!bs.get(5)) {
            throw new RuntimeException("Expected true for bit 5 after set");
        }
        if (!bs.get(63)) {
            throw new RuntimeException("Expected true for bit 63 after set");
        }
        if (!bs.get(64)) {
            throw new RuntimeException("Expected true for bit 64 after set");
        }
    }

    private static void testGetBitIndexAtWordBoundary() {
        BitSet bs = new BitSet();
        // Set bit at 63 (last bit of first word) and 64 (first bit of second word)
        bs.set(63);
        bs.set(64);
        if (!bs.get(63)) {
            throw new RuntimeException("Expected true for bit 63 at word boundary");
        }
        if (!bs.get(64)) {
            throw new RuntimeException("Expected true for bit 64 at word boundary");
        }
        // Check a bit just outside wordsInUse (e.g. 128) returns false
        if (bs.get(128)) {
            throw new RuntimeException("Expected false for bit 128 unset and outside wordsInUse");
        }
    }
}
