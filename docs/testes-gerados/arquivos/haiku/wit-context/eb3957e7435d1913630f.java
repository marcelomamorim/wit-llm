/*
 * @test
 * @summary Tests retainAll null handling and observable behaviour through ConcurrentHashMap key, value and entry views
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testKeySetRetainAllNullThrowsNPE();
        testValuesRetainAllNullThrowsNPE();
        testEntrySetRetainAllNullThrowsNPE();
        testRetainAllAllKeysReturnsFalse();
        testRetainAllSubsetRemovesOthers();
        testRetainAllEmptyCollectionClearsMap();
        testRetainAllOnEmptyMapReturnsFalse();
        testValuesRetainAllRemovesEntriesByValue();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static ConcurrentHashMap<Integer, Integer> newMap() {
        ConcurrentHashMap<Integer, Integer> m = new ConcurrentHashMap<>();
        m.put(1, 10);
        m.put(2, 20);
        m.put(3, 30);
        return m;
    }

    static void testKeySetRetainAllNullThrowsNPE() {
        ConcurrentHashMap<Integer, Integer> m = newMap();
        boolean thrown = false;
        try {
            m.keySet().retainAll(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "keySet().retainAll(null) must throw NullPointerException");
        check(m.size() == 3, "map must be unchanged after NullPointerException");
    }

    static void testValuesRetainAllNullThrowsNPE() {
        ConcurrentHashMap<Integer, Integer> m = newMap();
        boolean thrown = false;
        try {
            m.values().retainAll(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "values().retainAll(null) must throw NullPointerException");
        check(m.size() == 3, "map must be unchanged after NullPointerException");
    }

    static void testEntrySetRetainAllNullThrowsNPE() {
        ConcurrentHashMap<Integer, Integer> m = newMap();
        boolean thrown = false;
        try {
            m.entrySet().retainAll(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "entrySet().retainAll(null) must throw NullPointerException");
        check(m.size() == 3, "map must be unchanged after NullPointerException");
    }

    static void testRetainAllAllKeysReturnsFalse() {
        ConcurrentHashMap<Integer, Integer> m = newMap();
        Collection<Integer> keep = Arrays.asList(1, 2, 3, 4);
        boolean modified = m.keySet().retainAll(keep);
        check(!modified, "retainAll with superset of keys must return false");
        check(m.size() == 3, "map size must remain 3");
    }

    static void testRetainAllSubsetRemovesOthers() {
        ConcurrentHashMap<Integer, Integer> m = newMap();
        Collection<Integer> keep = Arrays.asList(1, 2);
        boolean modified = m.keySet().retainAll(keep);
        check(modified, "retainAll removing a key must return true");
        check(m.size() == 2, "map size must be 2");
        check(m.containsKey(1), "key 1 must be retained");
        check(m.containsKey(2), "key 2 must be retained");
        check(!m.containsKey(3), "key 3 must be removed");
    }

    static void testRetainAllEmptyCollectionClearsMap() {
        ConcurrentHashMap<Integer, Integer> m = newMap();
        Collection<Integer> keep = new ArrayList<>();
        boolean modified = m.keySet().retainAll(keep);
        check(modified, "retainAll with empty collection must return true for non-empty map");
        check(m.isEmpty(), "map must be empty after retaining nothing");
    }

    static void testRetainAllOnEmptyMapReturnsFalse() {
        ConcurrentHashMap<Integer, Integer> m = new ConcurrentHashMap<>();
        boolean modified = m.keySet().retainAll(Arrays.asList(1, 2));
        check(!modified, "retainAll on empty map must return false");
        check(m.isEmpty(), "empty map must remain empty");
    }

    static void testValuesRetainAllRemovesEntriesByValue() {
        ConcurrentHashMap<Integer, Integer> m = newMap();
        boolean modified = m.values().retainAll(Arrays.asList(20));
        check(modified, "values().retainAll removing entries must return true");
        check(m.size() == 1, "map size must be 1");
        check(m.containsKey(2), "entry with value 20 must be retained");
        check(m.get(2) == 20, "retained entry must keep value 20");
    }
}
