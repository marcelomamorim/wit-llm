/*
 * @test
 * @summary Test SynchronousQueue.drainTo(Collection) method behavior
 * @run main SynchronousQueueWitupTest
 */

import java.util.concurrent.SynchronousQueue;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Objects;
import java.util.IllegalFormatException;
import java.lang.IllegalArgumentException;

public class SynchronousQueueWitupTest {
    public static void main(String[] args) throws Exception {
        testDrainToNullCollection();
        testDrainToSelfCollection();
        testDrainToEmptyQueue();
        testDrainToWithElements();
    }

    static void testDrainToNullCollection() {
        SynchronousQueue<String> queue = new SynchronousQueue<>();
        try {
            queue.drainTo(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testDrainToSelfCollection() {
        SynchronousQueue<String> queue = new SynchronousQueue<>();
        try {
            queue.drainTo(queue);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testDrainToEmptyQueue() {
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

    static void testDrainToWithElements() throws InterruptedException {
        final SynchronousQueue<String> queue = new SynchronousQueue<>();
        final Collection<String> target = new ArrayList<>();

        // Start a thread to put elements into the queue
        Thread producer = new Thread(new Runnable() {
            public void run() {
                try {
                    queue.put("one");
                    queue.put("two");
                    queue.put("three");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        producer.start();

        // Drain elements from the queue
        int drained = 0;
        // Wait a bit to let producer start
        Thread.sleep(100);
        drained = queue.drainTo(target);

        // The number drained should be 3
        if (drained != 3) {
            throw new RuntimeException("Expected 3 elements drained, got " + drained);
        }

        // The target collection should contain the elements
        if (!target.contains("one") || !target.contains("two") || !target.contains("three")) {
            throw new RuntimeException("Target collection missing expected elements");
        }

        producer.join();
    }
}
