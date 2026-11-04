/* @test    @summary Test PriorityQueue.next() method for normal and exceptional behavior    @run main PriorityQueueWitupTest */

import java.util.PriorityQueue;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

public class PriorityQueueWitupTest {

    public static void main(String[] args) {
        testNextWithElements();
        testNextAfterModification();
        testNextWhenEmpty();
    }

    static void testNextWithElements() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.offer(10);
        pq.offer(20);
        pq.offer(15);
        try {
            Integer first = pq.next();
            assert first != null : "Expected non-null element";
            assert first.equals(10) : "Expected first element to be 10";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testNextWithElements", e);
        }
    }

    static void testNextAfterModification() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.offer(5);
        pq.offer(3);
        pq.offer(8);
        int expectedModCount = pq.modCount;
        // Modify the queue to cause modCount change
        pq.offer(1);
        try {
            pq.next();
            throw new AssertionError("Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testNextAfterModification", e);
        }
    }

    static void testNextWhenEmpty() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        try {
            pq.next();
            throw new AssertionError("Expected NoSuchElementException not thrown");
        } catch (NoSuchElementException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testNextWhenEmpty", e);
        }
    }
}
