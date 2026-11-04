/*
 * @test
 * @summary Test LinkedTransferQueue.removeIf with null and valid predicate
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;
import java.util.function.Predicate;
import java.util.Objects;

public class LinkedTransferQueueWitupTest {
    public static void main(String[] args) {
        testRemoveIfWithNullPredicate();
        testRemoveIfWithAlwaysFalsePredicate();
        testRemoveIfWithAlwaysTruePredicate();
    }

    private static void testRemoveIfWithNullPredicate() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
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

    private static void testRemoveIfWithAlwaysFalsePredicate() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        queue.add("one");
        queue.add("two");
        queue.add("three");

        Predicate<String> neverRemove = new Predicate<String>() {
            public boolean test(String s) {
                return false;
            }
        };

        boolean changed = queue.removeIf(neverRemove);
        if (changed) {
            throw new AssertionError("removeIf with predicate always false should not remove elements");
        }
        if (queue.size() != 3) {
            throw new AssertionError("Queue size should remain 3 after removeIf with predicate always false");
        }
    }

    private static void testRemoveIfWithAlwaysTruePredicate() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        queue.add("one");
        queue.add("two");
        queue.add("three");

        Predicate<String> alwaysRemove = new Predicate<String>() {
            public boolean test(String s) {
                return true;
            }
        };

        boolean changed = queue.removeIf(alwaysRemove);
        if (!changed) {
            throw new AssertionError("removeIf with predicate always true should remove elements");
        }
        if (queue.size() != 0) {
            throw new AssertionError("Queue should be empty after removeIf with predicate always true");
        }
    }
}
