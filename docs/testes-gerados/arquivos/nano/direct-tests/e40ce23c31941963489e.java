/* @test    @summary Test for Arrays.binarySearch(Object[] a, Object key) method    @run main ArraysWitupTest */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testBinarySearchFound();
        testBinarySearchNotFound();
        testBinarySearchEmptyArray();
        testBinarySearchNullArray();
        testBinarySearchNullKey();
    }

    static void testBinarySearchFound() {
        String[] array = {"apple", "banana", "cherry"};
        int index = Arrays.binarySearch(array, "banana");
        if (index != 1) {
            throw new AssertionError("Expected index 1, but got " + index);
        }
    }

    static void testBinarySearchNotFound() {
        String[] array = {"apple", "banana", "cherry"};
        int index = Arrays.binarySearch(array, "date");
        if (index >= 0) {
            throw new AssertionError("Expected negative value for not found, but got " + index);
        }
    }

    static void testBinarySearchEmptyArray() {
        String[] array = {};
        int index = Arrays.binarySearch(array, "any");
        if (index != -1) {
            throw new AssertionError("Expected -1 for empty array, but got " + index);
        }
    }

    static void testBinarySearchNullArray() {
        try {
            Arrays.binarySearch((Object[]) null, "key");
            throw new AssertionError("Expected NullPointerException for null array");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testBinarySearchNullKey() {
        String[] array = {"a", "b", "c"};
        int index = Arrays.binarySearch(array, null);
        if (index >= 0) {
            throw new AssertionError("Expected negative value for null key, but got " + index);
        }
    }
}
