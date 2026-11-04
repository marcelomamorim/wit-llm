/* @test    @summary Test for ImmutableCollections.replace method to ensure it throws UnsupportedOperationException.    @run main ImmutableCollectionsWitupTest */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        testReplaceThrows();
    }

    public static void testReplaceThrows() {
        try {
            // Attempt to call replace, expecting an UnsupportedOperationException
            boolean result = ImmutableCollections.replace("key", "oldValue", "newValue");
            // If no exception, the test fails
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Expected exception caught, test passes
        } catch (Throwable t) {
            // Any other exception means test fails
            throw new AssertionError("Unexpected exception type: " + t, t);
        }
    }
}
