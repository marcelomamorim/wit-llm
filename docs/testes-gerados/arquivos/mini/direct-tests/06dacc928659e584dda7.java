/*
 * @test
 * @summary Test LongPipeline.skip(long) method behavior
 * @run main LongPipelineWitupTest
 */

import java.util.stream.LongStream;
import java.util.stream.StreamSupport;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.AbstractPipeline;
import java.util.stream.LongPipeline;

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
        public void forEach(java.util.function.LongConsumer action) {
            Spliterator.OfLong spl = (Spliterator.OfLong) sourceStage();
            spl.forEachRemaining(action);
        }

        private Spliterator<Long> sourceStage() {
            // Return the source spliterator if available
            try {
                java.lang.reflect.Field sourceField = AbstractPipeline.class.getDeclaredField("sourceSpliterator");
                sourceField.setAccessible(true);
                Object src = sourceField.get(this);
                if (src instanceof Spliterator) {
                    return (Spliterator<Long>) src;
                }
            } catch (Exception e) {
                // fallback
            }
            return null;
        }
    }

    public static void main(String[] args) {
        testSkipNegative();
        testSkipZero();
        testSkipPositive();
    }

    static void testSkipNegative() {
        TestLongPipeline pipeline = createPipeline(new long[] {1, 2, 3});
        try {
            pipeline.skip(-1L);
            throw new RuntimeException("Expected IllegalArgumentException for negative skip");
        } catch (IllegalArgumentException e) {
            if (!e.getMessage().equals("-1")) {
                throw new RuntimeException("Exception message mismatch for negative skip");
            }
        }
    }

    static void testSkipZero() {
        long[] data = {10L, 20L, 30L};
        TestLongPipeline pipeline = createPipeline(data);
        LongStream result = pipeline.skip(0L);
        if (result != pipeline) {
            throw new RuntimeException("skip(0) should return the same pipeline instance");
        }
    }

    static void testSkipPositive() {
        long[] data = {5L, 6L, 7L, 8L};
        TestLongPipeline pipeline = createPipeline(data);
        LongStream skipped = pipeline.skip(2L);
        long[] expected = {7L, 8L};
        long[] actual = skipped.toArray();
        if (actual.length != expected.length) {
            throw new RuntimeException("skip(2) result length mismatch");
        }
        for (int i = 0; i < expected.length; i++) {
            if (actual[i] != expected[i]) {
                throw new RuntimeException("skip(2) result element mismatch at index " + i);
            }
        }
    }

    private static TestLongPipeline createPipeline(long[] data) {
        Spliterator.OfLong spliterator = Spliterators.spliterator(data, 0);
        return new TestLongPipeline(Spliterators.iterator(spliterator), 0, false);
    }

    // Adapter constructor to create TestLongPipeline from PrimitiveIterator.OfLong
    TestLongPipeline(PrimitiveIterator.OfLong it, int sourceFlags, boolean parallel) {
        super(Spliterators.spliteratorUnknownSize(it, 0), sourceFlags, parallel);
    }
}
