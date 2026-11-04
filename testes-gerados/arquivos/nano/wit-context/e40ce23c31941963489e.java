/*
 * @test
 * @summary Tests for java.util.Arrays.binarySearch(Object[] a, Object key)
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testBinarySearchWithValidArray();
        testBinarySearchWithNullArray();
        testBinarySearchWithEmptyArray();
        testBinarySearchWithNullKey();
        testBinarySearchRangeCheck();
    }

    static void testBinarySearchWithValidArray() {
        String[] array = {"a", "b", "c"};
        int index = Arrays.binarySearch(array, "b");
        if (index != 1) {
            throw new AssertionError("Expected index 1, got " + index);
        }
    }

    static void testBinarySearchWithNullArray() {
        try {
            Arrays.binarySearch(null, "a");
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException expected) {
        }
    }

    static void testBinarySearchWithEmptyArray() {
        String[] empty = {};
        int index = Arrays.binarySearch(empty, "a");
        if (index != -1) {
            throw new AssertionError("Expected -1 for empty array, got " + index);
        }
    }

    static void testBinarySearchWithNullKey() {
        String[] array = {"a", "b", "c"};
        int index = Arrays.binarySearch(array, null);
        if (index != -1) {
            throw new AssertionError("Expected -1 for null key, got " + index);
        }
    }

    static void testBinarySearchRangeCheck() {
        String[] array = {"a", "b", "c"};
        try {
            Arrays.binarySearch(array, 1, 0, "b");
            throw new AssertionError("Expected IllegalArgumentException for fromIndex > toIndex");
        } catch (IllegalArgumentException expected) {
        }
        try {
            Arrays.binarySearch(array, -1, 2, "b");
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for fromIndex < 0");
        } catch (ArrayIndexOutOfBoundsException expected) {
        }
        try {
            Arrays.binarySearch(array, 0, 4, "b");
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for toIndex > array.length");
        } catch (ArrayIndexOutOfBoundsException expected) {
        }
        try {
            Arrays.binarySearch(array, 2, 1, "b");
            throw new AssertionError("Expected IllegalArgumentException for fromIndex > toIndex");
        } catch (IllegalArgumentException expected) {
        }
    }
}
