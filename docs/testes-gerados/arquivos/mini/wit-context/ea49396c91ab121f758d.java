/*
 * @test
 * @summary Test ConcurrentHashMap.removeIf(Predicate) for null filter and basic behavior
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

public class ConcurrentHashMapWitupTest {
    public static void main(String[] args) {
        testRemoveIfNullFilter();
        testRemoveIfBasic();
    }

    private static void testRemoveIfNullFilter() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.removeIf(null);
            throw new RuntimeException("Expected NullPointerException for null filter");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testRemoveIfBasic() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");

        // Remove entries with key starting with "key2"
        boolean removed = map.removeIf(entry -> entry.getKey().equals("key2"));
        if (!removed) {
            throw new RuntimeException("Expected removeIf to remove matching entries");
        }

        if (map.containsKey("key2")) {
            throw new RuntimeException("Entry with key2 should have been removed");
        }

        // Remove entries with key starting with "nonexistent" - should return false
        boolean removedNone = map.removeIf(entry -> entry.getKey().startsWith("nonexistent"));
        if (removedNone) {
            throw new RuntimeException("Expected removeIf to return false when no entries removed");
        }

        // Remove all entries
        boolean removedAll = map.removeIf(entry -> true);
        if (!removedAll) {
            throw new RuntimeException("Expected removeIf to remove all entries");
        }

        if (!map.isEmpty()) {
            throw new RuntimeException("Map should be empty after removing all entries");
        }
    }
}
