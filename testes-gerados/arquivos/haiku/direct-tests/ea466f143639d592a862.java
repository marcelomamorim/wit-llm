/*
 * @test
 * @summary Black-box checks of PriorityQueue.offer: null rejection, return value, size, head ordering, growth and comparator ordering
 * @run main PriorityQueueWitupTest
 */

import java.util.Comparator;
import java.util.PriorityQueue;

public class PriorityQueueWitupTest {

    public static void main(String[] args) {
        testNullRejected();
        testOfferReturnsTrueAndIncrementsSize();
        testHeadIsLeastNatural();
        testGrowthPreservesOrder();
        testReverseComparatorHead();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullRejected() {
        PriorityQueue<String> pq = new PriorityQueue<>();
        boolean threw = false;
        try {
            pq.offer(null);
        } catch (NullPointerException e) {
            threw = true;
        }
        check(threw, "offer(null) must throw NullPointerException");
        check(pq.size() == 0, "size must be unchanged after rejected null");
    }

    static void testOfferReturnsTrueAndIncrementsSize() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        check(pq.offer(5) == true, "offer must return true");
        check(pq.size() == 1, "size must be 1 after first offer");
        check(pq.offer(3) == true, "offer must return true for second element");
        check(pq.size() == 2, "size must be 2 after second offer");
    }

    static void testHeadIsLeastNatural() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.offer(7);
        pq.offer(2);
        pq.offer(9);
        pq.offer(2);
        check(pq.peek() == 2, "head must be least element, got " + pq.peek());
    }

    static void testGrowthPreservesOrder() {
        PriorityQueue<Integer> pq = new PriorityQueue<>(1);
        for (int i = 50; i >= 1; i--) {
            pq.offer(i);
        }
        check(pq.size() == 50, "size must be 50 after growth, got " + pq.size());
        for (int expected = 1; expected <= 50; expected++) {
            Integer v = pq.poll();
            check(v != null && v == expected, "poll order broken at " + expected + ", got " + v);
        }
        check(pq.isEmpty(), "queue must be empty after polling all elements");
    }

    static void testReverseComparatorHead() {
        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder());
        pq.offer(3);
        pq.offer(1);
        pq.offer(2);
        check(pq.peek() == 3, "with reverse comparator head must be greatest, got " + pq.peek());
        check(pq.size() == 3, "size must be 3");
    }
}
