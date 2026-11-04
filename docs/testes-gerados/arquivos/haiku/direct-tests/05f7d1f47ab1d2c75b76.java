/*
 * @test
 * @summary Verifies ConcurrentHashMap.values().removeIf(Predicate) removes matching entries and reports changes
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testRemovesMatchingValues();
        testNoMatchReturnsFalseAndKeepsMap();
        testEmptyMapReturnsFalse();
        testAllValuesRemoved();
        testPredicateOnValuesOnly();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static ConcurrentHashMap<String, Integer> populated() {
        ConcurrentHashMap<String, Integer> m = new ConcurrentHashMap<>();
        for (int i = 1; i <= 5; i++) {
            m.put("k" + i, i);
        }
        return m;
    }

    static void testRemovesMatchingValues() {
        ConcurrentHashMap<String, Integer> m = populated();
        Predicate<Integer> even = v -> v % 2 == 0;
        boolean changed = m.values().removeIf(even);
        check(changed, "removeIf should return true when entries are removed");
        check(m.size() == 3, "expected 3 entries after removing evens, got " + m.size());
        check(m.get("k2") == null, "k2 (value 2) should be removed");
        check(m.get("k4") == null, "k4 (value 4) should be removed");
        check(m.get("k1") == 1, "k1 should remain");
        check(m.get("k3") == 3, "k3 should remain");
        check(m.get("k5") == 5, "k5 should remain");
    }

    static void testNoMatchReturnsFalseAndKeepsMap() {
        ConcurrentHashMap<String, Integer> m = populated();
        boolean changed = m.values().removeIf(v -> v > 100);
        check(!changed, "removeIf should return false when nothing matches");
        check(m.size() == 5, "map size should be unchanged, got " + m.size());
        for (int i = 1; i <= 5; i++) {
            check(m.get("k" + i) == i, "entry k" + i + " should be unchanged");
        }
    }

    static void testEmptyMapReturnsFalse() {
        ConcurrentHashMap<String, Integer> m = new ConcurrentHashMap<>();
        boolean changed = m.values().removeIf(v -> true);
        check(!changed, "removeIf on empty map should return false");
        check(m.isEmpty(), "empty map should remain empty");
    }

    static void testAllValuesRemoved() {
        ConcurrentHashMap<String, Integer> m = populated();
        boolean changed = m.values().removeIf(v -> true);
        check(changed, "removeIf should return true when all entries are removed");
        check(m.isEmpty(), "map should be empty after removing all values");
    }

    static void testPredicateOnValuesOnly() {
        ConcurrentHashMap<String, Integer> m = new ConcurrentHashMap<>();
        m.put("a", 10);
        m.put("b", 20);
        boolean changed = m.values().removeIf(v -> v == 20);
        check(changed, "removeIf should remove the value 20");
        check(m.size() == 1, "expected one remaining entry");
        check(m.get("a") == 10, "entry a should remain");
        check(!m.containsKey("b"), "entry b should be removed");
    }
}
