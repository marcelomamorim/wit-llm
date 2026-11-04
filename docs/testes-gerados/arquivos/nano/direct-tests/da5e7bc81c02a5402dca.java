/* @test    @summary Test PriorityQueue.remove() method for normal and exceptional behavior    @run main PriorityQueueRemoveMethodTest */

import java.util.PriorityQueue;
import java.util.ConcurrentModificationException;

public class PriorityQueueRemoveMethodTest {

    public static void main(String[] args) {
        testRemoveWithElements();
        testRemoveWithoutLastRet();
        testRemoveWithModifiedQueue();
    }

    static void testRemoveWithElements() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(1);
        pq.add(2);
        pq.add(3);
        try {
            pq.remove();
            assert pq.size() == 2 : "Size should be 2 after remove";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testRemoveWithElements", e);
        }
    }

    static void testRemoveWithoutLastRet() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        try {
            pq.remove();
            throw new AssertionError("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testRemoveWithoutLastRet", e);
        }
    }

    static void testRemoveWithModifiedQueue() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(1);
        pq.add(2);
        pq.add(3);
        // simulate modification
        pq.add(4);
        try {
            pq.remove();
            // No exception expected here, as modCount is internal, but we can test for consistency
            assert pq.size() >= 1 : "Size should be at least 1 after removal";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testRemoveWithModifiedQueue", e);
        }
    }
}
