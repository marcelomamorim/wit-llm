/*
 * @test
 * @summary Verifies observable behavior of PriorityQueue(Comparator) constructor: comparator ordering, comparator() accessor and natural ordering when comparator is null
 * @run main PriorityQueueWitupTest
 */

import java.util.Comparator;
import java.util.PriorityQueue;

public class PriorityQueueWitupTest {

    public static void main(String[] args) {
        testReverseComparatorOrdering();
        testComparatorInstanceIsReturned();
        testNullComparatorUsesNaturalOrdering();
        testEmptyQueueWithComparator();
        testCustomComparatorByLength();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testReverseComparatorOrdering() {
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>(Comparator.reverseOrder());
        pq.offer(3);
        pq.offer(1);
        pq.offer(2);
        check(pq.size() == 3, "size should be 3");
        check(pq.peek() == 3, "peek should return maximum under reverse order");
        check(pq.poll() == 3, "first poll should be 3");
        check(pq.poll() == 2, "second poll should be 2");
        check(pq.poll() == 1, "third poll should be 1");
        check(pq.poll() == null, "poll on empty queue should return null");
    }

    static void testComparatorInstanceIsReturned() {
        Comparator<String> cmp = Comparator.reverseOrder();
        PriorityQueue<String> pq = new PriorityQueue<String>(cmp);
        check(pq.comparator() == cmp, "comparator() should return the supplied comparator instance");
    }

    static void testNullComparatorUsesNaturalOrdering() {
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>((Comparator<Integer>) null);
        check(pq.comparator() == null, "comparator() should be null for natural ordering");
        pq.offer(5);
        pq.offer(4);
        pq.offer(6);
        check(pq.poll() == 4, "natural ordering should poll 4 first");
        check(pq.poll() == 5, "natural ordering should poll 5 second");
        check(pq.poll() == 6, "natural ordering should poll 6 last");
    }

    static void testEmptyQueueWithComparator() {
        PriorityQueue<String> pq = new PriorityQueue<String>(Comparator.reverseOrder());
        check(pq.isEmpty(), "new queue should be empty");
        check(pq.size() == 0, "new queue size should be 0");
        check(pq.peek() == null, "peek on empty queue should return null");
        check(pq.poll() == null, "poll on empty queue should return null");
    }

    static void testCustomComparatorByLength() {
        PriorityQueue<String> pq = new PriorityQueue<String>(Comparator.comparingInt(String::length));
        pq.offer("ccc");
        pq.offer("a");
        pq.offer("bb");
        check("a".equals(pq.poll()), "shortest string should be polled first");
        check("bb".equals(pq.poll()), "medium string should be polled second");
        check("ccc".equals(pq.poll()), "longest string should be polled last");
    }
}
