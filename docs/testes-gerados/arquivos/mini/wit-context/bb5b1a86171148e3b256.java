/*
 * @test
 * @summary Test LongPipeline.map(LongUnaryOperator) for null argument and basic mapping
 * @run main LongPipelineWitupTest
 */

import java.util.Objects;
import java.util.stream.LongStream;
import java.util.function.LongUnaryOperator;

public class LongPipelineWitupTest {

    static class DummyLongPipeline extends java.util.stream.LongPipeline<Object> {
        DummyLongPipeline() {
            super((Spliterator<Long>) null, 0, false);
        }

        @Override
        public void forEach(java.util.function.LongConsumer action) {
            // no-op
        }
    }

    public static void main(String[] args) {
        testMapNullArgument();
        testMapBasicFunctionality();
    }

    private static void testMapNullArgument() {
        LongPipeline<Object> pipeline = new DummyLongPipeline();
        try {
            pipeline.map(null);
            throw new AssertionError("Expected NullPointerException for null mapper");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testMapBasicFunctionality() {
        LongStream baseStream = LongStream.of(1L, 2L, 3L);
        LongStream mappedStream = baseStream.map(new LongUnaryOperator() {
            @Override
            public long applyAsLong(long operand) {
                return operand * 2;
            }
        });

        long[] results = mappedStream.toArray();
        if (results.length != 3 || results[0] != 2L || results[1] != 4L || results[2] != 6L) {
            throw new AssertionError("Mapped stream did not produce expected results");
        }
    }
}
