/*
 * @test
 * @summary Test DoublePipeline.mapToLong method behavior
 * @run main DoublePipelineWitupTest
 */

import java.util.Objects;
import java.util.stream.LongStream;
import java.util.stream.DoubleStream;
import java.util.function.DoubleToLongFunction;

public class DoublePipelineWitupTest {

    static class DummyDoublePipeline extends DoublePipeline<Object> {
        DummyDoublePipeline() {
            super((Spliterator<Double>) null, 0, false);
        }

        @Override
        public void forEach(DoubleConsumer action) {
            // no-op
        }
    }

    public static void main(String[] args) {
        // Test 1: Null mapper should throw NullPointerException
        try {
            DoublePipeline<Object> pipeline = new DummyDoublePipeline();
            pipeline.mapToLong(null);
            throw new AssertionError("Expected NullPointerException for null mapper");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test 2: mapToLong returns a LongStream that maps values correctly
        DoublePipeline<Object> pipeline = new DummyDoublePipeline() {
            @Override
            public LongStream mapToLong(DoubleToLongFunction mapper) {
                return DoublePipelineWitupTest.super.mapToLong(mapper);
            }
        };

        final double[] inputs = {1.5, 2.5, -3.0};
        final long[] expected = {3L, 5L, -6L};

        DoubleToLongFunction mapper = new DoubleToLongFunction() {
            int index = 0;
            @Override
            public long applyAsLong(double value) {
                if (index >= expected.length) {
                    throw new AssertionError("Mapper called more times than expected");
                }
                long result = expected[index];
                if (value != inputs[index]) {
                    throw new AssertionError("Mapper input mismatch at index " + index);
                }
                index++;
                return result;
            }
        };

        LongStream longStream = pipeline.mapToLong(mapper);

        // Collect results from the LongStream
        long[] results = longStream.toArray();

        if (results.length != expected.length) {
            throw new AssertionError("Result length mismatch");
        }

        for (int i = 0; i < results.length; i++) {
            if (results[i] != expected[i]) {
                throw new AssertionError("Result mismatch at index " + i + ": expected " + expected[i] + ", got " + results[i]);
            }
        }
    }
}
