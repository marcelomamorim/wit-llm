/*
 * @test
 * @summary Test WeakHashMap(Map<? extends K, ? extends V>) constructor behavior
 * @run main WeakHashMapWitupTest
 */

import java.util.Map;
import java.util.WeakHashMap;
import java.util.HashMap;

public class WeakHashMapWitupTest {
    public static void main(String[] args) {
        testEmptyMap();
        testNonEmptyMap();
        testNullKeyAndValue();
    }

    private static void testEmptyMap() {
        Map<String, String> empty = new HashMap<>();
        WeakHashMap<String, String> whm = new WeakHashMap<>(empty);
        if (whm.size() != 0) {
            throw new AssertionError("Expected size 0 for empty map constructor");
        }
    }

    private static void testNonEmptyMap() {
        Map<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        WeakHashMap<String, String> whm = new WeakHashMap<>(map);
        if (whm.size() != 2) {
            throw new AssertionError("Expected size 2 after construction from map");
        }
        if (!"value1".equals(whm.get("key1"))) {
            throw new AssertionError("Expected value1 for key1");
        }
        if (!"value2".equals(whm.get("key2"))) {
            throw new AssertionError("Expected value2 for key2");
        }
    }

    private static void testNullKeyAndValue() {
        Map<String, String> map = new HashMap<>();
        map.put(null, null);
        WeakHashMap<String, String> whm = new WeakHashMap<>(map);
        if (whm.size() != 1) {
            throw new AssertionError("Expected size 1 for map with null key and value");
        }
        if (whm.get(null) != null) {
            throw new AssertionError("Expected null value for null key");
        }
    }
}
