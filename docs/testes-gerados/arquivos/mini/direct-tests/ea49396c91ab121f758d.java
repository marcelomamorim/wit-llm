/*
 * @test
 * @summary Test ConcurrentHashMap removeIf method behavior
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.function.Predicate;

public class ConcurrentHashMapWitupTest {
    public static void main(String[] args) {
        testRemoveIfRemovesMatchingEntries();
        testRemoveIfWithEmptyMapReturnsFalse();
        testRemoveIfWithNoMatchingEntriesReturnsFalse();
    }

    private static void testRemoveIfRemovesMatchingEntries() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("one", "1");
        map.put("two", "2");
        map.put("three", "3");

        // Remove entries where key length is 3
        boolean removed = map.removeIf(entry -> entry.getKey().length() == 3);

        if (!removed) {
            throw new AssertionError("Expected removeIf to return true when entries are removed");
        }

        if (map.containsKey("one") || map.containsKey("two")) {
            throw new AssertionError("Entries with key length 3 should be removed");
        }

        if (!map.containsKey("three")) {
            throw new AssertionError("Entry with key length != 3 should remain");
        }
    }

    private static void testRemoveIfWithEmptyMapReturnsFalse() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();

        boolean removed = map.removeIf(entry -> true);

        if (removed) {
            throw new AssertionError("Expected removeIf to return false when map is empty");
        }
    }

    private static void testRemoveIfWithNoMatchingEntriesReturnsFalse() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("one", "1");
        map.put("two", "2");

        // Predicate that matches no entries
        boolean removed = map.removeIf(entry -> false);

        if (removed) {
            throw new AssertionError("Expected removeIf to return false when no entries match");
        }

        if (map.size() != 2) {
            throw new AssertionError("No entries should be removed");
        }
    }
}
