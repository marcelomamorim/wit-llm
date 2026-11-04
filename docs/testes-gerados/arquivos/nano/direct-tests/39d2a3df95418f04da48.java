/* @test    @summary Test that ImmutableCollections.put always throws UnsupportedOperationException    @run main ImmutableCollectionsWitupTest */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        testPutThrowsException();
    }

    static void testPutThrowsException() {
        try {
            ImmutableCollections.put("key", "value");
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        } catch (UnsupportedOperationException e) {
            // Test passes if exception is thrown
        }
    }
}
