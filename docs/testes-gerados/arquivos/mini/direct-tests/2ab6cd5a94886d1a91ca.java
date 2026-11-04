/*
 * @test
 * @summary Test ConcurrentHashMap(Map) constructor with various map inputs
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {
    public static void main(String[] args) {
        testEmptyMap();
        testSingleEntryMap();
        testMultipleEntriesMap();
        testNullMapThrowsNPE();
    }

    private static void testEmptyMap() {
        Map<String, String> empty = new HashMap<>();
        ConcurrentHashMap<String, String> chm = new ConcurrentHashMap<>(empty);
        if (!chm.isEmpty()) {
            throw new AssertionError("Expected empty ConcurrentHashMap from empty map");
        }
    }

    private static void testSingleEntryMap() {
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");
        ConcurrentHashMap<String, String> chm = new ConcurrentHashMap<>(map);
        if (!"value".equals(chm.get("key"))) {
            throw new AssertionError("Expected value 'value' for key 'key'");
        }
    }

    private static void testMultipleEntriesMap() {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            map.put(i, i * i);
        }
        ConcurrentHashMap<Integer, Integer> chm = new ConcurrentHashMap<>(map);
        for (int i = 0; i < 10; i++) {
            Integer val = chm.get(i);
            if (val == null || val != i * i) {
                throw new AssertionError("Expected value " + (i * i) + " for key " + i);
            }
        }
    }

    private static void testNullMapThrowsNPE() {
        try {
            new ConcurrentHashMap<String, String>(null);
            throw new AssertionError("Expected NullPointerException for null map");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
