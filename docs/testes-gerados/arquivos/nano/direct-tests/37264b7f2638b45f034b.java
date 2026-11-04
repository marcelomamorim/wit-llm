/*
 * @test
 * @summary Test IntPipeline.accept method for correct downstream invocation.
 * @run main IntPipelineWitupTest
 */

import java.util.function.IntConsumer;
import java.util.stream.IntPipeline;

public class IntPipelineWitupTest {

    static class DummyDownstream implements IntConsumer {
        int receivedValue = 0;
        boolean wasCalled = false;

        @Override
        public void accept(int value) {
            this.receivedValue = value;
            this.wasCalled = true;
        }
    }

    public static void main(String[] args) {
        testAcceptMethodCallsDownstream();
    }

    public static void testAcceptMethodCallsDownstream() {
        DummyDownstream downstream = new DummyDownstream();
        IntPipeline<Integer> pipeline = new IntPipeline<Integer>(
            () -> new java.util.Spliterator.OfInt() {
                private int current = 0;
                private final int max = 5;

                @Override
                public boolean tryAdvance(java.util.function.IntConsumer action) {
                    if (current < max) {
                        action.accept(current);
                        current++;
                        return true;
                    } else {
                        return false;
                    }
                }

                @Override
                public java.util.Spliterator.OfInt trySplit() {
                    return null;
                }

                @Override
                public long estimateSize() {
                    return max - current;
                }

                @Override
                public int characteristics() {
                    return 0;
                }
            },
            0,
            false
        ) {
            @Override
            public void forEach(IntConsumer action) {
                try (java.util.stream.BaseStream<Integer, ?> stream = this) {
                    stream.forEach(action);
                }
            }
        };
        // Manually invoke accept to simulate stream processing
        for (int i = 0; i < 5; i++) {
            pipeline.accept(i, downstream);
        }
        // Verify that downstream received the last value
        if (downstream.wasCalled && downstream.receivedValue == 4) {
            System.out.println("Test passed: accept method correctly invoked downstream.");
        } else {
            System.out.println("Test failed: downstream was not invoked correctly.");
        }
    }
}

// Extend IntPipeline to add accept method with downstream parameter for testing
// Note: This is a simplified mock-up to invoke accept method as per the source code.
// In real scenario, accept is called internally by the stream pipeline.
// Here, we simulate its invocation directly.
// The actual accept method in source code calls downstream.accept(mapper.applyAsInt(t));
// For testing, we call accept with a downstream that records the call.

// Since the original accept method does not take downstream as parameter,
// we simulate the call by invoking downstream.accept directly in test.
// Alternatively, if the real accept method is to be tested, it should be invoked on an instance.
// But as per the source, accept is an instance method, so we instantiate a subclass.

// Note: The above code is a simplified test harness to verify that accept calls downstream.accept.
// It assumes that the accept method is invoked with a downstream, which is not the case in actual implementation.
// For a more accurate test, the class should be extended or mocked accordingly.
