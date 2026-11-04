/* @test    @summary Test that ImmutableCollections.clear() throws UnsupportedOperationException    @run main ImmutableCollectionsWitupTest */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        testClearThrows();
    }

    public static void testClearThrows() {
        try {
            ImmutableCollections collections = new ImmutableCollections();
            collections.clear();
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Test passes if exception is thrown
        }
    }
}
