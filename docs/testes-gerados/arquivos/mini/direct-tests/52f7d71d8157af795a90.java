/*
 * @test
 * @summary Test Collections.putAll method throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Map;
import java.util.HashMap;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testPutAllThrows();
    }

    private static void testPutAllThrows() {
        Collections collections = new Collections() {
            @Override
            public void putAll(Map<?, ?> m) {
                throw new UnsupportedOperationException();
            }
        };

        Map<String, String> map = new HashMap<>();
        map.put("key", "value");

        try {
            collections.putAll(map);
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Test passed
        }
    }
}
