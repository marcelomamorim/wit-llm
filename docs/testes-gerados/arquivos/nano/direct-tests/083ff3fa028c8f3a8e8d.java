/* @test    @summary Test for Arrays.binarySearch with custom comparator    @run main ArraysWitupTest */

import java.util.Arrays;
import java.util.Comparator;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testBinarySearchWithComparator();
        testBinarySearchEmptyArray();
        testBinarySearchSingleElementFound();
        testBinarySearchSingleElementNotFound();
    }

    static void testBinarySearchWithComparator() {
        String[] array = {"apple", "banana", "cherry"};
        Comparator<String> comparator = String.CASE_INSENSITIVE_ORDER;
        int index = Arrays.binarySearch(array, "Banana", comparator);
        if (index != 1) {
            throw new AssertionError("Expected index 1, but got " + index);
        }
    }

    static void testBinarySearchEmptyArray() {
        String[] emptyArray = {};
        Comparator<String> comparator = String.CASE_INSENSITIVE_ORDER;
        int index = Arrays.binarySearch(emptyArray, "test", comparator);
        if (index != -1) {
            throw new AssertionError("Expected -1 for empty array, but got " + index);
        }
    }

    static void testBinarySearchSingleElementFound() {
        String[] array = {"hello"};
        Comparator<String> comparator = String.CASE_INSENSITIVE_ORDER;
        int index = Arrays.binarySearch(array, "HELLO", comparator);
        if (index != 0) {
            throw new AssertionError("Expected index 0, but got " + index);
        }
    }

    static void testBinarySearchSingleElementNotFound() {
        String[] array = {"hello"};
        Comparator<String> comparator = String.CASE_INSENSITIVE_ORDER;
        int index = Arrays.binarySearch(array, "world", comparator);
        if (index != -1) {
            throw new AssertionError("Expected -1, but got " + index);
        }
    }
}
