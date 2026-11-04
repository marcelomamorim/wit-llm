/*
 * @test
 * @summary Test ConcurrentLinkedDeque.removeAll(Collection<?>) behavior
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.Collection;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Iterator;
import java.util.Objects;

public class ConcurrentLinkedDequeWitupTest {
    public static void main(String[] args) {
        testRemoveAllWithNullCollection();
        testRemoveAllRemovesElementsPresentInCollection();
        testRemoveAllReturnsFalseWhenNoElementsRemoved();
        testRemoveAllReturnsTrueWhenElementsRemoved();
        testRemoveAllOnEmptyDequeReturnsFalse();
    }

    static void testRemoveAllWithNullCollection() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        try {
            deque.removeAll(null);
            throw new Error("Expected NullPointerException");
        } catch (NullPointerException expected) {
        }
    }

    static void testRemoveAllRemovesElementsPresentInCollection() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("a");
        deque.add("b");
        deque.add("c");
        Collection<String> toRemove = Arrays.asList("a", "c");
        boolean changed = deque.removeAll(toRemove);
        if (!changed) throw new Error("Expected removeAll to return true when elements removed");
        if (deque.contains("a") || deque.contains("c"))
            throw new Error("Elements 'a' and 'c' should have been removed");
        if (!deque.contains("b"))
            throw new Error("Element 'b' should remain");
    }

    static void testRemoveAllReturnsFalseWhenNoElementsRemoved() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("x");
        Collection<String> toRemove = Arrays.asList("a", "b");
        boolean changed = deque.removeAll(toRemove);
        if (changed) throw new Error("Expected removeAll to return false when no elements removed");
        if (!deque.contains("x"))
            throw new Error("Element 'x' should remain");
    }

    static void testRemoveAllReturnsTrueWhenElementsRemoved() {
        ConcurrentLinkedDeque<Integer> deque = new ConcurrentLinkedDeque<>();
        deque.add(1);
        deque.add(2);
        deque.add(3);
        Collection<Integer> toRemove = new ArrayList<>();
        toRemove.add(2);
        boolean changed = deque.removeAll(toRemove);
        if (!changed) throw new Error("Expected removeAll to return true when elements removed");
        if (deque.contains(2))
            throw new Error("Element 2 should have been removed");
        if (!deque.contains(1) || !deque.contains(3))
            throw new Error("Elements 1 and 3 should remain");
    }

    static void testRemoveAllOnEmptyDequeReturnsFalse() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        Collection<String> toRemove = Arrays.asList("a", "b");
        boolean changed = deque.removeAll(toRemove);
        if (changed) throw new Error("Expected removeAll to return false on empty deque");
    }
}
