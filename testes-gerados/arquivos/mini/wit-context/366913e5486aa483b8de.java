/*
 * @test
 * @summary Test that ImmutableCollections.AbstractImmutableMap.replaceAll always throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.function.BiFunction;
import java.util.Map;
import java.util.AbstractMap;

public class ImmutableCollectionsWitupTest {

    static class TestImmutableMap<K,V> extends java.util.ImmutableCollections.AbstractImmutableMap<K,V> {
        @Override
        public int size() { return 0; }
        @Override
        public boolean containsKey(Object key) { return false; }
        @Override
        public V get(Object key) { return null; }
        @Override
        public java.util.Set<java.util.Map.Entry<K,V>> entrySet() { return java.util.Collections.emptySet(); }
    }

    public static void main(String[] args) {
        TestImmutableMap<String,String> map = new TestImmutableMap<>();
        BiFunction<String,String,String> f = new BiFunction<String,String,String>() {
            public String apply(String k, String v) {
                return v + "_modified";
            }
        };

        try {
            map.replaceAll(f);
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }
}
