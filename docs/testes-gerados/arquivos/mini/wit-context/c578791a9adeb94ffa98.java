/*
 * @test
 * @summary Test Collections.SingletonMap.replace throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Map;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        // Create a singleton map using Collections.singletonMap
        Map<String, String> singletonMap = Collections.singletonMap("key", "value");

        // Test that replace throws UnsupportedOperationException
        try {
            singletonMap.replace("key", "newValue");
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Expected exception caught, test passes
        }

        // Also test replace with a key not present returns null (behavior of Map.replace)
        // but since singletonMap is immutable, replace always throws, so no need to test this

        System.out.println("Test passed");
    }
}
