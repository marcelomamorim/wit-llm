/*
 * @test
 * @summary Test java.util.BitSet.clear(int) for correct behavior and exception
 * @run main BitSetWitupTest
 */

import java.util.BitSet;

public class BitSetWitupTest {
    public static void main(String[] args) {
        testClearNegativeIndex();
        testClearIndexOutOfRange();
        testClearValidBit();
    }

    private static void testClearNegativeIndex() {
        BitSet bs = new BitSet();
        try {
            bs.clear(-1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testClearIndexOutOfRange() {
        BitSet bs = new BitSet();
        // Clear a bit index beyond wordsInUse (empty BitSet), should do nothing and not throw
        bs.clear(1000); // no exception expected
        // Also test with a bit set bit beyond wordsInUse
        bs.set(10);
        bs.clear(1000); // no exception expected
        // The bit 10 should remain set
        if (!bs.get(10)) {
            throw new RuntimeException("Bit 10 should remain set after clearing out-of-range bit");
        }
    }

    private static void testClearValidBit() {
        BitSet bs = new BitSet();
        bs.set(5);
        if (!bs.get(5)) {
            throw new RuntimeException("Bit 5 should be set before clear");
        }
        bs.clear(5);
        if (bs.get(5)) {
            throw new RuntimeException("Bit 5 should be cleared after clear");
        }
    }
}
