/*
 * @test
 * @summary Test PriorityBlockingQueue.contains(Object) method behavior
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class PriorityBlockingQueueWitupTest {
    public static void main(String[] args) {
        testContainsWithPresentElement();
        testContainsWithAbsentElement();
        testContainsWithNull();
    }

    private static void testContainsWithPresentElement() {
        PriorityBlockingQueue<String> queue = new PriorityBlockingQueue<>();
        queue.add("test");
        boolean result = queue.contains("test");
        if (!result) {
            throw new AssertionError("contains should return true for present element");
        }
    }

    private static void testContainsWithAbsentElement() {
        PriorityBlockingQueue<String> queue = new PriorityBlockingQueue<>();
        queue.add("test");
        boolean result = queue.contains("absent");
        if (result) {
            throw new AssertionError("contains should return false for absent element");
        }
    }

    private static void testContainsWithNull() {
        PriorityBlockingQueue<String> queue = new PriorityBlockingQueue<>();
        queue.add("test");
        // According to PriorityBlockingQueue spec, null elements are not permitted,
        // so contains(null) should return false rather than throw.
        boolean result = queue.contains(null);
        if (result) {
            throw new AssertionError("contains should return false for null element");
        }
    }
}
