/*
 * @test
 * @summary Test LongPipeline.mapToObj(LongFunction) for null argument and basic behavior
 * @run main LongPipelineWitupTest
 */

import java.util.Objects;
import java.util.stream.Stream;
import java.util.function.LongFunction;
import java.util.stream.StreamSupport;
import java.util.Spliterator;
import java.util.stream.AbstractPipeline;
import java.util.stream.StreamOpFlag;

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
        public boolean isParallel() {
            return false;
        }

        @Override
        public Stream<Long> mapToObj(LongFunction<?> mapper) {
            return super.mapToObj(mapper);
        }

        // Implement abstract methods with minimal stubs
        @Override
        public void forEach(java.util.function.LongConsumer action) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Spliterator.OfLong spliterator() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        testMapToObjNull();
        testMapToObjBasic();
    }

    private static void testMapToObjNull() {
        // Create a dummy spliterator with no elements
        Spliterator<Long> emptySpliterator = new Spliterator<Long>() {
            public boolean tryAdvance(java.util.function.Consumer<? super Long> action) { return false; }
            public Spliterator<Long> trySplit() { return null; }
            public long estimateSize() { return 0; }
            public int characteristics() { return 0; }
        };

        TestLongPipeline pipeline = new TestLongPipeline(emptySpliterator, 0, false);

        try {
            pipeline.mapToObj(null);
            throw new RuntimeException("Expected NullPointerException for null mapper");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testMapToObjBasic() {
        // Create a spliterator over a single element
        final long value = 42L;
        Spliterator<Long> spliterator = new Spliterator<Long>() {
            boolean done = false;
            public boolean tryAdvance(java.util.function.Consumer<? super Long> action) {
                if (done) return false;
                action.accept(value);
                done = true;
                return true;
            }
            public Spliterator<Long> trySplit() { return null; }
            public long estimateSize() { return 1; }
            public int characteristics() { return Spliterator.SIZED | Spliterator.IMMUTABLE | Spliterator.ORDERED; }
        };

        TestLongPipeline pipeline = new TestLongPipeline(spliterator, 0, false);

        LongFunction<String> mapper = new LongFunction<String>() {
            public String apply(long l) {
                return "Value:" + l;
            }
        };

        Stream<String> resultStream = pipeline.mapToObj(mapper);

        // Collect results to verify mapping
        java.util.List<String> results = resultStream.collect(java.util.stream.Collectors.toList());

        if (results.size() != 1 || !"Value:42".equals(results.get(0))) {
            throw new RuntimeException("mapToObj did not produce expected mapped result");
        }
    }
}
