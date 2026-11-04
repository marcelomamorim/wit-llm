/*
 * @test    @summary Test for retainAll method in LinkedTransferQueue
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;
import java.util.Collection;
import java.util.Arrays;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) {
        testRetainAllWithNull();
        testRetainAllWithEmptyCollection();
        testRetainAllWithNonEmptyCollection();
    }

    static void testRetainAllWithNull() {
        LinkedTransferQueue<Integer> queue = new LinkedTransferQueue<>();
        boolean exceptionThrown = false;
        try {
            queue.retainAll(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null to retainAll");
        }
    }

    static void testRetainAllWithEmptyCollection() {
        LinkedTransferQueue<Integer> queue = new LinkedTransferQueue<>();
        queue.add(1);
        queue.add(2);
        boolean changed = queue.retainAll(Arrays.asList());
        if (changed) {
            throw new AssertionError("retainAll with empty collection should not change the queue");
        }
        if (queue.size() != 2) {
            throw new AssertionError("Queue size should remain 2 after retainAll with empty collection");
        }
    }

    static void testRetainAllWithNonEmptyCollection() {
        LinkedTransferQueue<Integer> queue = new LinkedTransferQueue<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        boolean changed = queue.retainAll(Arrays.asList(2, 3));
        if (!changed) {
            throw new AssertionError("retainAll should return true when the queue is modified");
        }
        if (queue.size() != 2) {
            throw new AssertionError("Queue size should be 2 after retainAll");
        }
        if (!queue.contains(2) || !queue.contains(3)) {
            throw new AssertionError("Queue should contain 2 and 3 after retainAll");
        }
        if (queue.contains(1)) {
            throw new AssertionError("Queue should not contain 1 after retainAll");
        }
    }
}
