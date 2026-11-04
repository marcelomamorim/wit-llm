/*
 * @test
 * @summary Test LongPipeline.accept method for correct downstream invocation
 * @run main LongPipelineWitupTest
 */

import java.util.stream.LongPipeline;
import java.util.function.LongConsumer;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testAcceptMethod();
    }

    public static void testAcceptMethod() {
        class DummyConsumer implements LongConsumer {
            public long receivedValue = 0;
            public boolean accepted = false;

            @Override
            public void accept(long t) {
                receivedValue = t;
                accepted = true;
            }
        }

        DummyConsumer dummy = new DummyConsumer();
        // Create a LongPipeline with a dummy source and downstream
        LongPipeline<Long> pipeline = new LongPipeline<Long>(
            () -> java.util.Spliterators.spliteratorUnknownSize(
                new java.util.Iterator<Long>() {
                    int count = 0;
                    public boolean hasNext() { return count < 1; }
                    public Long next() { count++; return 42L; }
                }, 0), 0), 0, false
        ) {
            @Override
            public void forEach(LongConsumer action) {
                accept(42L);
                action.accept(42L);
            }
        };
        // Override downstream to our dummy
        pipeline.downstream = dummy;
        // Call accept method
        pipeline.accept(42L);
        // Verify that downstream received the value
        if (dummy.receivedValue != 42L || !dummy.accepted) {
            throw new AssertionError("accept method did not invoke downstream correctly");
        }
    }
}
