/* @test    @summary Test for PriorityBlockingQueue.take() method to verify normal behavior and exception handling.    @run main PriorityBlockingQueueWitupTest */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;
import java.util.NoSuchElementException;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) throws Exception {
        testTakeNormal();
        testTakeInterrupted();
        testTakeUnsupportedOperation();
    }

    static void testTakeNormal() throws Exception {
        PriorityBlockingQueue<Integer> pq = new PriorityBlockingQueue<>();
        pq.offer(10);
        Integer result = pq.take();
        if (result == null || result != 10) {
            throw new AssertionError("Expected 10, got " + result);
        }
    }

    static void testTakeInterrupted() {
        PriorityBlockingQueue<Integer> pq = new PriorityBlockingQueue<>();
        Thread t = new Thread(() -> {
            try {
                pq.take();
            } catch (InterruptedException e) {
                // Expected interruption
            }
        });
        t.start();
        Thread.sleep(50);
        t.interrupt();
        t.join();
    }

    static void testTakeUnsupportedOperation() {
        PriorityBlockingQueue<Integer> pq = new PriorityBlockingQueue<>();
        // Simulate unsupported operation by subclassing and overriding dequeue to throw
        PriorityBlockingQueue<Integer> pq2 = new PriorityBlockingQueue<Integer>() {
            @Override
            public Integer dequeue() {
                throw new UnsupportedOperationException();
            }
        };
        try {
            pq2.take();
            throw new AssertionError("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            // Expected
        } catch (InterruptedException e) {
            // Not expected here
            throw new AssertionError(e);
        }
    }
}
