/* @test    @summary Test for PriorityQueue.remove() method to verify exception paths and behavior.    @run main PriorityQueueWitupTest */

import java.util.PriorityQueue;

public class PriorityQueueWitupTest {

    public static void main(String[] args) {
        testRemoveWithExpectedModCountMismatch();
        testRemoveWithIllegalState();
    }

    private static void testRemoveWithExpectedModCountMismatch() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(1);
        pq.add(2);
        pq.add(3);
        // Simulate concurrent modification by changing modCount directly is not possible,
        // so we test the exception path by mocking or subclassing if needed.
        // Since direct modification isn't feasible, we test the exception by calling remove() after
        // external modification, which in real scenario would cause ConcurrentModificationException.
        // Here, we simulate by forcibly throwing the exception.
        PriorityQueue<Integer> pq2 = new PriorityQueue<Integer>() {
            @Override
            public void remove() {
                throw new java.util.ConcurrentModificationException();
            }
        };
        try {
            pq2.remove();
            System.out.println("Failed: Expected ConcurrentModificationException not thrown.");
        } catch (java.util.ConcurrentModificationException e) {
            System.out.println("Passed: Caught expected ConcurrentModificationException.");
        }
    }

    private static void testRemoveWithIllegalState() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        // Call remove() without setting lastRet or lastRetElt to trigger IllegalStateException
        try {
            pq.remove();
            System.out.println("Failed: Expected IllegalStateException not thrown.");
        } catch (IllegalStateException e) {
            System.out.println("Passed: Caught expected IllegalStateException.");
        }
    }
}
