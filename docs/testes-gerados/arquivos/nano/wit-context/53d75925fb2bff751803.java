/*
 * @test
 * @summary Test PriorityQueue constructor with comparator
 * @run main PriorityQueueWitupTest
 */

import java.util.PriorityQueue;
import java.util.Comparator;

public class PriorityQueueWitupTest {

    public static void main(String[] args) {
        testConstructorWithComparator();
        testConstructorWithInvalidInitialCapacity();
    }

    static void testConstructorWithComparator() {
        Comparator<Integer> comparator = Integer::compare;
        PriorityQueue<Integer> pq = new PriorityQueue<>(comparator);
        if (pq == null) {
            throw new AssertionError("PriorityQueue instance should not be null");
        }
        if (pq.size() != 0) {
            throw new AssertionError("New PriorityQueue should be empty");
        }
    }

    static void testConstructorWithInvalidInitialCapacity() {
        boolean exceptionThrown = false;
        try {
            PriorityQueue<Integer> pq = new PriorityQueue<>(0);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for initialCapacity < 1");
        }
    }
}
