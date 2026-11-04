/*
 * @test
 * @summary Verifies ConcurrentHashMap.entrySet().removeIf(Predicate) removes only entries matching the predicate and reports whether anything was removed
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testRemovesOnlyMatchingEntries();
        testRemovesByKeyPredicate();
        testNoMatchReturnsFalse();
        testEmptyMapReturnsFalse();
        testRemoveAllMatchingReturnsTrueAndEmpties();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testRemovesOnlyMatchingEntries() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        boolean removed = map.entrySet().removeIf(e -> e.getValue() % 2 == 1);
        check(removed, "removeIf should return true when entries were removed");
        check(map.size() == 1, "expected 1 remaining entry but was " + map.size());
        check(map.containsKey("b"), "entry b should remain");
        check(!map.containsKey("a"), "entry a should be removed");
        check(!map.containsKey("c"), "entry c should be removed");
        check(map.get("b") == 2, "value of b should be unchanged");
    }

    static void testRemovesByKeyPredicate() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("x", 10);
        map.put("y", 20);
        boolean removed = map.entrySet().removeIf(e -> e.getKey().equals("y"));
        check(removed, "removeIf by key should return true");
        check(map.size() == 1, "expected size 1 but was " + map.size());
        check(map.get("x") == 10, "entry x should remain with value 10");
        check(!map.containsKey("y"), "entry y should be removed");
    }

    static void testNoMatchReturnsFalse() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 2);
        boolean removed = map.entrySet().removeIf(e -> e.getValue() > 10);
        check(!removed, "removeIf should return false when nothing matches");
        check(map.size() == 1, "map should be unchanged when nothing matches");
        check(map.get("a") == 2, "entry a should be unchanged");
    }

    static void testEmptyMapReturnsFalse() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        boolean removed = map.entrySet().removeIf(e -> true);
        check(!removed, "removeIf on empty map should return false");
        check(map.isEmpty(), "empty map should remain empty");
    }

    static void testRemoveAllMatchingReturnsTrueAndEmpties() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("p", 1);
        map.put("q", 2);
        boolean removed = map.entrySet().removeIf(e -> true);
        check(removed, "removeIf matching all entries should return true");
        check(map.isEmpty(), "map should be empty after removing all entries");
    }
}
