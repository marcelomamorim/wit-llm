/*
 * @test
 * @summary Test java.util.Arrays.copyOfRange(short[], int, int) for normal and exceptional cases
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {
    public static void main(String[] args) {
        testCopyOfRangeNormal();
        testCopyOfRangeEmptyRange();
        testCopyOfRangeNegativeLength();
        System.out.println("All tests passed.");
    }

    private static void testCopyOfRangeNormal() {
        short[] original = {1, 2, 3, 4, 5};
        short[] copy = Arrays.copyOfRange(original, 1, 4);
        if (copy.length != 3) {
            throw new AssertionError("Expected length 3 but got " + copy.length);
        }
        if (copy[0] != 2 || copy[1] != 3 || copy[2] != 4) {
            throw new AssertionError("Copied elements do not match expected values");
        }
    }

    private static void testCopyOfRangeEmptyRange() {
        short[] original = {10, 20, 30};
        short[] copy = Arrays.copyOfRange(original, 2, 2);
        if (copy.length != 0) {
            throw new AssertionError("Expected empty array but got length " + copy.length);
        }
    }

    private static void testCopyOfRangeNegativeLength() {
        short[] original = {1, 2, 3};
        try {
            Arrays.copyOfRange(original, 3, 2);
            throw new AssertionError("Expected IllegalArgumentException for from > to");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
