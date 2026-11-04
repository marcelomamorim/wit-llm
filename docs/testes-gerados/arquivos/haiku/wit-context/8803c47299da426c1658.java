/*
 * @test
 * @summary Verify ConcurrentHashMap entrySet().remove(Object) guards for non-entry, null key/value and mismatched entries
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.AbstractMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testRemoveMatchingEntry();
        testRemoveWrongValue();
        testRemoveMissingKey();
        testRemoveNonEntry();
        testRemoveNullObject();
        testRemoveNullKeyEntry();
        testRemoveNullValueEntry();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static ConcurrentHashMap<String, Integer> newMap() {
        ConcurrentHashMap<String, Integer> m = new ConcurrentHashMap<>();
        m.put("a", 1);
        return m;
    }

    static void testRemoveMatchingEntry() {
        ConcurrentHashMap<String, Integer> m = newMap();
        boolean removed = m.entrySet().remove(new AbstractMap.SimpleEntry<String, Integer>("a", 1));
        check(removed, "matching entry should be removed");
        check(!m.containsKey("a"), "key should be gone after removal");
        check(m.size() == 0, "map should be empty");
    }

    static void testRemoveWrongValue() {
        ConcurrentHashMap<String, Integer> m = newMap();
        boolean removed = m.entrySet().remove(new AbstractMap.SimpleEntry<String, Integer>("a", 2));
        check(!removed, "entry with wrong value must not be removed");
        check(m.get("a") == 1, "original mapping must be preserved");
    }

    static void testRemoveMissingKey() {
        ConcurrentHashMap<String, Integer> m = newMap();
        boolean removed = m.entrySet().remove(new AbstractMap.SimpleEntry<String, Integer>("b", 1));
        check(!removed, "entry with absent key must not be removed");
        check(m.size() == 1, "map size must be unchanged");
    }

    static void testRemoveNonEntry() {
        ConcurrentHashMap<String, Integer> m = newMap();
        boolean removed = m.entrySet().remove("a");
        check(!removed, "non-Map.Entry object must not be removed");
        check(m.get("a") == 1, "mapping must be preserved");
    }

    static void testRemoveNullObject() {
        ConcurrentHashMap<String, Integer> m = newMap();
        boolean removed = m.entrySet().remove(null);
        check(!removed, "null argument must not be removed");
        check(m.size() == 1, "map size must be unchanged");
    }

    static void testRemoveNullKeyEntry() {
        ConcurrentHashMap<String, Integer> m = newMap();
        Map.Entry<String, Integer> e = new AbstractMap.SimpleEntry<String, Integer>(null, 1);
        boolean removed = m.entrySet().remove(e);
        check(!removed, "entry with null key must not be removed");
        check(m.size() == 1, "map size must be unchanged");
    }

    static void testRemoveNullValueEntry() {
        ConcurrentHashMap<String, Integer> m = newMap();
        Map.Entry<String, Integer> e = new AbstractMap.SimpleEntry<String, Integer>("a", null);
        boolean removed = m.entrySet().remove(e);
        check(!removed, "entry with null value must not be removed");
        check(m.get("a") == 1, "mapping must be preserved");
    }
}
