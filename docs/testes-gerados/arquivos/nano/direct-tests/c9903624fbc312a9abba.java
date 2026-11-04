/*
 * @test
 * @summary Tests the set(int) method of java.util.BitSet
 * @run main BitSetWitupTest
 */

import java.util.BitSet;

public class BitSetWitupTest {

    public static void main(String[] args) {
        testSetAtZero();
        testSetAtPositive();
        testSetAtNegative();
        testSetAtLarge();
    }

    static void testSetAtZero() {
        BitSet bs = new BitSet();
        bs.set(0);
        if (!bs.get(0)) {
            throw new AssertionError("Bit at index 0 should be set.");
        }
    }

    static void testSetAtPositive() {
        BitSet bs = new BitSet();
        bs.set(10);
        if (!bs.get(10)) {
            throw new AssertionError("Bit at index 10 should be set.");
        }
    }

    static void testSetAtNegative() {
        BitSet bs = new BitSet();
        try {
            bs.set(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index.");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testSetAtLarge() {
        BitSet bs = new BitSet();
        int largeIndex = Integer.MAX_VALUE;
        try {
            bs.set(largeIndex);
            if (!bs.get(largeIndex)) {
                throw new AssertionError("Bit at large index should be set.");
            }
        } catch (IndexOutOfBoundsException e) {
            throw new AssertionError("Unexpected exception for large index.");
        }
    }
}
