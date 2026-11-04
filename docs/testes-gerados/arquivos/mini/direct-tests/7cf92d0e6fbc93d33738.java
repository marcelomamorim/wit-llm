/*
 * @test
 * @summary Test LinkedBlockingDeque.removeFirstOccurrence(Object) behavior
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) throws Exception {
        testRemoveFirstOccurrenceNull();
        testRemoveFirstOccurrenceNotPresent();
        testRemoveFirstOccurrencePresent();
        testRemoveFirstOccurrenceMultiple();
    }

    static void testRemoveFirstOccurrenceNull() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        // null argument returns false
        if (deque.removeFirstOccurrence(null)) {
            throw new AssertionError("removeFirstOccurrence(null) should return false");
        }
    }

    static void testRemoveFirstOccurrenceNotPresent() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("a");
        deque.add("b");
        // remove element not present returns false
        if (deque.removeFirstOccurrence("c")) {
            throw new AssertionError("removeFirstOccurrence(non-present) should return false");
        }
        // deque unchanged
        if (deque.size() != 2 || !deque.contains("a") || !deque.contains("b")) {
            throw new AssertionError("deque modified incorrectly when removing non-present element");
        }
    }

    static void testRemoveFirstOccurrencePresent() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("a");
        deque.add("b");
        deque.add("c");
        // remove element present returns true
        if (!deque.removeFirstOccurrence("b")) {
            throw new AssertionError("removeFirstOccurrence(present) should return true");
        }
        // element removed
        if (deque.contains("b")) {
            throw new AssertionError("element 'b' should be removed");
        }
        // other elements remain
        if (deque.size() != 2 || !deque.contains("a") || !deque.contains("c")) {
            throw new AssertionError("deque state incorrect after removal");
        }
    }

    static void testRemoveFirstOccurrenceMultiple() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("x");
        deque.add("y");
        deque.add("x");
        deque.add("z");
        // removeFirstOccurrence removes only first occurrence
        if (!deque.removeFirstOccurrence("x")) {
            throw new AssertionError("removeFirstOccurrence(present) should return true");
        }
        // one 'x' remains
        int countX = 0;
        for (String s : deque) {
            if ("x".equals(s)) countX++;
        }
        if (countX != 1) {
            throw new AssertionError("only one occurrence of 'x' should remain");
        }
        // size reduced by one
        if (deque.size() != 3) {
            throw new AssertionError("deque size should be 3 after removal");
        }
    }
}
