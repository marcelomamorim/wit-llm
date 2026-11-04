/*
 * @test
 * @summary Test ImmutableCollections.get(int) for index bounds and normal access
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.List;
import java.util.Objects;

public class ImmutableCollectionsWitupTest {

    static class DummySubList<E> extends java.util.ImmutableCollections.SubList<E> {
        DummySubList(List<E> root, int offset, int size) {
            super(root, offset, size);
        }

        @Override
        public E get(int index) {
            return super.get(index);
        }
    }

    public static void main(String[] args) {
        // Prepare a root list with known elements
        List<String> root = java.util.List.of("a", "b", "c", "d", "e");

        // Create a SubList view from offset 1, size 3 -> elements "b", "c", "d"
        DummySubList<String> subList = new DummySubList<>(root, 1, 3);

        // Test valid indices
        for (int i = 0; i < 3; i++) {
            String expected = root.get(1 + i);
            String actual = subList.get(i);
            if (!Objects.equals(expected, actual)) {
                throw new AssertionError("Expected element at index " + i + " to be '" + expected + "' but got '" + actual + "'");
            }
        }

        // Test index == size (3) -> should throw IndexOutOfBoundsException
        try {
            subList.get(3);
            throw new AssertionError("Expected IndexOutOfBoundsException for index == size");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }

        // Test index < 0 -> should throw IndexOutOfBoundsException
        try {
            subList.get(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }

        System.out.println("ImmutableCollections.get(int) tests passed.");
    }
}
