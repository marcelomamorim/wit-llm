/*
 * @test
 * @summary Test IntPipeline.forEach method with simple IntConsumer
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntPipeline;
import java.util.stream.ForEachOps;
import java.util.function.IntConsumer;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.AbstractPipeline;
import java.util.stream.StreamOpFlag;
import java.util.function.Supplier;

public class IntPipelineWitupTest {

    // Minimal concrete subclass of IntPipeline to allow testing
    static class TestIntPipeline extends IntPipeline<Integer> {

        private final int[] elements;

        TestIntPipeline(int[] elements) {
            super(new Supplier<Spliterator<Integer>>() {
                public Spliterator<Integer> get() {
                    return Spliterators.spliterator(elements, 0);
                }
            }, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT | StreamOpFlag.NOT_SIZED, false);
            this.elements = elements;
        }

        @Override
        public void forEach(IntConsumer action) {
            super.forEach(action);
        }

        // Override evaluate to directly consume elements for testing
        @Override
        <P_IN> void evaluate(java.util.stream.TerminalOp<Integer, P_IN> terminalOp) {
            IntConsumer consumer = ForEachOps.makeInt((IntConsumer) terminalOp, false);
            for (int e : elements) {
                consumer.accept(e);
            }
        }
    }

    public static void main(String[] args) {
        // Test 1: forEach with a consumer that sums elements
        final int[] sum = new int[1];
        int[] data = {1, 2, 3, 4, 5};
        TestIntPipeline pipeline = new TestIntPipeline(data);
        pipeline.forEach(new IntConsumer() {
            public void accept(int value) {
                sum[0] += value;
            }
        });
        if (sum[0] != 15) {
            throw new AssertionError("Sum should be 15 but was " + sum[0]);
        }

        // Test 2: forEach with a consumer that counts elements
        final int[] count = new int[1];
        pipeline.forEach(new IntConsumer() {
            public void accept(int value) {
                count[0]++;
            }
        });
        if (count[0] != data.length) {
            throw new AssertionError("Count should be " + data.length + " but was " + count[0]);
        }

        // Test 3: forEach with a consumer that does nothing
        pipeline.forEach(new IntConsumer() {
            public void accept(int value) {
                // no-op
            }
        });

        // Test 4: forEach with empty data
        TestIntPipeline emptyPipeline = new TestIntPipeline(new int[0]);
        final int[] emptyCount = new int[1];
        emptyPipeline.forEach(new IntConsumer() {
            public void accept(int value) {
                emptyCount[0]++;
            }
        });
        if (emptyCount[0] != 0) {
            throw new AssertionError("Empty pipeline should not call accept");
        }
    }
}
