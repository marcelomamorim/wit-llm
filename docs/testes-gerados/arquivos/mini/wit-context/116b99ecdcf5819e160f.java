/*
 * @test
 * @summary Test java.util.Arrays.copyOfRange(int[], int, int) for valid and invalid ranges
 * @run main ArraysWitupTest
 */

public class ArraysWitupTest {
    public static void main(String[] args) {
        testCopyOfRangeValid();
        testCopyOfRangeEmptyRange();
        testCopyOfRangeNegativeLength();
        testCopyOfRangeFromEqualsTo();
        testCopyOfRangeFromZeroToLength();
    }

    private static void testCopyOfRangeValid() {
        int[] original = {1, 2, 3, 4, 5};
        int[] copy = java.util.Arrays.copyOfRange(original, 1, 4);
        if (copy.length != 3) {
            throw new RuntimeException("Expected length 3 but got " + copy.length);
        }
        if (copy[0] != 2 || copy[1] != 3 || copy[2] != 4) {
            throw new RuntimeException("Copied elements do not match expected values");
        }
    }

    private static void testCopyOfRangeEmptyRange() {
        int[] original = {10, 20, 30};
        int[] copy = java.util.Arrays.copyOfRange(original, 2, 2);
        if (copy.length != 0) {
            throw new RuntimeException("Expected empty array but got length " + copy.length);
        }
    }

    private static void testCopyOfRangeNegativeLength() {
        int[] original = {1, 2, 3};
        try {
            java.util.Arrays.copyOfRange(original, 3, 2);
            throw new RuntimeException("Expected IllegalArgumentException for negative length");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    private static void testCopyOfRangeFromEqualsTo() {
        int[] original = {5, 6, 7};
        int[] copy = java.util.Arrays.copyOfRange(original, 1, 1);
        if (copy.length != 0) {
            throw new RuntimeException("Expected empty array when from == to");
        }
    }

    private static void testCopyOfRangeFromZeroToLength() {
        int[] original = {7, 8, 9};
        int[] copy = java.util.Arrays.copyOfRange(original, 0, original.length);
        if (copy.length != original.length) {
            throw new RuntimeException("Expected copy length to match original length");
        }
        for (int i = 0; i < copy.length; i++) {
            if (copy[i] != original[i]) {
                throw new RuntimeException("Element mismatch at index " + i);
            }
        }
    }
}
