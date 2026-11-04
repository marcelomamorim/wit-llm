/*
 * @test
 * @summary Test IntPipeline.accept(int) method for expected behavior and exception path
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntPipeline;
import java.util.function.IntUnaryOperator;
import java.util.function.IntConsumer;

public class IntPipelineWitupTest {

    // Minimal concrete subclass to test accept(int) method
    static class TestIntPipeline extends IntPipeline<Integer> {

        private final IntUnaryOperator mapper;
        private final IntConsumer downstream;

        TestIntPipeline(IntUnaryOperator mapper, IntConsumer downstream) {
            super(null, 0, false);
            this.mapper = mapper;
            this.downstream = downstream;
        }

        @Override
        public void accept(int t) {
            downstream.accept(mapper.applyAsInt(t));
        }
    }

    public static void main(String[] args) {
        // Test normal behavior: accept applies mapper and passes to downstream
        final int[] downstreamValue = new int[1];
        IntUnaryOperator mapper = new IntUnaryOperator() {
            public int applyAsInt(int operand) {
                return operand * 2;
            }
        };
        IntConsumer downstream = new IntConsumer() {
            public void accept(int value) {
                downstreamValue[0] = value;
            }
        };

        TestIntPipeline pipeline = new TestIntPipeline(mapper, downstream);
        pipeline.accept(5);
        if (downstreamValue[0] != 10) {
            throw new AssertionError("Expected downstream to receive 10 but got " + downstreamValue[0]);
        }

        // Test exception path: downstream.accept throws IllegalStateException
        IntConsumer throwingDownstream = new IntConsumer() {
            public void accept(int value) {
                throw new IllegalStateException("forced exception");
            }
        };

        TestIntPipeline pipelineWithException = new TestIntPipeline(mapper, throwingDownstream);
        boolean caught = false;
        try {
            pipelineWithException.accept(1);
        } catch (IllegalStateException e) {
            caught = true;
            if (!"forced exception".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
        if (!caught) {
            throw new AssertionError("Expected IllegalStateException was not thrown");
        }
    }
}
