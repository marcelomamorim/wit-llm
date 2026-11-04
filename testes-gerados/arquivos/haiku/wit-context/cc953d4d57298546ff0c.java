/*
 * @test
 * @summary Witup-derived checks for PriorityBlockingQueue.take(): priority order, interrupt handling, and blocking wake-up
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicReference;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) throws Exception {
        testTakeReturnsInPriorityOrder();
        testTakeThrowsInterruptedExceptionWhenInterrupted();
        testTakeBlocksUntilElementAvailable();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testTakeReturnsInPriorityOrder() throws InterruptedException {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.offer(3);
        q.offer(1);
        q.offer(2);
        check(q.take() == 1, "first take should return smallest element");
        check(q.take() == 2, "second take should return next smallest element");
        check(q.take() == 3, "third take should return largest remaining element");
        check(q.isEmpty(), "queue should be empty after taking all elements");
    }

    static void testTakeThrowsInterruptedExceptionWhenInterrupted() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        Thread.currentThread().interrupt();
        boolean thrown = false;
        try {
            q.take();
        } catch (InterruptedException e) {
            thrown = true;
        } finally {
            Thread.interrupted();
        }
        check(thrown, "take() on interrupted thread should throw InterruptedException");
    }

    static void testTakeBlocksUntilElementAvailable() throws InterruptedException {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        AtomicReference<Integer> got = new AtomicReference<>();
        Thread t = new Thread(() -> {
            try {
                got.set(q.take());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        t.start();
        q.offer(42);
        t.join(10000);
        check(!t.isAlive(), "consumer thread should finish after an element is offered");
        check(got.get() != null && got.get() == 42, "blocked take() should return the offered element");
    }
}
