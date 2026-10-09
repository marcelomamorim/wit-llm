/*
 * @test
 * @summary Test for ConcurrentHashMap.computeIfPresent handling null key and null remappingFunction
 * @run main ConcurrentHashMapWitupTest_P0ad0a59c8b4c
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class ConcurrentHashMapWitupTest_P0ad0a59c8b4c {

    public static void main(String[] args) {
        testComputeIfPresentWithNullKey();
        testComputeIfPresentWithNullRemappingFunction();
        testComputeIfPresentNormalExecution();
    }

    static void testComputeIfPresentWithNullKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        BiFunction<String, String, String> remappingFunction = (k, v) -> v;
        boolean thrown = false;
        try {
            map.computeIfPresent(null, remappingFunction);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NullPointerException for null key");
        }
    }

    static void testComputeIfPresentWithNullRemappingFunction() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key", "value");
        boolean thrown = false;
        try {
            map.computeIfPresent("key", null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NullPointerException for null remappingFunction");
        }
    }

    static void testComputeIfPresentNormalExecution() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("a", "1");
        BiFunction<String, String, String> remappingFunction = (k, v) -> v + "2";
        String result = map.computeIfPresent("a", remappingFunction);
        if (!"12".equals(result)) {
            throw new AssertionError("Unexpected result: " + result);
        }
        if (!"12".equals(map.get("a"))) {
            throw new AssertionError("Map not updated correctly");
        }
    }
}
