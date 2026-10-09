/* @test    @summary Test the get(int, int) method of java.util.BitSet    @run main BitSetWitupTest_Pb38f70163811 */

import java.util.BitSet;

public class BitSetWitupTest_Pb38f70163811 {
    public static void main(String[] args) {
        testGetEmptyRange();
        testGetFullRange();
        testGetPartialRange();
        testGetRangeBeyondLength();
        testGetZeroLength();
    }

    static void testGetEmptyRange() {
        BitSet bs = new BitSet();
        bs.set(0);
        bs.set(10);
        BitSet result = bs.get(2, 2);
        if (result.length() != 0) {
            throw new AssertionError("Expected empty BitSet for zero-length range");
        }
    }

    static void testGetFullRange() {
        BitSet bs = new BitSet();
        bs.set(1);
        bs.set(3);
        bs.set(5);
        BitSet result = bs.get(0, bs.length());
        if (!result.get(1) || !result.get(3) || !result.get(5)) {
            throw new AssertionError("Full range get failed to include set bits");
        }
        if (result.cardinality() != 3) {
            throw new AssertionError("Expected 3 bits set in result");
        }
    }

    static void testGetPartialRange() {
        BitSet bs = new BitSet();
        bs.set(2);
        bs.set(4);
        bs.set(6);
        BitSet result = bs.get(1, 5);
        if (result.get(1) || !result.get(2) || !result.get(4) || result.get(5)) {
            throw new AssertionError("Partial range get failed");
        }
        if (result.cardinality() != 2) {
            throw new AssertionError("Expected 2 bits set in partial result");
        }
    }

    static void testGetRangeBeyondLength() {
        BitSet bs = new BitSet();
        bs.set(0);
        bs.set(10);
        BitSet result = bs.get(0, 20);
        if (!result.get(0) || !result.get(10)) {
            throw new AssertionError("Get beyond length failed to include set bits");
        }
        if (result.cardinality() != 2) {
            throw new AssertionError("Expected 2 bits in result");
        }
    }

    static void testGetZeroLength() {
        BitSet bs = new BitSet();
        bs.set(0);
        BitSet result = bs.get(0, 0);
        if (result.length() != 0) {
            throw new AssertionError("Expected empty BitSet for zero-length get");
        }
    }
}
