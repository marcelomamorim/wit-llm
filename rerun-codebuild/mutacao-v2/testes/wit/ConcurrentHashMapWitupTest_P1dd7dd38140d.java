/*
 * @test
 * @summary Test ConcurrentHashMap.containsValue(Object) for null input and value presence
 * @run main ConcurrentHashMapWitupTest_P1dd7dd38140d
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest_P1dd7dd38140d {
    public static void main(String[] args) {
        testContainsValueNullThrows();
        testContainsValuePresent();
        testContainsValueAbsent();
    }

    private static void testContainsValueNullThrows() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        boolean npeThrown = false;
        try {
            map.containsValue(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when passing null to containsValue");
        }
    }

    private static void testContainsValuePresent() {
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

    private static void testContainsValueAbsent() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");
        if (map.containsValue("nonexistent")) {
            throw new AssertionError("containsValue should return false for absent value");
        }
    }
}
