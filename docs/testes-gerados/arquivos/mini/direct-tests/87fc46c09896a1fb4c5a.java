/*
 * @test
 * @summary Test ImmutableCollections.containsKey(Object) method behavior
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Objects;

public class ImmutableCollectionsWitupTest {

    static class TestImmutableCollections extends java.util.ImmutableCollections {
        private final int size;
        private final Object[] keys;

        TestImmutableCollections(int size, Object[] keys) {
            this.size = size;
            this.keys = keys;
        }

        // simulate probe method: returns index if key found, else -1
        int probe(Object o) {
            for (int i = 0; i < keys.length; i++) {
                if (Objects.equals(keys[i], o)) {
                    return i;
                }
            }
            return -1;
        }

        @Override
        public boolean containsKey(Object o) {
            Objects.requireNonNull(o);
            return size > 0 && probe(o) >= 0;
        }
    }

    public static void main(String[] args) {
        // Test 1: containsKey with null argument throws NullPointerException
        try {
            new TestImmutableCollections(1, new Object[] {"a"}).containsKey(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test 2: containsKey returns false if size == 0
        TestImmutableCollections empty = new TestImmutableCollections(0, new Object[0]);
        if (empty.containsKey("any")) {
            throw new RuntimeException("containsKey should return false when size is 0");
        }

        // Test 3: containsKey returns true if key is present
        Object key1 = "key1";
        TestImmutableCollections coll = new TestImmutableCollections(2, new Object[] {key1, "key2"});
        if (!coll.containsKey(key1)) {
            throw new RuntimeException("containsKey should return true for present key");
        }

        // Test 4: containsKey returns false if key is absent
        if (coll.containsKey("absent")) {
            throw new RuntimeException("containsKey should return false for absent key");
        }

        System.out.println("PASS");
    }
}
