/*
 * @test
 * @summary Test that ImmutableCollections.compute throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.function.BiFunction;
import java.util.Map;
import java.util.AbstractMap;

public class ImmutableCollectionsWitupTest {

    static class TestMap<K,V> extends java.util.ImmutableCollections {
        @Override
        public V compute(K key, BiFunction<? super K, ? super V, ? extends V> rf) {
            throw uoe();
        }
    }

    public static void main(String[] args) {
        TestMap<String, String> map = new TestMap<>();
        BiFunction<String, String, String> rf = new BiFunction<String, String, String>() {
            public String apply(String k, String v) {
                return "value";
            }
        };

        try {
            map.compute("key", rf);
            throw new RuntimeException("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }
}
