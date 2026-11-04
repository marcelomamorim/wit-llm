/*
 * @test
 * @summary Basic tests for ConcurrentHashMap.putIfAbsent(K,V) method
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {
    public static void main(String[] args) {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();

        // Test putIfAbsent on empty map returns null and inserts value
        String prev = map.putIfAbsent("key1", "value1");
        if (prev != null) throw new RuntimeException("Expected null on first putIfAbsent");
        if (!"value1".equals(map.get("key1"))) throw new RuntimeException("Value not inserted correctly");

        // Test putIfAbsent on existing key returns existing value and does not overwrite
        prev = map.putIfAbsent("key1", "value2");
        if (!"value1".equals(prev)) throw new RuntimeException("Expected existing value on putIfAbsent");
        if (!"value1".equals(map.get("key1"))) throw new RuntimeException("Value should not be overwritten by putIfAbsent");

        // Test putIfAbsent with null value allowed (ConcurrentHashMap does not allow null keys or values, expect NullPointerException)
        boolean npeThrown = false;
        try {
            map.putIfAbsent("key2", null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) throw new RuntimeException("Expected NullPointerException when inserting null value");

        // Test putIfAbsent with null key throws NullPointerException
        npeThrown = false;
        try {
            map.putIfAbsent(null, "value");
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) throw new RuntimeException("Expected NullPointerException when inserting null key");
    }
}
