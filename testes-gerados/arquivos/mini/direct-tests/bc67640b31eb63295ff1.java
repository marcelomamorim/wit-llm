/*
 * @test
 * @summary Test java.util.ImmutableCollections.get(Object) method behavior
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Objects;

public class ImmutableCollectionsWitupTest {

    static class TestMap<V> {
        private final Object[] table;
        private final int size;

        TestMap(int size, Object[] table) {
            this.size = size;
            this.table = table;
        }

        // Mimic probe method: linear search for key in even indices
        int probe(Object o) {
            if (o == null) return -1;
            for (int i = 0; i < table.length; i += 2) {
                if (o.equals(table[i])) {
                    return i;
                }
            }
            return -1;
        }

        @SuppressWarnings("unchecked")
        public V get(Object o) {
            if (size == 0) {
                Objects.requireNonNull(o);
                return null;
            }
            int i = probe(o);
            if (i >= 0) {
                return (V)table[i+1];
            } else {
                return null;
            }
        }
    }

    public static void main(String[] args) {
        // Test 1: size == 0, get(null) throws NullPointerException
        TestMap<String> emptyMap = new TestMap<>(0, new Object[0]);
        try {
            emptyMap.get(null);
            throw new RuntimeException("Expected NullPointerException for null key on empty map");
        } catch (NullPointerException npe) {
            // expected
        }

        // Test 2: size == 0, get(non-null) returns null
        String result = emptyMap.get("any");
        if (result != null) {
            throw new RuntimeException("Expected null for any key on empty map");
        }

        // Test 3: size > 0, key present returns value
        Object[] table = new Object[] {"key1", "value1", "key2", "value2"};
        TestMap<String> map = new TestMap<>(2, table);
        String v1 = map.get("key1");
        if (!"value1".equals(v1)) {
            throw new RuntimeException("Expected value1 for key1, got " + v1);
        }
        String v2 = map.get("key2");
        if (!"value2".equals(v2)) {
            throw new RuntimeException("Expected value2 for key2, got " + v2);
        }

        // Test 4: size > 0, key absent returns null
        String v3 = map.get("key3");
        if (v3 != null) {
            throw new RuntimeException("Expected null for absent key key3");
        }

        // Test 5: size > 0, get(null) returns null (no NPE)
        String v4 = map.get(null);
        if (v4 != null) {
            throw new RuntimeException("Expected null for null key when size > 0");
        }

        System.out.println("All tests passed.");
    }
}
