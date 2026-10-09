/* @test
   @summary Verifies computeIfPresent behavior of ConcurrentHashMap.
   @run main ConcurrentHashMapWitupTest_Pfd99dc27c4c6
 */
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class ConcurrentHashMapWitupTest_Pfd99dc27c4c6 {
    public static void main(String[] args) {
        testComputeIfPresentWithExistingKey();
        testComputeIfPresentWithNullKey();
        testComputeIfPresentWithNullRemappingFunction();
        testComputeIfPresentWithNullValues();
    }

    static void testComputeIfPresentWithExistingKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key", "value");
        BiFunction<String, String, String> remap = (k, v) -> v + "_updated";
        String result = map.computeIfPresent("key", remap);
        assert "value_updated".equals(result) : "Expected 'value_updated', got " + result;
        assert "value_updated".equals(map.get("key")) : "Map value not updated";
    }

    static void testComputeIfPresentWithNullKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        BiFunction<String, String, String> remap = (k, v) -> v + "_updated";
        try {
            map.computeIfPresent(null, remap);
            throw new AssertionError("Expected NullPointerException for null key");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testComputeIfPresentWithNullRemappingFunction() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key", "value");
        try {
            map.computeIfPresent("key", null);
            throw new AssertionError("Expected NullPointerException for null remappingFunction");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testComputeIfPresentWithNullValues() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key", null);
        BiFunction<String, String, String> remap = (k, v) -> "newValue";
        String result = map.computeIfPresent("key", remap);
        assert "newValue".equals(result) : "Expected 'newValue', got " + result;
        assert "newValue".equals(map.get("key")) : "Map value not updated";
    }
}
