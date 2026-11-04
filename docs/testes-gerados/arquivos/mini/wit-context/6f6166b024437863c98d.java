/*
 * @test
 * @summary Test DoublePipeline.map method for null argument and basic mapping
 * @run main DoublePipelineWitupTest
 */

import java.util.Objects;
import java.util.stream.DoubleStream;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.AbstractPipeline;
import java.util.stream.StreamShape;
import java.util.stream.StreamOpFlag;
import java.util.stream.Sink;

public class DoublePipelineWitupTest {

    // Minimal concrete subclass to test map method
    static class TestDoublePipeline extends DoublePipeline<Object> {

        TestDoublePipeline() {
            super(new SupplierSpliterator(), 0, false);
        }

        TestDoublePipeline(AbstractPipeline<?, Object, ?> upstream, int opFlags) {
            super(upstream, opFlags);
        }

        // Dummy Supplier for constructor
        static class SupplierSpliterator implements java.util.function.Supplier<java.util.Spliterator<Double>> {
            public java.util.Spliterator<Double> get() {
                return new java.util.Spliterator<Double>() {
                    public boolean tryAdvance(java.util.function.Consumer<? super Double> action) { return false; }
                    public Spliterator<Double> trySplit() { return null; }
                    public long estimateSize() { return 0; }
                    public int characteristics() { return 0; }
                };
            }
        }

        // Expose map method publicly
        public DoubleStream mapPublic(DoubleUnaryOperator mapper) {
            return this.map(mapper);
        }
    }

    public static void main(String[] args) {
        testMapNullArgument();
        testMapBasicFunctionality();
    }

    private static void testMapNullArgument() {
        TestDoublePipeline pipeline = new TestDoublePipeline();
        try {
            pipeline.mapPublic(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testMapBasicFunctionality() {
        // Create a simple DoubleStream from array
        double[] input = {1.0, 2.0, 3.0};
        DoubleStream baseStream = DoubleStream.of(input);

        // Wrap baseStream in a TestDoublePipeline subclass to call map
        // Since we cannot directly create DoublePipeline from DoubleStream,
        // we test the map method behavior by applying the mapper on the baseStream
        // and comparing results.

        DoubleUnaryOperator mapper = new DoubleUnaryOperator() {
            public double applyAsDouble(double value) {
                return value * 2;
            }
        };

        // Apply map on baseStream
        DoubleStream mappedStream = baseStream.map(mapper);

        // Collect results
        double[] results = mappedStream.toArray();

        if (results.length != input.length) {
            throw new RuntimeException("Mapped stream length mismatch");
        }

        for (int i = 0; i < results.length; i++) {
            if (results[i] != input[i] * 2) {
                throw new RuntimeException("Mapped value incorrect at index " + i);
            }
        }
    }
}
