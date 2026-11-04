/*
 * @test
 * @summary Behavioral tests for ArrayBlockingQueue.put: null rejection, FIFO ordering with bounded capacity, blocking when full, and interruption before entry.
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        testNullRejected();
        testFifoAndCapacity();
        testBlocksWhenFullThenProceeds();
        testInterruptedBeforePut();
    }

    static void testNullRejected() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(2);
        try {
            q.put(null);
            throw new RuntimeException("put(null) should throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        } catch (InterruptedException e) {
            throw new RuntimeException("unexpected InterruptedException", e);
        }
        check(q.isEmpty(), "queue must remain empty after rejected null");
    }

    static void testFifoAndCapacity() throws InterruptedException {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(3);
        q.put("a");
        q.put("b");
        q.put("c");
        check(q.size() == 3, "size should be 3 after three puts");
        check(q.remainingCapacity() == 0, "remaining capacity should be 0 when full");
        check("a".equals(q.poll()), "FIFO order violated: expected a");
        check("b".equals(q.poll()), "FIFO order violated: expected b");
        check("c".equals(q.poll()), "FIFO order violated: expected c");
        check(q.poll() == null, "queue should be empty");
    }

    static void testBlocksWhenFullThenProceeds() throws Exception {
        final ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(1);
        q.put("a");
        final Throwable[] error = new Throwable[1];
        Thread t = new Thread(() -> {
            try {
                q.put("b");
            } catch (InterruptedException e) {
                error[0] = e;
            }
        });
        t.start();
        long deadline = System.nanoTime() + 10_000_000_000L;
        while (t.getState() != Thread.State.WAITING && System.nanoTime() < deadline) {
            Thread.sleep(10);
        }
        check(t.getState() == Thread.State.WAITING, "producer should block on full queue");
        check(q.size() == 1, "blocked put must not insert element");
        check("a".equals(q.take()), "take should return first element a");
        t.join(10_000);
        check(!t.isAlive(), "producer should finish after space is available");
        check(error[0] == null, "producer should not be interrupted");
        check("b".equals(q.take()), "second element b should be enqueued");
        check(q.isEmpty(), "queue should be empty at end");
    }

    static void testInterruptedBeforePut() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(1);
        Thread.currentThread().interrupt();
        try {
            q.put(1);
            throw new RuntimeException("put should throw InterruptedException when interrupted");
        } catch (InterruptedException expected) {
            // expected
        } finally {
            Thread.interrupted();
        }
        check(q.isEmpty(), "interrupted put must not insert element");
    }
}
