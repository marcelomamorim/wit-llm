/*
 * @test
 * @summary Test BitSet.nextSetBit(int) for valid and invalid inputs
 * @run main BitSetWitupTest_Peeb4a0015b74
 */

import java.util.BitSet;

public class BitSetWitupTest_Peeb4a0015b74 {
    public static void main(String[] args) {
        testNextSetBitNegativeIndex();
        testNextSetBitEmptyBitSet();
        testNextSetBitSetBits();
    }

    private static void testNextSetBitNegativeIndex() {
        BitSet bs = new BitSet();
        try {
            bs.nextSetBit(-1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for negative fromIndex");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testNextSetBitEmptyBitSet() {
        BitSet bs = new BitSet();
        // No bits set, nextSetBit should return -1 for any non-negative index
        if (bs.nextSetBit(0) != -1) {
            throw new RuntimeException("Expected -1 for empty BitSet starting at 0");
        }
        if (bs.nextSetBit(10) != -1) {
            throw new RuntimeException("Expected -1 for empty BitSet starting at 10");
        }
    }

    private static void testNextSetBitSetBits() {
        BitSet bs = new BitSet();
        bs.set(0);
        bs.set(5);
        bs.set(64);

        // nextSetBit from 0 should find 0
        if (bs.nextSetBit(0) != 0) {
            throw new RuntimeException("Expected nextSetBit(0) to be 0");
        }

        // nextSetBit from 1 should find 5
        if (bs.nextSetBit(1) != 5) {
            throw new RuntimeException("Expected nextSetBit(1) to be 5");
        }

        // nextSetBit from 6 should find 64
        if (bs.nextSetBit(6) != 64) {
            throw new RuntimeException("Expected nextSetBit(6) to be 64");
        }

        // nextSetBit from 65 should return -1
        if (bs.nextSetBit(65) != -1) {
            throw new RuntimeException("Expected nextSetBit(65) to be -1");
        }
    }
}
