/*
 * @test
 * @summary Tests for LinkedBlockingQueue retainAll method behavior
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.Collection;
import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testRetainAllWithNull();
        testRetainAllWithNonNull();
    }

    private static void testRetainAllWithNull() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        boolean exceptionThrown = false;
        try {
            queue.retainAll(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when retainAll(null)");
        }
    }

    private static void testRetainAllWithNonNull() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        Collection<Integer> c = java.util.Arrays.asList(2, 3);
        boolean changed = queue.retainAll(c);
        if (!changed) {
            throw new AssertionError("retainAll should return true when queue is modified");
        }
        if (queue.size() != 2 || !queue.contains(2) || !queue.contains(3)) {
            throw new AssertionError("Queue contents incorrect after retainAll");
        }
        // Test retainAll with a collection that does not change the queue
        changed = queue.retainAll(c);
        if (changed) {
            throw new AssertionError("retainAll should return false when queue is unchanged");
        }
        if (queue.size() != 2) {
            throw new AssertionError("Queue size changed unexpectedly");
        }
    }
}
