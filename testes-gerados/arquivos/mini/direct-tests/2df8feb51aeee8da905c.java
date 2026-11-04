/*
 * @test
 * @summary Test ArrayBlockingQueue remove() method behavior
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) throws Exception {
        testRemoveIllegalStateException();
        testRemoveWithLastRetNegativeAndDetached();
        testRemoveWithLastRetPositiveAndAttached();
    }

    // Test that remove() throws IllegalStateException if lastRet == NONE (-1)
    private static void testRemoveIllegalStateException() throws Exception {
        final TestIterator it = new TestIterator(1);
        it.lastRet = -1; // NONE
        try {
            it.remove();
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException ise) {
            // expected
        }
    }

    // Test remove() when lastRet < 0 and nextIndex < 0 triggers detach()
    private static void testRemoveWithLastRetNegativeAndDetached() throws Exception {
        final TestIterator it = new TestIterator(1);
        it.lastRet = -2; // REMOVED
        it.cursor = -1;
        it.nextIndex = -1;
        it.detached = false;
        it.remove();
        if (!it.detached) {
            throw new RuntimeException("Expected detach() to be called");
        }
    }

    // Test remove() when lastRet >= 0 and isDetached() false calls removeAt(lastRet)
    private static void testRemoveWithLastRetPositiveAndAttached() throws Exception {
        final TestIterator it = new TestIterator(2);
        it.lastRet = 0;
        it.detached = false;
        it.remove();
        if (!it.removedAtCalled) {
            throw new RuntimeException("Expected removeAt(lastRet) to be called");
        }
    }

    // Minimal subclass to expose protected fields and methods for testing
    static class TestIterator extends ArrayBlockingQueue<Integer>.Itr {

        boolean removedAtCalled = false;
        boolean detached = false;

        TestIterator(int capacity) {
            // Create queue with capacity and fair=false
            super(new ArrayBlockingQueue<Integer>(capacity));
        }

        @Override
        boolean isDetached() {
            return detached;
        }

        @Override
        void incorporateDequeues() {
            // no-op for test
        }

        @Override
        void removeAt(int i) {
            removedAtCalled = true;
            // remove element at index i from queue
            // simulate by removing element from queue
            queue.remove();
        }

        @Override
        void detach() {
            detached = true;
        }

        @Override
        Integer itemAt(int i) {
            return queue.peek();
        }
    }
}
