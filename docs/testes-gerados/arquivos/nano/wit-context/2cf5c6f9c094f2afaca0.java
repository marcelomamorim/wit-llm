/*
 * @test
 * @summary Tests for java.util.BitSet.clear(int, int)
 * @run main BitSetWitupTest
 */

import java.util.BitSet;

public class BitSetWitupTest {

    public static void main(String[] args) {
        testClearWithValidRange();
        testClearWithFromIndexGreaterThanToIndex();
        testClearWithFromIndexNegative();
        testClearWithToIndexNegative();
        testClearWithFromIndexEqualToToIndex();
    }

    static void testClearWithValidRange() {
        BitSet bs = new BitSet();
        bs.set(0);
        bs.set(1);
        bs.set(2);
        bs.set(10);
        bs.clear(0, 3);
        assert !bs.get(0) : "Bit 0 should be cleared";
        assert !bs.get(1) : "Bit 1 should be cleared";
        assert !bs.get(2) : "Bit 2 should be cleared";
        assert bs.get(10) : "Bit 10 should remain set";
    }

    static void testClearWithFromIndexGreaterThanToIndex() {
        BitSet bs = new BitSet();
        bs.set(0);
        bs.set(1);
        try {
            bs.clear(5, 3);
            assert false : "Expected IndexOutOfBoundsException";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testClearWithFromIndexNegative() {
        BitSet bs = new BitSet();
        bs.set(0);
        try {
            bs.clear(-1, 2);
            assert false : "Expected IndexOutOfBoundsException";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testClearWithToIndexNegative() {
        BitSet bs = new BitSet();
        bs.set(0);
        try {
            bs.clear(0, -2);
            assert false : "Expected IndexOutOfBoundsException";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testClearWithFromIndexEqualToToIndex() {
        BitSet bs = new BitSet();
        bs.set(0);
        bs.set(5);
        bs.clear(5, 5); // should do nothing
        assert bs.get(0) : "Bit 0 should remain set";
        assert bs.get(5) : "Bit 5 should remain set";
    }
}
