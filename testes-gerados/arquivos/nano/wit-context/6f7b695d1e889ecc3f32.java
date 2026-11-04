/*
 * @test
 * @summary Test for IntPipeline.accept(int) method, focusing on normal behavior.
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntPipeline;
import java.util.stream.Sink;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testAcceptNormal();
        testAcceptNullSink();
    }

    private static void testAcceptNormal() {
        class DummySink implements Sink<Long> {
            public long value = 0;
            @Override
            public void accept(Long t) {
                value = t;
            }
            @Override
            public boolean isParallel() { return false; }
            @Override
            public void begin(long size) {}
            @Override
            public void end() {}
            @Override
            public boolean cancellationSupported() { return false; }
            @Override
            public boolean tryAdvance(java.util.function.LongConsumer action) { return false; }
            @Override
            public java.util.Spliterator<Long> spliterator() { return null; }
            @Override
            public java.util.stream.Sink<Long> trySplit() { return null; }
        }
        DummySink sink = new DummySink();
        IntPipeline<Integer> pipeline = new IntPipeline<Integer>(
            () -> java.util.Spliterators.spliteratorUnknownSize(
                new java.util.Iterator<Integer>() {
                    int current = 0;
                    public boolean hasNext() { return current < 5; }
                    public Integer next() { return current++; }
                }, 0), 0
            ), 0, false
        );
        // Override downstream with our dummy sink
        pipeline.downstream = sink;
        pipeline.accept(42);
        if (sink.value != 42L) {
            throw new AssertionError("Expected sink value to be 42, but was " + sink.value);
        }
    }

    private static void testAcceptNullSink() {
        try {
            IntPipeline<Integer> pipeline = new IntPipeline<Integer>(
                () -> java.util.Spliterators.spliteratorUnknownSize(
                    new java.util.Iterator<Integer>() {
                        int current = 0;
                        public boolean hasNext() { return current < 1; }
                        public Integer next() { return current++; }
                    }, 0), 0
                ), 0, false
            );
            pipeline.downstream = null;
            pipeline.accept(1);
            throw new AssertionError("Expected NullPointerException for null downstream");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
