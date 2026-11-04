/*
 * @test
 * @summary Test BitSet(int nbits) constructor for valid and invalid inputs
 * @run main BitSetWitupTest
 */

import java.util.BitSet;

public class BitSetWitupTest {
    public static void main(String[] args) {
        testValidConstruction();
        testNegativeSizeThrows();
        System.out.println("All tests passed.");
    }

    private static void testValidConstruction() {
        // Test with zero size
        BitSet bs0 = new BitSet(0);
        if (bs0.length() != 0) {
            throw new AssertionError("BitSet(0) should have length 0");
        }

        // Test with positive size
        BitSet bs10 = new BitSet(10);
        if (bs10.length() != 0) {
            throw new AssertionError("New BitSet(10) should have length 0");
        }

        // Test that bits can be set within the initial size
        bs10.set(9);
        if (!bs10.get(9)) {
            throw new AssertionError("Bit 9 should be set");
        }

        // Test that bits outside initial size can be set (BitSet grows)
        bs10.set(100);
        if (!bs10.get(100)) {
            throw new AssertionError("Bit 100 should be set");
        }
    }

    private static void testNegativeSizeThrows() {
        try {
            new BitSet(-1);
            throw new AssertionError("Expected NegativeArraySizeException for negative nbits");
        } catch (NegativeArraySizeException e) {
            // Expected
        }

        try {
            new BitSet(Integer.MIN_VALUE);
            throw new AssertionError("Expected NegativeArraySizeException for negative nbits");
        } catch (NegativeArraySizeException e) {
            // Expected
        }
    }
}
