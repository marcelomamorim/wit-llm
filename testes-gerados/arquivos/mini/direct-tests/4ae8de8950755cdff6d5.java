/*
 * @test
 * @summary Basic tests for ConcurrentHashMap.computeIfAbsent method
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class ConcurrentHashMapWitupTest {
    public static void main(String[] args) {
        testNullKey();
        testNullMappingFunction();
        testComputeIfAbsentNewValue();
        testComputeIfAbsentExistingValue();
        testComputeIfAbsentReturnsNull();
    }

    static void testNullKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.computeIfAbsent(null, k -> "value");
            throw new Error("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
        }
    }

    static void testNullMappingFunction() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.computeIfAbsent("key", null);
            throw new Error("Expected NullPointerException for null mappingFunction");
        } catch (NullPointerException expected) {
        }
    }

    static void testComputeIfAbsentNewValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        String result = map.computeIfAbsent("key1", k -> "value1");
        if (!"value1".equals(result))
            throw new Error("Expected value1 but got " + result);
        if (!"value1".equals(map.get("key1")))
            throw new Error("Map should contain value1 for key1");
    }

    static void testComputeIfAbsentExistingValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key2", "existing");
        String result = map.computeIfAbsent("key2", k -> "newvalue");
        if (!"existing".equals(result))
            throw new Error("Expected existing but got " + result);
        if (!"existing".equals(map.get("key2")))
            throw new Error("Map should still contain existing for key2");
    }

    static void testComputeIfAbsentReturnsNull() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        String result = map.computeIfAbsent("key3", k -> null);
        if (result != null)
            throw new Error("Expected null result when mappingFunction returns null");
        if (map.containsKey("key3"))
            throw new Error("Map should not contain key3 when mappingFunction returns null");
    }
}
