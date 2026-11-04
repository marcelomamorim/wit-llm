/*
 * @test
 * @summary Test IntPipeline.allMatch behavior including linkedOrConsumed state
 * @run main IntPipelineWitupTest
 */

import java.util.Spliterator;
import java.util.stream.IntPipeline;
import java.util.stream.AbstractPipeline;
import java.util.function.IntPredicate;
import java.util.stream.MatchOps;
import java.util.stream.TerminalOp;
import java.util.stream.StreamOpFlag;

public class IntPipelineWitupTest {

    // Minimal subclass to instantiate IntPipeline for testing
    static class TestIntPipeline extends IntPipeline<Integer> {

        // Constructor for head pipeline from Spliterator
        TestIntPipeline(Spliterator<Integer> source, int sourceFlags, boolean parallel) {
            super(source, sourceFlags, parallel);
        }

        // Constructor for intermediate pipeline
        TestIntPipeline(AbstractPipeline<?, Integer, ?> upstream, int opFlags) {
            super(upstream, opFlags);
        }

        // Expose evaluate method for testing
        public <R> R testEvaluate(TerminalOp<Integer, R> terminalOp) {
            return evaluate(terminalOp);
        }
    }

    // Dummy Spliterator for testing
    static class DummySpliterator implements Spliterator<Integer> {
        public boolean tryAdvance(java.util.function.Consumer<? super Integer> action) {
            return false;
        }
        public Spliterator<Integer> trySplit() { return null; }
        public long estimateSize() { return 0; }
        public int characteristics() { return 0; }
    }

    // Dummy TerminalOp to simulate MatchOps.makeInt behavior
    static class DummyMatchOp implements TerminalOp<Integer, Boolean> {
        private final IntPredicate predicate;
        DummyMatchOp(IntPredicate predicate) {
            this.predicate = predicate;
        }
        public <S> Boolean evaluateSequential(AbstractPipeline<Integer, Integer, S> pipeline) {
            // For testing, return true if predicate.test(1) is true, else false
            return predicate.test(1);
        }
        public <S> Boolean evaluateParallel(AbstractPipeline<Integer, Integer, S> pipeline) {
            return evaluateSequential(pipeline);
        }
        public int getOpFlags() { return 0; }
    }

    public static void main(String[] args) {
        // Create a TestIntPipeline head with dummy spliterator
        TestIntPipeline pipeline = new TestIntPipeline(new DummySpliterator(), 0, false);

        // Test 1: Normal call with predicate that returns true
        IntPredicate alwaysTrue = new IntPredicate() {
            public boolean test(int value) { return true; }
        };
        boolean result1 = pipeline.allMatch(alwaysTrue);
        if (!result1) {
            throw new AssertionError("allMatch should return true when predicate always true");
        }

        // Test 2: Normal call with predicate that returns false
        IntPredicate alwaysFalse = new IntPredicate() {
            public boolean test(int value) { return false; }
        };
        boolean result2 = pipeline.allMatch(alwaysFalse);
        if (result2) {
            throw new AssertionError("allMatch should return false when predicate always false");
        }

        // Test 3: Simulate linkedOrConsumed state by calling allMatch twice on same pipeline
        // The second call should throw IllegalStateException as per WIT expath
        boolean exceptionThrown = false;
        try {
            pipeline.allMatch(alwaysTrue); // first call consumes pipeline
            pipeline.allMatch(alwaysTrue); // second call should fail
        } catch (IllegalStateException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalStateException on second allMatch call due to linkedOrConsumed");
        }

        System.out.println("All tests passed");
    }
}