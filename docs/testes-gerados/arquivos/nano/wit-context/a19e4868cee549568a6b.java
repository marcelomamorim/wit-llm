/*
 * @test
 * @summary Tests for CopyOnWriteArrayList.removeAll(Collection)
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Collection;
import java.util.Arrays;

public class CopyOnWriteArrayListWitupTest {

    public static void main(String[] args) {
        testRemoveAllWithNullCollection();
        testRemoveAllWithEmptyCollection();
        testRemoveAllWithNonEmptyCollection();
    }

    static void testRemoveAllWithNullCollection() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.addAll(Arrays.asList("a", "b", "c"));
        boolean exceptionThrown = false;
        try {
            list.removeAll(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when removeAll(null)");
        }
    }

    static void testRemoveAllWithEmptyCollection() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.addAll(Arrays.asList("a", "b", "c"));
        Collection<String> empty = Arrays.asList();
        boolean result = list.removeAll(empty);
        if (result != false) {
            throw new AssertionError("removeAll with empty collection should return false");
        }
        if (list.size() != 3) {
            throw new AssertionError("List should remain unchanged after removeAll with empty collection");
        }
    }

    static void testRemoveAllWithNonEmptyCollection() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.addAll(Arrays.asList("a", "b", "c"));
        Collection<String> toRemove = Arrays.asList("b", "c", "d");
        boolean result = list.removeAll(toRemove);
        if (result != true) {
            throw new AssertionError("removeAll should return true when elements are removed");
        }
        if (list.size() != 1 || !list.contains("a")) {
            throw new AssertionError("List should contain only 'a' after removal");
        }
    }
}
