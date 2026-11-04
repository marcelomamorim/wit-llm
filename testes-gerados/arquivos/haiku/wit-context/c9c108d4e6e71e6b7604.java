/*
 * @test
 * @summary Verify observable behavior of ArrayBlockingQueue.clear()
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testClearNonEmptyQueue();
        testClearEmptyQueue();
        testClearFullQueueRestoresCapacity();
        testClearAfterWrapAround();
        testClearTwiceIsSafe();
    }

    static void testClearNonEmptyQueue() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(3);
        check(q.offer(1), "offer 1 should succeed");
        check(q.offer(2), "offer 2 should succeed");
        q.clear();
        check(q.isEmpty(), "queue should be empty after clear");
        check(q.size() == 0, "size should be 0 after clear");
        check(q.poll() == null, "poll should return null after clear");
        check(q.peek() == null, "peek should return null after clear");
        check(q.remainingCapacity() == 3, "remaining capacity should be restored");
    }

    static void testClearEmptyQueue() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(2);
        q.clear();
        check(q.isEmpty(), "empty queue should remain empty after clear");
        check(q.size() == 0, "size should be 0 for empty queue after clear");
        check(q.remainingCapacity() == 2, "capacity should be unchanged for empty queue");
    }

    static void testClearFullQueueRestoresCapacity() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(2);
        check(q.offer(1), "offer 1 should succeed");
        check(q.offer(2), "offer 2 should succeed");
        check(!q.offer(3), "offer on full queue should fail");
        q.clear();
        check(q.offer(3), "offer after clear should succeed");
        check(q.offer(4), "second offer after clear should succeed");
        check(!q.offer(5), "offer beyond capacity after clear should fail");
        check(q.poll() == 3, "FIFO order should hold after clear");
        check(q.poll() == 4, "FIFO order should hold after clear");
        check(q.poll() == null, "queue should be empty again");
    }

    static void testClearAfterWrapAround() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(3);
        check(q.offer(1), "offer 1");
        check(q.offer(2), "offer 2");
        check(q.offer(3), "offer 3");
        check(q.poll() == 1, "poll should return 1");
        check(q.poll() == 2, "poll should return 2");
        check(q.offer(4), "offer 4 should wrap around");
        check(q.offer(5), "offer 5 should wrap around");
        q.clear();
        check(q.isEmpty(), "queue should be empty after clear following wrap");
        check(q.remainingCapacity() == 3, "capacity should be restored after wrap");
        check(q.offer(6), "offer 6 after clear should succeed");
        check(q.poll() == 6, "poll should return 6 after clear");
        check(q.poll() == null, "queue should be empty at end");
    }

    static void testClearTwiceIsSafe() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(2);
        check(q.offer(7), "offer 7");
        q.clear();
        q.clear();
        check(q.isEmpty(), "queue should be empty after repeated clear");
        check(q.remainingCapacity() == 2, "capacity should be 2 after repeated clear");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
