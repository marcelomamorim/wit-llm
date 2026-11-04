/* @test    @summary Test put method of SynchronousQueue for normal operation and interruption    @run main SynchronousQueueWitupTest */

import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueWitupTest {

    public static void main(String[] args) throws InterruptedException {
        testPutNormal();
        testPutInterrupted();
    }

    private static void testPutNormal() throws InterruptedException {
        SynchronousQueue<String> queue = new SynchronousQueue<>();
        Thread producer = new Thread(() -> {
            try {
                queue.put("element");
            } catch (InterruptedException e) {
                // Should not happen in this test
            }
        });
        producer.start();
        String result = queue.take();
        producer.join();
        assert "element".equals(result) : "Expected 'element', got " + result;
    }

    private static void testPutInterrupted() throws InterruptedException {
        SynchronousQueue<String> queue = new SynchronousQueue<>();
        Thread producer = new Thread(() -> {
            try {
                queue.put("element");
            } catch (InterruptedException e) {
                // Expected interruption
            }
        });
        producer.start();
        Thread.sleep(50); // Ensure the thread is waiting on put
        producer.interrupt();
        producer.join();
        // After interruption, the thread should have exited
        // No exception thrown to main thread, test passes
    }
}
