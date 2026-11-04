/*
 * @test
 * @summary Test that ImmutableCollections.merge throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.function.BiFunction;

public class ImmutableCollectionsWitupTest {
    static class TestImmutableCollections extends java.util.ImmutableCollections {
        @Override
        public <K, V> V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> rf) {
            return super.merge(key, value, rf);
        }
    }

    public static void main(String[] args) {
        TestImmutableCollections coll = new TestImmutableCollections();
        boolean thrown = false;
        try {
            coll.merge("key", "value", new BiFunction<String, String, String>() {
                @Override
                public String apply(String v1, String v2) {
                    return v1 + v2;
                }
            });
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException not thrown");
        }
    }
}
