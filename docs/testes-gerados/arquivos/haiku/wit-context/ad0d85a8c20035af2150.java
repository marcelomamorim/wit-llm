/*
 * @test
 * @summary Witup-generated tests for PriorityBlockingQueue.removeIf
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) throws Exception {
        testNullFilterThrowsNPE();
        testRemoveMatchingElements();
        testNoMatchReturnsFalse();
        testEmptyQueueReturnsFalse();
        testRemoveAllElements();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testNullFilterThrowsNPE() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(1);
        boolean thrown = false;
        try {
            q.removeIf(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "removeIf(null) must throw NullPointerException");
        check(q.size() == 1, "queue must be unchanged after NPE");
    }

    static void testRemoveMatchingElements() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(4);
        q.add(1);
        q.add(6);
        q.add(3);
        q.add(2);
        q.add(5);
        boolean changed = q.removeIf(x -> x % 2 == 0);
        check(changed, "removeIf must return true when elements are removed");
        check(q.size() == 3, "size after removing evens must be 3, was " + q.size());
        check(q.poll() == 1, "first poll must be 1");
        check(q.poll() == 3, "second poll must be 3");
        check(q.poll() == 5, "third poll must be 5");
        check(q.poll() == null, "queue must be empty");
    }

    static void testNoMatchReturnsFalse() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(1);
        q.add(3);
        boolean changed = q.removeIf(x -> x > 10);
        check(!changed, "removeIf must return false when nothing matches");
        check(q.size() == 2, "size must remain 2");
        check(q.contains(1) && q.contains(3), "elements must remain present");
    }

    static void testEmptyQueueReturnsFalse() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        boolean changed = q.removeIf(x -> true);
        check(!changed, "removeIf on empty queue must return false");
        check(q.isEmpty(), "queue must remain empty");
    }

    static void testRemoveAllElements() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(2);
        q.add(1);
        boolean changed = q.removeIf(x -> true);
        check(changed, "removeIf must return true when all elements are removed");
        check(q.isEmpty(), "queue must be empty");
        check(q.poll() == null, "poll on empty queue must return null");
    }
}
