/* @test    @summary Test for Arrays.parallelPrefix with a simple addition operation    @run main ArraysWitupTest */

import java.util.Arrays;
import java.util.function.BinaryOperator;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testParallelPrefixSum();
        testParallelPrefixEmptyArray();
        testParallelPrefixSingleElement();
    }

    private static void testParallelPrefixSum() {
        Integer[] array = {1, 2, 3, 4, 5};
        BinaryOperator<Integer> sumOp = (a, b) -> a + b;
        Arrays.parallelPrefix(array, sumOp);
        int[] expected = {1, 3, 6, 10, 15};
        for (int i = 0; i < array.length; i++) {
            if (array[i] != expected[i]) {
                throw new AssertionError("Failed at index " + i + ": expected " + expected[i] + " but got " + array[i]);
            }
        }
    }

    private static void testParallelPrefixEmptyArray() {
        Integer[] array = {};
        BinaryOperator<Integer> sumOp = (a, b) -> a + b;
        Arrays.parallelPrefix(array, sumOp);
        if (array.length != 0) {
            throw new AssertionError("Array should remain empty after prefix operation.");
        }
    }

    private static void testParallelPrefixSingleElement() {
        Integer[] array = {42};
        BinaryOperator<Integer> sumOp = (a, b) -> a + b;
        Arrays.parallelPrefix(array, sumOp);
        if (array[0] != 42) {
            throw new AssertionError("Single element array should remain unchanged.");
        }
    }
}
