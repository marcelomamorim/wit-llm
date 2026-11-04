/*
 * @test
 * @summary Test java.util.Collections.synchronizedMap(Map) wrapper basic behavior
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.Collection;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testSynchronizedMapBasic();
        testSynchronizedMapNullKeyValue();
        testSynchronizedMapViewCollections();
    }

    private static void testSynchronizedMapBasic() {
        Map<String, String> base = new HashMap<>();
        base.put("key1", "value1");
        Map<String, String> syncMap = Collections.synchronizedMap(base);

        // The synchronized map should reflect the base map contents
        if (!"value1".equals(syncMap.get("key1"))) {
            throw new AssertionError("Expected value1 for key1");
        }

        // Put a new value via synchronized map
        syncMap.put("key2", "value2");
        if (!"value2".equals(base.get("key2"))) {
            throw new AssertionError("Base map should reflect put via synchronized map");
        }

        // Remove via synchronized map
        syncMap.remove("key1");
        if (base.containsKey("key1")) {
            throw new AssertionError("Base map should reflect remove via synchronized map");
        }
    }

    private static void testSynchronizedMapNullKeyValue() {
        Map<String, String> base = new HashMap<>();
        Map<String, String> syncMap = Collections.synchronizedMap(base);

        // Put null key and null value
        syncMap.put(null, null);
        if (!syncMap.containsKey(null)) {
            throw new AssertionError("Synchronized map should contain null key");
        }
        if (syncMap.get(null) != null) {
            throw new AssertionError("Value for null key should be null");
        }
    }

    private static void testSynchronizedMapViewCollections() {
        Map<String, String> base = new HashMap<>();
        base.put("a", "1");
        base.put("b", "2");
        Map<String, String> syncMap = Collections.synchronizedMap(base);

        // Test keySet view
        Set<String> keys = syncMap.keySet();
        if (!keys.contains("a") || !keys.contains("b")) {
            throw new AssertionError("keySet should contain all keys");
        }

        // Test values view
        Collection<String> values = syncMap.values();
        if (!values.contains("1") || !values.contains("2")) {
            throw new AssertionError("values should contain all values");
        }

        // Test entrySet view
        Set<Map.Entry<String, String>> entries = syncMap.entrySet();
        boolean foundA1 = false;
        boolean foundB2 = false;
        for (Map.Entry<String, String> e : entries) {
            if ("a".equals(e.getKey()) && "1".equals(e.getValue())) {
                foundA1 = true;
            }
            if ("b".equals(e.getKey()) && "2".equals(e.getValue())) {
                foundB2 = true;
            }
        }
        if (!foundA1 || !foundB2) {
            throw new AssertionError("entrySet should contain all entries");
        }
    }
}
