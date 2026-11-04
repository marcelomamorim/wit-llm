/*
 * @test
 * @summary Test the sum() method of LongPipeline for correctness and overflow handling
 * @run main LongPipelineWitupTest
 */

import java.util.stream.LongStream;
import java.util.stream.LongPipeline;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testSumWithPositiveNumbers();
        testSumWithNegativeNumbers();
        testSumWithMixedNumbers();
        testSumWithOverflow();
    }

    static void testSumWithPositiveNumbers() {
        long[] data = {1L, 2L, 3L, 4L, 5L};
        long expected = 15L;
        long result = LongStream.of(data).collect(LongStream::sum);
        if (result != expected) {
            throw new AssertionError("Expected sum: " + expected + ", but got: " + result);
        }
    }

    static void testSumWithNegativeNumbers() {
        long[] data = {-1L, -2L, -3L, -4L, -5L};
        long expected = -15L;
        long result = LongStream.of(data).collect(LongStream::sum);
        if (result != expected) {
            throw new AssertionError("Expected sum: " + expected + ", but got: " + result);
        }
    }

    static void testSumWithMixedNumbers() {
        long[] data = {10L, -2L, 3L, -4L, 5L};
        long expected = 12L;
        long result = LongStream.of(data).collect(LongStream::sum);
        if (result != expected) {
            throw new AssertionError("Expected sum: " + expected + ", but got: " + result);
        }
    }

    static void testSumWithOverflow() {
        long max = Long.MAX_VALUE;
        long[] data = {max, 1L};
        long result = LongStream.of(data).collect(LongStream::sum);
        long expected = Long.MIN_VALUE; // overflow wraps around
        if (result != expected) {
            throw new AssertionError("Expected overflow sum: " + expected + ", but got: " + result);
        }
    }
}
