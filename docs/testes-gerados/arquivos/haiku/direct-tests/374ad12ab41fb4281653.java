/*
 * @test
 * @summary Black-box tests for PriorityQueue(int initialCapacity) using only public API
 * @run main PriorityQueueWitupTest
 */

import java.util.PriorityQueue;

public class PriorityQueueWitupTest {

    public static void main(String[] args) {
        testInitialCapacityOneStartsEmpty();
        testGrowsBeyondInitialCapacity();
        testNaturalOrderingPoll();
        testZeroCapacityRejected();
        testNegativeCapacityRejected();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testInitialCapacityOneStartsEmpty() {
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>(1);
        check(pq.isEmpty(), "new queue with capacity 1 should be empty");
        check(pq.size() == 0, "size should be 0");
        check(pq.peek() == null, "peek on empty queue should return null");
        check(pq.poll() == null, "poll on empty queue should return null");
    }

    static void testGrowsBeyondInitialCapacity() {
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>(1);
        int n = 1000;
        for (int i = n; i > 0; i--) {
            check(pq.offer(i), "offer should return true");
        }
        check(pq.size() == n, "size should be " + n + " after inserts");
        for (int expected = 1; expected <= n; expected++) {
            Integer head = pq.poll();
            check(head != null && head.intValue() == expected,
                  "expected " + expected + " but got " + head);
        }
        check(pq.isEmpty(), "queue should be empty after draining");
    }

    static void testNaturalOrderingPoll() {
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>(4);
        pq.add(5);
        pq.add(3);
        pq.add(8);
        pq.add(1);
        check(pq.peek().intValue() == 1, "peek should return least element");
        check(pq.poll().intValue() == 1, "first poll should be 1");
        check(pq.poll().intValue() == 3, "second poll should be 3");
        check(pq.poll().intValue() == 5, "third poll should be 5");
        check(pq.poll().intValue() == 8, "fourth poll should be 8");
        check(pq.poll() == null, "queue should now be empty");
    }

    static void testZeroCapacityRejected() {
        boolean thrown = false;
        try {
            new PriorityQueue<Integer>(0);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "capacity 0 should throw IllegalArgumentException");
    }

    static void testNegativeCapacityRejected() {
        boolean thrown = false;
        try {
            new PriorityQueue<Integer>(-1);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "negative capacity should throw IllegalArgumentException");
    }
}
