/*
 * @test
 * @summary Tests for BitSet.nextClearBit method
 * @run main BitSetWitupTest
 */

import java.util.BitSet;

public class BitSetWitupTest {

    public static void main(String[] args) {
        testNextClearBitWithEmptyBitSet();
        testNextClearBitWithSetBits();
        testNextClearBitBeyondInUse();
        testNextClearBitNegativeIndex();
    }

    static void testNextClearBitWithEmptyBitSet() {
        BitSet bs = new BitSet();
        int index = 10;
        int result = bs.nextClearBit(index);
        assert result == index : "Expected next clear bit at " + index + ", but got " + result;
    }

    static void testNextClearBitWithSetBits() {
        BitSet bs = new BitSet();
        bs.set(0);
        bs.set(2);
        bs.set(4);
        int index = 0;
        int result = bs.nextClearBit(index);
        assert result == 1 : "Expected next clear bit at 1, but got " + result;
        index = 1;
        result = bs.nextClearBit(index);
        assert result == 1 : "Expected next clear bit at 1, but got " + result;
        index = 3;
        result = bs.nextClearBit(index);
        assert result == 3 : "Expected next clear bit at 3, but got " + result;
    }

    static void testNextClearBitBeyondInUse() {
        BitSet bs = new BitSet();
        bs.set(0, 5);
        int index = 10;
        int result = bs.nextClearBit(index);
        assert result == index : "Expected next clear bit at " + index + ", but got " + result;
    }

    static void testNextClearBitNegativeIndex() {
        try {
            BitSet bs = new BitSet();
            bs.nextClearBit(-1);
            assert false : "Expected IndexOutOfBoundsException for negative index";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
