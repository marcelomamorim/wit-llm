/*
 * @test
 * @summary Deterministic tests for java.util.TreeMap.replace(K key, V value)
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        testReplaceExistingKey();
        testReplaceNonExistingKey();
    }

    private static void testReplaceExistingKey() {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("key1", "value1");
        String oldValue = map.replace("key1", "value2");
        if (!"value1".equals(oldValue)) {
            throw new AssertionError("Expected old value 'value1', got: " + oldValue);
        }
        String newValue = map.get("key1");
        if (!"value2".equals(newValue)) {
            throw new AssertionError("Expected new value 'value2', got: " + newValue);
        }
    }

    private static void testReplaceNonExistingKey() {
        TreeMap<String, String> map = new TreeMap<>();
        String result = map.replace("nonexistent", "value");
        if (result != null) {
            throw new AssertionError("Expected null when replacing non-existing key, got: " + result);
        }
    }
}
