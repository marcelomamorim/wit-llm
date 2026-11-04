/*
 * @test
 * @summary Test java.util.Arrays.parallelPrefix(int[], IntBinaryOperator) basic and null operator cases
 * @run main java.util.ArraysWitupTest
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
        try {
            Arrays.parallelPrefix(array, null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testEmptyArray() {
        int[] array = new int[0];
        IntBinaryOperator op = (x, y) -> x + y;
        Arrays.parallelPrefix(array, op);
        if (array.length != 0) {
            throw new RuntimeException("Empty array should remain empty");
        }
    }

    private static void testSingleElementArray() {
        int[] array = {42};
        IntBinaryOperator op = (x, y) -> x + y;
        Arrays.parallelPrefix(array, op);
        if (array.length != 1 || array[0] != 42) {
            throw new RuntimeException("Single element array should remain unchanged");
        }
    }

    private static void testMultipleElementsArray() {
        int[] array = {1, 2, 3, 4};
        IntBinaryOperator op = (x, y) -> x + y;
        Arrays.parallelPrefix(array, op);
        int[] expected = {1, 3, 6, 10};
        if (array.length != expected.length) {
            throw new RuntimeException("Array length changed");
        }
        for (int i = 0; i < array.length; i++) {
            if (array[i] != expected[i]) {
                throw new RuntimeException("parallelPrefix failed at index " + i + ": expected " + expected[i] + ", got " + array[i]);
            }
        }
    }
}
