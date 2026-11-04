/*
 * @test
 * @summary Test ConcurrentHashMap.removeIf(Predicate) for null filter and basic removal
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

public class ConcurrentHashMapWitupTest {
    public static void main(String[] args) {
        testRemoveIfNullFilter();
        testRemoveIfBasic();
    }

    private static void testRemoveIfNullFilter() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        boolean npeThrown = false;
        try {
            map.removeIf(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when filter is null");
        }
    }

    private static void testRemoveIfBasic() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");

        // Remove entries with values containing '2'
        boolean removed = map.removeIf(new Predicate<String>() {
            public boolean test(String v) {
                return v.contains("2");
            }
        });

        if (!removed) {
            throw new AssertionError("Expected removeIf to return true when entries removed");
        }

        if (map.containsKey("key2")) {
            throw new AssertionError("Entry with key2 should have been removed");
        }

        if (!map.containsKey("key1") || !map.containsKey("key3")) {
            throw new AssertionError("Entries with key1 and key3 should remain");
        }

        // Remove entries with values containing 'x' (none)
        boolean removedNone = map.removeIf(new Predicate<String>() {
            public boolean test(String v) {
                return v.contains("x");
            }
        });

        if (removedNone) {
            throw new AssertionError("Expected removeIf to return false when no entries removed");
        }
    }
}
