/* @test    @summary Test for Arrays.parallelSetAll method to verify correct array population.    @run main ArraysWitupTest */

import java.util.Arrays;
import java.util.stream.IntStream;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testParallelSetAllWithZeroLengthArray();
        testParallelSetAllWithPositiveLengthArray();
        testParallelSetAllWithNullGenerator();
    }

    static void testParallelSetAllWithZeroLengthArray() {
        double[] array = new double[0];
        Arrays.parallelSetAll(array, i -> i * 2.0);
        assert array.length == 0 : "Array of length 0 should remain empty";
    }

    static void testParallelSetAllWithPositiveLengthArray() {
        double[] array = new double[5];
        Arrays.parallelSetAll(array, i -> i * 1.5);
        double[] expected = {0.0, 1.5, 3.0, 4.5, 6.0};
        for (int i = 0; i < array.length; i++) {
            assert array[i] == expected[i] : "Mismatch at index " + i;
        }
    }

    static void testParallelSetAllWithNullGenerator() {
        double[] array = {1.0, 2.0};
        try {
            Arrays.parallelSetAll(array, null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
