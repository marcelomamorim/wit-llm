/*
 * @test
 * @summary Test java.util.Arrays.parallelPrefix(T[] array, BinaryOperator<T> op) for null operator and basic functionality
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.function.BinaryOperator;
import java.util.Objects;

public class ArraysWitupTest {
    public static void main(String[] args) {
        testNullOperator();
        testEmptyArray();
        testSimpleSum();
    }

    private static void testNullOperator() {
        String[] array = {"a", "b"};
        try {
            Arrays.parallelPrefix(array, null);
            throw new RuntimeException("Expected NullPointerException for null operator");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testEmptyArray() {
        Integer[] empty = new Integer[0];
        // Should not throw and leave array unchanged
        Arrays.parallelPrefix(empty, (x, y) -> x + y);
        if (empty.length != 0) {
            throw new RuntimeException("Empty array length changed");
        }
    }

    private static void testSimpleSum() {
        Integer[] array = {1, 2, 3, 4};
        Arrays.parallelPrefix(array, new BinaryOperator<Integer>() {
            public Integer apply(Integer a, Integer b) {
                return a + b;
            }
        });
        int[] expected = {1, 3, 6, 10};
        for (int i = 0; i < array.length; i++) {
            if (!Objects.equals(array[i], expected[i])) {
                throw new RuntimeException("parallelPrefix failed at index " + i + ": expected " + expected[i] + ", got " + array[i]);
            }
        }
    }
}
