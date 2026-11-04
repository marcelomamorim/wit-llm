/*
 * @test
 * @summary Test ReferencePipeline.mapToDouble with valid and null mapper
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.ReferencePipeline;
import java.util.stream.DoubleStream;
import java.util.function.ToDoubleFunction;
import java.util.Objects;

public class ReferencePipelineWitupTest {

    // Minimal concrete subclass to instantiate ReferencePipeline
    static class RP<T> extends ReferencePipeline<T, T> {
        RP() {
            super((Spliterator<?>) null, 0, false);
        }
    }

    public static void main(String[] args) {
        testMapToDoubleWithValidMapper();
        testMapToDoubleWithNullMapper();
    }

    static void testMapToDoubleWithValidMapper() {
        RP<String> pipeline = new RP<>();
        ToDoubleFunction<String> mapper = new ToDoubleFunction<String>() {
            public double applyAsDouble(String s) {
                return s.length();
            }
        };
        DoubleStream ds = pipeline.mapToDouble(mapper);
        if (ds == null) {
            throw new AssertionError("mapToDouble returned null");
        }
    }

    static void testMapToDoubleWithNullMapper() {
        RP<String> pipeline = new RP<>();
        try {
            pipeline.mapToDouble(null);
            throw new AssertionError("Expected NullPointerException for null mapper");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
