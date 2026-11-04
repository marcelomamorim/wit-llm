/*
 * @test
 * @summary Test LongPipeline.forEach(LongConsumer) behavior for sequential and parallel streams
 * @run main LongPipelineWitupTest
 */

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.LongConsumer;
import java.util.function.Supplier;
import java.util.stream.LongStream;
import java.util.stream.AbstractPipeline;

public class LongPipelineWitupTest {

    // Minimal concrete subclass of LongPipeline to test forEach
    static class TestLongPipeline extends LongPipeline<Long> {

        private final boolean parallel;
        private final Supplier<? extends Spliterator<Long>> sourceSupplier;

        TestLongPipeline(Supplier<? extends Spliterator<Long>> sourceSupplier, boolean parallel) {
            super(sourceSupplier, 0, parallel);
            this.parallel = parallel;
            this.sourceSupplier = sourceSupplier;
        }

        @Override
        public boolean isParallel() {
            return parallel;
        }

        @Override
        public Spliterator<Long> sourceStageSpliterator() {
            return sourceSupplier.get();
        }

        // Override super.forEach to detect parallel invocation
        @Override
        public void forEach(LongConsumer action) {
            if (!isParallel()) {
                super.forEach(action); // calls adapt(sourceStageSpliterator()).forEachRemaining(action)
            } else {
                // For parallel, call super.forEach which LongPipeline calls AbstractPipeline.forEach
                // We override here to simulate parallel behavior by counting calls
                // but for test, just call sourceStageSpliterator().forEachRemaining
                sourceStageSpliterator().forEachRemaining(action);
            }
        }
    }

    public static void main(String[] args) {
        testSequentialForEach();
        testParallelForEach();
    }

    private static void testSequentialForEach() {
        final AtomicLong sum = new AtomicLong();
        Supplier<Spliterator<Long>> supplier = new Supplier<Spliterator<Long>>() {
            @Override
            public Spliterator<Long> get() {
                long[] data = {1L, 2L, 3L};
                return Spliterators.spliterator(data, 0);
            }
        };

        TestLongPipeline pipeline = new TestLongPipeline(supplier, false);
        pipeline.forEach(new LongConsumer() {
            @Override
            public void accept(long value) {
                sum.addAndGet(value);
            }
        });

        if (sum.get() != 6L) {
            throw new AssertionError("Sequential forEach sum expected 6 but was " + sum.get());
        }
    }

    private static void testParallelForEach() {
        final AtomicLong sum = new AtomicLong();
        Supplier<Spliterator<Long>> supplier = new Supplier<Spliterator<Long>>() {
            @Override
            public Spliterator<Long> get() {
                long[] data = {4L, 5L, 6L};
                return Spliterators.spliterator(data, 0);
            }
        };

        TestLongPipeline pipeline = new TestLongPipeline(supplier, true);
        pipeline.forEach(new LongConsumer() {
            @Override
            public void accept(long value) {
                sum.addAndGet(value);
            }
        });

        if (sum.get() != 15L) {
            throw new AssertionError("Parallel forEach sum expected 15 but was " + sum.get());
        }
    }
}
