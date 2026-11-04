/*
 * @test
 * @summary Verifies observable behavior of PriorityBlockingQueue.clear()
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.Iterator;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testClearNonEmptyQueue();
        testClearEmptyQueue();
        testClearTwice();
        testClearThenReuseKeepsOrdering();
        testClearRemovesIteratorElements();
        System.out.println("PASSED");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testClearNonEmptyQueue() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(5);
        q.add(1);
        q.add(3);
        q.clear();
        check(q.size() == 0, "size after clear must be 0");
        check(q.isEmpty(), "queue must be empty after clear");
        check(q.peek() == null, "peek after clear must return null");
        check(q.poll() == null, "poll after clear must return null");
        check(q.toArray().length == 0, "toArray after clear must be empty");
    }

    static void testClearEmptyQueue() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>(4);
        q.clear();
        check(q.isEmpty(), "clear on empty queue must leave it empty");
        check(q.size() == 0, "size of empty queue after clear must be 0");
    }

    static void testClearTwice() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(2);
        q.clear();
        q.clear();
        check(q.isEmpty(), "second clear must keep queue empty");
        check(q.poll() == null, "poll after double clear must return null");
    }

    static void testClearThenReuseKeepsOrdering() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(9);
        q.add(4);
        q.add(7);
        q.clear();
        q.add(8);
        q.add(2);
        q.add(6);
        check(q.size() == 3, "size after reuse must be 3");
        check(q.poll() == 2, "first poll after reuse must be 2");
        check(q.poll() == 6, "second poll after reuse must be 6");
        check(q.poll() == 8, "third poll after reuse must be 8");
        check(q.poll() == null, "queue must be empty after draining");
    }

    static void testClearRemovesIteratorElements() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(10);
        q.add(20);
        q.clear();
        Iterator<Integer> it = q.iterator();
        check(!it.hasNext(), "iterator created after clear must have no elements");
    }
}
