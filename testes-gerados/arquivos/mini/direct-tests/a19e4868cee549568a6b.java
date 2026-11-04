/*
 * @test
 * @summary Test CopyOnWriteArrayList removeAll(Collection<?>) method
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CopyOnWriteArrayListWitupTest {
    public static void main(String[] args) {
        testRemoveAllWithNullCollection();
        testRemoveAllWithEmptyCollection();
        testRemoveAllWithNoMatchingElements();
        testRemoveAllWithSomeMatchingElements();
        testRemoveAllWithAllMatchingElements();
    }

    private static void testRemoveAllWithNullCollection() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        try {
            list.removeAll(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testRemoveAllWithEmptyCollection() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(Arrays.asList("a", "b", "c"));
        boolean changed = list.removeAll(Collections.emptyList());
        if (changed) {
            throw new RuntimeException("removeAll with empty collection should not change the list");
        }
        if (list.size() != 3 || !list.contains("a") || !list.contains("b") || !list.contains("c")) {
            throw new RuntimeException("List contents changed unexpectedly");
        }
    }

    private static void testRemoveAllWithNoMatchingElements() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(Arrays.asList("a", "b", "c"));
        Collection<String> toRemove = Arrays.asList("x", "y");
        boolean changed = list.removeAll(toRemove);
        if (changed) {
            throw new RuntimeException("removeAll with no matching elements should not change the list");
        }
        if (list.size() != 3 || !list.contains("a") || !list.contains("b") || !list.contains("c")) {
            throw new RuntimeException("List contents changed unexpectedly");
        }
    }

    private static void testRemoveAllWithSomeMatchingElements() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(Arrays.asList("a", "b", "c", "d"));
        Collection<String> toRemove = Arrays.asList("b", "x", "d");
        boolean changed = list.removeAll(toRemove);
        if (!changed) {
            throw new RuntimeException("removeAll with some matching elements should change the list");
        }
        if (list.size() != 2 || !list.contains("a") || !list.contains("c")) {
            throw new RuntimeException("List contents incorrect after removeAll");
        }
    }

    private static void testRemoveAllWithAllMatchingElements() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(Arrays.asList("a", "b"));
        Collection<String> toRemove = Arrays.asList("a", "b");
        boolean changed = list.removeAll(toRemove);
        if (!changed) {
            throw new RuntimeException("removeAll with all matching elements should change the list");
        }
        if (!list.isEmpty()) {
            throw new RuntimeException("List should be empty after removeAll of all elements");
        }
    }
}
