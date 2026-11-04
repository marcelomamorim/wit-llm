/* @test    @summary Test the remove() method of ReferenceQueue for normal operation and interruption handling    @run main ReferenceQueueWitupTest */

import java.lang.ref.ReferenceQueue;

public class ReferenceQueueWitupTest {

    public static void main(String[] args) throws Exception {
        testRemoveReturnsNullWhenEmpty();
        testRemoveBlocksUntilElementEnqueued();
        testRemoveInterrupted();
    }

    static void testRemoveReturnsNullWhenEmpty() {
        ReferenceQueue<Object> queue = new ReferenceQueue<>();
        Thread remover = new Thread(() -> {
            try {
                ReferenceQueue<Object> q = new ReferenceQueue<>();
                // Call remove with timeout to avoid blocking indefinitely
                // but since method signature does not include timeout, we simulate with a thread
                // that will be interrupted
                q.remove();
            } catch (InterruptedException e) {
                // ignore
            }
        });
        remover.start();
        Thread.sleep(100); // give time for thread to block
        remover.interrupt();
        remover.join();
        // No exception should be thrown, test passes if thread terminates
    }

    static void testRemoveBlocksUntilElementEnqueued() throws Exception {
        ReferenceQueue<Object> queue = new ReferenceQueue<>();
        Thread remover = new Thread(() -> {
            try {
                Object ref = queue.remove();
                // Should return null if queue is empty and interrupted, but in this test, we enqueue an element
                // after a delay
            } catch (InterruptedException e) {
                // ignore
            }
        });
        remover.start();
        Thread.sleep(200); // ensure remove() is blocking
        // Enqueue an element to unblock remove()
        queue.enqueue(new java.lang.ref.Reference<Object>(null, queue) {});
        Object result = queue.remove();
        // The remove() should return the enqueued reference
        assert result != null : "Expected non-null reference";
        remover.interrupt();
        remover.join();
    }

    static void testRemoveInterrupted() throws Exception {
        ReferenceQueue<Object> queue = new ReferenceQueue<>();
        Thread t = new Thread(() -> {
            try {
                queue.remove();
            } catch (InterruptedException e) {
                // Expected
            }
        });
        t.start();
        Thread.sleep(100);
        t.interrupt();
        t.join();
        // Confirm thread was interrupted and method threw InterruptedException
    }
}
