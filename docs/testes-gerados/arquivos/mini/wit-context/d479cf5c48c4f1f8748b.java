/*
 * @test
 * @summary Test ImmutableCollections.AbstractImmutableMap.compute throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.function.BiFunction;
import java.util.Map;
import java.util.AbstractMap;

public class ImmutableCollectionsWitupTest {

    static class TestImmutableMap<K, V> extends java.util.ImmutableCollections.AbstractImmutableMap<K, V> {
        @Override
        public V get(Object key) {
            return null;
        }

        @Override
        public boolean containsKey(Object key) {
            return false;
        }

        @Override
        public java.util.Set<Entry<K, V>> entrySet() {
            return java.util.Collections.emptySet();
        }

        @Override
        public V compute(K key, BiFunction<? super K, ? super V, ? extends V> rf) {
            return super.compute(key, rf);
        }
    }

    public static void main(String[] args) {
        TestImmutableMap<String, String> map = new TestImmutableMap<>();
        boolean threw = false;
        try {
            map.compute("key", new BiFunction<String, String, String>() {
                @Override
                public String apply(String k, String v) {
                    return "value";
                }
            });
        } catch (UnsupportedOperationException uoe) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected UnsupportedOperationException from compute method");
        }
    }
}
