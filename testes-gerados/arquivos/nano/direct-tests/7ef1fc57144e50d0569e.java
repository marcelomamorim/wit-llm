/* @test    @summary Test that ImmutableCollections.set throws UnsupportedOperationException    @run main ImmutableCollectionsTest */

import java.util.ImmutableCollections;

public class ImmutableCollectionsTest {
    public static void main(String[] args) {
        testSetMethodThrows();
    }

    static void testSetMethodThrows() {
        try {
            ImmutableCollections col = new ImmutableCollections();
            col.set(0, null);
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
        }
    }
}
