/*
 * @test
 * @summary Test for BitSet constructor with negative size to verify NegativeArraySizeException
 * @run main BitSetWitupTest
 */

import java.util.BitSet;

public class BitSetWitupTest {

    public static void main(String[] args) {
        testNegativeSizeConstructor();
        testZeroSizeConstructor();
        testPositiveSizeConstructor();
    }

    private static void testNegativeSizeConstructor() {
        boolean exceptionThrown = false;
        try {
            new BitSet(-1);
        } catch (NegativeArraySizeException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NegativeArraySizeException for negative size");
        }
    }

    private static void testZeroSizeConstructor() {
        try {
            BitSet bs = new BitSet(0);
            if (bs == null) {
                throw new AssertionError("BitSet with size 0 should not be null");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for size 0: " + e);
        }
    }

    private static void testPositiveSizeConstructor() {
        try {
            BitSet bs = new BitSet(10);
            if (bs == null) {
                throw new AssertionError("BitSet with size 10 should not be null");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for size 10: " + e);
        }
    }
}
