/* @test    @summary Test for Arrays.parallelSetAll method to verify array initialization with generator function    @run main ArraysWitupTest */

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.function.IntUnaryOperator;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testParallelSetAllWithPositiveSize();
        testParallelSetAllWithZeroSize();
        testParallelSetAllWithNullGenerator();
    }

    static void testParallelSetAllWithPositiveSize() {
        int[] array = new int[5];
        IntUnaryOperator generator = i -> i * 2;
        Arrays.parallelSetAll(array, generator);
        int[] expected = {0, 2, 4, 6, 8};
        if (!Arrays.equals(array, expected)) {
            throw new AssertionError("Failed: array does not match expected values.");
        }
    }

    static void testParallelSetAllWithZeroSize() {
        int[] array = new int[0];
        IntUnaryOperator generator = i -> i * 2;
        Arrays.parallelSetAll(array, generator);
        if (array.length != 0) {
            throw new AssertionError("Failed: zero-length array should remain zero-length.");
        }
    }

    static void testParallelSetAllWithNullGenerator() {
        int[] array = {1, 2, 3};
        try {
            Arrays.parallelSetAll(array, null);
            throw new AssertionError("Expected NullPointerException was not thrown.");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
