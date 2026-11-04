/*
 * @test
 * @summary Test for Collections.replaceAll method to verify exception throwing behavior.
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
            // calling it should always result in this exception.
            Collections.replaceAll((k, v) -> v, (k, v) -> v);
            // If no exception is thrown, the test fails.
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Expected exception caught, test passes.
        } catch (Throwable t) {
            // Any other exception indicates a failure.
            throw new AssertionError("Unexpected exception type: " + t, t);
        }
    }
}
