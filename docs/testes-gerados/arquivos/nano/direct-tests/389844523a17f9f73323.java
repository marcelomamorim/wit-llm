/* @test    @summary Test for replace method in ImmutableCollections class    @run main ImmutableCollectionsWitupTest */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        testReplaceMethodThrows();
    }

    public static void testReplaceMethodThrows() {
        try {
            ImmutableCollections collection = new ImmutableCollections();
            collection.replace(null, null);
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        } catch (UnsupportedOperationException e) {
            // Test passes if exception is thrown
        }
    }
}
