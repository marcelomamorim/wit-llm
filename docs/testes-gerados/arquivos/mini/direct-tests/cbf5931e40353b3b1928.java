/*
 * @test
 * @summary Test java.util.ImmutableCollections.get(int) method behavior
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.AbstractList;

public class ImmutableCollectionsWitupTest {

    static class TestImmutableCollections<E> extends java.util.ImmutableCollections.AbstractImmutableList<E> {
        final E e0;
        final E e1;

        TestImmutableCollections(E e0, E e1) {
            this.e0 = e0;
            this.e1 = e1;
        }

        @Override
        public E get(int index) {
            if (index == 0) {
                return e0;
            } else if (index == 1 && e1 != null) {
                return e1;
            }
            throw new IndexOutOfBoundsException("Index: " + index);
        }

        @Override
        public int size() {
            return e1 == null ? 1 : 2;
        }
    }

    public static void main(String[] args) {
        // Test get(0) returns e0
        TestImmutableCollections<String> list1 = new TestImmutableCollections<>("first", "second");
        if (!"first".equals(list1.get(0))) {
            throw new Error("get(0) did not return e0");
        }

        // Test get(1) returns e1 when e1 != null
        if (!"second".equals(list1.get(1))) {
            throw new Error("get(1) did not return e1 when e1 != null");
        }

        // Test get(1) throws when e1 == null
        TestImmutableCollections<String> list2 = new TestImmutableCollections<>("only", null);
        if (!"only".equals(list2.get(0))) {
            throw new Error("get(0) did not return e0 when e1 == null");
        }
        try {
            list2.get(1);
            throw new Error("get(1) did not throw when e1 == null");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }

        // Test get with index < 0 throws
        try {
            list1.get(-1);
            throw new Error("get(-1) did not throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }

        // Test get with index > 1 throws
        try {
            list1.get(2);
            throw new Error("get(2) did not throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }
}
