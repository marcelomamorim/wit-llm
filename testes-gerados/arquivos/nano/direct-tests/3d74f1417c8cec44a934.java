/* @test    @summary Test Arrays.parallelPrefix with a simple addition operation    @run main ArraysWitupTest */

import java.util.Arrays;
import java.util.function.IntBinaryOperator;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testParallelPrefixSum();
        testParallelPrefixEmptyArray();
        testParallelPrefixNullOperator();
    }

    static void testParallelPrefixSum() {
        int[] array = {1, 2, 3, 4, 5};
        IntBinaryOperator sumOperator = (a, b) -> a + b;
        Arrays.parallelPrefix(array, sumOperator);
        int[] expected = {1, 3, 6, 10, 15};
        if (!Arrays.equals(array, expected)) {
            throw new AssertionError("Failed: Expected " + java.util.Arrays.toString(expected) + " but got " + java.util.Arrays.toString(array));
        }
    }

    static void testParallelPrefixEmptyArray() {
        int[] array = {};
        IntBinaryOperator sumOperator = (a, b) -> a + b;
        Arrays.parallelPrefix(array, sumOperator);
        int[] expected = {};
        if (!Arrays.equals(array, expected)) {
            throw new AssertionError("Failed on empty array: Expected " + java.util.Arrays.toString(expected) + " but got " + java.util.Arrays.toString(array));
        }
    }

    static void testParallelPrefixNullOperator() {
        int[] array = {1, 2, 3};
        boolean exceptionThrown = false;
        try {
            Arrays.parallelPrefix(array, null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when operator is null");
        }
    }
}
