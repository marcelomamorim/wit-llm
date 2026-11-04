/*
 * @test
 * @summary Tests computeIfAbsent method of ConcurrentHashMap for correctness and regressions
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testComputeIfAbsentWithNullKey();
        testComputeIfAbsentWithNullFunction();
        testComputeIfAbsentWithAbsentKey();
        testComputeIfAbsentWithExistingKey();
        testComputeIfAbsentWithConcurrentModification();
    }

    static void testComputeIfAbsentWithNullKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.computeIfAbsent(null, k -> "value");
            throw new AssertionError("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testComputeIfAbsentWithNullFunction() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.computeIfAbsent("key", null);
            throw new AssertionError("Expected NullPointerException for null function");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testComputeIfAbsentWithAbsentKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        String result = map.computeIfAbsent("newKey", k -> "newValue");
        if (!"newValue".equals(result)) {
            throw new AssertionError("Expected value 'newValue', got: " + result);
        }
        if (!"newValue".equals(map.get("newKey"))) {
            throw new AssertionError("Map should contain 'newKey' with 'newValue'");
        }
    }

    static void testComputeIfAbsentWithExistingKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("existingKey", "existingValue");
        String result = map.computeIfAbsent("existingKey", k -> "newValue");
        if (!"existingValue".equals(result)) {
            throw new AssertionError("Expected existing value 'existingValue', got: " + result);
        }
        if (!"existingValue".equals(map.get("existingKey"))) {
            throw new AssertionError("Map should retain 'existingValue' for 'existingKey'");
        }
    }

    static void testComputeIfAbsentWithConcurrentModification() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key", "initial");
        String result = map.computeIfAbsent("key", k -> {
            map.put(k, "modified");
            return "new";
        });
        if (!"initial".equals(result)) {
            throw new AssertionError("Expected existing value 'initial', got: " + result);
        }
        if (!"initial".equals(map.get("key"))) {
            throw new AssertionError("Map should not be modified during computeIfAbsent for existing key");
        }
    }
}
