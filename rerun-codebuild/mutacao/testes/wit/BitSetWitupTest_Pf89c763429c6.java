/*
 * @test
 * @summary Tests for java.util.BitSet.flip(int, int)
 * @run main BitSetWitupTest_Pf89c763429c6
 */

import java.util.BitSet;

public class BitSetWitupTest_Pf89c763429c6 {

    public static void main(String[] args) {
        testFlipWithValidRange();
        testFlipWithFromIndexEqualToToIndex();
        testFlipWithNegativeFromIndex();
        testFlipWithNegativeToIndex();
        testFlipWithFromIndexGreaterThanToIndex();
    }

    static void testFlipWithValidRange() {
        BitSet bs = new BitSet();
        bs.set(0);
        bs.set(2);
        bs.set(4);
        bs.flip(1, 5);
        assert bs.get(0) : "Bit 0 should be set";
        assert bs.get(1) : "Bit 1 should be flipped to set";
        assert bs.get(2) : "Bit 2 should be flipped to set";
        assert bs.get(3) : "Bit 3 should be flipped to set";
        assert bs.get(4) : "Bit 4 should be flipped to set";
        assert !bs.get(5) : "Bit 5 should remain unset";
    }

    static void testFlipWithFromIndexEqualToToIndex() {
        BitSet bs = new BitSet();
        bs.set(3);
        bs.flip(3, 3);
        assert bs.get(3) : "Bit 3 should remain set after flip with same indices";
    }

    static void testFlipWithNegativeFromIndex() {
        BitSet bs = new BitSet();
        try {
            bs.flip(-1, 2);
            assert false : "Expected IndexOutOfBoundsException for negative fromIndex";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testFlipWithNegativeToIndex() {
        BitSet bs = new BitSet();
        try {
            bs.flip(0, -2);
            assert false : "Expected IndexOutOfBoundsException for negative toIndex";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testFlipWithFromIndexGreaterThanToIndex() {
        BitSet bs = new BitSet();
        try {
            bs.flip(5, 3);
            assert false : "Expected IndexOutOfBoundsException for fromIndex > toIndex";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
