/*
 * @test
 * @summary Test IntPipeline.min() for normal and consumed state
 * @run main IntPipelineWitupTest
 */

import java.util.OptionalInt;
import java.util.Spliterator;
import java.util.stream.IntPipeline;
import java.util.stream.AbstractPipeline;
import java.util.function.Supplier;

public class IntPipelineWitupTest {

    // Minimal concrete subclass of IntPipeline to allow testing
    static class TestIntPipeline extends IntPipeline<Integer> {

        private final Supplier<? extends Spliterator<Integer>> sourceSupplier;
        private boolean consumed = false;

        TestIntPipeline(Supplier<? extends Spliterator<Integer>> sourceSupplier, int sourceFlags, boolean parallel) {
            super(sourceSupplier, sourceFlags, parallel);
            this.sourceSupplier = sourceSupplier;
        }

        TestIntPipeline(AbstractPipeline<?, Integer, ?> upstream, int opFlags) {
            super(upstream, opFlags);
            this.sourceSupplier = null;
        }

        // Override evaluate to simulate consumption
        @Override
        public <P_IN> Object evaluate(AbstractPipeline<T, Integer, ?> terminalOp, Object context) {
            consumed = true;
            return super.evaluate(terminalOp, context);
        }

        // Expose a method to simulate consumption for testing
        void markConsumed() {
            consumed = true;
        }

        boolean isConsumed() {
            return consumed;
        }
    }

    public static void main(String[] args) {
        // Test 1: min() on a simple IntPipeline with known values
        IntPipeline<Integer> pipeline = new TestIntPipeline(
            new Supplier<Spliterator<Integer>>() {
                public Spliterator<Integer> get() {
                    return java.util.Spliterators.spliterator(new int[] {5, 3, 9, 1, 4}, 0);
                }
            }, 0, false);

        OptionalInt result = pipeline.min();
        if (!result.isPresent() || result.getAsInt() != 1) {
            throw new AssertionError("min() did not return expected minimum value 1, got: " + result);
        }

        // Test 2: min() on empty pipeline returns OptionalInt.empty
        IntPipeline<Integer> emptyPipeline = new TestIntPipeline(
            new Supplier<Spliterator<Integer>>() {
                public Spliterator<Integer> get() {
                    return java.util.Spliterators.emptySpliterator();
                }
            }, 0, false);

        OptionalInt emptyResult = emptyPipeline.min();
        if (emptyResult.isPresent()) {
            throw new AssertionError("min() on empty pipeline should return OptionalInt.empty");
        }

        // Test 3: calling min() twice on the same pipeline should throw IllegalStateException
        TestIntPipeline consumedPipeline = new TestIntPipeline(
            new Supplier<Spliterator<Integer>>() {
                public Spliterator<Integer> get() {
                    return java.util.Spliterators.spliterator(new int[] {2, 4, 6}, 0);
                }
            }, 0, false);

        // First call to min() consumes the pipeline
        OptionalInt firstCall = consumedPipeline.min();
        if (!firstCall.isPresent() || firstCall.getAsInt() != 2) {
            throw new AssertionError("First min() call returned wrong result: " + firstCall);
        }

        // Second call should throw IllegalStateException
        boolean threw = false;
        try {
            consumedPipeline.min();
        } catch (IllegalStateException e) {
            threw = true;
        } catch (Exception e) {
            throw new AssertionError("Expected IllegalStateException but got " + e);
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalStateException on second min() call but none thrown");
        }

        System.out.println("All tests passed.");
    }
}
