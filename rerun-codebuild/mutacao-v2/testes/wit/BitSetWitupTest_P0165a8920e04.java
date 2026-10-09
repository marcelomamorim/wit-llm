/*
 * @test
 * @summary Test BitSet.nextClearBit(int) for valid and invalid inputs
 * @run main BitSetWitupTest_P0165a8920e04
 */

import java.util.BitSet;

public class BitSetWitupTest_P0165a8920e04 {
    public static void main(String[] args) {
        testNextClearBitValid();
        testNextClearBitFromIndexNegative();
        System.out.println("All tests passed.");
    }

    private static void testNextClearBitValid() {
        BitSet bs = new BitSet();
        // Initially all bits are clear, so nextClearBit(0) should be 0
        int result = bs.nextClearBit(0);
        if (result != 0) {
            throw new AssertionError("Expected nextClearBit(0) to be 0 but got " + result);
        }

        // Set bit 0, next clear bit from 0 should be 1
        bs.set(0);
        result = bs.nextClearBit(0);
        if (result != 1) {
            throw new AssertionError("Expected nextClearBit(0) to be 1 after setting bit 0 but got " + result);
        }

        // Set bits 1 through 10
        for (int i = 1; i <= 10; i++) {
            bs.set(i);
        }
        // Next clear bit from 0 should be 11
        result = bs.nextClearBit(0);
        if (result != 11) {
            throw new AssertionError("Expected nextClearBit(0) to be 11 after setting bits 0-10 but got " + result);
        }

        // Next clear bit from 5 should be 11
        result = bs.nextClearBit(5);
        if (result != 11) {
            throw new AssertionError("Expected nextClearBit(5) to be 11 but got " + result);
        }

        // Next clear bit from 11 should be 11 (bit 11 is clear)
        result = bs.nextClearBit(11);
        if (result != 11) {
            throw new AssertionError("Expected nextClearBit(11) to be 11 but got " + result);
        }

        // Next clear bit from a large index beyond wordsInUse should return fromIndex
        int largeIndex = 1000;
        result = bs.nextClearBit(largeIndex);
        if (result != largeIndex) {
            throw new AssertionError("Expected nextClearBit(" + largeIndex + ") to be " + largeIndex + " but got " + result);
        }
    }

    private static void testNextClearBitFromIndexNegative() {
        BitSet bs = new BitSet();
        try {
            bs.nextClearBit(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for nextClearBit(-1) but none thrown");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
            String msg = e.getMessage();
            if (msg == null || !msg.contains("fromIndex < 0")) {
                throw new AssertionError("Exception message does not contain expected text: " + msg);
            }
        }
    }
}
