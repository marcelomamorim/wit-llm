/*
 * @test
 * @summary Test for ImmutableCollections.indexOf(Object o)
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testIndexOfWithNull();
        testIndexOfWithExistingElement();
        testIndexOfWithNonExistingElement();
    }

    static void testIndexOfWithNull() {
        ImmutableCollections.AbstractImmutableCollection<?> collection = new ImmutableCollections.AbstractImmutableCollection<Object>() {
            @Override
            public int size() { return 3; }
            @Override
            public Object get(int index) {
                if (index == 0) return "a";
                if (index == 1) return "b";
                if (index == 2) return "c";
                throw new IndexOutOfBoundsException();
            }
        };
        try {
            collection.indexOf(null);
            // Expect NullPointerException
        } catch (NullPointerException e) {
            // Test passes
        }
    }

    static void testIndexOfWithExistingElement() {
        ImmutableCollections.AbstractImmutableCollection<?> collection = new ImmutableCollections.AbstractImmutableCollection<Object>() {
            @Override
            public int size() { return 3; }
            @Override
            public Object get(int index) {
                if (index == 0) return "a";
                if (index == 1) return "b";
                if (index == 2) return "c";
                throw new IndexOutOfBoundsException();
            }
        };
        int index = collection.indexOf("b");
        if (index != 1) {
            throw new AssertionError("Expected index 1, got " + index);
        }
    }

    static void testIndexOfWithNonExistingElement() {
        ImmutableCollections.AbstractImmutableCollection<?> collection = new ImmutableCollections.AbstractImmutableCollection<Object>() {
            @Override
            public int size() { return 3; }
            @Override
            public Object get(int index) {
                if (index == 0) return "a";
                if (index == 1) return "b";
                if (index == 2) return "c";
                throw new IndexOutOfBoundsException();
            }
        };
        int index = collection.indexOf("d");
        if (index != -1) {
            throw new AssertionError("Expected -1, got " + index);
        }
    }
}
