/*
 * @test
 * @summary Test UnsupportedOperationException thrown by Collections.UnmodifiableMap.clear()
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Map;
import java.util.HashMap;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testUnmodifiableMapClearThrows();
    }

    private static void testUnmodifiableMapClearThrows() {
        Map<String, String> modifiable = new HashMap<>();
        modifiable.put("key", "value");
        Map<String, String> unmodifiable = Collections.unmodifiableMap(modifiable);
        try {
            unmodifiable.clear();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Expected exception caught, test passes
        }
    }
}
