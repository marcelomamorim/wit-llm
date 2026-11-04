/* @test    @summary Test for Collections.add method to verify UnsupportedOperationException is thrown.    @run main CollectionsWitupTest */

import java.util.Collections;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        try {
            Collections.add("test element");
            // If no exception, the test fails
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Test passes if exception is caught
            System.out.println("Test passed: UnsupportedOperationException thrown as expected.");
        }
    }
}
