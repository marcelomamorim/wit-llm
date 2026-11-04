/*
 * @test
 * @summary Test ReferencePipeline.max(Comparator) for normal and consumed pipeline cases
 * @run main ReferencePipelineWitupTest
 */

import java.util.Comparator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Supplier;
import java.util.stream.ReferencePipeline;
import java.util.stream.Stream;

public class ReferencePipelineWitupTest {

    // Minimal concrete subclass of ReferencePipeline to test max method
    static class TestReferencePipeline<T> extends ReferencePipeline<T, T> {

        TestReferencePipeline(Supplier<? extends Spliterator<?>> source, int sourceFlags, boolean parallel) {
            super(source, sourceFlags, parallel);
        }

        TestReferencePipeline(Spliterator<?> source, int sourceFlags, boolean parallel) {
            super(source, sourceFlags, parallel);
        }

        TestReferencePipeline(ReferencePipeline<?, T> upstream, int opFlags) {
            super(upstream, opFlags);
        }

        @Override
        public void forEach(java.util.function.Consumer<? super T> action) {
            throw new UnsupportedOperationException();
        }

        @Override
        public java.util.Spliterator<T> spliterator() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        testMaxWithNonEmptyStream();
        testMaxWithEmptyStream();
        testMaxOnConsumedStream();
    }

    private static void testMaxWithNonEmptyStream() {
        // Create a ReferencePipeline from a known source
        Supplier<Spliterator<Integer>> source = () -> Spliterators.spliterator(new Integer[]{1, 3, 2}, 0);
        ReferencePipeline<Integer, Integer> pipeline = new TestReferencePipeline<>(source, 0, false);

        Optional<Integer> max = pipeline.max(Comparator.naturalOrder());
        if (!max.isPresent() || max.get() != 3) {
            throw new AssertionError("Expected max 3 but got " + max);
        }
    }

    private static void testMaxWithEmptyStream() {
        Supplier<Spliterator<Integer>> source = () -> Spliterators.emptySpliterator();
        ReferencePipeline<Integer, Integer> pipeline = new TestReferencePipeline<>(source, 0, false);

        Optional<Integer> max = pipeline.max(Comparator.naturalOrder());
        if (max.isPresent()) {
            throw new AssertionError("Expected empty Optional for empty stream but got " + max);
        }
    }

    private static void testMaxOnConsumedStream() {
        Supplier<Spliterator<Integer>> source = () -> Spliterators.spliterator(new Integer[]{1, 2}, 0);
        ReferencePipeline<Integer, Integer> pipeline = new TestReferencePipeline<>(source, 0, false);

        // Consume the stream once
        pipeline.max(Comparator.naturalOrder());

        // Second call to max should throw IllegalStateException due to linkedOrConsumed
        boolean threw = false;
        try {
            pipeline.max(Comparator.naturalOrder());
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalStateException on second max call due to consumed pipeline");
        }
    }
}
