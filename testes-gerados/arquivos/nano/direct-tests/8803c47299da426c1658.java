/* @test    @summary Test ConcurrentHashMap.remove(Object o) for correctness and regressions    @run main ConcurrentHashMapWitupTest */

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testRemoveWithEntry();
        testRemoveWithNull();
        testRemoveWithNonExisting();
    }

    static void testRemoveWithEntry() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");
        Map.Entry<String, String> entry = Map.entry("key1", "value1");
        boolean removed = map.remove(entry);
        assert removed : "Entry should be removed successfully";
        assert !map.containsKey("key1") : "Map should not contain key1 after removal";
    }

    static void testRemoveWithNull() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key2", "value2");
        boolean removed = map.remove(null);
        assert !removed : "Removing null should return false";
        assert map.containsKey("key2") : "Map should still contain key2";
    }

    static void testRemoveWithNonExisting() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key3", "value3");
        Map.Entry<String, String> nonExistingEntry = Map.entry("nonexistent", "value");
        boolean removed = map.remove(nonExistingEntry);
        assert !removed : "Removing non-existing entry should return false";
        assert map.containsKey("key3") : "Map should still contain key3";
    }
}
