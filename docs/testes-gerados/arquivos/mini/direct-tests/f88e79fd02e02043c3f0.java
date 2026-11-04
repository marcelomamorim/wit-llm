/*
 * @test
 * @summary Test ImmutableCollections.contains(Object) method behavior
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Objects;

public class ImmutableCollectionsWitupTest {

    static class TestImmutableCollections extends java.util.ImmutableCollections {
        private final int size;
        private final Object[] elements;

        TestImmutableCollections(Object[] elements) {
            this.elements = elements;
            this.size = elements.length;
        }

        // mimic probe method: returns index if found, else -1
        int probe(Object o) {
            for (int i = 0; i < elements.length; i++) {
                if (Objects.equals(elements[i], o)) {
                    return i;
                }
            }
            return -1;
        }

        public boolean contains(Object o) {
            Objects.requireNonNull(o);
            return size > 0 && probe(o) >= 0;
        }
    }

    public static void main(String[] args) {
        TestImmutableCollections coll = new TestImmutableCollections(new Object[] {"a", "b", "c"});

        // Test contains with element present
        if (!coll.contains("a")) {
            throw new AssertionError("contains should return true for existing element 'a'");
        }

        // Test contains with element absent
        if (coll.contains("z")) {
            throw new AssertionError("contains should return false for non-existing element 'z'");
        }

        // Test contains with null argument throws NullPointerException
        boolean npeThrown = false;
        try {
            coll.contains(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("contains should throw NullPointerException for null argument");
        }

        // Test contains on empty collection
        TestImmutableCollections emptyColl = new TestImmutableCollections(new Object[0]);
        if (emptyColl.contains("a")) {
            throw new AssertionError("contains should return false for any element on empty collection");
        }

        System.out.println("All tests passed.");
    }
}
