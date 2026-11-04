/* @test    @summary Test for Arrays.binarySearch(short[] a, short key) method    @run main ArraysWitupTest */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testBinarySearchWithEmptyArray();
        testBinarySearchWithSingleElementFound();
        testBinarySearchWithSingleElementNotFound();
        testBinarySearchWithMultipleElementsFound();
        testBinarySearchWithMultipleElementsNotFound();
    }

    static void testBinarySearchWithEmptyArray() {
        short[] array = {};
        short key = 1;
        int result = Arrays.binarySearch(array, key);
        assert result == -1 : "Expected -1 for empty array, got " + result;
    }

    static void testBinarySearchWithSingleElementFound() {
        short[] array = {5};
        short key = 5;
        int result = Arrays.binarySearch(array, key);
        assert result == 0 : "Expected 0 for single element found, got " + result;
    }

    static void testBinarySearchWithSingleElementNotFound() {
        short[] array = {5};
        short key = 10;
        int result = Arrays.binarySearch(array, key);
        assert result == -2 : "Expected -2 for single element not found, got " + result;
    }

    static void testBinarySearchWithMultipleElementsFound() {
        short[] array = {1, 3, 5, 7, 9};
        short key = 5;
        int result = Arrays.binarySearch(array, key);
        assert result == 2 : "Expected index 2 for key 5, got " + result;
    }

    static void testBinarySearchWithMultipleElementsNotFound() {
        short[] array = {1, 3, 5, 7, 9};
        short key = 6;
        int result = Arrays.binarySearch(array, key);
        assert result == -4 : "Expected -4 for key 6, got " + result;
    }
}
