/*
 * @test
 * @summary Test deterministic behavior of ArrayBlockingQueue.size() with locking
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class ArrayBlockingQueueWitupTest {
    public static void main(String[] args) {
        // Create a queue with capacity 10
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);

        // Initially size should be 0
        int initialSize = queue.size();
        if (initialSize != 0) {
            throw new AssertionError("Expected initial size 0 but got " + initialSize);
        }

        // Add one element and check size
        boolean added = queue.offer(42);
        if (!added) {
            throw new AssertionError("Failed to add element to queue");
        }
        int sizeAfterAdd = queue.size();
        if (sizeAfterAdd != 1) {
            throw new AssertionError("Expected size 1 after add but got " + sizeAfterAdd);
        }

        // Remove element and check size
        Integer removed = queue.poll();
        if (removed == null || removed != 42) {
            throw new AssertionError("Expected to poll 42 but got " + removed);
        }
        int sizeAfterRemove = queue.size();
        if (sizeAfterRemove != 0) {
            throw new AssertionError("Expected size 0 after remove but got " + sizeAfterRemove);
        }

        // Test concurrency: lock should be held and released properly
        // We test that size() does not throw and returns consistent values
        // This is a basic sanity check since the method uses ReentrantLock internally
        for (int i = 0; i < 5; i++) {
            queue.offer(i);
        }
        int sizeBefore = queue.size();
        if (sizeBefore != 5) {
            throw new AssertionError("Expected size 5 but got " + sizeBefore);
        }

        // Clear queue by polling all elements
        for (int i = 0; i < 5; i++) {
            Integer val = queue.poll();
            if (val == null) {
                throw new AssertionError("Expected non-null element during poll");
            }
        }

        int sizeAfterClear = queue.size();
        if (sizeAfterClear != 0) {
            throw new AssertionError("Expected size 0 after clearing but got " + sizeAfterClear);
        }

        System.out.println("ArrayBlockingQueue.size() tests passed.");
    }
}
