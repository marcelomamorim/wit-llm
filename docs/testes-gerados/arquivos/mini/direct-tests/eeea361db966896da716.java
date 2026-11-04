/*
 * @test
 * @summary Test Collections.putIfAbsent throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Map;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        Map<String, String> map = new Collections() {
            @Override
            public String putIfAbsent(String key, String value) {
                return super.putIfAbsent(key, value);
            }
        };

        try {
            map.putIfAbsent("key", "value");
            throw new RuntimeException("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // Test passed
        }
    }
}
