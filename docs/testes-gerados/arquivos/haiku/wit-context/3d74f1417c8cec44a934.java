/*
 * @test
 * @summary Witup-derived behavioral tests for Arrays.parallelPrefix(int[], IntBinaryOperator)
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.function.IntBinaryOperator;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testNullOperatorThrowsNPE();
        testNullArrayThrowsNPE();
        testEmptyArrayIsUnchanged();
        testSmallPrefixSum();
        testSmallPrefixMax();
        testLargeArrayPrefixSum();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullOperatorThrowsNPE() {
        int[] array = {1, 2, 3};
        boolean thrown = false;
        try {
            Arrays.parallelPrefix(array, (IntBinaryOperator) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null operator");
        check(array[0] == 1 && array[1] == 2 && array[2] == 3,
              "array must not be modified when operator is null");
    }

    static void testNullArrayThrowsNPE() {
        boolean thrown = false;
        try {
            Arrays.parallelPrefix((int[]) null, (a, b) -> a + b);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null array");
    }

    static void testEmptyArrayIsUnchanged() {
        int[] array = new int[0];
        Arrays.parallelPrefix(array, (a, b) -> a + b);
        check(array.length == 0, "empty array length must remain 0");
    }

    static void testSmallPrefixSum() {
        int[] array = {1, 2, 3, 4};
        Arrays.parallelPrefix(array, (a, b) -> a + b);
        int[] expected = {1, 3, 6, 10};
        check(Arrays.equals(expected, array),
              "unexpected prefix sum: " + Arrays.toString(array));
    }

    static void testSmallPrefixMax() {
        int[] array = {3, 1, 4, 1, 5};
        Arrays.parallelPrefix(array, Math::max);
        int[] expected = {3, 3, 4, 4, 5};
        check(Arrays.equals(expected, array),
              "unexpected prefix max: " + Arrays.toString(array));
    }

    static void testLargeArrayPrefixSum() {
        int n = 20000;
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = 1;
        }
        Arrays.parallelPrefix(array, (a, b) -> a + b);
        for (int i = 0; i < n; i++) {
            check(array[i] == i + 1,
                  "wrong prefix value at index " + i + ": " + array[i]);
        }
    }
}
