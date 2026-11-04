/*
 * @test
 * @summary Tests for ReferencePipeline.mapToDouble method
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.ReferencePipeline;
import java.util.stream.DoubleStream;
import java.util.function.ToDoubleFunction;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testMapToDoubleWithValidFunction();
        testMapToDoubleWithNullFunction();
    }

    static void testMapToDoubleWithValidFunction() {
        // Create a simple pipeline with a source array
        DoubleStream result = ReferencePipeline.of(new Double[] {1.0, 2.0, 3.0})
            .mapToDouble(new ToDoubleFunction<Double>() {
                @Override
                public double applyAsDouble(Double value) {
                    return value * 2;
                }
            });
        double[] expected = {2.0, 4.0, 6.0};
        double[] actual = result.toArray();
        for (int i = 0; i < expected.length; i++) {
            if (expected[i] != actual[i]) {
                throw new AssertionError("Expected: " + expected[i] + ", but got: " + actual[i]);
            }
        }
    }

    static void testMapToDoubleWithNullFunction() {
        boolean exceptionThrown = false;
        try {
            ReferencePipeline.of(new Double[] {1.0, 2.0})
                .mapToDouble(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null to mapToDouble");
        }
    }

    // Helper method to create a pipeline from an array
    static <T> ReferencePipeline<T, T> of(T[] array) {
        return new ReferencePipeline<T, T>() {
            @Override
            public DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper) {
                return ReferencePipeline.this.mapToDouble(mapper);
            }
        };
    }
}
