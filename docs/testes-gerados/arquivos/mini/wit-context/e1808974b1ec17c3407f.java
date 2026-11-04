/*
 * @test
 * @summary Test LinkedBlockingQueue.drainTo(Collection) for NPE on null argument and basic behavior
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

public class LinkedBlockingQueueWitupTest {
    public static void main(String[] args) throws Exception {
        testDrainToNullCollection();
        testDrainToEmptyQueue();
        testDrainToNonEmptyQueue();
    }

    private static void testDrainToNullCollection() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        try {
            queue.drainTo(null);
            throw new AssertionError("Expected NullPointerException when passing null collection");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testDrainToEmptyQueue() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        List<String> target = new ArrayList<>();
        int drained = queue.drainTo(target);
        if (drained != 0) {
            throw new AssertionError("Expected 0 elements drained from empty queue, got " + drained);
        }
        if (!target.isEmpty()) {
            throw new AssertionError("Target collection should remain empty after draining empty queue");
        }
    }

    private static void testDrainToNonEmptyQueue() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.add("one");
        queue.add("two");
        queue.add("three");
        List<String> target = new ArrayList<>();
        int drained = queue.drainTo(target);
        if (drained != 3) {
            throw new AssertionError("Expected 3 elements drained, got " + drained);
        }
        if (target.size() != 3) {
            throw new AssertionError("Target collection size should be 3 after draining");
        }
        if (!target.get(0).equals("one") || !target.get(1).equals("two") || !target.get(2).equals("three")) {
            throw new AssertionError("Elements drained are not in FIFO order");
        }
        if (!queue.isEmpty()) {
            throw new AssertionError("Queue should be empty after draining all elements");
        }
    }
}
