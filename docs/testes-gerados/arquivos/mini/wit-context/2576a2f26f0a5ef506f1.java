/*
 * @test
 * @summary Test ConcurrentSkipListMap.subMap(K fromKey, K toKey) for null argument handling and basic behavior
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(3, "three");
        map.put(5, "five");

        // Test normal subMap call with valid keys
        ConcurrentSkipListMap.SubMap<Integer, String> subMap = map.subMap(1, 5);
        if (subMap == null) {
            throw new AssertionError("subMap returned null for valid keys");
        }
        // Check that subMap contains expected keys
        if (!subMap.containsKey(1) || !subMap.containsKey(3) || subMap.containsKey(5)) {
            throw new AssertionError("subMap keys unexpected");
        }

        // Test subMap with fromKey == toKey (should be empty)
        ConcurrentSkipListMap.SubMap<Integer, String> emptySubMap = map.subMap(3, 3);
        if (!emptySubMap.isEmpty()) {
            throw new AssertionError("subMap with equal fromKey and toKey should be empty");
        }

        // Test null fromKey triggers NullPointerException
        try {
            map.subMap(null, 3);
            throw new AssertionError("Expected NullPointerException for null fromKey");
        } catch (NullPointerException expected) {
        }

        // Test null toKey triggers NullPointerException
        try {
            map.subMap(1, null);
            throw new AssertionError("Expected NullPointerException for null toKey");
        } catch (NullPointerException expected) {
        }

        // Test both null keys triggers NullPointerException
        try {
            map.subMap(null, null);
            throw new AssertionError("Expected NullPointerException for null fromKey and toKey");
        } catch (NullPointerException expected) {
        }
    }
}
