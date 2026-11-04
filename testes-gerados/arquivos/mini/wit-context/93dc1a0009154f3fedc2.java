/*
 * @test
 * @summary Test ImmutableCollections.AbstractImmutableList.sort throws UnsupportedOperationException
 * @run main java.util.ImmutableCollectionsWitupTest
 */

import java.util.Comparator;
import java.util.List;
import java.util.AbstractList;
import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {

    static class TestImmutableList<E> extends ImmutableCollections.AbstractImmutableList<E> {
        private final E[] elements;

        @SafeVarargs
        TestImmutableList(E... elements) {
            this.elements = elements.clone();
        }

        @Override
        public E get(int index) {
            return elements[index];
        }

        @Override
        public int size() {
            return elements.length;
        }
    }

    public static void main(String[] args) {
        TestImmutableList<String> list = new TestImmutableList<>("a", "b", "c");
        try {
            list.sort(Comparator.naturalOrder());
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }
}
