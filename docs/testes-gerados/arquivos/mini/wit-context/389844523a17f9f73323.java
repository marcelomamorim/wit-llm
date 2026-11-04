/*
 * @test
 * @summary Test ImmutableCollections.replace method throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        ImmutableCollections.AbstractImmutableMap<String, String> map = new ImmutableCollections.AbstractImmutableMap<String, String>() {
            @Override public int size() { return 0; }
            @Override public boolean containsKey(Object key) { return false; }
            @Override public String get(Object key) { return null; }
            @Override public java.util.Set<java.util.Map.Entry<String, String>> entrySet() { return java.util.Collections.emptySet(); }
        };

        try {
            map.replace("key", "value");
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }
}
