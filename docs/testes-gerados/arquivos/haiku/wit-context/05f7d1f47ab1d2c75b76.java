/*
 * @test
 * @summary Verifies ConcurrentHashMap.values().removeIf (ValuesView.removeIf) null-filter NPE and observable removal behavior
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testNullFilterThrowsNPE();
        testRemovesMatchingValues();
        testNoMatchReturnsFalse();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullFilterThrowsNPE() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        Collection<Integer> values = map.values();
        boolean thrown = false;
        try {
            values.removeIf(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "removeIf(null) must throw NullPointerException");
        check(map.size() == 2, "map must be unchanged after NPE");
        check(map.get("a") == 1 && map.get("b") == 2, "entries must be unchanged after NPE");
    }

    static void testRemovesMatchingValues() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 2);
        map.put("d", 3);
        boolean changed = map.values().removeIf(v -> v.intValue() == 2);
        check(changed, "removeIf must return true when elements are removed");
        check(map.size() == 2, "two matching entries must be removed");
        check(!map.containsKey("b") && !map.containsKey("c"), "matching keys must be absent");
        check(map.get("a") == 1 && map.get("d") == 3, "non-matching entries must remain");
    }

    static void testNoMatchReturnsFalse() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        boolean changed = map.values().removeIf(v -> v.intValue() > 100);
        check(!changed, "removeIf must return false when nothing matches");
        check(map.size() == 2, "map size must be unchanged when nothing matches");
    }
}
