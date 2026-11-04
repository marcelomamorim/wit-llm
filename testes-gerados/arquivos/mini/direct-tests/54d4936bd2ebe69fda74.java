/*
 * @test
 * @summary Test DoublePipeline.mapToInt with valid and null mapper
 * @run main DoublePipelineWitupTest
 */

import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.DoubleStream;
import java.util.function.DoubleToIntFunction;

public class DoublePipelineWitupTest {

    static class DummyDoublePipeline extends DoublePipeline<Object> {
        DummyDoublePipeline() {
            super((Spliterator<Double>) null, 0, false);
        }
    }

    public static void main(String[] args) {
        testMapToIntWithValidMapper();
        testMapToIntWithNullMapper();
    }

    private static void testMapToIntWithValidMapper() {
        DoublePipeline<Object> pipeline = new DummyDoublePipeline();
        DoubleToIntFunction mapper = new DoubleToIntFunction() {
            public int applyAsInt(double value) {
                return (int) (value * 2);
            }
        };
        IntStream intStream = pipeline.mapToInt(mapper);
        if (intStream == null) {
            throw new AssertionError("mapToInt returned null");
        }
        // Since IntStream is lazy, we test that it is an IntStream by calling a terminal operation
        int count = intStream.limit(0).toArray().length;
        if (count != 0) {
            throw new AssertionError("Expected empty IntStream from limit(0)");
        }
    }

    private static void testMapToIntWithNullMapper() {
        DoublePipeline<Object> pipeline = new DummyDoublePipeline();
        try {
            pipeline.mapToInt(null);
            throw new AssertionError("Expected NullPointerException for null mapper");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
