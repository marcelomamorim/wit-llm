/*
 * @test
 * @summary Test for Collections.set method to observe exception behavior.
 * @run main CollectionsWitupTest
 */

import java.util.Collections;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testSetThrowsException();
    }

    public static void testSetThrowsException() {
        try {
            Collections.set("test");
            System.err.println("Expected IllegalStateException was not thrown.");
            System.exit(1);
        } catch (IllegalStateException e) {
            System.out.println("Caught expected IllegalStateException.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
