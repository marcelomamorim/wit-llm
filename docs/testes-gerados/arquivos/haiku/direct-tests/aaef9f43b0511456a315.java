/*
 * @test
 * @summary Verifies that LinkedBlockingQueue.clear() empties the queue and restores its capacity
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.Iterator;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testClearBoundedFullQueue();
        testClearEmptyQueue();
        testClearUnboundedQueue();
        testClearRestoresCapacityForOffer();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testClearBoundedFullQueue() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>(3);
        check(q.offer(1), "offer 1 should succeed");
        check(q.offer(2), "offer 2 should succeed");
        check(q.offer(3), "offer 3 should succeed");
        check(!q.offer(4), "offer 4 should fail on a full queue");

        q.clear();

        check(q.size() == 0, "size after clear should be 0");
        check(q.isEmpty(), "queue should be empty after clear");
        check(q.remainingCapacity() == 3, "remainingCapacity after clear should be 3");
        check(q.poll() == null, "poll after clear should return null");
        Iterator<Integer> it = q.iterator();
        check(!it.hasNext(), "iterator after clear should have no elements");
    }

    static void testClearEmptyQueue() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        q.clear();
        check(q.size() == 0, "size of cleared empty queue should be 0");
        q.clear();
        check(q.isEmpty(), "second clear on empty queue should keep it empty");
    }

    static void testClearUnboundedQueue() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        for (int i = 0; i < 10; i++) {
            check(q.add(i), "add should succeed on unbounded queue");
        }
        check(q.size() == 10, "size before clear should be 10");

        q.clear();

        check(q.size() == 0, "size after clear should be 0");
        check(q.remainingCapacity() == Integer.MAX_VALUE,
              "remainingCapacity of unbounded cleared queue should be Integer.MAX_VALUE");
    }

    static void testClearRestoresCapacityForOffer() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>(2);
        check(q.offer("x"), "offer x should succeed");
        check(q.offer("y"), "offer y should succeed");
        check(!q.offer("z"), "offer z should fail when full");

        q.clear();

        check(q.offer("a"), "offer a should succeed after clear");
        check(q.offer("b"), "offer b should succeed after clear");
        check(!q.offer("c"), "offer c should fail when capacity is reached again");
        check("a".equals(q.poll()), "first element after clear should be a");
        check("b".equals(q.poll()), "second element after clear should be b");
        check(q.poll() == null, "queue should be empty at the end");
    }
}
