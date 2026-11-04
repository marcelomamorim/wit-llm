/* @test    @summary Test for ImmutableCollections.add method to verify it throws UnsupportedOperationException.    @run main ImmutableCollectionsWitupTest */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testAddThrowsUOE();
    }

    public static void testAddThrowsUOE() {
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
            collection.add(new Object());
            System.err.println("Failed: add did not throw UnsupportedOperationException");
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            // Expected exception
            System.out.println("Passed: add threw UnsupportedOperationException as expected");
        } catch (Throwable t) {
            System.err.println("Failed: add threw unexpected exception: " + t);
            System.exit(1);
        }
    }
}
