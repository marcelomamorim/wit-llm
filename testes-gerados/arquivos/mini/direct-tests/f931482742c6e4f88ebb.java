/*
 * @test
 * @summary Test that ImmutableCollections putIfAbsent throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Map;
import java.util.AbstractMap;
import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        // Create an anonymous subclass of ImmutableCollections to access putIfAbsent
        Map<String, String> map = new ImmutableCollections.AbstractImmutableMap<String, String>() {
            @Override
            public int size() { return 0; }
            @Override
            public boolean containsKey(Object key) { return false; }
            @Override
            public String get(Object key) { return null; }
            @Override
            public java.util.Set<Entry<String, String>> entrySet() { return java.util.Collections.emptySet(); }
            @Override
            public String putIfAbsent(String key, String value) {
                return ImmutableCollections.uoe();
            }
        };

        try {
            map.putIfAbsent("key", "value");
            throw new RuntimeException("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }
}
