/*
 * @test
 * @summary Test for ConcurrentHashMap.computeIfAbsent to handle null inputs
 * @run main ConcurrentHashMapWitupTest_P9be1f43da396
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest_P9be1f43da396 {

    public static void main(String[] args) {
        testComputeIfAbsentWithNullKey();
        testComputeIfAbsentWithNullMappingFunction();
        testComputeIfAbsentNormalBehavior();
    }

    private static void testComputeIfAbsentWithNullKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        boolean threw = false;
        try {
            map.computeIfAbsent(null, k -> "value");
        } catch (NullPointerException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected NullPointerException for null key");
        }
    }

    private static void testComputeIfAbsentWithNullMappingFunction() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        boolean threw = false;
        try {
            map.computeIfAbsent("key", null);
        } catch (NullPointerException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected NullPointerException for null mappingFunction");
        }
    }

    private static void testComputeIfAbsentNormalBehavior() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        String result = map.computeIfAbsent("testKey", k -> "testValue");
        if (!"testValue".equals(result)) {
            throw new AssertionError("Unexpected value returned: " + result);
        }
        if (!"testValue".equals(map.get("testKey"))) {
            throw new AssertionError("Map did not contain expected value after computeIfAbsent");
        }
    }
}
