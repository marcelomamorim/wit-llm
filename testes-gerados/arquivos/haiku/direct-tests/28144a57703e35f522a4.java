/*
 * @test
 * @summary Verify PriorityBlockingQueue.poll() returns null on empty queues and removes elements in priority order
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testPollOnEmptyQueueReturnsNull();
        testPollReturnsNaturalOrder();
        testPollReturnsComparatorOrder();
        testPollRemovesHead();
        System.out.println("PASSED");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testPollOnEmptyQueueReturnsNull() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        check(q.poll() == null, "poll() on empty queue must return null");
        check(q.isEmpty(), "queue must remain empty after poll()");
        check(q.size() == 0, "size must stay 0 after poll() on empty queue");
    }

    static void testPollReturnsNaturalOrder() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(5);
        q.add(1);
        q.add(3);
        Integer first = q.poll();
        Integer second = q.poll();
        Integer third = q.poll();
        check(first != null && first == 1, "first poll must return 1 but was " + first);
        check(second != null && second == 3, "second poll must return 3 but was " + second);
        check(third != null && third == 5, "third poll must return 5 but was " + third);
        check(q.poll() == null, "poll() after draining must return null");
    }

    static void testPollReturnsComparatorOrder() {
        Comparator<Integer> reverse = Comparator.reverseOrder();
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<Integer>(11, reverse);
        q.add(2);
        q.add(7);
        q.add(4);
        Integer first = q.poll();
        Integer second = q.poll();
        Integer third = q.poll();
        check(first != null && first == 7, "reverse poll must return 7 but was " + first);
        check(second != null && second == 4, "reverse poll must return 4 but was " + second);
        check(third != null && third == 2, "reverse poll must return 2 but was " + third);
    }

    static void testPollRemovesHead() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(10);
        q.add(20);
        check(q.size() == 2, "size must be 2 before poll()");
        Integer head = q.poll();
        check(head != null && head == 10, "poll() must return head 10 but was " + head);
        check(q.size() == 1, "size must be 1 after poll()");
        check(q.peek() != null && q.peek() == 20, "remaining head must be 20");
    }
}
