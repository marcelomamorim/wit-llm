/*
 * @test
 * @summary Test java.util.Hashtable(Map<? extends K, ? extends V> t) constructor behavior
 * @run main HashtableWitupTest
 */

import java.util.Hashtable;
import java.util.Map;
import java.util.HashMap;

public class HashtableWitupTest {
    public static void main(String[] args) {
        testConstructorWithValidMap();
        testConstructorWithEmptyMap();
    }

    private static void testConstructorWithValidMap() {
        Map<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");

        Hashtable<String, String> ht = new Hashtable<>(map);

        // Check that all entries are present
        if (!"value1".equals(ht.get("key1"))) {
            throw new RuntimeException("Failed: key1 mapping incorrect");
        }
        if (!"value2".equals(ht.get("key2"))) {
            throw new RuntimeException("Failed: key2 mapping incorrect");
        }

        // Check size
        if (ht.size() != 2) {
            throw new RuntimeException("Failed: size should be 2");
        }
    }

    private static void testConstructorWithEmptyMap() {
        Map<Object, Object> emptyMap = new HashMap<>();
        Hashtable<Object, Object> ht = new Hashtable<>(emptyMap);

        // Should be empty
        if (ht.size() != 0) {
            throw new RuntimeException("Failed: size should be 0 for empty map");
        }
    }
}
