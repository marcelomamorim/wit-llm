/*
 * @test
 * @summary Test ImmutableCollections.get(int) method for expected behavior and exception
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {

    static class TestImmutableCollections<E> extends ImmutableCollections {
        final E e0;
        final E e1;

        TestImmutableCollections(E e0, E e1) {
            this.e0 = e0;
            this.e1 = e1;
        }

        @SuppressWarnings("unchecked")
        public E get(int index) {
            if (index == 0) {
                return e0;
            } else if (index == 1 && e1 != null) {
                return e1;
            }
            throw outOfBounds(index);
        }

        private IndexOutOfBoundsException outOfBounds(int index) {
            return new IndexOutOfBoundsException("Index: " + index);
        }
    }

    public static void main(String[] args) {
        // Create instance with two elements
        TestImmutableCollections<String> coll = new TestImmutableCollections<>("first", "second");

        // Test get(0) returns first element
        String first = coll.get(0);
        if (!"first".equals(first)) {
            throw new AssertionError("Expected 'first' for get(0), got: " + first);
        }

        // Test get(1) returns second element
        String second = coll.get(1);
        if (!"second".equals(second)) {
            throw new AssertionError("Expected 'second' for get(1), got: " + second);
        }

        // Create instance with only one element (e1 == null)
        TestImmutableCollections<String> collSingle = new TestImmutableCollections<>("only", null);

        // Test get(0) returns only element
        String only = collSingle.get(0);
        if (!"only".equals(only)) {
            throw new AssertionError("Expected 'only' for get(0), got: " + only);
        }

        // Test get(1) throws IndexOutOfBoundsException when e1 == null
        try {
            collSingle.get(1);
            throw new AssertionError("Expected IndexOutOfBoundsException for get(1) when e1 is null");
        } catch (IndexOutOfBoundsException ex) {
            // expected
        }

        // Test get with invalid index (e.g. 2) throws IndexOutOfBoundsException
        try {
            coll.get(2);
            throw new AssertionError("Expected IndexOutOfBoundsException for get(2)");
        } catch (IndexOutOfBoundsException ex) {
            // expected
        }

        // Test get with negative index throws IndexOutOfBoundsException
        try {
            coll.get(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for get(-1)");
        } catch (IndexOutOfBoundsException ex) {
            // expected
        }
    }
}
