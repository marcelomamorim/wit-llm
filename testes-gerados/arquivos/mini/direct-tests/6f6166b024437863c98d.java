/*
 * @test
 * @summary Test DoublePipeline.map method behavior
 * @run main DoublePipelineWitupTest
 */

import java.util.Objects;
import java.util.stream.DoubleStream;
import java.util.function.DoubleUnaryOperator;

public class DoublePipelineWitupTest {

    // Minimal DoublePipeline subclass to test map method
    static class TestDoublePipeline extends java.util.stream.DoublePipeline<Object> {

        TestDoublePipeline() {
            super((java.util.Spliterator<Double>) null, 0, false);
        }

        @Override
        public java.util.stream.Sink<Double> opWrapSink(int flags, java.util.stream.Sink<Double> sink) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void forEach(java.util.function.DoubleConsumer action) {
            throw new UnsupportedOperationException();
        }

        @Override
        public java.util.stream.Spliterator.OfDouble spliterator() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isParallel() {
            return false;
        }

        @Override
        public java.util.stream.DoubleStream unordered() {
            return this;
        }

        @Override
        public java.util.stream.DoubleStream sequential() {
            return this;
        }

        @Override
        public java.util.stream.DoubleStream parallel() {
            return this;
        }

        @Override
        public java.util.stream.DoubleStream onClose(Runnable closeHandler) {
            return this;
        }

        @Override
        public void close() {
        }
    }

    public static void main(String[] args) {
        TestDoublePipeline pipeline = new TestDoublePipeline();

        // Test 1: map with null should throw NullPointerException
        try {
            pipeline.map(null);
            throw new AssertionError("Expected NullPointerException for null mapper");
        } catch (NullPointerException expected) {
        }

        // Test 2: map with identity function returns a DoubleStream
        DoubleUnaryOperator identity = new DoubleUnaryOperator() {
            public double applyAsDouble(double operand) {
                return operand;
            }
        };
        DoubleStream mappedStream = pipeline.map(identity);
        if (mappedStream == null) {
            throw new AssertionError("map returned null for valid mapper");
        }

        // Test 3: map with a doubling function returns a DoubleStream
        DoubleUnaryOperator doubler = new DoubleUnaryOperator() {
            public double applyAsDouble(double operand) {
                return operand * 2;
            }
        };
        DoubleStream doubledStream = pipeline.map(doubler);
        if (doubledStream == null) {
            throw new AssertionError("map returned null for valid doubler mapper");
        }

        System.out.println("All tests passed.");
    }
}
