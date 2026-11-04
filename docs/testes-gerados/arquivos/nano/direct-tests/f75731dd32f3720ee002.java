/*
 * @test
 * @summary Test the get(int bitIndex) method of java.util.BitSet
 * @run main BitSetGetMethodTest
 */

import java.util.BitSet;

public class BitSetGetMethodTest {
    public static void main(String[] args) {
        testGetBitSet();
        testGetNegativeIndex();
        testGetUninitializedBit();
    }

    static void testGetBitSet() {
        BitSet bs = new BitSet();
        bs.set(10);
        bs.set(64);
        bs.set(127);
        assert bs.get(10) : "Bit 10 should be set";
        assert bs.get(64) : "Bit 64 should be set";
        assert bs.get(127) : "Bit 127 should be set";
        assert !bs.get(0) : "Bit 0 should not be set";
        assert !bs.get(63) : "Bit 63 should not be set";
        assert !bs.get(128) : "Bit 128 should not be set";
    }

    static void testGetNegativeIndex() {
        BitSet bs = new BitSet();
        try {
            bs.get(-1);
            assert false : "Expected IndexOutOfBoundsException for negative index";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testGetUninitializedBit() {
        BitSet bs = new BitSet();
        // By default, all bits are false
        assert !bs.get(0) : "Bit 0 should not be set";
        assert !bs.get(999) : "Bit 999 should not be set";
    }
}
