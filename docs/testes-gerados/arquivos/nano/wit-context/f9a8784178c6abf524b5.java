/*
 * @test
 * @summary Test for PriorityQueue.retainAll(Collection<?>) method.
 * @run main PriorityQueueWitupTest
 */

import java.util.PriorityQueue;
import java.util.Collection;

public class PriorityQueueWitupTest {

    public static void main(String[] args) {
        testRetainAllWithNullCollection();
        testRetainAllWithEmptyCollection();
        testRetainAllWithNonEmptyCollection();
    }

    private static void testRetainAllWithNullCollection() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(1);
        pq.add(2);
        boolean exceptionThrown = false;
        try {
            pq.retainAll(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null to retainAll");
        }
    }

    private static void testRetainAllWithEmptyCollection() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(1);
        pq.add(2);
        Collection<Integer> empty = java.util.Collections.emptyList();
        boolean changed = pq.retainAll(empty);
        if (changed != true) {
            throw new AssertionError("Expected retainAll to return true when removing all elements");
        }
        if (pq.size() != 0) {
            throw new AssertionError("Expected priority queue to be empty after retainAll with empty collection");
        }
    }

    private static void testRetainAllWithNonEmptyCollection() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(1);
        pq.add(2);
        pq.add(3);
        Collection<Integer> c = java.util.Arrays.asList(2, 3);
        boolean changed = pq.retainAll(c);
        if (changed != true) {
            throw new AssertionError("Expected retainAll to return true when removing some elements");
        }
        if (pq.size() != 2) {
            throw new AssertionError("Expected priority queue to contain 2 elements after retainAll");
        }
        if (!pq.contains(2) || !pq.contains(3)) {
            throw new AssertionError("Expected priority queue to contain 2 and 3 after retainAll");
        }
    }
}
