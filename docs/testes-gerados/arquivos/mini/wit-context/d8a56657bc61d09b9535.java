/*
 * @test
 * @summary Test ConcurrentLinkedDeque.removeAll(Collection<?>) behavior including null argument
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class ConcurrentLinkedDequeWitupTest {
    public static void main(String[] args) {
        testRemoveAllWithNullCollection();
        testRemoveAllWithEmptyCollection();
        testRemoveAllWithNonEmptyCollection();
    }

    private static void testRemoveAllWithNullCollection() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        try {
            deque.removeAll(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testRemoveAllWithEmptyCollection() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("a");
        deque.add("b");
        Collection<String> empty = new ArrayList<>();
        boolean changed = deque.removeAll(empty);
        if (changed) {
            throw new RuntimeException("removeAll(empty) should return false when no elements removed");
        }
        if (deque.size() != 2) {
            throw new RuntimeException("Deque size should remain 2 after removeAll(empty)");
        }
    }

    private static void testRemoveAllWithNonEmptyCollection() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("a");
        deque.add("b");
        deque.add("c");
        Collection<String> toRemove = Arrays.asList("a", "c");
        boolean changed = deque.removeAll(toRemove);
        if (!changed) {
            throw new RuntimeException("removeAll(toRemove) should return true when elements removed");
        }
        if (deque.size() != 1) {
            throw new RuntimeException("Deque size should be 1 after removeAll(toRemove)");
        }
        if (!deque.contains("b")) {
            throw new RuntimeException("Deque should contain 'b' after removeAll(toRemove)");
        }
    }
}
