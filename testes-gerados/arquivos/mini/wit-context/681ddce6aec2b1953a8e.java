/*
 * @test
 * @summary Test ImmutableCollections.AbstractImmutableMap.merge throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.function.BiFunction;
import java.util.Map;

public class ImmutableCollectionsWitupTest {

    static class TestImmutableMap<K, V> extends java.util.ImmutableCollections.AbstractImmutableMap<K, V> {
        @Override
        public V get(Object key) { return null; }
        @Override
        public int size() { return 0; }
    }

    public static void main(String[] args) {
        TestImmutableMap<String, String> map = new TestImmutableMap<>();
        BiFunction<String, String, String> rf = new BiFunction<String, String, String>() {
            @Override
            public String apply(String v1, String v2) {
                return v1 + v2;
            }
        };

        try {
            map.merge("key", "value", rf);
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }
}
