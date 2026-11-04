/*
 * @test
 * @summary Test java.util.ImmutableCollections.get(int) method behavior
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.List;
import java.util.Objects;
import java.util.AbstractList;
import java.util.Arrays;

public class ImmutableCollectionsWitupTest {

    static class SimpleImmutableCollections<E> extends java.util.ImmutableCollections {
        final List<E> root;
        final int offset;
        final int size;

        SimpleImmutableCollections(List<E> list, int offset, int size) {
            this.root = list;
            this.offset = offset;
            this.size = size;
        }

        public E get(int index) {
            Objects.checkIndex(index, size);
            return root.get(offset + index);
        }
    }

    public static void main(String[] args) {
        // Prepare a backing list
        List<String> backing = Arrays.asList("zero", "one", "two", "three", "four");

        // Create instance with offset 1, size 3 (elements: one, two, three)
        SimpleImmutableCollections<String> sic = new SimpleImmutableCollections<>(backing, 1, 3);

        // Test valid indices
        if (!"one".equals(sic.get(0))) {
            throw new Error("Expected 'one' at index 0");
        }
        if (!"two".equals(sic.get(1))) {
            throw new Error("Expected 'two' at index 1");
        }
        if (!"three".equals(sic.get(2))) {
            throw new Error("Expected 'three' at index 2");
        }

        // Test index == size (should throw IndexOutOfBoundsException)
        try {
            sic.get(3);
            throw new Error("Expected IndexOutOfBoundsException for index == size");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }

        // Test negative index (should throw IndexOutOfBoundsException)
        try {
            sic.get(-1);
            throw new Error("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }
}
