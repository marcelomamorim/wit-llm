/*
 * @test
 * @summary Test PriorityBlockingQueue.drainTo(Collection) method
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.Collection;
import java.util.ArrayList;

public class PriorityBlockingQueueWitupTest {
    public static void main(String[] args) throws Exception {
        testDrainToEmptyQueue();
        testDrainToNonEmptyQueue();
        testDrainToNullCollection();
    }

    static void testDrainToEmptyQueue() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        Collection<Integer> target = new ArrayList<>();
        int drained = queue.drainTo(target);
        if (drained != 0) {
            throw new AssertionError("Expected 0 elements drained from empty queue, got " + drained);
        }
        if (!target.isEmpty()) {
            throw new AssertionError("Target collection should be empty after draining from empty queue");
        }
    }

    static void testDrainToNonEmptyQueue() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.add(3);
        queue.add(1);
        queue.add(2);
        Collection<Integer> target = new ArrayList<>();
        int drained = queue.drainTo(target);
        if (drained != 3) {
            throw new AssertionError("Expected 3 elements drained, got " + drained);
        }
        if (queue.size() != 0) {
            throw new AssertionError("Queue should be empty after drainTo");
        }
        if (target.size() != 3) {
            throw new AssertionError("Target collection should contain 3 elements after drainTo");
        }
        // The order in target is not guaranteed, but all elements must be present
        if (!(target.contains(1) && target.contains(2) && target.contains(3))) {
            throw new AssertionError("Target collection missing expected elements");
        }
    }

    static void testDrainToNullCollection() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        boolean threw = false;
        try {
            queue.drainTo(null);
        } catch (NullPointerException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected NullPointerException when passing null collection to drainTo");
        }
    }
}
