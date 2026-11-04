/*
 * @test
 * @summary Observable behavior of PriorityQueue(Comparator) constructor
 * @run main PriorityQueueWitupTest
 */

import java.util.Comparator;
import java.util.PriorityQueue;

public class PriorityQueueWitupTest {

    public static void main(String[] args) {
        testNullComparatorUsesNaturalOrdering();
        testReverseComparatorOrdersHeadAsGreatest();
        testCustomComparatorOrdering();
        testComparatorAccessorReturnsSameInstance();
        testNullElementRejected();
        testEmptyQueuePeekReturnsNull();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullComparatorUsesNaturalOrdering() {
        PriorityQueue<Integer> pq = new PriorityQueue<>((Comparator<? super Integer>) null);
        check(pq.comparator() == null, "comparator() should be null for natural ordering");
        pq.offer(3);
        pq.offer(1);
        pq.offer(2);
        check(pq.size() == 3, "size should be 3");
        check(pq.poll() == 1, "first poll should be 1");
        check(pq.poll() == 2, "second poll should be 2");
        check(pq.poll() == 3, "third poll should be 3");
        check(pq.poll() == null, "poll on empty queue should return null");
    }

    static void testReverseComparatorOrdersHeadAsGreatest() {
        Comparator<Integer> reverse = Comparator.reverseOrder();
        PriorityQueue<Integer> pq = new PriorityQueue<>(reverse);
        pq.offer(1);
        pq.offer(3);
        pq.offer(2);
        check(pq.poll() == 3, "reverse comparator: first poll should be 3");
        check(pq.poll() == 2, "reverse comparator: second poll should be 2");
        check(pq.poll() == 1, "reverse comparator: third poll should be 1");
    }

    static void testCustomComparatorOrdering() {
        Comparator<Integer> byLastDigit = Comparator.comparingInt(x -> x % 10);
        PriorityQueue<Integer> pq = new PriorityQueue<>(byLastDigit);
        pq.offer(13);
        pq.offer(21);
        pq.offer(32);
        check(pq.poll() == 21, "last-digit comparator: first poll should be 21");
        check(pq.poll() == 32, "last-digit comparator: second poll should be 32");
        check(pq.poll() == 13, "last-digit comparator: third poll should be 13");
    }

    static void testComparatorAccessorReturnsSameInstance() {
        Comparator<Integer> cmp = Comparator.reverseOrder();
        PriorityQueue<Integer> pq = new PriorityQueue<>(cmp);
        check(pq.comparator() == cmp, "comparator() should return the supplied instance");
    }

    static void testNullElementRejected() {
        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder());
        boolean thrown = false;
        try {
            pq.offer(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "offer(null) should throw NullPointerException");
        check(pq.isEmpty(), "queue should remain empty after rejected null");
    }

    static void testEmptyQueuePeekReturnsNull() {
        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder());
        check(pq.peek() == null, "peek on empty queue should return null");
        check(pq.size() == 0, "size of empty queue should be 0");
    }
}
