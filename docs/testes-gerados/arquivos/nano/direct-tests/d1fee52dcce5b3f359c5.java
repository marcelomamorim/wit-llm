/*
 * @test
 * @summary Test for BitSet.previousSetBit(int fromIndex)
 * @run main BitSetWitupTest
 */

import java.util.BitSet;

public class BitSetWitupTest {

    public static void main(String[] args) {
        testPreviousSetBitBasic();
        testPreviousSetBitNegativeIndex();
        testPreviousSetBitIndexOutOfBounds();
        testPreviousSetBitNoSetBits();
        testPreviousSetBitWithSetBits();
    }

    static void testPreviousSetBitBasic() {
        BitSet bs = new BitSet();
        bs.set(2);
        bs.set(5);
        bs.set(10);
        int result = bs.previousSetBit(9);
        assert result == 5 : "Expected 5, got " + result;
    }

    static void testPreviousSetBitNegativeIndex() {
        BitSet bs = new BitSet();
        bs.set(3);
        try {
            bs.previousSetBit(-1);
            assert false : "Expected IndexOutOfBoundsException";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testPreviousSetBitIndexOutOfBounds() {
        BitSet bs = new BitSet();
        bs.set(4);
        int result = bs.previousSetBit(0);
        assert result == -1 : "Expected -1, got " + result;
    }

    static void testPreviousSetBitNoSetBits() {
        BitSet bs = new BitSet();
        int result = bs.previousSetBit(10);
        assert result == -1 : "Expected -1, got " + result;
    }

    static void testPreviousSetBitWithSetBits() {
        BitSet bs = new BitSet();
        bs.set(1);
        bs.set(3);
        bs.set(7);
        int result = bs.previousSetBit(6);
        assert result == 3 : "Expected 3, got " + result;
    }
}
