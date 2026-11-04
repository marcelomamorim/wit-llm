/*
 * @test
 * @summary Test for ArrayBlockingQueue.put method, covering null input and interruption scenarios
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Objects;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) throws Exception {
        testPutNull();
        testPutInterrupted();
        testPutNormal();
    }

    static void testPutNull() {
        ArrayBlockingQueue<Object> queue = new ArrayBlockingQueue<>(10);
        try {
            queue.put(null);
            System.err.println("Failed: Expected NullPointerException for null input");
        } catch (NullPointerException expected) {
            // Expected exception
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for null input: " + e);
        }
    }

    static void testPutInterrupted() throws Exception {
        ArrayBlockingQueue<Object> queue = new ArrayBlockingQueue<>(1);
        Thread t = new Thread(() -> {
            try {
                queue.put(new Object());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        queue.offer(new Object()); // fill the queue
        t.start();
        Thread.sleep(50); // ensure thread is waiting on put
        t.interrupt();
        t.join();
        // After interruption, the thread should have exited
        if (t.isAlive()) {
            System.err.println("Failed: Thread still alive after interruption");
        }
    }

    static void testPutNormal() throws Exception {
        ArrayBlockingQueue<Object> queue = new ArrayBlockingQueue<>(2);
        Object obj1 = new Object();
        Object obj2 = new Object();
        queue.put(obj1);
        queue.put(obj2);
        if (queue.size() != 2) {
            System.err.println("Failed: Queue size mismatch after puts");
        }
        Object first = queue.take();
        Object second = queue.take();
        if (first != obj1 || second != obj2) {
            System.err.println("Failed: Queue elements mismatch after takes");
        }
    }
}
