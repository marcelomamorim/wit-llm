/*
 * @test
 * @summary Test ArrayBlockingQueue.contains(Object) method
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class ArrayBlockingQueueWitupTest {
    public static void main(String[] args) throws Exception {
        testContainsNullReturnsFalse();
        testContainsOnEmptyQueueReturnsFalse();
        testContainsOnQueueWithElementReturnsTrue();
        testContainsOnQueueWithoutElementReturnsFalse();
        testContainsWithMultipleElements();
    }

    static void testContainsNullReturnsFalse() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(10);
        if (q.contains(null)) {
            throw new AssertionError("contains(null) should return false");
        }
    }

    static void testContainsOnEmptyQueueReturnsFalse() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(10);
        if (q.contains("foo")) {
            throw new AssertionError("contains on empty queue should return false");
        }
    }

    static void testContainsOnQueueWithElementReturnsTrue() throws InterruptedException {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(10);
        q.put("foo");
        if (!q.contains("foo")) {
            throw new AssertionError("contains should return true for present element");
        }
    }

    static void testContainsOnQueueWithoutElementReturnsFalse() throws InterruptedException {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(10);
        q.put("foo");
        if (q.contains("bar")) {
            throw new AssertionError("contains should return false for absent element");
        }
    }

    static void testContainsWithMultipleElements() throws InterruptedException {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(10);
        q.put("a");
        q.put("b");
        q.put("c");
        if (!q.contains("a") || !q.contains("b") || !q.contains("c")) {
            throw new AssertionError("contains should return true for all present elements");
        }
        if (q.contains("d")) {
            throw new AssertionError("contains should return false for absent element");
        }
    }
}
