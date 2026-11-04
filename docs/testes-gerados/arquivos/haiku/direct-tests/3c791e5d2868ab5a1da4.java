/*
 * @test
 * @summary Verify LinkedBlockingQueue.take() FIFO order, blocking behavior, interruption and bounded-queue signalling
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicReference;

public class LinkedBlockingQueueWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        testFifoOrder();
        testBlockingTakeWakesOnPut();
        testInterruptedTakeThrows();
        testBoundedQueueTakeFreesCapacity();
    }

    static void testFifoOrder() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        q.add(1);
        q.add(2);
        q.add(3);
        check(q.size() == 3, "size before take");
        check(q.take() == 1, "first take must return head");
        check(q.take() == 2, "second take must return next head");
        check(q.take() == 3, "third take must return last element");
        check(q.isEmpty(), "queue must be empty after draining");
    }

    static void testBlockingTakeWakesOnPut() throws Exception {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        AtomicReference<Integer> result = new AtomicReference<>();
        AtomicReference<Throwable> failure = new AtomicReference<>();
        Thread t = new Thread(() -> {
            try {
                result.set(q.take());
            } catch (Throwable e) {
                failure.set(e);
            }
        });
        t.start();
        q.put(42);
        t.join(10000);
        check(!t.isAlive(), "take() thread must finish after put");
        check(failure.get() == null, "take() must not fail: " + failure.get());
        check(result.get() != null && result.get() == 42, "take() must return the put element");
    }

    static void testInterruptedTakeThrows() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        Thread.currentThread().interrupt();
        boolean thrown = false;
        try {
            q.take();
        } catch (InterruptedException e) {
            thrown = true;
        }
        check(thrown, "take() on interrupted thread must throw InterruptedException");
        check(q.isEmpty(), "queue must remain empty after interrupted take");
    }

    static void testBoundedQueueTakeFreesCapacity() throws Exception {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>(1);
        q.put(1);
        check(!q.offer(2), "bounded queue at capacity must reject offer");
        check(q.take() == 1, "take() must return the single element");
        check(q.offer(2), "take() must free capacity for a new offer");
        check(q.take() == 2, "take() must return the newly offered element");
    }
}
