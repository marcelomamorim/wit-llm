/*
 * @test
 * @summary Test that ImmutableCollections.sort throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Comparator;
import java.util.List;
import java.util.AbstractList;
import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {

    static class TestImmutableList<E> extends ImmutableCollections.AbstractImmutableList<E> {
        @Override
        public E get(int index) {
            throw new IndexOutOfBoundsException();
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public void sort(Comparator<? super E> c) {
            throw ImmutableCollections.uoe();
        }
    }

    public static void main(String[] args) {
        TestImmutableList<String> list = new TestImmutableList<>();
        boolean thrown = false;
        try {
            list.sort(null);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException");
        }
    }
}
