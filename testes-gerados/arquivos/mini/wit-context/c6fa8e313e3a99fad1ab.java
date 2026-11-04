/*
 * @test
 * @summary Test IntPipeline.forEachOrdered behavior including IllegalStateException on consumed pipeline
 * @run main IntPipelineWitupTest
 */

import java.util.Spliterator;
import java.util.function.IntConsumer;
import java.util.stream.IntPipeline;
import java.util.stream.AbstractPipeline;
import java.util.stream.ForEachOps;

public class IntPipelineWitupTest {

    // Minimal subclass to instantiate IntPipeline for testing
    static class TestIntPipeline extends IntPipeline<Integer> {

        private boolean consumed = false;

        TestIntPipeline() {
            super(new Spliterator<Integer>() {
                boolean done = false;

                @Override
                public boolean tryAdvance(java.util.function.Consumer<? super Integer> action) {
                    if (done) return false;
                    action.accept(1);
                    done = true;
                    return true;
                }

                @Override
                public Spliterator<Integer> trySplit() {
                    return null;
                }

                @Override
                public long estimateSize() {
                    return 1;
                }

                @Override
                public int characteristics() {
                    return 0;
                }
            }, 0, false);
        }

        @Override
        public void forEachOrdered(IntConsumer action) {
            if (consumed) {
                throw new IllegalStateException("Stream has already been operated upon or closed");
            }
            consumed = true;
            super.forEachOrdered(action);
        }
    }

    public static void main(String[] args) {
        TestIntPipeline pipeline = new TestIntPipeline();

        final int[] sum = {0};
        IntConsumer adder = new IntConsumer() {
            public void accept(int value) {
                sum[0] += value;
            }
        };

        // First call should succeed and sum to 1
        pipeline.forEachOrdered(adder);
        if (sum[0] != 1) {
            throw new AssertionError("Expected sum 1 but got " + sum[0]);
        }

        // Second call should throw IllegalStateException due to consumed pipeline
        boolean threw = false;
        try {
            pipeline.forEachOrdered(adder);
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalStateException on second forEachOrdered call");
        }

        System.out.println("Test passed");
    }
}
