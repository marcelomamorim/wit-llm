/*
 * @test
 * @summary Behavioral tests for PriorityBlockingQueue.peek()
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) throws Exception {
        testPeekEmptyReturnsNull();
        testPeekReturnsLeastElementWithoutRemoving();
        testPeekWithCustomComparator();
        testPeekAfterPoll();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testPeekEmptyReturnsNull() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        check(q.peek() == null, "peek() on empty queue must return null");
        check(q.isEmpty(), "queue must remain empty after peek()");
        check(q.size() == 0, "size must remain 0 after peek()");
    }

    static void testPeekReturnsLeastElementWithoutRemoving() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(5);
        q.add(1);
        q.add(3);
        check(q.peek() == 1, "peek() must return the least element");
        check(q.size() == 3, "peek() must not remove elements");
        check(q.peek() == 1, "repeated peek() must return the same head");
        check(q.size() == 3, "repeated peek() must not change size");
    }

    static void testPeekWithCustomComparator() {
        PriorityBlockingQueue<Integer> q =
            new PriorityBlockingQueue<>(11, (a, b) -> b - a);
        q.offer(2);
        q.offer(9);
        q.offer(4);
        check(q.peek() == 9, "peek() must honor the comparator (max first)");
        check(q.size() == 3, "peek() must not remove elements with comparator");
    }

    static void testPeekAfterPoll() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(7);
        q.add(2);
        check(q.poll() == 2, "poll() must return the least element");
        check(q.peek() == 7, "peek() must return the new head after poll()");
        q.poll();
        check(q.peek() == null, "peek() must return null after all elements are removed");
    }
}
