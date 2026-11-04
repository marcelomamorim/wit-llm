/*
 * @test
 * @summary Test DoublePipeline.reduce(double, DoubleBinaryOperator) for normal and consumed state
 * @run main DoublePipelineWitupTest
 */

import java.util.Spliterator;
import java.util.function.DoubleBinaryOperator;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.DoublePipeline;
import java.util.stream.AbstractPipeline;
import java.util.stream.StreamSupport;

public class DoublePipelineWitupTest {

    // Minimal DoublePipeline subclass to test reduce method
    static class TestDoublePipeline extends DoublePipeline<Double> {

        TestDoublePipeline(Supplier<? extends Spliterator<Double>> source, int sourceFlags, boolean parallel) {
            super(source, sourceFlags, parallel);
        }

        TestDoublePipeline(Spliterator<Double> source, int sourceFlags, boolean parallel) {
            super(source, sourceFlags, parallel);
        }

        TestDoublePipeline(AbstractPipeline<?, Double, ?> upstream, int opFlags) {
            super(upstream, opFlags);
        }

        // Expose evaluate method publicly for testing
        public <R> R testEvaluate(java.util.stream.TerminalOp<Double, R> terminalOp) {
            return super.evaluate(terminalOp);
        }
    }

    public static void main(String[] args) {
        testReduceSum();
        testReduceEmpty();
        testReduceConsumed();
    }

    private static void testReduceSum() {
        // Create a DoubleStream from array and wrap in TestDoublePipeline
        double[] values = {1.0, 2.0, 3.0};
        DoubleStream ds = DoubleStream.of(values);
        DoublePipeline<Double> pipeline = new TestDoublePipeline(ds.spliterator(), 0, false);

        double result = pipeline.reduce(0.0, new DoubleBinaryOperator() {
            @Override
            public double applyAsDouble(double left, double right) {
                return left + right;
            }
        });

        if (result != 6.0) {
            throw new AssertionError("Expected sum 6.0 but got " + result);
        }
    }

    private static void testReduceEmpty() {
        DoubleStream ds = DoubleStream.empty();
        DoublePipeline<Double> pipeline = new TestDoublePipeline(ds.spliterator(), 0, false);

        double result = pipeline.reduce(42.0, new DoubleBinaryOperator() {
            @Override
            public double applyAsDouble(double left, double right) {
                return left + right;
            }
        });

        if (result != 42.0) {
            throw new AssertionError("Expected identity 42.0 for empty stream but got " + result);
        }
    }

    private static void testReduceConsumed() {
        DoubleStream ds = DoubleStream.of(1.0, 2.0);
        DoublePipeline<Double> pipeline = new TestDoublePipeline(ds.spliterator(), 0, false);

        // Consume the stream once
        pipeline.reduce(0.0, (a, b) -> a + b);

        // Second reduce call should throw IllegalStateException due to consumed pipeline
        boolean threw = false;
        try {
            pipeline.reduce(0.0, (a, b) -> a + b);
        } catch (IllegalStateException e) {
            threw = true;
        }

        if (!threw) {
            throw new AssertionError("Expected IllegalStateException on second reduce call after consumption");
        }
    }
}
