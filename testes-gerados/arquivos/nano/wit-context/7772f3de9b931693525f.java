/*
 * @test
 * @summary Test for ArrayBlockingQueue.toArray() method to observe behavior and exception handling.
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testToArrayEmptyQueue();
        testToArrayWithElements();
        testToArrayWithLockState();
    }

    private static void testToArrayEmptyQueue() {
        ArrayBlockingQueue<Object> queue = new ArrayBlockingQueue<>(10);
        Object[] result = queue.toArray();
        if (result.length != 0) {
            throw new AssertionError("Expected empty array, got length: " + result.length);
        }
    }

    private static void testToArrayWithElements() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        queue.add("a");
        queue.add("b");
        Object[] array = queue.toArray();
        if (array.length != 2) {
            throw new AssertionError("Expected array length 2, got: " + array.length);
        }
        if (!"a".equals(array[0]) || !"b".equals(array[1])) {
            throw new AssertionError("Array contents mismatch: " + java.util.Arrays.toString(array));
        }
    }

    private static void testToArrayWithLockState() {
        ArrayBlockingQueue<Object> queue = new ArrayBlockingQueue<>(10);
        // Access the lock directly to simulate lock held state
        ReentrantLock lock = null;
        try {
            java.lang.reflect.Field lockField = ArrayBlockingQueue.class.getDeclaredField("lock");
            lockField.setAccessible(true);
            lock = (ReentrantLock) lockField.get(queue);
            lock.lock();
            Object[] result = null;
            try {
                result = queue.toArray();
                // Expect no exception, but lock is held
                if (result == null) {
                    throw new AssertionError("Result should not be null when lock is held");
                }
            } finally {
                lock.unlock();
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new AssertionError("Reflection failed: " + e.getMessage(), e);
        }
    }
}
