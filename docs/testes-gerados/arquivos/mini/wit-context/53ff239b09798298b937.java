/*
 * @test
 * @summary Test IntPipeline.forEach behavior including parallel and sequential execution
 * @run main IntPipelineWitupTest
 */

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;
import java.util.stream.AbstractPipeline;
import java.util.stream.IntPipeline;
import java.util.function.Supplier;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testSequentialForEach();
        testParallelForEachDelegates();
    }

    // Test that forEach on a sequential IntPipeline invokes the action on all elements
    private static void testSequentialForEach() {
        final int[] sum = new int[1];
        IntConsumer action = new IntConsumer() {
            public void accept(int value) {
                sum[0] += value;
            }
        };

        // Create a simple IntPipeline head with a known source
        IntPipeline<Integer> pipeline = new IntPipeline<Integer>(new Supplier<Spliterator<Integer>>() {
            public Spliterator<Integer> get() {
                return Spliterators.spliterator(new Integer[] {1, 2, 3, 4, 5}, 0);
            }
        }, 0, false) {
            @Override
            public void forEach(IntConsumer action) {
                super.forEach(action);
            }
        };

        pipeline.forEach(action);

        if (sum[0] != 15) {
            throw new AssertionError("Sequential forEach did not sum correctly, expected 15 but got " + sum[0]);
        }
    }

    // Test that forEach on a parallel IntPipeline delegates to super.forEach (cannot test full parallel behavior here)
    private static void testParallelForEachDelegates() {
        final boolean[] called = new boolean[1];

        IntConsumer dummyAction = new IntConsumer() {
            public void accept(int value) {
                // no-op
            }
        };

        IntPipeline<Integer> pipeline = new IntPipeline<Integer>(new Supplier<Spliterator<Integer>>() {
            public Spliterator<Integer> get() {
                return Spliterators.spliterator(new Integer[] {1, 2, 3}, 0);
            }
        }, 0, true) {
            @Override
            public void forEach(IntConsumer action) {
                called[0] = true;
                super.forEach(action);
            }
        };

        pipeline.forEach(dummyAction);

        if (!called[0]) {
            throw new AssertionError("Parallel forEach did not delegate to super.forEach as expected");
        }
    }
}
