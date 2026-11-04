/*
 * @test
 * @summary Test removeIf method for LinkedTransferQueue
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;
import java.util.function.Predicate;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) {
        testRemoveIfWithNullPredicate();
        testRemoveIfWithValidPredicate();
    }

    private static void testRemoveIfWithNullPredicate() {
        LinkedTransferQueue<Integer> queue = new LinkedTransferQueue<>();
        boolean exceptionThrown = false;
        try {
            queue.removeIf(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null predicate");
        }
    }

    private static void testRemoveIfWithValidPredicate() {
        LinkedTransferQueue<Integer> queue = new LinkedTransferQueue<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        boolean result = queue.removeIf(e -> e % 2 == 0); // remove even numbers
        if (!result) {
            throw new AssertionError("removeIf should return true when elements are removed");
        }
        if (queue.contains(2)) {
            throw new AssertionError("Queue should not contain 2 after removal");
        }
        if (queue.size() != 2) {
            throw new AssertionError("Queue size should be 2 after removal, but was " + queue.size());
        }
        if (!queue.contains(1) || !queue.contains(3)) {
            throw new AssertionError("Queue should contain 1 and 3 after removal");
        }
    }
}
