/*
 * @test
 * @summary Test java.util.ArrayDeque removeIf method behavior
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;
import java.util.function.Predicate;
import java.util.Objects;

public class ArrayDequeWitupTest {
    public static void main(String[] args) {
        testRemoveIfNullPredicate();
        testRemoveIfEmptyDeque();
        testRemoveIfRemoveSomeElements();
        testRemoveIfRemoveAllElements();
        testRemoveIfRemoveNoElements();
    }

    private static void testRemoveIfNullPredicate() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        try {
            deque.removeIf(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testRemoveIfEmptyDeque() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        boolean changed = deque.removeIf(new Predicate<String>() {
            public boolean test(String s) {
                return true; // would remove all if any
            }
        });
        if (changed) {
            throw new RuntimeException("removeIf on empty deque should return false");
        }
    }

    private static void testRemoveIfRemoveSomeElements() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.add(1);
        deque.add(2);
        deque.add(3);
        deque.add(4);
        deque.add(5);
        // Remove even numbers
        boolean changed = deque.removeIf(new Predicate<Integer>() {
            public boolean test(Integer i) {
                return (i % 2) == 0;
            }
        });
        if (!changed) {
            throw new RuntimeException("removeIf should return true when elements removed");
        }
        // Check remaining elements are odd
        for (Integer i : deque) {
            if ((i % 2) == 0) {
                throw new RuntimeException("Even element not removed: " + i);
            }
        }
    }

    private static void testRemoveIfRemoveAllElements() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("a");
        deque.add("b");
        deque.add("c");
        // Remove all elements
        boolean changed = deque.removeIf(new Predicate<String>() {
            public boolean test(String s) {
                return true;
            }
        });
        if (!changed) {
            throw new RuntimeException("removeIf should return true when all elements removed");
        }
        if (!deque.isEmpty()) {
            throw new RuntimeException("Deque should be empty after removeIf removes all elements");
        }
    }

    private static void testRemoveIfRemoveNoElements() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("x");
        deque.add("y");
        deque.add("z");
        // Remove no elements
        boolean changed = deque.removeIf(new Predicate<String>() {
            public boolean test(String s) {
                return false;
            }
        });
        if (changed) {
            throw new RuntimeException("removeIf should return false when no elements removed");
        }
        if (deque.size() != 3) {
            throw new RuntimeException("Deque size should remain unchanged when no elements removed");
        }
    }
}
