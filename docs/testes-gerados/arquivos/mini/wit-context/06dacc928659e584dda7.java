/*
 * @test
 * @summary Test LongPipeline.skip(long) for argument validation and behavior
 * @run main LongPipelineWitupTest
 */

import java.util.stream.LongStream;
import java.util.stream.SliceOps;
import java.util.stream.AbstractPipeline;

public class LongPipelineWitupTest {

    // Minimal subclass to allow instantiation of LongPipeline for testing
    static class TestLongPipeline extends LongPipeline<Object> {
        TestLongPipeline() {
            super((Spliterator<Long>) null, 0, false);
        }

        @Override
        public void forEach(LongConsumer action) {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        LongPipeline<Object> pipeline = new TestLongPipeline();

        // Test skip with negative argument throws IllegalArgumentException
        try {
            pipeline.skip(-1L);
            throw new Error("Expected IllegalArgumentException for negative skip argument");
        } catch (IllegalArgumentException e) {
            // expected
        }

        // Test skip with zero returns the same instance
        LongStream resultZero = pipeline.skip(0L);
        if (resultZero != pipeline) {
            throw new Error("skip(0) should return the same LongStream instance");
        }

        // Test skip with positive argument returns a non-null LongStream
        LongStream resultPositive = pipeline.skip(5L);
        if (resultPositive == null) {
            throw new Error("skip(positive) should not return null");
        }

        // We cannot assert more about the returned stream without internal access
    }
}
