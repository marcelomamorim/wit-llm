/*
 * @test
 * @summary Tests for computeIfAbsent method in ConcurrentHashMap.
 * @run main ConcurrentHashMapWitupTest_P2fa229f0da70
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class ConcurrentHashMapWitupTest_P2fa229f0da70 {

    public static void main(String[] args) {
        testComputeIfAbsentWithNullKey();
        testComputeIfAbsentWithNullMappingFunction();
        testComputeIfAbsentWithExistingKey();
        testComputeIfAbsentWithNewKey();
    }

    static void testComputeIfAbsentWithNullKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        boolean caught = false;
        try {
            map.computeIfAbsent(null, k -> "value");
        } catch (NullPointerException e) {
            caught = true;
        }
        assert caught : "Expected NullPointerException for null key";
    }

    static void testComputeIfAbsentWithNullMappingFunction() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        boolean caught = false;
        try {
            map.computeIfAbsent("key", null);
        } catch (NullPointerException e) {
            caught = true;
        }
        assert caught : "Expected NullPointerException for null mappingFunction";
    }

    static void testComputeIfAbsentWithExistingKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("existing", "initial");
        String result = map.computeIfAbsent("existing", k -> "newValue");
        assert result.equals("initial") : "Value should not change for existing key";
        assert map.get("existing").equals("initial") : "Map value should remain unchanged";
    }

    static void testComputeIfAbsentWithNewKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        String result = map.computeIfAbsent("newKey", k -> "computedValue");
        assert result.equals("computedValue") : "Value should be computed for new key";
        assert map.get("newKey").equals("computedValue") : "Map should contain the new key-value pair";
    }
}
