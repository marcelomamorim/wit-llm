/*
 * @test
 * @summary Test for computeIfPresent method in ConcurrentHashMap
 * @run main ConcurrentHashMapWitupTest_P0ad0a59c8b4c
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class ConcurrentHashMapWitupTest_P0ad0a59c8b4c {
    public static void main(String[] args) {
        testComputeIfPresentWithExistingKey();
        testComputeIfPresentWithNullKey();
        testComputeIfPresentWithNullRemappingFunction();
        testComputeIfPresentWithNullValue();
        testComputeIfPresentWithKeyNotPresent();
    }

    static void testComputeIfPresentWithExistingKey() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("key", 1);
        BiFunction<String, Integer, Integer> remappingFunction = (k, v) -> v + 1;
        Integer result = map.computeIfPresent("key", remappingFunction);
        assert result.equals(2) : "Expected 2, got " + result;
        assert map.get("key").equals(2) : "Value not updated correctly";
    }

    static void testComputeIfPresentWithNullKey() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        BiFunction<String, Integer, Integer> remappingFunction = (k, v) -> v + 1;
        try {
            map.computeIfPresent(null, remappingFunction);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // Expected
        }
    }

    static void testComputeIfPresentWithNullRemappingFunction() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("key", 1);
        try {
            map.computeIfPresent("key", null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // Expected
        }
    }

    static void testComputeIfPresentWithNullValue() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("key", 1);
        BiFunction<String, Integer, Integer> remappingFunction = (k, v) -> null;
        Integer result = map.computeIfPresent("key", remappingFunction);
        assert result == null : "Expected null";
        assert !map.containsKey("key") : "Key should be removed";
    }

    static void testComputeIfPresentWithKeyNotPresent() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        BiFunction<String, Integer, Integer> remappingFunction = (k, v) -> v + 1;
        Integer result = map.computeIfPresent("absentKey", remappingFunction);
        assert result == null : "Expected null";
        assert !map.containsKey("absentKey") : "Map should not contain the key";
    }
}
