/*
 * @test
 * @summary Test ArrayBlockingQueue removeIf method behavior
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.function.Predicate;

public class ArrayBlockingQueueWitupTest {
    public static void main(String[] args) {
        testRemoveIfRemovesMatchingElements();
        testRemoveIfWithNoMatchingElements();
        testRemoveIfWithNullPredicate();
    }

    private static void testRemoveIfRemovesMatchingElements() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(5);
        queue.add("one");
        queue.add("two");
        queue.add("three");

        Predicate<String> filter = new Predicate<String>() {
            public boolean test(String s) {
                return s.length() == 3; // remove "one" and "two"
            }
        };

        boolean removed = queue.removeIf(filter);
        if (!removed) {
            throw new AssertionError("Expected elements to be removed");
        }
        if (queue.size() != 1 || !"three".equals(queue.peek())) {
            throw new AssertionError("Queue contents incorrect after removeIf");
        }
    }

    private static void testRemoveIfWithNoMatchingElements() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(3);
        queue.add("four");
        queue.add("five");

        Predicate<String> filter = new Predicate<String>() {
            public boolean test(String s) {
                return s.length() > 10; // no elements match
            }
        };

        boolean removed = queue.removeIf(filter);
        if (removed) {
            throw new AssertionError("Expected no elements to be removed");
        }
        if (queue.size() != 2) {
            throw new AssertionError("Queue size changed unexpectedly");
        }
    }

    private static void testRemoveIfWithNullPredicate() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(1);
        try {
            queue.removeIf(null);
            throw new AssertionError("Expected NullPointerException for null predicate");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
