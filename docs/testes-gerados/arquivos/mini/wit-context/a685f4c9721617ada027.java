/*
 * @test
 * @summary Test ConcurrentLinkedQueue.removeIf(Predicate) for null argument and basic removal
 * @run main ConcurrentLinkedQueueWitupTest
 */

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Predicate;
import java.util.NoSuchElementException;

public class ConcurrentLinkedQueueWitupTest {
    public static void main(String[] args) {
        testRemoveIfNullPredicate();
        testRemoveIfBasicRemoval();
    }

    private static void testRemoveIfNullPredicate() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        boolean npeThrown = false;
        try {
            queue.removeIf(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when passing null predicate to removeIf");
        }
    }

    private static void testRemoveIfBasicRemoval() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        queue.add("one");
        queue.add("two");
        queue.add("three");

        Predicate<String> removeTwo = new Predicate<String>() {
            public boolean test(String s) {
                return "two".equals(s);
            }
        };

        boolean removed = queue.removeIf(removeTwo);
        if (!removed) {
            throw new AssertionError("Expected removeIf to return true when element is removed");
        }

        if (queue.contains("two")) {
            throw new AssertionError("Element 'two' should have been removed");
        }

        if (queue.size() != 2) {
            throw new AssertionError("Queue size should be 2 after removal, but was " + queue.size());
        }

        // Remove elements that do not exist
        Predicate<String> removeNone = new Predicate<String>() {
            public boolean test(String s) {
                return "four".equals(s);
            }
        };

        boolean removedNone = queue.removeIf(removeNone);
        if (removedNone) {
            throw new AssertionError("Expected removeIf to return false when no elements removed");
        }

        if (queue.size() != 2) {
            throw new AssertionError("Queue size should remain 2 when no elements removed, but was " + queue.size());
        }
    }
}
