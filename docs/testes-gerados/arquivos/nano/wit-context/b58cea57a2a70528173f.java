/*
 * @test
 * @summary Test for LongPipeline.limit(long maxSize)
 * @run main LongPipelineWitupTest
 */

import java.util.stream.LongStream;
import java.util.stream.LongPipeline;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testLimitWithNegative();
        testLimitWithZero();
        testLimitWithPositive();
    }

    private static void testLimitWithNegative() {
        LongStream stream = LongStream.of(1L, 2L, 3L);
        try {
            LongStream result = stream.limit(-1L);
            // Expecting an exception, so if no exception, fail
            System.err.println("Failed: Expected IllegalArgumentException for limit(-1)");
        } catch (IllegalArgumentException e) {
            // Expected exception
            System.out.println("Passed: limit(-1) throws IllegalArgumentException");
        }
    }

    private static void testLimitWithZero() {
        LongStream stream = LongStream.of(4L, 5L, 6L);
        LongStream result = stream.limit(0L);
        long[] expected = {};
        long[] actual = result.toArray();
        if (actual.length == 0) {
            System.out.println("Passed: limit(0) results in empty stream");
        } else {
            System.err.println("Failed: limit(0) did not result in empty stream");
        }
    }

    private static void testLimitWithPositive() {
        LongStream stream = LongStream.of(7L, 8L, 9L);
        LongStream result = stream.limit(2L);
        long[] expected = {7L, 8L};
        long[] actual = result.toArray();
        if (java.util.Arrays.equals(expected, actual)) {
            System.out.println("Passed: limit(2) returns first two elements");
        } else {
            System.err.println("Failed: limit(2) did not return expected elements");
        }
    }
}
