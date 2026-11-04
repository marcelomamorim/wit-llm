/*
 * @test
 * @summary WITUP-derived behavioral tests for ArrayBlockingQueue.take()
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicReference;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) throws Exception {
        testInterruptedBeforeTakeThrowsInterruptedException();
        testTakeReturnsHeadInFifoOrder();
        testBlockedTakeIsInterrupted();
        System.out.println("PASSED");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testInterruptedBeforeTakeThrowsInterruptedException() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(1);
        Thread.currentThread().interrupt();
        boolean thrown = false;
        try {
            q.take();
        } catch (InterruptedException e) {
            thrown = true;
        }
        check(thrown, "take() on interrupted thread must throw InterruptedException");
        check(!Thread.interrupted(), "interrupt status must be cleared after InterruptedException");
    }

    static void testTakeReturnsHeadInFifoOrder() throws InterruptedException {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(3);
        check(q.offer("a"), "offer a");
        check(q.offer("b"), "offer b");
        check("a".equals(q.take()), "first take must return head 'a'");
        check("b".equals(q.take()), "second take must return 'b'");
        check(q.size() == 0, "queue must be empty after taking all elements");
    }

    static void testBlockedTakeIsInterrupted() throws InterruptedException {
        final ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(1);
        final AtomicReference<Throwable> error = new AtomicReference<>();
        Thread t = new Thread(() -> {
            try {
                q.take();
            } catch (Throwable x) {
                error.set(x);
            }
        });
        t.start();
        t.interrupt();
        t.join(10000);
        check(!t.isAlive(), "blocked take() thread must terminate after interrupt");
        check(error.get() instanceof InterruptedException,
              "blocked take() must throw InterruptedException, got " + error.get());
    }
}
