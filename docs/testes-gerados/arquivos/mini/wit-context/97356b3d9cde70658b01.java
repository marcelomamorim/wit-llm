/*
 * @test
 * @summary Test ConcurrentSkipListMap.removeIf(Predicate) behavior including null filter and removal correctness
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.Predicate;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        testRemoveIfNullFilter();
        testRemoveIfRemovesMatchingEntries();
        testRemoveIfNoMatchReturnsFalse();
    }

    private static void testRemoveIfNullFilter() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        try {
            map.entrySet().removeIf(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testRemoveIfRemovesMatchingEntries() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("a", "1");
        map.put("b", "2");
        map.put("c", "3");

        // Remove entries with key equals "b" or value equals "3"
        boolean removed = map.entrySet().removeIf(new Predicate<Map.Entry<String, String>>() {
            public boolean test(Map.Entry<String, String> e) {
                return "b".equals(e.getKey()) || "3".equals(e.getValue());
            }
        });

        if (!removed) {
            throw new RuntimeException("Expected entries to be removed but removeIf returned false");
        }

        if (map.containsKey("b") || map.containsKey("c")) {
            throw new RuntimeException("Entries matching predicate were not removed");
        }

        if (!map.containsKey("a")) {
            throw new RuntimeException("Entry not matching predicate was incorrectly removed");
        }
    }

    private static void testRemoveIfNoMatchReturnsFalse() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("x", "10");
        map.put("y", "20");

        // Predicate that matches no entries
        boolean removed = map.entrySet().removeIf(new Predicate<Map.Entry<String, String>>() {
            public boolean test(Map.Entry<String, String> e) {
                return false;
            }
        });

        if (removed) {
            throw new RuntimeException("Expected removeIf to return false when no entries removed");
        }

        if (!map.containsKey("x") || !map.containsKey("y")) {
            throw new RuntimeException("Entries were incorrectly removed when predicate matched none");
        }
    }
}
