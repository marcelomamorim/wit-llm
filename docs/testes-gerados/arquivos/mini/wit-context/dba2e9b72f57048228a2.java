/*
 * @test
 * @summary Test deterministic behavior of ArrayBlockingQueue.contains(Object)
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class ArrayBlockingQueueWitupTest {
    public static void main(String[] args) {
        testContainsNullReturnsFalse();
        testContainsElementPresent();
        testContainsElementAbsent();
    }

    private static void testContainsNullReturnsFalse() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        // contains(null) must return false, not throw
        if (queue.contains(null)) {
            throw new AssertionError("contains(null) should return false");
        }
    }

    private static void testContainsElementPresent() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        queue.add("foo");
        queue.add("bar");
        // contains should return true for present element
        if (!queue.contains("foo")) {
            throw new AssertionError("contains should return true for element present");
        }
        if (!queue.contains("bar")) {
            throw new AssertionError("contains should return true for element present");
        }
    }

    private static void testContainsElementAbsent() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        queue.add("foo");
        // contains should return false for absent element
        if (queue.contains("baz")) {
            throw new AssertionError("contains should return false for element absent");
        }
    }
}
