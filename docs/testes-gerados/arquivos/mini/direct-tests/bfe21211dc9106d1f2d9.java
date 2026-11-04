/*
 * @test
 * @summary Test ConcurrentHashMap.containsValue behavior including null and present/absent values
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {
    public static void main(String[] args) {
        testContainsValueWithNull();
        testContainsValuePresent();
        testContainsValueAbsent();
        testContainsValueWithDifferentObject();
    }

    static void testContainsValueWithNull() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        boolean thrown = false;
        try {
            map.containsValue(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NullPointerException for null value");
        }
    }

    static void testContainsValuePresent() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        if (!map.containsValue("value1")) {
            throw new AssertionError("containsValue should return true for existing value");
        }
        if (!map.containsValue("value2")) {
            throw new AssertionError("containsValue should return true for existing value");
        }
    }

    static void testContainsValueAbsent() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");
        if (map.containsValue("absent")) {
            throw new AssertionError("containsValue should return false for absent value");
        }
    }

    static void testContainsValueWithDifferentObject() {
        ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();
        Object value = new String("test");
        map.put("key", value);
        // Different object but equals returns true
        Object query = new String("test");
        if (!map.containsValue(query)) {
            throw new AssertionError("containsValue should return true for equal but different object");
        }
    }
}
