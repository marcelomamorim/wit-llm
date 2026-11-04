/*
 * @test
 * @summary Test java.util.Arrays.parallelPrefix with various inputs
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.Objects;
import java.util.function.BinaryOperator;

public class ArraysWitupTest {
    public static void main(String[] args) {
        testNullOperator();
        testEmptyArray();
        testSingleElementArray();
        testMultipleElementsArray();
        testOperatorIsAssociative();
    }

    private static void testNullOperator() {
        String[] arr = {"a", "b"};
        try {
            Arrays.parallelPrefix(arr, null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testEmptyArray() {
        Integer[] arr = new Integer[0];
        Arrays.parallelPrefix(arr, (x, y) -> x + y);
        if (arr.length != 0) {
            throw new RuntimeException("Empty array length changed");
        }
    }

    private static void testSingleElementArray() {
        Integer[] arr = {42};
        Arrays.parallelPrefix(arr, (x, y) -> x + y);
        if (arr.length != 1 || !arr[0].equals(42)) {
            throw new RuntimeException("Single element array changed incorrectly");
        }
    }

    private static void testMultipleElementsArray() {
        Integer[] arr = {1, 2, 3, 4};
        Arrays.parallelPrefix(arr, (x, y) -> x + y);
        int[] expected = {1, 3, 6, 10};
        for (int i = 0; i < arr.length; i++) {
            if (!arr[i].equals(expected[i])) {
                throw new RuntimeException("parallelPrefix failed at index " + i + ": expected " + expected[i] + ", got " + arr[i]);
            }
        }
    }

    private static void testOperatorIsAssociative() {
        // Use multiplication operator to verify correctness
        Integer[] arr = {1, 2, 3, 4};
        Arrays.parallelPrefix(arr, (x, y) -> x * y);
        int[] expected = {1, 2, 6, 24};
        for (int i = 0; i < arr.length; i++) {
            if (!arr[i].equals(expected[i])) {
                throw new RuntimeException("parallelPrefix multiplication failed at index " + i + ": expected " + expected[i] + ", got " + arr[i]);
            }
        }
    }
}
