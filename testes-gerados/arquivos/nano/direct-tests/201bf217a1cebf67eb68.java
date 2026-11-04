/* @test    @summary Test for Arrays.parallelSetAll method to verify correct array initialization.    @run main ArraysWitupTest */

import java.util.Arrays;
import java.util.stream.IntStream;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testParallelSetAllWithZeroLengthArray();
        testParallelSetAllWithPositiveLength();
        testParallelSetAllWithNullGenerator();
    }

    static void testParallelSetAllWithZeroLengthArray() {
        long[] array = new long[0];
        Arrays.parallelSetAll(array, i -> i * 2L);
        assert array.length == 0 : "Array of length 0 should remain empty";
    }

    static void testParallelSetAllWithPositiveLength() {
        long[] array = new long[5];
        Arrays.parallelSetAll(array, i -> i * 10L);
        long[] expected = {0L, 10L, 20L, 30L, 40L};
        assert Arrays.equals(array, expected) : "Array elements do not match expected values";
    }

    static void testParallelSetAllWithNullGenerator() {
        long[] array = {1L, 2L};
        try {
            Arrays.parallelSetAll(array, null);
            assert false : "Expected NullPointerException when generator is null";
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
