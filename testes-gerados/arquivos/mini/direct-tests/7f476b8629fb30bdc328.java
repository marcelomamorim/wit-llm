/*
 * @test
 * @summary Test LongPipeline.forEachOrdered with simple LongConsumer
 * @run main java.util.stream.LongPipelineWitupTest
 */

import java.util.stream.LongPipeline;
import java.util.stream.StreamSupport;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.LongConsumer;
import java.util.stream.ForEachOps;
import java.util.stream.AbstractPipeline;
import java.util.stream.LongStream;
import java.util.stream.StreamOpFlag;
import java.util.function.Supplier;

public class LongPipelineWitupTest {

    // Minimal concrete subclass of LongPipeline to allow testing
    static class TestLongPipeline extends LongPipeline<Long> {

        TestLongPipeline(Spliterator<Long> source, int sourceFlags, boolean parallel) {
            super(source, sourceFlags, parallel);
        }

        TestLongPipeline(AbstractPipeline<?, Long, ?> upstream, int opFlags) {
            super(upstream, opFlags);
        }

        @Override
        public void forEachOrdered(LongConsumer action) {
            super.forEachOrdered(action);
        }

        @Override
        public void forEach(LongConsumer action) {
            throw new UnsupportedOperationException();
        }

        @Override
        public LongStream unordered() {
            throw new UnsupportedOperationException();
        }

        @Override
        public LongStream filter(LongPredicate predicate) {
            throw new UnsupportedOperationException();
        }

        @Override
        public <U> java.util.stream.Stream<U> mapToObj(LongFunction<? extends U> mapper) {
            throw new UnsupportedOperationException();
        }

        @Override
        public java.util.stream.IntStream mapToInt(LongToIntFunction mapper) {
            throw new UnsupportedOperationException();
        }

        @Override
        public java.util.stream.DoubleStream mapToDouble(LongToDoubleFunction mapper) {
            throw new UnsupportedOperationException();
        }

        @Override
        public LongStream flatMap(LongFunction<? extends LongStream> mapper) {
            throw new UnsupportedOperationException();
        }

        @Override
        public LongStream peek(LongConsumer action) {
            throw new UnsupportedOperationException();
        }

        @Override
        public LongStream limit(long maxSize) {
            throw new UnsupportedOperationException();
        }

        @Override
        public LongStream skip(long n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void close() {
            // no-op
        }
    }

    public static void main(String[] args) {
        // Create a simple spliterator over longs 1,2,3
        Spliterator<Long> spliterator = Spliterators.spliterator(new Long[]{1L, 2L, 3L}, 0);

        // Create LongPipeline instance
        TestLongPipeline pipeline = new TestLongPipeline(spliterator, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT | StreamOpFlag.NOT_SIZED, false);

        final StringBuilder sb = new StringBuilder();

        // Use forEachOrdered to append values to sb
        pipeline.forEachOrdered(new LongConsumer() {
            public void accept(long value) {
                sb.append(value).append(",");
            }
        });

        String result = sb.toString();

        // Expected output is "1,2,3,"
        if (!"1,2,3,".equals(result)) {
            throw new AssertionError("forEachOrdered did not process elements in order: " + result);
        }
    }
}
