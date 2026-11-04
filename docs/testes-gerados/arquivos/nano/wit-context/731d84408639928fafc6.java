/* @test    @summary Test for ImmutableCollections.replaceAll method to verify exception throwing behavior.    @run main ImmutableCollectionsWitupTest */

import java.util.function.UnaryOperator;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testReplaceAllThrowsUOE();
    }

    private static void testReplaceAllThrowsUOE() {
        try {
            // Since replaceAll always throws UnsupportedOperationException, test that it does.
            ImmutableCollections.replaceAll(null);
            // If no exception thrown, test fails.
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Expected exception caught, test passes.
        } catch (Throwable t) {
            // Any other exception means test fails.
            throw new AssertionError("Unexpected exception type: " + t, t);
        }
    }
}
