/* @test    @summary Test for BitSet.previousClearBit(int) handling negative fromIndex values and boundary conditions    @run main BitSetWitupTest */

import java.util.BitSet;

public class BitSetWitupTest {

    public static void main(String[] args) {
        testPreviousClearBitNegativeIndex();
        testPreviousClearBitIndexBeyondSize();
        testPreviousClearBitAtZero();
        testPreviousClearBitWithinRange();
    }

    static void testPreviousClearBitNegativeIndex() {
        BitSet bs = new BitSet();
        try {
            int result = bs.previousClearBit(-2);
            if (result != -1) {
                throw new AssertionError("Expected -1 for negative index, got: " + result);
            }
        } catch (IndexOutOfBoundsException e) {
            throw new AssertionError("Did not expect exception for negative index", e);
        }
    }

    static void testPreviousClearBitIndexBeyondSize() {
        BitSet bs = new BitSet();
        bs.set(0);
        bs.set(2);
        int index = 10;
        int result = bs.previousClearBit(index);
        if (result != index) {
            throw new AssertionError("Expected result to be index itself when beyond size, got: " + result);
        }
    }

    static void testPreviousClearBitAtZero() {
        BitSet bs = new BitSet();
        int result = bs.previousClearBit(0);
        if (result != 0) {
            throw new AssertionError("Expected 0 when no bits set and index 0, got: " + result);
        }
    }

    static void testPreviousClearBitWithinRange() {
        BitSet bs = new BitSet();
        bs.set(1);
        bs.set(3);
        int result = bs.previousClearBit(4);
        if (result != 2) {
            throw new AssertionError("Expected 2 as previous clear bit before 4, got: " + result);
        }
        result = bs.previousClearBit(3);
        if (result != 2) {
            throw new AssertionError("Expected 2 as previous clear bit before 3, got: " + result);
        }
    }
}
