/*
 * @test
 * @summary Test PriorityQueue.Itr.remove() for expected exceptions and behavior
 * @run main PriorityQueueWitupTest
 */

import java.util.PriorityQueue;
import java.util.ConcurrentModificationException;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.IllegalStateException;

public class PriorityQueueWitupTest {
    public static void main(String[] args) {
        testRemoveThrowsConcurrentModificationException();
        testRemoveThrowsIllegalStateException();
        testRemoveNormalBehavior();
    }

    // Test that remove() throws ConcurrentModificationException if modCount changed
    private static void testRemoveThrowsConcurrentModificationException() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(1);
        pq.add(2);
        Iterator<Integer> it = pq.iterator();
        if (!it.hasNext()) {
            throw new RuntimeException("Iterator should have elements");
        }
        it.next();
        // Modify the queue to change modCount
        pq.add(3);
        try {
            it.remove();
            throw new RuntimeException("Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException expected) {
            // Expected
        }
    }

    // Test that remove() throws IllegalStateException if called before next() or after remove()
    private static void testRemoveThrowsIllegalStateException() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(1);
        Iterator<Integer> it = pq.iterator();
        try {
            it.remove(); // remove before next()
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException expected) {
            // Expected
        }
        it.next();
        it.remove(); // valid remove
        try {
            it.remove(); // remove again without next()
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException expected) {
            // Expected
        }
    }

    // Test normal remove behavior does not throw and updates queue correctly
    private static void testRemoveNormalBehavior() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(10);
        pq.add(20);
        pq.add(30);
        Iterator<Integer> it = pq.iterator();
        int count = 0;
        while (it.hasNext()) {
            Integer val = it.next();
            if (val == 20) {
                it.remove();
            }
            count++;
        }
        if (count != 3) {
            throw new RuntimeException("Iterator should have iterated over 3 elements");
        }
        if (pq.contains(20)) {
            throw new RuntimeException("Element 20 should have been removed");
        }
        if (pq.size() != 2) {
            throw new RuntimeException("PriorityQueue size should be 2 after removal");
        }
    }
}
