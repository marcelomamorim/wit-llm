/* @test    @summary Test remove method of ImmutableCollections to ensure it throws UnsupportedOperationException    @run main ImmutableCollectionsWitupTest */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        testRemoveThrowsException();
    }

    public static void testRemoveThrowsException() {
        try {
            ImmutableCollections ic = new ImmutableCollections();
            boolean result = ic.remove(new Object());
            // If no exception, fail the test
            System.err.println("Test failed: remove did not throw exception");
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
            System.out.println("Test passed: remove throws UnsupportedOperationException");
        } catch (Throwable t) {
            System.err.println("Test failed: unexpected exception: " + t);
            System.exit(1);
        }
    }
}
