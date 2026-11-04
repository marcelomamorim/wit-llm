/*
 * @test
 * @summary Test DoublePipeline.mapToInt with null and valid mapper
 * @run main DoublePipelineWitupTest
 */

import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.StreamShape;
import java.util.stream.StreamOpFlag;
import java.util.stream.Sink;
import java.util.function.DoubleToIntFunction;

public class DoublePipelineWitupTest {

    // Minimal concrete subclass of DoublePipeline to allow testing
    static class TestDoublePipeline extends java.util.stream.DoublePipeline<Object> {
        TestDoublePipeline() {
            super((Spliterator<Double>) null, 0, false);
        }
    }

    public static void main(String[] args) {
        testMapToIntNullMapper();
        testMapToIntValidMapper();
    }

    private static void testMapToIntNullMapper() {
        TestDoublePipeline pipeline = new TestDoublePipeline();
        try {
            pipeline.mapToInt(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testMapToIntValidMapper() {
        TestDoublePipeline pipeline = new TestDoublePipeline();
        DoubleToIntFunction mapper = new DoubleToIntFunction() {
            public int applyAsInt(double value) {
                return (int) value;
            }
        };
        IntStream intStream = pipeline.mapToInt(mapper);
        if (intStream == null) {
            throw new RuntimeException("mapToInt returned null");
        }
        // We cannot test further without a real pipeline source, but at least the returned stream is non-null
    }
}
