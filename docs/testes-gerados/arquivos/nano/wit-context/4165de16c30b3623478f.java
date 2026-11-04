/* @test    @summary Test for PriorityBlockingQueue.toArray() method to verify correct behavior and exception handling.    @run main PriorityBlockingQueueWitupTest */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testToArrayNormal();
        testToArrayEmpty();
        testToArrayWithLockInterruption();
    }

    static void testToArrayNormal() {
        PriorityBlockingQueue<Integer> pq = new PriorityBlockingQueue<>();
        pq.add(1);
        pq.add(2);
        pq.add(3);
        Object[] arr = pq.toArray();
        boolean containsAll = Arrays.asList(arr).containsAll(Arrays.asList(1, 2, 3));
        if (!containsAll) {
            throw new AssertionError("toArray() did not contain all elements");
        }
    }

    static void testToArrayEmpty() {
        PriorityBlockingQueue<Integer> pq = new PriorityBlockingQueue<>();
        Object[] arr = pq.toArray();
        if (arr.length != 0) {
            throw new AssertionError("Expected empty array for empty queue");
        }
    }

    static void testToArrayWithLockInterruption() {
        PriorityBlockingQueue<Integer> pq = new PriorityBlockingQueue<>();
        pq.add(1);
        pq.add(2);
        Thread t = new Thread(() -> {
            try {
                // Lock the queue to simulate contention
                java.util.concurrent.locks.ReentrantLock lock = (java.util.concurrent.locks.ReentrantLock) java.util.concurrent.PriorityBlockingQueue.class
                        .getDeclaredField("lock")
                        .get(pq);
                lock.lock();
                try {
                    pq.toArray();
                } finally {
                    lock.unlock();
                }
            } catch (Exception e) {
                // ignore
            }
        });
        t.start();
        try {
            Thread.sleep(50); // ensure thread has acquired lock
        } catch (InterruptedException e) {
            // ignore
        }
        t.interrupt();
        try {
            t.join();
        } catch (InterruptedException e) {
            // ignore
        }
    }
}
