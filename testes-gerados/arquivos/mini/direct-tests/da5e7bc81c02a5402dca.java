/*
 * @test
 * @summary Test PriorityQueue remove() method behavior and exceptions
 * @run main PriorityQueueWitupTest
 */

import java.util.PriorityQueue;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ArrayDeque;
import java.util.IllegalStateException;

public class PriorityQueueWitupTest {
    public static void main(String[] args) {
        testRemoveWithoutNextThrows();
        testRemoveAfterNextRemovesElement();
        testRemoveAfterNextWithConcurrentModificationThrows();
        testRemoveAfterNextWithForgetMeNotBehavior();
    }

    // Test that calling remove() before next() throws IllegalStateException
    private static void testRemoveWithoutNextThrows() {
        PriorityQueue<String> pq = new PriorityQueue<>();
        pq.add("a");
        Iterator<String> it = pq.iterator();
        try {
            it.remove();
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException ise) {
            // expected
        }
    }

    // Test that remove() after next() removes the element and iterator behaves correctly
    private static void testRemoveAfterNextRemovesElement() {
        PriorityQueue<String> pq = new PriorityQueue<>();
        pq.add("a");
        pq.add("b");
        Iterator<String> it = pq.iterator();
        String first = it.next();
        it.remove();
        if (pq.contains(first)) {
            throw new RuntimeException("Element was not removed from PriorityQueue");
        }
        // After removal, next() should return the other element
        String second = it.next();
        if (second.equals(first)) {
            throw new RuntimeException("Iterator returned removed element");
        }
    }

    // Test that remove() throws ConcurrentModificationException if modCount changed
    private static void testRemoveAfterNextWithConcurrentModificationThrows() {
        PriorityQueue<String> pq = new PriorityQueue<>();
        pq.add("a");
        pq.add("b");
        Iterator<String> it = pq.iterator();
        it.next();
        // Modify the queue outside iterator
        pq.add("c");
        try {
            it.remove();
            throw new RuntimeException("Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException cme) {
            // expected
        }
    }

    // Test remove() behavior when forgetMeNot queue is used (simulate by removing at lastRet)
    private static void testRemoveAfterNextWithForgetMeNotBehavior() {
        PriorityQueue<String> pq = new PriorityQueue<>();
        pq.add("a");
        pq.add("b");
        Iterator<String> it = pq.iterator();
        String first = it.next();
        // Remove element directly from queue to simulate removeAt returning non-null
        // We cannot access removeAt directly, so simulate by removing element and then calling remove()
        pq.remove(first);
        try {
            it.remove();
        } catch (IllegalStateException ise) {
            throw new RuntimeException("Unexpected IllegalStateException", ise);
        } catch (ConcurrentModificationException cme) {
            throw new RuntimeException("Unexpected ConcurrentModificationException", cme);
        }
        // The queue should not contain the removed element
        if (pq.contains(first)) {
            throw new RuntimeException("Element was not removed from PriorityQueue");
        }
    }
}
