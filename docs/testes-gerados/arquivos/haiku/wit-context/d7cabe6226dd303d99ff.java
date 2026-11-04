/*
 * @test
 * @summary Checks observable behavior of PriorityBlockingQueue.contains(Object)
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testEmptyQueue();
        testPresentAndAbsentElements();
        testNullArgument();
        testAfterPoll();
        testComparatorConstructor();
    }

    static void testEmptyQueue() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        check(!q.contains(1), "empty queue must not contain 1");
        check(!q.contains(null), "empty queue must not contain null");
    }

    static void testPresentAndAbsentElements() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(3);
        q.add(1);
        q.add(2);
        check(q.contains(1), "queue must contain 1");
        check(q.contains(2), "queue must contain 2");
        check(q.contains(3), "queue must contain 3");
        check(!q.contains(5), "queue must not contain 5");
        check(!q.contains("x"), "queue must not contain a String");
    }

    static void testNullArgument() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(1);
        check(!q.contains(null), "contains(null) must return false");
        check(q.contains(1), "queue must still contain 1");
    }

    static void testAfterPoll() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(3);
        q.add(1);
        q.add(2);
        Integer head = q.poll();
        check(head != null && head == 1, "poll must return 1");
        check(!q.contains(1), "polled element must no longer be contained");
        check(q.contains(2), "queue must still contain 2");
    }

    static void testComparatorConstructor() {
        PriorityBlockingQueue<Integer> q =
            new PriorityBlockingQueue<>(4, Comparator.reverseOrder());
        q.add(10);
        q.add(20);
        check(q.contains(10), "queue must contain 10");
        check(q.contains(20), "queue must contain 20");
        check(!q.contains(30), "queue must not contain 30");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
