/* @test    @summary Test for Collections.clear() method to verify exception behavior    @run main CollectionsWitupTest */

import java.util.Collections;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testClearThrowsUnsupportedOperationException();
    }

    public static void testClearThrowsUnsupportedOperationException() {
        try {
            Collections.clear();
            System.err.println("Expected UnsupportedOperationException was not thrown.");
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            // Test passes if exception is thrown
            System.out.println("Test passed: UnsupportedOperationException thrown as expected.");
        } catch (Throwable t) {
            System.err.println("Test failed: Unexpected exception type: " + t);
            System.exit(1);
        }
    }
}
