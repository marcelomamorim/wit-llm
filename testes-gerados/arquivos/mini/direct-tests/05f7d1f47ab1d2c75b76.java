/*
 * @test
 * @summary Test ConcurrentHashMap removeIf method behavior
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

public class ConcurrentHashMapWitupTest {
    public static void main(String[] args) {
        testRemoveIfRemovesMatchingValues();
        testRemoveIfReturnsFalseWhenNoMatch();
        testRemoveIfOnEmptyMapReturnsFalse();
    }

    private static void testRemoveIfRemovesMatchingValues() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");

        // Remove values that contain '2'
        boolean removed = map.removeIf(new Predicate<String>() {
            public boolean test(String v) {
                return v.contains("2");
            }
        });

        if (!removed) {
            throw new RuntimeException("Expected removeIf to return true when elements removed");
        }

        if (map.containsKey("key2")) {
            throw new RuntimeException("Expected key2 to be removed");
        }

        if (!map.containsKey("key1") || !map.containsKey("key3")) {
            throw new RuntimeException("Expected keys key1 and key3 to remain");
        }
    }

    private static void testRemoveIfReturnsFalseWhenNoMatch() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");

        // Predicate that matches no values
        boolean removed = map.removeIf(new Predicate<String>() {
            public boolean test(String v) {
                return v.equals("nonexistent");
            }
        });

        if (removed) {
            throw new RuntimeException("Expected removeIf to return false when no elements removed");
        }

        if (!map.containsKey("key1")) {
            throw new RuntimeException("Expected key1 to remain");
        }
    }

    private static void testRemoveIfOnEmptyMapReturnsFalse() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();

        boolean removed = map.removeIf(new Predicate<String>() {
            public boolean test(String v) {
                return true;
            }
        });

        if (removed) {
            throw new RuntimeException("Expected removeIf to return false on empty map");
        }
    }
}
