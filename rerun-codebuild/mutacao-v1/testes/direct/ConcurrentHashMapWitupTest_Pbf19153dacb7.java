/* @test    @summary Test computeIfPresent method of ConcurrentHashMap    @run main ConcurrentHashMapWitupTest_Pbf19153dacb7 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class ConcurrentHashMapWitupTest_Pbf19153dacb7 {

    public static void main(String[] args) {
        testComputeIfPresentWithExistingKey();
        testComputeIfPresentWithNullKey();
        testComputeIfPresentWithNullRemappingFunction();
        testComputeIfPresentWithAbsentKey();
        System.out.println("All tests passed.");
    }

    static void testComputeIfPresentWithExistingKey() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("key1", 1);
        BiFunction<String, Integer, Integer> remap = (k, v) -> v + 10;
        Integer result = map.computeIfPresent("key1", remap);
        if (result == null || !result.equals(11)) {
            throw new AssertionError("Failed: computeIfPresent with existing key");
        }
        if (!map.get("key1").equals(11)) {
            throw new AssertionError("Map not updated correctly");
        }
    }

    static void testComputeIfPresentWithNullKey() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        BiFunction<String, Integer, Integer> remap = (k, v) -> v + 10;
        try {
            map.computeIfPresent(null, remap);
            throw new AssertionError("Expected NullPointerException for null key");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testComputeIfPresentWithNullRemappingFunction() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("key2", 2);
        try {
            map.computeIfPresent("key2", null);
            throw new AssertionError("Expected NullPointerException for null remappingFunction");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testComputeIfPresentWithAbsentKey() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        BiFunction<String, Integer, Integer> remap = (k, v) -> v + 10;
        Integer result = map.computeIfPresent("absentKey", remap);
        if (result != null) {
            throw new AssertionError("Expected null for absent key");
        }
        if (map.containsKey("absentKey")) {
            throw new AssertionError("Map should not contain the key");
        }
    }
}
