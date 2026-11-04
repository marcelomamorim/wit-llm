/*
 * @test
 * @summary Test for DoublePipeline.reduce(DoubleBinaryOperator)
 * @run main DoublePipelineWitupTest
 */

import java.util.OptionalDouble;
import java.util.function.DoubleBinaryOperator;
import java.util.stream.DoublePipeline;

public class DoublePipelineWitupTest {

    public static void main(String[] args) {
        testReduceWithValidOperator();
        testReduceWithNullOperator();
    }

    static void testReduceWithValidOperator() {
        DoubleBinaryOperator op = (a, b) -> a + b;
        DoublePipeline pipeline = createSamplePipeline();
        try {
            OptionalDouble result = pipeline.reduce(op);
            if (result == null) {
                throw new AssertionError("Expected non-null OptionalDouble");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    static void testReduceWithNullOperator() {
        DoubleBinaryOperator op = null;
        DoublePipeline pipeline = createSamplePipeline();
        try {
            pipeline.reduce(op);
            throw new AssertionError("Expected NullPointerException for null operator");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    static DoublePipeline createSamplePipeline() {
        // Create a simple DoublePipeline for testing purposes
        // Using a spliterator with a small array
        double[] data = {1.0, 2.0, 3.0};
        java.util.Spliterator.OfDouble spliterator = java.util.Spliterators.spliterator(data, java.util.Spliterator.ORDERED);
        return new DoublePipeline() {
            @Override
            public java.util.stream.BaseStream<Double, ?> getStream() {
                return null; // Not used in this test
            }
            @Override
            public java.util.stream.DoubleStream evaluate(java.util.stream.TerminalOp<?> op) {
                // Simulate evaluate by applying the operation
                double result = 0;
                for (double d : data) {
                    result += d; // Simplified for test
                }
                return java.util.stream.DoubleStream.of(result);
            }
        };
    }
}
