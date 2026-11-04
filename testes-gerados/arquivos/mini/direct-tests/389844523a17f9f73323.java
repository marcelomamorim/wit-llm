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
        testReplaceThrowsUOE();
    }

    static void testReplaceThrowsUOE() {
        Map<String, String> map = new ImmutableCollections.AbstractImmutableMap<String, String>() {
            @Override
            public int size() { return 0; }
            @Override
            public boolean containsKey(Object key) { return false; }
            @Override
            public String get(Object key) { return null; }
            @Override
            public java.util.Set<java.util.Map.Entry<String, String>> entrySet() { return java.util.Collections.emptySet(); }
            @Override
            public String replace(String key, String value) {
                return ImmutableCollections.uoe(); // This line is to satisfy override, but we will call the real method below
            }
        };

        try {
            // We call the real replace method from ImmutableCollections which throws UOE
            new ImmutableCollections.AbstractImmutableMap<String, String>() {
                @Override
                public int size() { return 0; }
                @Override
                public boolean containsKey(Object key) { return false; }
                @Override
                public String get(Object key) { return null; }
                @Override
                public java.util.Set<java.util.Map.Entry<String, String>> entrySet() { return java.util.Collections.emptySet(); }
                @Override
                public String replace(String key, String value) {
                    return ImmutableCollections.uoe();
                }
            }.replace("key", "value");
            throw new AssertionError("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }
}
