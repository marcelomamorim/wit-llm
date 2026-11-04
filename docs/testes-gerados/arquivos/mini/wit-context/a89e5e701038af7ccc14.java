/*
 * @test
 * @summary Test SynchronousQueue.drainTo(Collection) for null argument and self argument
 * @run main SynchronousQueueWitupTest
 */

import java.util.concurrent.SynchronousQueue;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Objects;

public class SynchronousQueueWitupTest {
    public static void main(String[] args) {
        testDrainToNullCollection();
        testDrainToSelf();
        testDrainToEmptyQueue();
    }

    private static void testDrainToNullCollection() {
        SynchronousQueue<Object> queue = new SynchronousQueue<>();
        try {
            queue.drainTo(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testDrainToSelf() {
        SynchronousQueue<Object> queue = new SynchronousQueue<>();
        try {
            queue.drainTo(queue);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testDrainToEmptyQueue() {
        SynchronousQueue<String> queue = new SynchronousQueue<>();
        Collection<String> target = new ArrayList<>();
        int drained = queue.drainTo(target);
        if (drained != 0) {
            throw new RuntimeException("Expected 0 elements drained from empty queue, got " + drained);
        }
        if (!target.isEmpty()) {
            throw new RuntimeException("Target collection should remain empty when draining empty queue");
        }
    }
}
