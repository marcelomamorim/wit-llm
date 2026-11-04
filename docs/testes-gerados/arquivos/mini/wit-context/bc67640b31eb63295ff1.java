/*
 * @test
 * @summary Test ImmutableCollections.get(Object) method behavior
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Objects;

public class ImmutableCollectionsWitupTest {

    static class MapN<V> {
        final int size;
        final Object[] table;

        MapN(int size, Object[] table) {
            this.size = size;
            this.table = table;
        }

        // probe method simulates index lookup; returns index or -1 if not found
        int probe(Object o) {
            if (o == null) return -1;
            for (int i = 0; i < table.length; i += 2) {
                if (o.equals(table[i])) {
                    return i;
                }
            }
            return -1;
        }

        public V get(Object o) {
            if (size == 0) {
                Objects.requireNonNull(o);
                return null;
            }
            int i = probe(o);
            if (i >= 0) {
                @SuppressWarnings("unchecked")
                V value = (V)table[i+1];
                return value;
            } else {
                return null;
            }
        }
    }

    public static void main(String[] args) {
        testGetWithNullKeyAndEmptyMap();
        testGetWithNullKeyAndNonEmptyMap();
        testGetWithPresentKey();
        testGetWithAbsentKey();
    }

    static void testGetWithNullKeyAndEmptyMap() {
        MapN<String> emptyMap = new MapN<>(0, new Object[0]);
        try {
            emptyMap.get(null);
            throw new RuntimeException("Expected NullPointerException for null key on empty map");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testGetWithNullKeyAndNonEmptyMap() {
        Object[] table = {"key1", "value1"};
        MapN<String> map = new MapN<>(1, table);
        // Should not throw, returns null because null key not found
        String result = map.get(null);
        if (result != null) {
            throw new RuntimeException("Expected null for null key on non-empty map");
        }
    }

    static void testGetWithPresentKey() {
        Object[] table = {"key1", "value1", "key2", "value2"};
        MapN<String> map = new MapN<>(2, table);
        String v1 = map.get("key1");
        if (!"value1".equals(v1)) {
            throw new RuntimeException("Expected value1 for key1");
        }
        String v2 = map.get("key2");
        if (!"value2".equals(v2)) {
            throw new RuntimeException("Expected value2 for key2");
        }
    }

    static void testGetWithAbsentKey() {
        Object[] table = {"key1", "value1"};
        MapN<String> map = new MapN<>(1, table);
        String result = map.get("absent");
        if (result != null) {
            throw new RuntimeException("Expected null for absent key");
        }
    }
}
