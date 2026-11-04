/*
 * @test
 * @summary Test ConcurrentLinkedDeque.retainAll(Collection<?>) behavior
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
        testRetainAllWithNullCollection();
        testRetainAllRemovesElementsNotInCollection();
        testRetainAllWithEmptyCollectionRemovesAll();
        testRetainAllWithAllElementsRetained();
        testRetainAllOnEmptyDequeReturnsFalse();
    }

    static void testRetainAllWithNullCollection() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        try {
            deque.retainAll(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testRetainAllRemovesElementsNotInCollection() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("a");
        deque.add("b");
        deque.add("c");

        Collection<String> retain = Arrays.asList("a", "c");
        boolean changed = deque.retainAll(retain);

        if (!changed) {
            throw new RuntimeException("retainAll should return true when elements are removed");
        }

        if (deque.size() != 2 || !deque.contains("a") || !deque.contains("c") || deque.contains("b")) {
            throw new RuntimeException("retainAll did not remove elements correctly");
        }
    }

    static void testRetainAllWithEmptyCollectionRemovesAll() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("x");
        deque.add("y");

        Collection<String> empty = new ArrayList<>();
        boolean changed = deque.retainAll(empty);

        if (!changed) {
            throw new RuntimeException("retainAll should return true when all elements are removed");
        }

        if (!deque.isEmpty()) {
            throw new RuntimeException("retainAll with empty collection should remove all elements");
        }
    }

    static void testRetainAllWithAllElementsRetained() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("one");
        deque.add("two");

        Collection<String> retain = Arrays.asList("one", "two");
        boolean changed = deque.retainAll(retain);

        if (changed) {
            throw new RuntimeException("retainAll should return false when no elements are removed");
        }

        if (deque.size() != 2 || !deque.contains("one") || !deque.contains("two")) {
            throw new RuntimeException("retainAll incorrectly modified the deque");
        }
    }

    static void testRetainAllOnEmptyDequeReturnsFalse() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        Collection<String> retain = Arrays.asList("anything");
        boolean changed = deque.retainAll(retain);
        if (changed) {
            throw new RuntimeException("retainAll on empty deque should return false");
        }
    }
}
