/* @test    @summary Test nextClearBit method of BitSet for various scenarios    @run main BitSetWitupTest_P039c2d1ca87a */

import java.util.BitSet;

public class BitSetWitupTest_P039c2d1ca87a {
    public static void main(String[] args) {
        testNextClearBitEmpty();
        testNextClearBitSetBits();
        testNextClearBitUnsetBits();
        testNextClearBitNegativeIndex();
        testNextClearBitBeyondSize();
    }

    static void testNextClearBitEmpty() {
        BitSet bs = new BitSet();
        int index = 0;
        int result = bs.nextClearBit(index);
        if (result != index) {
            throw new AssertionError("Failed: nextClearBit on empty BitSet at index " + index + ", got " + result);
        }
    }

    static void testNextClearBitSetBits() {
        BitSet bs = new BitSet();
        bs.set(0);
        bs.set(1);
        bs.set(3);
        int index = 0;
        int result = bs.nextClearBit(index);
        if (result != 2) {
            throw new AssertionError("Failed: nextClearBit after setting bits at 0,1,3 starting at 0, got " + result);
        }
        result = bs.nextClearBit(2);
        if (result != 2) {
            throw new AssertionError("Failed: nextClearBit at index 2, got " + result);
        }
        result = bs.nextClearBit(4);
        if (result != 4) {
            throw new AssertionError("Failed: nextClearBit at index 4, got " + result);
        }
    }

    static void testNextClearBitUnsetBits() {
        BitSet bs = new BitSet();
        bs.set(5);
        int result = bs.nextClearBit(0);
        if (result != 0) {
            throw new AssertionError("Failed: nextClearBit from 0, expected 0, got " + result);
        }
        result = bs.nextClearBit(5);
        if (result != 5) {
            throw new AssertionError("Failed: nextClearBit at 5, got " + result);
        }
        result = bs.nextClearBit(6);
        if (result != 6) {
            throw new AssertionError("Failed: nextClearBit at 6, got " + result);
        }
    }

    static void testNextClearBitNegativeIndex() {
        try {
            BitSet bs = new BitSet();
            bs.nextClearBit(-1);
            throw new AssertionError("Failed: Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testNextClearBitBeyondSize() {
        BitSet bs = new BitSet();
        bs.set(0);
        bs.set(10);
        int result = bs.nextClearBit(11);
        if (result != 11) {
            throw new AssertionError("Failed: nextClearBit at 11, expected 11, got " + result);
        }
    }
}
