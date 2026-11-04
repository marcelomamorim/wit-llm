/*
 * @test
 * @summary Tests for java.util.concurrent.LinkedBlockingQueue
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) throws Exception {
        testPutWithNull();
        testPutWithInterruptedException();
        testPutWithInterruptedAndNonNull();
    }

    public static void testPutWithNull() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        try {
            queue.put(null);
            System.err.println("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    public static void testPutWithInterruptedException() throws Exception {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        ReentrantLock lock = new ReentrantLock();
        AtomicInteger count = new AtomicInteger(0);
        Thread thread = new Thread(() -> {
            try {
                lock.lock();
                // Hold lock to simulate blocking
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // ignore
            } finally {
                lock.unlock();
            }
        });
        thread.start();
        // Interrupt the thread to cause InterruptedException in lockInterruptibly
        thread.interrupt();
        try {
            lock.lockInterruptibly();
            // Should throw InterruptedException
            System.err.println("Expected InterruptedException not thrown");
        } catch (InterruptedException expected) {
            // Expected
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    public static void testPutWithInterruptedAndNonNull() throws Exception {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(1);
        // Fill the queue to capacity
        queue.put("element");
        Thread thread = new Thread(() -> {
            try {
                queue.put("another element");
            } catch (InterruptedException e) {
                // ignore
            }
        });
        thread.start();
        Thread.sleep(50); // ensure thread is blocked on put
        thread.interrupt();
        thread.join();
        // Verify that the queue still contains the first element
        if (queue.size() != 1 || !queue.contains("element")) {
            System.err.println("Queue state invalid after interrupted put");
        }
    }
}
