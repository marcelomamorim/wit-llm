/*
 * @test
 * @summary Tests for ConcurrentHashMap.computeIfPresent method
 * @run main ConcurrentHashMapWitupTest_Pa00bb86a6345
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class ConcurrentHashMapWitupTest_Pa00bb86a6345 {

    public static void main(String[] args) {
        testComputeIfPresentWithExistingKey();
        testComputeIfPresentWithNullKey();
        testComputeIfPresentWithNullRemappingFunction();
        testComputeIfPresentWithAbsentKey();
    }

    static void testComputeIfPresentWithExistingKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");
        BiFunction<String, String, String> remap = (k, v) -> v + "_updated";
        String result = map.computeIfPresent("key1", remap);
        assert "value1_updated".equals(result) : "Expected updated value";
        assert "value1_updated".equals(map.get("key1")) : "Map should contain updated value";
    }

    static void testComputeIfPresentWithNullKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        BiFunction<String, String, String> remap = (k, v) -> v + "_updated";
        try {
            map.computeIfPresent(null, remap);
            assert false : "Expected NullPointerException for null key";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testComputeIfPresentWithNullRemappingFunction() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");
        try {
            map.computeIfPresent("key1", null);
            assert false : "Expected NullPointerException for null remapping function";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testComputeIfPresentWithAbsentKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        BiFunction<String, String, String> remap = (k, v) -> v + "_updated";
        String result = map.computeIfPresent("absentKey", remap);
        assert result == null : "Result should be null for absent key";
        assert !map.containsKey("absentKey") : "Map should not contain absent key";
    }
}
