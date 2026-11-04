/*
 * @test
 * @summary Test for Collections.add method to verify exception throwing behavior.
 * @run main CollectionsWitupTest
 */

import java.util.Collections;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testAddThrowsUnsupportedOperationException();
    }

    public static void testAddThrowsUnsupportedOperationException() {
        try {
            Collections.add("test element");
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Test passes if exception is caught
        }
    }
}
