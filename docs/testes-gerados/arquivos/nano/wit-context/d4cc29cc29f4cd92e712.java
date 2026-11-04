/* @test    @summary Test for ImmutableCollections.remove() method to verify it throws UnsupportedOperationException.    @run main ImmutableCollectionsWitupTest */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        testRemoveThrowsUOE();
    }

    public static void testRemoveThrowsUOE() {
        ImmutableCollections.AbstractImmutableCollection<?> collection = new ImmutableCollections.AbstractImmutableCollection<Object>() {
            @Override
            public java.util.Iterator<?> iterator() {
                return null;
            }
            @Override
            public int size() {
                return 0;
            }
        };
        try {
            collection.remove();
            System.out.println("FAIL: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: remove() threw UnsupportedOperationException as expected.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
