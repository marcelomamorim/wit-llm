/* @test    @summary Test computeIfAbsent method of ConcurrentHashMap    @run main ConcurrentHashMapWitupTest_P95e254341436 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class ConcurrentHashMapWitupTest_P95e254341436 {

    public static void main(String[] args) {
        testComputeIfAbsentWithNullKey();
        testComputeIfAbsentWithNullFunction();
        testComputeIfAbsentWithExistingKey();
        testComputeIfAbsentWithNewKey();
        System.out.println("All tests passed.");
    }

    static void testComputeIfAbsentWithNullKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.computeIfAbsent(null, k -> "value");
            throw new AssertionError("Expected NullPointerException for null key");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testComputeIfAbsentWithNullFunction() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.computeIfAbsent("key", null);
            throw new AssertionError("Expected NullPointerException for null function");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testComputeIfAbsentWithExistingKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("existing", "value1");
        String result = map.computeIfAbsent("existing", k -> "value2");
        if (!"value1".equals(result)) {
            throw new AssertionError("Expected existing value, got: " + result);
        }
        if (!"value1".equals(map.get("existing"))) {
            throw new AssertionError("Map value changed unexpectedly");
        }
    }

    static void testComputeIfAbsentWithNewKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        String result = map.computeIfAbsent("newKey", k -> "newValue");
        if (!"newValue".equals(result)) {
            throw new AssertionError("Expected newValue, got: " + result);
        }
        if (!"newValue".equals(map.get("newKey"))) {
            throw new AssertionError("Map does not contain expected value for newKey");
        }
    }
}
