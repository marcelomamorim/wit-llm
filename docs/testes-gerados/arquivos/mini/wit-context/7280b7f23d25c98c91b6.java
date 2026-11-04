/*
 * @test
 * @summary Test LongPipeline.sum() method behavior and exception on consumed pipeline
 * @run main LongPipelineWitupTest
 */

import java.util.stream.LongPipeline;
import java.util.stream.StreamSupport;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Supplier;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testSumOnSimpleStream();
        testSumOnConsumedStreamThrows();
    }

    private static void testSumOnSimpleStream() {
        // Create a LongPipeline from a known Spliterator over longs 1,2,3
        Spliterator.OfLong spliterator = Spliterators.spliterator(new long[] {1L, 2L, 3L}, 0);
        LongPipeline<?> pipeline = new LongPipeline.Head<>(spliterator, 0, false);

        long result = pipeline.sum();
        if (result != 6L) {
            throw new AssertionError("Expected sum 6 but got " + result);
        }
    }

    private static void testSumOnConsumedStreamThrows() {
        // Create a LongPipeline and consume it once, then call sum() again to check for IllegalStateException
        Spliterator.OfLong spliterator = Spliterators.spliterator(new long[] {1L, 2L, 3L}, 0);
        LongPipeline<?> pipeline = new LongPipeline.Head<>(spliterator, 0, false);

        // First consumption
        long firstSum = pipeline.sum();
        if (firstSum != 6L) {
            throw new AssertionError("Expected first sum 6 but got " + firstSum);
        }

        // Second consumption should throw IllegalStateException
        try {
            pipeline.sum();
            throw new AssertionError("Expected IllegalStateException on second sum() call but none thrown");
        } catch (IllegalStateException expected) {
            // Expected exception
        }
    }

    // Minimal subclass to access LongPipeline constructor for head pipeline
    static final class Head<E_IN> extends LongPipeline<E_IN> {
        Head(Spliterator<Long> source, int sourceFlags, boolean parallel) {
            super(source, sourceFlags, parallel);
        }
    }
}
