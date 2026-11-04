/*
 * @test
 * @summary Test java.util.Arrays.parallelPrefix(int[], IntBinaryOperator) for basic correctness and null operator
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.Objects;
import java.util.function.IntBinaryOperator;

public class ArraysWitupTest {
    public static void main(String[] args) {
        testNullOperator();
        testEmptyArray();
        testSingleElementArray();
        testMultipleElementsArray();
    }

    private static void testNullOperator() {
        int[] array = {1, 2, 3};
        IntBinaryOperator op = null;
        try {
            Arrays.parallelPrefix(array, op);
            throw new AssertionError("Expected NullPointerException for null operator");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testEmptyArray() {
        int[] array = new int[0];
        IntBinaryOperator op = (x, y) -> x + y;
        Arrays.parallelPrefix(array, op);
        if (array.length != 0) {
            throw new AssertionError("Empty array should remain empty after parallelPrefix");
        }
    }

    private static void testSingleElementArray() {
        int[] array = {42};
        IntBinaryOperator op = (x, y) -> x + y;
        Arrays.parallelPrefix(array, op);
        if (array.length != 1 || array[0] != 42) {
            throw new AssertionError("Single element array should remain unchanged");
        }
    }

    private static void testMultipleElementsArray() {
        int[] array = {1, 2, 3, 4};
        IntBinaryOperator op = (x, y) -> x + y;
        Arrays.parallelPrefix(array, op);
        int[] expected = {1, 3, 6, 10};
        if (array.length != expected.length) {
            throw new AssertionError("Array length changed after parallelPrefix");
        }
        for (int i = 0; i < array.length; i++) {
            if (array[i] != expected[i]) {
                throw new AssertionError("parallelPrefix result incorrect at index " + i + ": expected " + expected[i] + ", got " + array[i]);
            }
        }
    }
}
