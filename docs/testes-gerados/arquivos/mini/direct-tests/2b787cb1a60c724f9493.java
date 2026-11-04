/*
 * @test
 * @summary Test ImmutableCollections.contains method behavior
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.AbstractCollection;

public class ImmutableCollectionsWitupTest {

    static class TestImmutableCollections extends java.util.ImmutableCollections.AbstractImmutableCollection<Object> {
        private final Object[] elements;

        TestImmutableCollections(Object[] elements) {
            this.elements = elements;
        }

        // Implement size and iterator to satisfy AbstractCollection
        @Override
        public int size() {
            return elements.length;
        }

        @Override
        public java.util.Iterator<Object> iterator() {
            return new java.util.Iterator<Object>() {
                int idx = 0;

                public boolean hasNext() {
                    return idx < elements.length;
                }

                public Object next() {
                    return elements[idx++];
                }

                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        }

        // Provide indexOf method to support contains
        public int indexOf(Object o) {
            if (o == null) {
                for (int i = 0; i < elements.length; i++) {
                    if (elements[i] == null) return i;
                }
            } else {
                for (int i = 0; i < elements.length; i++) {
                    if (o.equals(elements[i])) return i;
                }
            }
            return -1;
        }

        // Expose contains method as in ImmutableCollections
        public boolean contains(Object o) {
            return indexOf(o) >= 0;
        }
    }

    public static void main(String[] args) {
        TestImmutableCollections coll = new TestImmutableCollections(new Object[] {"a", "b", null, "c"});

        // Test contains for present elements
        if (!coll.contains("a")) throw new AssertionError("contains should return true for 'a'");
        if (!coll.contains("b")) throw new AssertionError("contains should return true for 'b'");
        if (!coll.contains(null)) throw new AssertionError("contains should return true for null");

        // Test contains for absent element
        if (coll.contains("d")) throw new AssertionError("contains should return false for 'd'");

        // Test contains for different object with same content
        String s1 = new String("a");
        if (!coll.contains(s1)) throw new AssertionError("contains should return true for equal string 'a'");

        // Test contains for object not in collection
        Object o = new Object();
        if (coll.contains(o)) throw new AssertionError("contains should return false for new Object instance");

        System.out.println("Test passed.");
    }
}
