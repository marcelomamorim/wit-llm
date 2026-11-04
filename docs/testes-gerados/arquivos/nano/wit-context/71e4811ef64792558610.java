/* @test    @summary Test for PriorityBlockingQueue.remove(Object o) method to verify correct behavior and exception handling.    @run main PriorityBlockingQueueWitupTest */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.NoSuchElementException;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testRemoveExistingElement();
        testRemoveNonExistingElement();
        testRemoveNullElement();
    }

    static void testRemoveExistingElement() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.add(10);
        queue.add(20);
        boolean removed = queue.remove(10);
        if (!removed) {
            throw new AssertionError("Expected to remove existing element 10");
        }
        if (queue.contains(10)) {
            throw new AssertionError("Queue should not contain 10 after removal");
        }
    }

    static void testRemoveNonExistingElement() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.add(30);
        boolean removed = queue.remove(40);
        if (removed) {
            throw new AssertionError("Removing non-existing element 40 should return false");
        }
        if (!queue.contains(30)) {
            throw new AssertionError("Queue should still contain 30 after attempting to remove 40");
        }
    }

    static void testRemoveNullElement() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.add(50);
        try {
            queue.remove(null);
            throw new AssertionError("Removing null should throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected exception
        }
        if (!queue.contains(50)) {
            throw new AssertionError("Queue should still contain 50 after attempting to remove null");
        }
    }
}
