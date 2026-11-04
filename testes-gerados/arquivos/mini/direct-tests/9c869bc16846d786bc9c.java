/*
 * @test
 * @summary Test that ImmutableCollections.replace throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Map;
import java.util.AbstractMap;
import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        // Create an anonymous subclass of ImmutableCollections to access replace method
        Map<String, String> map = new ImmutableCollections() {
            @Override
            public boolean replace(Object key, Object oldValue, Object newValue) {
                return super.replace((String)key, (String)oldValue, (String)newValue);
            }
        };

        // Test that replace throws UnsupportedOperationException
        try {
            map.replace("key", "old", "new");
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Expected exception
        }
    }
}
