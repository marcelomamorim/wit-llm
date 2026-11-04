/*
 * @test
 * @summary Verifies observable behavior of PriorityBlockingQueue.clear() using only public API
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.Iterator;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    private static void testClearNonEmptyQueue() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(1);
        q.add(2);
        q.add(3);
        q.clear();
        check(q.size() == 0, "size after clear must be 0");
        check(q.isEmpty(), "queue must be empty after clear");
        check(q.peek() == null, "peek after clear must return null");
        check(q.poll() == null, "poll after clear must return null");
        Iterator<Integer> it = q.iterator();
        check(!it.hasNext(), "iterator after clear must have no elements");
    }

    private static void testClearEmptyQueue() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.clear();
        check(q.size() == 0, "size of empty queue after clear must be 0");
        check(q.isEmpty(), "empty queue must remain empty after clear");
    }

    private static void testClearTwice() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(10);
        q.clear();
        q.clear();
        check(q.isEmpty(), "queue must be empty after repeated clear");
        check(q.size() == 0, "size must be 0 after repeated clear");
    }

    private static void testReuseAfterClearPreservesOrdering() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(7);
        q.add(9);
        q.clear();
        q.offer(5);
        q.offer(4);
        q.offer(6);
        check(q.size() == 3, "size after reuse must be 3");
        check(q.poll() == 4, "first poll after reuse must return smallest element 4");
        check(q.poll() == 5, "second poll after reuse must return 5");
        check(q.poll() == 6, "third poll after reuse must return 6");
        check(q.poll() == null, "queue must be empty after draining");
    }

    public static void main(String[] args) {
        testClearNonEmptyQueue();
        testClearEmptyQueue();
        testClearTwice();
        testReuseAfterClearPreservesOrdering();
    }
}
