/*
 * @test
 * @summary Test ImmutableCollections.AbstractImmutableMap.putIfAbsent throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.ImmutableCollections;
import java.util.Map;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        testPutIfAbsentThrowsUOE();
    }

    static void testPutIfAbsentThrowsUOE() {
        ImmutableCollections.AbstractImmutableMap<String, String> map = new ImmutableCollections.AbstractImmutableMap<String, String>() {
            @Override
            public int size() { return 0; }
            @Override
            public boolean containsKey(Object key) { return false; }
            @Override
            public String get(Object key) { return null; }
            @Override
            public java.util.Set<Entry<String, String>> entrySet() { return java.util.Collections.emptySet(); }
        };

        try {
            map.putIfAbsent("key", "value");
            throw new AssertionError("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }
}
