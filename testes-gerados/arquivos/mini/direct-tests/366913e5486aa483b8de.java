/*
 * @test
 * @summary Test that ImmutableCollections.replaceAll throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.function.BiFunction;
import java.util.Map;
import java.util.AbstractMap;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        // Create an anonymous subclass of ImmutableCollections to access replaceAll
        Map<Object, Object> map = new Map<Object, Object>() {
            @Override
            public int size() { return 0; }
            @Override
            public boolean isEmpty() { return true; }
            @Override
            public boolean containsKey(Object key) { return false; }
            @Override
            public boolean containsValue(Object value) { return false; }
            @Override
            public Object get(Object key) { return null; }
            @Override
            public Object put(Object key, Object value) { throw new UnsupportedOperationException(); }
            @Override
            public Object remove(Object key) { throw new UnsupportedOperationException(); }
            @Override
            public void putAll(java.util.Map<? extends Object, ? extends Object> m) { throw new UnsupportedOperationException(); }
            @Override
            public void clear() { throw new UnsupportedOperationException(); }
            @Override
            public java.util.Set<Object> keySet() { return java.util.Collections.emptySet(); }
            @Override
            public java.util.Collection<Object> values() { return java.util.Collections.emptySet(); }
            @Override
            public java.util.Set<java.util.Map.Entry<Object, Object>> entrySet() { return java.util.Collections.emptySet(); }
            // Implement replaceAll method from Map interface to call ImmutableCollections.replaceAll
            @Override
            public void replaceAll(BiFunction<? super Object, ? super Object, ? extends Object> f) {
                throw new UnsupportedOperationException();
            }
        };

        boolean thrown = false;
        try {
            map.replaceAll(new BiFunction<Object, Object, Object>() {
                @Override
                public Object apply(Object k, Object v) {
                    return v;
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
