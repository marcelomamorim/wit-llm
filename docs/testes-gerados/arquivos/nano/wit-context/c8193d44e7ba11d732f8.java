/*
 * @test
 * @summary Test for Collections.add method to verify exception throwing behavior.
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Map;
import java.util.AbstractMap;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testAddThrowsUnsupportedOperationException();
    }

    public static void testAddThrowsUnsupportedOperationException() {
        Map.Entry<String, String> entry = new AbstractMap.SimpleEntry<>("key", "value");
        try {
            boolean result = Collections.add(entry);
            // If no exception, the test should fail
            System.err.println("Expected UnsupportedOperationException was not thrown.");
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            // Expected exception caught, test passes
            System.out.println("Caught expected UnsupportedOperationException.");
            System.exit(0);
        } catch (Throwable t) {
            // Unexpected exception, test fails
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
