/* @test    @summary Test drainTo method of PriorityBlockingQueue for correct behavior    @run main PriorityBlockingQueueWitupTest */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.Collection;
import java.util.ArrayList;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testDrainToEmptyQueue();
        testDrainToNonEmptyQueue();
        testDrainToLimitedCapacity();
    }

    static void testDrainToEmptyQueue() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        Collection<Integer> collection = new ArrayList<>();
        int drainedCount = queue.drainTo(collection);
        if (drainedCount != 0) {
            throw new AssertionError("Expected drained count 0, but got " + drainedCount);
        }
        if (!collection.isEmpty()) {
            throw new AssertionError("Expected collection to be empty after draining from empty queue");
        }
    }

    static void testDrainToNonEmptyQueue() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        Collection<Integer> collection = new ArrayList<>();
        int drainedCount = queue.drainTo(collection);
        if (drainedCount != 3) {
            throw new AssertionError("Expected drained count 3, but got " + drainedCount);
        }
        if (collection.size() != 3) {
            throw new AssertionError("Expected collection size 3, but got " + collection.size());
        }
        if (!collection.contains(1) || !collection.contains(2) || !collection.contains(3)) {
            throw new AssertionError("Collection does not contain all drained elements");
        }
        if (!queue.isEmpty()) {
            throw new AssertionError("Expected queue to be empty after drainTo");
        }
    }

    static void testDrainToLimitedCapacity() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.add(10);
        queue.add(20);
        queue.add(30);
        Collection<Integer> collection = new ArrayList<>();
        int limit = 2;
        int drainedCount = queue.drainTo(collection, limit);
        if (drainedCount != limit) {
            throw new AssertionError("Expected drained count " + limit + ", but got " + drainedCount);
        }
        if (collection.size() != limit) {
            throw new AssertionError("Expected collection size " + limit + ", but got " + collection.size());
        }
        if (!queue.contains(30)) {
            throw new AssertionError("Remaining element should be 30");
        }
        if (queue.size() != 1) {
            throw new AssertionError("Queue size should be 1 after partial drain");
        }
    }
}
