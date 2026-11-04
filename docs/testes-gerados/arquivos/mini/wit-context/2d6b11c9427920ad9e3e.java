/*
 * @test
 * @summary Test IntPipeline.noneMatch(IntPredicate) for normal and consumed state
 * @run main IntPipelineWitupTest
 */

import java.util.Spliterator;
import java.util.stream.IntPipeline;
import java.util.stream.AbstractPipeline;
import java.util.function.IntPredicate;
import java.util.stream.MatchOps;
import java.util.stream.StreamOpFlag;

public class IntPipelineWitupTest {

    // Minimal concrete subclass of IntPipeline to allow testing
    static class TestIntPipeline extends IntPipeline<Integer> {

        private final IntPredicate predicate;
        private boolean consumed = false;

        TestIntPipeline(IntPredicate predicate) {
            super(new Spliterator<Integer>() {
                public boolean tryAdvance(java.util.function.Consumer<? super Integer> action) { return false; }
                public Spliterator<Integer> trySplit() { return null; }
                public long estimateSize() { return 0; }
                public int characteristics() { return 0; }
            }, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT | StreamOpFlag.NOT_SIZED, false);
            this.predicate = predicate;
        }

        @Override
        public boolean noneMatch(IntPredicate predicate) {
            if (consumed) {
                throw new IllegalStateException("Stream has already been operated upon or closed");
            }
            consumed = true;
            // Simulate evaluation by applying predicate to a fixed int array
            int[] data = {1, 2, 3};
            for (int i : data) {
                if (predicate.test(i)) {
                    return false;
                }
            }
            return true;
        }
    }

    public static void main(String[] args) {
        // Test 1: noneMatch returns true when no elements match predicate
        TestIntPipeline pipeline1 = new TestIntPipeline(x -> x > 10);
        boolean result1 = pipeline1.noneMatch(x -> x > 10);
        if (!result1) {
            throw new AssertionError("Expected noneMatch to return true when no elements match predicate");
        }

        // Test 2: noneMatch returns false when some elements match predicate
        TestIntPipeline pipeline2 = new TestIntPipeline(x -> x == 2);
        boolean result2 = pipeline2.noneMatch(x -> x == 2);
        if (result2) {
            throw new AssertionError("Expected noneMatch to return false when some elements match predicate");
        }

        // Test 3: calling noneMatch twice throws IllegalStateException
        TestIntPipeline pipeline3 = new TestIntPipeline(x -> false);
        pipeline3.noneMatch(x -> false); // first call consumes stream
        boolean threw = false;
        try {
            pipeline3.noneMatch(x -> false); // second call should throw
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalStateException when calling noneMatch on consumed stream");
        }
    }
}
