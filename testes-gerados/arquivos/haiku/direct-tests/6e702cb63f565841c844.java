/*
 * @test
 * @summary Verifies that LinkedTransferQueue.offer(e, timeout, unit) always returns true and enqueues elements observably in FIFO order
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) throws Exception {
        LinkedTransferQueue<String> q = new LinkedTransferQueue<>();

        check(q.offer("a", 1, TimeUnit.SECONDS), "offer must return true");
        check(q.size() == 1, "size must be 1 after one offer, was " + q.size());
        check("a".equals(q.peek()), "peek must return the offered element");
        check("a".equals(q.poll()), "poll must return the offered element");
        check(q.isEmpty(), "queue must be empty after polling the only element");

        check(q.offer("x", 0, TimeUnit.MILLISECONDS), "offer with zero timeout must return true");
        check(q.offer("y", -5, TimeUnit.NANOSECONDS), "offer with negative timeout must return true");
        check(q.offer("z", Long.MAX_VALUE, TimeUnit.DAYS), "offer with large timeout must return true");
        check(q.size() == 3, "size must be 3 after three offers, was " + q.size());
        check("x".equals(q.poll()), "FIFO order violated: expected x first");
        check("y".equals(q.poll()), "FIFO order violated: expected y second");
        check("z".equals(q.poll()), "FIFO order violated: expected z third");
        check(q.poll() == null, "queue must be empty after all elements are polled");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
