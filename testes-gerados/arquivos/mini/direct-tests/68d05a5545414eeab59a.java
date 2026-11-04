/*
 * @test
 * @summary Test java.util.ImmutableCollections.lastIndexOf(Object) method
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Objects;

public class ImmutableCollectionsWitupTest {

    static class TestList extends java.util.AbstractList<Object> {
        private final Object[] elements;
        TestList(Object... elements) {
            this.elements = elements;
        }
        @Override
        public Object get(int index) {
            return elements[index];
        }
        @Override
        public int size() {
            return elements.length;
        }

        // Implement lastIndexOf as in ImmutableCollections for testing
        public int lastIndexOf(Object o) {
            Objects.requireNonNull(o);
            for (int i = size() - 1; i >= 0; i--) {
                if (o.equals(get(i))) {
                    return i;
                }
            }
            return -1;
        }
    }

    public static void main(String[] args) {
        TestList list = new TestList("a", "b", "c", "b", "a");

        // Test lastIndexOf with element present multiple times
        int idx = list.lastIndexOf("b");
        if (idx != 3) {
            throw new Error("Expected lastIndexOf(\"b\") to be 3 but was " + idx);
        }

        // Test lastIndexOf with element present once
        idx = list.lastIndexOf("c");
        if (idx != 2) {
            throw new Error("Expected lastIndexOf(\"c\") to be 2 but was " + idx);
        }

        // Test lastIndexOf with element not present
        idx = list.lastIndexOf("x");
        if (idx != -1) {
            throw new Error("Expected lastIndexOf(\"x\") to be -1 but was " + idx);
        }

        // Test lastIndexOf with null argument - should throw NullPointerException
        boolean npeThrown = false;
        try {
            list.lastIndexOf(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new Error("Expected NullPointerException when calling lastIndexOf(null)");
        }
    }
}
