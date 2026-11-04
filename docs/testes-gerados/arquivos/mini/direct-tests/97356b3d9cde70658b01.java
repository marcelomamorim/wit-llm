/*
 * @test
 * @summary Test ConcurrentSkipListMap removeIf method behavior
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.Predicate;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        testRemoveIfWithNullPredicate();
        testRemoveIfWithEmptyMap();
        testRemoveIfRemovesMatchingEntries();
        testRemoveIfReturnsFalseWhenNoRemoval();
    }

    static void testRemoveIfWithNullPredicate() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        try {
            map.removeIf(null);
            throw new AssertionError("Expected NullPointerException for null predicate");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testRemoveIfWithEmptyMap() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        boolean removed = map.removeIf(e -> true);
        if (removed) {
            throw new AssertionError("removeIf should return false when map is empty");
        }
    }

    static void testRemoveIfRemovesMatchingEntries() {
        ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);

        Predicate<Map.Entry<String, Integer>> filter = e -> e.getValue() % 2 == 1; // remove odd values
        boolean removed = map.removeIf(filter);

        if (!removed) {
            throw new AssertionError("removeIf should return true when entries are removed");
        }
        if (map.containsKey("one") || map.containsKey("three")) {
            throw new AssertionError("Entries with odd values should be removed");
        }
        if (!map.containsKey("two")) {
            throw new AssertionError("Entry with even value should remain");
        }
    }

    static void testRemoveIfReturnsFalseWhenNoRemoval() {
        ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();
        map.put("one", 1);
        map.put("two", 2);

        Predicate<Map.Entry<String, Integer>> filter = e -> false; // remove none
        boolean removed = map.removeIf(filter);

        if (removed) {
            throw new AssertionError("removeIf should return false when no entries removed");
        }
        if (!map.containsKey("one") || !map.containsKey("two")) {
            throw new AssertionError("No entries should be removed");
        }
    }
}
