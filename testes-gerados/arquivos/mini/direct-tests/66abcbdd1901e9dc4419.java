/*
 * @test
 * @summary Test ArrayBlockingQueue.drainTo(Collection<? super E>) method
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.Collection;
import java.util.ArrayList;

public class ArrayBlockingQueueWitupTest {
    public static void main(String[] args) throws Exception {
        testDrainToWithElements();
        testDrainToEmptyQueue();
        testDrainToNullCollection();
    }

    static void testDrainToWithElements() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        queue.add("one");
        queue.add("two");
        queue.add("three");

        Collection<String> target = new ArrayList<>();
        int drained = queue.drainTo(target);

        if (drained != 3) {
            throw new RuntimeException("Expected drained count 3 but got " + drained);
        }
        if (target.size() != 3) {
            throw new RuntimeException("Expected target size 3 but got " + target.size());
        }
        if (!target.contains("one") || !target.contains("two") || !target.contains("three")) {
            throw new RuntimeException("Target collection missing expected elements");
        }
        if (!queue.isEmpty()) {
            throw new RuntimeException("Queue should be empty after drainTo");
        }
    }

    static void testDrainToEmptyQueue() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(5);
        Collection<String> target = new ArrayList<>();
        int drained = queue.drainTo(target);

        if (drained != 0) {
            throw new RuntimeException("Expected drained count 0 but got " + drained);
        }
        if (!target.isEmpty()) {
            throw new RuntimeException("Target collection should be empty");
        }
    }

    static void testDrainToNullCollection() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(5);
        boolean threw = false;
        try {
            queue.drainTo(null);
        } catch (NullPointerException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected NullPointerException when passing null collection");
        }
    }
}
