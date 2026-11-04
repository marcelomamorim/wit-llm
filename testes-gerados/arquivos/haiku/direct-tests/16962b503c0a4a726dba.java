/*
 * @test
 * @summary Verifies PriorityQueue.add(E) delegates to offer with observable ordering and null rejection
 * @run main PriorityQueueWitupTest
 */

import java.util.Comparator;
import java.util.PriorityQueue;

public class PriorityQueueWitupTest {

    public static void main(String[] args) {
        testAddReturnsTrue();
        testAddUpdatesHeadToLeastElement();
        testAddWithComparatorOrdersByComparator();
        testAddNullRejected();
        testAddDrainsInSortedOrder();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testAddReturnsTrue() {
        PriorityQueue<Integer> q = new PriorityQueue<>();
        check(q.add(5) == true, "add should return true for first element");
        check(q.add(3) == true, "add should return true for second element");
        check(q.size() == 2, "size should be 2 after two adds, was " + q.size());
    }

    static void testAddUpdatesHeadToLeastElement() {
        PriorityQueue<Integer> q = new PriorityQueue<>();
        q.add(10);
        check(q.peek() == 10, "head should be 10 after adding 10");
        q.add(4);
        check(q.peek() == 4, "head should be least element 4, was " + q.peek());
        q.add(7);
        check(q.peek() == 4, "head should remain 4, was " + q.peek());
    }

    static void testAddWithComparatorOrdersByComparator() {
        PriorityQueue<Integer> q = new PriorityQueue<>(Comparator.reverseOrder());
        q.add(1);
        q.add(9);
        q.add(5);
        check(q.peek() == 9, "with reverse comparator head should be 9, was " + q.peek());
    }

    static void testAddNullRejected() {
        PriorityQueue<Integer> q = new PriorityQueue<>();
        boolean thrown = false;
        try {
            q.add(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "add(null) should throw NullPointerException");
        check(q.isEmpty(), "queue should remain empty after rejected null add");
    }

    static void testAddDrainsInSortedOrder() {
        PriorityQueue<Integer> q = new PriorityQueue<>();
        int[] values = {8, 2, 6, 2, 9, 1};
        for (int v : values) {
            q.add(v);
        }
        int[] expected = {1, 2, 2, 6, 8, 9};
        for (int i = 0; i < expected.length; i++) {
            Integer polled = q.poll();
            check(polled != null && polled == expected[i],
                  "poll index " + i + " expected " + expected[i] + " but was " + polled);
        }
        check(q.isEmpty(), "queue should be empty after draining");
    }
}
