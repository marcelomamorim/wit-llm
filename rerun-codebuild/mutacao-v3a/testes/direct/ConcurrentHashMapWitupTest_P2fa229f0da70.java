/*
 * @test
 * @summary Test computeIfAbsent method of ConcurrentHashMap for correct behavior
 * @run main ConcurrentHashMapWitupTest_P2fa229f0da70
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest_P2fa229f0da70 {

    public static void main(String[] args) {
        testComputeIfAbsentWithNulls();
        testComputeIfAbsentWithExistingKey();
        testComputeIfAbsentWithNewKey();
    }

    static void testComputeIfAbsentWithNulls() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        boolean exceptionThrown = false;
        try {
            map.computeIfAbsent(null, key -> "value");
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected NullPointerException for null key";

        exceptionThrown = false;
        try {
            map.computeIfAbsent("key", null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected NullPointerException for null mappingFunction";
    }

    static void testComputeIfAbsentWithExistingKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("existing", "initial");
        String result = map.computeIfAbsent("existing", key -> "newValue");
        assert result.equals("initial") : "Value should not change for existing key";
        assert map.get("existing").equals("initial") : "Map value should remain unchanged";
    }

    static void testComputeIfAbsentWithNewKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        String key = "newKey";
        String value = "computedValue";
        String result = map.computeIfAbsent(key, k -> value);
        assert result.equals(value) : "Returned value should be the computed value";
        assert map.get(key).equals(value) : "Map should contain the new key with computed value";
    }
}
