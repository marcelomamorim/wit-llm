/*
 * @test
 * @summary Test LongPipeline.map(LongUnaryOperator) for basic functionality and null handling
 * @run main LongPipelineWitupTest
 */

import java.util.Objects;
import java.util.stream.LongStream;
import java.util.stream.StreamSupport;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.LongUnaryOperator;
import java.util.stream.AbstractPipeline;
import java.util.stream.StreamShape;
import java.util.stream.StreamOpFlag;
import java.util.stream.Sink;

public class LongPipelineWitupTest {

    // Minimal concrete subclass of LongPipeline to test map method
    static class TestLongPipeline extends LongPipeline<Long> {

        TestLongPipeline(Spliterator<Long> source, int sourceFlags, boolean parallel) {
            super(source, sourceFlags, parallel);
        }

        TestLongPipeline(AbstractPipeline<?, Long, ?> upstream, int opFlags) {
            super(upstream, opFlags);
        }

        @Override
        public void forEach(LongConsumer action) {
            Objects.requireNonNull(action);
            Spliterator<Long> spliterator = this.spliterator();
            while (spliterator.tryAdvance(action)) {}
        }

        @Override
        public Spliterator<Long> spliterator() {
            // Provide a spliterator over a fixed array of longs
            long[] data = {1L, 2L, 3L};
            return Spliterators.spliterator(data, 0);
        }

        @Override
        public boolean isParallel() {
            return false;
        }

        @Override
        public LongStream map(LongUnaryOperator mapper) {
            return super.map(mapper);
        }
    }

    public static void main(String[] args) {
        testMapBasicFunctionality();
        testMapNullMapper();
    }

    static void testMapBasicFunctionality() {
        TestLongPipeline pipeline = new TestLongPipeline(
                Spliterators.spliterator(new long[]{1L, 2L, 3L}, 0),
                0, false);

        LongUnaryOperator mapper = new LongUnaryOperator() {
            public long applyAsLong(long operand) {
                return operand * 2;
            }
        };

        LongStream mappedStream = pipeline.map(mapper);

        final long[] results = new long[3];
        final int[] index = {0};
        mappedStream.forEach(new java.util.function.LongConsumer() {
            public void accept(long value) {
                results[index[0]++] = value;
            }
        });

        if (results[0] != 2L || results[1] != 4L || results[2] != 6L) {
            throw new AssertionError("map did not correctly apply the mapper function");
        }
    }

    static void testMapNullMapper() {
        TestLongPipeline pipeline = new TestLongPipeline(
                Spliterators.spliterator(new long[]{1L}, 0),
                0, false);

        try {
            pipeline.map(null);
            throw new AssertionError("Expected NullPointerException for null mapper");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
