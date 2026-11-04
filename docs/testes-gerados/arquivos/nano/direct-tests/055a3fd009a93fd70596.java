/*
 * @test
 * @summary Test that ImmutableCollections.add() always throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        testAddThrows();
    }

    static void testAddThrows() {
        ImmutableCollections collection = new ImmutableCollections();
        boolean threw = false;
        try {
            collection.add("test");
        } catch (UnsupportedOperationException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("add() did not throw UnsupportedOperationException");
        }
    }
}
