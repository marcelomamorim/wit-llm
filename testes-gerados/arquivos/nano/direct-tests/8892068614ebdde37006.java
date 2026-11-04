/* @test    @summary Test for Collections.set method to verify it throws IllegalStateException.    @run main CollectionsWitupTest */

import java.util.Collections;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testSetMethodThrowsException();
    }

    public static void testSetMethodThrowsException() {
        try {
            Collections.set("test");
            System.err.println("Expected IllegalStateException was not thrown.");
            System.exit(1);
        } catch (IllegalStateException e) {
            // Test passes if exception is caught
            System.out.println("IllegalStateException correctly thrown.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
