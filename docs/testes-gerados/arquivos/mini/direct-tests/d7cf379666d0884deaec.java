/*
 * @test
 * @summary Test java.util.ArrayList.retainAll(Collection<?>) method
 * @run main java.util.ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testRetainAllWithEmptyCollection();
        testRetainAllWithFullCollection();
        testRetainAllWithPartialCollection();
        testRetainAllWithNullElements();
    }

    private static void testRetainAllWithEmptyCollection() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        boolean changed = list.retainAll(Collections.emptyList());

        if (!changed) {
            throw new RuntimeException("Expected retainAll to return true when removing all elements");
        }
        if (!list.isEmpty()) {
            throw new RuntimeException("Expected list to be empty after retainAll with empty collection");
        }
    }

    private static void testRetainAllWithFullCollection() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        Collection<String> c = new ArrayList<>();
        c.add("a");
        c.add("b");
        c.add("c");

        boolean changed = list.retainAll(c);

        if (changed) {
            throw new RuntimeException("Expected retainAll to return false when no elements removed");
        }
        if (list.size() != 3 || !list.contains("a") || !list.contains("b") || !list.contains("c")) {
            throw new RuntimeException("Expected list to remain unchanged after retainAll with full collection");
        }
    }

    private static void testRetainAllWithPartialCollection() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");

        Collection<String> c = new ArrayList<>();
        c.add("b");
        c.add("d");

        boolean changed = list.retainAll(c);

        if (!changed) {
            throw new RuntimeException("Expected retainAll to return true when some elements removed");
        }
        if (list.size() != 2 || !list.contains("b") || !list.contains("d")) {
            throw new RuntimeException("Expected list to contain only elements in the collection after retainAll");
        }
    }

    private static void testRetainAllWithNullElements() {
        ArrayList<String> list = new ArrayList<>();
        list.add(null);
        list.add("a");
        list.add(null);
        list.add("b");

        Collection<String> c = new ArrayList<>();
        c.add(null);

        boolean changed = list.retainAll(c);

        if (!changed) {
            throw new RuntimeException("Expected retainAll to return true when some elements removed");
        }
        if (list.size() != 2) {
            throw new RuntimeException("Expected list to contain only null elements after retainAll");
        }
        for (String s : list) {
            if (s != null) {
                throw new RuntimeException("Expected only null elements in list after retainAll");
            }
        }
    }
}
