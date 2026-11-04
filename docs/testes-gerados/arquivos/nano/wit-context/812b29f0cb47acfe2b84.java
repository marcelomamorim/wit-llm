/* @test    @summary Test for Collections.remove() method to verify exception throwing behavior    @run main CollectionsWitupTest */

import java.util.Collections;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testRemoveThrowsException();
    }

    public static void testRemoveThrowsException() {
        try {
            Collections.remove();
            System.err.println("Test failed: Expected IllegalStateException was not thrown.");
            System.exit(1);
        } catch (IllegalStateException e) {
            // Expected exception, test passes
            System.out.println("Test passed: IllegalStateException was thrown as expected.");
        } catch (Throwable t) {
            System.err.println("Test failed: Unexpected exception type: " + t);
            System.exit(1);
        }
    }
}
