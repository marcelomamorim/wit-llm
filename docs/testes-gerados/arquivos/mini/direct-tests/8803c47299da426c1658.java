/*
 * @test
 * @summary Test ConcurrentHashMap remove(Object o) method with Map.Entry argument
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {
    public static void main(String[] args) {
        testRemoveWithValidEntry();
        testRemoveWithNullKey();
        testRemoveWithNullValue();
        testRemoveWithNonEntryObject();
        testRemoveWithEntryNotInMap();
    }

    private static void testRemoveWithValidEntry() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");
        Map.Entry<String, String> entry = new Map.Entry<String, String>() {
            public String getKey() { return "key1"; }
            public String getValue() { return "value1"; }
            public String setValue(String value) { throw new UnsupportedOperationException(); }
        };
        boolean removed = map.remove(entry);
        if (!removed) throw new AssertionError("Expected removal to succeed");
        if (map.containsKey("key1")) throw new AssertionError("Key should be removed");
    }

    private static void testRemoveWithNullKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        Map.Entry<String, String> entry = new Map.Entry<String, String>() {
            public String getKey() { return null; }
            public String getValue() { return "value"; }
            public String setValue(String value) { throw new UnsupportedOperationException(); }
        };
        boolean removed = map.remove(entry);
        if (removed) throw new AssertionError("Removal should fail with null key");
    }

    private static void testRemoveWithNullValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        Map.Entry<String, String> entry = new Map.Entry<String, String>() {
            public String getKey() { return "key"; }
            public String getValue() { return null; }
            public String setValue(String value) { throw new UnsupportedOperationException(); }
        };
        boolean removed = map.remove(entry);
        if (removed) throw new AssertionError("Removal should fail with null value");
    }

    private static void testRemoveWithNonEntryObject() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key", "value");
        boolean removed = map.remove("not an entry");
        if (removed) throw new AssertionError("Removal should fail with non-Map.Entry object");
        if (!map.containsKey("key")) throw new AssertionError("Map should remain unchanged");
    }

    private static void testRemoveWithEntryNotInMap() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key", "value");
        Map.Entry<String, String> entry = new Map.Entry<String, String>() {
            public String getKey() { return "key"; }
            public String getValue() { return "wrongValue"; }
            public String setValue(String value) { throw new UnsupportedOperationException(); }
        };
        boolean removed = map.remove(entry);
        if (removed) throw new AssertionError("Removal should fail if value does not match");
        if (!map.containsKey("key")) throw new AssertionError("Map should remain unchanged");
    }
}
