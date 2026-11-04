/* @test    @summary Test removeIf method of PriorityBlockingQueue for null and non-null predicates    @run main PriorityBlockingQueueWitupTest */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.function.Predicate;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testRemoveIfNullPredicate();
        testRemoveIfNonNullPredicate();
    }

    static void testRemoveIfNullPredicate() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
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

    static void testRemoveIfNonNullPredicate() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.add(1);
        queue.add(2);
        Predicate<Integer> predicate = (Integer x) -> x > 0;
        boolean result = queue.removeIf(predicate);
        if (!result) {
            throw new AssertionError("removeIf with valid predicate should return true if elements are removed");
        }
        if (queue.size() != 0) {
            throw new AssertionError("Queue should be empty after removing all elements");
        }
    }
}
