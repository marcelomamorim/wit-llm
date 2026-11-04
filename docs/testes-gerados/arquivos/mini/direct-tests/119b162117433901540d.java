/*
 * @test
 * @summary Test IntPipeline.anyMatch method behavior
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntPipeline;
import java.util.function.IntPredicate;
import java.util.stream.StreamSupport;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.AbstractPipeline;
import java.util.stream.MatchOps;
import java.util.stream.StreamOpFlag;
import java.util.function.Supplier;

public class IntPipelineWitupTest {

    // Minimal concrete subclass of IntPipeline to allow testing
    static class TestIntPipeline extends IntPipeline<Integer> {

        private final IntPredicate predicate;

        TestIntPipeline(IntPredicate predicate) {
            super(new Supplier<Spliterator<Integer>>() {
                public Spliterator<Integer> get() {
                    // Provide a spliterator over fixed int array
                    return Spliterators.spliterator(new int[] {1, 2, 3, 4, 5}, 0);
                }
            }, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT | StreamOpFlag.NOT_SIZED, false);
            this.predicate = predicate;
        }

        // Override evaluate to simulate the anyMatch behavior using MatchOps
        @Override
        public <R, P_IN> R evaluate(java.util.stream.PipelineHelper<Integer> helper, java.util.stream.Sink<R> sink) {
            throw new UnsupportedOperationException("Not used in this test");
        }

        // Provide a method to call anyMatch as in IntPipeline
        public boolean testAnyMatch() {
            // Use the same call as anyMatch does
            return evaluate(MatchOps.makeInt(predicate, MatchOps.MatchKind.ANY));
        }

        // Provide evaluate method to call MatchOps.evaluate
        public <R> R evaluate(java.util.stream.TerminalOp<Integer, R> terminalOp) {
            // Use the pipeline source spliterator
            Spliterator<Integer> spliterator = Spliterators.spliterator(new int[] {1, 2, 3, 4, 5}, 0);
            return terminalOp.evaluateParallel(this, spliterator);
        }
    }

    public static void main(String[] args) {
        // Test anyMatch with predicate that matches some elements
        IntPredicate matchEven = new IntPredicate() {
            public boolean test(int value) {
                return value % 2 == 0;
            }
        };

        TestIntPipeline pipeline = new TestIntPipeline(matchEven);
        boolean result = pipeline.testAnyMatch();
        if (!result) {
            throw new AssertionError("Expected anyMatch to return true for even numbers");
        }

        // Test anyMatch with predicate that matches no elements
        IntPredicate matchGreaterThanTen = new IntPredicate() {
            public boolean test(int value) {
                return value > 10;
            }
        };

        pipeline = new TestIntPipeline(matchGreaterThanTen);
        result = pipeline.testAnyMatch();
        if (result) {
            throw new AssertionError("Expected anyMatch to return false for no matching elements");
        }

        // Test anyMatch with predicate that matches all elements
        IntPredicate matchAll = new IntPredicate() {
            public boolean test(int value) {
                return true;
            }
        };

        pipeline = new TestIntPipeline(matchAll);
        result = pipeline.testAnyMatch();
        if (!result) {
            throw new AssertionError("Expected anyMatch to return true when all elements match");
        }

        System.out.println("All IntPipeline.anyMatch tests passed.");
    }
}
