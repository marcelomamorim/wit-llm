/*
 * @test    @summary Test PriorityBlockingQueue.remove(Object o) method for correctness
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testRemoveExistingElement();
        testRemoveNonExistingElement();
        testRemoveNullElement();
        testRemoveFromEmptyQueue();
    }

    static void testRemoveExistingElement() {
        PriorityBlockingQueue<Integer> pq = new PriorityBlockingQueue<>(Arrays.asList(5, 3, 7));
        boolean removed = pq.remove(3);
        assert removed : "Element 3 should be removed";
        assert !pq.contains(3) : "Queue should not contain 3 after removal";
        assert pq.size() == 2 : "Queue size should be 2 after removal";
    }

    static void testRemoveNonExistingElement() {
        PriorityBlockingQueue<Integer> pq = new PriorityBlockingQueue<>(Arrays.asList(1, 2, 4));
        boolean removed = pq.remove(10);
        assert !removed : "Removing non-existing element should return false";
        assert pq.size() == 3 : "Queue size should remain unchanged";
    }

    static void testRemoveNullElement() {
        PriorityBlockingQueue<Integer> pq = new PriorityBlockingQueue<>(Arrays.asList(1, 2, 3));
        boolean removed = false;
        try {
            removed = pq.remove(null);
            assert false : "Removing null should throw NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
        assert pq.size() == 3 : "Queue size should remain unchanged after null removal attempt";
    }

    static void testRemoveFromEmptyQueue() {
        PriorityBlockingQueue<Integer> pq = new PriorityBlockingQueue<>();
        boolean removed = pq.remove(1);
        assert !removed : "Removing from empty queue should return false";
        assert pq.isEmpty() : "Queue should remain empty";
    }
}
