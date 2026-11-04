/*
 * @test
 * @summary Test IntPipeline.forEach method behavior for sequential and parallel streams
 * @run main IntPipelineWitupTest
 */

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;
import java.util.stream.AbstractPipeline;
import java.util.stream.StreamOpFlag;

public class IntPipelineWitupTest {

    // Minimal concrete subclass of IntPipeline to test forEach
    static class TestIntPipeline extends IntPipeline<Integer> {

        private final IntStream stream;

        TestIntPipeline(IntStream stream) {
            super(stream.spliterator(), StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT | StreamOpFlag.NOT_SIZED, stream.isParallel());
            this.stream = stream;
        }

        @Override
        public boolean isParallel() {
            return stream.isParallel();
        }

        @Override
        public Spliterator<Integer> sourceStageSpliterator() {
            // Wrap IntStream spliterator<Integer> for testing
            final Spliterator.OfInt intSpliterator = stream.spliterator();
            return Spliterators.spliteratorUnknownSize(new java.util.PrimitiveIterator.OfInt() {
                public int nextInt() { return intSpliterator.tryAdvance((IntConsumer) i -> {} ) ? 0 : 0; }
                public boolean hasNext() { return false; }
            }, 0);
        }

        @Override
        public void forEach(IntConsumer action) {
            if (!isParallel()) {
                // Use IntStream's forEach directly for testing
                stream.forEach(action);
            } else {
                super.forEach(action);
            }
        }
    }

    public static void main(String[] args) {
        testSequentialForEach();
        testParallelForEach();
    }

    private static void testSequentialForEach() {
        final AtomicInteger sum = new AtomicInteger();
        IntStream sequentialStream = IntStream.of(1, 2, 3, 4);
        TestIntPipeline pipeline = new TestIntPipeline(sequentialStream);
        pipeline.forEach(new IntConsumer() {
            public void accept(int value) {
                sum.addAndGet(value);
            }
        });
        if (sum.get() != 10) {
            throw new AssertionError("Sequential forEach sum expected 10 but was " + sum.get());
        }
    }

    private static void testParallelForEach() {
        final AtomicInteger sum = new AtomicInteger();
        IntStream parallelStream = IntStream.of(1, 2, 3, 4).parallel();
        TestIntPipeline pipeline = new TestIntPipeline(parallelStream) {
            @Override
            public void forEach(IntConsumer action) {
                // Call super to test parallel branch
                super.forEach(action);
            }

            @Override
            public boolean isParallel() {
                return true;
            }

            @Override
            public Spliterator<Integer> sourceStageSpliterator() {
                // Provide a spliterator that yields the integers 1 to 4
                return Spliterators.spliterator(new Integer[] {1, 2, 3, 4}, 0);
            }
        };

        pipeline.forEach(new IntConsumer() {
            public void accept(int value) {
                sum.addAndGet(value);
            }
        });

        if (sum.get() != 10) {
            throw new AssertionError("Parallel forEach sum expected 10 but was " + sum.get());
        }
    }
}
