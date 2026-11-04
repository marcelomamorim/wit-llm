/* @test    @summary Test drainTo method of SynchronousQueue for null argument handling    @run main SynchronousQueueWitupTest */

import java.util.concurrent.SynchronousQueue;
import java.util.Collection;

public class SynchronousQueueWitupTest {

    public static void main(String[] args) {
        testDrainToNullArgument();
        testDrainToNormalBehavior();
    }

    public static void testDrainToNullArgument() {
        SynchronousQueue<Object> queue = new SynchronousQueue<>();
        try {
            queue.drainTo(null);
            System.err.println("Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    public static void testDrainToNormalBehavior() {
        SynchronousQueue<String> queue = new SynchronousQueue<>();
        // Since SynchronousQueue is empty, drainTo should return 0 and not throw
        Collection<String> c = new java.util.ArrayList<>();
        int result = queue.drainTo(c);
        if (result != 0) {
            System.err.println("drainTo on empty queue should return 0");
            System.exit(1);
        }
        if (!c.isEmpty()) {
            System.err.println("Collection should be empty after drainTo on empty queue");
            System.exit(1);
        }
        // Test with a non-empty queue by inserting and removing elements
        // Note: Since SynchronousQueue blocks on put if no thread is ready to take,
        // we simulate a scenario where an element is available for drainTo by
        // starting a thread to put an element.
        Thread producer = new Thread(() -> {
            try {
                queue.put("element");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        producer.start();
        try {
            Thread.sleep(50); // allow thread to execute
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        c.clear();
        int drainedCount = queue.drainTo(c);
        if (drainedCount != 1) {
            System.err.println("drainTo should drain one element");
            System.exit(1);
        }
        if (c.size() != 1 || !"element".equals(c.iterator().next())) {
            System.err.println("drainTo did not drain the expected element");
            System.exit(1);
        }
        try {
            producer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
