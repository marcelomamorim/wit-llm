/*
 * @test
 * @summary Test ImmutableCollections.AbstractImmutableList.add(int, E) throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.AbstractList;
import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        testAddThrowsUOE();
    }

    static void testAddThrowsUOE() {
        ImmutableCollections.AbstractImmutableList<String> list = new ImmutableCollections.AbstractImmutableList<String>() {
            @Override
            public String get(int index) {
                throw new IndexOutOfBoundsException();
            }

            @Override
            public int size() {
                return 0;
            }
        };

        try {
            list.add(0, "test");
            throw new AssertionError("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }
}
