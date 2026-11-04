/*
 * @test
 * @summary TreeMap iterator remove() behavior observable through public iterators
 * @run main TreeMapWitupTest
 */
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        testRemoveWithoutNext();
        testRemoveTwice();
        testRemoveAfterStructuralModification();
        testRemoveAllThroughKeyIterator();
        testEntryIteratorRemove();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static TreeMap<String, Integer> sample() {
        TreeMap<String, Integer> m = new TreeMap<>();
        m.put("a", 1);
        m.put("b", 2);
        m.put("c", 3);
        return m;
    }

    static void testRemoveWithoutNext() {
        Iterator<String> it = sample().keySet().iterator();
        try {
            it.remove();
            throw new RuntimeException("expected IllegalStateException");
        } catch (IllegalStateException expected) {
        }
    }

    static void testRemoveTwice() {
        TreeMap<String, Integer> m = sample();
        Iterator<String> it = m.keySet().iterator();
        check(it.next().equals("a"), "first key should be a");
        it.remove();
        try {
            it.remove();
            throw new RuntimeException("expected IllegalStateException on second remove");
        } catch (IllegalStateException expected) {
        }
        check(!m.containsKey("a"), "a should have been removed once");
        check(m.size() == 2, "size should be 2");
    }

    static void testRemoveAfterStructuralModification() {
        TreeMap<String, Integer> m = sample();
        Iterator<String> it = m.keySet().iterator();
        it.next();
        m.put("z", 26);
        try {
            it.remove();
            throw new RuntimeException("expected ConcurrentModificationException");
        } catch (ConcurrentModificationException expected) {
        }
    }

    static void testRemoveAllThroughKeyIterator() {
        TreeMap<String, Integer> m = sample();
        Iterator<String> it = m.keySet().iterator();
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
        check(m.isEmpty(), "map should be empty after removing all keys");
        check(m.size() == 0, "size should be 0");
    }

    static void testEntryIteratorRemove() {
        TreeMap<String, Integer> m = sample();
        Iterator<Map.Entry<String, Integer>> it = m.entrySet().iterator();
        Map.Entry<String, Integer> first = it.next();
        check(first.getKey().equals("a"), "first entry key should be a");
        it.remove();
        check(!m.containsKey("a"), "entry a should be removed");
        check(m.size() == 2, "size should be 2 after entry removal");
        check(m.firstKey().equals("b"), "b should now be the first key");
    }
}
