/*
 * @test
 * @summary Test PriorityBlockingQueue.Itr.remove() behavior including exception paths
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.Iterator;

public class PriorityBlockingQueueWitupTest {
    public static void main(String[] args) {
        testRemoveWithoutNext();
        testRemoveAfterNext();
    }

    private static void testRemoveWithoutNext() {
        PriorityBlockingQueue<Integer> pq = new PriorityBlockingQueue<>();
        pq.add(1);
        Iterator<Integer> it = pq.iterator();
        try {
            // Directly call remove() without next() should throw IllegalStateException
            it.remove();
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    private static void testRemoveAfterNext() {
        PriorityBlockingQueue<Integer> pq = new PriorityBlockingQueue<>();
        pq.add(1);
        pq.add(2);
        Iterator<Integer> it = pq.iterator();
        if (it.hasNext()) {
            Integer val = it.next();
            it.remove(); // should remove the element returned by next()
            // After remove, calling remove again without next should throw IllegalStateException
            try {
                it.remove();
                throw new RuntimeException("Expected IllegalStateException not thrown after second remove");
            } catch (IllegalStateException expected) {
                // expected
            }
        } else {
            throw new RuntimeException("Iterator has no elements, test invalid");
        }
        // Verify that the removed element is no longer in the queue
        if (pq.contains(1) && pq.contains(2)) {
            throw new RuntimeException("Element was not removed from PriorityBlockingQueue");
        }
    }
}
