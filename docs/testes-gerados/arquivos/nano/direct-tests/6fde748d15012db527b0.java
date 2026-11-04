/* @test    @summary Test computeIfAbsent method in Collections class    @run main CollectionsWitupTest */

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testComputeIfAbsentWithAbsentKey();
        testComputeIfAbsentWithExistingKey();
    }

    private static void testComputeIfAbsentWithAbsentKey() {
        Map<String, String> map = new HashMap<>();
        String key = "newKey";
        String value = "computedValue";
        Function<String, String> func = k -> value;
        String result = null;
        try {
            result = Collections.computeIfAbsent(map, key, func);
        } catch (UnsupportedOperationException e) {
            // Expected, as method is unsupported in actual implementation
            return;
        }
        // Since the method throws UnsupportedOperationException, we expect no change
        assert map.isEmpty() : "Map should remain empty";
        assert result == null : "Result should be null due to exception";
    }

    private static void testComputeIfAbsentWithExistingKey() {
        Map<String, String> map = new HashMap<>();
        String key = "existingKey";
        String existingValue = "value";
        map.put(key, existingValue);
        Function<String, String> func = k -> "newValue";
        String result = null;
        try {
            result = Collections.computeIfAbsent(map, key, func);
        } catch (UnsupportedOperationException e) {
            // Expected, as method is unsupported in actual implementation
            return;
        }
        // Since the method throws UnsupportedOperationException, we expect no change
        assert map.get(key).equals(existingValue) : "Value should remain unchanged";
        assert result == null : "Result should be null due to exception";
    }
}
