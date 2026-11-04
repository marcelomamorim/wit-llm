/*
 * @test
 * @summary Test for PriorityBlockingQueue.tryAdvance with null Consumer
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.function.Consumer;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithNullConsumer();
        testTryAdvanceWithValidConsumer();
    }

    static void testTryAdvanceWithNullConsumer() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        Consumer<Integer> consumer = null;
        boolean threwException = false;
        try {
            queue.tryAdvance(consumer);
        } catch (NullPointerException e) {
            threwException = true;
        }
        if (!threwException) {
            throw new AssertionError("Expected NullPointerException when passing null Consumer");
        }
    }

    static void testTryAdvanceWithValidConsumer() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.add(42);
        final boolean[] accepted = {false};
        Consumer<Integer> consumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer t) {
                if (t != null && t == 42) {
                    accepted[0] = true;
                }
            }
        };
        boolean result = queue.tryAdvance(consumer);
        if (!result) {
            throw new AssertionError("tryAdvance should return true when element is available");
        }
        if (!accepted[0]) {
            throw new AssertionError("Consumer did not accept the element as expected");
        }
    }
}
