/* @test    @summary Test add method throws UnsupportedOperationException in ImmutableCollections    @run main ImmutableCollectionsWitupTest */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        testAddMethodThrowsException();
    }

    static void testAddMethodThrowsException() {
        try {
            ImmutableCollections collection = new ImmutableCollections();
            collection.add(0, "test");
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        } catch (UnsupportedOperationException e) {
            // Test passes if exception is thrown
        }
    }
}
