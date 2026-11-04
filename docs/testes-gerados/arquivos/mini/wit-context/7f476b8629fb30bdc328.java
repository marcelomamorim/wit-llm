/*
 * @test
 * @summary Test LongPipeline.forEachOrdered behavior including IllegalStateException on consumed pipeline
 * @run main LongPipelineWitupTest
 */

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.LongConsumer;
import java.util.stream.LongStream;
import java.util.stream.StreamSupport;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testForEachOrderedNormal();
        testForEachOrderedConsumedPipeline();
    }

    private static void testForEachOrderedNormal() {
        // Create a LongStream from a known source
        long[] data = {1L, 2L, 3L};
        LongStream stream = LongStream.of(data);

        final long[] sum = {0L};
        stream.forEachOrdered(new LongConsumer() {
            public void accept(long value) {
                sum[0] += value;
            }
        });

        if (sum[0] != 6L) {
            throw new AssertionError("Sum mismatch: expected 6 but got " + sum[0]);
        }
    }

    private static void testForEachOrderedConsumedPipeline() {
        // Create a LongStream and consume it once
        long[] data = {4L, 5L};
        LongStream stream = LongStream.of(data);

        // Consume the stream normally
        stream.forEachOrdered(new LongConsumer() {
            public void accept(long value) {
                // no-op
            }
        });

        // Try to consume again, should throw IllegalStateException
        boolean threw = false;
        try {
            stream.forEachOrdered(new LongConsumer() {
                public void accept(long value) {
                    // no-op
                }
            });
        } catch (IllegalStateException e) {
            threw = true;
        }

        if (!threw) {
            throw new AssertionError("Expected IllegalStateException when consuming a consumed stream");
        }
    }
}
