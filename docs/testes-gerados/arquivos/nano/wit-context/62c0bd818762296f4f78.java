/*
 * @test
 * @summary Test for drainTo method in LinkedTransferQueue
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;
import java.util.Collection;
import java.util.ArrayList;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) {
        testDrainToWithNullCollection();
        testDrainToWithSelf();
        testDrainToNormalOperation();
    }

    static void testDrainToWithNullCollection() {
        LinkedTransferQueue<Integer> queue = new LinkedTransferQueue<>();
        int exceptionThrown = 0;
        try {
            queue.drainTo(null, 10);
        } catch (NullPointerException e) {
            exceptionThrown = 1;
        }
        if (exceptionThrown != 1) {
            throw new AssertionError("Expected NullPointerException when c is null");
        }
    }

    static void testDrainToWithSelf() {
        LinkedTransferQueue<Integer> queue = new LinkedTransferQueue<>();
        try {
            queue.drainTo(queue, 10);
            throw new AssertionError("Expected IllegalArgumentException when draining to self");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testDrainToNormalOperation() {
        LinkedTransferQueue<Integer> queue = new LinkedTransferQueue<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        Collection<Integer> c = new ArrayList<>();
        int drainedCount = queue.drainTo(c, 2);
        if (drainedCount != 2) {
            throw new AssertionError("drainTo should drain 2 elements, but drained " + drainedCount);
        }
        if (c.size() != 2 || !c.contains(1) || !c.contains(2)) {
            throw new AssertionError("Collection c should contain first two elements");
        }
        if (queue.size() != 1 || !queue.contains(3)) {
            throw new AssertionError("Queue should contain the remaining element 3");
        }
        // drain remaining
        c.clear();
        int remaining = queue.drainTo(c, 10);
        if (remaining != 1) {
            throw new AssertionError("Remaining elements drained should be 1, but was " + remaining);
        }
        if (!c.contains(3)) {
            throw new AssertionError("Collection should contain element 3");
        }
        if (queue.size() != 0) {
            throw new AssertionError("Queue should be empty after draining remaining elements");
        }
    }
}
