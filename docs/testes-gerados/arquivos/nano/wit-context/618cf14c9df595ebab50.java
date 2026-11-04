/* @test    @summary Test for retainAll method in PriorityBlockingQueue to verify exception paths and behavior.    @run main PriorityBlockingQueueWitupTest */

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testRetainAllWithNullCollection();
        testRetainAllWithNonNullCollection();
    }

    static void testRetainAllWithNullCollection() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        boolean exceptionThrown = false;
        try {
            queue.retainAll(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when retainAll called with null");
        }
    }

    static void testRetainAllWithNonNullCollection() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.addAll(Arrays.asList(1, 2, 3));
        Collection<Integer> c = Arrays.asList(2, 3);
        boolean result = false;
        try {
            result = queue.retainAll(c);
        } catch (UnsupportedOperationException e) {
            throw new AssertionError("retainAll should not throw UnsupportedOperationException with non-null collection", e);
        }
        if (!result) {
            throw new AssertionError("retainAll should return true if the queue was modified");
        }
        if (queue.size() != 2 || !queue.containsAll(c)) {
            throw new AssertionError("Queue contents after retainAll do not match expected");
        }
    }
}
