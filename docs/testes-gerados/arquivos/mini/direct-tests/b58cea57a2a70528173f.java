/*
 * @test
 * @summary Test LongPipeline.limit(long) method behavior
 * @run main LongPipelineWitupTest
 */

import java.util.stream.LongStream;
import java.util.stream.StreamSupport;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.LongConsumer;

public class LongPipelineWitupTest {

    static class TestLongPipeline extends java.util.stream.LongPipeline<Long> {
        TestLongPipeline(long[] array) {
            super(Spliterators.spliterator(array, 0), 0, false);
        }
    }

    public static void main(String[] args) {
        testLimitPositive();
        testLimitZero();
        testLimitNegative();
    }

    private static void testLimitPositive() {
        long[] data = {1L, 2L, 3L, 4L, 5L};
        TestLongPipeline pipeline = new TestLongPipeline(data);
        LongStream limited = pipeline.limit(3L);
        long[] result = limited.toArray();
        if (result.length != 3) {
            throw new AssertionError("Expected length 3 but got " + result.length);
        }
        if (result[0] != 1L || result[1] != 2L || result[2] != 3L) {
            throw new AssertionError("Expected first three elements 1,2,3 but got " + java.util.Arrays.toString(result));
        }
    }

    private static void testLimitZero() {
        long[] data = {10L, 20L, 30L};
        TestLongPipeline pipeline = new TestLongPipeline(data);
        LongStream limited = pipeline.limit(0L);
        long[] result = limited.toArray();
        if (result.length != 0) {
            throw new AssertionError("Expected empty array but got length " + result.length);
        }
    }

    private static void testLimitNegative() {
        long[] data = {1L, 2L};
        TestLongPipeline pipeline = new TestLongPipeline(data);
        try {
            pipeline.limit(-1L);
            throw new AssertionError("Expected IllegalArgumentException for negative limit");
        } catch (IllegalArgumentException e) {
            if (!e.getMessage().equals("-1")) {
                throw new AssertionError("Exception message mismatch: " + e.getMessage());
            }
        }
    }
}
