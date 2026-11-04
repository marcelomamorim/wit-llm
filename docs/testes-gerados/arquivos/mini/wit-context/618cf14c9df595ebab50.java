/*
 * @test
 * @summary Test PriorityBlockingQueue.retainAll(Collection<?>) behavior and exceptions
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class PriorityBlockingQueueWitupTest {
    public static void main(String[] args) {
        testRetainAllNullCollection();
        testRetainAllReturnsBoolean();
    }

    private static void testRetainAllNullCollection() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        try {
            queue.retainAll(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testRetainAllReturnsBoolean() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);

        Collection<Integer> retain = new ArrayList<>();
        retain.add(2);
        retain.add(3);

        boolean changed = queue.retainAll(retain);
        if (!changed) {
            throw new RuntimeException("retainAll should return true when elements are removed");
        }

        // After retainAll, queue should contain only 2 and 3
        for (Integer i : queue) {
            if (!retain.contains(i)) {
                throw new RuntimeException("Queue contains element not in retain collection");
            }
        }

        // Calling retainAll with the same collection again should return false (no change)
        boolean changedAgain = queue.retainAll(retain);
        if (changedAgain) {
            throw new RuntimeException("retainAll should return false when no elements are removed");
        }
    }
}
