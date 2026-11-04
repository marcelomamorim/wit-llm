/*
 * @test
 * @summary Test java.util.Arrays.fill(int[], int, int, int) for boundary and exception cases
 * @run main ArraysWitupTest
 */

public class ArraysWitupTest {
    public static void main(String[] args) {
        testFillNormal();
        testFillFromIndexGreaterThanToIndex();
        testFillFromIndexNegative();
        testFillToIndexGreaterThanLength();
    }

    private static void testFillNormal() {
        int[] arr = new int[5];
        java.util.Arrays.fill(arr, 1, 4, 7);
        for (int i = 0; i < arr.length; i++) {
            if (i >= 1 && i < 4) {
                if (arr[i] != 7) {
                    throw new AssertionError("Expected 7 at index " + i + " but found " + arr[i]);
                }
            } else {
                if (arr[i] != 0) {
                    throw new AssertionError("Expected 0 at index " + i + " but found " + arr[i]);
                }
            }
        }
    }

    private static void testFillFromIndexGreaterThanToIndex() {
        int[] arr = new int[3];
        try {
            java.util.Arrays.fill(arr, 2, 1, 5);
            throw new AssertionError("Expected IllegalArgumentException for fromIndex > toIndex");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    private static void testFillFromIndexNegative() {
        int[] arr = new int[3];
        try {
            java.util.Arrays.fill(arr, -1, 2, 5);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for fromIndex < 0");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected
        }
    }

    private static void testFillToIndexGreaterThanLength() {
        int[] arr = new int[3];
        try {
            java.util.Arrays.fill(arr, 1, 5, 5);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for toIndex > array length");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected
        }
    }
}
