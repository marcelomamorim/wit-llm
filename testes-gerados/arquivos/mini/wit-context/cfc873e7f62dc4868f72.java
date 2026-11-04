/*
 * @test
 * @summary Test PriorityBlockingQueue.drainTo(Collection) for NPE on null argument and normal behavior
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PriorityBlockingQueueWitupTest {
    public static void main(String[] args) {
        testDrainToNullCollection();
        testDrainToEmptyCollection();
        testDrainToNonEmptyCollection();
    }

    private static void testDrainToNullCollection() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        boolean npeThrown = false;
        try {
            queue.drainTo(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when passing null to drainTo");
        }
    }

    private static void testDrainToEmptyCollection() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        List<Integer> target = new ArrayList<>();
        int drained = queue.drainTo(target);
        if (drained != 0) {
            throw new AssertionError("Expected 0 elements drained from empty queue, got " + drained);
        }
        if (!target.isEmpty()) {
            throw new AssertionError("Target collection should remain empty when draining empty queue");
        }
    }

    private static void testDrainToNonEmptyCollection() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.add(5);
        queue.add(1);
        queue.add(3);
        List<Integer> target = new ArrayList<>();
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
        // The order in target is not guaranteed, so just check contents
        if (!target.contains(1) || !target.contains(3) || !target.contains(5)) {
            throw new AssertionError("Target collection missing expected elements after drainTo");
        }
    }
}
