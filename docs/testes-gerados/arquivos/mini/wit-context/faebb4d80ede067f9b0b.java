/*
 * @test
 * @summary Test DoublePipeline.flatMap method for null argument and basic behavior
 * @run main DoublePipelineWitupTest
 */

import java.util.stream.DoublePipeline;
import java.util.stream.DoubleStream;
import java.util.function.DoubleFunction;
import java.util.Objects;

public class DoublePipelineWitupTest {

    // Minimal concrete subclass of DoublePipeline to allow instantiation
    static class DummyDoublePipeline extends DoublePipeline<Object> {
        DummyDoublePipeline() {
            super((Spliterator<Double>) null, 0, false);
        }
    }

    public static void main(String[] args) {
        testFlatMapNullMapper();
        testFlatMapBasic();
    }

    private static void testFlatMapNullMapper() {
        DoublePipeline<Object> pipeline = new DummyDoublePipeline();
        try {
            pipeline.flatMap(null);
            throw new AssertionError("Expected NullPointerException for null mapper");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testFlatMapBasic() {
        DoublePipeline<Object> pipeline = new DummyDoublePipeline();

        DoubleFunction<DoubleStream> mapper = new DoubleFunction<DoubleStream>() {
            @Override
            public DoubleStream apply(double value) {
                // map each double to a stream of two doubles: value and value*2
                return DoubleStream.of(value, value * 2);
            }
        };

        DoubleStream resultStream = pipeline.flatMap(mapper);

        // Collect results to array to verify behavior
        double[] results = resultStream.toArray();

        // Since DummyDoublePipeline has no source, the stream is empty, so results length should be 0
        if (results.length != 0) {
            throw new AssertionError("Expected empty result array from flatMap on empty pipeline");
        }
    }
}
