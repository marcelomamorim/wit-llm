/*
 * @test
 * @summary Test DoublePipeline.flatMap method behavior
 * @run main DoublePipelineWitupTest
 */

import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.stream.DoubleStream;
import java.util.stream.StreamSupport;

public class DoublePipelineWitupTest {

    // Minimal DoublePipeline subclass to test flatMap
    static abstract class TestDoublePipeline extends java.util.stream.DoublePipeline<Double> {
        TestDoublePipeline(Spliterator<Double> source) {
            super(source, 0, false);
        }
    }

    public static void main(String[] args) {
        testFlatMapWithValidMapper();
        testFlatMapWithNullMapper();
        testFlatMapWithMapperReturningNull();
    }

    private static void testFlatMapWithValidMapper() {
        // Create a DoubleStream source with elements 1.0, 2.0
        DoubleStream source = DoubleStream.of(1.0, 2.0);

        // Wrap source in a DoublePipeline
        TestDoublePipeline pipeline = new TestDoublePipeline(source.spliterator()) {};

        // Mapper that maps each double to a DoubleStream of that value and its double
        DoubleFunction<DoubleStream> mapper = new DoubleFunction<DoubleStream>() {
            public DoubleStream apply(double value) {
                return DoubleStream.of(value, value * 2);
            }
        };

        DoubleStream resultStream = pipeline.flatMap(mapper);

        // Collect results to array
        double[] results = resultStream.toArray();

        // Expected: for 1.0 -> [1.0, 2.0], for 2.0 -> [2.0, 4.0]
        double[] expected = {1.0, 2.0, 2.0, 4.0};

        if (results.length != expected.length) {
            throw new AssertionError("flatMap result length mismatch: expected " + expected.length + " but got " + results.length);
        }

        for (int i = 0; i < expected.length; i++) {
            if (Double.compare(results[i], expected[i]) != 0) {
                throw new AssertionError("flatMap result mismatch at index " + i + ": expected " + expected[i] + " but got " + results[i]);
            }
        }
    }

    private static void testFlatMapWithNullMapper() {
        DoubleStream source = DoubleStream.of(1.0);
        TestDoublePipeline pipeline = new TestDoublePipeline(source.spliterator()) {};

        try {
            pipeline.flatMap(null);
            throw new AssertionError("Expected NullPointerException for null mapper");
        } catch (NullPointerException e) {
            // Expected
        }
    }

    private static void testFlatMapWithMapperReturningNull() {
        DoubleStream source = DoubleStream.of(1.0);
        TestDoublePipeline pipeline = new TestDoublePipeline(source.spliterator()) {};

        DoubleFunction<DoubleStream> mapper = new DoubleFunction<DoubleStream>() {
            public DoubleStream apply(double value) {
                return null; // mapper returns null
            }
        };

        DoubleStream resultStream = pipeline.flatMap(mapper);

        // The resulting stream should be empty because mapper returns null
        long count = resultStream.count();
        if (count != 0) {
            throw new AssertionError("Expected empty stream when mapper returns null, but count was " + count);
        }
    }
}
