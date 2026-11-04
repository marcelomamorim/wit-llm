/*
 * @test
 * @summary Test PriorityBlockingQueue.size() method for correctness and thread-safety
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class PriorityBlockingQueueWitupTest {
    public static void main(String[] args) throws Exception {
        testSizeOnEmptyQueue();
        testSizeAfterAdditions();
        testSizeThreadSafety();
        System.out.println("PASS");
    }

    static void testSizeOnEmptyQueue() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        int size = queue.size();
        if (size != 0) {
            throw new AssertionError("Expected size 0 for empty queue, got " + size);
        }
    }

    static void testSizeAfterAdditions() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        int size = queue.size();
        if (size != 3) {
            throw new AssertionError("Expected size 3 after adding 3 elements, got " + size);
        }
    }

    static void testSizeThreadSafety() throws InterruptedException {
        final PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        final int threadCount = 10;
        final int addsPerThread = 1000;

        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(new Runnable() {
                public void run() {
                    for (int j = 0; j < addsPerThread; j++) {
                        queue.add(j);
                    }
                }
            });
            threads[i].start();
        }

        for (int i = 0; i < threadCount; i++) {
            threads[i].join();
        }

        int expectedSize = threadCount * addsPerThread;
        int actualSize = queue.size();
        if (actualSize != expectedSize) {
            throw new AssertionError("Expected size " + expectedSize + " after concurrent adds, got " + actualSize);
        }
    }
}
