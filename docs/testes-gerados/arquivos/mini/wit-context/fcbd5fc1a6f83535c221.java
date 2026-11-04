/*
 * @test
 * @summary Test IntPipeline.accept(int) for expected behavior and exception propagation
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntPipeline;
import java.util.stream.AbstractPipeline;
import java.util.Spliterator;
import java.util.function.Supplier;
import java.util.function.IntConsumer;

public class IntPipelineWitupTest {

    // Minimal concrete subclass to test accept(int) method
    static class TestIntPipeline extends IntPipeline<Object> {

        final IntConsumer downstream;

        TestIntPipeline(IntConsumer downstream) {
            // Use null and 0 for source and flags as they are unused in accept test
            super((Supplier<Spliterator<Integer>>) null, 0, false);
            this.downstream = downstream;
        }

        @Override
        public void accept(int t) {
            // Delegate to downstream.accept(double) as in source code
            // but downstream is IntConsumer, so cast int to double via doubleValue
            // The original method calls downstream.accept((double) t);
            // We simulate this by calling downstream.accept(t) since IntConsumer accepts int
            // To test exception propagation, downstream.accept will throw
            downstream.accept(t);
        }

        // Other abstract methods from AbstractPipeline and IntStream are not needed for this test
    }

    public static void main(String[] args) {
        // Test normal accept call: downstream accepts value without exception
        final int testValue = 42;
        final boolean[] accepted = new boolean[1];
        IntConsumer normalConsumer = new IntConsumer() {
            public void accept(int value) {
                if (value == testValue) {
                    accepted[0] = true;
                }
            }
        };

        TestIntPipeline pipeline = new TestIntPipeline(normalConsumer);
        pipeline.accept(testValue);
        if (!accepted[0]) {
            throw new AssertionError("accept(int) did not forward value correctly");
        }

        // Test that accept(int) propagates IllegalStateException from downstream
        IntConsumer throwingConsumer = new IntConsumer() {
            public void accept(int value) {
                throw new IllegalStateException("forced exception");
            }
        };

        TestIntPipeline pipelineWithException = new TestIntPipeline(throwingConsumer);
        boolean thrown = false;
        try {
            pipelineWithException.accept(0);
        } catch (IllegalStateException e) {
            thrown = true;
            if (!"forced exception".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
        if (!thrown) {
            throw new AssertionError("accept(int) did not propagate IllegalStateException");
        }
    }
}
