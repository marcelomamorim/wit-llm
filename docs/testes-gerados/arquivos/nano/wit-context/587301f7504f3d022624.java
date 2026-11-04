/*
 * @test
 * @summary Test for ImmutableCollections.addAll method to verify exception throwing behavior.
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Collection;
import java.util.Arrays;
import java.util.List;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testAddAllThrowsUOE();
    }

    public static void testAddAllThrowsUOE() {
        try {
            // Prepare a sample collection
            List<String> sampleList = Arrays.asList("a", "b", "c");
            // Call addAll with index 0 and sample collection
            boolean result = java.util.ImmutableCollections.addAll(0, sampleList);
            // Since method throws UnsupportedOperationException, if no exception, test fails
            System.err.println("Test failed: Expected UnsupportedOperationException was not thrown.");
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            // Expected exception caught, test passes
            System.out.println("Test passed: UnsupportedOperationException was thrown as expected.");
            System.exit(0);
        } catch (Throwable t) {
            // Unexpected exception caught, test fails
            System.err.println("Test failed: Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
