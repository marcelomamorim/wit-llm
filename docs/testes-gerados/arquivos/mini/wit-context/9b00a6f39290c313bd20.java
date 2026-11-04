/*
 * @test
 * @summary Test LongPipeline.peek(LongConsumer) for null argument and normal behavior
 * @run main java.util.stream.LongPipelineWitupTest
 */

import java.util.Objects;
import java.util.Spliterator;
import java.util.stream.LongPipeline;
import java.util.stream.StreamShape;
import java.util.stream.AbstractPipeline;
import java.util.stream.Sink;
import java.util.function.LongConsumer;

public class LongPipelineWitupTest {

    // Minimal subclass of LongPipeline to allow testing peek method
    static class TestLongPipeline extends LongPipeline<Object> {
        TestLongPipeline() {
            super(new Spliterator<Long>() {
                public boolean tryAdvance(java.util.function.Consumer<? super Long> action) { return false; }
                public Spliterator<Long> trySplit() { return null; }
                public long estimateSize() { return 0; }
                public int characteristics() { return 0; }
            }, 0, false);
        }

        // Implement abstract method to satisfy AbstractPipeline
        @Override
        public void forEach(LongConsumer action) {
            // no-op
        }
    }

    public static void main(String[] args) {
        testPeekNullAction();
        testPeekReturnsNonNullStream();
    }

    private static void testPeekNullAction() {
        LongPipeline<?> pipeline = new TestLongPipeline();
        try {
            pipeline.peek(null);
            throw new RuntimeException("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testPeekReturnsNonNullStream() {
        LongPipeline<?> pipeline = new TestLongPipeline();
        LongConsumer action = new LongConsumer() {
            public void accept(long value) {
                // do nothing
            }
        };
        LongStream result = pipeline.peek(action);
        if (result == null) {
            throw new RuntimeException("peek should not return null");
        }
    }
}
