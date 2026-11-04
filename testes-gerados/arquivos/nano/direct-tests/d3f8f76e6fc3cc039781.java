/* @test    @summary Test that ImmutableCollections.remove throws UnsupportedOperationException for any input    @run main ImmutableCollectionsWitupTest */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        testRemoveThrows();
    }

    static void testRemoveThrows() {
        Object dummyKey = new Object();
        try {
            ImmutableCollections.remove(dummyKey);
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
        }
    }
}
