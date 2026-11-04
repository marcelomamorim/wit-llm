/*
 * @test
 * @summary Test ImmutableCollections.AbstractImmutableMap.computeIfPresent throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.function.BiFunction;
import java.util.Map;

public class ImmutableCollectionsWitupTest {

    static class TestImmutableMap<K, V> extends java.util.ImmutableCollections.AbstractImmutableMap<K, V> {
        @Override
        public V get(Object key) { return null; }
        @Override
        public boolean containsKey(Object key) { return false; }
        @Override
        public java.util.Set<Entry<K, V>> entrySet() { return java.util.Collections.emptySet(); }
    }

    public static void main(String[] args) {
        TestImmutableMap<String, String> map = new TestImmutableMap<>();
        BiFunction<String, String, String> remappingFunction = new BiFunction<String, String, String>() {
            @Override
            public String apply(String k, String v) {
                return v + "_modified";
            }
        };

        try {
            map.computeIfPresent("key", remappingFunction);
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }
}
