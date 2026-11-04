/*
 * @test
 * @summary Test drainTo(Collection<? super E> c, int maxElements) method of PriorityBlockingQueue
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.ArrayList;
import java.util.Collection;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testDrainToWithPositiveMax();
        testDrainToWithZeroMax();
        testDrainToWithNegativeMax();
        testDrainToWithEmptyQueue();
        testDrainToWithFullQueue();
        testDrainToWithNullCollection();
        testDrainToWithSelfCollection();
    }

    static void testDrainToWithPositiveMax() {
        PriorityBlockingQueue<Integer> pq = new PriorityBlockingQueue<>();
        pq.add(1);
        pq.add(2);
        pq.add(3);
        Collection<Integer> c = new ArrayList<>();
        int drained = pq.drainTo(c, 2);
        assert drained == 2 : "Expected drained=2, got " + drained;
        assert c.size() == 2 : "Collection size should be 2";
        assert pq.size() == 1 : "Remaining queue size should be 1";
    }

    static void testDrainToWithZeroMax() {
        PriorityBlockingQueue<Integer> pq = new PriorityBlockingQueue<>();
        pq.add(1);
        Collection<Integer> c = new ArrayList<>();
        int drained = pq.drainTo(c, 0);
        assert drained == 0 : "Expected drained=0, got " + drained;
        assert c.isEmpty() : "Collection should be empty";
        assert pq.size() == 1 : "Queue size should remain 1";
    }

    static void testDrainToWithNegativeMax() {
        PriorityBlockingQueue<Integer> pq = new PriorityBlockingQueue<>();
        pq.add(1);
        Collection<Integer> c = new ArrayList<>();
        int drained = pq.drainTo(c, -5);
        assert drained == 0 : "Expected drained=0 for negative max";
        assert c.isEmpty() : "Collection should be empty";
        assert pq.size() == 1 : "Queue size should remain 1";
    }

    static void testDrainToWithEmptyQueue() {
        PriorityBlockingQueue<Integer> pq = new PriorityBlockingQueue<>();
        Collection<Integer> c = new ArrayList<>();
        int drained = pq.drainTo(c, 10);
        assert drained == 0 : "Expected drained=0 for empty queue";
        assert c.isEmpty() : "Collection should be empty";
    }

    static void testDrainToWithFullQueue() {
        PriorityBlockingQueue<Integer> pq = new PriorityBlockingQueue<>();
        pq.add(1);
        pq.add(2);
        Collection<Integer> c = new ArrayList<>();
        int drained = pq.drainTo(c, 10);
        assert drained == 2 : "Expected drained=2";
        assert c.size() == 2 : "Collection size should be 2";
        assert pq.isEmpty() : "Queue should be empty after drain";
    }

    static void testDrainToWithNullCollection() {
        PriorityBlockingQueue<Integer> pq = new PriorityBlockingQueue<>();
        pq.add(1);
        try {
            pq.drainTo(null, 1);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testDrainToWithSelfCollection() {
        PriorityBlockingQueue<Integer> pq = new PriorityBlockingQueue<>();
        pq.add(1);
        try {
            pq.drainTo(pq, 1);
            assert false : "Expected IllegalArgumentException";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
