/*
 * @test
 * @summary Test for LongPipeline.forEachOrdered method to observe behavior with valid and null LongConsumer.
 * @run main LongPipelineWitupTest
 */

import java.util.stream.LongPipeline;
import java.util.function.LongConsumer;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testForEachOrderedWithValidConsumer();
        testForEachOrderedWithNullConsumer();
    }

    static void testForEachOrderedWithValidConsumer() {
        LongConsumer consumer = new LongConsumer() {
            int count = 0;
            @Override
            public void accept(long value) {
                count++;
            }
        };
        try {
            LongPipeline pipeline = createSamplePipeline();
            pipeline.forEachOrdered(consumer);
            System.out.println("testForEachOrderedWithValidConsumer passed");
        } catch (Exception e) {
            System.err.println("testForEachOrderedWithValidConsumer failed: " + e);
        }
    }

    static void testForEachOrderedWithNullConsumer() {
        try {
            LongPipeline pipeline = createSamplePipeline();
            pipeline.forEachOrdered(null);
            System.err.println("testForEachOrderedWithNullConsumer failed: no exception thrown");
        } catch (NullPointerException e) {
            System.out.println("testForEachOrderedWithNullConsumer passed");
        } catch (Exception e) {
            System.err.println("testForEachOrderedWithNullConsumer failed: " + e);
        }
    }

    static LongPipeline createSamplePipeline() {
        // Create a simple pipeline with a source spliterator of longs
        java.util.Spliterator.OfLong spliterator = java.util.Spliterators.spliterator(new long[]{1L, 2L, 3L}, java.util.Spliterator.ORDERED);
        return new LongPipeline(spliterator, 0, false) {
            @Override
            public void evaluate(java.util.stream.TerminalOp<?, ?> op) {
                // No-op for testing
            }
        };
    }
}
