/*
 * @test    @summary Test the sum() method of IntPipeline for correctness.
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntPipeline;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testSumWithPositiveNumbers();
        testSumWithNegativeNumbers();
        testSumWithMixedNumbers();
        testSumWithEmptyStream();
    }

    static void testSumWithPositiveNumbers() {
        int[] data = {1, 2, 3, 4, 5};
        int expectedSum = 15;
        int result = createStream(data).sum();
        if (result != expectedSum) {
            throw new AssertionError("Expected sum: " + expectedSum + ", but got: " + result);
        }
    }

    static void testSumWithNegativeNumbers() {
        int[] data = {-1, -2, -3, -4, -5};
        int expectedSum = -15;
        int result = createStream(data).sum();
        if (result != expectedSum) {
            throw new AssertionError("Expected sum: " + expectedSum + ", but got: " + result);
        }
    }

    static void testSumWithMixedNumbers() {
        int[] data = {10, -10, 20, -20, 0};
        int expectedSum = 0;
        int result = createStream(data).sum();
        if (result != expectedSum) {
            throw new AssertionError("Expected sum: " + expectedSum + ", but got: " + result);
        }
    }

    static void testSumWithEmptyStream() {
        int[] data = {};
        int expectedSum = 0;
        int result = createStream(data).sum();
        if (result != expectedSum) {
            throw new AssertionError("Expected sum: " + expectedSum + ", but got: " + result);
        }
    }

    static IntPipeline.OfInt createStream(int[] data) {
        return java.util.Arrays.stream(data);
    }
}
