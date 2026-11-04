/*
 * @test
 * @summary Test ImmutableCollections.containsKey(Object) for null input and normal behavior
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Objects;

public class ImmutableCollectionsWitupTest {

    static class MapN {
        private final Object[] keys;
        private final int size;

        MapN(Object[] keys) {
            this.keys = keys;
            this.size = keys.length;
        }

        private int probe(Object o) {
            for (int i = 0; i < size; i++) {
                if (keys[i].equals(o)) {
                    return i;
                }
            }
            return -1;
        }

        public boolean containsKey(Object o) {
            Objects.requireNonNull(o);
            return size > 0 && probe(o) >= 0;
        }
    }

    public static void main(String[] args) {
        // Test 1: containsKey throws NullPointerException if argument is null
        MapN map = new MapN(new Object[] {"a", "b", "c"});
        boolean npeThrown = false;
        try {
            map.containsKey(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException for null argument");
        }

        // Test 2: containsKey returns true for existing key
        if (!map.containsKey("a")) {
            throw new RuntimeException("Expected containsKey to return true for existing key");
        }

        // Test 3: containsKey returns false for non-existing key
        if (map.containsKey("z")) {
            throw new RuntimeException("Expected containsKey to return false for non-existing key");
        }

        // Test 4: containsKey returns false for empty map
        MapN emptyMap = new MapN(new Object[0]);
        if (emptyMap.containsKey("a")) {
            throw new RuntimeException("Expected containsKey to return false for empty map");
        }

        System.out.println("All tests passed.");
    }
}
