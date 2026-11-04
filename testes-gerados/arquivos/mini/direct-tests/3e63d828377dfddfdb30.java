/*
 * @test
 * @summary Test java.util.Arrays.parallelPrefix(long[], LongBinaryOperator) basic and edge cases
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.Objects;
import java.util.function.LongBinaryOperator;

public class ArraysWitupTest {
    public static void main(String[] args) {
        testNullOperator();
        testEmptyArray();
        testSingleElementArray();
        testMultipleElementsArray();
        testOperatorIsAssociative();
    }

    private static void testNullOperator() {
        long[] array = {1L, 2L, 3L};
        try {
            Arrays.parallelPrefix(array, null);
            throw new RuntimeException("Expected NullPointerException for null operator");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testEmptyArray() {
        long[] array = new long[0];
        LongBinaryOperator op = (x, y) -> x + y;
        Arrays.parallelPrefix(array, op);
        if (array.length != 0) {
            throw new RuntimeException("Empty array length changed");
        }
    }

    private static void testSingleElementArray() {
        long[] array = {42L};
        LongBinaryOperator op = (x, y) -> x + y;
        Arrays.parallelPrefix(array, op);
        if (array.length != 1 || array[0] != 42L) {
            throw new RuntimeException("Single element array modified incorrectly");
        }
    }

    private static void testMultipleElementsArray() {
        long[] array = {1L, 2L, 3L, 4L};
        LongBinaryOperator op = (x, y) -> x + y;
        Arrays.parallelPrefix(array, op);
        long[] expected = {1L, 3L, 6L, 10L};
        if (array.length != expected.length) {
            throw new RuntimeException("Array length changed");
        }
        for (int i = 0; i < array.length; i++) {
            if (array[i] != expected[i]) {
                throw new RuntimeException("parallelPrefix failed at index " + i + ": expected " + expected[i] + ", got " + array[i]);
            }
        }
    }

    private static void testOperatorIsAssociative() {
        // Use multiplication operator
        long[] array = {1L, 2L, 3L, 4L};
        LongBinaryOperator op = (x, y) -> x * y;
        Arrays.parallelPrefix(array, op);
        long[] expected = {1L, 2L, 6L, 24L};
        if (array.length != expected.length) {
            throw new RuntimeException("Array length changed");
        }
        for (int i = 0; i < array.length; i++) {
            if (array[i] != expected[i]) {
                throw new RuntimeException("parallelPrefix multiplication failed at index " + i + ": expected " + expected[i] + ", got " + array[i]);
            }
        }
    }
}
