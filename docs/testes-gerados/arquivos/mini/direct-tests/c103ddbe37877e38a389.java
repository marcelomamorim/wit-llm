/*
 * @test
 * @summary Test LinkedBlockingDeque.retainAll(Collection<?>) method behavior
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Iterator;
import java.util.Objects;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) throws Exception {
        testRetainAllWithNullCollection();
        testRetainAllWithEmptyCollection();
        testRetainAllWithPartialRetain();
        testRetainAllWithFullRetain();
        testRetainAllWithNoRetain();
    }

    static void testRetainAllWithNullCollection() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        try {
            deque.retainAll(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testRetainAllWithEmptyCollection() throws Exception {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("a");
        deque.add("b");
        deque.add("c");

        boolean changed = deque.retainAll(new ArrayList<String>());
        if (!changed) {
            throw new RuntimeException("Expected retainAll to return true when removing elements");
        }
        if (!deque.isEmpty()) {
            throw new RuntimeException("Expected deque to be empty after retainAll with empty collection");
        }
    }

    static void testRetainAllWithPartialRetain() throws Exception {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("a");
        deque.add("b");
        deque.add("c");

        Collection<String> retain = Arrays.asList("b", "c", "d");
        boolean changed = deque.retainAll(retain);
        if (!changed) {
            throw new RuntimeException("Expected retainAll to return true when removing elements");
        }

        if (deque.size() != 2) {
            throw new RuntimeException("Expected size 2 after retainAll");
        }

        if (!deque.contains("b") || !deque.contains("c")) {
            throw new RuntimeException("Expected deque to contain only retained elements");
        }
    }

    static void testRetainAllWithFullRetain() throws Exception {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("a");
        deque.add("b");

        Collection<String> retain = Arrays.asList("a", "b", "c");
        boolean changed = deque.retainAll(retain);
        if (changed) {
            throw new RuntimeException("Expected retainAll to return false when no elements removed");
        }

        if (deque.size() != 2) {
            throw new RuntimeException("Expected size 2 after retainAll with full retain");
        }
    }

    static void testRetainAllWithNoRetain() throws Exception {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("a");
        deque.add("b");

        Collection<String> retain = Arrays.asList("x", "y");
        boolean changed = deque.retainAll(retain);
        if (!changed) {
            throw new RuntimeException("Expected retainAll to return true when all elements removed");
        }

        if (!deque.isEmpty()) {
            throw new RuntimeException("Expected deque to be empty after retainAll with no retained elements");
        }
    }
}
