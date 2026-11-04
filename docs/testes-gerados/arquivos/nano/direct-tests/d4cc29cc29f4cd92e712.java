/* @test    @summary Test that ImmutableCollections.remove() throws UnsupportedOperationException    @run main ImmutableCollectionsWitupTest */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        testRemoveThrows();
    }

    public static void testRemoveThrows() {
        try {
            ImmutableCollections.remove();
            System.err.println("Failed: Expected UnsupportedOperationException was not thrown.");
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
            System.out.println("Passed: remove() throws UnsupportedOperationException as expected.");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception type: " + t);
            System.exit(1);
        }
    }
}
