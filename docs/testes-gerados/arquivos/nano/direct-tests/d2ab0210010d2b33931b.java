/*
 * @test
 * @summary Tests the skip method of IntPipeline for various inputs.
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntStream;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testSkipWithPositiveN();
        testSkipWithZero();
        testSkipWithNegativeN();
        testSkipWithLargeN();
    }

    static void testSkipWithPositiveN() {
        IntStream stream = IntStream.of(1, 2, 3, 4, 5);
        IntStream result = stream.skip(2);
        int[] expected = {3, 4, 5};
        int[] actual = result.toArray();
        assert java.util.Arrays.equals(expected, actual) : "Failed: skip(2)";
    }

    static void testSkipWithZero() {
        IntStream stream = IntStream.of(10, 20, 30);
        IntStream result = stream.skip(0);
        int[] expected = {10, 20, 30};
        int[] actual = result.toArray();
        assert java.util.Arrays.equals(expected, actual) : "Failed: skip(0)";
    }

    static void testSkipWithNegativeN() {
        boolean exceptionThrown = false;
        try {
            IntStream stream = IntStream.of(1, 2, 3);
            stream.skip(-1).toArray();
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Failed: skip(-1) did not throw";
    }

    static void testSkipWithLargeN() {
        IntStream stream = IntStream.of(1, 2, 3);
        IntStream result = stream.skip(10);
        int[] expected = {};
        int[] actual = result.toArray();
        assert java.util.Arrays.equals(expected, actual) : "Failed: skip(10)";
    }
}
