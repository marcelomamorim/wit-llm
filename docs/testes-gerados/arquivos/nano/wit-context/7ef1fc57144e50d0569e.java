/*
 * @test
 * @summary Test for ImmutableCollections.set method to verify exception throwing behavior.
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testSetThrowsUOE();
    }

    private static void testSetThrowsUOE() {
        try {
            // Since the set method throws UnsupportedOperationException unconditionally,
            // calling it should always result in this exception.
            ImmutableCollections.set(0, "test");
            // If no exception is thrown, the test fails.
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Expected exception caught, test passes.
        } catch (Exception e) {
            // If any other exception occurs, the test fails.
            throw new AssertionError("Unexpected exception type: " + e, e);
        }
    }
}
