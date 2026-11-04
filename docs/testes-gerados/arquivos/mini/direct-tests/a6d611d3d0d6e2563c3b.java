/*
 * @test
 * @summary Test java.util.Arrays.fill(int[] a, int fromIndex, int toIndex, int val) method
 * @run main ArraysWitupTest
 */

public class ArraysWitupTest {
    public static void main(String[] args) {
        testFillBasic();
        testFillEmptyRange();
        testFillFullArray();
        testFillSingleElement();
        testFillInvalidRange();
    }

    private static void testFillBasic() {
        int[] arr = new int[5];
        java.util.Arrays.fill(arr, 1, 4, 7);
        if (arr[0] != 0) throw new Error("Index 0 should remain 0");
        if (arr[1] != 7) throw new Error("Index 1 should be 7");
        if (arr[2] != 7) throw new Error("Index 2 should be 7");
        if (arr[3] != 7) throw new Error("Index 3 should be 7");
        if (arr[4] != 0) throw new Error("Index 4 should remain 0");
    }

    private static void testFillEmptyRange() {
        int[] arr = new int[] {1, 2, 3};
        java.util.Arrays.fill(arr, 2, 2, 9); // empty range, no change
        if (arr[0] != 1 || arr[1] != 2 || arr[2] != 3) throw new Error("Array should remain unchanged for empty range");
    }

    private static void testFillFullArray() {
        int[] arr = new int[3];
        java.util.Arrays.fill(arr, 0, arr.length, -5);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != -5) throw new Error("All elements should be -5");
        }
    }

    private static void testFillSingleElement() {
        int[] arr = new int[] {0, 0, 0};
        java.util.Arrays.fill(arr, 1, 2, 42);
        if (arr[0] != 0) throw new Error("Index 0 should remain 0");
        if (arr[1] != 42) throw new Error("Index 1 should be 42");
        if (arr[2] != 0) throw new Error("Index 2 should remain 0");
    }

    private static void testFillInvalidRange() {
        int[] arr = new int[3];
        boolean caught = false;
        try {
            java.util.Arrays.fill(arr, -1, 2, 1);
        } catch (ArrayIndexOutOfBoundsException e) {
            caught = true;
        }
        if (!caught) throw new Error("Expected ArrayIndexOutOfBoundsException for negative fromIndex");

        caught = false;
        try {
            java.util.Arrays.fill(arr, 1, 5, 1);
        } catch (ArrayIndexOutOfBoundsException e) {
            caught = true;
        }
        if (!caught) throw new Error("Expected ArrayIndexOutOfBoundsException for toIndex > length");

        caught = false;
        try {
            java.util.Arrays.fill(arr, 2, 1, 1);
        } catch (IllegalArgumentException e) {
            caught = true;
        }
        if (!caught) throw new Error("Expected IllegalArgumentException for fromIndex > toIndex");
    }
}
