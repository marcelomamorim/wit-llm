/*
 * @test
 * @summary Verifies observable behavior of PriorityBlockingQueue.peek()
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testEmptyQueuePeekReturnsNull();
        testPeekReturnsMinimumWithoutRemoving();
        testPeekWithReverseComparatorReturnsMaximum();
        testPeekAfterPollReturnsNewHead();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testEmptyQueuePeekReturnsNull() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        check(q.peek() == null, "peek() on empty queue must return null");
        check(q.size() == 0, "peek() must not change size of empty queue");
    }

    static void testPeekReturnsMinimumWithoutRemoving() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.offer(5);
        q.offer(1);
        q.offer(3);
        check(q.peek() == 1, "peek() must return the least element by natural ordering");
        check(q.size() == 3, "peek() must not remove the element");
        check(q.peek() == 1, "repeated peek() must return the same element");
    }

    static void testPeekWithReverseComparatorReturnsMaximum() {
        PriorityBlockingQueue<Integer> q =
                new PriorityBlockingQueue<>(11, Comparator.reverseOrder());
        q.offer(5);
        q.offer(1);
        q.offer(3);
        check(q.peek() == 5, "peek() must honor the supplied comparator");
        check(q.size() == 3, "peek() with comparator must not remove the element");
    }

    static void testPeekAfterPollReturnsNewHead() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.offer(2);
        q.offer(4);
        check(q.poll() == 2, "poll() must return the least element");
        check(q.peek() == 4, "peek() must return the new head after poll()");
        check(q.size() == 1, "size must reflect the poll() removal only");
    }
}
