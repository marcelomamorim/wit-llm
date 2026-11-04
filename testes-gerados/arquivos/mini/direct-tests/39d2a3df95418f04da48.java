/*
 * @test
 * @summary Test that ImmutableCollections put method throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Map;
import java.util.AbstractMap;
import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        // Create an anonymous subclass of ImmutableCollections.AbstractImmutableMap to test put
        Map<String, String> map = new ImmutableCollections.AbstractImmutableMap<String, String>() {
            @Override
            public int size() { return 0; }
            @Override
            public boolean containsKey(Object key) { return false; }
            @Override
            public String get(Object key) { return null; }
        };

        try {
            map.put("key", "value");
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Test passed
        }
    }
}
