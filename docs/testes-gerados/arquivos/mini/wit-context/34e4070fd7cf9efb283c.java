/*
 * @test
 * @summary Test Collections.UnmodifiableMap.putIfAbsent throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Map;
import java.util.HashMap;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        // Create a modifiable map
        Map<String, String> modifiable = new HashMap<>();
        modifiable.put("key1", "value1");

        // Wrap it with unmodifiableMap
        Map<String, String> unmodifiable = Collections.unmodifiableMap(modifiable);

        // Test that putIfAbsent throws UnsupportedOperationException
        boolean threw = false;
        try {
            unmodifiable.putIfAbsent("key2", "value2");
        } catch (UnsupportedOperationException e) {
            threw = true;
        }

        if (!threw) {
            throw new AssertionError("Expected UnsupportedOperationException from putIfAbsent on unmodifiableMap");
        }

        // Also verify that get returns expected values
        if (!"value1".equals(unmodifiable.get("key1"))) {
            throw new AssertionError("Unexpected value for key1");
        }

        if (unmodifiable.get("key2") != null) {
            throw new AssertionError("Expected null for key2 before putIfAbsent");
        }

        System.out.println("Test passed.");
    }
}
