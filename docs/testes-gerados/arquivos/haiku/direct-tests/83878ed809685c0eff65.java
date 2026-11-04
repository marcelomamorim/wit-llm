/*
 * @test
 * @summary Verify Iterator.remove() of TreeMap views removes the last returned element and rejects invalid calls
 * @run main TreeMapWitupTest
 */

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        entryIteratorRemoveAfterNext();
        keyIteratorRemoveAfterNext();
        valueIteratorRemoveAfterNext();
        removeBeforeNextThrows();
        removeTwiceThrows();
        removeAllEntriesEmptiesMap();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static TreeMap<Integer, String> sample() {
        TreeMap<Integer, String> m = new TreeMap<>();
        m.put(3, "c");
        m.put(1, "a");
        m.put(2, "b");
        return m;
    }

    static void entryIteratorRemoveAfterNext() {
        TreeMap<Integer, String> m = sample();
        Iterator<Map.Entry<Integer, String>> it = m.entrySet().iterator();
        Map.Entry<Integer, String> first = it.next();
        check(first.getKey() == 1, "first key should be 1");
        it.remove();
        check(!m.containsKey(1), "key 1 should be removed");
        check(m.size() == 2, "size should be 2 after remove");
        check(it.hasNext(), "iterator should still have elements");
        check(it.next().getKey() == 2, "next key should be 2");
        check(m.firstKey() == 2, "first key of map should be 2");
    }

    static void keyIteratorRemoveAfterNext() {
        TreeMap<Integer, String> m = sample();
        Iterator<Integer> it = m.keySet().iterator();
        check(it.next() == 1, "first key should be 1");
        it.remove();
        check(!m.containsKey(1), "key 1 should be removed via keySet");
        check(it.next() == 2, "second key should be 2");
        it.remove();
        check(!m.containsKey(2), "key 2 should be removed via keySet");
        check(m.size() == 1, "size should be 1");
        check(m.containsKey(3), "key 3 should remain");
    }

    static void valueIteratorRemoveAfterNext() {
        TreeMap<Integer, String> m = sample();
        Iterator<String> it = m.values().iterator();
        check("a".equals(it.next()), "first value should be a");
        it.remove();
        check(!m.containsValue("a"), "value a should be removed");
        check(m.size() == 2, "size should be 2");
    }

    static void removeBeforeNextThrows() {
        TreeMap<Integer, String> m = sample();
        Iterator<Integer> it = m.keySet().iterator();
        boolean thrown = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "remove before next should throw IllegalStateException");
        check(m.size() == 3, "map should be unchanged");
    }

    static void removeTwiceThrows() {
        TreeMap<Integer, String> m = sample();
        Iterator<Integer> it = m.keySet().iterator();
        it.next();
        it.remove();
        boolean thrown = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "second remove without next should throw IllegalStateException");
        check(m.size() == 2, "size should be 2 after single effective removal");
    }

    static void removeAllEntriesEmptiesMap() {
        TreeMap<Integer, String> m = sample();
        Iterator<Map.Entry<Integer, String>> it = m.entrySet().iterator();
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
        check(m.isEmpty(), "map should be empty after removing all entries");
    }
}
