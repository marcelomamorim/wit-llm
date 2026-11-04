/*
 * @test
 * @summary Test LongPipeline.peek(LongConsumer) for null and normal behavior
 * @run main LongPipelineWitupTest
 */

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.LongStream;
import java.util.function.LongConsumer;
import java.util.stream.StreamSupport;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.AbstractPipeline;
import java.util.stream.StreamShape;

public class LongPipelineWitupTest {

    // Minimal concrete subclass of LongPipeline to test peek
    static class TestLongPipeline extends LongPipeline<Long> {
        TestLongPipeline(Spliterator<Long> source) {
            super(source, 0, false);
        }

        TestLongPipeline(AbstractPipeline<?, Long, ?> upstream, int opFlags) {
            super(upstream, opFlags);
        }

        @Override
        public void forEach(LongConsumer action) {
            Objects.requireNonNull(action);
            Spliterator<Long> spliterator = sourceSpliterator();
            spliterator.forEachRemaining(l -> action.accept(l));
        }

        // Expose source spliterator for testing
        Spliterator<Long> sourceSpliterator() {
            return super.sourceSpliterator();
        }
    }

    public static void main(String[] args) {
        testNullAction();
        testPeekActionCalled();
    }

    static void testNullAction() {
        try {
            LongStream stream = new TestLongPipeline(Spliterators.spliterator(new long[]{1L, 2L, 3L}, 0));
            stream.peek(null);
            throw new RuntimeException("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testPeekActionCalled() {
        final AtomicLong sum = new AtomicLong(0);
        long[] values = {10L, 20L, 30L};
        Spliterator<Long> spliterator = Spliterators.spliterator(values, 0);
        TestLongPipeline pipeline = new TestLongPipeline(spliterator);

        LongStream peeked = pipeline.peek(new LongConsumer() {
            public void accept(long value) {
                sum.addAndGet(value);
            }
        });

        // Consume the stream to trigger peek
        long total = peeked.reduce(0L, (a, b) -> a + b);

        if (total != 60L) {
            throw new RuntimeException("Unexpected sum of elements: " + total);
        }

        if (sum.get() != 60L) {
            throw new RuntimeException("Peek action was not called correctly, sum=" + sum.get());
        }
    }
}
