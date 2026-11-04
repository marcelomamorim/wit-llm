/*
 * @test
 * @summary Test IntPipeline.mapToLong behavior including null argument check
 * @run main IntPipelineWitupTest
 */

import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.function.IntToLongFunction;

public class IntPipelineWitupTest {

    // Minimal concrete subclass of IntPipeline to allow testing
    static class TestIntPipeline extends IntStream {

        private final IntStream delegate;

        TestIntPipeline(IntStream delegate) {
            this.delegate = delegate;
        }

        @Override
        public LongStream mapToLong(IntToLongFunction mapper) {
            return delegate.mapToLong(mapper);
        }

        // Implement abstract methods by delegating to the wrapped IntStream
        @Override
        public IntStream filter(java.util.function.IntPredicate predicate) {
            return delegate.filter(predicate);
        }

        @Override
        public void forEach(java.util.function.IntConsumer action) {
            delegate.forEach(action);
        }

        @Override
        public PrimitiveIterator.OfInt iterator() {
            return delegate.iterator();
        }

        @Override
        public Spliterator.OfInt spliterator() {
            return delegate.spliterator();
        }

        @Override
        public boolean isParallel() {
            return delegate.isParallel();
        }

        @Override
        public IntStream sequential() {
            return delegate.sequential();
        }

        @Override
        public IntStream parallel() {
            return delegate.parallel();
        }

        @Override
        public IntStream unordered() {
            return delegate.unordered();
        }

        @Override
        public IntStream onClose(Runnable closeHandler) {
            return delegate.onClose(closeHandler);
        }

        @Override
        public void close() {
            delegate.close();
        }
    }

    public static void main(String[] args) {
        testMapToLongWithValidMapper();
        testMapToLongWithNullMapper();
    }

    private static void testMapToLongWithValidMapper() {
        IntStream baseStream = IntStream.of(1, 2, 3);
        TestIntPipeline pipeline = new TestIntPipeline(baseStream);

        IntToLongFunction mapper = new IntToLongFunction() {
            public long applyAsLong(int value) {
                return value * 10L;
            }
        };

        LongStream longStream = pipeline.mapToLong(mapper);

        long[] results = longStream.toArray();

        if (results.length != 3 || results[0] != 10L || results[1] != 20L || results[2] != 30L) {
            throw new AssertionError("mapToLong did not correctly map values");
        }
    }

    private static void testMapToLongWithNullMapper() {
        IntStream baseStream = IntStream.of(1);
        TestIntPipeline pipeline = new TestIntPipeline(baseStream);

        try {
            pipeline.mapToLong(null);
            throw new AssertionError("Expected NullPointerException for null mapper");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
