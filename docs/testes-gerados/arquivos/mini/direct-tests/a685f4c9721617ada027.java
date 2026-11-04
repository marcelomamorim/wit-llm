/*
 * @test
 * @summary Test ConcurrentLinkedQueue removeIf method behavior
 * @run main ConcurrentLinkedQueueWitupTest
 */

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Predicate;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ConcurrentLinkedQueueWitupTest {
    public static void main(String[] args) {
        testRemoveIfWithNullPredicate();
        testRemoveIfRemovesMatchingElements();
        testRemoveIfReturnsFalseWhenNoMatch();
        testRemoveIfOnEmptyQueue();
    }

    static void testRemoveIfWithNullPredicate() {
        ConcurrentLinkedQueue<String> q = new ConcurrentLinkedQueue<>();
        try {
            q.removeIf(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testRemoveIfRemovesMatchingElements() {
        ConcurrentLinkedQueue<String> q = new ConcurrentLinkedQueue<>();
        q.add("one");
        q.add("two");
        q.add("three");
        q.add("four");

        Predicate<String> filter = new Predicate<String>() {
            public boolean test(String s) {
                return s.length() == 3; // remove "one" and "two"
            }
        };

        boolean removed = q.removeIf(filter);
        if (!removed) {
            throw new RuntimeException("Expected elements to be removed");
        }

        if (q.contains("one") || q.contains("two")) {
            throw new RuntimeException("Elements matching predicate were not removed");
        }

        if (!q.contains("three") || !q.contains("four")) {
            throw new RuntimeException("Elements not matching predicate were removed incorrectly");
        }
    }

    static void testRemoveIfReturnsFalseWhenNoMatch() {
        ConcurrentLinkedQueue<String> q = new ConcurrentLinkedQueue<>();
        q.add("one");
        q.add("two");

        Predicate<String> filter = new Predicate<String>() {
            public boolean test(String s) {
                return s.length() > 10; // no element matches
            }
        };

        boolean removed = q.removeIf(filter);
        if (removed) {
            throw new RuntimeException("Expected no elements to be removed");
        }

        if (!q.contains("one") || !q.contains("two")) {
            throw new RuntimeException("Elements were removed incorrectly");
        }
    }

    static void testRemoveIfOnEmptyQueue() {
        ConcurrentLinkedQueue<String> q = new ConcurrentLinkedQueue<>();
        Predicate<String> filter = new Predicate<String>() {
            public boolean test(String s) {
                return true;
            }
        };

        boolean removed = q.removeIf(filter);
        if (removed) {
            throw new RuntimeException("Expected no elements to be removed from empty queue");
        }
    }
}
