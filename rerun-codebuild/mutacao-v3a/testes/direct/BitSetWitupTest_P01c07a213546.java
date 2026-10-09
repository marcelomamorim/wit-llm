/*
 * @test
 * @summary Tests for java.util.BitSet.get(int, int) method.
 * @run main BitSetWitupTest_P01c07a213546
 */

import java.util.BitSet;

public class BitSetWitupTest_P01c07a213546 {

    public static void main(String[] args) {
        testGetEmptyRange();
        testGetRangeWithinBitSet();
        testGetRangeExceedsLength();
        testGetSingleBit();
        testGetFullRange();
    }

    private static void testGetEmptyRange() {
        BitSet bs = new BitSet();
        bs.set(0);
        bs.set(2);
        BitSet result = bs.get(1, 1);
        assert result.length() == 0 : "Empty range should return empty BitSet";
    }

    private static void testGetRangeWithinBitSet() {
        BitSet bs = new BitSet();
        bs.set(1);
        bs.set(3);
        BitSet result = bs.get(1, 4);
        assert result.get(0) : "Bit 1 should be set";
        assert result.get(2) : "Bit 3 should be set";
        assert result.length() == 3 : "Result length should be 3";
    }

    private static void testGetRangeExceedsLength() {
        BitSet bs = new BitSet();
        bs.set(5);
        BitSet result = bs.get(3, 10);
        assert result.get(2) : "Bit 5 (index 5-3=2) should be set in result";
        assert result.length() == 3 : "Result length should be 3";
    }

    private static void testGetSingleBit() {
        BitSet bs = new BitSet();
        bs.set(4);
        BitSet result = bs.get(4, 5);
        assert result.get(0) : "Single bit 4 should be set";
        assert result.length() == 1 : "Result length should be 1";
    }

    private static void testGetFullRange() {
        BitSet bs = new BitSet();
        for (int i = 0; i < 10; i++) {
            bs.set(i);
        }
        BitSet result = bs.get(0, 10);
        for (int i = 0; i < 10; i++) {
            assert result.get(i) : "Bit " + i + " should be set";
        }
        assert result.length() == 10 : "Result length should be 10";
    }
}
