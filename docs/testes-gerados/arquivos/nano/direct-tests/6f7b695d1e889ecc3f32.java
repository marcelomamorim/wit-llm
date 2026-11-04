/*
 * @test    @summary Test IntPipeline.accept method for correct downstream invocation
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntPipeline;
import java.util.function.IntConsumer;

public class IntPipelineWitupTest {

    static class DummyConsumer implements IntConsumer {
        int receivedValue = -1;
        boolean wasCalled = false;

        @Override
        public void accept(int t) {
            this.receivedValue = t;
            this.wasCalled = true;
        }
    }

    public static void main(String[] args) {
        DummyConsumer dummy = new DummyConsumer();
        // Create a minimal subclass of IntPipeline to test accept method
        IntPipeline<?> pipeline = new IntPipeline<>(
            () -> new java.util.Spliterator.OfInt() {
                boolean done = false;
                @Override
                public boolean tryAdvance(java.util.function.IntConsumer action) {
                    if (done) return false;
                    action.accept(42);
                    done = true;
                    return true;
                }
                @Override
                public java.util.Spliterator.OfInt trySplit() { return null; }
                @Override
                public long estimateSize() { return 1; }
                @Override
                public int characteristics() { return 0; }
            },
            0,
            false
        ) {
            @Override
            public void accept(int t) {
                downstream.accept(t);
            }
        };
        // Invoke accept method with test value
        pipeline.accept(99);
        // Verify that downstream consumer received the value
        if (pipeline.downstream instanceof IntConsumer) {
            IntConsumer consumer = (IntConsumer) pipeline.downstream;
            if (consumer instanceof DummyConsumer) {
                DummyConsumer dc = (DummyConsumer) consumer;
                if (dc.receivedValue != 99 || !dc.wasCalled) {
                    throw new AssertionError("Downstream consumer did not receive the correct value or was not called")
                }
            } else {
                throw new AssertionError("Downstream is not an instance of DummyConsumer")
            }
        } else {
            throw new AssertionError("Downstream is not an IntConsumer")
        }
        System.out.println("Test passed: accept method correctly forwards value.");
    }
}
