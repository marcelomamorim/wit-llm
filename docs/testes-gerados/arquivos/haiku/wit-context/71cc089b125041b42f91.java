/*
 * @test
 * @summary Witup-derived checks for PriorityBlockingQueue.add(E): null rejection, size/return contract, and priority ordering
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testAddNullThrowsNullPointerException();
        testAddNullDoesNotChangeQueue();
        testAddReturnsTrueAndIncrementsSize();
        testAddRetrievesInPriorityOrder();
        testAddWithComparatorOrder();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testAddNullThrowsNullPointerException() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        boolean thrown = false;
        try {
            q.add(null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        check(thrown, "add(null) must throw NullPointerException");
    }

    static void testAddNullDoesNotChangeQueue() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(7);
        try {
            q.add(null);
        } catch (NullPointerException expected) {
        }
        check(q.size() == 1, "size changed after rejected null add: " + q.size());
        check(q.peek() == 7, "head changed after rejected null add");
    }

    static void testAddReturnsTrueAndIncrementsSize() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        check(q.size() == 0, "new queue must be empty");
        check(q.add(10) == true, "add must return true");
        check(q.size() == 1, "size after first add: " + q.size());
        check(q.add(20) == true, "add must return true on second element");
        check(q.size() == 2, "size after second add: " + q.size());
    }

    static void testAddRetrievesInPriorityOrder() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(5);
        q.add(1);
        q.add(3);
        check(q.peek() == 1, "head must be smallest natural value, was " + q.peek());
        check(q.poll() == 1, "first poll must be 1");
        check(q.poll() == 3, "second poll must be 3");
        check(q.poll() == 5, "third poll must be 5");
        check(q.poll() == null, "queue must be empty after draining");
    }

    static void testAddWithComparatorOrder() {
        Comparator<Integer> reverse = (a, b) -> b.compareTo(a);
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>(11, reverse);
        q.add(1);
        q.add(3);
        q.add(2);
        check(q.poll() == 3, "reverse comparator head must be 3");
        check(q.poll() == 2, "reverse comparator second must be 2");
        check(q.poll() == 1, "reverse comparator third must be 1");
    }
}
