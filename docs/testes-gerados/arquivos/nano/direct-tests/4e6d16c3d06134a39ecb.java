/* @test    @summary Test for Arrays.binarySearch(char[] a, char key) method.    @run main ArraysWitupTest */

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
        char[] emptyArray = {};
        int result = Arrays.binarySearch(emptyArray, 'a');
        assert result == -1 : "Expected -1 for empty array, got " + result;
    }

    static void testBinarySearchWithSingleElementFound() {
        char[] array = {'a'};
        int result = Arrays.binarySearch(array, 'a');
        assert result == 0 : "Expected 0 for single element 'a', got " + result;
    }

    static void testBinarySearchWithSingleElementNotFound() {
        char[] array = {'a'};
        int result = Arrays.binarySearch(array, 'b');
        assert result == -1 : "Expected -1 for 'b' not found, got " + result;
    }

    static void testBinarySearchWithMultipleElementsFound() {
        char[] array = {'a', 'b', 'c', 'd'};
        int result = Arrays.binarySearch(array, 'c');
        assert result == 2 : "Expected index 2 for 'c', got " + result;
    }

    static void testBinarySearchWithMultipleElementsNotFound() {
        char[] array = {'a', 'b', 'c', 'd'};
        int result = Arrays.binarySearch(array, 'e');
        assert result == -5 : "Expected -5 for 'e' not found, got " + result;
    }
}
