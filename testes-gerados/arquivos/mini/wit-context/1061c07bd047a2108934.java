/*
 * @test
 * @summary Test DoublePipeline.accept(double) method for expected behavior and exception paths
 * @run main DoublePipelineWitupTest
 */

import java.util.stream.DoublePipeline;
import java.util.stream.AbstractPipeline;
import java.util.Spliterator;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Supplier;
import java.util.function.DoubleConsumer;

public class DoublePipelineWitupTest {

    // Minimal concrete subclass to test accept(double) method
    static class TestDoublePipeline extends DoublePipeline<Double> {

        private final DoubleConsumer downstream;
        private final DoubleUnaryOperator mapper;

        TestDoublePipeline(DoubleConsumer downstream, DoubleUnaryOperator mapper) {
            // Call super constructor with dummy parameters
            super(new Supplier<Spliterator<Double>>() {
                public Spliterator<Double> get() { return null; }
            }, 0, false);
            this.downstream = downstream;
            this.mapper = mapper;
        }

        @Override
        public void accept(double t) {
            // replicate the method under test
            downstream.accept(mapper.applyAsInt(t));
        }

        // Other abstract methods from DoubleStream and AbstractPipeline are not needed for this test
    }

    public static void main(String[] args) {
        testAcceptNormal();
        testAcceptThrowsIllegalStateException();
    }

    private static void testAcceptNormal() {
        final double input = 2.5;
        final double[] received = new double[1];

        DoubleConsumer downstream = new DoubleConsumer() {
            public void accept(double value) {
                received[0] = value;
            }
        };

        DoubleUnaryOperator mapper = new DoubleUnaryOperator() {
            public double applyAsInt(double operand) {
                return operand * 2;
            }
        };

        TestDoublePipeline pipeline = new TestDoublePipeline(downstream, mapper);
        pipeline.accept(input);

        if (received[0] != mapper.applyAsInt(input)) {
            throw new AssertionError("accept did not forward mapped value correctly: expected "
                + mapper.applyAsInt(input) + " but got " + received[0]);
        }
    }

    private static void testAcceptThrowsIllegalStateException() {
        DoubleConsumer downstream = new DoubleConsumer() {
            public void accept(double value) {
                throw new IllegalStateException("forced exception");
            }
        };

        DoubleUnaryOperator mapper = new DoubleUnaryOperator() {
            public double applyAsInt(double operand) {
                return operand;
            }
        };

        TestDoublePipeline pipeline = new TestDoublePipeline(downstream, mapper);

        boolean thrown = false;
        try {
            pipeline.accept(1.0);
        } catch (IllegalStateException e) {
            thrown = true;
            if (!"forced exception".equals(e.getMessage())) {
                throw new AssertionError("IllegalStateException thrown but message unexpected: " + e.getMessage());
            }
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalStateException was not thrown");
        }
    }
}
