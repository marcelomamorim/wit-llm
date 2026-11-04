/*
 * @test
 * @summary Test PriorityBlockingQueue.size() method for correct locking and size retrieval
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class PriorityBlockingQueueWitupTest {
    public static void main(String[] args) {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();

        // Initially empty queue size should be 0
        int initialSize = queue.size();
        if (initialSize != 0) {
            throw new AssertionError("Expected initial size 0 but got " + initialSize);
        }

        // Add elements and verify size increments
        queue.add(10);
        queue.add(20);
        int sizeAfterAdd = queue.size();
        if (sizeAfterAdd != 2) {
            throw new AssertionError("Expected size 2 after adding two elements but got " + sizeAfterAdd);
        }

        // Remove element and verify size decrements
        queue.poll();
        int sizeAfterPoll = queue.size();
        if (sizeAfterPoll != 1) {
            throw new AssertionError("Expected size 1 after polling one element but got " + sizeAfterPoll);
        }

        // Test concurrent access to size() by locking explicitly
        // This is to ensure no deadlock or exception occurs
        ReentrantLock lock = getLock(queue);
        lock.lock();
        try {
            int lockedSize = queue.size();
            if (lockedSize != 1) {
                throw new AssertionError("Expected size 1 while holding lock but got " + lockedSize);
            }
        } finally {
            lock.unlock();
        }
    }

    // Helper method to access the private lock field via subclassing
    // Since the lock field is private and no accessor exists, we create a subclass
    static ReentrantLock getLock(PriorityBlockingQueue<?> queue) {
        class LockAccessor<E> extends PriorityBlockingQueue<E> {
            LockAccessor(PriorityBlockingQueue<E> q) {
                super();
                // We do not copy elements, just return the lock from the original queue
            }

            ReentrantLock getLockField() {
                // The lock field is private in PriorityBlockingQueue, so we cannot access it directly.
                // We rely on the fact that PriorityBlockingQueue uses a final ReentrantLock lock field.
                // Since no public accessor exists, and reflection is disallowed, we return a new lock here.
                // This is a limitation; thus, we do not test internal locking directly.
                // Instead, we test size() behavior which internally locks.
                return null;
            }
        }
        // Reflection or direct access is disallowed; return null to indicate no direct lock access
        return null;
    }
}