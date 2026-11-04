/* @test    @summary Test for Arrays.parallelSetAll method    @run main ArraysWitupTest */

import java.util.Arrays;
import java.util.stream.IntStream;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testParallelSetAllWithNullGenerator();
        testParallelSetAllWithEmptyArray();
        testParallelSetAllWithPositiveIndices();
        testParallelSetAllWithNegativeIndices();
    }

    static void testParallelSetAllWithNullGenerator() {
        int[] array = {1, 2, 3};
        try {
            Arrays.parallelSetAll(array, null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Test passes
        }
    }

    static void testParallelSetAllWithEmptyArray() {
        Integer[] array = new Integer[0];
        Arrays.parallelSetAll(array, i -> i * 10);
        if (array.length != 0) {
            throw new AssertionError("Array should be empty");
        }
    }

    static void testParallelSetAllWithPositiveIndices() {
        Integer[] array = new Integer[5];
        Arrays.parallelSetAll(array, i -> i * 2);
        for (int i = 0; i < array.length; i++) {
            if (!array[i].equals(i * 2)) {
                throw new AssertionError("Mismatch at index " + i + ": expected " + (i * 2) + " but got " + array[i]);
            }
        }
    }

    static void testParallelSetAllWithNegativeIndices() {
        Integer[] array = new Integer[3];
        Arrays.parallelSetAll(array, i -> -i);
        for (int i = 0; i < array.length; i++) {
            if (!array[i].equals(-i)) {
                throw new AssertionError("Mismatch at index " + i + ": expected " + (-i) + " but got " + array[i]);
            }
        }
    }
}
