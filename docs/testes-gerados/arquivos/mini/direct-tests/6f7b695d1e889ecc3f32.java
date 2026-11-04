/*
 * @test
 * @summary Test IntPipeline.accept(int) method behavior
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntPipeline;
import java.util.function.IntConsumer;

public class IntPipelineWitupTest {

    static class TestIntPipeline extends IntPipeline<Object> {
        IntConsumer downstream;

        TestIntPipeline(IntConsumer downstream) {
            super(null, 0, false);
            this.downstream = downstream;
        }

        @Override
        public void accept(int t) {
            downstream.accept((long) t);
        }
    }

    static class DownstreamConsumer implements IntConsumer {
        int lastValue = Integer.MIN_VALUE;
        boolean called = false;

        @Override
        public void accept(int value) {
            lastValue = value;
            called = true;
        }

        public void accept(long value) {
            // Cast long to int explicitly to simulate downstream.accept((long) t) call
            accept((int) value);
        }
    }

    public static void main(String[] args) {
        DownstreamConsumer downstream = new DownstreamConsumer();
        TestIntPipeline pipeline = new TestIntPipeline(downstream);

        // Test with zero
        pipeline.accept(0);
        if (!downstream.called || downstream.lastValue != 0) {
            throw new AssertionError("accept(0) failed: expected 0, got " + downstream.lastValue);
        }

        // Test with positive int
        downstream.called = false;
        pipeline.accept(12345);
        if (!downstream.called || downstream.lastValue != 12345) {
            throw new AssertionError("accept(12345) failed: expected 12345, got " + downstream.lastValue);
        }

        // Test with negative int
        downstream.called = false;
        pipeline.accept(-9876);
        if (!downstream.called || downstream.lastValue != -9876) {
            throw new AssertionError("accept(-9876) failed: expected -9876, got " + downstream.lastValue);
        }

        System.out.println("All tests passed.");
    }
}
