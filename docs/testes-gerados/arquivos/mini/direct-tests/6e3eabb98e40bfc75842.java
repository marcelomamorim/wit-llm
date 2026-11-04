/*
 * @test
 * @summary Test ConcurrentLinkedQueue remove() method behavior
 * @run main ConcurrentLinkedQueueWitupTest
 */

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.NoSuchElementException;

public class ConcurrentLinkedQueueWitupTest {
    public static void main(String[] args) {
        testRemoveWithoutLastRetThrows();
        testRemoveClearsLastRetItem();
    }

    // This test verifies that calling remove() without a prior lastRet set throws IllegalStateException
    private static void testRemoveWithoutLastRetThrows() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        // We cannot access lastRet directly, so we simulate the scenario by calling remove() on iterator without next()
        java.util.Iterator<String> it = queue.iterator();
        boolean thrown = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalStateException when calling remove() without next()");
        }
    }

    // This test verifies that after calling next() and remove(), the element is removed from the queue
    private static void testRemoveClearsLastRetItem() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        queue.add("a");
        queue.add("b");

        java.util.Iterator<String> it = queue.iterator();
        String first = it.next();
        if (!"a".equals(first)) {
            throw new AssertionError("Expected first element to be 'a'");
        }
        it.remove();

        // After remove, the element "a" should no longer be in the queue
        if (queue.contains("a")) {
            throw new AssertionError("Element 'a' should have been removed from the queue");
        }

        // The queue should still contain "b"
        if (!queue.contains("b")) {
            throw new AssertionError("Element 'b' should still be in the queue");
        }
    }
}
