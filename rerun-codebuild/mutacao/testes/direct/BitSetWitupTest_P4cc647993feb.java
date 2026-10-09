/*
 * @test
 * @summary Tests for BitSet.get(int, int)
 * @run main BitSetWitupTest_P4cc647993feb
 */

import java.util.BitSet;

public class BitSetWitupTest_P4cc647993feb {
    public static void main(String[] args) {
        testGetEmptyRange();
        testGetRangeWithinSize();
        testGetRangeExceedingSize();
        testGetRangeWithUnalignedStart();
        testGetRangeWithUnalignedEnd();
        testGetRangeFull();
    }

    static void testGetEmptyRange() {
        BitSet bs = new BitSet();
        bs.set(10);
        bs.set(20);
        BitSet result = bs.get(10, 10);
        assert result.length() == 0 : "Expected empty BitSet";
    }

    static void testGetRangeWithinSize() {
        BitSet bs = new BitSet();
        bs.set(5);
        bs.set(6);
        bs.set(7);
        BitSet result = bs.get(5, 8);
        assert result.get(0) : "Bit 5 should be set";
        assert result.get(1) : "Bit 6 should be set";
        assert result.get(2) : "Bit 7 should be set";
        assert result.length() == 3 : "Result length should be 3";
    }

    static void testGetRangeExceedingSize() {
        BitSet bs = new BitSet();
        bs.set(0);
        bs.set(3);
        BitSet result = bs.get(0, 10);
        assert result.get(0) : "Bit 0 should be set";
        assert result.get(3) : "Bit 3 should be set";
        assert result.length() == 4 : "Result length should be 4";
    }

    static void testGetRangeWithUnalignedStart() {
        BitSet bs = new BitSet();
        bs.set(2);
        bs.set(3);
        bs.set(4);
        BitSet result = bs.get(2, 5);
        assert result.get(0) : "Bit 2 should be set";
        assert result.get(1) : "Bit 3 should be set";
        assert result.get(2) : "Bit 4 should be set";
        assert result.length() == 3 : "Result length should be 3";
    }

    static void testGetRangeWithUnalignedEnd() {
        BitSet bs = new BitSet();
        bs.set(1);
        bs.set(2);
        bs.set(3);
        BitSet result = bs.get(1, 4);
        assert result.get(0) : "Bit 1 should be set";
        assert result.get(1) : "Bit 2 should be set";
        assert result.get(2) : "Bit 3 should be set";
        assert result.length() == 3 : "Result length should be 3";
    }

    static void testGetRangeFull() {
        BitSet bs = new BitSet();
        bs.set(0);
        bs.set(1);
        bs.set(2);
        BitSet result = bs.get(0, bs.length());
        assert result.get(0) : "Bit 0 should be set";
        assert result.get(1) : "Bit 1 should be set";
        assert result.get(2) : "Bit 2 should be set";
        assert result.length() == 3 : "Result length should be 3";
    }
}
