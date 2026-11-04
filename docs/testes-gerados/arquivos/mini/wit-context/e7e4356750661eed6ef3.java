/*
 * @test
 * @summary Test for java.util.stream.DoublePipeline.accept(double) method
 * @run main DoublePipelineWitupTest
 */

import java.util.stream.DoublePipeline;
import java.util.stream.AbstractPipeline;
import java.util.stream.StreamOpFlag;
import java.util.Spliterator;
import java.util.function.Supplier;
import java.util.function.DoubleUnaryOperator;
import java.util.function.DoubleConsumer;

public class DoublePipelineWitupTest {

    // Minimal concrete subclass to test accept(double) method
    static class TestDoublePipeline extends DoublePipeline<Double> {

        DoubleConsumer downstream;
        DoubleUnaryOperator mapper;

        TestDoublePipeline(DoubleConsumer downstream, DoubleUnaryOperator mapper) {
            super(new Supplier<Spliterator<Double>>() {
                public Spliterator<Double> get() {
                    return null; // Not used in this test
                }
            }, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT | StreamOpFlag.NOT_SIZED, false);
            this.downstream = downstream;
            this.mapper = mapper;
        }

        @Override
        public void accept(double t) {
            downstream.accept(mapper.applyAsLong(t));
        }
    }

    public static void main(String[] args) {
        // Test 1: accept applies mapper and passes to downstream
        final double[] received = new double[1];
        DoubleConsumer downstream = new DoubleConsumer() {
            public void accept(double value) {
                received[0] = value;
            }
        };

        DoubleUnaryOperator mapper = new DoubleUnaryOperator() {
            public double applyAsDouble(double operand) {
                return operand * 2;
            }
        };

        TestDoublePipeline pipeline = new TestDoublePipeline(downstream, mapper);

        pipeline.accept(3.5);
        if (received[0] != 7.0) {
            throw new AssertionError("Expected 7.0 but got " + received[0]);
        }

        // Test 2: downstream accept throws IllegalStateException as per WIT expath
        DoubleConsumer throwingDownstream = new DoubleConsumer() {
            public void accept(double value) {
                throw new IllegalStateException("Downstream failure");
            }
        };

        TestDoublePipeline pipeline2 = new TestDoublePipeline(throwingDownstream, mapper);

        boolean threw = false;
        try {
            pipeline2.accept(1.0);
        } catch (IllegalStateException e) {
            threw = true;
            if (!"Downstream failure".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalStateException was not thrown");
        }
    }
}
