/*
 * @test
 * @summary Test that ImmutableCollections putAll method throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        // Create an anonymous subclass of ImmutableCollections to access putAll
        Map<Object, Object> map = new ImmutableCollections.AbstractImmutableMap<Object, Object>() {
            @Override
            public int size() { return 0; }
            @Override
            public boolean containsKey(Object key) { return false; }
            @Override
            public Object get(Object key) { return null; }
            @Override
            public java.util.Set<Entry<Object, Object>> entrySet() { return Collections.emptySet(); }
            @Override
            public void putAll(Map<? extends Object, ? extends Object> m) {
                super.putAll(m);
            }
        };

        Map<Object, Object> toPut = new HashMap<>();
        toPut.put("key", "value");

        try {
            map.putAll(toPut);
            throw new RuntimeException("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // Test passed
        }
    }
}
