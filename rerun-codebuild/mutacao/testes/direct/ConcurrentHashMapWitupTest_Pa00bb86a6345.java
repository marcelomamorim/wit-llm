/*
 * @test
 * @summary Tests computeIfPresent method of ConcurrentHashMap for correctness and regressions
 * @run main ConcurrentHashMapWitupTest_Pa00bb86a6345
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class ConcurrentHashMapWitupTest_Pa00bb86a6345 {

    public static void main(String[] args) {
        testComputeIfPresentWithExistingKey();
        testComputeIfPresentWithNullKey();
        testComputeIfPresentWithNullRemappingFunction();
        testComputeIfPresentWithNullValues();
        testComputeIfPresentWithNonExistingKey();
    }

    static void testComputeIfPresentWithExistingKey() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("key1", 1);
        BiFunction<String, Integer, Integer> remap = (k, v) -> v + 10;
        Integer result = map.computeIfPresent("key1", remap);
        assert result.equals(11) : "Expected 11, got " + result;
        assert map.get("key1") == 11 : "Map value not updated correctly";
    }

    static void testComputeIfPresentWithNullKey() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        BiFunction<String, Integer, Integer> remap = (k, v) -> v + 10;
        try {
            map.computeIfPresent(null, remap);
            assert false : "Expected NullPointerException for null key";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testComputeIfPresentWithNullRemappingFunction() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("key1", 1);
        try {
            map.computeIfPresent("key1", null);
            assert false : "Expected NullPointerException for null remappingFunction";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testComputeIfPresentWithNullValues() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("key1", null);
        BiFunction<String, Integer, Integer> remap = (k, v) -> v == null ? 0 : v + 10;
        Integer result = map.computeIfPresent("key1", remap);
        assert result == null : "Expected null, got " + result;
        assert map.get("key1") == null : "Map value should remain null";
    }

    static void testComputeIfPresentWithNonExistingKey() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        BiFunction<String, Integer, Integer> remap = (k, v) -> v == null ? 0 : v + 10;
        Integer result = map.computeIfPresent("nonexistent", remap);
        assert result == null : "Expected null for non-existing key";
        assert !map.containsKey("nonexistent") : "Map should not contain the key";
    }
}
