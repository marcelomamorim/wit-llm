/*
 * @test
 * @summary Test java.util.BitSet.previousClearBit(int) method behavior
 * @run main BitSetWitupTest
 */

import java.util.BitSet;

public class BitSetWitupTest {
    public static void main(String[] args) {
        testNegativeFromIndex();
        testFromIndexMinusOne();
        testFromIndexBeyondWordsInUse();
        testPreviousClearBitBasic();
        testPreviousClearBitAllSet();
        testPreviousClearBitAllClear();
    }

    private static void testNegativeFromIndex() {
        BitSet bs = new BitSet();
        try {
            bs.previousClearBit(-2);
            throw new RuntimeException("Expected IndexOutOfBoundsException for fromIndex < -1");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testFromIndexMinusOne() {
        BitSet bs = new BitSet();
        int result = bs.previousClearBit(-1);
        if (result != -1) {
            throw new RuntimeException("Expected -1 for fromIndex == -1, got " + result);
        }
    }

    private static void testFromIndexBeyondWordsInUse() {
        BitSet bs = new BitSet();
        // wordsInUse is 0 initially, so fromIndex >= wordsInUse means return fromIndex
        int fromIndex = 1000;
        int result = bs.previousClearBit(fromIndex);
        if (result != fromIndex) {
            throw new RuntimeException("Expected previousClearBit to return fromIndex when fromIndex >= wordsInUse");
        }
    }

    private static void testPreviousClearBitBasic() {
        BitSet bs = new BitSet();
        // Set bits 0,1,2,3,4
        for (int i = 0; i <= 4; i++) {
            bs.set(i);
        }
        // previousClearBit from 4 should find bit 5 clear
        int result = bs.previousClearBit(4);
        if (result != 5) {
            throw new RuntimeException("Expected previousClearBit(4) to return 5, got " + result);
        }
        // previousClearBit from 0 should find bit 5 clear (since bits 0-4 set)
        result = bs.previousClearBit(0);
        if (result != 5) {
            throw new RuntimeException("Expected previousClearBit(0) to return 5, got " + result);
        }
    }

    private static void testPreviousClearBitAllSet() {
        BitSet bs = new BitSet();
        // Set bits 0 to 127 (two words fully set)
        for (int i = 0; i < 128; i++) {
            bs.set(i);
        }
        // previousClearBit from 127 should find bit 128 clear
        int result = bs.previousClearBit(127);
        if (result != 128) {
            throw new RuntimeException("Expected previousClearBit(127) to return 128, got " + result);
        }
        // previousClearBit from 0 should find bit 128 clear
        result = bs.previousClearBit(0);
        if (result != 128) {
            throw new RuntimeException("Expected previousClearBit(0) to return 128, got " + result);
        }
    }

    private static void testPreviousClearBitAllClear() {
        BitSet bs = new BitSet();
        // No bits set, previousClearBit from 10 should return 10
        int result = bs.previousClearBit(10);
        if (result != 10) {
            throw new RuntimeException("Expected previousClearBit(10) to return 10 when all bits clear, got " + result);
        }
    }
}
