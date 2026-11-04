/*
 * @test
 * @summary Test ImmutableCollections.AbstractImmutableMap.put throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.ImmutableCollections;
import java.util.Map;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        testPutThrowsUOE();
    }

    static void testPutThrowsUOE() {
        ImmutableCollections.AbstractImmutableMap<String, String> map = new ImmutableCollections.AbstractImmutableMap<String, String>() {
            @Override public int size() { return 0; }
            @Override public boolean containsKey(Object key) { return false; }
            @Override public String get(Object key) { return null; }
            @Override public java.util.Set<java.util.Map.Entry<String, String>> entrySet() { return java.util.Collections.emptySet(); }
        };

        try {
            map.put("key", "value");
            throw new AssertionError("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }
}
