/*
 * @test
 * @summary Test DoublePipeline.reduce(DoubleBinaryOperator) for normal and illegal state
 * @run main DoublePipelineWitupTest
 */

import java.util.OptionalDouble;
import java.util.Spliterator;
import java.util.function.DoubleBinaryOperator;
import java.util.function.Supplier;
import java.util.stream.DoublePipeline;
import java.util.stream.StreamOpFlag;

public class DoublePipelineWitupTest {

    // Minimal concrete subclass to test DoublePipeline
    static class TestDoublePipeline extends DoublePipeline<Double> {

        TestDoublePipeline(Supplier<? extends Spliterator<Double>> source, int sourceFlags, boolean parallel) {
            super(source, sourceFlags, parallel);
        }

        TestDoublePipeline(Spliterator<Double> source, int sourceFlags, boolean parallel) {
            super(source, sourceFlags, parallel);
        }

        TestDoublePipeline(DoublePipeline<Double> upstream, int opFlags) {
            super(upstream, opFlags);
        }

        // Override evaluate to simulate terminal operation evaluation
        @Override
        public <R, P_IN> R evaluate(java.util.stream.TerminalOp<Double, R> terminalOp) {
            // For testing reduce, delegate to terminalOp
            return terminalOp.evaluateSequential(this.spliterator());
        }

        // Provide a spliterator over fixed data for testing
        @Override
        public Spliterator<Double> spliterator() {
            return java.util.Spliterators.spliterator(new double[] {1.0, 2.0, 3.0}, 0);
        }
    }

    public static void main(String[] args) {
        // Create a TestDoublePipeline with a simple source
        TestDoublePipeline pipeline = new TestDoublePipeline(
                () -> java.util.Spliterators.spliterator(new Double[] {1.0, 2.0, 3.0}, 0),
                StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT | StreamOpFlag.NOT_SIZED,
                false);

        // Test reduce with sum operator
        DoubleBinaryOperator sumOp = new DoubleBinaryOperator() {
            public double applyAsDouble(double left, double right) {
                return left + right;
            }
        };

        OptionalDouble result = pipeline.reduce(sumOp);
        if (!result.isPresent()) {
            throw new AssertionError("Expected result present for reduce sum");
        }
        double expectedSum = 1.0 + 2.0 + 3.0;
        if (result.getAsDouble() != expectedSum) {
            throw new AssertionError("Expected sum " + expectedSum + " but got " + result.getAsDouble());
        }

        // Test reduce with operator that returns max
        DoubleBinaryOperator maxOp = new DoubleBinaryOperator() {
            public double applyAsDouble(double left, double right) {
                return left > right ? left : right;
            }
        };

        OptionalDouble maxResult = pipeline.reduce(maxOp);
        if (!maxResult.isPresent()) {
            throw new AssertionError("Expected result present for reduce max");
        }
        double expectedMax = 3.0;
        if (maxResult.getAsDouble() != expectedMax) {
            throw new AssertionError("Expected max " + expectedMax + " but got " + maxResult.getAsDouble());
        }

        // Test that calling reduce twice on the same pipeline throws IllegalStateException
        boolean illegalStateThrown = false;
        try {
            pipeline.reduce(sumOp); // first call, should succeed
            pipeline.reduce(sumOp); // second call, should throw
        } catch (IllegalStateException e) {
            illegalStateThrown = true;
        }
        if (!illegalStateThrown) {
            throw new AssertionError("Expected IllegalStateException on second reduce call");
        }

        System.out.println("All tests passed.");
    }
}
