/* @test    @summary Test drainTo method of SynchronousQueue    @run main SynchronousQueueWitupTest */

import java.util.concurrent.SynchronousQueue;
import java.util.Collection;

public class SynchronousQueueWitupTest {

    public static void main(String[] args) {
        testDrainToWithEmptyQueue();
        testDrainToWithOneElement();
        testDrainToWithMultipleElements();
        testDrainToWithNullCollection();
        testDrainToWithSelfCollection();
    }

    static void testDrainToWithEmptyQueue() {
        SynchronousQueue<String> queue = new SynchronousQueue<>();
        Collection<String> collection = new java.util.ArrayList<>();
        int drainedCount = queue.drainTo(collection);
        assert drainedCount == 0 : "Expected drained count 0, got " + drainedCount;
        assert collection.isEmpty() : "Collection should be empty after draining empty queue";
    }

    static void testDrainToWithOneElement() {
        SynchronousQueue<String> queue = new SynchronousQueue<>();
        // Insert an element by a thread to avoid blocking
        Thread producer = new Thread(() -> {
            try {
                queue.put("element1");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        producer.start();
        try {
            producer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        Collection<String> collection = new java.util.ArrayList<>();
        int drainedCount = queue.drainTo(collection);
        assert drainedCount == 1 : "Expected drained count 1, got " + drainedCount;
        assert collection.contains("element1") : "Collection should contain 'element1'";
    }

    static void testDrainToWithMultipleElements() {
        SynchronousQueue<String> queue = new SynchronousQueue<>();
        String[] elements = {"a", "b", "c"};
        Thread producer = new Thread(() -> {
            try {
                for (String e : elements) {
                    queue.put(e);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        producer.start();
        try {
            Thread.sleep(100); // Allow some time for producer to enqueue
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        Collection<String> collection = new java.util.ArrayList<>();
        int drainedCount = queue.drainTo(collection);
        // Since the queue is synchronous, only one element can be drained at a time
        assert drainedCount >= 1 && drainedCount <= elements.length : "Drained count should be between 1 and " + elements.length;
        boolean containsAny = false;
        for (String e : elements) {
            if (collection.contains(e)) {
                containsAny = true;
                break;
            }
        }
        assert containsAny : "Collection should contain at least one of the enqueued elements";
        producer.interrupt(); // Cleanup
    }

    static void testDrainToWithNullCollection() {
        SynchronousQueue<String> queue = new SynchronousQueue<>();
        try {
            queue.drainTo(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // Expected
        }
    }

    static void testDrainToWithSelfCollection() {
        SynchronousQueue<String> queue = new SynchronousQueue<>();
        Collection<String> collection = new java.util.ArrayList<>();
        try {
            queue.drainTo((Collection<? super String>) queue);
            assert false : "Expected IllegalArgumentException";
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }
}
