/* @test    @summary Test for BitSet.nextSetBit(int fromIndex) method behavior.    @run main BitSetWitupTest */

import java.util.BitSet;

public class BitSetWitupTest {
    public static void main(String[] args) {
        testNextSetBitWithSetBits();
        testNextSetBitWithNoSetBits();
        testNextSetBitWithNegativeIndex();
        testNextSetBitWithIndexBeyondSize();
    }

    static void testNextSetBitWithSetBits() {
        BitSet bs = new BitSet();
        bs.set(2);
        bs.set(5);
        bs.set(10);
        int result1 = bs.nextSetBit(0);
        int result2 = bs.nextSetBit(3);
        int result3 = bs.nextSetBit(6);
        int result4 = bs.nextSetBit(11);
        assert result1 == 2 : "Expected 2, got " + result1;
        assert result2 == 5 : "Expected 5, got " + result2;
        assert result3 == 10 : "Expected 10, got " + result3;
        assert result4 == -1 : "Expected -1, got " + result4;
    }

    static void testNextSetBitWithNoSetBits() {
        BitSet bs = new BitSet();
        int result = bs.nextSetBit(0);
        assert result == -1 : "Expected -1, got " + result;
    }

    static void testNextSetBitWithNegativeIndex() {
        BitSet bs = new BitSet();
        try {
            bs.nextSetBit(-1);
            assert false : "Expected IndexOutOfBoundsException";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testNextSetBitWithIndexBeyondSize() {
        BitSet bs = new BitSet();
        bs.set(0);
        int result = bs.nextSetBit(100);
        assert result == -1 : "Expected -1, got " + result;
    }
}
