/*
 * @test
 * @summary Test BitSet.previousClearBit(int) for boundary and exception cases
 * @run main BitSetWitupTest
 */

import java.util.BitSet;

public class BitSetWitupTest {
    public static void main(String[] args) {
        testPreviousClearBitNegativeIndex();
        testPreviousClearBitMinusOne();
        testPreviousClearBitAboveWordsInUse();
        testPreviousClearBitNormal();
    }

    private static void testPreviousClearBitNegativeIndex() {
        BitSet bs = new BitSet();
        try {
            bs.previousClearBit(-2);
            throw new RuntimeException("Expected IndexOutOfBoundsException for fromIndex < -1");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testPreviousClearBitMinusOne() {
        BitSet bs = new BitSet();
        int result = bs.previousClearBit(-1);
        if (result != -1) {
            throw new RuntimeException("Expected -1 for fromIndex == -1, got " + result);
        }
    }

    private static void testPreviousClearBitAboveWordsInUse() {
        BitSet bs = new BitSet();
        // wordsInUse is 0 initially, so any non-negative index >= 0 is >= wordsInUse
        int fromIndex = 10;
        int result = bs.previousClearBit(fromIndex);
        if (result != fromIndex) {
            throw new RuntimeException("Expected previousClearBit to return fromIndex when fromIndex >= wordsInUse, got " + result);
        }
    }

    private static void testPreviousClearBitNormal() {
        BitSet bs = new BitSet();
        // Set bits 0,1,2,3,4
        for (int i = 0; i <= 4; i++) {
            bs.set(i);
        }
        // previousClearBit from index 4 should find bit 5 clear
        int result = bs.previousClearBit(4);
        if (result != 5) {
            throw new RuntimeException("Expected previousClearBit(4) to return 5, got " + result);
        }
        // previousClearBit from index 0 should find bit 0 clear? No, bit 0 is set, so it should find -1
        result = bs.previousClearBit(0);
        if (result != -1) {
            throw new RuntimeException("Expected previousClearBit(0) to return -1, got " + result);
        }
    }
}
