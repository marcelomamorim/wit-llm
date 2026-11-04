/* @test    @summary Test for Collections.clear() method to ensure it throws UnsupportedOperationException.    @run main CollectionsWitupTest */

import java.util.Collections;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        try {
            Collections.clear();
            // If no exception is thrown, the test fails
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Test passes if exception is caught
            System.out.println("Test passed: UnsupportedOperationException thrown as expected.");
        } catch (Throwable t) {
            // Fail the test if any other exception is thrown
            throw new AssertionError("Unexpected exception thrown: " + t, t);
        }
    }
}
