/* @test    @summary Test previousClearBit method of BitSet for various scenarios    @run main BitSetWitupTest */

import java.util.BitSet;

public class BitSetWitupTest {

    public static void main(String[] args) {
        testPreviousClearBitWithSetBits();
        testPreviousClearBitWithAllClear();
        testPreviousClearBitWithNoClearBits();
        testPreviousClearBitWithNegativeIndex();
        testPreviousClearBitWithIndexBeyondSize();
    }

    static void testPreviousClearBitWithSetBits() {
        BitSet bs = new BitSet();
        bs.set(0);
        bs.set(2);
        bs.set(4);
        // bits: 0, 2, 4 are set; others are clear
        int result = bs.previousClearBit(5);
        // The previous clear bit before index 5 should be 3
        if (result != 3) {
            throw new AssertionError("Expected 3, but got " + result);
        }
    }

    static void testPreviousClearBitWithAllClear() {
        BitSet bs = new BitSet();
        // All bits are clear by default
        int result = bs.previousClearBit(10);
        // Should return 10 - 1 = 9, since all bits are clear
        if (result != 9) {
            throw new AssertionError("Expected 9, but got " + result);
        }
    }

    static void testPreviousClearBitWithNoClearBits() {
        BitSet bs = new BitSet();
        // Set all bits up to 10
        for (int i = 0; i <= 10; i++) {
            bs.set(i);
        }
        // Now, no clear bits before index 11
        int result = bs.previousClearBit(11);
        // Should return 10, the last clear bit before 11
        if (result != 10) {
            throw new AssertionError("Expected 10, but got " + result);
        }
    }

    static void testPreviousClearBitWithNegativeIndex() {
        BitSet bs = new BitSet();
        try {
            bs.previousClearBit(-2);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    static void testPreviousClearBitWithIndexBeyondSize() {
        BitSet bs = new BitSet();
        bs.set(0);
        bs.set(1);
        int result = bs.previousClearBit(100);
        // The previous clear bit before index 100 should be 99
        if (result != 99) {
            throw new AssertionError("Expected 99, but got " + result);
        }
    }
}
