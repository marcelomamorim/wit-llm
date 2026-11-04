/*
 * @test
 * @summary Test PriorityQueue.retainAll(Collection<?>) behavior
 * @run main PriorityQueueWitupTest
 */

import java.util.PriorityQueue;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class PriorityQueueWitupTest {
    public static void main(String[] args) {
        testRetainAllWithNullCollection();
        testRetainAllRemovesElementsNotInCollection();
        testRetainAllWithEmptyCollectionRemovesAll();
        testRetainAllWithAllElementsPresentKeepsAll();
    }

    private static void testRetainAllWithNullCollection() {
        PriorityQueue<String> pq = new PriorityQueue<>();
        try {
            pq.retainAll(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testRetainAllRemovesElementsNotInCollection() {
        PriorityQueue<String> pq = new PriorityQueue<>();
        pq.add("a");
        pq.add("b");
        pq.add("c");

        Collection<String> retain = Arrays.asList("a", "c");
        boolean changed = pq.retainAll(retain);

        if (!changed) {
            throw new RuntimeException("retainAll should return true when elements are removed");
        }

        if (pq.size() != 2 || !pq.contains("a") || !pq.contains("c") || pq.contains("b")) {
            throw new RuntimeException("retainAll did not remove elements correctly");
        }
    }

    private static void testRetainAllWithEmptyCollectionRemovesAll() {
        PriorityQueue<String> pq = new PriorityQueue<>();
        pq.add("x");
        pq.add("y");

        Collection<String> empty = new ArrayList<>();
        boolean changed = pq.retainAll(empty);

        if (!changed) {
            throw new RuntimeException("retainAll should return true when all elements are removed");
        }

        if (!pq.isEmpty()) {
            throw new RuntimeException("retainAll with empty collection should remove all elements");
        }
    }

    private static void testRetainAllWithAllElementsPresentKeepsAll() {
        PriorityQueue<String> pq = new PriorityQueue<>();
        pq.add("one");
        pq.add("two");

        Collection<String> all = Arrays.asList("one", "two", "three");
        boolean changed = pq.retainAll(all);

        if (changed) {
            throw new RuntimeException("retainAll should return false when no elements are removed");
        }

        if (pq.size() != 2 || !pq.contains("one") || !pq.contains("two")) {
            throw new RuntimeException("retainAll removed elements incorrectly when all present");
        }
    }
}
