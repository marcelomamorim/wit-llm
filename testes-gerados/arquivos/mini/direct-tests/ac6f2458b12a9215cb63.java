/*
 * @test
 * @summary Test IntPipeline.forEachOrdered behavior for sequential and parallel streams
 * @run main IntPipelineWitupTest
 */

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;
import java.util.stream.AbstractPipeline;
import java.util.stream.IntPipeline;
import java.util.function.Supplier;

public class IntPipelineWitupTest {

    // Minimal concrete subclass of IntPipeline to test forEachOrdered
    static class TestIntPipeline extends IntPipeline<Integer> {

        private final boolean parallel;
        private final Spliterator<Integer> spliterator;

        TestIntPipeline(Spliterator<Integer> source, boolean parallel) {
            super(source, 0, parallel);
            this.parallel = parallel;
            this.spliterator = source;
        }

        @Override
        public boolean isParallel() {
            return parallel;
        }

        @Override
        public Spliterator<Integer> sourceStageSpliterator() {
            return spliterator;
        }

        @Override
        public void forEachOrdered(IntConsumer action) {
            // Call the actual method under test
            super.forEachOrdered(action);
        }
    }

    public static void main(String[] args) {
        testSequentialForEachOrdered();
        testParallelForEachOrdered();
    }

    private static void testSequentialForEachOrdered() {
        final int[] data = {1, 2, 3, 4, 5};
        Spliterator<Integer> spliterator = Spliterators.spliterator(data, 0);
        TestIntPipeline pipeline = new TestIntPipeline(spliterator, false); // sequential

        final StringBuilder sb = new StringBuilder();
        IntConsumer action = new IntConsumer() {
            public void accept(int value) {
                sb.append(value).append(",");
            }
        };

        pipeline.forEachOrdered(action);

        String result = sb.toString();
        if (!result.equals("1,2,3,4,5,")) {
            throw new AssertionError("Sequential forEachOrdered did not process elements in order: " + result);
        }
    }

    private static void testParallelForEachOrdered() {
        // Use IntStream parallel to create a pipeline and test forEachOrdered
        IntStream stream = IntStream.of(10, 20, 30, 40, 50).parallel();

        final StringBuilder sb = new StringBuilder();
        IntConsumer action = new IntConsumer() {
            public void accept(int value) {
                sb.append(value).append(",");
            }
        };

        // forEachOrdered on parallel stream should preserve order
        stream.forEachOrdered(action);

        String result = sb.toString();
        if (!result.equals("10,20,30,40,50,")) {
            throw new AssertionError("Parallel forEachOrdered did not preserve order: " + result);
        }
    }
}
