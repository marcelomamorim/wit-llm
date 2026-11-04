/*
 * @test
 * @summary Test IntPipeline.reduce(IntBinaryOperator) for normal and consumed states
 * @run main IntPipelineWitupTest
 */

import java.util.OptionalInt;
import java.util.Spliterator;
import java.util.function.IntBinaryOperator;
import java.util.function.Supplier;
import java.util.stream.IntPipeline;
import java.util.stream.StreamOpFlag;

public class IntPipelineWitupTest {

    // Minimal IntPipeline subclass to test reduce method
    static class TestIntPipeline extends IntPipeline<Integer> {

        private final Supplier<? extends Spliterator<Integer>> sourceSupplier;
        private boolean consumed = false;

        TestIntPipeline(Supplier<? extends Spliterator<Integer>> sourceSupplier, int sourceFlags, boolean parallel) {
            super(sourceSupplier, sourceFlags, parallel);
            this.sourceSupplier = sourceSupplier;
        }

        // Override evaluate to simulate consumption and call reduce operation
        @Override
        public <R, P_IN> R evaluate(java.util.stream.TerminalOp<Integer, R> terminalOp) {
            if (consumed) {
                throw new IllegalStateException("Stream has already been operated upon or closed");
            }
            consumed = true;
            // Use the source spliterator to create a pipeline helper
            Spliterator<Integer> spliterator = sourceSupplier.get();
            // Use AbstractPipeline's evaluate method indirectly by calling super
            return super.evaluate(terminalOp);
        }
    }

    public static void main(String[] args) {
        // IntBinaryOperator that sums two ints
        IntBinaryOperator sumOp = new IntBinaryOperator() {
            public int applyAsInt(int left, int right) {
                return left + right;
            }
        };

        // Supplier of a Spliterator over fixed integers
        Supplier<Spliterator<Integer>> supplier = new Supplier<Spliterator<Integer>>() {
            public Spliterator<Integer> get() {
                return java.util.Spliterators.spliterator(new int[] {1, 2, 3, 4}, 0);
            }
        };

        // Create a TestIntPipeline instance
        TestIntPipeline pipeline = new TestIntPipeline(supplier, StreamOpFlag.NOT_SORTED, false);

        // Test normal reduce call
        OptionalInt result = pipeline.reduce(sumOp);
        if (!result.isPresent() || result.getAsInt() != 10) {
            throw new AssertionError("Expected sum 10 but got " + result);
        }

        // Test that second reduce call throws IllegalStateException due to consumed pipeline
        boolean threw = false;
        try {
            pipeline.reduce(sumOp);
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalStateException on second reduce call");
        }

        System.out.println("Test passed");
    }
}
