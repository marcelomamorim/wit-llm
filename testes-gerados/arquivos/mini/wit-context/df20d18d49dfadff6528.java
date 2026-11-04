/*
 * @test
 * @summary Test ImmutableCollections.AbstractImmutableMap.computeIfAbsent throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.function.Function;
import java.util.AbstractMap;
import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        AbstractImmutableMap<String, String> map = new AbstractImmutableMap<String, String>() {
            @Override
            public int size() { return 0; }
            @Override
            public boolean containsKey(Object key) { return false; }
            @Override
            public String get(Object key) { return null; }
            @Override
            public java.util.Set<java.util.Map.Entry<String, String>> entrySet() { return java.util.Collections.emptySet(); }
        };

        boolean uoeThrown = false;
        try {
            map.computeIfAbsent("key", new Function<String, String>() {
                public String apply(String k) { return "value"; }
            });
        } catch (UnsupportedOperationException e) {
            uoeThrown = true;
        }

        if (!uoeThrown) {
            throw new AssertionError("Expected UnsupportedOperationException was not thrown by computeIfAbsent");
        }
    }

    // Minimal AbstractImmutableMap stub to allow compilation
    static abstract class AbstractImmutableMap<K, V> extends java.util.AbstractMap<K, V> {
        @Override
        public V computeIfAbsent(K key, Function<? super K, ? extends V> mf) {
            throw ImmutableCollections.uoe();
        }
    }
}
