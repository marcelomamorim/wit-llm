/*
 * @test
 * @summary Test LinkedBlockingDeque.removeIf(Predicate) behavior
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.Predicate;
import java.util.NoSuchElementException;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) {
        testRemoveIfWithNullPredicate();
        testRemoveIfRemovesMatchingElements();
        testRemoveIfReturnsFalseWhenNoMatch();
        testRemoveIfOnEmptyDeque();
    }

    static void testRemoveIfWithNullPredicate() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        try {
            deque.removeIf(null);
            throw new Error("Expected NullPointerException for null predicate");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testRemoveIfRemovesMatchingElements() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("apple");
        deque.add("banana");
        deque.add("cherry");
        deque.add("date");

        Predicate<String> startsWithB = new Predicate<String>() {
            public boolean test(String s) {
                return s.startsWith("b");
            }
        };

        boolean removed = deque.removeIf(startsWithB);
        if (!removed) {
            throw new Error("Expected removeIf to return true when elements removed");
        }

        if (deque.contains("banana")) {
            throw new Error("Element 'banana' should have been removed");
        }

        if (!deque.contains("apple") || !deque.contains("cherry") || !deque.contains("date")) {
            throw new Error("Non-matching elements should remain in deque");
        }
    }

    static void testRemoveIfReturnsFalseWhenNoMatch() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.add(1);
        deque.add(2);
        deque.add(3);

        Predicate<Integer> greaterThanTen = new Predicate<Integer>() {
            public boolean test(Integer i) {
                return i > 10;
            }
        };

        boolean removed = deque.removeIf(greaterThanTen);
        if (removed) {
            throw new Error("Expected removeIf to return false when no elements removed");
        }

        if (deque.size() != 3) {
            throw new Error("Deque size should remain unchanged when no elements removed");
        }
    }

    static void testRemoveIfOnEmptyDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        Predicate<String> any = new Predicate<String>() {
            public boolean test(String s) {
                return true;
            }
        };

        boolean removed = deque.removeIf(any);
        if (removed) {
            throw new Error("Expected removeIf to return false on empty deque");
        }
    }
}
