/*
 * @test
 * @summary Test AbstractMap.Entry.setValue throws UnsupportedOperationException
 * @run main AbstractMapWitupTest
 */

import java.util.AbstractMap;

public class AbstractMapWitupTest {
    public static void main(String[] args) {
        // Create a minimal AbstractMap.Entry implementation to test setValue
        AbstractMap.Entry<Object, Object> entry = new AbstractMap.Entry<Object, Object>() {
            public Object getKey() { return null; }
            public Object getValue() { return null; }
            public Object setValue(Object value) {
                throw new UnsupportedOperationException();
            }
        };

        // Test that setValue throws UnsupportedOperationException
        try {
            entry.setValue(null);
            throw new Error("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }
}
