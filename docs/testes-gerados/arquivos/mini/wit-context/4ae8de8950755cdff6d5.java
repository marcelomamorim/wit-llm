/*
 * @test
 * @summary Test ConcurrentHashMap.computeIfAbsent behavior including null checks and mappingFunction application
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class ConcurrentHashMapWitupTest {
    public static void main(String[] args) {
        testNullKeyThrows();
        testNullMappingFunctionThrows();
        testComputeIfAbsentReturnsExistingValue();
        testComputeIfAbsentComputesAndInsertsValue();
        testComputeIfAbsentReturnsNullIfMappingReturnsNull();
    }

    static void testNullKeyThrows() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.computeIfAbsent(null, k -> "value");
            throw new Error("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
        }
    }

    static void testNullMappingFunctionThrows() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.computeIfAbsent("key", null);
            throw new Error("Expected NullPointerException for null mappingFunction");
        } catch (NullPointerException expected) {
        }
    }

    static void testComputeIfAbsentReturnsExistingValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key", "existing");
        String result = map.computeIfAbsent("key", k -> { throw new Error("mappingFunction should not be called"); });
        if (!"existing".equals(result)) {
            throw new Error("Expected existing value to be returned");
        }
    }

    static void testComputeIfAbsentComputesAndInsertsValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        String result = map.computeIfAbsent("key", k -> "computed");
        if (!"computed" .equals(result)) {
            throw new Error("Expected computed value to be returned");
        }
        // Verify value is stored
        String stored = map.get("key");
        if (!"computed".equals(stored)) {
            throw new Error("Expected computed value to be stored in map");
        }
    }

    static void testComputeIfAbsentReturnsNullIfMappingReturnsNull() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        String result = map.computeIfAbsent("key", k -> null);
        if (result != null) {
            throw new Error("Expected null result when mappingFunction returns null");
        }
        if (map.containsKey("key")) {
            throw new Error("Expected map not to contain key when mappingFunction returns null");
        }
    }
}
