/*
 * @test
 * @summary Test LongPipeline.forEach(LongConsumer) for normal and consumed state
 * @run main LongPipelineWitupTest
 */

import java.util.Spliterator;
import java.util.function.LongConsumer;
import java.util.stream.LongStream;
import java.util.stream.LongPipeline;
import java.util.stream.AbstractPipeline;
import java.util.stream.TerminalOp;
import java.util.stream.ForEachOps;

public class LongPipelineWitupTest {

    // Minimal concrete subclass of LongPipeline to allow instantiation
    static class TestLongPipeline extends LongPipeline<Long> {

        TestLongPipeline(Spliterator<Long> source, int sourceFlags, boolean parallel) {
            super(source, sourceFlags, parallel);
        }

        TestLongPipeline(AbstractPipeline<?, Long, ?> upstream, int opFlags) {
            super(upstream, opFlags);
        }

        // Override evaluate to call super.evaluate to test forEach
        @Override
        public void forEach(LongConsumer action) {
            super.forEach(action);
        }
    }

    // Dummy Spliterator<Long> that yields no elements
    static class EmptySpliterator implements Spliterator<Long> {
        public boolean tryAdvance(java.util.function.Consumer<? super Long> action) { return false; }
        public Spliterator<Long> trySplit() { return null; }
        public long estimateSize() { return 0; }
        public int characteristics() { return 0; }
    }

    public static void main(String[] args) {
        // Test 1: Normal usage - forEach with a simple LongConsumer
        TestLongPipeline pipeline = new TestLongPipeline(new EmptySpliterator(), 0, false);
        final long[] sum = new long[1];
        LongConsumer consumer = new LongConsumer() {
            public void accept(long value) {
                sum[0] += value;
            }
        };
        pipeline.forEach(consumer);
        if (sum[0] != 0) {
            throw new AssertionError("Expected sum 0 for empty source, got " + sum[0]);
        }

        // Test 2: Calling forEach twice on the same pipeline should throw IllegalStateException
        boolean threw = false;
        try {
            pipeline.forEach(consumer); // second call should fail
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalStateException on second forEach call");
        }

        System.out.println("LongPipeline.forEach tests passed.");
    }
}
