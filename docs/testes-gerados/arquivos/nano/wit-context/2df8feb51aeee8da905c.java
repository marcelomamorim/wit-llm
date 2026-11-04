/*
 * @test
 * @summary Testa o método remove() de ArrayBlockingQueue para comportamento esperado e exceções.
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testRemoveNormal();
        testRemoveEmpty();
        testRemoveAfterDetach();
    }

    static void testRemoveNormal() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
        queue.add(1);
        queue.add(2);
        try {
            queue.remove();
            assert queue.size() == 1 : "Queue size should be 1 after one remove";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testRemoveNormal", e);
        }
    }

    static void testRemoveEmpty() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
        try {
            queue.remove();
            throw new AssertionError("Expected NoSuchElementException when removing from empty queue");
        } catch (java.util.NoSuchElementException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testRemoveEmpty", e);
        }
    }

    static void testRemoveAfterDetach() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
        queue.add(1);
        // Accessing internal iterator to simulate detach scenario
        // Note: This is a fragile test as it depends on internal implementation
        try {
            java.util.Iterator<Integer> it = queue.iterator();
            // Move to first element
            if (it.hasNext()) {
                it.next();
                // Attempt to remove via iterator
                it.remove();
                // After removal, calling remove() again should throw IllegalStateException
                try {
                    it.remove();
                    throw new AssertionError("Expected IllegalStateException on second remove() call");
                } catch (IllegalStateException e) {
                    // expected
                }
            }
        } catch (UnsupportedOperationException e) {
            // iterator.remove() may throw if not supported, skip
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testRemoveAfterDetach", e);
        }
    }
}
