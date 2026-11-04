/* @test    @summary Test remove method in ImmutableCollections to ensure it throws UnsupportedOperationException    @run main ImmutableCollectionsWitupTest */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        testRemoveMethodThrows();
    }

    public static void testRemoveMethodThrows() {
        try {
            boolean result = ImmutableCollections.remove("key", "value");
            // If no exception, the test should fail
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
        }
    }
}
