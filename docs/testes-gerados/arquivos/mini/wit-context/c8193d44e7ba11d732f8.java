/*
 * @test
 * @summary Test Collections.add(Map.Entry) throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Map;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        // Create a dummy Map.Entry implementation for testing
        Map.Entry<Object, Object> entry = new Map.Entry<Object, Object>() {
            public Object getKey() { return "key"; }
            public Object getValue() { return "value"; }
            public Object setValue(Object value) { throw new UnsupportedOperationException(); }
        };

        boolean thrown = false;
        try {
            Collections.add(entry);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }

        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException was not thrown");
        }
    }
}
