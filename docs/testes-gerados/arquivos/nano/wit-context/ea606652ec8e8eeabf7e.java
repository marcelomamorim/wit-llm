/*
 * @test
 * @summary Tests for PriorityBlockingQueue.drainTo(Collection<? super E> c, int maxElements)
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.Collection;
import java.util.ArrayList;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testDrainToWithNullCollection();
        testDrainToWithEmptyQueue();
        testDrainToWithLimitedCapacity();
        testDrainToWithFullQueue();
        testDrainToWithNegativeMaxElements();
    }

    static void testDrainToWithNullCollection() {
        PriorityBlockingQueue<Integer> pq = new PriorityBlockingQueue<>();
        try {
            pq.drainTo(null, 10);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Test passes
        }
    }

    static void testDrainToWithEmptyQueue() {
        PriorityBlockingQueue<Integer> pq = new PriorityBlockingQueue<>();
        Collection<Integer> c = new ArrayList<>();
        int drained = pq.drainTo(c, 10);
        assert drained == 0 : "Drain from empty queue should return 0";
        assert c.isEmpty() : "Collection should remain empty";
    }

    static void testDrainToWithLimitedCapacity() {
        PriorityBlockingQueue<Integer> pq = new PriorityBlockingQueue<>();
        pq.add(1);
        pq.add(2);
        pq.add(3);
        Collection<Integer> c = new ArrayList<>();
        int drained = pq.drainTo(c, 2);
        assert drained == 2 : "Should drain 2 elements";
        assert c.size() == 2 : "Collection should contain 2 elements";
        assert pq.size() == 1 : "One element should remain in queue";
    }

    static void testDrainToWithFullQueue() {
        PriorityBlockingQueue<Integer> pq = new PriorityBlockingQueue<>();
        pq.add(5);
        pq.add(10);
        Collection<Integer> c = new ArrayList<>();
        int drained = pq.drainTo(c, 10);
        assert drained == 2 : "Should drain all 2 elements";
        assert c.contains(5) && c.contains(10) : "Collection should contain drained elements";
        assert pq.isEmpty() : "Queue should be empty after drain";
    }

    static void testDrainToWithNegativeMaxElements() {
        PriorityBlockingQueue<Integer> pq = new PriorityBlockingQueue<>();
        pq.add(1);
        Collection<Integer> c = new ArrayList<>();
        int drained = pq.drainTo(c, -1);
        assert drained == 0 : "Negative maxElements should result in 0";
        assert c.isEmpty() : "Collection should remain empty";
        assert pq.size() == 1 : "Queue should remain unchanged";
    }
}
