/*
 * @test
 * @summary Deterministic tests for ReferencePipeline.forEach(Consumer) to detect IllegalStateException on consumed pipeline
 * @run main ReferencePipelineWitupTest
 */

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.ReferencePipeline;
import java.util.stream.StreamSupport;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testForEachWithValidConsumer();
        testForEachOnConsumedPipelineThrows();
    }

    static void testForEachWithValidConsumer() {
        // Create a simple ReferencePipeline from a Spliterator over an array
        Integer[] data = {1, 2, 3};
        Spliterator<Integer> spliterator = Spliterators.spliterator(data, 0);
        ReferencePipeline<Integer, Integer> pipeline = new ReferencePipeline.Head<>(spliterator, 0, false);

        final int[] sum = new int[1];
        Consumer<Integer> consumer = new Consumer<Integer>() {
            public void accept(Integer i) {
                sum[0] += i;
            }
        };

        pipeline.forEach(consumer);

        if (sum[0] != 6) {
            throw new AssertionError("Sum should be 6 but was " + sum[0]);
        }
    }

    static void testForEachOnConsumedPipelineThrows() {
        Integer[] data = {1, 2, 3};
        Spliterator<Integer> spliterator = Spliterators.spliterator(data, 0);
        ReferencePipeline<Integer, Integer> pipeline = new ReferencePipeline.Head<>(spliterator, 0, false);

        Consumer<Integer> consumer = new Consumer<Integer>() {
            public void accept(Integer i) {
                // no-op
            }
        };

        // First consumption should succeed
        pipeline.forEach(consumer);

        // Second consumption should throw IllegalStateException due to linkedOrConsumed
        boolean threw = false;
        try {
            pipeline.forEach(consumer);
        } catch (IllegalStateException e) {
            threw = true;
        }

        if (!threw) {
            throw new AssertionError("Expected IllegalStateException on second forEach call");
        }
    }

    // Minimal subclass to instantiate ReferencePipeline.Head since it is package-private and abstract
    static final class Head<P_OUT> extends ReferencePipeline<Object, P_OUT> {
        Head(Spliterator<?> source, int sourceFlags, boolean parallel) {
            super(source, sourceFlags, parallel);
        }

        @Override
        public void forEach(Consumer<? super P_OUT> action) {
            super.forEach(action);
        }
    }
}
