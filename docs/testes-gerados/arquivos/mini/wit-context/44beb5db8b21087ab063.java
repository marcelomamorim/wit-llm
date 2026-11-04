/*
 * @test
 * @summary Test java.util.Arrays.fill(char[], int, int, char) for boundary and exception cases
 * @run main ArraysWitupTest
 */

public class ArraysWitupTest {
    public static void main(String[] args) {
        testFillBasic();
        testFillFromIndexGreaterThanToIndex();
        testFillFromIndexNegative();
        testFillToIndexGreaterThanLength();
    }

    private static void testFillBasic() {
        char[] arr = new char[] {'a', 'b', 'c', 'd', 'e'};
        java.util.Arrays.fill(arr, 1, 4, 'z');
        if (arr[0] != 'a' || arr[1] != 'z' || arr[2] != 'z' || arr[3] != 'z' || arr[4] != 'e') {
            throw new RuntimeException("Basic fill test failed");
        }
    }

    private static void testFillFromIndexGreaterThanToIndex() {
        char[] arr = new char[] {'a', 'b', 'c'};
        try {
            java.util.Arrays.fill(arr, 2, 1, 'x');
            throw new RuntimeException("Expected IllegalArgumentException not thrown when fromIndex > toIndex");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testFillFromIndexNegative() {
        char[] arr = new char[] {'a', 'b', 'c'};
        try {
            java.util.Arrays.fill(arr, -1, 2, 'x');
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException not thrown when fromIndex < 0");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testFillToIndexGreaterThanLength() {
        char[] arr = new char[] {'a', 'b', 'c'};
        try {
            java.util.Arrays.fill(arr, 1, 5, 'x');
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException not thrown when toIndex > array length");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }
}
