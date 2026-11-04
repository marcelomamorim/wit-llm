/*
 * @test
 * @summary Test ImmutableCollections.remove(Object key, Object value) throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        ImmutableCollections.AbstractImmutableMap<Object, Object> map = new ImmutableCollections.AbstractImmutableMap<Object, Object>() {
            @Override
            public int size() { return 0; }
            @Override
            public boolean containsKey(Object key) { return false; }
            @Override
            public Object get(Object key) { return null; }
            @Override
            public java.util.Set<java.util.Map.Entry<Object, Object>> entrySet() { return java.util.Collections.emptySet(); }
        };

        boolean threw = false;
        try {
            map.remove(new Object(), new Object());
        } catch (UnsupportedOperationException e) {
            threw = true;
        }

        if (!threw) {
            throw new AssertionError("Expected UnsupportedOperationException from remove(key, value)");
        }
    }
}
