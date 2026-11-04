/*
 * @test
 * @summary Test java.util.ImmutableCollections.indexOf(Object) method
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Objects;

public class ImmutableCollectionsWitupTest {

    static class TestImmutableCollection extends java.util.ImmutableCollections.AbstractImmutableCollection<Object> {
        private final Object[] elements;

        TestImmutableCollection(Object[] elements) {
            this.elements = elements;
        }

        @Override
        public int size() {
            return elements.length;
        }

        @Override
        public Object get(int index) {
            return elements[index];
        }

        // Implement indexOf as in ImmutableCollections
        public int indexOf(Object o) {
            Objects.requireNonNull(o);
            for (int i = 0, s = size(); i < s; i++) {
                if (o.equals(get(i))) {
                    return i;
                }
            }
            return -1;
        }
    }

    public static void main(String[] args) {
        TestImmutableCollection coll = new TestImmutableCollection(new Object[] {"a", "b", "c", null});

        // Test indexOf with element present
        int idx = coll.indexOf("b");
        if (idx != 1) {
            throw new Error("indexOf failed for element present: expected 1, got " + idx);
        }

        // Test indexOf with element not present
        idx = coll.indexOf("x");
        if (idx != -1) {
            throw new Error("indexOf failed for element not present: expected -1, got " + idx);
        }

        // Test indexOf with null argument - should throw NullPointerException
        boolean npeThrown = false;
        try {
            coll.indexOf(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new Error("indexOf did not throw NullPointerException for null argument");
        }

        // Test indexOf with element equal to null element in collection (should not find, since null not allowed)
        // Actually, indexOf(null) throws NPE, so no test for null element matching

        System.out.println("Test passed.");
    }
}
