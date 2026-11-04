/*
 * @test
 * @summary Test ImmutableCollections.AbstractImmutableList.lastIndexOf behavior including null argument
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Objects;
import java.util.List;
import java.util.AbstractList;

public class ImmutableCollectionsWitupTest {

    // Minimal AbstractImmutableList subclass to test lastIndexOf
    static class TestImmutableList<E> extends ImmutableCollections.AbstractImmutableList<E> {
        private final Object[] elements;

        TestImmutableList(Object[] elements) {
            this.elements = elements.clone();
        }

        @Override
        public E get(int index) {
            if (index < 0 || index >= elements.length) {
                throw new IndexOutOfBoundsException();
            }
            @SuppressWarnings("unchecked")
            E e = (E) elements[index];
            return e;
        }

        @Override
        public int size() {
            return elements.length;
        }
    }

    public static void main(String[] args) {
        testLastIndexOfWithNullArgument();
        testLastIndexOfWithPresentElement();
        testLastIndexOfWithAbsentElement();
        testLastIndexOfWithEmptyList();
    }

    private static void testLastIndexOfWithNullArgument() {
        TestImmutableList<String> list = new TestImmutableList<>(new String[] {"a", "b", "c"});
        try {
            list.lastIndexOf(null);
            throw new AssertionError("Expected NullPointerException for null argument");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testLastIndexOfWithPresentElement() {
        TestImmutableList<String> list = new TestImmutableList<>(new String[] {"a", "b", "c", "b"});
        int idx = list.lastIndexOf("b");
        if (idx != 3) {
            throw new AssertionError("Expected lastIndexOf to return 3 but got " + idx);
        }
    }

    private static void testLastIndexOfWithAbsentElement() {
        TestImmutableList<String> list = new TestImmutableList<>(new String[] {"a", "b", "c"});
        int idx = list.lastIndexOf("x");
        if (idx != -1) {
            throw new AssertionError("Expected lastIndexOf to return -1 for absent element but got " + idx);
        }
    }

    private static void testLastIndexOfWithEmptyList() {
        TestImmutableList<String> list = new TestImmutableList<>(new String[0]);
        int idx = list.lastIndexOf("a");
        if (idx != -1) {
            throw new AssertionError("Expected lastIndexOf to return -1 for empty list but got " + idx);
        }
    }
}
