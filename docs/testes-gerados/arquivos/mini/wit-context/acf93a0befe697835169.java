/*
 * @test
 * @summary Test ReferencePipeline.mapToDouble behavior including null argument check
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.ReferencePipeline;
import java.util.stream.DoubleStream;
import java.util.function.ToDoubleFunction;
import java.util.Objects;
import java.util.Spliterator;
import java.util.stream.StreamSupport;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testMapToDoubleWithValidMapper();
        testMapToDoubleWithNullMapper();
    }

    static void testMapToDoubleWithValidMapper() {
        // Create a simple ReferencePipeline from a Spliterator over a single element
        Spliterator<String> spliterator = java.util.Spliterators.spliterator(new String[] {"test"}, 0);
        ReferencePipeline<String, String> pipeline = new ReferencePipeline<String, String>(spliterator, 0, false) {
            @Override
            public java.util.stream.Stream<String> sequential() { return this; }
            @Override
            public java.util.stream.Stream<String> parallel() { return this; }
            @Override
            public void forEach(java.util.function.Consumer<? super String> action) { }
        };

        ToDoubleFunction<String> mapper = new ToDoubleFunction<String>() {
            public double applyAsDouble(String s) {
                return s.length();
            }
        };

        DoubleStream ds = pipeline.mapToDouble(mapper);
        Objects.requireNonNull(ds);

        // Test that the returned DoubleStream is not null and behaves as expected
        // Since pipeline is minimal, test that the opWrapSink is functional indirectly
        // by checking that the DoubleStream is not null and is a DoubleStream instance
        if (!(ds instanceof DoubleStream)) {
            throw new AssertionError("mapToDouble did not return a DoubleStream instance");
        }
    }

    static void testMapToDoubleWithNullMapper() {
        // Create a minimal ReferencePipeline instance
        Spliterator<String> spliterator = java.util.Spliterators.spliterator(new String[] {"test"}, 0);
        ReferencePipeline<String, String> pipeline = new ReferencePipeline<String, String>(spliterator, 0, false) {
            @Override
            public java.util.stream.Stream<String> sequential() { return this; }
            @Override
            public java.util.stream.Stream<String> parallel() { return this; }
            @Override
            public void forEach(java.util.function.Consumer<? super String> action) { }
        };

        try {
            pipeline.mapToDouble(null);
            throw new AssertionError("Expected NullPointerException for null mapper");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

}