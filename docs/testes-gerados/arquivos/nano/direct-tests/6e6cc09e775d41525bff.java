/* @test    @summary Test that ImmutableCollections.add throws UnsupportedOperationException    @run main ImmutableCollectionsWitupTest */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        testAddThrowsException();
    }

    public static void testAddThrowsException() {
        ImmutableCollections collection = new ImmutableCollections();
        boolean threw = false;
        try {
            collection.add("test");
        } catch (UnsupportedOperationException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        }
    }
}
