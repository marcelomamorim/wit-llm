/*
 * @test
 * @summary Test Collections.UnmodifiableMap.replace throws UnsupportedOperationException
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

        // Test that replace throws UnsupportedOperationException
        boolean threw = false;
        try {
            unmodifiable.replace("key1", "newValue");
        } catch (UnsupportedOperationException e) {
            threw = true;
        }

        if (!threw) {
            throw new RuntimeException("Expected UnsupportedOperationException from replace on unmodifiableMap");
        }

        // Also test replace on a modifiable map returns previous value
        String oldValue = modifiable.replace("key1", "newValue");
        if (!"value1".equals(oldValue)) {
            throw new RuntimeException("Expected old value 'value1' from replace on modifiable map");
        }

        // Test replace with a key not present returns null
        String nullValue = modifiable.replace("key2", "value2");
        if (nullValue != null) {
            throw new RuntimeException("Expected null from replace on absent key");
        }

        System.out.println("All tests passed.");
    }
}
