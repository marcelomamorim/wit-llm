/*
 * @test
 * @summary Tests for LinkedBlockingQueue.removeIf(Predicate)
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Predicate;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testRemoveIfWithNullPredicate();
        testRemoveIfWithNonNullPredicate();
    }

    private static void testRemoveIfWithNullPredicate() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
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

    private static void testRemoveIfWithNonNullPredicate() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        queue.add(1);
        queue.add(2);
        boolean result = queue.removeIf(e -> e % 2 == 0);
        if (result != true) {
            throw new AssertionError("removeIf should return true when elements are removed");
        }
        if (queue.contains(2)) {
            throw new AssertionError("Queue should not contain element 2 after removal");
        }
        if (queue.size() != 1) {
            throw new AssertionError("Queue size should be 1 after removal");
        }
        if (!queue.contains(1)) {
            throw new AssertionError("Queue should contain element 1");
        }
    }
}
