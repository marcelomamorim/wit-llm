/*
 * @test
 * @summary Test Collections.UnmodifiableMap.replaceAll throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Map;
import java.util.function.BiFunction;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        // Create an unmodifiable map wrapper around a simple map
        Map<String, String> modifiable = new java.util.HashMap<>();
        modifiable.put("key", "value");
        Map<String, String> unmodifiable = Collections.unmodifiableMap(modifiable);

        // Test that replaceAll throws UnsupportedOperationException
        boolean threw = false;
        try {
            unmodifiable.replaceAll(new BiFunction<String, String, String>() {
                @Override
                public String apply(String k, String v) {
                    return v + "_modified";
                }
            });
        } catch (UnsupportedOperationException e) {
            threw = true;
        }

        if (!threw) {
            throw new AssertionError("Expected UnsupportedOperationException was not thrown by replaceAll");
        }
    }
}
