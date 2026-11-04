/*
 * @test
 * @summary Test java.util.Arrays.fill(char[] a, int fromIndex, int toIndex, char val)
 * @run main ArraysWitupTest
 */

public class ArraysWitupTest {
    public static void main(String[] args) {
        testFillNormalRange();
        testFillEmptyRange();
        testFillFullArray();
        testFillSingleElement();
        testFillInvalidRange();
    }

    private static void testFillNormalRange() {
        char[] arr = {'a', 'b', 'c', 'd', 'e'};
        java.util.Arrays.fill(arr, 1, 4, 'z');
        if (arr[0] != 'a' || arr[1] != 'z' || arr[2] != 'z' || arr[3] != 'z' || arr[4] != 'e') {
            throw new Error("testFillNormalRange failed: " + java.util.Arrays.toString(arr));
        }
    }

    private static void testFillEmptyRange() {
        char[] arr = {'x', 'y', 'z'};
        java.util.Arrays.fill(arr, 2, 2, 'w'); // empty range, no change expected
        if (arr[0] != 'x' || arr[1] != 'y' || arr[2] != 'z') {
            throw new Error("testFillEmptyRange failed: " + java.util.Arrays.toString(arr));
        }
    }

    private static void testFillFullArray() {
        char[] arr = new char[5];
        java.util.Arrays.fill(arr, 0, arr.length, 'q');
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != 'q') {
                throw new Error("testFillFullArray failed at index " + i);
            }
        }
    }

    private static void testFillSingleElement() {
        char[] arr = {'m', 'n', 'o'};
        java.util.Arrays.fill(arr, 1, 2, 'p');
        if (arr[0] != 'm' || arr[1] != 'p' || arr[2] != 'o') {
            throw new Error("testFillSingleElement failed: " + java.util.Arrays.toString(arr));
        }
    }

    private static void testFillInvalidRange() {
        char[] arr = {'a', 'b', 'c'};
        boolean threw = false;
        try {
            java.util.Arrays.fill(arr, -1, 2, 'x');
        } catch (ArrayIndexOutOfBoundsException e) {
            threw = true;
        } catch (IllegalArgumentException e) {
            threw = true;
        } catch (IndexOutOfBoundsException e) {
            threw = true;
        }
        if (!threw) {
            throw new Error("testFillInvalidRange failed: expected exception for negative fromIndex");
        }

        threw = false;
        try {
            java.util.Arrays.fill(arr, 1, 4, 'x');
        } catch (ArrayIndexOutOfBoundsException e) {
            threw = true;
        } catch (IllegalArgumentException e) {
            threw = true;
        } catch (IndexOutOfBoundsException e) {
            threw = true;
        }
        if (!threw) {
            throw new Error("testFillInvalidRange failed: expected exception for toIndex > length");
        }

        threw = false;
        try {
            java.util.Arrays.fill(arr, 2, 1, 'x');
        } catch (IllegalArgumentException e) {
            threw = true;
        } catch (IndexOutOfBoundsException e) {
            threw = true;
        }
        if (!threw) {
            throw new Error("testFillInvalidRange failed: expected exception for fromIndex > toIndex");
        }
    }
}
