/*
 * @test
 * @summary Test LongPipeline.sum() method behavior
 * @run main java.util.stream.LongPipelineWitupTest
 */

import java.util.stream.LongStream;

public class LongPipelineWitupTest {
    public static void main(String[] args) {
        testSumEmptyStream();
        testSumSingleElement();
        testSumMultipleElements();
        testSumWithNegativeValues();
        testSumWithLargeValues();
    }

    private static void testSumEmptyStream() {
        LongStream stream = LongStream.empty();
        long result = stream.sum();
        if (result != 0L) {
            throw new AssertionError("sum() of empty stream should be 0, got: " + result);
        }
    }

    private static void testSumSingleElement() {
        LongStream stream = LongStream.of(42L);
        long result = stream.sum();
        if (result != 42L) {
            throw new AssertionError("sum() of single element stream should be element itself, got: " + result);
        }
    }

    private static void testSumMultipleElements() {
        LongStream stream = LongStream.of(1L, 2L, 3L, 4L, 5L);
        long result = stream.sum();
        if (result != 15L) {
            throw new AssertionError("sum() of 1..5 should be 15, got: " + result);
        }
    }

    private static void testSumWithNegativeValues() {
        LongStream stream = LongStream.of(-1L, -2L, 3L, 4L);
        long result = stream.sum();
        if (result != 4L) {
            throw new AssertionError("sum() with negative values failed, expected 4, got: " + result);
        }
    }

    private static void testSumWithLargeValues() {
        // Test sum with large values to check for overflow behavior
        long large1 = Long.MAX_VALUE - 1;
        long large2 = 1L;
        LongStream stream = LongStream.of(large1, large2);
        long result = stream.sum();
        if (result != Long.MAX_VALUE) {
            throw new AssertionError("sum() with large values failed, expected Long.MAX_VALUE, got: " + result);
        }
    }
}
