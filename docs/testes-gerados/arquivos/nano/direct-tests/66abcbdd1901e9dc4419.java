/*
 * @test    @summary Test drainTo method of ArrayBlockingQueue
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.Collection;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testDrainToWithElements();
        testDrainToEmptyQueue();
        testDrainToNullCollection();
    }

    static void testDrainToWithElements() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
        queue.add(1);
        queue.add(2);
        queue.add(3);
        Collection<Integer> c = new java.util.ArrayList<>();
        int drainedCount = queue.drainTo(c);
        assert drainedCount == 3 : "Expected drained count 3, got " + drainedCount;
        assert c.size() == 3 : "Collection size should be 3";
        assert c.contains(1) && c.contains(2) && c.contains(3) : "Collection should contain all drained elements";
        assert queue.isEmpty() : "Queue should be empty after drain";
    }

    static void testDrainToEmptyQueue() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);
        Collection<Integer> c = new java.util.ArrayList<>();
        int drainedCount = queue.drainTo(c);
        assert drainedCount == 0 : "Expected drained count 0 for empty queue, got " + drainedCount;
        assert c.isEmpty() : "Collection should be empty";
    }

    static void testDrainToNullCollection() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);
        queue.add(42);
        try {
            queue.drainTo(null);
            assert false : "Expected NullPointerException when collection is null";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
