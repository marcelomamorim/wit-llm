/*
 * @test
 * @summary Test ArrayBlockingQueue.peek() method for correct behavior and concurrency safety
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a queue with capacity 2
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(2);

        // Test peek on empty queue returns null
        if (queue.peek() != null) {
            throw new AssertionError("peek() on empty queue should return null");
        }

        // Add one element and test peek returns it without removing
        String element = "test";
        if (!queue.offer(element)) {
            throw new AssertionError("offer() should succeed on empty queue");
        }
        String peeked = queue.peek();
        if (peeked == null || !peeked.equals(element)) {
            throw new AssertionError("peek() should return the element added");
        }

        // Test peek does not remove the element
        if (queue.size() != 1) {
            throw new AssertionError("peek() should not remove element");
        }

        // Add another element to fill the queue
        if (!queue.offer("second")) {
            throw new AssertionError("offer() should succeed when queue not full");
        }

        // Test peek still returns the head element
        peeked = queue.peek();
        if (peeked == null || !peeked.equals(element)) {
            throw new AssertionError("peek() should return the head element");
        }

        // Remove the head element and test peek updates
        String removed = queue.poll();
        if (removed == null || !removed.equals(element)) {
            throw new AssertionError("poll() should remove the head element");
        }
        peeked = queue.peek();
        if (peeked == null || !peeked.equals("second")) {
            throw new AssertionError("peek() should return new head after poll");
        }

        // Remove last element and test peek returns null again
        removed = queue.poll();
        if (removed == null || !removed.equals("second")) {
            throw new AssertionError("poll() should remove the last element");
        }
        if (queue.peek() != null) {
            throw new AssertionError("peek() on empty queue after removals should return null");
        }

        // Test concurrency: multiple threads calling peek concurrently
        final ArrayBlockingQueue<Integer> concurrentQueue = new ArrayBlockingQueue<>(10);
        concurrentQueue.offer(42);

        Thread t1 = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    Integer val = concurrentQueue.peek();
                    if (val == null || val != 42) {
                        throw new AssertionError("Concurrent peek returned wrong value");
                    }
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    Integer val = concurrentQueue.peek();
                    if (val == null || val != 42) {
                        throw new AssertionError("Concurrent peek returned wrong value");
                    }
                }
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}
