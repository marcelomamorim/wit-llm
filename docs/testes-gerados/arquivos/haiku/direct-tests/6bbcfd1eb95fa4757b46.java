/*
 * @test
 * @summary Witup-derived tests for ArrayBlockingQueue.take()
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicReference;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) throws Exception {
        testFifoOrder();
        testInterruptedBeforeTake();
        testBlockedTakeInterrupted();
        testBlockedTakeReceivesPut();
        System.out.println("PASS");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testFifoOrder() throws Exception {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(3);
        q.put(Integer.valueOf(1));
        q.put(Integer.valueOf(2));
        check(q.take().equals(Integer.valueOf(1)), "first take should return head 1");
        check(q.take().equals(Integer.valueOf(2)), "second take should return 2");
        check(q.isEmpty(), "queue should be empty after two takes");
    }

    static void testInterruptedBeforeTake() throws Exception {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(1);
        Thread.currentThread().interrupt();
        boolean thrown = false;
        try {
            q.take();
        } catch (InterruptedException e) {
            thrown = true;
        }
        check(thrown, "take with interrupt status set should throw InterruptedException");
        check(q.offer(Integer.valueOf(5)), "offer should succeed after interrupted take");
        check(q.take().equals(Integer.valueOf(5)), "take should return 5 after interrupt cleared");
    }

    static void testBlockedTakeInterrupted() throws Exception {
        final ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(1);
        final AtomicReference<Throwable> failure = new AtomicReference<>();
        Thread t = new Thread(() -> {
            try {
                q.take();
            } catch (Throwable e) {
                failure.set(e);
            }
        });
        t.start();
        t.interrupt();
        t.join(10000);
        check(!t.isAlive(), "blocked take thread should terminate after interrupt");
        check(failure.get() instanceof InterruptedException,
              "blocked take should throw InterruptedException, got " + failure.get());
    }

    static void testBlockedTakeReceivesPut() throws Exception {
        final ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(1);
        final AtomicReference<Integer> result = new AtomicReference<>();
        Thread t = new Thread(() -> {
            try {
                result.set(q.take());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        t.start();
        q.put(Integer.valueOf(42));
        t.join(10000);
        check(!t.isAlive(), "blocked take thread should finish after put");
        check(Integer.valueOf(42).equals(result.get()), "take should return element put by other thread");
        check(q.isEmpty(), "queue should be empty after take");
    }
}
