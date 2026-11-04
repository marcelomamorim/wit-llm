/*
 * @test
 * @summary Test ArrayBlockingQueue.remove(Object) method behavior
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {
    public static void main(String[] args) {
        testRemoveNullReturnsFalse();
        testRemoveExistingElementReturnsTrue();
        testRemoveNonExistingElementReturnsFalse();
    }

    private static void testRemoveNullReturnsFalse() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        boolean result = queue.remove(null);
        if (result != false) {
            throw new AssertionError("remove(null) should return false");
        }
    }

    private static void testRemoveExistingElementReturnsTrue() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        queue.add("one");
        queue.add("two");
        queue.add("three");
        boolean removed = queue.remove("two");
        if (!removed) {
            throw new AssertionError("remove(existing element) should return true");
        }
        if (queue.contains("two")) {
            throw new AssertionError("Element 'two' should be removed from the queue");
        }
        if (queue.size() != 2) {
            throw new AssertionError("Queue size should be 2 after removal");
        }
    }

    private static void testRemoveNonExistingElementReturnsFalse() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        queue.add("one");
        queue.add("two");
        boolean removed = queue.remove("three");
        if (removed) {
            throw new AssertionError("remove(non-existing element) should return false");
        }
        if (queue.size() != 2) {
            throw new AssertionError("Queue size should remain 2 when removing non-existing element");
        }
    }
}
