/*
 * @test
 * @summary Test removeIf method of ConcurrentHashMap for correct behavior
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.function.Predicate;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testRemoveIfRemovesMatchingEntries();
        testRemoveIfNoEntriesMatch();
        testRemoveIfAllEntriesMatch();
        testRemoveIfEmptyMap();
    }

    static void testRemoveIfRemovesMatchingEntries() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        Predicate<Map.Entry<String, Integer>> predicate = entry -> entry.getValue() % 2 == 0;
        boolean result = map.removeIf(predicate);
        assert result : "Expected removeIf to return true when entries are removed";
        assert map.size() == 2 : "Expected size 2 after removal";
        assert !map.containsKey("b") : "Key 'b' should be removed";
        assert map.containsKey("a") : "Key 'a' should remain";
        assert map.containsKey("c") : "Key 'c' should remain";
    }

    static void testRemoveIfNoEntriesMatch() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("x", 10);
        map.put("y", 20);
        Predicate<Map.Entry<String, Integer>> predicate = entry -> false;
        boolean result = map.removeIf(predicate);
        assert !result : "Expected removeIf to return false when no entries are removed";
        assert map.size() == 2 : "Map size should remain 2";
        assert map.containsKey("x") : "Key 'x' should remain";
        assert map.containsKey("y") : "Key 'y' should remain";
    }

    static void testRemoveIfAllEntriesMatch() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("m", 5);
        map.put("n", 7);
        Predicate<Map.Entry<String, Integer>> predicate = entry -> true;
        boolean result = map.removeIf(predicate);
        assert result : "Expected removeIf to return true when all entries are removed";
        assert map.isEmpty() : "Map should be empty after removal";
    }

    static void testRemoveIfEmptyMap() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        Predicate<Map.Entry<String, Integer>> predicate = entry -> true;
        boolean result = map.removeIf(predicate);
        assert !result : "Expected removeIf to return false on empty map";
        assert map.isEmpty() : "Map should remain empty";
    }
}
