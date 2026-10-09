/* @test    @summary Test for BitSet.get(int, int) method handling of index bounds    @run main BitSetWitupTest_Pb38f70163811 */

import java.util.BitSet;

public class BitSetWitupTest_Pb38f70163811 {

    public static void main(String[] args) {
        testGetWithNegativeFromIndex();
        testGetWithFromIndexEqualToToIndex();
        testGetWithToIndexExceedingLength();
        System.out.println("All tests passed.");
    }

    private static void testGetWithNegativeFromIndex() {
        BitSet bs = new BitSet();
        bs.set(0, 10); // set bits 0-9
        try {
            bs.get(-1, 5);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative fromIndex");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testGetWithFromIndexEqualToToIndex() {
        BitSet bs = new BitSet();
        bs.set(0, 10);
        BitSet result = bs.get(5, 5);
        if (result.length() != 0) {
            throw new AssertionError("Expected empty BitSet when fromIndex == toIndex");
        }
    }

    private static void testGetWithToIndexExceedingLength() {
        BitSet bs = new BitSet();
        bs.set(0, 10);
        int length = bs.length();
        BitSet result = bs.get(5, length + 10);
        // Should not throw, and toIndex should be capped at length
        if (result == null) {
            throw new AssertionError("Result should not be null");
        }
        // Check that result length is as expected
        if (result.length() != length - 5) {
            throw new AssertionError("Unexpected length of result BitSet");
        }
    }
}
