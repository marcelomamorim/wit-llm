/*
 * @test
 * @summary Test for Collections.replaceAll method to verify behavior and exception throwing.
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.function.BiFunction;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testReplaceAllThrowsException();
    }

    public static void testReplaceAllThrowsException() {
        try {
            // Since replaceAll throws UnsupportedOperationException unconditionally,
            // we can call it directly with a dummy function.
            Collections.replaceAll((BiFunction<?, ?, ?>) null);
            // If no exception is thrown, the test fails.
            System.err.println("Test failed: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Expected exception caught, test passes.
            System.out.println("Test passed: UnsupportedOperationException was thrown as expected.");
        } catch (Exception e) {
            // Any other exception indicates a failure.
            System.err.println("Test failed: Unexpected exception: " + e);
        }
    }
}
