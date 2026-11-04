/*
 * @test
 * @summary Test LinkedBlockingQueue.drainTo(Collection<? super E>) method
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.ArrayList;
import java.util.Collection;

public class LinkedBlockingQueueWitupTest {
    public static void main(String[] args) throws Exception {
        testDrainToWithEmptyQueue();
        testDrainToWithElements();
        testDrainToWithNullCollection();
    }

    private static void testDrainToWithEmptyQueue() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        Collection<String> target = new ArrayList<>();
        int drained = queue.drainTo(target);
        if (drained != 0) {
            throw new RuntimeException("Expected 0 elements drained from empty queue, got " + drained);
        }
        if (!target.isEmpty()) {
            throw new RuntimeException("Target collection should be empty after draining empty queue");
        }
    }

    private static void testDrainToWithElements() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.add("one");
        queue.add("two");
        queue.add("three");
        Collection<String> target = new ArrayList<>();
        int drained = queue.drainTo(target);
        if (drained != 3) {
            throw new RuntimeException("Expected 3 elements drained, got " + drained);
        }
        if (target.size() != 3) {
            throw new RuntimeException("Target collection size should be 3 after draining");
        }
        if (!target.contains("one") || !target.contains("two") || !target.contains("three")) {
            throw new RuntimeException("Target collection missing expected elements");
        }
        if (!queue.isEmpty()) {
            throw new RuntimeException("Queue should be empty after draining all elements");
        }
    }

    private static void testDrainToWithNullCollection() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        boolean threw = false;
        try {
            queue.drainTo(null);
        } catch (NullPointerException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected NullPointerException when passing null collection to drainTo");
        }
    }
}
