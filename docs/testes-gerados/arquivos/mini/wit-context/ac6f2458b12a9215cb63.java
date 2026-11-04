/*
 * @test
 * @summary Test IntPipeline.forEachOrdered behavior including parallel and sequential cases
 * @run main IntPipelineWitupTest
 */

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;
import java.util.stream.AbstractPipeline;
import java.util.stream.IntPipeline;

public class IntPipelineWitupTest {

    // Minimal concrete subclass of IntPipeline to test forEachOrdered
    static class TestIntPipeline extends IntPipeline<Integer> {

        private final boolean parallel;
        private final Spliterator<Integer> spliterator;

        TestIntPipeline(Spliterator<Integer> source, int sourceFlags, boolean parallel) {
            super(source, sourceFlags, parallel);
            this.parallel = parallel;
            this.spliterator = source;
        }

        @Override
        public boolean isParallel() {
            return parallel;
        }

        // Expose sourceStageSpliterator for adapt call
        Spliterator<Integer> sourceStageSpliterator() {
            return spliterator;
        }

        // Override forEachOrdered to call IntPipeline's implementation
        @Override
        public void forEachOrdered(IntConsumer action) {
            super.forEachOrdered(action);
        }

        // Provide minimal implementation of abstract methods from AbstractPipeline
        @Override
        public IntStream sequential() {
            throw new UnsupportedOperationException();
        }

        @Override
        public IntStream parallel() {
            throw new UnsupportedOperationException();
        }

        @Override
        public IntStream unordered() {
            throw new UnsupportedOperationException();
        }

        @Override
        public IntStream onClose(Runnable closeHandler) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void close() {
            // no-op
        }
    }

    public static void main(String[] args) {
        testSequentialForEachOrdered();
        testParallelForEachOrdered();
    }

    private static void testSequentialForEachOrdered() {
        final int[] sum = new int[1];
        IntConsumer action = new IntConsumer() {
            public void accept(int value) {
                sum[0] += value;
            }
        };

        // Create a spliterator over fixed integers
        Spliterator<Integer> spliterator = Spliterators.spliterator(new Integer[] {1, 2, 3, 4, 5}, 0);

        TestIntPipeline pipeline = new TestIntPipeline(spliterator, 0, false); // sequential

        pipeline.forEachOrdered(action);

        if (sum[0] != 15) {
            throw new AssertionError("Sequential forEachOrdered sum expected 15 but was " + sum[0]);
        }
    }

    private static void testParallelForEachOrdered() {
        final StringBuilder sb = new StringBuilder();
        IntConsumer action = new IntConsumer() {
            public void accept(int value) {
                synchronized(sb) {
                    sb.append(value);
                }
            }
        };

        // Create a spliterator over fixed integers
        Spliterator<Integer> spliterator = Spliterators.spliterator(new Integer[] {6, 7, 8}, 0);

        TestIntPipeline pipeline = new TestIntPipeline(spliterator, 0, true); // parallel

        pipeline.forEachOrdered(action);

        String result = sb.toString();

        // Because forEachOrdered in parallel calls super.forEachOrdered, which is AbstractPipeline's implementation,
        // the order should be preserved as in the source spliterator
        if (!result.equals("678")) {
            throw new AssertionError("Parallel forEachOrdered expected '678' but was '" + result + "'");
        }
    }
}
