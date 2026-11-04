/*
 * @test
 * @summary Test DoublePipeline.accept(double) method behavior
 * @run main DoublePipelineWitupTest
 */

import java.util.function.DoubleUnaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongUnaryOperator;
import java.util.stream.DoublePipeline;

public class DoublePipelineWitupTest {

    static class TestDoublePipeline extends DoublePipeline<Object> {
        final LongConsumer downstream;
        final LongUnaryOperator mapper;

        TestDoublePipeline(LongConsumer downstream, LongUnaryOperator mapper) {
            super(null, 0, false);
            this.downstream = downstream;
            this.mapper = mapper;
        }

        @Override
        public void accept(double t) {
            downstream.accept(mapper.applyAsLong(t));
        }
    }

    public static void main(String[] args) {
        final long[] received = new long[1];

        LongConsumer downstream = new LongConsumer() {
            public void accept(long value) {
                received[0] = value;
            }
        };

        LongUnaryOperator mapper = new LongUnaryOperator() {
            public long applyAsLong(long operand) {
                return operand * 2;
            }
        };

        // Create instance with downstream and mapper
        TestDoublePipeline pipeline = new TestDoublePipeline(downstream, mapper);

        // Test with a positive double value
        pipeline.accept(3.0);
        if (received[0] != 6L) {
            throw new AssertionError("Expected 6 but got " + received[0]);
        }

        // Test with zero
        pipeline.accept(0.0);
        if (received[0] != 0L) {
            throw new AssertionError("Expected 0 but got " + received[0]);
        }

        // Test with negative double value
        pipeline.accept(-4.5);
        if (received[0] != -8L) {
            throw new AssertionError("Expected -8 but got " + received[0]);
        }

        // Test with a large double value
        pipeline.accept(1.5e10);
        if (received[0] != (long)(1.5e10 * 2)) {
            throw new AssertionError("Expected " + (long)(1.5e10 * 2) + " but got " + received[0]);
        }
    }
}
