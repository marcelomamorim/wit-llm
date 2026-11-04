/*
 * @test
 * @summary Test Collections.UnmodifiableMap.putAll throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Map;
import java.util.HashMap;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        // Create an unmodifiable map wrapper around a modifiable map
        Map<String, String> modifiable = new HashMap<>();
        modifiable.put("key1", "value1");
        Map<String, String> unmodifiable = Collections.unmodifiableMap(modifiable);

        // Test that putAll throws UnsupportedOperationException
        try {
            unmodifiable.putAll(new HashMap<String, String>());
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Expected exception
        }

        // Also test that putAll on modifiable map works normally
        Map<String, String> anotherMap = new HashMap<>();
        anotherMap.put("a", "b");
        modifiable.putAll(anotherMap);
        if (!"b".equals(modifiable.get("a"))) {
            throw new RuntimeException("putAll did not copy entries correctly");
        }
    }
}
