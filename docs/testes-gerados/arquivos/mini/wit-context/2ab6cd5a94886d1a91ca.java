/*
 * @test
 * @summary Test ConcurrentHashMap(Map<? extends K, ? extends V> m) constructor behavior
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.HashMap;

public class ConcurrentHashMapWitupTest {
    public static void main(String[] args) {
        testConstructorWithNullMap();
        testConstructorWithEmptyMap();
        testConstructorWithNonEmptyMap();
    }

    private static void testConstructorWithNullMap() {
        try {
            new ConcurrentHashMap(null);
            throw new RuntimeException("Expected NullPointerException for null map argument");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testConstructorWithEmptyMap() {
        Map<String, String> emptyMap = new HashMap<>();
        ConcurrentHashMap<String, String> chm = new ConcurrentHashMap<>(emptyMap);
        if (chm.size() != 0) {
            throw new RuntimeException("Expected empty ConcurrentHashMap for empty input map");
        }
    }

    private static void testConstructorWithNonEmptyMap() {
        Map<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        ConcurrentHashMap<String, String> chm = new ConcurrentHashMap<>(map);
        if (chm.size() != 2) {
            throw new RuntimeException("Expected ConcurrentHashMap size 2 for input map with 2 entries");
        }
        if (!"value1".equals(chm.get("key1"))) {
            throw new RuntimeException("Expected value1 for key1");
        }
        if (!"value2".equals(chm.get("key2"))) {
            throw new RuntimeException("Expected value2 for key2");
        }
    }
}
