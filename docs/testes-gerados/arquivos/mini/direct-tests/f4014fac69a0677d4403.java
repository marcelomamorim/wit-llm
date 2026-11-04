/*
 * @test
 * @summary Test ArrayBlockingQueue.size() method for correctness and concurrency safety
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class ArrayBlockingQueueWitupTest {
    public static void main(String[] args) throws Exception {
        testSizeEmptyQueue();
        testSizeAfterAdd();
        testSizeAfterRemove();
        testSizeConcurrentAccess();
    }

    static void testSizeEmptyQueue() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(10);
        int size = q.size();
        if (size != 0) {
            throw new AssertionError("Expected size 0 for empty queue, got " + size);
        }
    }

    static void testSizeAfterAdd() throws InterruptedException {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(10);
        boolean added = q.offer(1);
        if (!added) {
            throw new AssertionError("Failed to add element to queue");
        }
        int size = q.size();
        if (size != 1) {
            throw new AssertionError("Expected size 1 after adding one element, got " + size);
        }
    }

    static void testSizeAfterRemove() throws InterruptedException {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(10);
        q.put(1);
        Integer val = q.take();
        if (val == null || val != 1) {
            throw new AssertionError("Expected to take element 1, got " + val);
        }
        int size = q.size();
        if (size != 0) {
            throw new AssertionError("Expected size 0 after removing element, got " + size);
        }
    }

    static void testSizeConcurrentAccess() throws Exception {
        final ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(100);
        final int n = 50;

        Thread producer = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < n; i++) {
                    try {
                        q.put(i);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        Thread consumer = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < n; i++) {
                    try {
                        q.take();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        producer.start();
        consumer.start();

        // While producer and consumer run, repeatedly check size is between 0 and n
        for (int i = 0; i < 100; i++) {
            int size = q.size();
            if (size < 0 || size > n) {
                throw new AssertionError("Queue size out of bounds during concurrent access: " + size);
            }
            Thread.sleep(10);
        }

        producer.join();
        consumer.join();

        // After completion, size should be zero
        int finalSize = q.size();
        if (finalSize != 0) {
            throw new AssertionError("Expected size 0 after producer and consumer finish, got " + finalSize);
        }
    }
}
