/*
 * @test
 * @summary Test java.util.Arrays.parallelPrefix(long[], LongBinaryOperator) for expected exceptions and basic behavior
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
        testBasicOperation();
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
        long[] empty = new long[0];
        LongBinaryOperator op = new LongBinaryOperator() {
            public long applyAsLong(long left, long right) {
                return left + right;
            }
        };
        // Should not throw and leave array unchanged
        Arrays.parallelPrefix(empty, op);
        if (empty.length != 0) {
            throw new RuntimeException("Empty array length changed");
        }
    }

    private static void testSingleElementArray() {
        long[] single = {42L};
        LongBinaryOperator op = new LongBinaryOperator() {
            public long applyAsLong(long left, long right) {
                return left + right;
            }
        };
        Arrays.parallelPrefix(single, op);
        if (single.length != 1 || single[0] != 42L) {
            throw new RuntimeException("Single element array changed incorrectly");
        }
    }

    private static void testBasicOperation() {
        long[] array = {1L, 2L, 3L, 4L};
        LongBinaryOperator op = new LongBinaryOperator() {
            public long applyAsLong(long left, long right) {
                return left + right;
            }
        };
        Arrays.parallelPrefix(array, op);
        // After parallelPrefix with sum operator, array should be prefix sums: [1, 3, 6, 10]
        long[] expected = {1L, 3L, 6L, 10L};
        for (int i = 0; i < array.length; i++) {
            if (array[i] != expected[i]) {
                throw new RuntimeException("parallelPrefix failed at index " + i + ": expected " + expected[i] + ", got " + array[i]);
            }
        }
    }
}
