/*
 * @test
 * @summary Test LongPipeline.mapToDouble(LongToDoubleFunction) for null argument and basic mapping
 * @run main LongPipelineWitupTest
 */

import java.util.Objects;
import java.util.stream.DoubleStream;
import java.util.stream.LongStream;
import java.util.function.LongToDoubleFunction;

public class LongPipelineWitupTest {

    static class DummyLongPipeline extends java.util.stream.LongPipeline<Long> {
        DummyLongPipeline() {
            super((Spliterator<Long>)null, 0, false);
        }

        // Override mapToDouble to call super
        @Override
        public DoubleStream mapToDouble(LongToDoubleFunction mapper) {
            return super.mapToDouble(mapper);
        }
    }

    public static void main(String[] args) {
        testNullMapperThrows();
        testBasicMapping();
    }

    private static void testNullMapperThrows() {
        DummyLongPipeline pipeline = new DummyLongPipeline();
        try {
            pipeline.mapToDouble(null);
            throw new AssertionError("Expected NullPointerException for null mapper");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testBasicMapping() {
        // Create a LongStream from array and map to double using mapToDouble
        long[] values = {1L, 2L, 3L};
        LongStream baseStream = java.util.stream.LongStream.of(values);
        DoubleStream mapped = baseStream.mapToDouble(new LongToDoubleFunction() {
            public double applyAsDouble(long value) {
                return value * 2.0;
            }
        });

        double[] results = mapped.toArray();
        if (results.length != values.length) {
            throw new AssertionError("Result length mismatch");
        }
        for (int i = 0; i < results.length; i++) {
            if (results[i] != values[i] * 2.0) {
                throw new AssertionError("Mapping incorrect at index " + i);
            }
        }
    }
}
