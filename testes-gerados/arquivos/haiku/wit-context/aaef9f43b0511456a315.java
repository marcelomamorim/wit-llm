/*
 * @test
 * @summary Witup-generated behavioral tests for LinkedBlockingQueue.clear()
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testClearOnEmptyQueue();
        testClearRemovesAllElements();
        testClearRestoresRemainingCapacity();
        testQueueUsableAfterClear();
        testClearIsIdempotent();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testClearOnEmptyQueue() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>();
        q.clear();
        check(q.isEmpty(), "queue should be empty after clear on empty queue");
        check(q.size() == 0, "size should be 0 after clear on empty queue");
        check(q.poll() == null, "poll should return null after clear");
    }

    static void testClearRemovesAllElements() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        for (int i = 0; i < 10; i++) {
            check(q.add(i), "add should succeed on unbounded queue");
        }
        check(q.size() == 10, "size should be 10 before clear");
        q.clear();
        check(q.isEmpty(), "queue should be empty after clear");
        check(q.size() == 0, "size should be 0 after clear");
        check(q.peek() == null, "peek should return null after clear");
        check(q.poll() == null, "poll should return null after clear");
    }

    static void testClearRestoresRemainingCapacity() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>(3);
        check(q.offer(1), "offer 1 should succeed");
        check(q.offer(2), "offer 2 should succeed");
        check(q.offer(3), "offer 3 should succeed");
        check(!q.offer(4), "offer should fail when bounded queue is full");
        check(q.remainingCapacity() == 0, "remaining capacity should be 0 when full");
        q.clear();
        check(q.remainingCapacity() == 3, "remaining capacity should be restored to 3 after clear");
        check(q.offer(4), "offer should succeed after clear");
    }

    static void testQueueUsableAfterClear() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>();
        q.add("a");
        q.add("b");
        q.clear();
        q.add("c");
        q.add("d");
        check(q.size() == 2, "size should be 2 after adding post-clear");
        check("c".equals(q.poll()), "first element after clear should be c");
        check("d".equals(q.poll()), "second element after clear should be d");
        check(q.poll() == null, "queue should be empty at the end");
    }

    static void testClearIsIdempotent() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>(2);
        q.offer(1);
        q.clear();
        q.clear();
        check(q.isEmpty(), "queue should remain empty after repeated clear");
        check(q.remainingCapacity() == 2, "capacity should remain 2 after repeated clear");
    }
}
