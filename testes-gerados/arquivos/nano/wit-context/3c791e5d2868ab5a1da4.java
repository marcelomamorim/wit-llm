/*
 * @test
 * @summary Tests for java.util.concurrent.LinkedBlockingQueue
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) throws Exception {
        testTakeNormal();
        testTakeInterrupted();
    }

    public static void testTakeNormal() throws Exception {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        // Add an element to ensure queue is not empty
        queue.offer("element");
        String result = queue.take();
        if (!"element".equals(result)) {
            throw new AssertionError("Expected 'element', got: " + result);
        }
    }

    public static void testTakeInterrupted() throws Exception {
        final LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        final Thread t = new Thread(() -> {
            try {
                queue.take();
            } catch (InterruptedException e) {
                // Expected interruption
            }
        });
        t.start();
        Thread.sleep(50);
        t.interrupt();
        t.join();
    }
}
