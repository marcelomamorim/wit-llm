/*
 * @test
 * @summary Test Collections.replaceAll method for correct exception throwing
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.function.UnaryOperator;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testReplaceAllThrowsUnsupported();
    }

    public static void testReplaceAllThrowsUnsupported() {
        try {
            Collections.replaceAll(new java.util.ArrayList<>(), null);
            // Since replaceAll always throws UnsupportedOperationException, this line should not be reached.
            System.err.println("Error: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("Passed: replaceAll throws UnsupportedOperationException as expected.");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception: " + e);
        }
    }
}
