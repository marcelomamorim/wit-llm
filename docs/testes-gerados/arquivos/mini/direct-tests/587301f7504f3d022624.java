/*
 * @test
 * @summary Test that ImmutableCollections.addAll(int, Collection) throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
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
        // addAll(int, Collection) is inherited and throws UOE
    }

    public static void main(String[] args) {
        TestImmutableList<String> list = new TestImmutableList<>();
        Collection<String> c = new ArrayList<>();
        c.add("test");

        // The tested method is addAll(int, Collection) which always throws UOE
        try {
            list.addAll(0, c);
            throw new RuntimeException("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected
        }

        // Also test that other mutating methods throw UOE
        try {
            list.add("x");
            throw new RuntimeException("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
        }

        try {
            list.remove("x");
            throw new RuntimeException("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
        }

        try {
            list.clear();
            throw new RuntimeException("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
        }
    }
}
