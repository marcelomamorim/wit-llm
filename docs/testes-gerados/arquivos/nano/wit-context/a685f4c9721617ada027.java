/*
 * @test
 * @summary Tests for ConcurrentLinkedQueue.removeIf method
 * @run main ConcurrentLinkedQueueWitupTest
 */

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Predicate;

public class ConcurrentLinkedQueueWitupTest {

    public static void main(String[] args) {
        testRemoveIfWithNullPredicate();
        testRemoveIfWithValidPredicate();
    }

    private static void testRemoveIfWithNullPredicate() {
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
        queue.add(1);
        queue.add(2);
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
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        boolean removed = queue.removeIf(e -> e % 2 == 0);
        if (!removed) {
            throw new AssertionError("Expected removeIf to return true when elements are removed");
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
