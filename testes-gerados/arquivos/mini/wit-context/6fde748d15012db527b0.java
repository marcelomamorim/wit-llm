/*
 * @test
 * @summary Test java.util.Collections.UnmodifiableMap.computeIfAbsent throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.function.Function;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testComputeIfAbsentUnsupported();
    }

    private static void testComputeIfAbsentUnsupported() {
        // Create an unmodifiable map via Collections.unmodifiableMap
        java.util.Map<String, String> modifiable = new java.util.HashMap<>();
        modifiable.put("key", "value");
        java.util.Map<String, String> unmodifiable = Collections.unmodifiableMap(modifiable);

        try {
            // The computeIfAbsent method of the unmodifiable map should throw UnsupportedOperationException
            unmodifiable.computeIfAbsent("key2", new Function<String, String>() {
                public String apply(String k) {
                    return "newValue";
                }
            });
            throw new AssertionError("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Expected exception
        }
    }
}
