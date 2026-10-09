/* @test
   @summary Verifies computeIfAbsent behavior of ConcurrentHashMap.
   @run main ConcurrentHashMapWitupTest_P35e78a57a2f1
 */
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class ConcurrentHashMapWitupTest_P35e78a57a2f1 {
    public static void main(String[] args) {
        testComputeIfAbsentWithNullKey();
        testComputeIfAbsentWithNullFunction();
        testComputeIfAbsentWithExistingKey();
        testComputeIfAbsentWithNewKey();
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

    static void testComputeIfAbsentWithExistingKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("existing", "value");
        String result = map.computeIfAbsent("existing", k -> "newValue");
        if (!"value".equals(result)) {
            throw new AssertionError("Expected existing value, got: " + result);
        }
        if (!"value".equals(map.get("existing"))) {
            throw new AssertionError("Map value changed unexpectedly");
        }
    }

    static void testComputeIfAbsentWithNewKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        String result = map.computeIfAbsent("newKey", k -> "computedValue");
        if (!"computedValue".equals(result)) {
            throw new AssertionError("Expected computedValue, got: " + result);
        }
        if (!"computedValue".equals(map.get("newKey"))) {
            throw new AssertionError("Map does not contain expected value");
        }
    }
}
